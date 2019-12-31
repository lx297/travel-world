package cn.travel.world.request;

public class QueryListReq {
	private String key;
	private String type;
	private int page=1;
	public String getKey() {
		return key;
	}
	public QueryListReq setKey(String key) {
		this.key = key;
		return this;
	}
	public String getType() {
		return type;
	}
	public QueryListReq setType(String type) {
		this.type = type;
		return this;
	}
	public int getPage() {
		return page;
	}
	public QueryListReq setPage(int page) {
		this.page = page;
		return this;
	}
	
	
}
