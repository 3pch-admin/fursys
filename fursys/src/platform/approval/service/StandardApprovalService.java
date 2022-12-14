package platform.approval.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import platform.approval.entity.ApprovalHistoryLink;
import platform.approval.entity.ApprovalLine;
import platform.approval.entity.ApprovalMaster;
import platform.approval.entity.HistoryMasterLink;
import platform.approval.entity.LatestApprovalLine;
import platform.approval.entity.OpenPersistableLink;
import platform.approval.entity.ReceiveObjectLink;
import platform.dist.entity.Dist;
import platform.dist.service.DistHelper;
import platform.echange.ecn.entity.ECN;
import platform.echange.ecn.service.ECNHelper;
import platform.echange.eco.entity.ECO;
import platform.echange.eco.service.ECOHelper;
import platform.echange.ecr.entity.ECR;
import platform.message.service.MessageHelper;
import platform.user.entity.User;
import platform.util.CommonUtils;
import platform.util.DateUtils;
import platform.util.StringUtils;
import wt.doc.WTDocument;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.vc.Mastered;

public class StandardApprovalService extends StandardManager implements ApprovalService {

	public static StandardApprovalService newStandardApprovalService() throws WTException {
		StandardApprovalService instance = new StandardApprovalService();
		instance.initialize();
		return instance;
	}

	@Override
	public void submit(Persistable per, ArrayList<String> appOid, ArrayList<String> refOid, String appLimit)
			throws Exception {
		ApprovalMaster master = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			String name = ApprovalHelper.manager.getName(per);
			Ownership ownership = Ownership.newOwnership(SessionHelper.manager.getPrincipal());

			master = ApprovalMaster.newApprovalMaster();
			master.setName(name);
			master.setCompleteTime(null);
			master.setOwnership(ownership); // 등록자 = 기안자
			master.setPersist(per);
			master.setStartTime(DateUtils.today());
			// 모든상태는 결재중 경유는 없다..
			master.setState(ApprovalHelper.LINE_APPROVING);
			master = (ApprovalMaster) PersistenceHelper.manager.save(master);

			ApprovalLine startLine = ApprovalLine.newApprovalLine();
			startLine.setOwnership(ownership);
			startLine.setMaster(master);
			startLine.setSort(-50);
			startLine.setUser((WTUser) SessionHelper.manager.getPrincipal());
			startLine.setStartTime(DateUtils.today());
			startLine.setLineType(ApprovalHelper.APP_LINE);
			startLine.setRole(ApprovalHelper.SUBMIT_ROLE);
			startLine.setDescription(ownership.getOwner().getFullName() + " 사용자가 결재를 제출하였습니다.");
			startLine.setState(ApprovalHelper.LINE_SUBMIT_COMPLETE);
			startLine.setCompleteTime(DateUtils.today());
			startLine = (ApprovalLine) PersistenceHelper.manager.save(startLine);

			saveHistory(startLine, per);

			// 결재
			int sort = 0;
			HashMap<String, Object> compMap = new HashMap<String, Object>();
			for (int i = 0; i < appOid.size(); i++) {
				String oid = (String) appOid.get(i).split("&")[0];
				String type = (String) appOid.get(i).split("&")[1];

				String s = (String) compMap.get("t");
				if (s == null) {
					sort += 1;
				} else {
					if (!s.equals(type)) {
						sort += 1;
					} else {
						if (type.equals("결재")) {
							sort += 1;
						}
					}
				}
				compMap.put("t", type);

				User user = (User) CommonUtils.persistable(oid);
				ApprovalLine appLine = ApprovalLine.newApprovalLine();
				appLine.setUser(user.getWtUser());
				appLine.setOwnership(Ownership.newOwnership(user.getWtUser()));
				appLine.setCompleteTime(null);
				appLine.setDescription(null);
				appLine.setMaster(master);
				appLine.setUser(user.getWtUser()); // 담당자
				appLine.setRole(ApprovalHelper.APP_ROLE);
				appLine.setSort(sort);
				if (type.equals("경유")) {
					appLine.setLimit(DateUtils.startTimestamp(appLimit));
					appLine.setLineType(ApprovalHelper.VIA_LINE);
				} else {
					appLine.setLineType(ApprovalHelper.APP_LINE);
				}

				if (i == 0) {
					appLine.setStartTime(DateUtils.today());
					appLine.setState(ApprovalHelper.LINE_APPROVING);

					// 승인중 메세지 전달
					WTUser submitUser = (WTUser) SessionHelper.manager.getPrincipal();
					MessageHelper.service.create(
							submitUser.getFullName() + "이(가) " + master.getName() + " 결재를 기안하였습니다.",
							Ownership.newOwnership(user.getWtUser()));

				} else {
					appLine.setStartTime(null);
					appLine.setState(ApprovalHelper.LINE_STAND);
				}
				appLine = (ApprovalLine) PersistenceHelper.manager.save(appLine);
			}

			for (int i = 0; i < refOid.size(); i++) {
				String oid = (String) refOid.get(i);
				User user = (User) CommonUtils.persistable(oid);
				ApprovalLine refLine = ApprovalLine.newApprovalLine();
				refLine.setOwnership(Ownership.newOwnership(user.getWtUser()));
				refLine.setUser(user.getWtUser());
				refLine.setCompleteTime(null);
				refLine.setDescription(null);
				refLine.setMaster(master);
				refLine.setLineType(ApprovalHelper.REF_LINE);
				refLine.setSort(0);
//				refLine.setRole(ApprovalHelper.REF_ROLE);
				// 수신 바로 시작
				refLine.setUser(user.getWtUser());
				refLine.setStartTime(DateUtils.today());
//				refLine.setState(ApprovalHelper.LINE_REF_STAND);
				refLine = (ApprovalLine) PersistenceHelper.manager.save(refLine);
			}

			if (per instanceof LifeCycleManaged) {
//				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) per, State.toState("INAPPROVE"));
			} else {
				if (per instanceof ECR) {
					ECR ecr = (ECR) per;
					ecr.setState("승인중");
					PersistenceHelper.manager.modify(ecr);
				}

				if (per instanceof ECO) {
					ECO eco = (ECO) per;
					eco.setState("승인중");
					PersistenceHelper.manager.modify(eco);
				}

				if (per instanceof ECN) {
					ECN ecn = (ECN) per;
					ecn.setState("승인중");
					PersistenceHelper.manager.modify(ecn);
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
	public void rtn(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		String description = (String) params.get("description");
		Transaction trs = new Transaction();
		try {
			trs.start();

			WTUser prin = (WTUser) SessionHelper.manager.getPrincipal();

			ApprovalLine line = (ApprovalLine) CommonUtils.persistable(oid);
			line.setDescription(description);
			line.setState(ApprovalHelper.LINE_RETURN_COMPLETE);
			line.setCompleteTime(DateUtils.today());
			PersistenceHelper.manager.modify(line);

			ApprovalMaster master = line.getMaster();
			master.setState(ApprovalHelper.LINE_RETURN_COMPLETE);
			master.setCompleteTime(DateUtils.today());
			PersistenceHelper.manager.modify(master);

			MessageHelper.service.create(master.getName() + " 결재을(를) " + prin.getFullName() + " 사용자가 반려하였습니다.",
					master.getOwnership());

			Persistable per = master.getPersist();
			saveHistory(line, per);
			if (per instanceof LifeCycleManaged) {
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) per, State.toState("RETURN"));
			} else {
				if (per instanceof ECR) {
					ECR ecr = (ECR) per;
					ecr.setState("반려됨");
					PersistenceHelper.manager.modify(ecr);
				}

				if (per instanceof ECO) {
					ECO eco = (ECO) per;
					eco.setState("반려됨");
					PersistenceHelper.manager.modify(eco);
				}

				if (per instanceof ECN) {
					ECN ecn = (ECN) per;
					ecn.setState("반려됨");
					PersistenceHelper.manager.modify(ecn);
				}
			}

			trs.commit();
			trs = null;
		} catch (

		Exception e) {
			e.printStackTrace();
			trs.rollback();
			throw e;
		} finally {
			if (trs != null)
				trs.rollback();
		}
	}

	@Override
	public void approval(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		String description = (String) params.get("description");
		ReferenceFactory rf = new ReferenceFactory();
		ApprovalLine line = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			line = (ApprovalLine) rf.getReference(oid).getObject();
			ApprovalMaster master = line.getMaster();
			Persistable per = master.getPersist();

			if (!StringUtils.isNotNull(description)) {
				description = "승인합니다.";
			}

			if (line.getLineType().equals(ApprovalHelper.EXAM_LINE)) {
				line.setState(ApprovalHelper.LINE_EXAM_COMPLETE);
			} else if (line.getLineType().equals(ApprovalHelper.APP_LINE)) {
				line.setState(ApprovalHelper.LINE_APPROVAL_COMPLETE);
			} else if (line.getLineType().equals(ApprovalHelper.VIA_LINE)) {
				line.setState(ApprovalHelper.LINE_VIA_COMPLETE);
			}
			line.setDescription(description);
			line.setCompleteTime(DateUtils.today());

			line = (ApprovalLine) PersistenceHelper.manager.modify(line);
//			saveHistory(line, per);

			// 경유 라인인지 ..
			boolean isViaLine = line.getLineType().equals(ApprovalHelper.VIA_LINE);

			boolean isLastViaLine = false;
			if (line.getLineType().equals(ApprovalHelper.VIA_LINE)) {
				isLastViaLine = ApprovalHelper.manager.isLastViaLine(master);
			}

			// 경유라인이 아닐 경우 체크
			// 경유 라인이지만 마지막 라인일 경우 체크
			// 결재가 아닐경우
			if (!line.getLineType().equals(ApprovalHelper.APP_LINE)) {
				if (!isViaLine || (isViaLine && isLastViaLine)) {
					ArrayList<ApprovalLine> examLines = ApprovalHelper.manager.getExamAndViaLines(master);
					for (ApprovalLine lines : examLines) {
						int sort = lines.getSort(); // 원래꺼가 1 다음꺼 2..
						if (sort == line.getSort() + 1) {
							lines.setStartTime(DateUtils.today());
							if (lines.getLineType().equals(ApprovalHelper.EXAM_LINE)) {
								lines.setState(ApprovalHelper.LINE_EXAM);
							} else if (lines.getLineType().equals(ApprovalHelper.VIA_LINE)) {
								lines.setState(ApprovalHelper.LINE_VIA_STAND);
							}
							MessageHelper.service.create(master.getName() + " 결재가 도착하였습니다.", lines.getOwnership());
						}
						lines.setSort(sort - 1);
						lines = (ApprovalLine) PersistenceHelper.manager.modify(lines);
					}
				}
			}

			boolean isLastAppLine = ApprovalHelper.manager.isLastLine(master);
//			 마지막이면 승인 결재 시작..
			if (isLastAppLine && !line.getLineType().equals(ApprovalHelper.APP_LINE)) {
				ApprovalLine lastLine = ApprovalHelper.manager.getAppLine(master);
				lastLine.setStartTime(DateUtils.today());
				lastLine.setState(ApprovalHelper.LINE_APPROVING);

				MessageHelper.service.create(master.getName() + " 결재가 도착하였습니다.", lastLine.getOwnership());
				lastLine = (ApprovalLine) PersistenceHelper.manager.modify(lastLine);
				PersistenceHelper.manager.modify(lastLine);
				// 객체 상태 어떻게 처리 할지??
			}

			// 결재 완료시 수신 확인
			if (line.getState().equals(ApprovalHelper.LINE_APPROVAL_COMPLETE)) {
				// 수신 라인이 있는지 체크에 들어간다..
				ArrayList<ApprovalLine> list = ApprovalHelper.manager.getReceiveLine(master);
				for (ApprovalLine receiveLine : list) {
					receiveLine.setDescription(null);
					receiveLine.setStartTime(DateUtils.today());
					receiveLine.setState(ApprovalHelper.LINE_RECEIVE_STAND);

					// 수신자 처리 메세지..
					MessageHelper.service.create(master.getName() + " 결재가 도착하였습니다.", receiveLine.getOwnership());
					receiveLine = (ApprovalLine) PersistenceHelper.manager.modify(receiveLine);

					// 객체 상태 변경 부분..
					if (per instanceof LifeCycleManaged) {
//						State state = State.toState("RECEIVING"); // RECEIVED
//						per = (Persistable) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) per, state);
					} else {
						if (per instanceof ECR) {
							ECR ecr = (ECR) per;
							ecr.setState("접수중");
							PersistenceHelper.manager.modify(ecr);
						}

						if (per instanceof ECO) {
							ECO eco = (ECO) per;
							eco.setState("접수중");
							eco = (ECO) PersistenceHelper.manager.modify(eco);
						}

						if (per instanceof ECN) {
							ECN ecn = (ECN) per;
							ecn.setState("접수중");
							PersistenceHelper.manager.modify(ecn);
						}

						if (per instanceof Dist) {
							Dist dist = (Dist) per;
							dist.setState("접수중");
							PersistenceHelper.manager.modify(dist);
						}
					}
				}
				// 수신자 없을시..
				if (list.size() == 0) {
					master.setCompleteTime(DateUtils.today());
					master.setState(ApprovalHelper.LINE_APPROVAL_COMPLETE);
					PersistenceHelper.manager.modify(master);

					// 객체 상태 변경 부분..
					if (per instanceof LifeCycleManaged) {
						State state = State.toState("RELEASED");
						per = (Persistable) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) per, state);
					} else {
						if (per instanceof ECR) {
							ECR ecr = (ECR) per;
							ecr.setState("결재완료");
							PersistenceHelper.manager.modify(ecr);
						}

						if (per instanceof ECO) {
							ECO eco = (ECO) per;
							eco.setState("결재완료");
							eco = (ECO) PersistenceHelper.manager.modify(eco);
							ECOHelper.service.afterAction(eco);
						}

						if (per instanceof ECN) {
							ECN ecn = (ECN) per;
							ecn.setState("결재완료");
							ECNHelper.service.afterAction(ecn);
							PersistenceHelper.manager.modify(ecn);
						}
					}

					if (per instanceof Dist) {
						Dist dist = (Dist) per;
						dist.setState("결재완료");
						DistHelper.service.afterAction(dist, "DIST");
						PersistenceHelper.manager.modify(dist);
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
	}

	@Override
	public void confirm(Map<String, Object> params) throws Exception {
		String oid = (String) params.get("oid");
		String description = (String) params.get("description");
		ReferenceFactory rf = new ReferenceFactory();
		ApprovalLine line = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			line = (ApprovalLine) rf.getReference(oid).getObject();

			if (!StringUtils.isNotNull(description)) {
				description = "확인합니다..";
			}

			line.setDescription(description);
			line.setCompleteTime(DateUtils.today());
//			line.setState(ApprovalHelper.LINE_REF_COMPLETE);

			line = (ApprovalLine) PersistenceHelper.manager.modify(line);

			saveHistory(line, line.getMaster().getPersist());
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
	public void saveHistory(ApprovalLine line, Persistable per) throws Exception {
		QuerySpec query = new QuerySpec();
		Transaction trs = new Transaction();
		try {
			trs.start();

			// 마스터 객체와 일반 객체 구분해서 저장
			if (per instanceof WTDocument || per instanceof WTPart || per instanceof EPMDocument) {

				RevisionControlled rc = (RevisionControlled) per;
				Mastered mastered = rc.getMaster();

				int idx = query.appendClassList(HistoryMasterLink.class, true);
				int idx_p = query.appendClassList(Mastered.class, false);
				SearchCondition sc = new SearchCondition(HistoryMasterLink.class, "roleBObjectRef.key.id",
						Persistable.class, "thePersistInfo.theObjectIdentifier.id");
				query.appendWhere(sc, new int[] { idx, idx_p });
				query.appendAnd();

				sc = new SearchCondition(HistoryMasterLink.class, "roleBObjectRef.key.id", "=",
						mastered.getPersistInfo().getObjectIdentifier().getId());
				query.appendWhere(sc, new int[] { idx });
				query.appendAnd();

				sc = new SearchCondition(HistoryMasterLink.class, "roleAObjectRef.key.id", "=",
						line.getPersistInfo().getObjectIdentifier().getId());
				query.appendWhere(sc, new int[] { idx });

				QueryResult result = PersistenceHelper.manager.find(query);
				if (result.size() == 0) {
					HistoryMasterLink link = HistoryMasterLink.newHistoryMasterLink(line, mastered);
					PersistenceHelper.manager.save(link);
				}
			} else {

				int idx = query.appendClassList(ApprovalHistoryLink.class, true);
				int idx_p = query.appendClassList(Persistable.class, false);
				SearchCondition sc = new SearchCondition(ApprovalHistoryLink.class, "roleBObjectRef.key.id",
						Persistable.class, "thePersistInfo.theObjectIdentifier.id");
				query.appendWhere(sc, new int[] { idx, idx_p });
				query.appendAnd();

				sc = new SearchCondition(ApprovalHistoryLink.class, "roleBObjectRef.key.id", "=",
						per.getPersistInfo().getObjectIdentifier().getId());
				query.appendWhere(sc, new int[] { idx });
				query.appendAnd();

				sc = new SearchCondition(ApprovalHistoryLink.class, "roleAObjectRef.key.id", "=",
						line.getPersistInfo().getObjectIdentifier().getId());
				query.appendWhere(sc, new int[] { idx });

				QueryResult result = PersistenceHelper.manager.find(query);
				if (result.size() == 0) {
					ApprovalHistoryLink link = ApprovalHistoryLink.newApprovalHistoryLink(line, per);
					PersistenceHelper.manager.save(link);
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
	public void save(Map<String, Object> params) throws Exception {
		ArrayList<String> exams = (ArrayList<String>) params.get("exam");
		ArrayList<String> vias = (ArrayList<String>) params.get("via");
		ArrayList<String> receives = (ArrayList<String>) params.get("receive");
		ArrayList<String> opens = (ArrayList<String>) params.get("open");
		String appUser = (String) params.get("appUser");
		String oid = (String) params.get("oid");
		String conn = (String) params.get("conn"); // 0 작성 1,2,3,4, 5 승인
		String appLimit = (String) params.get("appLimit");
		ApprovalMaster master = null;
		Transaction trs = new Transaction();
		try {
			trs.start();

			Persistable per = CommonUtils.persistable(oid);

			for (int i = 0; opens != null && i < opens.size(); i++) {
				String o = (String) opens.get(i);
				WTUser u = (WTUser) CommonUtils.persistable(o);
				OpenPersistableLink link = OpenPersistableLink.newOpenPersistableLink(u, per);
				PersistenceHelper.manager.save(link);
			}

			String name = ApprovalHelper.manager.getName(per);
			Ownership ownership = Ownership.newOwnership(SessionHelper.manager.getPrincipal());

			master = ApprovalMaster.newApprovalMaster();
			master.setName(name);
			master.setCompleteTime(null);
			master.setOwnership(ownership); // 등록자 = 기안자
			master.setPersist(per);
			master.setStartTime(DateUtils.today());
			master.setObjType(ApprovalHelper.manager.getObjType(per));
			// 모든상태는 결재중 경유는 없다..
			master.setState(ApprovalHelper.LINE_APPROVING);
			master = (ApprovalMaster) PersistenceHelper.manager.save(master);

			WTUser submitUser = (WTUser) SessionHelper.manager.getPrincipal();
			LatestApprovalLine latest = LatestApprovalLine.newLatestApprovalLine(submitUser, master);
			Class clazz = Class.forName("platform.approval.entity._LatestApprovalLine");
			int idx = 1;
			for (int i = 0; exams != null && i < exams.size(); i++) {
				Method method = clazz.getDeclaredMethod("setIndex" + idx, String.class);
				method.invoke(latest, (String) exams.get(i));
				idx++;
			}
			latest.setIndex5(appUser);
			PersistenceHelper.manager.save(latest);

			ApprovalLine startLine = ApprovalLine.newApprovalLine();
			startLine.setOwnership(ownership);
			startLine.setMaster(master);
			startLine.setSort(-50);
			startLine.setUser((WTUser) SessionHelper.manager.getPrincipal());
			startLine.setStartTime(DateUtils.today());
			startLine.setLineType(ApprovalHelper.SUBMIT_LINE);
			startLine.setRole(ApprovalHelper.SUBMIT_ROLE);
			startLine.setDescription(ownership.getOwner().getFullName() + " 사용자가 결재를 제출하였습니다.");
			startLine.setState(ApprovalHelper.LINE_SUBMIT_COMPLETE);
			startLine.setCompleteTime(DateUtils.today());
			startLine = (ApprovalLine) PersistenceHelper.manager.save(startLine);
			saveHistory(startLine, per);
			if (vias.size() == 0) {
				// 경유가 없을 경우
				int sort = 0;
				for (int i = 0; i < exams.size(); i++) {
					String woid = (String) exams.get(i);
					WTUser user = (WTUser) CommonUtils.persistable(woid);
					ApprovalLine appLine = ApprovalLine.newApprovalLine();
					appLine.setUser(user);
					appLine.setOwnership(Ownership.newOwnership(user));
					appLine.setCompleteTime(null);
					appLine.setDescription("");
					appLine.setMaster(master);
					appLine.setUser(user);
					appLine.setRole(ApprovalHelper.EXAM_ROLE);
					appLine.setSort(sort);
					appLine.setLineType(ApprovalHelper.EXAM_LINE);

					if (i == 0) {
						appLine.setStartTime(DateUtils.today());
						appLine.setState(ApprovalHelper.LINE_EXAM);

						// 승인중 메세지 전달
						MessageHelper.service.create(
								submitUser.getFullName() + "이(가) " + master.getName() + " 결재를 기안하였습니다.",
								Ownership.newOwnership(user));
					} else {
						appLine.setStartTime(null);
						appLine.setState(ApprovalHelper.LINE_STAND);
					}
					appLine = (ApprovalLine) PersistenceHelper.manager.save(appLine);
					saveHistory(appLine, per);
					sort++;
				}
			} else {
				// 경우가 있으면서 작성 뒤에 일경우..
				if ("-1".equals(conn)) {
					int sort = 0;
					for (String s : vias) {
						WTUser user = (WTUser) CommonUtils.persistable(s);
						ApprovalLine appLine = ApprovalLine.newApprovalLine();
						appLine.setUser(user);
						appLine.setOwnership(Ownership.newOwnership(user));
						appLine.setCompleteTime(null);
						appLine.setDescription("");
						appLine.setMaster(master);
						appLine.setUser(user);
						appLine.setLimit(DateUtils.startTimestamp(appLimit));
						appLine.setRole(ApprovalHelper.VIA_ROLE);
						appLine.setLineType(ApprovalHelper.VIA_LINE);
						appLine.setStartTime(DateUtils.today());
						appLine.setState(ApprovalHelper.LINE_VIA_STAND);
						// 승인중 메세지 전달
						appLine.setSort(sort);
						MessageHelper.service.create(
								submitUser.getFullName() + "이(가) " + master.getName() + " 결재를 기안하였습니다.",
								Ownership.newOwnership(user));
						appLine = (ApprovalLine) PersistenceHelper.manager.save(appLine);
						saveHistory(appLine, per);
					}
					// 나머지 결재선 생성..
					sort++;
					for (int i = 0; i < exams.size(); i++) {
						String woid = (String) exams.get(i);
						WTUser user = (WTUser) CommonUtils.persistable(woid);
						ApprovalLine appLine = ApprovalLine.newApprovalLine();
						appLine.setUser(user);
						appLine.setOwnership(Ownership.newOwnership(user));
						appLine.setCompleteTime(null);
						appLine.setDescription("");
						appLine.setMaster(master);
						appLine.setUser(user);
						appLine.setRole(ApprovalHelper.EXAM_ROLE);
						appLine.setSort(sort);
						appLine.setLineType(ApprovalHelper.EXAM_LINE);
						appLine.setStartTime(null);
						appLine.setState(ApprovalHelper.LINE_STAND);
						appLine = (ApprovalLine) PersistenceHelper.manager.save(appLine);
						sort++;
						saveHistory(appLine, per);
					}
					// 모든 결재 대기상태로..
				} else {
					int sort = 0;
					for (int i = 0; i < exams.size(); i++) {
						String woid = (String) exams.get(i);
						WTUser user = (WTUser) CommonUtils.persistable(woid);
						ApprovalLine appLine = ApprovalLine.newApprovalLine();
						appLine.setUser(user);
						appLine.setOwnership(Ownership.newOwnership(user));
						appLine.setCompleteTime(null);
						appLine.setDescription("");
						appLine.setMaster(master);
						appLine.setUser(user);
						appLine.setRole(ApprovalHelper.EXAM_ROLE);
						appLine.setSort(sort);
						appLine.setLineType(ApprovalHelper.EXAM_LINE);

						if (i == 0) {
							appLine.setStartTime(DateUtils.today());
							appLine.setState(ApprovalHelper.LINE_EXAM);

							// 승인중 메세지 전달
							MessageHelper.service.create(
									submitUser.getFullName() + "이(가) " + master.getName() + " 결재를 기안하였습니다.",
									Ownership.newOwnership(user));
						} else {
							appLine.setStartTime(null);
							appLine.setState(ApprovalHelper.LINE_STAND);
						}
						appLine = (ApprovalLine) PersistenceHelper.manager.save(appLine);
						saveHistory(appLine, per);
						if (i == Integer.parseInt(conn)) {
							sort++;
							for (String s : vias) {
								WTUser viaUser = (WTUser) CommonUtils.persistable(s);
								ApprovalLine viaLine = ApprovalLine.newApprovalLine();
								viaLine.setUser(viaUser);
								viaLine.setOwnership(Ownership.newOwnership(viaUser));
								viaLine.setCompleteTime(null);
								viaLine.setDescription("");
								viaLine.setMaster(master);
								viaLine.setUser(viaUser);
								viaLine.setRole(ApprovalHelper.VIA_ROLE);
								viaLine.setLineType(ApprovalHelper.VIA_LINE);
								viaLine.setStartTime(null);
								viaLine.setState(ApprovalHelper.LINE_STAND);
								// 승인중 메세지 전달
								viaLine.setSort(sort);
								viaLine.setLimit(DateUtils.startTimestamp(appLimit));
								viaLine = (ApprovalLine) PersistenceHelper.manager.save(viaLine);
								saveHistory(viaLine, per);
							}
						}
						sort++;
					}
				}
			}

			WTUser user = (WTUser) CommonUtils.persistable(appUser);
			ApprovalLine appLine = ApprovalLine.newApprovalLine();
			appLine.setUser(user);
			appLine.setOwnership(Ownership.newOwnership(user));
			appLine.setCompleteTime(null);
			appLine.setDescription("");
			appLine.setMaster(master);
			appLine.setUser(user);
			appLine.setRole(ApprovalHelper.APP_ROLE);
//			appLine.setSort(sort);
			appLine.setLineType(ApprovalHelper.APP_LINE);
			if ((exams == null || exams.size() == 0) && (vias == null || vias.size() == 0)
					&& (receives == null || receives.size() == 0)) {
				appLine.setStartTime(DateUtils.today());
				appLine.setState(ApprovalHelper.LINE_APPROVING);
			} else {
				appLine.setStartTime(null);
				appLine.setState(ApprovalHelper.LINE_STAND);
			}
			appLine = (ApprovalLine) PersistenceHelper.manager.save(appLine);
			saveHistory(appLine, per);
			// 수신자처리 결재 이어가기 위함.. 수신은 한번으로 끝인가???
//			if (StringUtils.isNotNull(receive)) {
			for (String receive : receives) {
				WTUser receiveUser = (WTUser) CommonUtils.persistable(receive);
				ApprovalLine receiveLine = ApprovalLine.newApprovalLine();
				receiveLine.setUser(receiveUser);
				receiveLine.setOwnership(Ownership.newOwnership(receiveUser));
				receiveLine.setCompleteTime(null);
				receiveLine.setDescription("");
				receiveLine.setMaster(master);
				receiveLine.setUser(receiveUser);
				receiveLine.setRole(ApprovalHelper.RECEIVE_ROLE);
				receiveLine.setLineType(ApprovalHelper.RECEIVE_LINE);
				receiveLine.setStartTime(null);
				receiveLine.setState(ApprovalHelper.LINE_STAND);
				receiveLine = (ApprovalLine) PersistenceHelper.manager.save(receiveLine);
				saveHistory(receiveLine, per);
				ReceiveObjectLink link = ReceiveObjectLink.newReceiveObjectLink(receiveUser, (WTObject) per);
				PersistenceHelper.manager.save(link);
			}

			if (per instanceof LifeCycleManaged) {
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) per, State.toState("INAPPROVE"));
			} else {
				if (per instanceof ECR) {
					ECR ecr = (ECR) per;
					ecr.setState("승인중");
					PersistenceHelper.manager.modify(ecr);
				}

				if (per instanceof ECO) {
					ECO eco = (ECO) per;
					eco.setState("승인중");
					PersistenceHelper.manager.modify(eco);
					ECOHelper.service.preAction(eco);
				}

				if (per instanceof ECN) {
					ECN ecn = (ECN) per;
					ecn.setState("승인중");
					PersistenceHelper.manager.modify(ecn);
				}

				if (per instanceof Dist) {
					Dist dist = (Dist) per;
					dist.setState("승인중");
					PersistenceHelper.manager.modify(dist);
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
	public void receiveSave(Map<String, Object> params) throws Exception {
		ArrayList<String> exams = (ArrayList<String>) params.get("exam");
		String appUser = (String) params.get("appUser");
		String oid = (String) params.get("oid");
		String line = (String) params.get("line");
		ApprovalMaster master = null; // 기존마스터에 이어 갈지 다시 새로 갈지..
		Transaction trs = new Transaction();
		try {
			trs.start();

			Persistable per = CommonUtils.persistable(oid);

			String name = ApprovalHelper.manager.getName(per);
			Ownership ownership = Ownership.newOwnership(SessionHelper.manager.getPrincipal());

			master = ApprovalMaster.newApprovalMaster();
			master.setName(name);
			master.setCompleteTime(null);
			master.setOwnership(ownership); // 등록자 = 기안자
			master.setPersist(per);
			master.setStartTime(DateUtils.today());
			master.setObjType(ApprovalHelper.manager.getObjType(per));
			// 모든상태는 결재중 경유는 없다..
			master.setState(ApprovalHelper.LINE_APPROVING);
			master = (ApprovalMaster) PersistenceHelper.manager.save(master);

			ApprovalLine startLine = ApprovalLine.newApprovalLine();
			startLine.setOwnership(ownership);
			startLine.setMaster(master);
			startLine.setSort(-50);
			startLine.setUser((WTUser) SessionHelper.manager.getPrincipal());
			startLine.setStartTime(DateUtils.today());
			startLine.setLineType(ApprovalHelper.SUBMIT_LINE);
			startLine.setRole(ApprovalHelper.SUBMIT_ROLE);
			startLine.setDescription(ownership.getOwner().getFullName() + " 사용자가 결재를 제출하였습니다.");
			startLine.setState(ApprovalHelper.LINE_SUBMIT_COMPLETE);
			startLine.setCompleteTime(DateUtils.today());
			startLine = (ApprovalLine) PersistenceHelper.manager.save(startLine);
			saveHistory(startLine, per);

			// 기존 결재 상태 변경
			ApprovalLine receiveLine = (ApprovalLine) CommonUtils.persistable(line);
			receiveLine.setState(ApprovalHelper.LINE_RECEIVE_COMPLETE);
			receiveLine.setCompleteTime(DateUtils.today());
			PersistenceHelper.manager.modify(receiveLine);

			int sort = 0;
			for (int i = 0; exams != null && i < exams.size(); i++) {
				String woid = (String) exams.get(i);
				WTUser user = (WTUser) CommonUtils.persistable(woid);
				ApprovalLine appLine = ApprovalLine.newApprovalLine();
				appLine.setUser(user);
				appLine.setOwnership(Ownership.newOwnership(user));
				appLine.setCompleteTime(null);
				appLine.setDescription("");
				appLine.setMaster(master);
				appLine.setUser(user);
				appLine.setRole(ApprovalHelper.EXAM_ROLE);
				appLine.setSort(sort);
				appLine.setLineType(ApprovalHelper.EXAM_LINE);

				if (i == 0) {
					appLine.setStartTime(DateUtils.today());
					appLine.setState(ApprovalHelper.LINE_EXAM);

					// 승인중 메세지 전달
					WTUser submitUser = (WTUser) SessionHelper.manager.getPrincipal();
					MessageHelper.service.create(
							submitUser.getFullName() + "이(가) " + master.getName() + " 결재를 기안하였습니다.",
							Ownership.newOwnership(user));
				} else {
					appLine.setStartTime(null);
					appLine.setState(ApprovalHelper.LINE_STAND);
				}
				appLine = (ApprovalLine) PersistenceHelper.manager.save(appLine);
				saveHistory(appLine, per);
				sort++;
			}

			// 신규 결재 라인생성
			WTUser user = (WTUser) CommonUtils.persistable(appUser);
			ApprovalLine appLine = ApprovalLine.newApprovalLine();
			appLine.setUser(user);
			appLine.setOwnership(Ownership.newOwnership(user));
			appLine.setCompleteTime(null);
			appLine.setDescription("수신완료 처리 후 결재 기안 완료.");
			appLine.setMaster(master);
			appLine.setUser(user);
			appLine.setRole(ApprovalHelper.APP_ROLE);
//			appLine.setSort(sort);
			appLine.setLineType(ApprovalHelper.APP_LINE);
			if (exams == null || exams.size() == 0) {
				appLine.setStartTime(DateUtils.today());
				appLine.setState(ApprovalHelper.LINE_APPROVING);
			} else {
				appLine.setStartTime(null);
				appLine.setState(ApprovalHelper.LINE_STAND);
			}
			appLine = (ApprovalLine) PersistenceHelper.manager.save(appLine);
			saveHistory(appLine, per);

			// 승인중 메세지 전달
			WTUser submitUser = (WTUser) SessionHelper.manager.getPrincipal();
			MessageHelper.service.create(submitUser.getFullName() + "이(가) " + master.getName() + " 결재를 기안하였습니다.",
					Ownership.newOwnership(user));

			// 객체 상태 변경

			if (per instanceof LifeCycleManaged) {
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) per, State.toState("INAPPROVE"));
			} else {
				if (per instanceof ECR) {
					ECR ecr = (ECR) per;
					ecr.setState("승인중");
					PersistenceHelper.manager.modify(ecr);
				}

				if (per instanceof ECO) {
					ECO eco = (ECO) per;
					eco.setState("승인중");
					PersistenceHelper.manager.modify(eco);
				}

				if (per instanceof ECN) {
					ECN ecn = (ECN) per;
					ecn.setState("승인중");
					PersistenceHelper.manager.modify(ecn);
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
