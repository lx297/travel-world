package cn.travel.world.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.travel.world.request.QueryDetailsReq;
import cn.travel.world.request.QueryListReq;
import cn.travel.world.response.DescListResp;
import cn.travel.world.statics.PageBean;
import cn.travel.world.util.KK;

@Service
public class DouBanWebDataService {
	private static final String MOVIES_LIST_URL = "https://m.douban.com/search/?query=%s&type=movie&p=%s";
	private static final String MOVIES_DETAILS_URL = "https://m.douban.com/rexxar/api/v2/movie/%s/interests?count=%s&order_by=hot&start=%s&ck=&for_mobile=1";

	public List<Object> getDataList(QueryListReq req) {
		String html= KK.http.sendGet(String.format(MOVIES_LIST_URL, req.getKey(),req.getPage()));
		Document parse = Jsoup.parse(html);
		Elements elements = parse.select("ul.search_results_subjects > li > a");
		List<Object> respList=new ArrayList<>();
		for (Element e : elements) {
			DescListResp descResp = new DescListResp();
			descResp.setId(e.attr("href").replaceAll("/movie/subject/(\\d+)/", "$1"));
			descResp.setTitle(e.select("img > div > span.subject-title").text());
			descResp.setPic(e.select("img[src]").attr("src"));
//			descResp.setReply(e.select("dl > dd > div.post-info > div > span.reply").text());
//			descResp.setView(e.select("dl > dd > div.post-info > div > span.view").text());
//			descResp.setAuthorPic(e.select("dl > dd > div.post-info > div.author >img[src]").attr("src"));
			respList.add(descResp);
		}
		return respList;
	}

	public Object getDataDetail(QueryDetailsReq req) {
		if(req.getPage()==0)req.setPage(1);
		Map<String,String > input=new HashMap<>();
		input.put("Referer", "https://m.douban.com");
		String json= KK.http.sendGet(String.format(MOVIES_DETAILS_URL,req.getId(),PageBean.PAGE_LIMIT,PageBean.PAGE_LIMIT*(req.getPage()-1)),input);
		JSONArray jsonObj=JSONObject.parseObject(json).getJSONArray("interests");
		System.out.println(jsonObj);
		return jsonObj;
	}
public static void main(String[] args) {
	QueryDetailsReq req=new QueryDetailsReq();
	req.setPage(1);
	req.setId("3878007");
	new DouBanWebDataService().getDataDetail(req);
}
}
