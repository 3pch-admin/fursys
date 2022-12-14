package platform.project.output.service;

import java.util.Map;

import platform.project.output.entity.Output;
import wt.doc.WTDocument;
import wt.method.RemoteInterface;

@RemoteInterface
public interface OutputService {

	public Output create(Map<String, Object> params) throws Exception;

	public void delete(Map<String, Object> params) throws Exception;

	public void disconnect(String oid) throws Exception;

	public void connect(Map<String, Object> params) throws Exception;

	public WTDocument direct(Map<String, Object> params) throws Exception;

	public void modify(Map<String, Object> params) throws Exception;
}
