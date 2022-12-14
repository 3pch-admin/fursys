package platform.erp.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.sparql.pfunction.library.container;

import platform.code.service.BaseCodeHelper;
import platform.echange.eco.entity.ECO;
import platform.erp.entity.ERPColumns;
import platform.erp.entity.ERPSendHistory;
import platform.erp.entity.NonBomInfo;
import platform.part.service.PartHelper;
import platform.tree.entity.BomQuantity;
import platform.tree.entity.Combination;
import platform.tree.entity.EMaterialInfo;
import platform.util.IBAUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import platform.util.db.DBCPManager;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.iba.value.StringValue;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;

public class ERPHelper {

	public static final ERPService service = ServiceFactory.getService(ERPService.class);
	public static final ERPHelper manager = new ERPHelper();
	public static final String ERP_DEV_1 = "erpdev_1";
	public static final String ERP_DEV_2 = "erpdev_2";
	public static final String ERP = "erp";

	public static final String API_URL = "http://192.8.231.236:8081/dtIf/pcost";
	public static final String API_TOKEN = "WMJMC4Bxm4TrK4YdNXmzMhb4fXyRWg3Yn6nQgZ5Cg5k2cuXM7G";

	public List<Map<String, Object>> getErpCode(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String itm_type_val = (String) params.get("itm_type");
		String itm_cd_val = (String) params.get("itm_cd");
		String col_cd_val = (String) params.get("col_cd");
		String itm_nm_val = (String) params.get("itm_nm");
		String com_brand_val = (String) params.get("com_brand");
		String com_stdsec_val = (String) params.get("com_stdsec");
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {

			con = DBCPManager.getConnection(ERP_DEV_2);
			st = con.createStatement();

			StringBuffer sql = new StringBuffer();

			sql.append("SELECT ITM_TYPE, ITM_CD, COL_CD, ITM_NM, COM_BRAND, COM_STDSEC, USE_YN");
			sql.append(" FROM TV_ITMMST");
			sql.append(" WHERE USE_YN='사용'");

			if (StringUtils.isNotNull(itm_type_val)) {
				sql.append(" AND ITM_TYPE='" + itm_type_val + "'");
			}
			if (StringUtils.isNotNull(itm_cd_val)) {
				sql.append(" AND ITM_CD LIKE'" + itm_cd_val + "'");
			}
			if (StringUtils.isNotNull(col_cd_val)) {
				sql.append(" AND COL_CD LIKE'" + col_cd_val + "'");
			}
			if (StringUtils.isNotNull(itm_nm_val)) {
				sql.append(" AND ITM_NM LIKE'" + itm_nm_val + "'");
			}
			if (StringUtils.isNotNull(com_stdsec_val)) {
				sql.append(" AND COM_STDSEC='" + com_stdsec_val + "'");
			}
			if (StringUtils.isNotNull(com_brand_val)) {
				sql.append(" AND COM_BRAND='" + com_brand_val + "'");
			}

			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				HashMap<String, Object> resultMap = new HashMap<String, Object>();
				String itm_type = (String) rs.getString(1);
				String itm_cd = (String) rs.getString(2);
				String col_cd = (String) rs.getString(3);
				String itm_nm = (String) rs.getString(4);
				String com_brand = (String) rs.getString(5);
				String com_stdsec = (String) rs.getString(6);
				String use_yn = (String) rs.getString(7);

				resultMap.put("itm_type", itm_type.equals("ITM") ? "단품" : "세트");
				resultMap.put("itm_cd", itm_cd);
				resultMap.put("col_cd", col_cd);
				resultMap.put("itm_nm", itm_nm);
				resultMap.put("com_brand", com_brand);
				resultMap.put("com_brand_nm", BaseCodeHelper.manager.getNameByCodeTypeAndCode("BRNAD", com_brand));
				resultMap.put("com_stdsec", com_stdsec);
				resultMap.put("use_yn", use_yn);
				list.add(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPManager.freeConnection(con, st, rs);
		}
		return list;
	}

	public String getMaterialCodeByWood(String materialCode) throws Exception {
		if (!StringUtils.isNotNull(materialCode)) {
			return "";
		}
		String rtn = "";
		DecimalFormat df = new DecimalFormat("000000");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Combination.class, true);

		SearchCondition sc = new SearchCondition(Combination.class, Combination.CODE, "=", materialCode);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Combination combination = (Combination) obj[0];
			String erp_l = combination.getErp_l().trim();
			String erp_m = combination.getErp_m().trim();
//			rtn += erp_l + erp_m + df.format(1L);
			rtn = erp_l + erp_m;
		}
		return rtn;
	}

	public String getMaterialCodeByExtWood(String materialCode) throws Exception {
		if (!StringUtils.isNotNull(materialCode)) {
			return "";
		}
		String rtn = "";
		DecimalFormat df = new DecimalFormat("000000");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(EMaterialInfo.class, true);

		SearchCondition sc = new SearchCondition(EMaterialInfo.class, EMaterialInfo.CODE, "=", materialCode);
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			EMaterialInfo info = (EMaterialInfo) obj[0];
			String erp_l = info.getErp_l().trim();
			String erp_m = info.getErp_m().trim();
//			rtn += erp_l + erp_m + df.format(1L);
			rtn += erp_l + erp_m;
		}
		return rtn;
	}

	public String[] getCategory(WTPart part) throws Exception {
		String[] data = new String[2];
		EPMDocument epm = PartHelper.manager.getEPMDocument(part);
		if (!"MAT".equals(IBAUtils.getStringValue(part, "PART_TYPE"))) {
			data[0] = "";
			data[1] = "";
			return data;
		}
		if (epm == null) {
			System.out.println("NON CAD 자재");
			data[0] = "";
			data[1] = "";
		}

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Combination.class, true);

		SearchCondition sc = new SearchCondition(Combination.class, Combination.CODE, "=",
				IBAUtils.getStringValue(epm, "MATERIAL_CODE"));
		query.appendWhere(sc, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Combination combination = (Combination) obj[0];
			String cat_l = combination.getCat_l().trim();
			String cat_m = combination.getCat_m().trim();
			data[0] = cat_l;
			data[1] = cat_m;
		}
		return data;
	}

	public List<NonBomInfo> getNonBomInfo(WTPart part) throws Exception {
		List<NonBomInfo> list = new ArrayList<NonBomInfo>();
		String materialCode = IBAUtils.getStringValue(part, "MATERIAL_CODE");
		String processCode = IBAUtils.getStringValue(part, "PROCESS_CODE");
		String finishCode = IBAUtils.getStringValue(part, "FINISH_CODE");

		if (!StringUtils.isNotNull(materialCode) && !StringUtils.isNotNull(processCode)
				&& !StringUtils.isNotNull(finishCode)) {
			return list;
		}

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(BomQuantity.class, true);

		SearchCondition sc = null;
		query.appendOpenParen();
		if (StringUtils.isNotNull(materialCode)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(BomQuantity.class, BomQuantity.MATERIAL_INFO, "=", materialCode);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(processCode)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}
			sc = new SearchCondition(BomQuantity.class, BomQuantity.PROCESS_INFO, "=", processCode);
			query.appendWhere(sc, new int[] { idx });
		}

		if (StringUtils.isNotNull(finishCode)) {
			if (query.getConditionCount() > 0) {
				query.appendAnd();
			}

			sc = new SearchCondition(BomQuantity.class, BomQuantity.TREATMENT, "=", finishCode);
			query.appendWhere(sc, new int[] { idx });
		}

		query.appendCloseParen();

		ClassAttribute ca = new ClassAttribute(BomQuantity.class, BomQuantity.SORT);
		OrderBy by = new OrderBy(ca, false);
		query.appendOrderBy(by, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find(query);
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			BomQuantity bq = (BomQuantity) obj[0];
			list.add(new NonBomInfo(bq));
		}

		return list;
	}

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ERPColumns> list = new ArrayList<>();
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(ERPSendHistory.class, true);

		ClassAttribute ca = null;

		ca = new ClassAttribute(ERPSendHistory.class, ERPSendHistory.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, false);
		query.appendOrderBy(by, new int[] { idx });

//		QueryResult result = PersistenceHelper.manager.find(query);
		PageUtils pager = new PageUtils(params, query);
		QueryResult result = pager.find();
		int total = pager.getTotal();
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			ERPColumns columns = new ERPColumns((ERPSendHistory) obj[0]);
			columns.setNo(total--);
			list.add(columns);
		}
		map.put("list", list);
		map.put("topListCount", pager.getTotal());
		map.put("sessionid", pager.getSessionId());
		map.put("curPage", pager.getCpage());
		map.put("pageSize", pager.getPsize());
		map.put("total", pager.getTotalSize());
		return map;
	}

	public String getNextErpCode(String code) throws Exception {
		DecimalFormat df = new DecimalFormat("000000");
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(StringValue.class, true);
		SearchCondition sc = new SearchCondition(StringValue.class, StringValue.VALUE, "LIKE", code + "%");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute ca = new ClassAttribute(StringValue.class, StringValue.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		query.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);
		String nextCode = "";
		if (result.size() == 0) {
			int next = startSeq(code);
			return code + df.format(next);
		}
		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			StringValue sv = (StringValue) obj[0];
			String value = sv.getValue();
			if (value.length() > 10) {
				continue;
			}
			String s = value.substring(code.length());
			int next = Integer.parseInt(s) + 1;
			nextCode = code + df.format(next);
			return nextCode;
		}
		return "";
	}

	public int startSeq(String code) throws Exception {
		if ("WDWP".equals(code)) {
			return 1201;
		} else if ("WDWW".equals(code)) {
			return 15101;
		} else if ("STCP".equals(code)) {
			return 101;
		} else if ("STPP".equals(code)) {
			return 101;
		} else if ("STSW".equals(code)) {
			return 401;
		} else if ("STPG".equals(code)) {
			return 1201;
		} else if ("STCG".equals(code)) {
			return 1501;
		} else if ("STCM".equals(code)) {
			return 21;
		} else if ("OPPR".equals(code)) {
			return 41;
		} else if ("OPFR".equals(code)) {
			return 61;
		} else if ("OPCW".equals(code)) {
			return 601;
		} else if ("OPPW".equals(code)) {
			return 2650;
		} else if ("OPFW".equals(code)) {
			return 2101;
		} else if ("NSNR".equals(code)) {
			return 11;
		} else if ("NSNW".equals(code)) {
			return 1101;
		} else if ("PPRP".equals(code)) {
			return 21;
		} else if ("PPFC".equals(code)) {
			return 201;
		} else if ("PPFH".equals(code)) {
			return 101;
		} else if ("PPBX".equals(code)) {
			return 6201;
		} else if ("PPGW".equals(code)) {
			return 1101;
		} else if ("FBRF".equals(code)) {
			return 701;
		} else if ("FBFP".equals(code)) {
			return 7201;
		} else if ("ETET".equals(code)) {
			return 501;
		} else if ("ETFS".equals(code)) {
			return 1701;
		} else if ("ETEP".equals(code)) {
			return 201;
		} else if ("ETCS".equals(code)) {
			return 401;
		} else if ("ETPJ".equals(code)) {
			return 6201;
		} else if ("ETHW".equals(code)) {
			return 701;
		}
		return 1;
	}

	public List<Map<String, Object>> getStock(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String storecd_val = (String) params.get("storecd");
		String matcd_val = (String) params.get("matcd");
		String matcol_val = (String) params.get("matcol");
		String yymm_val = (String) params.get("yymm");

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {

			con = DBCPManager.getConnection(ERP_DEV_2);
			st = con.createStatement();

			StringBuffer sql = new StringBuffer();
//             : 단위(명칭),  : 단가

			sql.append("SELECT STORECD, MATCD, MATCOL, NOWQTY, UNIT, EXPR1, YYMM");
			sql.append(" FROM TV_STORESTK");

			if (StringUtils.isNotNull(storecd_val)) {
				sql.append(" AND STORECD='" + storecd_val + "'");
			}
			if (StringUtils.isNotNull(matcd_val)) {
				sql.append(" AND MATCD='" + matcd_val + "'");
			}
			if (StringUtils.isNotNull(matcol_val)) {
				sql.append(" AND MATCOL='" + matcol_val + "'");
			}
			if (StringUtils.isNotNull(yymm_val)) {
				sql.append(" AND YYMM LIKE'" + yymm_val + "'");
			}

			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				HashMap<String, Object> resultMap = new HashMap<String, Object>();
				String storecd = (String) rs.getString(1);
				String matcd = (String) rs.getString(2);
				String matcol = (String) rs.getString(3);
				String nowqty = (String) rs.getString(4);
				String unit = (String) rs.getString(5);
				String expr1 = (String) rs.getString(6);

				resultMap.put("storecd", storecd);
				resultMap.put("matcd", matcd);
				resultMap.put("matcol", matcol);
				resultMap.put("nowqty", nowqty);
				resultMap.put("unit", unit);
				resultMap.put("expr1", expr1);
				list.add(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPManager.freeConnection(con, st, rs);
		}
		return list;
	}

	public boolean validatePart(String plant, String erpCode, String color, String number) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {

			con = DBCPManager.getConnection(ERP_DEV_1);
			st = con.createStatement();

			StringBuffer sb = new StringBuffer();
			sb.append("SELECT * FROM IF_PART ");
			sb.append("WHERE PLANT_CODE='" + plant + "' AND");
			sb.append(" ERP_CODE_EXT='" + erpCode + "' AND");
			sb.append(" WTPART_APPLY_COLOR='" + color + "' AND");
			sb.append(" ECN_NUMBER='" + number + "'");

			System.out.println("validatePart = " + sb.toString());

			// 값이 있으면 안되
			rs = st.executeQuery(sb.toString());
			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPManager.freeConnection(con, st, rs);
		}
		return false;
	}

	public boolean validateBom(String plant, String perp, String pcolor, String cerp, String ccolor, String number) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {

			con = DBCPManager.getConnection(ERP_DEV_1);
			st = con.createStatement();

			StringBuffer sb = new StringBuffer();
			sb.append("SELECT * FROM IF_PLANT_BOM_ERP ");
			sb.append("WHERE PLANT_CODE='" + plant + "' AND");
			sb.append(" PARENT_ERP_CODE_EXT='" + perp + "' AND");
			sb.append(" PARENT_WTPART_APPLY_COLOR='" + pcolor + "' AND");
			sb.append(" CHILD_ERP_CODE_EXT='" + cerp + "' AND");
			sb.append(" CHILD_WTPART_APPLY_COLOR='" + ccolor + "' AND");
			sb.append(" ECN_NUMBER='" + number + "'");

			// 값이 있으면 안되
			rs = st.executeQuery(sb.toString());
			if (rs.next()) {
//			if (st.execute(sb.toString())) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCPManager.freeConnection(con, st, rs);
		}
		return false;
	}

	public String convertVersion(ECO eco, WTPart part) throws Exception {
		String preFix = eco.getDevType();
		String version = "";
		DecimalFormat df = new DecimalFormat("000");
		version = preFix + df.format(rtnVersion(part));
		return version;
	}

	private int rtnVersion(WTPart part) throws Exception {

		WTPartMaster m = part.getMaster();
		if (m.getSeries().toString().equals("wt.series.HarvardSeries")) {
			String v = part.getVersionIdentifier().getSeries().getValue();
			if ("A".equals(v)) {
				return 1;
			} else if ("B".equals(v)) {
				return 2;
			} else if ("C".equals(v)) {
				return 3;
			} else if ("D".equals(v)) {
				return 4;
			} else if ("E".equals(v)) {
				return 5;
			} else if ("F".equals(v)) {
				return 6;
			} else if ("G".equals(v)) {
				return 7;
			} else if ("H".equals(v)) {
				return 8;
			} else if ("I".equals(v)) {
				return 9;
			} else if ("J".equals(v)) {
				return 10;
			} else if ("K".equals(v)) {
				return 11;
			} else if ("L".equals(v)) {
				return 12;
			} else if ("M".equals(v)) {
				return 13;
			} else if ("N".equals(v)) {
				return 14;
			} else if ("O".equals(v)) {
				return 15;
			} else if ("P".equals(v)) {
				return 16;
			} else if ("Q".equals(v)) {
				return 17;
			} else if ("S".equals(v)) {
				return 18;
			} else if ("T".equals(v)) {
				return 19;
			} else if ("U".equals(v)) {
				return 20;
			} else if ("V".equals(v)) {
				return 21;
			} else if ("W".equals(v)) {
				return 22;
			} else if ("X".equals(v)) {
				return 23;
			} else if ("Y".equals(v)) {
				return 24;
			} else if ("Z".equals(v)) {
				return 25;
			}
		} else {
			return Integer.parseInt(part.getVersionIdentifier().getSeries().getValue());
		}
		return 0;
	}

	public ArrayList<Map<String, Object>> categoryL(Map<String, Object> params) throws Exception {
		ArrayList<Map<String, Object>> list = new ArrayList<>();

		return list;

	}

	public ArrayList<Map<String, Object>> categoryM(Map<String, Object> params) {
		ArrayList<Map<String, Object>> list = new ArrayList<>();

		return list;
	}

	public ArrayList<Map<String, Object>> categoryS(Map<String, Object> params) {
		ArrayList<Map<String, Object>> list = new ArrayList<>();

		return list;
	}
}