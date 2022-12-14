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
					// ?????? ERP ????????? ????????????.
					continue;
				}

				if (epm == null) {
					data.add("");
					String resultMessage = part.getNumber() + " ????????? ????????? ???????????? ????????????.";
					ERPHelper.service.createHistory(resultMessage, "ERP ???????????? ??????", false);
				} else {
					String partNo = IBAUtils.getStringValue(epm, "PART_NO");
					if (StringUtils.isNotNull(partNo.trim()) && !check.equals(partNo)) {
						data.add(partNo);
						// erp ??? ??????
						IBAUtils.createIBA(part, "s", "ERP_CODE", partNo); // ?????? ????????? ??????
						String resultMessage = part.getName() + " ????????? ERP CODE = " + partNo;
						ERPHelper.service.createHistory(resultMessage, "ERP ???????????? ??????", true);
					} else {
						String mat = IBAUtils.getStringValue(epm, "MATERIAL_CODE");
						String code = ERPHelper.manager.getMaterialCodeByWood(mat);

						// ?????? ?????? ?????????
						if (!StringUtils.isNotNull(code)) {
							// ?????? ????????? ????????? ??????
							code = ERPHelper.manager.getMaterialCodeByExtWood(mat);
							// ?????????????????? ????????? ?????? ?????? ??? ?????? ?????????
							if (!StringUtils.isNotNull(code)) {
								data.add("");
								String resultMessage = epm.getNumber() + " ????????? ??????????????? ???????????? ?????? ?????? ????????????. ???????????? = "
										+ IBAUtils.getStringValue(epm, "MATERIAL_CODE");
								ERPHelper.service.createHistory(resultMessage, "ERP ???????????? ??????", false);
							} else {
								String nextCode = ERPHelper.manager.getNextErpCode(code);
								data.add(nextCode);
								// erp ??? ??????
								IBAUtils.createIBA(part, "s", "ERP_CODE", nextCode); // ?????? ????????? ??????
								IBAUtils.createIBA(epm, "s", "ERP_CODE", nextCode); // ?????? ????????? ??????
								IBAUtils.createIBA(part, "s", "PART_NO", nextCode); // ?????? ????????? ??????
								IBAUtils.createIBA(epm, "s", "PART_NO", nextCode); // ?????? ????????? ??????
								String resultMessage = part.getName() + " ????????? ERP CODE = " + nextCode + ", CODE = "
										+ code + ", ???????????? = " + mat;
								ERPHelper.service.createHistory(resultMessage, "ERP ???????????? ??????", true);
							}
						} else {
							// ?????? ?????? ?????? ??????
							String nextCode = ERPHelper.manager.getNextErpCode(code);
							data.add(nextCode);
							IBAUtils.createIBA(part, "s", "ERP_CODE", nextCode); // ?????? ????????? ??????
							IBAUtils.createIBA(epm, "s", "ERP_CODE", nextCode); // ?????? ????????? ??????
							IBAUtils.createIBA(part, "s", "PART_NO", nextCode); // ?????? ????????? ??????
							IBAUtils.createIBA(epm, "s", "PART_NO", nextCode); // ?????? ????????? ??????
							String resultMessage = part.getName() + " ????????? ERP CODE = " + nextCode + ", CODE = " + code
									+ ", ???????????? = " + mat;
							ERPHelper.service.createHistory(resultMessage, "ERP ???????????? ??????", true);
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
			sql.append("'" + ecn.getNumber() + "',"); // ECN ??????
			sql.append("'" + ecn.getName() + "',"); // ECN ???
			sql.append("'" + ecn.getEco().getNumber() + "',"); // ECO ??????
			sql.append("'" + ecn.getApplicationDate().toString().substring(0, 10).replaceAll("-", "") + "',"); // ???????????????
			sql.append("'" + ecn.getNotiType() + "',"); // ????????????
			sql.append("'" + ecn.getEcnApplyTime().replaceAll("/", "") + "',"); // ????????????
			sql.append("'" + ecn.getOwnership().getOwner().getName() + "',"); // ?????????
			
			System.out.println("to="+ecn.getCreateTimestamp().toString().substring(0, 10).replaceAll("-", ""));
			sql.append("'" + ecn.getCreateTimestamp().toString().substring(0, 10).replaceAll("-", "") + ", ");
			
//			sql.append("TO_DATE('" + ecn.getCreateTimestamp().toString().substring(0, 10).replaceAll("-", "")
//					+ "', 'YYYYMMDD'),"); // ?????????
			sql.append("'" + ecn.getModifier() + "',"); // ?????????
			sql.append("TO_DATE('" + ecn.getModifiedDate().toString().substring(0, 10).replaceAll("-", "")
					+ "', 'YYYY/MM/DD')"); // ?????????
			sql.append(")");

			System.out.println("sendErpToEcn query = " + sql.toString());

//			String resultMessage = sql.toString();
//			ERPHelper.service.createSendHistory("ERP -> ECN ?????? ??????", resultMessage, "PLM TO ERP", true);

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
//			2	ERP_CODE_EXT	ERP CODE ??????	VARCHAR2(30 BYTE)	No	Yes	AABB000000001-P000
//			3	WTPART_APPLY_COLOR	????????????	VARCHAR2(10 BYTE)	No	Yes	?????? ?????? ??????
//			4	ECN_NUMBER	ECN ??????	VARCHAR2(14 BYTE)	No	Yes	
//			5	WTPART_NUMBER	WTPART ??????	VARCHAR2(120 BYTE)	No		
//			6	WTPART_REV	??????(?????????)	VARCHAR2(3 BYTE)	No		
//			7	ERP_CODE_EXT_NAME	ERP CODE ?????? ??????	VARCHAR2(50 BYTE)	No		
//			8	CAT_L	???????????? ???	VARCHAR2(6 BYTE)	No		
//			9	CAT_M	???????????? ???	VARCHAR2(6 BYTE)	No		
//			10	STD_KND	????????????	VARCHAR2(6 BYTE)	No		STANDARD_CODE
//			11	STATUS	????????????	VARCHAR2(6 BYTE)	No		M60001 ?????? (?????????)
//			12	WIDTH	??????	NUMBER(8,3)	Yes		
//			13	DEPTH	??????	NUMBER(8,3)	Yes		
//			14	HEIGHT	??????	NUMBER(8,3)	Yes		
//			15	INTERFACE_YN	??????????????? ??????	VARCHAR2(1 BYTE)	No		Y : ERP ??????, N : ERP ?????????
//			16	PART_TYPE	????????????	VARCHAR2(10 BYTE)	Yes		ITEM : ??????, MAT : ??????
//			20	SCALE	??????	VARCHAR2(1500 BYTE)	Yes		
//			21	ITEM_NAME	?????????	VARCHAR2(1500 BYTE)	Yes		
//			22	PROJECT_NAME	??????	VARCHAR2(1500 BYTE)	Yes		
//			23	DRAWN_BY	?????????	VARCHAR2(1500 BYTE)	Yes		
//			24	DRAWN_DATE	?????????	VARCHAR2(1500 BYTE)	Yes		
//			25	CHECKED_BY	?????????	VARCHAR2(1500 BYTE)	Yes		
//			26	CHECKED_DATE	?????????	VARCHAR2(1500 BYTE)	Yes		
//			27	APPROVED_BY	?????????	VARCHAR2(1500 BYTE)	Yes		
//			28	APPROVED_DATE	?????????	VARCHAR2(1500 BYTE)	Yes		
//			29	PART_NO	????????????	VARCHAR2(1500 BYTE)	Yes		
//			30	DRAWING_NO	????????????	VARCHAR2(1500 BYTE)	Yes		
//			31	BOM	BOM ????????????	VARCHAR2(1 BYTE)	Yes		Y/N
//			32	PTC_WM_REVISION	?????????	VARCHAR2(1500 BYTE)	Yes		
//			33	DESCRIPTION	??????	VARCHAR2(1500 BYTE)	Yes		
//			34	IM_CAM_GCODE	G?????? ????????????	VARCHAR2(1 BYTE)	Yes		Y/N
//			35	MATERIAL	????????????	VARCHAR2(1500 BYTE)	Yes		
//			36	PROCESS	????????????	VARCHAR2(1500 BYTE)	Yes		
//			37	FINISH	????????????	VARCHAR2(1500 BYTE)	Yes		
//			38	MATERIAL_CODE	???????????? ??????	VARCHAR2(1500 BYTE)	Yes		
//			39	PROCESS_CODE	???????????? ??????	VARCHAR2(1500 BYTE)	Yes		
//			40	FINISH_CODE	???????????? ??????	VARCHAR2(1500 BYTE)	Yes		
//			41	DT_WOODGRAIN	????????? ??????	VARCHAR2(1500 BYTE)	Yes		
//			42	BOM_REQUIREMENT	BOM ?????????	VARCHAR2(1500 BYTE)	Yes		
//			43	BOM_UNIT	BOM ??????	VARCHAR2(1500 BYTE)	Yes		
//			44	PACK_TYPE	???????????? ??????	VARCHAR2(1500 BYTE)	Yes		
//			45	PART_NAME_EN	????????? (??????)	VARCHAR2(1500 BYTE)	Yes		
//			46	CAD_KEY	CAD_KEY	VARCHAR2(1500 BYTE)	Yes		
//			52	COMPANY_CODE	?????? ??????	VARCHAR2(1500 BYTE)	Yes		
//			53	BRAND_CODE	????????? ??????	VARCHAR2(1500 BYTE)	Yes		
//			54	DUMMY_UNIT_PRICE	?????????	VARCHAR2(1500 BYTE)	Yes		
//			55	PURCHASE_YN	???????????????	VARCHAR2(1500 BYTE)	Yes		
//			57	USE_TYPE_CODE	?????? ?????? ??????	VARCHAR2(1500 BYTE)	Yes		
//			58	CREATE_ID	?????????	VARCHAR2(30 BYTE)	No		
//			59	CREATE_DATE	????????????	TIMESTAMP	No		??????
//			60	MODIFY_ID	?????????	VARCHAR2(30 BYTE)	No		
//			61	MODIFY_DATE	????????????	TIMESTAMP	No		??????

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
				query.append("'" + ecn.getPlant() + "',"); // ????????? ??????(??????)
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
				query.append("'" + part.getCreatorName() + "',"); // ?????????
				query.append("TO_DATE('" + part.getCreateTimestamp().toString().replaceAll("-", "").substring(0, 8)
						+ "', 'YYYY/MM/DD'),"); // ?????????
				query.append("'" + part.getModifierName() + "',"); // ?????????
				query.append("TO_DATE('" + part.getModifyTimestamp().toString().replaceAll("-", "").substring(0, 8)
						+ "', 'YYYY/MM/DD')"); // ?????????
				query.append("''"); // CAT_S
				query.append(")");

				System.out.println("PURCHASE_YN" + IBAUtils.getStringValue(part, "PURCHASE_YN"));
				System.out.println("PURCHASE_YN222 : " + IBAUtils.getStringValue(epm, "PURCHASE_YN"));
				System.out.println("PURCHASE_YN333 : " + IBAUtils.getStringValue(part, "PERCHASE_YN"));
				System.out.println("PURCHASE_YN444 : " + IBAUtils.getStringValue(epm, "PERCHASE_YN"));
				// header
				// ?????? ?????? ????????? ????????? ????????????..
				// plant , erp, color, ecnnumber false ??? ?????? ????????? ??????

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
						sql.append("'" + ecn.getPlant() + "', "); // ????????? ??????(??????)

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
						// ????????? ????????? ?????????..
						p = WTPart.newWTPart();
						p.setName(info.getPartName());
						p.setNumber(number);

						View view = ViewHelper.service.getView("Design");
						ViewHelper.assignToView(p, view);

						part.setDefaultUnit(QuantityUnit.toQuantityUnit("ea")); // ??????????????????..

						Folder folder = FolderTaskLogic.getFolder("/Default/NON-BOM",
								CommonUtils.getLibrary("Library-Fursys"));
						FolderHelper.assignLocation((FolderEntry) p, folder);

						PersistenceHelper.manager.save(p);

						IBAUtils.createIBA(p, "string", "PART_TYPE", "MAT"); // ????????? ??????
						IBAUtils.createIBA(p, "string", "ERP_CODE", info.getErpCode()); // ????????? ??????
						IBAUtils.createIBA(p, "string", "PART_NAME", info.getPartName());
					} else {
						Object[] obj = (Object[]) result.nextElement();
						p = (WTPart) obj[0];
						p = (WTPart) CommonUtils.getLatestVersion(p);
					}

					double amount = new NonBomInfo().getTotalQty(info.getBq(), p);

					// EBOM ????????? ??????..
					EBOMMaster parent = (EBOMMaster) parentMap.get(info.getLevel() - 1);
					if (parent == null) {
						parent = (EBOMMaster) CommonUtils.persistable(node.getOid()); // ????????? ??? OID
					}

					EBOMMaster child = EBOMMasterHelper.manager.getEBOMMaster(p.getMaster());
					if (child == null) {
						child = EBOMMaster.newEBOMMaster();
						child.setName(StringUtils.convertToStr(node.getItemName(), "-"));
						child.setNumber(p.getNumber()); // ????????? ?????? ????????
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

					// ?????? ?????? ?????? ?????? ??????.

					boolean exist = EBOMMasterHelper.manager.isExistLink(parent, child);
					if (exist) {
						parentMap.put(info.getLevel(), child); // ?????? ebom..
						continue;
					}

					EBOMMasterLink link = EBOMMasterLink.newEBOMMasterLink(parent, child);
					link.setDepth(node.getLevel());
					link.setSort(sort++);
					link.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.save(link);
					parentMap.put(info.getLevel(), child); // ?????? ebom..

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