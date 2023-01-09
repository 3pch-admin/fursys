package platform.util;

import java.util.Enumeration;

import platform.code.entity.BaseCode;
import platform.user.entity.User;
import platform.user.entity.UserWTUserLink;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTValuedHashMap;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.inf.library.WTLibrary;
import wt.org.OrganizationServicesHelper;
import wt.org.WTOrganization;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.pdmlink.PDMLinkProduct;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.vc.VersionControlHelper;
import wt.vc.VersionReference;

public class CommonUtils {

	static ReferenceFactory rf = new ReferenceFactory();
	public static final String ADMIN_GROUP = "Administrators";
	public static final String PRODUCT_NAME = "FURSYS";

	private CommonUtils() {

	}

	public static boolean isAdmin() throws Exception {
		return isMember(ADMIN_GROUP);
	}

	public static boolean isMember(String group) throws Exception {
		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
		return isMember(group, user);
	}

	public static boolean isMember(String group, WTUser user) throws Exception {
		Enumeration<?> en = user.parentGroupNames();
		while (en.hasMoreElements()) {
			String name = (String) en.nextElement();
			if (name.equals(group)) {
				return true;
			}
		}
		return false;
	}

	public static String oid(Persistable per) {
		return per.getPersistInfo().getObjectIdentifier().getStringValue();
	}
	
	public static long longValue(Persistable per) {
		return per.getPersistInfo().getObjectIdentifier().getId();
	}

	public static WTContainerRef getContainer(String productName) throws Exception {
		WTOrganization org = OrganizationServicesHelper.manager.getOrganization(SessionHelper.manager.getPrincipal());
		String orgName = org.getName();
		WTContainerRef ref = WTContainerHelper.service
				.getByPath("/wt.inf.container.OrgContainer=" + orgName + "/wt.pdmlink.PDMLinkProduct=" + productName);
		return ref;
	}

	public static WTLibrary getLibraryContext(String libraryName) throws Exception {
		WTLibrary library = null;
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(WTLibrary.class, true);
		SearchCondition sc = new SearchCondition(WTLibrary.class, WTLibrary.NAME, "=", libraryName);
		query.appendWhere(sc, new int[] { idx });
		QueryResult result = PersistenceHelper.manager.find(query);
		if (result.hasMoreElements()) {
			Object[] obj = (Object[]) result.nextElement();
			library = (WTLibrary) obj[0];
		}
		return library;
	}

	public static PDMLinkProduct getPDMLinkProduct(String name) throws Exception {
		QuerySpec query = new QuerySpec();
		int idx = query.appendClassList(PDMLinkProduct.class, true);
		SearchCondition sc = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, "=", name);
		query.appendWhere(sc, new int[] { idx });
		QueryResult results = (QueryResult) PersistenceHelper.manager.find(query);
		PDMLinkProduct wtProduct = null;
		if (results.hasMoreElements()) {
			Object[] obj = (Object[]) results.nextElement();
			wtProduct = (PDMLinkProduct) obj[0];
		}
		return wtProduct;
	}

	public static WTContainerRef getLibrary(String libraryName) throws Exception {
		WTLibrary library = getLibraryContext(libraryName);
		WTContainerRef container = WTContainerRef.newWTContainerRef(library);
		return container;
	}

	public static Persistable persistable(String oid) throws Exception {
		return rf.getReference(oid).getObject();
	}

	public static Persistable getLatestVersion(Persistable persistable) throws Exception {
		WTHashSet set = new WTHashSet();
		set.add(persistable);

		WTValuedHashMap map = (WTValuedHashMap) VersionControlHelper.service.getLatestRevisions(set);
		VersionReference reference = (VersionReference) map.get(persistable);
		return reference.getObject();
	}

	public static String getSessionBrand() throws Exception {
		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
		QueryResult result = PersistenceHelper.manager.navigate(sessionUser, "user", UserWTUserLink.class);
		User user = null;
		if (result.hasMoreElements()) {
			user = (User) result.nextElement();
			return user.getBrand() != null ? user.getBrand().getCode() : "";
		}
		return "";
	}

	public static String getSessionCompany() throws Exception {
		WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
		QueryResult result = PersistenceHelper.manager.navigate(sessionUser, "user", UserWTUserLink.class);
		User user = null;
		if (result.hasMoreElements()) {
			user = (User) result.nextElement();
			BaseCode brand = user.getBrand();
			if (StringUtils.isNotNull(brand)) {
				BaseCode company = brand.getParent();
				if (StringUtils.isNotNull(company)) {
					return company.getCode();
				}
			}
		}
		return "";
	}

	public static Persistable getLatestVersion(String oid) throws Exception {
		ReferenceFactory rf = new ReferenceFactory();
		Persistable persistable = (Persistable) rf.getReference(oid).getObject();
		WTHashSet set = new WTHashSet();
		set.add(persistable);

		WTValuedHashMap map = (WTValuedHashMap) VersionControlHelper.service.getLatestRevisions(set);
		VersionReference reference = (VersionReference) map.get(persistable);
		return reference.getObject();
	}

	public static Ownership ownership() throws Exception {
		WTPrincipal prin = SessionHelper.manager.getPrincipal();
		return Ownership.newOwnership(prin);
	}

	public static double doubleValue(Object obj, double initValue) {
		if (obj != null) {
			try {
				if (obj.getClass().equals(String.class)) {
					return Double.parseDouble((String) obj);
				} else if (obj.getClass().equals(Double.class)) {
					return ((Double) obj).doubleValue();
				} else {
					return Double.parseDouble(obj.toString());
				}
			} catch (NumberFormatException e) {
				// e.printStackTrace();
			}
		}
		return initValue;
	}

	public static double doubleValue(Object obj) {
		return doubleValue(obj, 0d);
	}
}