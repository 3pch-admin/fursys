package platform.util;

import java.util.HashMap;
import java.util.Map;

import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.query.QuerySpec;

public class PageUtils {

	private int psize = 30;
	private int cpage = 1;
	private int total = 0;
	private int topListCount = 1;
	private long sessionid = 0L;

	private Map<String, Object> params = new HashMap<String, Object>();
	private QuerySpec query = null;
	private PagingQueryResult result = null;

	public PageUtils(Map<String, Object> params, QuerySpec query) throws Exception {
		this.params = params;
		String sessionid = (String) this.params.get("sessionid");
		if (!StringUtils.isNotNull(sessionid)) {
			sessionid = "0";
		}
		this.query = query;
		this.sessionid = Long.parseLong(sessionid);
	}

	public static PagingQueryResult openPagingSession(int i, int j, QuerySpec query) throws Exception {
		return PagingSessionHelper.openPagingSession(i, j, query);
	}

	public static PagingQueryResult fetchPagingSession(int i, int j, long sessionid) throws Exception {
		return PagingSessionHelper.fetchPagingSession(i, j, sessionid);
	}

	public PagingQueryResult find() throws Exception {
		String cpage = (String) this.params.get("tpage");
		if (!StringUtils.isNotNull(cpage)) {
			cpage = "1";
		}

		String psize = (String) this.params.get("psize");
		if (!StringUtils.isNotNull(psize)) {
			psize = "25";
		}

		this.cpage = Integer.parseInt(cpage);
		this.psize = Integer.parseInt(psize);

		if (this.sessionid <= 0) {
			this.result = openPagingSession(0, this.psize, this.query);
		} else {
			this.result = fetchPagingSession((this.cpage - 1) * this.psize, this.psize, this.sessionid);
		}

		if (this.result != null) {
			this.total = this.result.getTotalSize();
			this.sessionid = this.result.getSessionId();
			this.topListCount = this.total - ((this.cpage - 1) * this.psize);
		}
		return this.result;
	}

	public int getPsize() {
		return this.psize;
	}

	public int getTotal() {
		return this.topListCount;
	}

	public int getTotalSize() {
		return this.total;
	}

	public int getCpage() {
		return this.cpage;
	}

	public long getSessionId() {
		return this.sessionid;
	}
}
