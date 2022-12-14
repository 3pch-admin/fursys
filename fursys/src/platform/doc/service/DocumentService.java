package platform.doc.service;

import platform.doc.entity.DocumentDTO;
import wt.doc.WTDocument;
import wt.method.RemoteInterface;

@RemoteInterface
public interface DocumentService {

	public WTDocument create(DocumentDTO params) throws Exception;

	public WTDocument modify(DocumentDTO params) throws Exception;

	public WTDocument delete(String oid) throws Exception;

	public WTDocument revise(DocumentDTO params) throws Exception;
}
