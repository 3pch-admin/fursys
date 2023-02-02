package platform.dist.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import platform.code.service.BaseCodeHelper;
import platform.dist.entity.Distributor;
import platform.dist.entity.DistributorUser;
import platform.util.CommonUtils;
import platform.util.db.DBCPManager;
import platform.util.service.CPCHistoryHelper;
import wt.fc.PersistenceHelper;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardDistributorService extends StandardManager implements DistributorService {
	
	private String cpcHost = "distlocal";
	
	public static StandardDistributorService newStandardDistributorService() throws WTException {
		StandardDistributorService instance = new StandardDistributorService();
		instance.initialize();
		return instance;
	}

	@Override
	public Distributor create(Map<String, Object> params) throws Exception {
		Distributor distributor = null;

		String name = (String) params.get("names"); // 업체명
		String plant = (String) params.get("factory"); // 사내
		String description = (String) params.get("description");
		String enable = (String) params.get("enable");
		String type = (String) params.get("type");

		Transaction trs = new Transaction();
		try {
			trs.start();

			distributor = Distributor.newDistributor();

			if ("IN".equals(type)) {
				distributor.setName(BaseCodeHelper.manager.getNameByCodeTypeAndCode("FACTORY_CODE", plant));
				distributor.setNumber(plant); // 사내코드
			} else if ("OUT".equals(type)) {
				distributor.setName(name);
				distributor.setNumber(DistributorHelper.manager.getNextNumber()); // 업체코드
			}

			distributor.setType(type);
			distributor.setDescription(description);
			distributor.setEnable(Boolean.parseBoolean(enable));
			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			distributor.setOwnership(Ownership.newOwnership(prin));

			distributor = (Distributor) PersistenceHelper.manager.save(distributor);
			
			sendDistributor(distributor); // 배포처
//			_send(distributor); // 로그인 유저
//			__send(distributor); // 배포사용자

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
		return distributor;
	}

	@Override
	public Distributor modify(Map<String, Object> params) throws Exception {
		Distributor distributor = null;
		Transaction trs = new Transaction();
		String oid = (String) params.get("oid");
		String name = (String) params.get("names"); // 업체명
		String plant = (String) params.get("fatory");
		String description = (String) params.get("description");
		String enable = (String) params.get("enable");
		String type = (String) params.get("type");

		try {
			trs.start();

			distributor = (Distributor) CommonUtils.persistable(oid);

			if ("IN".equals(type)) {
				distributor.setName(
						BaseCodeHelper.manager.getNameByCodeTypeAndCode("FACTORY_CODE", distributor.getName()));
				distributor.setNumber(plant); // 사내코드
			} else if ("OUT".equals(type)) {
				distributor.setName(name);
				distributor.setNumber(DistributorHelper.manager.getNextNumber()); // 업체코드
			}

			distributor.setType(type);
			distributor.setDescription(description);
			distributor.setEnable(Boolean.parseBoolean(enable));
			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			distributor.setOwnership(Ownership.newOwnership(prin));

			PersistenceHelper.manager.modify(distributor);

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
		return distributor;
	}

	@Override
	public Distributor delete(String oid) throws Exception {
		Distributor distributor = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			distributor = (Distributor) CommonUtils.persistable(oid);
			if (distributor.getEnable() == true) {
				distributor.setEnable(false);
			} else {
				distributor.setEnable(true);
			}
			distributor = (Distributor) PersistenceHelper.manager.modify(distributor);

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
		return distributor;
	}

	@Override
	public DistributorUser userCreate(Map<String, Object> params) throws Exception {
		DistributorUser diUser = null;
		String name = (String) params.get("distributor"); // 업체명
		String plant = (String) params.get("factory");
		String description = (String) params.get("description");
		String enable = (String) params.get("enable");
		String type = (String) params.get("type");

		// 사외 일경우
		String email = (String) params.get("email");
		String userName = (String) params.get("userName");
		String distributorOid = (String) params.get("distributorOid");
		
		// 사내
		String umail = (String) params.get("umail");
		String userId = (String) params.get("userId");
		String username = (String) params.get("username");

		System.out.println();
		
		Transaction trs = new Transaction();
		try {
			trs.start();
			Distributor distributorObj = null;
			diUser = DistributorUser.newDistributorUser();

			if ("IN".equals(type)) {
				diUser.setName(plant);
				diUser.setEmail(umail);
				diUser.setUserId(userId);
				diUser.setUserName(username);
			} else if ("OUT".equals(type)) {
				diUser.setName(name);
				diUser.setUserId(email);
				diUser.setEmail(email);
				diUser.setUserName(userName);
			}
			
			if( distributorOid != null && distributorOid.length() > 0 ) {
				distributorObj = (Distributor)CommonUtils.persistable(distributorOid);
			}else {
				distributorObj = DistributorHelper.manager.getDistributor(plant) ;
			}
			diUser.setDistributor(distributorObj);
			diUser.setType(distributorObj.getType());
			diUser.setNumber(distributorObj.getNumber());
			diUser.setName(distributorObj.getName());
			
			diUser.setDescription(description);
			diUser.setEnable(Boolean.parseBoolean(enable));
			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			diUser.setOwnership(Ownership.newOwnership(prin));

			PersistenceHelper.manager.save(diUser);

			sendDistributor(distributorObj);	//배포처
			sendDistributorUser(diUser); // 로그인 유저
//			__send(distributor); // 배포사용자

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
		return diUser;
	}

	@Override
	public DistributorUser userModify(Map<String, Object> params) throws Exception {
		DistributorUser distributor = null;
		Transaction trs = new Transaction();
		String oid = (String) params.get("oid");
		String name = (String) params.get("names"); // 업체명
		String plant = (String) params.get("plant");
		String description = (String) params.get("description");
		String enable = (String) params.get("enable");
		String type = (String) params.get("type");

		// 사외 일경우
		String email = (String) params.get("email");
		String userName = (String) params.get("userName");

		// 사내
		String umail = (String) params.get("umail");
		String userId = (String) params.get("userId");
		String username = (String) params.get("username");

		try {
			trs.start();

			distributor = (DistributorUser) CommonUtils.persistable(oid);

			if ("IN".equals(type)) {
				distributor.setName(plant);
				distributor.setEmail(umail);
				distributor.setUserId(userId);
				distributor.setUserName(username);
			} else if ("OUT".equals(type)) {
				distributor.setName(name);
				distributor.setUserId(email);
				distributor.setEmail(email);
				distributor.setUserName(userName);
			}

			distributor.setType(type);
			distributor.setNumber(DistributorHelper.manager.getNextNumber());
			distributor.setDescription(description);
			distributor.setEnable(Boolean.parseBoolean(enable));
			WTPrincipal prin = SessionHelper.manager.getPrincipal();
			distributor.setOwnership(Ownership.newOwnership(prin));

			PersistenceHelper.manager.modify(distributor);

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
		return distributor;
	}

	@Override
	public DistributorUser userDelete(String oid) throws Exception {
		DistributorUser distributor = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			distributor = (DistributorUser) CommonUtils.persistable(oid);
			distributor.setEnable(false);
			distributor = (DistributorUser) PersistenceHelper.manager.modify(distributor);

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
		return distributor;
	}
	
	@Override
	public int sendDistributor(Map<String, Object> params) throws Exception {
		String oid = (String)params.get("oid");
		
		Distributor di = (Distributor)CommonUtils.persistable(oid);
		
		return sendDistributor(di);
	}
	
	
	@Override
	public int sendDistributor(Distributor di) throws Exception {
		int reValue = 0;
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
			
			boolean duChk = DistributorHelper.service.duplicateDistributor(di);
			con = DBCPManager.getConnection(cpcHost); // 운영 dist..
			st = con.createStatement();
			
			if( !duChk) {
				
				/// 배포처
				StringBuffer sql = new StringBuffer();
				sql.append("INSERT INTO SITE_INFO (");
				sql.append("SITE_TYPE_CD, SITE_CD, SITE_NM, USE_YN, IS_FROM_ERP, IS_ADM_SITE");
				sql.append(", CREATE_DATE, UPDATE_DATE, CREATE_USER_ID, CREATE_USER_NM, UPDATE_USER_ID, UPDATE_USER_NM");
				sql.append(") VALUES(");
	
				// SITE_TYPE_CD
				if (di.getType().equals("OUT")) {
					sql.append("'O', ");
				} else {
					sql.append("'I', ");
				}
	
				// SITE_CD
	//			if (distributor.getType().equals("OUT")) {
	//				sql.append("'" + distributor.getNumber() + "', ");
	//			} else {
	//				sql.append("'" + distributor.getNumber() + "', ");
	//			}
				sql.append("'" + di.getNumber() + "', ");
	
				// SITE_NM
				sql.append("'" + di.getName() + "', ");
	
				// USE_YN
				if (di.getEnable()) {
					sql.append("'Y', ");
				} else {
					sql.append("'N', ");
				}
				// IS_FROM_ERP
				sql.append("'Y', "); // ??
	
				// IS_ADM_SITE
				sql.append("'N', ");
	
				// CREATE_DATE
				sql.append("SYSDATE, ");
	
				// UPDATE_DATE
				sql.append("SYSDATE, ");
	
				// CREATE_USER_ID
				sql.append("'" + user.getName() + "', ");
	
				// CREATE_USER_NM
				sql.append("'" + user.getFullName() + "', ");
	
				// UPDATE_USER_ID
				sql.append("'" + user.getName() + "', ");
	
				// UPDATE_USER_NM
				sql.append("'" + user.getFullName() + "'");
				sql.append(")");
				st.execute(sql.toString());
	
				CPCHistoryHelper.createCPCHistory(CommonUtils.oid(di), CPCHistoryHelper.company, sql.toString());
			}else {
				//중복임..update
				
				String isEnable = "N";
				
				if( di.getEnable()) {
					isEnable = "Y";
				}
				
				/// 배포처
				StringBuffer sql = new StringBuffer();
				sql.append(" UPDATE SITE_INFO ");
				sql.append(" SET " );
				sql.append(" USE_YN='" + isEnable + "', ");
				sql.append(" UPDATE_DATE=SYSDATE , ");
				sql.append(" UPDATE_USER_ID='" + user.getName() + "', ");
				sql.append(" UPDATE_USER_NM='" + user.getFullName() + "' ");
				sql.append(" WHERE SITE_CD = '" + di.getNumber() + "' ");
	
				st.execute(sql.toString());
	
				CPCHistoryHelper.createCPCHistory(CommonUtils.oid(di), CPCHistoryHelper.company, sql.toString());
				
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
		return reValue;
	}

	@Override
	public void _send(Distributor distributor) throws Exception {
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			con = DBCPManager.getConnection(cpcHost); // 운영 dist..
			st = con.createStatement();

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

			/// 배포처
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO USER_INFO (");
			sql.append("SITE_CD, USER_INFO_ID, USER_NAME, EMAIL, PASSWORD, USE_YN, ");
			sql.append(
					"IS_FROM_ERP, CREATE_DATE, UPDATE_DATE, CREATE_USER_ID, CREATE_USER_NM, UPDATE_USER_ID, UPDATE_USER_NM");
			sql.append(") VALUES(");
			//SITE_CD
			sql.append("'" + distributor.getNumber() + "', ");
			//USER_INFO_ID
			sql.append("'" + distributor.getUserId() + "', ");
			//USER_NAME
			sql.append("'" + distributor.getUserName() + "', ");
			//EMAIL
			sql.append("'" + distributor.getEmail() + "', ");
			//PASSWORD
			sql.append("'', "); // password
			//USE_YN
			sql.append("'Y', ");
			//IS_FROM_ERP
			sql.append("'N', ");
			//CREATE_DATE
			sql.append("SYSDATE, ");
			//UPDATE_DATE
			sql.append("SYSDATE, ");
			//CREATE_USER_ID
			sql.append("'" + user.getName() + "', ");
			//CREATE_USER_NM
			sql.append("'" + user.getFullName() + "', ");
			//UPDATE_USER_ID
			sql.append("'" + user.getName() + "', ");
			//UPDATE_USER_NM
			sql.append("'" + user.getFullName() + "')");

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
	public void __send(Distributor distributor) throws Exception {
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			con = DBCPManager.getConnection(cpcHost); // 운영 dist..
			st = con.createStatement();

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

			/// 배포처
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO SITE_USER_INFO (");
			sql.append("OBJ_ID, SITE_CD, USER_INFO_ID, USE_YN, ");
			sql.append("CREATE_DATE, UPDATE_DATE, CREATE_USER_ID, CREATE_USER_NM, UPDATE_USER_ID, UPDATE_USER_NM");
			sql.append(") VALUES(");

			sql.append("'" + UUID.randomUUID().toString().replace("-", "") + "', ");
			if (distributor.getType().equals("IN")) {
				sql.append("'" + distributor.getName() + "', ");
			} else {
				sql.append("'" + distributor.getNumber() + "', ");
			}
			sql.append("'" + distributor.getUserId() + "', ");
			sql.append("'Y', ");
			sql.append("SYSDATE, ");
			sql.append("SYSDATE, ");
			sql.append("'" + user.getName() + "', ");
			sql.append("'" + user.getFullName() + "', ");
			sql.append("'" + user.getName() + "', ");
			sql.append("'" + user.getFullName() + "')");

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
	public int sendDistributorUser(Map<String, Object> params) throws Exception {
		String oid = (String)params.get("oid");
		
		DistributorUser di = (DistributorUser)CommonUtils.persistable(oid);
		
		return sendDistributorUser(di);
	}
	
	@Override
	public int sendDistributorUser(DistributorUser distUser) throws Exception {
		
		int reValue = 0;
		
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			con = DBCPManager.getConnection(cpcHost); // 운영 dist..
			st = con.createStatement();

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

			boolean duChk = DistributorHelper.service.duplicateDistributorUser(distUser);
			
			if( !duChk) {
			
				sendDistributorUser2(distUser);
				
				String obj_id = UUID.randomUUID().toString().replace("-", "");
				
				/// 배포처
				StringBuffer sql = new StringBuffer();
				sql.append("INSERT INTO SITE_USER_INFO (");
				sql.append("OBJ_ID, SITE_CD, USER_INFO_ID, USE_YN, ");
				sql.append("CREATE_DATE, UPDATE_DATE, CREATE_USER_ID, CREATE_USER_NM, UPDATE_USER_ID, UPDATE_USER_NM");
				sql.append(") VALUES(");
	
				sql.append("'" + obj_id + "', ");
				if (distUser.getType().equals("IN")) {
					sql.append("'" + distUser.getName() + "', ");
				} else {
					sql.append("'" + distUser.getNumber() + "', ");
				}
				sql.append("'" + distUser.getUserId() + "', ");
				sql.append("'Y', ");
				sql.append("SYSDATE, ");
				sql.append("SYSDATE, ");
				sql.append("'" + user.getName() + "', ");
				sql.append("'" + user.getFullName() + "', ");
				sql.append("'" + user.getName() + "', ");
				sql.append("'" + user.getFullName() + "')");
	
				st.execute(sql.toString());
				
				distUser.setObj_id(obj_id);
				PersistenceHelper.manager.modify(distUser);
				
				CPCHistoryHelper.createCPCHistory(CommonUtils.oid(distUser), CPCHistoryHelper.company, sql.toString());
				
			}else {
				//중복
				
				String isEnable = "N";
				
				if( distUser.getEnable()) {
					isEnable = "Y";
				}
				
				/// 배포처
				StringBuffer sql = new StringBuffer();
				sql.append(" UPDATE SITE_INFO ");
				sql.append(" SET " );
				sql.append(" USE_YN='" + isEnable + "', ");
				sql.append(" UPDATE_DATE=SYSDATE , ");
				sql.append(" UPDATE_USER_ID='" + user.getName() + "', ");
				sql.append(" UPDATE_USER_NM='" + user.getFullName() + "' ");
				sql.append(" WHERE SITE_CD = '" + distUser.getObj_id() + "' ");
				
				CPCHistoryHelper.createCPCHistory(CommonUtils.oid(distUser), CPCHistoryHelper.company, sql.toString());
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
		return reValue;
	}
	
	@Override
	public boolean duplicateDistributor(Distributor di) throws Exception {
		
		boolean reValue = false;
		
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction trs = new Transaction();
		try {
			trs.start();
			
			con = DBCPManager.getConnection(cpcHost); // 운영 dist..
			st = con.createStatement();

			String SITE_CD = "O";
			
			if( "IN".equals(di.getType())) {
				 SITE_CD = "I";
			}
			
			/// 배포처
			StringBuffer sql = new StringBuffer();
			
			sql.append(" SELECT SITE_CD FROM SITE_INFO ");
			sql.append(" WHERE SITE_TYPE_CD = '" + SITE_CD + "' ");			//I: 사내, O: 사외
			sql.append(" AND SITE_CD = '" + di.getNumber() + "' ");					//사외 : 배포처(업체) 코드자동생성 (SU-YYYY-MM-XXXXX), 사내 : 사업장코드
			//sql.append(" AND USE_YN=''; ");						
			
			System.out.println("#### sql.toString()=="+sql.toString());

			st.execute(sql.toString());
			
			rs = st.getResultSet();
			int rowC = 0;
			while(rs.next()) {
				rowC++;
			}
			
			
			System.out.println("####rs.getRow()=="+rs.getRow());
			System.out.println("####rs.rowC()=="+rowC);
			if( rowC> 0 ) {
				reValue = true;
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
		return reValue;
	}
	
	
	@Override
	public boolean duplicateDistributorUser(DistributorUser distUser) throws Exception {
		boolean reValue = false;
		
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			con = DBCPManager.getConnection(cpcHost); // 운영 dist..
			st = con.createStatement();

			StringBuffer sql = new StringBuffer();
			
			sql.append(" SELECT * FROM SITE_USER_INFO ");
			sql.append(" WHERE OBJ_ID = '" + distUser.getObj_id() + "' ");			
			//sql.append(" AND USE_YN=''; ");						
			
			System.out.println("####DistributorUser sql.toString()=="+sql.toString());

			st.execute(sql.toString());
			
			rs = st.getResultSet();
			
			int rowC = 0;
			while(rs.next()) {
				rowC++;
			}
			
			
			System.out.println("####rs.getRow()=="+rs.getRow());
			System.out.println("####rs.rowC()=="+rowC);
			if( rowC> 0 ) {
				reValue = true;
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
		
		return reValue;
	}
	
	@Override
	public boolean duplicateDistributorUser2(DistributorUser distUser) throws Exception {
		boolean reValue = false;
		
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			con = DBCPManager.getConnection(cpcHost); // 운영 dist..
			st = con.createStatement();

			StringBuffer sql = new StringBuffer();
			
			sql.append(" SELECT USER_INFO_ID FROM USER_INFO ");
			sql.append(" WHERE USER_INFO_ID = '" + distUser.getUserId() + "' ");			
			//sql.append(" AND USE_YN=''; ");						
			
			System.out.println("####DistributorUser USER_INFO sql.toString()=="+sql.toString());

			st.execute(sql.toString());
			
			rs = st.getResultSet();
			
			System.out.println("####DistributorUser USER_INFO rs.getRow()=="+rs.getRow());

			int rowCount = 0;
			while(rs.next()) {
				rowCount++;
			}
			
			if( rowCount > 0 ) {
				reValue = true;
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
		
		return reValue;
	}
	
	
	@Override
	public int sendDistributorUser2(DistributorUser diUser) throws Exception {
		int reValue = 0;
		
		Statement st = null;
		Connection con = null;
		ResultSet rs = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			con = DBCPManager.getConnection(cpcHost); // 운영 dist..
			st = con.createStatement();

			WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

			if(duplicateDistributorUser2(diUser)) {
				System.out.println("### duplicateDistributorUser2(diUser)==TRUE");
			}else {
				System.out.println("### duplicateDistributorUser2(diUser)==FALSE");
				
				
				/// 배포처
				StringBuffer sql = new StringBuffer();
				sql.append("INSERT INTO USER_INFO (");
				sql.append("SITE_CD, USER_INFO_ID, USER_NAME, EMAIL, PASSWORD, USE_YN, ");
				sql.append(
						"IS_FROM_ERP, CREATE_DATE, UPDATE_DATE, CREATE_USER_ID, CREATE_USER_NM, UPDATE_USER_ID, UPDATE_USER_NM");
				sql.append(") VALUES(");
				//SITE_CD
				sql.append("'" + diUser.getNumber() + "', ");
				//USER_INFO_ID
				sql.append("'" + diUser.getUserId() + "', ");
				//USER_NAME
				sql.append("'" + diUser.getUserName() + "', ");
				//EMAIL
				sql.append("'" + diUser.getEmail() + "', ");
				//PASSWORD
				sql.append("'', "); // password
				//USE_YN
				sql.append("'Y', ");
				//IS_FROM_ERP
				sql.append("'N', ");
				//CREATE_DATE
				sql.append("SYSDATE, ");
				//UPDATE_DATE
				sql.append("SYSDATE, ");
				//CREATE_USER_ID
				sql.append("'" + user.getName() + "', ");
				//CREATE_USER_NM
				sql.append("'" + user.getFullName() + "', ");
				//UPDATE_USER_ID
				sql.append("'" + user.getName() + "', ");
				//UPDATE_USER_NM
				sql.append("'" + user.getFullName() + "')");
	
				st.execute(sql.toString());
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
		return reValue;
	}
	
}
