package platform.dist.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ptc.tml.utils.IBAUtils;
import com.ptc.wvs.server.util.PublishUtils;

import platform.dist.entity.Dist;
import platform.dist.entity.DistColumns;
import platform.dist.entity.DistDistributorUserLink;
import platform.dist.entity.DistPartColumns;
import platform.dist.entity.DistPartDistributorUserLink;
import platform.dist.entity.DistPartLink;
import platform.dist.entity.Distributor;
import platform.dist.entity.DistributorUser;
import platform.dist.entity.DistributorUserColumns;
import platform.dist.vo.DistFileVO;
import platform.dist.vo.DistUserVO;
import platform.dist.vo.TransferDetailVO;
import platform.dist.vo.TransferFileVO;
import platform.epm.service.EpmHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.PageUtils;
import platform.util.StringUtils;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.folder.Cabinet;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.folder.SubFolderLink;
import wt.part.WTPart;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.representation.Representation;
import wt.services.ServiceFactory;
import wt.util.WTAttributeNameIfc;
import wt.vc.VersionControlHelper;

public class DistHelper {

	public static DistService service = ServiceFactory.getService(DistService.class);
	public static DistHelper manager = new DistHelper();

	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DistColumns> list = new ArrayList<>();

		String name = (String) params.get("dist_name");
		String cadName = (String) params.get("fileName");
		String state = (String) params.get("state");
		String startCreatedDate = (String) params.get("startCreatedDate");
		String endCreatedDate = (String) params.get("endCreatedDate");
		String distributor = (String) params.get("distributor");
		String type = (String) params.get("type");
		String material_type = (String) params.get("material_type");
		try {
			SearchCondition sc = null;
			ClassAttribute ca = null;
			QuerySpec query = new QuerySpec();
			int idx = query.appendClassList(Dist.class, true);
//			int idx_l = query.appendClassList(DistEPMLink.class, false);

			if (StringUtils.isNotNull(name)) {
				if (query.getConditionCount() > 0) {
					query.appendAnd();
				}
				ca = new ClassAttribute(Dist.class, Dist.NAME);
				ColumnExpression ce = ConstantExpression.newExpression("%" + name.toUpperCase() + "%");
				SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				sc = new SearchCondition(function, SearchCondition.LIKE, ce);
				query.appendWhere(sc, new int[] { idx });
			}

			if (StringUtils.isNotNull(type)) {
				if (query.getConditionCount() > 0) {
					query.appendAnd();
				}

				sc = new SearchCondition(Dist.class, Dist.TYPE, "=", type);
				query.appendWhere(sc, new int[] { idx });
			}

//			if (StringUtils.isNotNull(cadName)) {
//				int idx_e = query.appendClassList(EPMDocument.class, false);
//				if (query.getConditionCount() > 0) {
//					query.appendAnd();
//				}
//	
//				sc = new SearchCondition(DistEPMLink.class, "roleBObjectRef.key.id", EPMDocument.class, WTAttributeNameIfc.ID_NAME);
//				query.appendWhere(sc, new int[] { idx_l, idx_e });
//				query.appendAnd();
//	
//				ca = new ClassAttribute(EPMDocument.class, EPMDocument.CADNAME);
//				ColumnExpression ce = ConstantExpression.newExpression("%" + cadName.toUpperCase() + "%");
//				SQLFunction function = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
//				sc = new SearchCondition(function, SearchCondition.LIKE, ce);
//				query.appendWhere(sc, new int[] { idx_e });
//	
//			}

//			if (StringUtils.isNotNull(distributor)) {
//				if (query.getConditionCount() > 0) {
//					query.appendAnd();
//				}
//	
//				WTUser user = (WTUser) CommonUtils.persistable(distributor);
//				sc = new SearchCondition(DistEPMLink.class, "distributorReference.key.id", "=", user.getPersistInfo().getObjectIdentifier().getId());
//				query.appendWhere(sc, new int[] { idx_l });
//			}

			// 상태
			if (StringUtils.isNotNull(state)) {
				if (query.getConditionCount() > 0) {
					query.appendAnd();
				}
				sc = new SearchCondition(Dist.class, Dist.STATE, "=", state);
				query.appendWhere(sc, new int[] { idx });
			}

			// 작성일자
			if (StringUtils.isNotNull(startCreatedDate)) {
				if (query.getConditionCount() > 0) {
					query.appendAnd();
				}
				sc = new SearchCondition(Dist.class, WTAttributeNameIfc.CREATE_STAMP_NAME, ">=",
						DateUtils.startTimestamp(startCreatedDate));
			}
			if (StringUtils.isNotNull(endCreatedDate)) {
				if (query.getConditionCount() > 0) {
					query.appendAnd();
				}
				sc = new SearchCondition(Dist.class, WTAttributeNameIfc.CREATE_STAMP_NAME, "<=",
						DateUtils.endTimestamp(endCreatedDate));
				query.appendWhere(sc, new int[] { idx });
			}

			if (StringUtils.isNotNull(material_type)) {
				if (query.getConditionCount() > 0) {
					query.appendAnd();
				}
				sc = new SearchCondition(Dist.class, Dist.MATERIAL_TYPE, "=", material_type);
				query.appendWhere(sc, new int[] { idx });
			}

//			if (query.getConditionCount() > 0) {
//				query.appendAnd();
//			}
//			sc = new SearchCondition(Dist.class, WTAttributeNameIfc.ID_NAME, DistEPMLink.class, "roleAObjectRef.key.id");
//			query.appendWhere(sc, new int[] { idx, idx_l });

			ca = new ClassAttribute(Dist.class, Dist.NUMBER);
			OrderBy by = new OrderBy(ca, true);
			query.appendOrderBy(by, new int[] { idx });

			System.out.println("###disthelper = dist query==" + query.toString());

			PageUtils pager = new PageUtils(params, query);
			QueryResult result = pager.find();
			int total = pager.getTotal();
			while (result.hasMoreElements()) {
				Object[] obj = (Object[]) result.nextElement();
				DistColumns columns = new DistColumns((Dist) obj[0]);
				columns.setNo(total--);
				list.add(columns);
			}
			map.put("list", list);
			map.put("topListCount", pager.getTotal());
			map.put("sessionid", pager.getSessionId());
			map.put("curPage", pager.getCpage());
			map.put("pageSize", pager.getPsize());
			map.put("total", pager.getTotalSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public String getNextNumber() throws Exception {

		Calendar ca = Calendar.getInstance();
		int day = ca.get(Calendar.DATE);
		int month = ca.get(Calendar.MONTH) + 1;
		int year = ca.get(Calendar.YEAR);
		DecimalFormat df = new DecimalFormat("00");
		String number = df.format(year) + df.format(month) + df.format(day);

		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(Dist.class, true);

		SearchCondition sc = new SearchCondition(Dist.class, Dist.NUMBER, "LIKE", "DIST-" + number.toUpperCase() + "%");
		query.appendWhere(sc, new int[] { idx });

		ClassAttribute attr = new ClassAttribute(Dist.class, Dist.NUMBER);
		OrderBy orderBy = new OrderBy(attr, true);
		query.appendOrderBy(orderBy, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(query);

		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			Dist dist = (Dist) obj[0];

			String s = dist.getNumber().substring(13);

			int ss = Integer.parseInt(s) + 1;
			DecimalFormat d = new DecimalFormat("00");
			number += d.format(ss);
		} else {
			number += "01";
		}
		return "DIST-" + number;
	}

	public ArrayList<DistPartColumns> getParts(Dist dist) throws Exception {
		ArrayList<DistPartColumns> list = new ArrayList<DistPartColumns>();
		//QueryResult result = PersistenceHelper.manager.navigate(dist, "part", DistPartLink.class);
		
		QuerySpec qs = new QuerySpec();
		
		int idx = qs.appendClassList(DistPartLink.class, true);
		
		qs.appendWhere(new SearchCondition(DistPartLink.class, "roleAObjectRef.key.id", "=", CommonUtils.longValue(dist)), new int [] {idx} );
		
		QueryResult qr = PersistenceHelper.manager.find(qs);
		
		while (qr.hasMoreElements()) {
			Object[] o = (Object[]) qr.nextElement();
			DistPartLink link = (DistPartLink)o[0];
			list.add(new DistPartColumns(link));
		}
		return list;
	}
	
	public ArrayList<DistributorUserColumns> getDistributors(Dist dist) throws Exception {
		ArrayList<DistributorUserColumns> list = new ArrayList<DistributorUserColumns>();
		QueryResult result = PersistenceHelper.manager.navigate(dist, "distUser", DistDistributorUserLink.class);
		while (result.hasMoreElements()) {
			DistDistributorUserLink link = (DistDistributorUserLink) result.nextElement();
			list.add(new DistributorUserColumns(link.getDistUser()));
		}
		return list;
	}

	public ArrayList<DistPartLink> getPartLinks(Dist dist) throws Exception {
		ArrayList<DistPartLink> list = new ArrayList<DistPartLink>();

		QueryResult result = PersistenceHelper.manager.navigate(dist, "part", DistPartLink.class, false);
		while (result.hasMoreElements()) {
			DistPartLink link = (DistPartLink) result.nextElement();
			list.add(link);
		}
		return list;
	}

	public List<TransferDetailVO> details(String type, DistPartLink link, String path, Dist di, EPMDocument epm) throws Exception {

		TransferDetailVO detail = detail(link, path, epm);
		List<TransferDetailVO> details = new ArrayList<>();
		details.add(detail);

		ArrayList<DistributorUser> diUsers = DistHelper.manager.getDistributorUserLinks(di);
	
		for(DistributorUser diUser: diUsers ) {
		
			DistUserVO userVo = new DistUserVO();

			userVo.setSiteTypeCd(diUser.getType().equals("IN") ? "I" : "O");
			userVo.setSiteCd(diUser.getNumber());
			userVo.setSiteNm(diUser.getName());
			userVo.setId(diUser.getUserId());
			userVo.setUserName(diUser.getUserName());
			userVo.setEmail(diUser.getEmail());

			detail.addUsers(userVo);
		}
		return details;
	}

	private TransferDetailVO detail(DistPartLink link, String path, EPMDocument epm) throws Exception {
		Timestamp today = DateUtils.today();

		TransferDetailVO detail = new TransferDetailVO();
		
		if( epm != null) {
			detail.setPartOid(String.valueOf(epm.getPersistInfo().getObjectIdentifier().getId()));
			detail.setPartName(epm.getName());
			detail.setPartVersion(VersionControlHelper.getIterationDisplayIdentifier(epm).toString());
			detail.setPartNumber(epm.getNumber());
			detail.setReleaseDate(DateUtils.getTimeToString(today, "yyyy-MM-dd"));
	
			detail.setEpmOid(CommonUtils.oid(epm));
			detail.setErpCd(IBAUtils.getStringIBAValue(epm, "ERP_CODE"));
	
			Folder folder = FolderHelper.service.getFolder(epm.getLocation(), epm.getContainerReference());
	
			if (folder instanceof Cabinet) {
				detail.setCadFolderOid(
						"ROOT:" + epm.getContainerReference().getObject().getPersistInfo().getObjectIdentifier().getId());
			} else {
				SubFolder subfolder = (SubFolder) folder;
				long subfolderOid = subfolder.getPersistInfo().getObjectIdentifier().getId();
				QuerySpec query = new QuerySpec(SubFolderLink.class);
				query = new QuerySpec(SubFolderLink.class);
				query.setAdvancedQueryEnabled(true);
	
				SearchCondition cond = new SearchCondition(SubFolderLink.class, "roleBObjectRef.key.id",
						SearchCondition.EQUAL, subfolderOid);
				query.appendWhere(cond, new int[] { 0 });
	
				QueryResult queryResult = PersistenceHelper.manager.find(query);
	
				SubFolderLink subFold = null;
	
				while (queryResult.hasMoreElements()) {
					subFold = (SubFolderLink) queryResult.nextElement();
				}
				detail.setCadFolderOid(subFold.getPersistInfo().getObjectIdentifier().getStringValue());
			}
	
			DistFileVO fileVo = getDistFileVO(epm);
			String newPath = path + File.separator + String.valueOf(epm.getPersistInfo().getObjectIdentifier().getId());
			write(fileVo, newPath);
	
			if (fileVo.getPdfFile() != null && link.isPdf()) {
				TransferFileVO tFile = new TransferFileVO();
				tFile.setFileName(fileVo.getPdfFile().getFileName());
				tFile.setOid(String.valueOf(fileVo.getPdfFile().getPersistInfo().getObjectIdentifier().getId()));
				tFile.setFileUrl(path + File.separator + tFile.getFileName());
				detail.setPdfFile(tFile);
			}
	
			if (fileVo.getDwgFile() != null && link.isDwg()) {
				TransferFileVO tFile = new TransferFileVO();
				tFile.setFileName(fileVo.getDwgFile().getFileName());
				tFile.setOid(String.valueOf(fileVo.getDwgFile().getPersistInfo().getObjectIdentifier().getId()));
				tFile.setFileUrl(path + File.separator + tFile.getFileName());
	
				detail.setDwgFile(tFile);
			}
	
			if (fileVo.getStpFile() != null && link.isStep()) {
				TransferFileVO tFile = new TransferFileVO();
				tFile.setFileName(fileVo.getStpFile().getFileName());
				tFile.setOid(String.valueOf(fileVo.getStpFile().getPersistInfo().getObjectIdentifier().getId()));
				tFile.setFileUrl(path + File.separator + tFile.getFileName());
	
				detail.setStpFile(tFile);
			}
	
			if (fileVo.getJpg2DFile() != null) {
				TransferFileVO tFile = new TransferFileVO();
				tFile.setFileName(fileVo.getJpg2DFile().getFileName());
				tFile.setOid(String.valueOf(fileVo.getJpg2DFile().getPersistInfo().getObjectIdentifier().getId()));
				tFile.setFileUrl(path + File.separator + tFile.getFileName());
	
				detail.setJpg2DFile(tFile);
			}
	
			if (fileVo.getJpg2DSmallFile() != null) {
				TransferFileVO tFile = new TransferFileVO();
				tFile.setFileName(fileVo.getJpg2DSmallFile().getFileName());
				tFile.setOid(String.valueOf(fileVo.getJpg2DSmallFile().getPersistInfo().getObjectIdentifier().getId()));
				tFile.setFileUrl(path + File.separator + tFile.getFileName());
	
				detail.setJpg2DSmallFile(tFile);
			}
	
			if (fileVo.getJpg3DFile() != null) {
				TransferFileVO tFile = new TransferFileVO();
				tFile.setFileName(fileVo.getJpg3DFile().getFileName());
				tFile.setOid(String.valueOf(fileVo.getJpg3DFile().getPersistInfo().getObjectIdentifier().getId()));
				tFile.setFileUrl(path + File.separator + tFile.getFileName());
	
				detail.setJpg3DFile(tFile);
			}
	
			if (fileVo.getJpg3DSmallFile() != null) {
				TransferFileVO tFile = new TransferFileVO();
				tFile.setFileName(fileVo.getJpg3DSmallFile().getFileName());
				tFile.setOid(String.valueOf(fileVo.getJpg3DSmallFile().getPersistInfo().getObjectIdentifier().getId()));
				tFile.setFileUrl(path + File.separator + tFile.getFileName());
	
				detail.setJpg3DSmallFile(tFile);
			}
		}
		return detail;
	}

	public DistFileVO getDistFileVO(EPMDocument epm) throws Exception {
		DistFileVO fileVo = new DistFileVO();
		String docType = epm.getDocType().toString();
		Representation representation = PublishUtils.getRepresentation(epm);
		// 기본 dwg.. add pdf step...
		if (representation != null) {
			if ("CADDRAWING".equals(docType)) {
				QueryResult result = ContentHelper.service.getContentsByRole(representation,
						ContentRoleType.ADDITIONAL_FILES);
				while (result.hasMoreElements()) {
					ApplicationData data = (ApplicationData) result.nextElement();
					fileVo.setPdfFile(data);// ??
				}
				result.reset();
				result = ContentHelper.service.getContentsByRole(representation, ContentRoleType.SECONDARY);
				while (result.hasMoreElements()) {
					ApplicationData data = (ApplicationData) result.nextElement();
					fileVo.setDwgFile(data);
				}

				result.reset();
				result = ContentHelper.service.getContentsByRole(representation, ContentRoleType.THUMBNAIL);
				while (result.hasMoreElements()) {
					ApplicationData data = (ApplicationData) result.nextElement();
					fileVo.setJpg2DFile(data);
				}

				result.reset();
				result = ContentHelper.service.getContentsByRole(representation, ContentRoleType.THUMBNAIL_SMALL);
				while (result.hasMoreElements()) {
					ApplicationData data = (ApplicationData) result.nextElement();
					fileVo.setJpg2DSmallFile(data);
				}

			} else if ("CADASSEMBLY".equals(docType) || "CADCOMPONENT".equals(docType)) {
				QueryResult result = ContentHelper.service.getContentsByRole(representation,
						ContentRoleType.ADDITIONAL_FILES);
				while (result.hasMoreElements()) {
					ApplicationData data = (ApplicationData) result.nextElement();
					fileVo.setStpFile(data);
				}

				result.reset();
				result = ContentHelper.service.getContentsByRole(representation, ContentRoleType.THUMBNAIL);
				while (result.hasMoreElements()) {
					ApplicationData data = (ApplicationData) result.nextElement();
					fileVo.setJpg3DFile(data);
				}

				result.reset();
				result = ContentHelper.service.getContentsByRole(representation, ContentRoleType.THUMBNAIL_SMALL);
				while (result.hasMoreElements()) {
					ApplicationData data = (ApplicationData) result.nextElement();
					fileVo.setJpg3DSmallFile(data);
				}
			}
		}
		return fileVo;
	}

	public void write(DistFileVO fileVo, String path) throws Exception {
		File file = new File(path);
		file.mkdirs();

		if (fileVo.getJpg2DFile() != null) {
			String inputFilePath = path + File.separator + fileVo.getJpg2DFile().getFileName();
			ContentServerHelper.service.writeContentStream(fileVo.getJpg2DFile(), inputFilePath);
		}

		if (fileVo.getJpg2DSmallFile() != null) {
			String inputFilePath = path + File.separator + fileVo.getJpg2DSmallFile().getFileName();
			ContentServerHelper.service.writeContentStream(fileVo.getJpg2DSmallFile(), inputFilePath);
		}

		if (fileVo.getJpg3DFile() != null) {
			String inputFilePath = path + File.separator + fileVo.getJpg3DFile().getFileName();
			ContentServerHelper.service.writeContentStream(fileVo.getJpg3DFile(), inputFilePath);
		}

		if (fileVo.getJpg3DSmallFile() != null) {
			String inputFilePath = path + File.separator + fileVo.getJpg3DSmallFile().getFileName();
			ContentServerHelper.service.writeContentStream(fileVo.getJpg3DSmallFile(), inputFilePath);
		}

		if (fileVo.getDwgFile() != null) {
			String inputFilePath = path + File.separator + fileVo.getDwgFile().getFileName();
			ContentServerHelper.service.writeContentStream(fileVo.getDwgFile(), inputFilePath);
		}

		if (fileVo.getPdfFile() != null) {
			String inputFilePath = path + File.separator + fileVo.getPdfFile().getFileName();
			ContentServerHelper.service.writeContentStream(fileVo.getPdfFile(), inputFilePath);
		}

		if (fileVo.getStpFile() != null) {
			String inputFilePath = path + File.separator + fileVo.getStpFile().getFileName();
			ContentServerHelper.service.writeContentStream(fileVo.getStpFile(), inputFilePath);
		}

		if (fileVo.getExcelFile() != null) {
			String inputFilePath = path + File.separator + fileVo.getExcelFile().getFileName();
			ContentServerHelper.service.writeContentStream(fileVo.getExcelFile(), inputFilePath);
		}

		if (fileVo.getPartPdfFile() != null) {
			String inputFilePath = path + File.separator + fileVo.getPartPdfFile().getFileName();
			ContentServerHelper.service.writeContentStream(fileVo.getPartPdfFile(), inputFilePath);
		}
	}

	public ArrayList<DistColumns> getDistPartLinkList(Dist dist) throws Exception {
		ArrayList<DistColumns> list = new ArrayList<DistColumns>();
		QueryResult result = PersistenceHelper.manager.navigate(dist, "epm", DistPartLink.class);
		while (result.hasMoreElements()) {
			DistPartLink link = (DistPartLink) result.nextElement();
			list.add(new DistColumns(link.getDist()));
		}
		return list;
	}

	public ArrayList<DistPartDistributorUserLink> getDistPartDistributorUserLinks(DistPartLink link) throws Exception {
		ArrayList<DistPartDistributorUserLink> list = new ArrayList<DistPartDistributorUserLink>();
		QuerySpec qs = new QuerySpec();
		int idx = qs.appendClassList(DistPartDistributorUserLink.class, true);

		qs.appendWhere(new SearchCondition(DistPartDistributorUserLink.class, "roleAObjectRef.key.id", "=",
				link.getPersistInfo().getObjectIdentifier().getId()), new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find(qs);
		while (result.hasMoreElements()) {
			Object[] o = (Object[]) result.nextElement();
			DistPartDistributorUserLink link2 = (DistPartDistributorUserLink) o[0];
			list.add(link2);
		}
		return list;
	}

	public List<DistPartColumns> info(Map<String, Object> params) throws Exception {
		ArrayList<String> list = (ArrayList<String>) params.get("list");
		ArrayList<DistPartColumns> data = new ArrayList<DistPartColumns>();
		try {
			for (String oid : list) {
				WTPart part = (WTPart) CommonUtils.persistable(oid);
				DistPartColumns info = new DistPartColumns(part);
				data.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return data;
	}
	
	public ArrayList<DistPartColumns> getPartColumnLinks(Dist dist) throws Exception {
		ArrayList<DistPartColumns> list = new ArrayList<DistPartColumns>();

		QueryResult result = PersistenceHelper.manager.navigate(dist, "part", DistPartLink.class, false);
		while (result.hasMoreElements()) {
			DistPartLink link = (DistPartLink) result.nextElement();
			list.add(new DistPartColumns(link));
		}
		return list;
	}
	
	public ArrayList<DistributorUserColumns> getDistributorUserColumnLinks(Dist dist) throws Exception {
		ArrayList<DistributorUserColumns> list = new ArrayList<DistributorUserColumns>();

		QueryResult result = PersistenceHelper.manager.navigate(dist, "distUser", DistDistributorUserLink.class, false);
		while (result.hasMoreElements()) {
			DistDistributorUserLink link = (DistDistributorUserLink) result.nextElement();
			list.add(new DistributorUserColumns(link.getDistUser()));
		}
		return list;
	}
	
	public ArrayList<DistributorUser> getDistributorUserLinks(Dist dist) throws Exception {
		ArrayList<DistributorUser> list = new ArrayList<DistributorUser>();

		QueryResult result = PersistenceHelper.manager.navigate(dist, "distUser", DistDistributorUserLink.class, false);
		while (result.hasMoreElements()) {
			DistDistributorUserLink link = (DistDistributorUserLink) result.nextElement();
			list.add(link.getDistUser());
		}
		return list;
	}
	
	public ArrayList<DistDistributorUserLink> getDistDistributorUserLinks(Dist dist) throws Exception {
		ArrayList<DistDistributorUserLink> list = new ArrayList<DistDistributorUserLink>();
		
		QueryResult result = PersistenceHelper.manager.navigate(dist, "distUser", DistDistributorUserLink.class, false);
		while (result.hasMoreElements()) {
			DistDistributorUserLink link = (DistDistributorUserLink) result.nextElement();
			list.add(link);
		}
		return list;
	}
}
