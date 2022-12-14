package platform.department.service;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import platform.department.entity.Department;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardDepartmentService extends StandardManager implements DepartmentService {

	public static StandardDepartmentService newStandardDepartmentService() throws WTException {
		StandardDepartmentService instance = new StandardDepartmentService();
		instance.initialize();
		return instance;
	}

	@Override
	public Department make() throws WTException {
		Transaction trs = new Transaction();
		Department department = null;
		SessionContext prev = SessionContext.newContext();
		try {
			trs.start();
			SessionHelper.manager.setAdministrator();
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Department.class, true);
			SearchCondition sc = new SearchCondition(Department.class, Department.CODE, "=", "0");
			query.appendWhere(sc, new int[] { idx });
			QueryResult result = PersistenceHelper.manager.find(query);
			if (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				department = (Department) obj[0];
			}

			if (department == null) {
				department = Department.newDepartment();
				department.setCode("0");
				department.setName("지정안됨");
				department.setSort(0);
				department.setParent(null);
				department.setUses(true);
				PersistenceHelper.manager.save(department);
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
		return department;
	}

	@Override
	public void loadFromDepartmentExcel(String path) throws Exception {
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

				String code = row.getCell(0).getStringCellValue().replaceAll("'", "");
				String name = row.getCell(1).getStringCellValue().replaceAll("'", "");
				String pcode = row.getCell(2).getStringCellValue().replaceAll("'", "");
				double seq = row.getCell(3).getNumericCellValue();
				String uses = row.getCell(4).getStringCellValue().replaceAll("'", "");

				Department dept = DepartmentHelper.manager.getDepartment(code);
				Department pdept = DepartmentHelper.manager.getDepartment(pcode);
				Department root = DepartmentHelper.manager.getRoot();
				if (dept == null) {
					dept = Department.newDepartment();
					dept.setCode(code);
					dept.setName(name);
					dept.setSort((int) seq);
					if (pdept != null) {
						dept.setParent(pdept);
					} else {
						dept.setParent(root);
					}
					if ("N".equals(uses)) {
						dept.setUses(false);
					} else {
						dept.setUses(true);
					}

					WTPrincipal prin = SessionHelper.manager.getPrincipal();
					dept.setOwnership(Ownership.newOwnership(prin));
					PersistenceHelper.manager.save(dept);
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
	public void setParent(String path) throws Exception {
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

			int _column = 1;
			// 다시 한번 정리??
			for (Row row : sheet) {
				// java 의 시작은 거의다 0부터 0번째 행일 경우 패스
				if (_column < 2) {
					_column++;
					continue;
				}

				String code = row.getCell(0).getStringCellValue().replaceAll("'", "");
				String pcode = row.getCell(2).getStringCellValue().replaceAll("'", "");

				System.out.println("code=" + code);

				Department dept = DepartmentHelper.manager.getDepartment(code);
				if (dept != null) {
					Department pdept = DepartmentHelper.manager.getDepartment(pcode);
					dept.setParent(pdept);
					PersistenceHelper.manager.modify(dept);
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
}