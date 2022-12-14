package platform.approval.service;

import java.util.ArrayList;
import java.util.Map;

import platform.approval.entity.ApprovalLine;
import wt.fc.Persistable;

public interface ApprovalService {

	public void submit(Persistable per, ArrayList<String> appOid, ArrayList<String> refOid, String appLimit)
			throws Exception;

	public void rtn(Map<String, Object> params) throws Exception;

	public void approval(Map<String, Object> params) throws Exception;

	public void confirm(Map<String, Object> params) throws Exception;

	public void saveHistory(ApprovalLine line, Persistable per) throws Exception;

	public void save(Map<String, Object> params) throws Exception;

	public void receiveSave(Map<String, Object> params) throws Exception;

}
