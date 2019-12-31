package cn.travel.world.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import cn.travel.world.request.QueryDetailsReq;
import cn.travel.world.request.QueryListReq;
import cn.travel.world.util.KK;

@Service
public class DCGWebDataService  {
	public static final String USER_LIST_URL = "http://dcone.portal.digitalchina.com/hrm/search/CustomizeHrmResourceSearchResult.jsp";

	public List<Object> getDataList(QueryListReq req) {
		List<Object> respList = new ArrayList<>();
		String html = KK.http.sendGet(USER_LIST_URL+"?page="+req.getPage());
		Document parse = Jsoup.parse(html);
		Elements user = parse.select("div.search-peop-alls div.search-peop.clearfloat");
		for (Element u : user) {
			String info= u.select(".search-peop-mid-rig-info1").text()+"	";
			info+= u.select(".search-peop-mid-rig ul li").get(1).text()+"	";
			info+= u.select(".search-peop-mid-rig ul li").get(2).text()+"	";
			info+= u.select("span.search-peop-mid-rig-info5").text().replace("人员档案", "")+"	";
			info+= u.select("div.search-peop-middle").text()+"	";
			info+= u.select(".pt5 strong").text()+"	";
			info+= u.select(".search-peop-mid-rig-sp2 strong").text()+"	";
			info+= u.select(".search-peop-mid-rig ul li").last().select("strong").text();
			System.out.println(info);
//			System.out.println(u.text().replaceAll(" 人员档案| 座 机：| 手 机：", ""));
			KK.file.fileLinesWrite("data/DCG_USER_INFO.txt", info, true);
		}
		return respList;
	}

	public String getDataDetail(QueryDetailsReq req) {
		return null;
	}

	public static void main(String[] args)  {
		DCGWebDataService dcgWebDataService = new DCGWebDataService();
		QueryListReq queryListReq = new QueryListReq();
		for (int i=1 ; i<7668;i++) {
			 dcgWebDataService.getDataList(queryListReq.setPage(i));
		}
	}

}
