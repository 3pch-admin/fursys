package platform.util.service;

import java.util.ArrayList;

import platform.dist.entity.Distributor;
import platform.util.entity.CPCHistory;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.ownership.Ownership;
import wt.pom.Transaction;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceFactory;
import wt.session.SessionHelper;

public class CPCHistoryHelper implements RemoteAccess {

	public static final UtilService service = ServiceFactory.getService(UtilService.class);
	public static final CPCHistoryHelper manager = new CPCHistoryHelper();
	public static final String company ="COMPANY";
	public static final String company_user ="COMPANY_USER";
	public static final String dist ="DIST";
	
	public static void createCPCHistory(String oid, String module, String sendQuery) throws Exception {
		
		Transaction trs = new Transaction();
		try {
			trs.start();
			CPCHistory hi = CPCHistory.newCPCHistory();
			hi.setTargetOid(oid);
			hi.setModuleName(module);
			hi.setSendQuery(sendQuery);
			hi.setOwnership(Ownership.newOwnership(SessionHelper.manager.getPrincipal()));
			PersistenceHelper.manager.save(hi);
			
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
	
	public ArrayList<CPCHistory> getCPCHistory(String oid) throws Exception {
		ArrayList<CPCHistory> list = new ArrayList<CPCHistory>();

		//select * from cpchis where targetoid='oid';
		
		
		QuerySpec qs = new QuerySpec();
		int idx = qs.appendClassList(CPCHistory.class, true);
		
		qs.appendWhere(new SearchCondition(CPCHistory.class, CPCHistory.TARGET_OID, "=", oid ), new int[] { idx });

		ClassAttribute ca = new ClassAttribute(CPCHistory.class, Distributor.CREATE_TIMESTAMP);
		OrderBy by = new OrderBy(ca, true);
		qs.appendOrderBy(by, new int[] { idx });

		QueryResult result = PersistenceHelper.manager.find(qs);

		while (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			CPCHistory cpcHistory = (CPCHistory) obj[0];

			list.add(cpcHistory);
		}

		return list;
	}
	
	
}
