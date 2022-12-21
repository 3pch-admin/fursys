package platform.user.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import platform.code.entity.BaseCode;
import platform.code.service.BaseCodeHelper;
import platform.department.entity.Department;
import platform.department.service.DepartmentHelper;
import platform.user.entity.User;
import platform.user.entity.UserColumns;
import platform.user.entity.UserWTUserLink;
import platform.util.CommonUtils;
import platform.util.EventKeys;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceManagerEvent;
import wt.fc.QueryResult;
import wt.load.LoadUser;
import wt.org.OrganizationServicesMgr;
import wt.org.WTUser;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardUserService extends StandardManager implements UserService {

	public static StandardUserService newStandardUserService() throws WTException {
		StandardUserService instance = new StandardUserService();
		instance.initialize();
		return instance;
	}

	@Override
	protected synchronized void performStartupProcess() throws ManagerException {
		super.performStartupProcess();
		UserListener listener = new UserListener(StandardUserService.class.getName());
		getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(EventKeys.POST_STORE));
		getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(EventKeys.POST_MODIFY));
		try {
			// root department create
			System.out.println("User Load..!");

			Department department = DepartmentHelper.service.make();
//
			inspectUser(department);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void inspectUser(Department department) {
		SessionContext prev = SessionContext.newContext();
		Transaction trs = new Transaction();
		try {
			trs.start();

			SessionHelper.manager.setAdministrator();

			QuerySpec spec = new QuerySpec();
			spec.appendClassList(User.class, true);
			QueryResult navi = PersistenceHelper.manager.find(spec);
			while (navi.hasMoreElements()) {
				Object[] obj = (Object[]) navi.nextElement();
				User people = (User) obj[0];
				String id = people.getUserId();
				WTUser user = OrganizationServicesMgr.getUser(id);
				if (user == null) {
					PersistenceHelper.manager.delete(user);
				}
			}

			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(WTUser.class, true);

			SearchCondition sc = new SearchCondition(WTUser.class, WTUser.DISABLED, SearchCondition.IS_FALSE);
			query.appendWhere(sc, new int[] { idx });
			query.appendAnd();

			sc = new SearchCondition(WTUser.class, WTUser.REPAIR_NEEDED, SearchCondition.IS_FALSE);
			query.appendWhere(sc, new int[] { idx });

			ClassAttribute ca = new ClassAttribute(WTUser.class, WTUser.FULL_NAME);
			OrderBy orderBy = new OrderBy(ca, false);
			query.appendOrderBy(orderBy, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);

			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				WTUser wtuser = (WTUser) obj[0];

				QueryResult qr = PersistenceHelper.manager.navigate(wtuser, "user", UserWTUserLink.class);
				User user = null;
				if (!qr.hasMoreElements()) {
					user = User.newUser();
					// set foreignKey
					user.setDepartment(department);
					user.setWtUser(wtuser);
					user.setUserId(wtuser.getName());
					user.setUserName(wtuser.getFullName());
					user.setEmail(wtuser.getEMail() != null ? wtuser.getEMail() : "");
					user = (User) PersistenceHelper.manager.save(user);
				}
			}

			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
		} finally {
			if (trs != null)
				trs.rollback();
			SessionContext.setContext(prev);
		}
	}

	@Override
	public void postSave(WTUser wtUser) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			QueryResult qr = PersistenceHelper.manager.navigate(wtUser, "user", UserWTUserLink.class);
			if (qr.hasMoreElements()) {
				User user = (User) qr.nextElement();
				if (user == null) {
					user = User.newUser();
					user.setWtUser(wtUser);
					user.setUserId(wtUser.getName());
					user.setUserName(wtUser.getFullName());
					user.setEmail(wtUser.getEMail() != null ? wtUser.getEMail() : "");
					PersistenceHelper.manager.save(user);
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
	}

	@Override
	public void postModify(WTUser wtUser) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();
			QueryResult qr = PersistenceHelper.manager.navigate(wtUser, "user", UserWTUserLink.class);
			if (qr.hasMoreElements()) {
				User user = (User) qr.nextElement();
				user.setWtUser(wtUser);
				user.setUserId(wtUser.getName());
				user.setUserName(wtUser.getFullName());
				user.setEmail(wtUser.getEMail() != null ? wtUser.getEMail() : "");
				PersistenceHelper.manager.modify(user);
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
	}

	@Override
	public void loadFromUserExcel(String path) throws Exception {
		SessionContext prev = SessionContext.newContext();
		Transaction trs = new Transaction();
		XSSFWorkbook workbook = null;
		File excel = null;
		try {
			trs.start();

			SessionHelper.manager.setAdministrator();

			excel = new File(path);
			workbook = new XSSFWorkbook(new FileInputStream(excel));
			XSSFSheet sheet = workbook.getSheetAt(0);

			int column = 1;
			for (Row row : sheet) {
				// java 의 시작은 거의다 0부터 0번째 행일 경우 패스
				if (column < 2) {
					column++;
					continue;
				}

				String id = row.getCell(0).getStringCellValue().replaceAll("'", "");
				String webServerID = id;
				String fullName = row.getCell(1).getStringCellValue().replaceAll("'", "");
				String password = id;
				String locale = "KO";
				String email = row.getCell(5).getStringCellValue().replaceAll("'", "");
				String org = "fusys";
				String ignore = "x";

				String code = row.getCell(2).getStringCellValue().replaceAll("'", "");
				String uses = row.getCell(6).getStringCellValue().replaceAll("'", "");
				String duty = row.getCell(4).getStringCellValue().replaceAll("'", "");

				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				hash.put("newUser", id);
				hash.put("webServerID", webServerID);
				hash.put("fullName", fullName);
				hash.put("password", password);
				hash.put("Locale", locale);
				hash.put("Email", email);
				hash.put("Organization", org);
				hash.put("ignore", ignore);

//				WTUser user = null;
//				QuerySpec query = new QuerySpec();
//				int idx = query.appendClassList(WTUser.class, true);
//
//				SearchCondition sc = new SearchCondition(WTUser.class, WTUser.DISABLED, SearchCondition.IS_FALSE);
//				query.appendWhere(sc, new int[] { idx });
//				query.appendAnd();
//
//				sc = new SearchCondition(WTUser.class, WTUser.REPAIR_NEEDED, SearchCondition.IS_FALSE);
//				query.appendWhere(sc, new int[] { idx });
//				query.appendAnd();
//
//				sc = new SearchCondition(WTUser.class, WTUser.NAME, "=", id);
//				query.appendWhere(sc, new int[] { idx });
//
//				ClassAttribute ca = new ClassAttribute(WTUser.class, WTUser.FULL_NAME);
//				OrderBy orderBy = new OrderBy(ca, false);
//				query.appendOrderBy(orderBy, new int[] { idx });
//				QueryResult qr = PersistenceHelper.manager.find(query);
//				if (qr.hasMoreElements()) {
//					Object[] obj = (Object[]) qr.nextElement();
//					user = (WTUser) obj[0];
//				}
				WTUser user = OrganizationServicesMgr.getUser(id);
				if (user == null) {
					boolean success = LoadUser.createUser(hash, new Hashtable(), new Vector());
					if (success) {
						user = OrganizationServicesMgr.getUser(id);
						QueryResult result = PersistenceHelper.manager.navigate(user, "user", UserWTUserLink.class);
						if (result.hasMoreElements()) {
							User u = (User) result.nextElement();
							u.setDepartment(DepartmentHelper.manager.getDepartment(code));
							u.setDuty(duty);
							u.setEmail(email);
							if ("N".equals(uses)) {
								u.setUses(false);
							} else {
								u.setUses(true);
							}
							PersistenceHelper.manager.modify(u);
						}
					}
				} else {

					user.setName(id);
					user.setFullName(fullName);
					user.setEMail(email);
					if ("N".equals(uses)) {
						user.setDisabled(true);
					} else {
						user.setDisabled(false);
						user.setRepairNeeded(false);
					}
					user = (WTUser) PersistenceHelper.manager.refresh(user);
					PersistenceHelper.manager.modify(user);

					QueryResult result = PersistenceHelper.manager.navigate(user, "user", UserWTUserLink.class);
					if (result.hasMoreElements()) {
						User u = (User) result.nextElement();
						u.setDepartment(DepartmentHelper.manager.getDepartment(code));
						u.setDuty(duty);
						u.setEmail(email);
						if ("N".equals(uses)) {
							u.setUses(false);
						} else {
							u.setUses(true);
						}
						PersistenceHelper.manager.modify(u);
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
			if (workbook != null)
				try {
					workbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			if (trs != null)
				trs.rollback();
			SessionContext.setContext(prev);
		}
	}

	@Override
	public void modify(HashMap<String, List<UserColumns>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			List<UserColumns> editRows = paramsMap.get("editRows");
			for (UserColumns dto : editRows) {
				User user = (User) CommonUtils.persistable(dto.getOid());
				BaseCode brand = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode("BRAND", dto.getBrand());
				user.setBrand(brand);

				Department dept = DepartmentHelper.manager.getDepartment(dto.getDepartment());
				user.setDepartment(dept);

				PersistenceHelper.manager.modify(user);
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
	}
}
