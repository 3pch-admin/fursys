package platform.code.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import platform.code.entity.BaseCode;
import platform.code.entity.BaseCodeDTO;
import platform.code.entity.BaseCodeType;
import platform.util.CommonUtils;
import platform.util.StringUtils;
import wt.fc.PersistenceHelper;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;

public class StandardBaseCodeService extends StandardManager implements BaseCodeService {

	public static StandardBaseCodeService newStandardBaseCodeService() throws WTException {
		StandardBaseCodeService instance = new StandardBaseCodeService();
		instance.initialize();
		return instance;
	}

	@Override
	public void initialize(String path) throws WTException {
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

				// 1행부터 데이터 시작
				String codeType = row.getCell(0).getStringCellValue();
				String code = row.getCell(1).getStringCellValue();
				String name = row.getCell(2).getStringCellValue();
				String parentType = row.getCell(4).getStringCellValue();
				String parentCode = row.getCell(5).getStringCellValue();
				String enable = row.getCell(6).getStringCellValue();
				String desc = row.getCell(8).getStringCellValue();

				if ("N".equalsIgnoreCase(enable)) {
					BaseCode baseCode = BaseCode.newBaseCode();
					baseCode.setCode(code);
					baseCode.setCodeType(BaseCodeType.toBaseCodeType(codeType.toUpperCase()));
					baseCode.setName(name);
					baseCode.setDescription(desc);

					BaseCode parent = null;
					if (parentCode != null && parentType != null) {
						parent = BaseCodeHelper.manager.getBaseCodeByCodeTypeAndCode(parentType, parentCode);
						baseCode.setParent(parent);
					}

					int sort = BaseCodeHelper.manager.getSort(codeType);
					baseCode.setSort(sort);
					PersistenceHelper.manager.save(baseCode);
				}
			}

			System.out.println("데이터 로드 완료..");
			trs.commit();
			trs = null;
		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
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
	public void save(HashMap<String, List<BaseCodeDTO>> paramsMap) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

			List<BaseCodeDTO> addRows = paramsMap.get("addRows");
			for (BaseCodeDTO dto : addRows) {
				System.out.println("==" + dto.getCodeType());
				BaseCode code = BaseCode.newBaseCode();
				code.setCode(dto.getCode());
				code.setName(dto.getName());
				code.setDescription(dto.getDescription());
				code.setCodeType(BaseCodeType.toBaseCodeType(dto.getCodeType()));
				code.setEnable(dto.isEnable());
				int sort = BaseCodeHelper.manager.getSort(dto.getCodeType());
				code.setSort(sort);
				if (StringUtils.isNotNull(dto.getPoid())) {
					BaseCode parent = (BaseCode) CommonUtils.persistable(dto.getPoid());
					code.setParent(parent);
				}
				PersistenceHelper.manager.modify(code);
			}

			List<BaseCodeDTO> editRows = paramsMap.get("editRows");
			for (BaseCodeDTO dto : editRows) {
				BaseCode code = (BaseCode) CommonUtils.persistable(dto.getOid());
				code.setCode(dto.getCode());
				code.setName(dto.getName());
				code.setDescription(dto.getDescription());
				code.setCodeType(BaseCodeType.toBaseCodeType(dto.getCodeType()));
				code.setEnable(dto.isEnable());
				int sort = BaseCodeHelper.manager.getSort(dto.getCodeType());
				code.setSort(sort);
				if (StringUtils.isNotNull(dto.getPoid())) {
					BaseCode parent = (BaseCode) CommonUtils.persistable(dto.getPoid());
					code.setParent(parent);
				}
				PersistenceHelper.manager.modify(code);
			}

			List<BaseCodeDTO> removeRows = paramsMap.get("removeRows");
			for (BaseCodeDTO dto : removeRows) {
				BaseCode code = (BaseCode) CommonUtils.persistable(dto.getOid());
				PersistenceHelper.manager.delete(code);
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
