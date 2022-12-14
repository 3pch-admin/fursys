package platform.erp.service;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMMaster;
import platform.ebom.entity.EBOMMasterLink;
import platform.ebom.service.EBOMMasterHelper;
import platform.ebom.vo.BOMTreeNode;
import platform.echange.ecn.entity.ECN;
import platform.erp.entity.ERPHistory;
import platform.erp.entity.NonBomInfo;
import platform.mbom.entity.MBOM;
import platform.mbom.entity.MBOMECNLink;
import platform.mbom.entity.MBOMLink;
import platform.mbom.service.MBOMHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.StringUtils;
import platform.util.db.DBCPManager;
import wt.clients.folder.FolderTaskLogic;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.part.QuantityUnit;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.views.View;
import wt.vc.views.ViewHelper;

public class StandardERPService extends StandardManager implements ERPService {

	private static final String check = "-";

	public static StandardERPService newStandardERPService() throws WTException {
		StandardERPService instance = new StandardERPService();
		instance.initialize();
		return instance;
	}

	@Override
	public List<String> material(Map<String, Object> params) throws Exception {
		Transaction trs = new Transaction();
		ArrayList<String> list = (ArrayList<String>) params.get("list");
		ArrayList<String> data = new ArrayList<String>();
		try {
			trs.start();

			for (String oid : list) {
				WTPart part = (WTPart) CommonUtils.persistable(oid);
				EPMDocument epm = PartHelper.manager.getEPMDocument(part);
				if (!"MAT".equals(IBAUtils.getStringValue(part, "PART_TYPE"))) {
					data.add("");
					continue;
				}

				String erpCode = IBAUtils.getStringValue(part, "ERP_CODE");
				if (StringUtils.isNotNull(erpCode)) {
					data.add(erpCode);
					// 기존 ERP 값으로 세팅한다.
					continue;
				}

				if (epm == null) {
					data.add("");
					String resultMessage = part.getNumber() + " 부품의 도면이 존재하지 않습니다.";
					ERPHelper.service.createHistory(resultMessage, "ERP 자재코드 생성", false);
				} else {
					String partNo = IBAUtils.getStringValue(epm, "PART_NO");
					if (StringUtils.isNotNull(partNo.trim()) && !check.equals(partNo)) {
						data.add(partNo);
						// erp 값 생성
						IBAUtils.createIBA(part, "s", "ERP_CODE", partNo); // 세트 단품일 경우
						String resultMessage = part.getName() + " 부품의 ERP CODE = " + partNo;
						ERPHelper.service.createHistory(resultMessage, "ERP 자재코드 생성", true);
					} else {
						String mat = IBAUtils.getStringValue(epm, "MATERIAL_CODE");
						String code = ERPHelper.manager.getMaterialCodeByWood(mat);

						// 코드 값이 없다면
						if (!StringUtils.isNotNull(code)) {
							// 목재 외에서 한번더 검색
							code = ERPHelper.manager.getMaterialCodeByExtWood(mat);
							// 목재외에서도 없다면 빈값 처리 후 로그 남기기
							if (!StringUtils.isNotNull(code)) {
								data.add("");
								String resultMessage = epm.getNumber() + " 도면의 자재코드와 일치하는 코드 값이 없습니다. 자재코드 = "
										+ IBAUtils.getStringValue(epm, "MATERIAL_CODE");
								ERPHelper.service.createHistory(resultMessage, "ERP 자재코드 생성", false);
							} else {
								String nextCode = ERPHelper.manager.getNextErpCode(code);
								data.add(nextCode);
								// erp 값 생성
								IBAUtils.createIBA(part, "s", "ERP_CODE", nextCode); // 세트 단품일 경우
								IBAUtils.createIBA(epm, "s", "ERP_CODE", nextCode); // 세트 단품일 경우
								IBAUtils.createIBA(part, "s", "PART_NO", nextCode); // 세트 단품일 경우
								IBAUtils.createIBA(epm, "s", "PART_NO", nextCode); // 세트 단품일 경우
								String resultMessage = part.getName() + " 부품의 ERP CODE = " + nextCode + ", CODE = "
										+ code + ", 자재코드 = " + mat;
								ERPHelper.service.createHistory(resultMessage, "ERP 자재코드 생성", true);
							}
						} else {
							// 코드 값이 잇을 경우
							String nextCode = ERPHelper.manager.getNextErpCode(code);
							data.add(nextCode);
							IBAUtils.createIBA(part, "s", "ERP_CODE", nextCode); // 세트 단품일 경우
							IBAUtils.createIBA(epm, "s", "ERP_CODE", nextCode); // 세트 단품일 경우
							IBAUtils.createIBA(part, "s", "PART_NO", nextCode); // 세트 단품일 경우
							IBAUtils.createIBA(epm, "s", "PART_NO", nextCode); // 세트 단품일 경우
							String resultMessage = part.getName() + " 부품의 ERP CODE = " + nextCode + ", CODE = " + code
									+ ", 자재코드 = " + mat;
							ERPHelper.service.createHistory(resultMessage, "ERP 자재코드 생성", true);
						}
					}
				}
			}

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return data;
	}

	@Override
	public void sendErpToECN(ECN ecn) throws Exception {
		Transaction trs = new Transaction();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			trs.start();

			con = DBCPManager.getConnection(ERPHelper.ERP_DEV_1);
			st = con.createStatement();

			StringBuffer sql = new StringBuffer();

			sql.append("INSERT INTO IF_ECN_HEADER");
			sql.append("(ECN_NUMBER, ECN_NAME, ECO_NUMBER, APPLY_DATE, ECN_NOTI_TYPE,");
			sql.append(" ECN_APPLY_TIME, CREATE_ID, CREATE_DATE, MODIFY_ID, MODIFY_DATE)");
			sql.append(" VALUES(");
			sql.append("'" + ecn.getNumber() + "',"); // ECN 번호
			sql.append("'" + ecn.getName() + "',"); // ECN 명
			sql.append("'" + ecn.getEco().getNumber() + "',"); // ECO 번호
			sql.append("'" + ecn.getApplicationDate().toString().substring(0, 10).replaceAll("-", "") + "',"); // 실제적용일
			sql.append("'" + ecn.getNotiType() + "',"); // 통보유형
			sql.append("'" + ecn.getEcnApplyTime().replaceAll("/", "") + "',"); // 적용시점
			sql.append("'" + ecn.getOwnership().getOwner().getName() + "',"); // 작성자
			
			System.out.println("to="+ecn.getCreateTimestamp().toString().substring(0, 10).replaceAll("-", ""));
			sql.append("'" + ecn.getCreateTimestamp().toString().substring(0, 10).replaceAll("-", "") + ", ");
			
//			sql.append("TO_DATE('" + ecn.getCreateTimestamp().toString().substring(0, 10).replaceAll("-", "")
//					+ "', 'YYYYMMDD'),"); // 작성일
			sql.append("'" + ecn.getModifier() + "',"); // 수정자
			sql.append("TO_DATE('" + ecn.getModifiedDate().toString().substring(0, 10).replaceAll("-", "")
					+ "', 'YYYY/MM/DD')"); // 수정일
			sql.append(")");

			System.out.println("sendErpToEcn query = " + sql.toString());

//			String resultMessage = sql.toString();
//			ERPHelper.service.createSendHistory("ERP -> ECN 정보 전송", resultMessage, "PLM TO ERP", true);

			st.execute(sql.toString());

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			DBCPManager.freeConnection(con, st, rs);
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public void sendErpToPart(ECN ecn) throws Exception {
		Transaction trs = new Transaction();
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			trs.start();

			con = DBCPManager.getConnection(ERPHelper.ERP_DEV_1);
			st = con.createStatement();

//			1	PLANT_CODE		VARCHAR2(3 BYTE)	No	Yes	
//			2	ERP_CODE_EXT	ERP CODE 확장	VARCHAR2(30 BYTE)	No	Yes	AABB000000001-P000
//			3	WTPART_APPLY_COLOR	적용색상	VARCHAR2(10 BYTE)	No	Yes	적용 컬러 코드
//			4	ECN_NUMBER	ECN 번호	VARCHAR2(14 BYTE)	No	Yes	
//			5	WTPART_NUMBER	WTPART 번호	VARCHAR2(120 BYTE)	No		
//			6	WTPART_REV	버전(리비전)	VARCHAR2(3 BYTE)	No		
//			7	ERP_CODE_EXT_NAME	ERP CODE 확장 명칭	VARCHAR2(50 BYTE)	No		
//			8	CAT_L	카테고리 大	VARCHAR2(6 BYTE)	No		
//			9	CAT_M	카테고리 中	VARCHAR2(6 BYTE)	No		
//			10	STD_KND	규격구분	VARCHAR2(6 BYTE)	No		STANDARD_CODE
//			11	STATUS	상태정보	VARCHAR2(6 BYTE)	No		M60001 항상 (사용중)
//			12	WIDTH	가로	NUMBER(8,3)	Yes		
//			13	DEPTH	세로	NUMBER(8,3)	Yes		
//			14	HEIGHT	높이	NUMBER(8,3)	Yes		
//			15	INTERFACE_YN	인터패이스 유무	VARCHAR2(1 BYTE)	No		Y : ERP 수신, N : ERP 수신전
//			16	PART_TYPE	부품유형	VARCHAR2(10 BYTE)	Yes		ITEM : 단품, MAT : 자재
//			20	SCALE	척도	VARCHAR2(1500 BYTE)	Yes		
//			21	ITEM_NAME	제품명	VARCHAR2(1500 BYTE)	Yes		
//			22	PROJECT_NAME	건명	VARCHAR2(1500 BYTE)	Yes		
//			23	DRAWN_BY	작성자	VARCHAR2(1500 BYTE)	Yes		
//			24	DRAWN_DATE	작성일	VARCHAR2(1500 BYTE)	Yes		
//			25	CHECKED_BY	검토자	VARCHAR2(1500 BYTE)	Yes		
//			26	CHECKED_DATE	검토일	VARCHAR2(1500 BYTE)	Yes		
//			27	APPROVED_BY	승인자	VARCHAR2(1500 BYTE)	Yes		
//			28	APPROVED_DATE	승인일	VARCHAR2(1500 BYTE)	Yes		
//			29	PART_NO	부품번호	VARCHAR2(1500 BYTE)	Yes		
//			30	DRAWING_NO	도면번호	VARCHAR2(1500 BYTE)	Yes		
//			31	BOM	BOM 포함여부	VARCHAR2(1 BYTE)	Yes		Y/N
//			32	PTC_WM_REVISION	리비전	VARCHAR2(1500 BYTE)	Yes		
//			33	DESCRIPTION	비고	VARCHAR2(1500 BYTE)	Yes		
//			34	IM_CAM_GCODE	G코드 생성여부	VARCHAR2(1 BYTE)	Yes		Y/N
//			35	MATERIAL	재질정보	VARCHAR2(1500 BYTE)	Yes		
//			36	PROCESS	가공정보	VARCHAR2(1500 BYTE)	Yes		
//			37	FINISH	표면처리	VARCHAR2(1500 BYTE)	Yes		
//			38	MATERIAL_CODE	자재정보 코드	VARCHAR2(1500 BYTE)	Yes		
//			39	PROCESS_CODE	가공정보 코드	VARCHAR2(1500 BYTE)	Yes		
//			40	FINISH_CODE	표면처리 코드	VARCHAR2(1500 BYTE)	Yes		
//			41	DT_WOODGRAIN	무늬결 방향	VARCHAR2(1500 BYTE)	Yes		
//			42	BOM_REQUIREMENT	BOM 소요량	VARCHAR2(1500 BYTE)	Yes		
//			43	BOM_UNIT	BOM 단위	VARCHAR2(1500 BYTE)	Yes		
//			44	PACK_TYPE	조립철물 여부	VARCHAR2(1500 BYTE)	Yes		
//			45	PART_NAME_EN	부품명 (영문)	VARCHAR2(1500 BYTE)	Yes		
//			46	CAD_KEY	CAD_KEY	VARCHAR2(1500 BYTE)	Yes		
//			52	COMPANY_CODE	회사 코드	VARCHAR2(1500 BYTE)	Yes		
//			53	BRAND_CODE	브랜드 코드	VARCHAR2(1500 BYTE)	Yes		
//			54	DUMMY_UNIT_PRICE	가단가	VARCHAR2(1500 BYTE)	Yes		
//			55	PURCHASE_YN	주문품여부	VARCHAR2(1500 BYTE)	Yes		
//			57	USE_TYPE_CODE	사용 유뮤 코드	VARCHAR2(1500 BYTE)	Yes		
//			58	CREATE_ID	생성자	VARCHAR2(30 BYTE)	No		
//			59	CREATE_DATE	생성일시	TIMESTAMP	No		일시
//			60	MODIFY_ID	수정자	VARCHAR2(30 BYTE)	No		
//			61	MODIFY_DATE	수정일시	TIMESTAMP	No		일시

			QueryResult result = PersistenceHelper.manager.navigate(ecn, "mbom", MBOMECNLink.class);
			while (result.hasMoreElements()) {
				MBOM mbom = (MBOM) result.nextElement();
				WTPart part = mbom.getPart();
				EPMDocument epm = PartHelper.manager.getEPMDocument(part);
				String headerErpCode = StringUtils.convertToStr(IBAUtils.getStringValue(part, "ERP_CODE"), "-");
				String headerVersion = ERPHelper.manager.convertVersion(ecn.getEco(), part);
				StringBuffer query = new StringBuffer();
				query.append("INSERT INTO IF_PART");
				query.append("(PLANT_CODE, ERP_CODE_EXT, WTPART_APPLY_COLOR, ECN_NUMBER, WTPART_NUMBER,");
				query.append(" WTPART_REV, ERP_CODE_EXT_NAME, CAT_L, CAT_M, STD_KND, ");
				query.append(" STATUS, WIDTH, DEPTH, HEIGHT, INTERFACE_YN, ");
				query.append(" PART_TYPE, SCALE, ITEM_NAME, PROJECT_NAME, DRAWN_BY, ");
				query.append(" DRAWN_DATE, CHECKED_BY, CHECKED_DATE, APPROVED_BY, APPROVED_DATE, ");
				query.append(" PART_NO, DRAWING_NO, BOM, PTC_WM_REVISION, DESCRIPTION, ");
				query.append(" IM_CAM_GCODE, MATERIAL, PROCESS, FINISH, MATERIAL_CODE, ");
				query.append(" PROCESS_CODE, FINISH_CODE, DT_WOODGRAIN, BOM_REQUIREMENT, BOM_UNIT, ");
				query.append(
						" PACK_TYPE, PART_NAME_EN, CAD_KEY, PART_HEIGHT, PART_DEPTH, PART_WIDTH, CAT_L_CODE, CAT_M_CODE, COMPANY_CODE, BRAND_CODE, ");
				query.append(
						" DUMMY_UNIT_PRICE, PURCHASE_YN, STANDARD_CODE, USE_TYPE_CODE, CREATE_ID, CREATE_DATE, MODIFY_ID, MODIFY_DATE, CAT_S)");
				query.append(" VALUES(");
				query.append("'" + ecn.getPlant() + "',"); // 사업장 번호(코드)
				if ("ITEM".equals(IBAUtils.getStringValue(part, "PART_TYPE"))) {
					query.append("'" + headerErpCode + "',");// ERP_CODE_EXT
				} else {
					query.append("'" + headerErpCode + "-" + headerVersion + "',"); // ERP_CODE_EXT
				}
				query.append("'" + mbom.getApplyColor() + "',"); // WTPART_APPLY_COLOR
				query.append("'" + ecn.getNumber() + "',"); // ECN_NUMBER
				query.append("'" + part.getNumber() + "',"); // WTPART_NUMBER
//			query.append("'" + headerPart.getVersionIdentifier().getSeries().getValue() + "',"); // WTPART_REV
				query.append("'" + headerVersion + "',"); // WTPART_REV
				if ("ITEM".equals(IBAUtils.getStringValue(part, "PART_TYPE"))) {
					query.append("'" + headerErpCode + "',"); // ERP_CODE_EXT_NAME
				} else {
					query.append("'" + headerErpCode + "-" + headerVersion + "',"); // ERP_CODE_EXT_NAME
				}
//				query.append("'" + headerCategory[0] + "',"); //CAT_L
//				query.append("'" + headerCategory[1] + "',"); //CAT_M
				if (StringUtils.isNotNull(headerErpCode) && headerErpCode.length() > 4) {
					query.append("'" + headerErpCode.substring(0, 2) + "',"); // CAT_L
					query.append("'" + headerErpCode.substring(2, 4) + "',"); // CAT_M
				} else {
					query.append("'" + headerErpCode + "',"); // CAT_L
					query.append("'" + headerErpCode + "',"); // CAT_M
				}
//				query.append("'" + IBAUtils.getStringValue(headerPart, "STANDARD_CODE") + "',"); // STD_KND
				query.append("'M58001',");
				query.append("'M60001',"); // STATUS
				query.append("'" + IBAUtils.getStringValue(epm, "PART_WIDTH") + "',"); // WIDTH
				query.append("'" + IBAUtils.getStringValue(epm, "PART_DEPTH") + "',"); // DEPTH
				query.append("'" + IBAUtils.getStringValue(epm, "PART_HEIGHT") + "',"); // HEIGHT
				query.append("'N',"); // INTERFACE_YN
				query.append("'" + IBAUtils.getStringValue(part, "PART_TYPE") + "',"); // PART_TYPE
				query.append("'" + IBAUtils.getStringValue(epm, "SCALE") + "',"); // SCALE
				query.append("'" + IBAUtils.getStringValue(part, "ITEM_NAME") + "',"); // ITEM_NAME
				query.append("'" + IBAUtils.getStringValue(epm, "PROJECT_NAME") + "',"); // PROJECT_NAME
				query.append("'" + IBAUtils.getStringValue(epm, "DRAWN_BY") + "',"); // DRAWN_BY
				query.append("'" + IBAUtils.getStringValue(epm, "DRAWN_DATE") + "',"); // DRAWN_DATE
				query.append("'" + IBAUtils.getStringValue(epm, "CHECKED_BY") + "',"); // CHECKED_BY
				query.append("'" + IBAUtils.getStringValue(epm, "CHECKED_DATE") + "',"); // CHECKED_DATE
				query.append("'" + IBAUtils.getStringValue(epm, "APPROVED_BY") + "',"); // APPROVED_BY
				query.append("'" + IBAUtils.getStringValue(epm, "APPROVED_DATE") + "',"); // APPROVED_DATE
				query.append("'" + IBAUtils.getStringValue(part, "PART_NO") + "',"); // PART_NO
				query.append("'" + IBAUtils.getStringValue(epm, "DRAWING_NO") + "',"); // DRAWING_NO
				query.append("'" + IBAUtils.getBooleanValue(part, "BOM") + "',"); // BOM
				query.append("'" + part.getVersionIdentifier().getSeries().getValue() + "',"); // PTC_WM_REVISION
				query.append("'" + IBAUtils.getStringValue(epm, "DESCRIPTION") + "',"); // DESCRIPTION
				query.append("'" + IBAUtils.getBooleanValue(part, "IM_CAM_GCODE") + "',"); // IM_CAM_GCODE
				query.append("'" + IBAUtils.getStringValue(epm, "MATERIAL") + "',"); // MATERIAL
				query.append("'" + IBAUtils.getStringValue(epm, "PROCESS") + "',"); // PROCESS
				query.append("'" + IBAUtils.getStringValue(epm, "FINISH") + "',"); // FINISH
				query.append("'" + IBAUtils.getStringValue(epm, "MATERIAL_CODE") + "',"); // MATERIAL_CODE
				query.append("'" + IBAUtils.getStringValue(epm, "PROCESS_CODE") + "',"); // PROCESS_CODE
				query.append("'" + IBAUtils.getStringValue(epm, "FINISH_CODE") + "',"); // FINISH_CODE
				query.append("'" + IBAUtils.getStringValue(epm, "DT_WOODGRAIN") + "',"); // DT_WOODGRAIN
				query.append("'" + IBAUtils.getStringValue(epm, "BOM_REQUIREMENT") + "',"); // BOM_REQUIREMENT
				query.append("'" + IBAUtils.getStringValue(epm, "BOM_UNIT") + "',"); // BOM_UNIT
				query.append("'" + IBAUtils.getStringValue(epm, "PACK_TYPE") + "',"); // PACK_TYPE
				query.append("'" + IBAUtils.getStringValue(part, "PART_NAME_EN") + "',"); // PART_NAME_EN
				query.append("'" + IBAUtils.getStringValue(epm, "CAD_KEY") + "',"); //
				query.append("'" + IBAUtils.getStringValue(epm, "PART_WIDTH") + "',"); // PART_HEIGHT
				query.append("'" + IBAUtils.getStringValue(epm, "PART_DEPTH") + "',"); // PART_DEPTH
				query.append("'" + IBAUtils.getStringValue(epm, "PART_WIDTH") + "',"); // PART_WIDTH
				query.append("'" + IBAUtils.getStringValue(epm, "CAT_L_CODE") + "',"); // CAT_L_CODE
				query.append("'" + IBAUtils.getStringValue(epm, "CAT_M_CODE") + "',"); // CAT_M_CODE
//				query.append("'" + IBAUtils.getStringValue(epm, "COMPANY_CODE") + "',"); //
//				query.append("'" + IBAUtils.getStringValue(epm, "BRAND_CODE") + "',"); //
				query.append("'" + ecn.getCompany() + "',"); //
				query.append("'" + ecn.getBrand() + "',"); //
				query.append("'" + IBAUtils.getStringValue(part, "DUMMY_UNIT_PRICE") + "',"); // DUMMY_UNIT_PRICE
				query.append("'" + IBAUtils.getStringValue(part, "PERCHASE_YN") + "',"); // PURCHASE_YN
				query.append("'" + IBAUtils.getStringValue(epm, "STANDARD_CODE") + "',"); // STANDARD_CODE
				query.append("'" + IBAUtils.getStringValue(epm, "USE_TYPE_CODE") + "',"); // USE_TYPE_CODE
				query.append("'" + part.getCreatorName() + "',"); // 작성자
				query.append("TO_DATE('" + part.getCreateTimestamp().toString().replaceAll("-", "").substring(0, 8)
						+ "', 'YYYY/MM/DD'),"); // 작성일
				query.append("'" + part.getModifierName() + "',"); // 수정자
				query.append("TO_DATE('" + part.getModifyTimestamp().toString().replaceAll("-", "").substring(0, 8)
						+ "', 'YYYY/MM/DD')"); // 수정일
				query.append("''"); // CAT_S
				query.append(")");

				System.out.println("PURCHASE_YN" + IBAUtils.getStringValue(part, "PURCHASE_YN"));
				System.out.println("PURCHASE_YN222 : " + IBAUtils.getStringValue(epm, "PURCHASE_YN"));
				System.out.println("PURCHASE_YN333 : " + IBAUtils.getStringValue(part, "PERCHASE_YN"));
				System.out.println("PURCHASE_YN444 : " + IBAUtils.getStringValue(epm, "PERCHASE_YN"));
				// header
				// 중복 여부 체크후 없으면 전송한다..
				// plant , erp, color, ecnnumber false 일 경우 쿼리문 시작

				if ("ITEM".equals(IBAUtils.getStringValue(part, "PART_TYPE"))) {
					if (!ERPHelper.manager.validatePart(ecn.getPlant(), headerErpCode, mbom.getApplyColor(),
							ecn.getNumber())) {
						System.out.println("sendErpToPart Header =" + query.toString());
						st.execute(query.toString());
					}
				} else {
					if (!ERPHelper.manager.validatePart(ecn.getPlant(), headerErpCode + "-" + headerVersion,
							mbom.getApplyColor(), ecn.getNumber())) {
						System.out.println("sendErpToPart Header =" + query.toString());
						st.execute(query.toString());
					}
				}
			}
			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			DBCPManager.freeConnection(con, st, rs);
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public void sendErpToBom(ECN ecn) throws Exception {
		Transaction trs = new Transaction();
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			trs.start();

			con = DBCPManager.getConnection(ERPHelper.ERP_DEV_1);
			st = con.createStatement();

			QueryResult result = PersistenceHelper.manager.navigate(ecn, "mbom", MBOMECNLink.class);
			while (result.hasMoreElements()) {
				MBOM mbom = (MBOM) result.nextElement();
				if (mbom.getManager() != null) {
					ArrayList<MBOMLink> list = MBOMHelper.manager.getAllMBOMLink(mbom);
					for (MBOMLink link : list) {
						MBOM parent = link.getParent();
						WTPart parentPart = parent.getPart();
						MBOM child = link.getChild();
						WTPart childPart = child.getPart();

						String perp = StringUtils.convertToStr(IBAUtils.getStringValue(parentPart, "ERP_CODE"), "-");
						String perpV = ERPHelper.manager.convertVersion(ecn.getEco(), parentPart);
						String cerp = StringUtils.convertToStr(IBAUtils.getStringValue(childPart, "ERP_CODE"), "-");
						String cerpV = ERPHelper.manager.convertVersion(ecn.getEco(), childPart);

						String parentColor = parent.getApplyColor();
						StringBuffer sql = new StringBuffer();
						sql.append("INSERT INTO IF_PLANT_BOM_ERP");
						sql.append(
								"(PLANT_CODE, PARENT_ERP_CODE_EXT, PARENT_WTPART_APPLY_COLOR, CHILD_ERP_CODE_EXT, CHILD_WTPART_APPLY_COLOR,");
						sql.append(" ECN_NUMBER, QTY, ORD_YN, MATDUP_YN, AS_YN,");
						sql.append(" ROUTECD, CREATE_ID, CREATE_DATE, MODIFY_ID,  MODIFY_DATE,");
						sql.append(" INTERFACE_YN)");
						sql.append(" VALUES(");
						sql.append("'" + ecn.getPlant() + "', "); // 사업장 번호(코드)

						String pv = "";
						if ("ITEM".equals(IBAUtils.getStringValue(parentPart, "PART_TYPE"))) {
							pv = perp;
						} else {
							pv = perp + "-" + perpV;
						}
						sql.append("'" + pv + "', ");// CHILD_ERP_CODE_EXT
						sql.append("'" + parentColor + "', ");// PARENT_WTPART_APPLY_COLOR

						String cv = "";
						if ("ITEM".equals(IBAUtils.getStringValue(parentPart, "PART_TYPE"))) {
							cv = cerp;
						} else {
							cv = cerp + "-" + cerpV;
						}
						sql.append("'" + cv + "', ");// CHILD_ERP_CODE_EXT

						double dd = child.getAmount();
						DecimalFormat df = new DecimalFormat("###0.000");

						String s = df.format(dd);

						sql.append("'" + child.getApplyColor() + "', ");// CHILD_WTPART_APPLY_COLOR
						sql.append("'" + ecn.getNumber() + "', ");// ECN_NUMBER
						sql.append("'" + s + "', ");// QTY
						sql.append("'N', ");// ORDYN
						sql.append("'Y', ");// MATDUP_YN
						sql.append("'Y', ");// AS_YN
						sql.append("'', ");// ROUTECD
						sql.append("'" + child.getOwnership().getOwner().getName() + "', ");// CREATE_ID
						sql.append(
								"TO_DATE('" + child.getCreateTimestamp().toString().replaceAll("-", "").substring(0, 8)
										+ "' 'YYYYMMDD'), ");// CREATE_DATE
						sql.append("'" + child.getOwnership().getOwner().getName() + "', ");// MODIFY_ID
						sql.append(
								"TO_DATE('" + child.getModifyTimestamp().toString().replaceAll("-", "").substring(0, 8)
										+ "' 'YYYYMMDD'), ");// MODIFY_DATE
						sql.append("'N'");// INTERFACE_YN
						sql.append(")");

//					ECNHelper.service.dtmg(childPart.getNumber(),
//							childPart.getVersionIdentifier().getSeries().getValue(), link.getApplyColor());

						// plant code perp, pcolor, cerp, ccolor, ecnnumber
						if (!ERPHelper.manager.validateBom(ecn.getPlant(), pv, parentColor, cv, child.getApplyColor(),
								ecn.getNumber())) {
							System.out.println("sendErpToBom(ECN) query=" + sql.toString());
							st.execute(sql.toString());
						}
					}
				}
			}
			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			DBCPManager.freeConnection(con, st, rs);
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public void createSendHistory(String name, String resultMessage, String sendType, boolean sendResult)
			throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}

	}

	@Override
	public void createHistory(String resultMessage, String createType, boolean createResult) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			ERPHistory history = ERPHistory.newERPHistory();
			history.setCreateResult(createResult);
			history.setCreateType(createType);
			history.setResultMessage(resultMessage);
			history.setOwnership(Ownership.newOwnership(prin));
			PersistenceHelper.manager.save(history);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public Map<String, Object> getDCost(Map<String, Object> params) throws Exception {
		double dcost = 0;
		Transaction trs = new Transaction();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Map<String, Object> result = new HashMap<String, Object>();
		String oid = (String) params.get("oid");
		String color = (String) params.get("color");
		try {
			trs.start();

			EBOM header = (EBOM) CommonUtils.persistable(oid);
			String erpCode = IBAUtils.getStringValue(header.getPart(), "ERP_CODE");
			con = DBCPManager.getConnection(ERPHelper.ERP_DEV_2);
			st = con.createStatement();

			StringBuffer sb = new StringBuffer();
			sb.append("SELECT MATPRICE FROM TV_PRICE ");
			sb.append("WHERE MATCD='" + erpCode + "'");
			sb.append(" AND MATCOL='" + color + "'");

			rs = st.executeQuery(sb.toString());
			if (rs.next()) {
				String p = (String) rs.getString(0);
				dcost = Double.parseDouble(p);
				header.setDcost(dcost);
				PersistenceHelper.manager.modify(header);
			}

			result.put("dcost", dcost);

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
			DBCPManager.freeConnection(con, st, rs);
		}
		return result;
	}

	@Override
	public void structure(Map<String, Object> params) throws Exception {
		String json = (String) params.get("json");
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTPrincipal prin = SessionHelper.manager.getPrincipal();

			String jsonStr = new String(DatatypeConverter.parseBase64Binary(json), "UTF-8");
			Type listType = new TypeToken<ArrayList<BOMTreeNode>>() {
			}.getType();

			Gson gson = new Gson();
			List<BOMTreeNode> nodes = gson.fromJson(jsonStr, listType);

			for (BOMTreeNode node : nodes) {
				String poid = node.getPoid();
//				WTPart part = (WTPart) CommonUtils.persistable(poid);
				WTPartMaster mm = (WTPartMaster) CommonUtils.persistable(poid);
				WTPart part = PartHelper.manager.getLatest(mm);
				List<NonBomInfo> data = ERPHelper.manager.getNonBomInfo(part);

				HashMap<Integer, Object> parentMap = new HashMap<Integer, Object>();

				int sort = 0;
				for (NonBomInfo info : data) {
					WTPart p = null;
					String number = info.getErpCode();
					QuerySpec query = new QuerySpec();
					int idx = query.appendClassList(WTPart.class, true);
					SearchCondition sc = new SearchCondition(WTPart.class, WTPart.NUMBER, "=", number);
					query.appendWhere(sc, new int[] { idx });

					QueryResult result = PersistenceHelper.manager.find(query);
					if (!result.hasMoreElements()) {
						// 없다면 부품을 만든다..
						p = WTPart.newWTPart();
						p.setName(info.getPartName());
						p.setNumber(number);

						View view = ViewHelper.service.getView("Design");
						ViewHelper.assignToView(p, view);

						part.setDefaultUnit(QuantityUnit.toQuantityUnit("ea")); // 단위설정필요..

						Folder folder = FolderTaskLogic.getFolder("/Default/NON-BOM",
								CommonUtils.getLibrary("Library-Fursys"));
						FolderHelper.assignLocation((FolderEntry) p, folder);

						PersistenceHelper.manager.save(p);

						IBAUtils.createIBA(p, "string", "PART_TYPE", "MAT"); // 무조건 자재
						IBAUtils.createIBA(p, "string", "ERP_CODE", info.getErpCode()); // 무조건 자재
						IBAUtils.createIBA(p, "string", "PART_NAME", info.getPartName());
					} else {
						Object[] obj = (Object[]) result.nextElement();
						p = (WTPart) obj[0];
						p = (WTPart) CommonUtils.getLatestVersion(p);
					}

					double amount = new NonBomInfo().getTotalQty(info.getBq(), p);

					// EBOM 잇는지 체크..
					EBOMMaster parent = (EBOMMaster) parentMap.get(info.getLevel() - 1);
					if (parent == null) {
						parent = (EBOMMaster) CommonUtils.persistable(node.getOid()); // 부모가 될 OID
					}

					EBOMMaster child = EBOMMasterHelper.manager.getEBOMMaster(p.getMaster());
					if (child == null) {
						child = EBOMMaster.newEBOMMaster();
						child.setName(StringUtils.convertToStr(node.getItemName(), "-"));
						child.setNumber(p.getNumber()); // 번호가 멀로 될지??
						child.setVer(p.getVersionIdentifier().getSeries().getValue());
						child.setApplyColor(null);
						child.setDerivedColor(null);
						child.setColor(EBOMMasterHelper.DEFAULT_COLOR);
						child.setAmount(amount);
						child.setState(EBOMMasterHelper.EBOM_CREATE);
						child.setBomType(EBOMMasterHelper.EBOM_TYPE);
						child.setOwnership(Ownership.newOwnership(prin));
						child.setPart((WTPartMaster) p.getMaster());
						child = (EBOMMaster) PersistenceHelper.manager.save(child);
					}

					// 모가 없을 경우 헤더 링크.

					boolean exist = EBOMMasterHelper.manager.isExistLink(parent, child);
					if (exist) {
						parentMap.put(info.getLevel(), child); // 레벨 ebom..
						continue;
					}

					EBOMMasterLink link = EBOMMasterLink.newEBOMMasterLink(parent, child);
					link.setDepth(node.getLevel());
					link.setSort(sort++);
					link.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.save(link);
					parentMap.put(info.getLevel(), child); // 레벨 ebom..

				} // end nonBom Info..
			} // end treeNode

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}
}