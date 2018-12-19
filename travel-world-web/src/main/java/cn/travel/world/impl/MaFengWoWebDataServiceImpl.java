package cn.travel.world.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.travel.world.request.QueryDetailsReq;
import cn.travel.world.request.QueryListReq;
import cn.travel.world.response.DescListResp;
import cn.travel.world.service.WebDataService;
import cn.travel.world.util.KK;

@Service
public class MaFengWoWebDataServiceImpl implements WebDataService {
	private static final String ART_LIST_URL = "https://m.mafengwo.cn/group/s.php?type=1&page=%s&limit=%s&key=%s";
	private static final String ART_DETAILS_URL = "https://m.mafengwo.cn/i/%s.html";
	private static final String ART_DETAILS_URL_NEXT = "https://m.mafengwo.cn/note/note/contentChunk?id=%s&iid=%s&back=0";

	@Override
	public List<Object> getDataList(QueryListReq  req) {
 		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(String.format(ART_LIST_URL, req.getPage(), PAGE_LIMIT, req.getKey())), 5000);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Object> respList = new ArrayList<>();
		Elements select = doc.select("section.articles > article.article > a");
		for (Element e : select) {
			DescListResp descResp = new DescListResp();
			descResp.setId(e.attr("href").replaceAll("/i/(\\d+).html", "$1"));
			descResp.setTitle(e.select("dl > dd > h2").text());
			descResp.setPic(e.select("dl > dt > img[src]").attr("src"));
			descResp.setReply(e.select("dl > dd > div.post-info > div > span.reply").text());
			descResp.setView(e.select("dl > dd > div.post-info > div > span.view").text());
			descResp.setAuthorPic(e.select("dl > dd > div.post-info > div.author >img[src]").attr("src"));
			respList.add(descResp);
		}
		return respList;
	}

	@Override
	public String getDataDetail(QueryDetailsReq req) {
		try {
			Document doc = Jsoup.parse(new URL(String.format(ART_DETAILS_URL, req.getId())), 5000);
			Matcher m = Pattern.compile("\"new_iid\":\"(\\d*)\"").matcher(doc.toString());
			String newId = "0";
			while (m.find()) {
				newId = m.group(1);
			}
			// "new_iid":"305418636"
			Elements mainDiv = doc.select("div.vi_con");
			mainDiv.addAll(doc.select("div._j_content_box"));
			String json = KK.http.sendGet(String.format(ART_DETAILS_URL_NEXT, newId, req.getId()));
			JSONObject data = JSONObject.parseObject(json).getJSONObject("data");
			mainDiv.append(data.getString("html"));
			mainDiv.select("a[href]").attr("href", "javascript:void(0);");
			mainDiv.select("img._j_lazyload._j_needInitShare").attr("style","width:50%");
			Elements ss = mainDiv.select("img[src^=data:]");
			for (Element element : ss) {
				element.attr("src",element.attr("data-src"));
			}
			System.out.println(mainDiv);
			return mainDiv.toString();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

}
