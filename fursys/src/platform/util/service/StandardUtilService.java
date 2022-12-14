package platform.util.service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import platform.ebom.entity.EBOM;
import platform.ebom.entity.EBOMMaster;
import platform.ebom.service.EBOMMasterHelper;
import platform.echange.eco.entity.ECO;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.IBAUtils;
import platform.util.entity.DTMG;
import platform.util.entity.FileDTO;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.EPMDocument;
import wt.epm.familytable.EPMSepFamilyTable;
import wt.epm.structure.EPMContainedIn;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTPrincipal;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;

public class StandardUtilService extends StandardManager implements UtilService {

	public static StandardUtilService newStandardUtilService() throws WTException {
		StandardUtilService instance = new StandardUtilService();
		instance.initialize();
		return instance;
	}

	@Override
	public Set<FileDTO> getCadFileDataList(String oid, String color) throws WTException {
		Set<FileDTO> fileSet = new HashSet<>();
		Set<WTPart> list = new HashSet<>();
		try {
			EBOMMaster first = (EBOMMaster) CommonUtils.persistable(oid);

//			WTPart rootPart = PartHelper.manager.getPart(partNumber, revision);
			WTPart rootPart = PartHelper.manager.getLatest(first.getPart());
			EPMDocument rootEpm = PartHelper.manager.getEPMDocument(rootPart);
			String erpCode = IBAUtils.getStringValue(rootEpm, "ERP_CODE");
			String finish_code = IBAUtils.getStringValue(rootEpm, "FINISH_CODE");
			String material_code = IBAUtils.getStringValue(rootEpm, "MATERIAL_CODE");
			QueryResult result = ContentHelper.service.getContentsByRole(rootEpm, ContentRoleType.PRIMARY);
			if (result.hasMoreElements()) {
				ContentItem item = (ContentItem) result.nextElement();
				if (item instanceof ApplicationData) {
					ApplicationData data = (ApplicationData) item;

					fileSet.add(new FileDTO(rootEpm.getCADName(),
							ContentServerHelper.service.findLocalContentStream(data), data.getFileSize(),
							rootPart.getNumber(), rootPart.getVersionInfo().getIdentifier().getSeries().getValue(),
							color, erpCode, finish_code, material_code));
				}
			}

			fileSet.add(new FileDTO("Root.txt", new ByteArrayInputStream(rootEpm.getCADName().getBytes()),
					rootEpm.getCADName().getBytes().length, rootPart.getNumber(),
					rootPart.getVersionIdentifier().getSeries().getValue(), color, erpCode, finish_code,
					material_code));

//			UtilHelper.manager.getAllParts(first, color, fileSet);

			UtilHelper.manager.dtmgPart(rootPart, list);

			for (WTPart p : list) {
//				WTPart p = PartHelper.manager.getLatest(l.getChild().getPart());
				EPMDocument epm = PartHelper.manager.getEPMDocument(p);

				EBOMMaster child = EBOMMasterHelper.manager.getEBOMMaster(p.getMaster(), color);

				System.out.println("epm=" + epm.getCADName() + ", child = " + child);

				if (epm != null) {
					QueryResult qr = ContentHelper.service.getContentsByRole(epm, ContentRoleType.PRIMARY);
					if (qr.hasMoreElements()) {
						ContentItem item = (ContentItem) qr.nextElement();
						if (item instanceof ApplicationData) {
							ApplicationData data = (ApplicationData) item;

							String _erpCode = IBAUtils.getStringValue(epm, "ERP_CODE");
							String _finish_code = IBAUtils.getStringValue(epm, "FINISH_CODE");
							String _material_code = IBAUtils.getStringValue(epm, "MATERIAL_CODE");
							String applyColor = child != null ? child.getApplyColor() : "";
							FileDTO dto = new FileDTO(epm.getCADName(),
									ContentServerHelper.service.findLocalContentStream(data), data.getFileSize(),
									p.getNumber(), p.getVersionInfo().getIdentifier().getSeries().getValue(),
									applyColor, _erpCode, _finish_code, _material_code);

							// 기존 데이터 없을시..
							if (!fileSet.contains(dto)) {
								fileSet.add(dto);
							}
						}
					}

					// family table..
					if (qr.size() == 0) {
						QueryResult _qr = PersistenceHelper.manager.navigate(epm, "containedIn", EPMContainedIn.class);
						if (_qr.hasMoreElements()) {
							EPMSepFamilyTable ft = (EPMSepFamilyTable) _qr.nextElement();
							QueryResult _result = ContentHelper.service.getContentsByRole(ft, ContentRoleType.PRIMARY);
							if (_result.hasMoreElements()) {
								ContentItem item = (ContentItem) _result.nextElement();
								if (item instanceof ApplicationData) {
									ApplicationData data = (ApplicationData) item;

									String _erpCode = IBAUtils.getStringValue(epm, "ERP_CODE");
									String _finish_code = IBAUtils.getStringValue(epm, "FINISH_CODE");
									String _material_code = IBAUtils.getStringValue(epm, "MATERIAL_CODE");
									String _applyColor = child != null ? child.getApplyColor() : "";
									FileDTO dto = new FileDTO(ft.getName(),
											ContentServerHelper.service.findLocalContentStream(data),
											data.getFileSize(), p.getNumber(),
											p.getVersionInfo().getIdentifier().getSeries().getValue(), _applyColor,
											_erpCode, _finish_code, _material_code);

									// 기존 데이터 없을시..
									if (!fileSet.contains(dto)) {
										fileSet.add(dto);
									}
								}
							}
						}
					}
				}
			}

			JSONArray arr = new JSONArray();
			JSONObject map = new JSONObject();

			for (FileDTO dto : fileSet) {
				if ("Root.txt".equals(dto.getFileName())) {
					continue;
				}
				JSONObject partInfo = new JSONObject();
				partInfo.put("fileName", dto.getFileName());
				partInfo.put("partNumber", dto.getPartNumber());
				partInfo.put("partRev", dto.getPartRev());
				partInfo.put("color", dto.getColor());
				partInfo.put("erpCode", dto.getErpCode());
				partInfo.put("finish_code", dto.getFinish_code());
				partInfo.put("material_code", dto.getMaterial_code());
				map.put(dto.getFileName(), partInfo);
				arr.put(partInfo);
			}
			String mapStr = map.toJSONString();
			String arrStr = arr.toJSONString();

			fileSet.add(new FileDTO("FileInfoMap.json", new ByteArrayInputStream(mapStr.getBytes()),
					mapStr.getBytes().length, "", "", "", "", "", ""));
			fileSet.add(new FileDTO("FileInfoArr.json", new ByteArrayInputStream(arrStr.getBytes()),
					arrStr.getBytes().length, "", "", "", "", "", ""));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileSet;
	}

	@Override
	public void transferToDTMG(EBOM ebom, ECO eco) throws Exception {
		Transaction trs = new Transaction();
		try {
			trs.start();

//			String root = WTProperties.getLocalProperties().getProperty("wt.codebase.location") + File.separator + "jsp"
//					+ File.separator + "temp" + File.separator + "pdm";
//			File p = new File(root);
//			if (!p.exists()) {
//				p.mkdirs();
//			}

			String hostName = WTProperties.getLocalProperties().getProperty("wt.rmi.server.hostname");

			EBOMMaster mm = ebom.getEbomMaster();
			ArrayList<EBOMMaster> list = EBOMMasterHelper.manager.getFirst(mm, new ArrayList<EBOMMaster>());
			for (EBOMMaster first : list) {

				WTPart part = PartHelper.manager.getLatest(first.getPart());
				String erpCode = IBAUtils.getStringValue(part, "ERP_CODE");
				String color = first.getColor();
				String ecoNo = eco.getNumber();
				String version = first.getVer();
				String partNumber = first.getPart().getNumber();

				boolean exist = UtilHelper.manager.exist(erpCode, color, version, ecoNo);

				// 값이 없으면 중복 X
				if (!exist) {

//					Set<FileDTO> fileSet = getCadFileDataList(first, color);
					WTPrincipal prin = SessionHelper.manager.getPrincipal();

//					String fileName = root + File.separator + partNumber + "-" + version + ".zip";

//					ZipUtils.writeZipFile(fileSet, fileName);

//					File file = new File(fileName);

					String url = "http://" + hostName + "/Windchill/platform/util/download?oid="
							+ first.getPersistInfo().getObjectIdentifier().getStringValue() + "&color=" + color;

					DTMG dtmg = DTMG.newDTMG();
					dtmg.setErpCode(erpCode);
					dtmg.setColor(color);
					dtmg.setEcoNumber(ecoNo);
					dtmg.setVersion(version);
					dtmg.setDtmg(url);
					dtmg.setOwnership(Ownership.newOwnership(prin));

					PersistenceHelper.manager.save(dtmg);
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
}
