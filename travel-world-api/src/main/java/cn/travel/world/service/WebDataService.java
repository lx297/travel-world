package cn.travel.world.service;

import java.util.List;

import cn.travel.world.request.QueryDetailsReq;
import cn.travel.world.request.QueryListReq;

public interface WebDataService {
	static final int PAGE_LIMIT=10;
	/**
	 * 获取搜索数据列表
	 * @return
	 */
	List<Object> getDataList(QueryListReq req);
	/**
	 * 文章详情 div
	 * @param urlId
	 * @return
	 */
	Object getDataDetail(QueryDetailsReq req);

	
}
