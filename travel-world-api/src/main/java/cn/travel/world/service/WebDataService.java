package cn.travel.world.service;

import java.util.List;

import cn.travel.world.request.ArticleDescReq;
import cn.travel.world.response.ArticleDescResp;

public interface WebDataService {
	/**
	 * 获取搜索数据列表
	 * @return
	 */
	List<ArticleDescResp> getDataList(ArticleDescReq search);
	/**
	 * 文章详情 div
	 * @param urlId
	 * @return
	 */
	String getDataDetail(String urlId);

	
}
