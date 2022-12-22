package platform.util.service;

import platform.util.entity.CPCHistory;
import wt.fc.PersistenceHelper;
import wt.method.RemoteAccess;
import wt.services.ServiceFactory;

public class CPCHistoryHelper implements RemoteAccess {

	public static final UtilService service = ServiceFactory.getService(UtilService.class);
	public static final CPCHistoryHelper manager = new CPCHistoryHelper();
	public static final String company ="COMPANY";
	public static final String company_user ="COMPANY_USER";
	public static final String dist ="DIST";
	
	public static void createCPCHistory(String oid, String module, String sendQuery) throws Exception {
		
		CPCHistory hi = CPCHistory.newCPCHistory();
		hi.setTargetOid(oid);
		hi.setModuleName(module);
		hi.setSendQuery(sendQuery);
		
		PersistenceHelper.manager.save(hi);
		
	}
	
	
}
