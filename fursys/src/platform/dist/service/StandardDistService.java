package platform.dist.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileDeleteStrategy;

import platform.dist.entity.Dist;
import platform.dist.entity.DistDTO;
import platform.dist.entity.DistDistributorUserLink;
import platform.dist.entity.DistPartColumns;
import platform.dist.entity.DistPartDistributorUserLink;
import platform.dist.entity.DistPartLink;
import platform.dist.entity.Distributor;
import platform.dist.entity.DistributorUser;
import platform.dist.vo.TransferFileVO;
import platform.dist.vo.TransferXMLVO;
import platform.epm.service.EpmHelper;
import platform.part.service.PartHelper;
import platform.util.CommonUtils;
import platform.util.ContentUtils;
import platform.util.DateUtils;
import platform.util.SFTPUtils;
import platform.util.StringUtils;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.ContentServerHelper;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;

public class StandardDistService extends StandardManager implements DistService {

	public static StandardDistService newStandardDistService() throws WTException {
		StandardDistService instance = new StandardDistService();
		instance.initialize();
		return instance;
	}

	@Override
	public Dist create(Map<String, Object> params) throws Exception {
		//ArrayList<String> secondary = params.getSecondary();
		Dist dist = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			String distName = (String) params.get("distName"); 
			String description = (String) params.get("description");
			String duration = (String) params.get("duration");
			
			
			dist = Dist.newDist();
			dist.setOwnership(CommonUtils.ownership());
			dist.setName(distName);
			dist.setDescription(description);
			dist.setNumber(DistHelper.manager.getNextNumber());
			dist.setState("작업중");
			dist.setDuration(Integer.parseInt(duration));
			dist.setMaterial_type("ITEM");

			PersistenceHelper.manager.save(dist);

			//ContentUtils.updateSecondary(secondary, dist);

			ArrayList<Map<String, Object>> partList = (ArrayList<Map<String, Object>>) params.get("partList");
			
			for (Map<String, Object> partMap : partList) {
				
				String oid  = (String)partMap.get("oid");
				
				boolean pdf  = (boolean)partMap.get("pdf");
				boolean step  = (boolean)partMap.get("step");
				boolean dwg  = (boolean)partMap.get("dwg");	
				
				System.out.println("### pdf=-="+pdf);
				System.out.println("### step=-="+step);
				System.out.println("### dwg=-="+dwg);
				
				WTPart part = (WTPart) CommonUtils.persistable(oid);
				DistPartLink link = DistPartLink.newDistPartLink(dist, part);
				//여기
				link.setPdf(pdf);
				link.setStep(step);
				link.setDwg(dwg);
				link = (DistPartLink) PersistenceHelper.manager.save(link);
			}
			
			ArrayList<Map<String, Object>> userList = (ArrayList<Map<String, Object>>) params.get("distributorList");
			
			for (Map<String, Object> userMap : userList) {
				String uoid  = (String)userMap.get("uoid");
				DistributorUser diUser = (DistributorUser) CommonUtils.persistable(uoid);
				DistDistributorUserLink link = DistDistributorUserLink.newDistDistributorUserLink(dist, diUser);
				PersistenceHelper.manager.save(link);
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
		return dist;
	}
	
	@Override
	public Dist matCreate(DistDTO params) throws Exception {
		ArrayList<String> secondary = params.getSecondary();
		Dist dist = null;
		Transaction trs = new Transaction();
		try {
			trs.start();
			
			dist = Dist.newDist();
			dist.setOwnership(CommonUtils.ownership());
			dist.setName(params.getName());
			dist.setDescription(params.getDescription());
			dist.setNumber(DistHelper.manager.getNextNumber());
			dist.setState("작업중");
			dist.setDuration(params.getDuration());
			dist.setMaterial_type("MAT");
			
			PersistenceHelper.manager.save(dist);
			
			ContentUtils.updateSecondary(secondary, dist);
			
			System.out.println("머냐.");
			
			for (DistPartColumns map : params.getPartList()) {
				WTPart part = (WTPart) CommonUtils.persistable(map.getOid());
				DistPartLink link = DistPartLink.newDistPartLink(dist, part);
				link.setPdf(map.isPdf());
				link.setDwg(map.isDwg());
				link.setStep(map.isStep());
				
//				DistributorUser user=(DistributorUser) CommonUtils.persistable(map.getUoid());
//				link.setDistributorType(user.getType());
//				link.setDistributorUserName(user.getUserName());
//				link.setDistributorName(user.getName());
//				
//				link = (DistPartLink) PersistenceHelper.manager.save(link);
////				Vector<String> distUsers = map.getDistributorUser();
//				
//				
//				String uoid = map.getUoid();
//				if (StringUtils.isNotNull(uoid)) {
//					DistributorUser distUser = (DistributorUser) CommonUtils.persistable(uoid);
//					DistPartDistributorUserLink ddLink = DistPartDistributorUserLink.newDistPartLink(link, distUser);
//					PersistenceHelper.manager.save(ddLink);
//				}
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
		return dist;
	}

	@Override
	public void afterAction(Dist dist, String type) throws Exception {

		Transaction trs = new Transaction();
		try {
			trs.start();

			String codebase = WTProperties.getLocalProperties().getProperty("wt.codebase.location");
			String path = codebase + File.separator + "jsp" + File.separator + "dist" + File.separator
					+ DateUtils.getTimeToString(new Date(), "yyyy-MM-dd") + File.separator + type
					+ String.valueOf(dist.getPersistInfo().getObjectIdentifier().getId());

			File dir = new File(path);

			if (dir.exists()) {
				FileDeleteStrategy.FORCE.delete(dir);
			}

			dir.mkdirs();

			WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
			
//			DistHelper.manager.parseXML();

			Timestamp today = DateUtils.today();

			TransferXMLVO vo = new TransferXMLVO();
			vo.setDistTypeCd(type);
			vo.setOid(type + String.valueOf(dist.getPersistInfo().getObjectIdentifier().getId()));
			vo.setName(dist.getName());
			vo.setNumber(dist.getNumber());
			vo.setContent(StringUtils.convertToDecodeBase64(dist.getDescription()));
			vo.setSenderName("admin");
			vo.setSenderEmail(sessionUser.getEMail());
			vo.setDistributeDate(DateUtils.getTimeToString(today, "yyyy-MM-dd"));
			vo.setReleaseDate(DateUtils.getTimeToString(today, "yyyy-MM-dd"));

			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DAY_OF_MONTH, 7 * dist.getDuration());
			Timestamp endTime = new Timestamp(ca.getTime().getTime());
			vo.setExpirationDate(DateUtils.getTimeToString(endTime, "yyyy-MM-dd"));

			ArrayList<DistPartLink> links = DistHelper.manager.getPartLinks(dist);
			for (DistPartLink link : links) {
				WTPart part = link.getPart();
				EPMDocument epm = PartHelper.manager.getEPMDocument(part);
				if( epm != null) {
					vo.addAllDistributeDetail(DistHelper.manager.details(type, link, path, dist, epm));
					EPMDocument epm2d = EpmHelper.manager.getEPM2D(epm);
					if (epm2d != null) {
						vo.addAllDistributeDetail(DistHelper.manager.details(type, link, path, dist, epm2d));
					}
				}
			}

			File triggerFile = new File(path + File.separator + "trigger");
			triggerFile.createNewFile();

			JAXBContext context = JAXBContext.newInstance(TransferXMLVO.class);
			Marshaller marshaller = context.createMarshaller();

			// 보기 좋게 출력해주는 옵션
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			String fileFullPath = path + File.separator + dist.getNumber() + ".xml";
			File xmlFile = new File(fileFullPath);
			// 표준 출력으로 결과를 보여준다.
			marshaller.marshal(vo, xmlFile);

			File attach = new File(path + File.separator + "attach");
			attach.mkdirs();

			QueryResult qr = ContentHelper.service.getContentsByRole(dist, ContentRoleType.SECONDARY);
			while (qr.hasMoreElements()) {
				ContentItem item = (ContentItem) qr.nextElement();
				if (item instanceof ApplicationData) {
					ApplicationData dd = (ApplicationData) item;
					String inputFilePath = path + File.separator + "attach" + File.separator + dd.getFileName();
					ContentServerHelper.service.writeContentStream(dd, inputFilePath);
					TransferFileVO file = new TransferFileVO();
					file.setFileName(dd.getFileName());
					file.setOid(dd.toString());
					vo.addFile(file);
				}
			}

			SFTPUtils sftpUtil = new SFTPUtils();
			sftpUtil.sendFiles(dir);

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
	public Dist delete(String oid) throws Exception {
		Dist dist = null;
		Transaction trs = new Transaction();
		try {

			dist = (Dist) CommonUtils.persistable(oid);

			ArrayList<DistPartLink> partLinks = DistHelper.manager.getPartLinks(dist);
			for (DistPartLink link : partLinks) {
				PersistenceHelper.manager.delete(link);
			}

			dist = (Dist) PersistenceHelper.manager.delete(dist);

		} catch (Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
		return dist;
	}

	@Override
	public Dist modify(Map<String, Object> params) throws Exception {
		ArrayList<String> secondary = (ArrayList<String>) params.get("secondary");
		Dist dist = null;
		Transaction trs = new Transaction();
		
		String oid = (String) params.get("oid");
		String distName = (String) params.get("distName");
		String description = (String) params.get("description");
		String duration = (String) params.get("duration");
		ArrayList<Map<String, String>> partList = (ArrayList<Map<String, String>>) params.get("partList");
		ArrayList<Map<String, String>> userList = (ArrayList<Map<String, String>>) params.get("distributorList");

		try {
			trs.start();
			dist = (Dist) CommonUtils.persistable(oid);
			dist.setOwnership(CommonUtils.ownership());
			dist.setName(distName);
			dist.setDescription(description);
			dist.setNumber(DistHelper.manager.getNextNumber());
			dist.setState("작업중");
			dist.setDuration(Integer.parseInt(duration));

			PersistenceHelper.manager.save(dist);

			ContentUtils.updateSecondary(secondary, dist);

			ArrayList<DistPartLink> links = DistHelper.manager.getPartLinks(dist);
			ArrayList<Dist> dlinks = new ArrayList<>();
			for(DistPartLink dd : links) {
				Dist ds = dd.getDist();
				System.out.println("dd.getPart() : " + dd.getPart());
				PersistenceHelper.manager.delete(dd);
				if(!dlinks.contains(ds)) {
					dlinks.add(ds);
				}
			}
			//test
			for (Map<String, String> partMap : partList) {
				String poid = (String) partMap.get("oid");
				WTPart part = (WTPart) CommonUtils.persistable(poid);
				DistPartLink link = DistPartLink.newDistPartLink(dist, part);
//				link.setPdf(map.isPdf());
//				link.setDwg(map.isDwg());
//				link.setStep(map.isStep());
				link = (DistPartLink) PersistenceHelper.manager.save(link);
			}

			ArrayList<DistDistributorUserLink> dUsers = DistHelper.manager.getDistDistributorUserLinks(dist);
			System.out.println("dUser : " + dUsers);
			for(DistDistributorUserLink link : dUsers) {
				PersistenceHelper.manager.delete(link);
			}
			
			for (Map<String, String> userMap : userList) {
				String uoid = (String) userMap.get("oid");
				DistributorUser diUser = (DistributorUser) CommonUtils.persistable(uoid);
				DistDistributorUserLink link = DistDistributorUserLink.newDistDistributorUserLink(dist, diUser);
				PersistenceHelper.manager.save(link);
			}
				
//				System.out.println("DType : " + map.getDistributorType());
//				System.out.println("LType : " + link.getDistributorType());
//				link.setDistributorType(link.getDistributorType());
//				link.setDistributorName(link.getDistributorName());
//				link.setDistributorUserName(link.getDistributorUserName());
//				
//				link = (DistPartLink) PersistenceHelper.manager.save(link);
////				Vector<DistributorUser> distUsers = map.getDistributorUser();
//				String uoid = map.getUoid();
//				if (StringUtils.isNotNull(uoid)) {
//					DistributorUser distUser = (DistributorUser) CommonUtils.persistable(uoid);
//					DistPartDistributorUserLink ddLink = DistPartDistributorUserLink.newDistPartLink(link, distUser);
//					PersistenceHelper.manager.save(ddLink);
//				}

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
		return dist;
	}
	
}
