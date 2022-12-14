package platform.dist.service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileDeleteStrategy;

import platform.dist.entity.Dist;
import platform.dist.entity.DistDTO;
import platform.dist.entity.DistPartColumns;
import platform.dist.entity.DistPartDistributorUserLink;
import platform.dist.entity.DistPartLink;
import platform.dist.entity.Distributor;
import platform.dist.entity.DistributorUser;
import platform.dist.vo.TransferFileVO;
import platform.dist.vo.TransferXMLVO;
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
	public Dist create(DistDTO params) throws Exception {
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
			dist.setMaterial_type("ITEM");

			PersistenceHelper.manager.save(dist);

			ContentUtils.updateSecondary(secondary, dist);

			System.out.println("머냐.");

			for (DistPartColumns map : params.getPartList()) {
				WTPart part = (WTPart) CommonUtils.persistable(map.getOid());
				DistPartLink link = DistPartLink.newDistPartLink(dist, part);
				link.setPdf(map.isPdf());
				link.setDwg(map.isDwg());
				link.setStep(map.isStep());
				
				DistributorUser user=(DistributorUser) CommonUtils.persistable(map.getUoid());
				link.setDistributorType(user.getType());
				link.setDistributorUserName(user.getUserName());
				System.out.println("distributor : " + user.getDistributor());
				System.out.println("Name : " + user.getName());
				System.out.println("email : " + user.getEmail());
				link.setDistributorName(user.getName());
				
				link = (DistPartLink) PersistenceHelper.manager.save(link);
//				Vector<String> distUsers = map.getDistributorUser();

				String uoid = map.getUoid();
				if (StringUtils.isNotNull(uoid)) {
					DistributorUser distUser = (DistributorUser) CommonUtils.persistable(uoid);
					DistPartDistributorUserLink ddLink = DistPartDistributorUserLink.newDistPartLink(link, distUser);
					PersistenceHelper.manager.save(ddLink);
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
				link = (DistPartLink) PersistenceHelper.manager.save(link);
//				Vector<String> distUsers = map.getDistributorUser();
				
				String uoid = map.getUoid();
				if (StringUtils.isNotNull(uoid)) {
					DistributorUser distUser = (DistributorUser) CommonUtils.persistable(uoid);
					DistPartDistributorUserLink ddLink = DistPartDistributorUserLink.newDistPartLink(link, distUser);
					PersistenceHelper.manager.save(ddLink);
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
			vo.setContent(StringUtils.convertToStr(dist.getDescription(), ""));
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
				vo.addAllDistributeDetail(DistHelper.manager.details(type, link, path));
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
	public Dist modify(DistDTO params) throws Exception {
		ArrayList<String> secondary = params.getSecondary();
		Dist dist = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			dist = (Dist) CommonUtils.persistable(params.getOid());
			dist.setOwnership(CommonUtils.ownership());
			dist.setName(params.getName());
			dist.setDescription(params.getDescription());
			dist.setNumber(DistHelper.manager.getNextNumber());
			dist.setState("작업 중");
			dist.setDuration(params.getDuration());

			PersistenceHelper.manager.save(dist);

			ContentUtils.updateSecondary(secondary, dist);

			for (DistPartColumns map : params.getPartList()) {
				WTPart part = (WTPart) CommonUtils.persistable(map.getOid());
				DistPartLink link = DistPartLink.newDistPartLink(dist, part);
				link.setPdf(map.isPdf());
				link.setDwg(map.isDwg());
				link.setStep(map.isStep());
				link = (DistPartLink) PersistenceHelper.manager.save(link);
//				Vector<DistributorUser> distUsers = map.getDistributorUser();
				String uoid = map.getUoid();
				if (StringUtils.isNotNull(uoid)) {
					DistributorUser distUser = (DistributorUser) CommonUtils.persistable(uoid);
					DistPartDistributorUserLink ddLink = DistPartDistributorUserLink.newDistPartLink(link, distUser);
					PersistenceHelper.manager.save(ddLink);
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
		return dist;
	}
}
