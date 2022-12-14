package platform.message.service;

import wt.method.RemoteInterface;
import wt.ownership.Ownership;

@RemoteInterface
public interface MessageService {

	public void create(String description, Ownership ownership) throws Exception;
}
