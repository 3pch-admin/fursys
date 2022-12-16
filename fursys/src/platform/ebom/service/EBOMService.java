package platform.ebom.service;

import java.util.ArrayList;
import java.util.Map;

import platform.ebom.entity.EBOM;
import platform.ebom.vo.BOMTreeNode;
import wt.method.RemoteInterface;

@RemoteInterface
public interface EBOMService {

	public EBOM create(Map<String, Object> params) throws Exception;

	public void saveTree(EBOM parent, ArrayList<BOMTreeNode> children) throws Exception;

	public EBOM modify(Map<String, Object> params) throws Exception;

	public void delete(String oid) throws Exception;

	public void confirm(String oid) throws Exception;
}
