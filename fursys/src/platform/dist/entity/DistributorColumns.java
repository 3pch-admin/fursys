package platform.dist.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import platform.code.service.BaseCodeHelper;
import platform.dist.service.DistributorHelper;
import platform.util.CommonUtils;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;

@Getter
@Setter
public class DistributorColumns {

	private String oid;
	private int no;
	private String name;
	private String number;
	private String userId;
	private String userName;
	private String email;
	private String description;
	private String creator;
	private String createdDate;
	private String type;
	private String enable;
	private ArrayList<DistributorUserColumns> diUsers;
	private String diUserOids;
	
	public DistributorColumns() {

	}

	public DistributorColumns(Distributor distributor) throws Exception {
		setOid(CommonUtils.oid(distributor));

		setName(distributor.getName());
		setNumber(distributor.getNumber());
		setDescription(distributor.getDescription());
		setCreatedDate(distributor.getCreateTimestamp().toString().substring(0, 10));
		setCreator(distributor.getOwnership().getOwner().getFullName());
		setType("IN".equals(distributor.getType()) == true ? "사내" : "사외");
		setEnable(distributor.getEnable() ? "사용" : "사용안함");
		setUserId(distributor.getUserId());
		setUserName(distributor.getUserName());
		setEmail(distributor.getEmail());
		
		ArrayList<DistributorUser> users = DistributorHelper.manager.getDistributorUser(distributor);
		String asd = "";
		
		for(DistributorUser diu : users) {
			asd += CommonUtils.oid(diu)+"▒";
		}
		
		setDiUserOids(asd);
		
		
		setDiUsers(DistributorHelper.manager.getDistributorUserColumns2(distributor));
		
	}
}
