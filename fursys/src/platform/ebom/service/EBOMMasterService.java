package platform.ebom.service;

import java.util.ArrayList;
import java.util.Map;

import platform.ebom.entity.EBOMMaster;
import platform.ebom.vo.BOMTreeNode;
import wt.method.RemoteInterface;

@RemoteInterface
public interface EBOMMasterService {

	public void create(Map<String, Object> params) throws Exception;

	public EBOMMaster modify(Map<String, Object> params) throws Exception;

	public EBOMMaster partlist(Map<String, Object> params) throws Exception;

	public void color(Map<String, Object> params) throws Exception;

	public EBOMMaster derived(Map<String, Object> params) throws Exception;

	public EBOMMaster delete(String oid) throws Exception;

	public EBOMMaster save(Map<String, Object> params) throws Exception;

	public void saveTree(EBOMMaster parent, ArrayList<BOMTreeNode> childrens, int sort) throws Exception;
}
