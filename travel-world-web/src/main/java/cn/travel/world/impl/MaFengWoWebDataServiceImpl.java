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

import cn.travel.world.request.ArticleDescReq;
import cn.travel.world.response.ArticleDescResp;
import cn.travel.world.service.WebDataService;
import cn.travel.world.util.KK;

@Service
public class MaFengWoWebDataServiceImpl implements WebDataService {
	private static final String ART_LIST_URL = "https://m.mafengwo.cn/group/s.php?type=1&page=%s&limit=%s&key=%s";
	private static final String ART_DETAILS_URL = "https://m.mafengwo.cn/i/%s.html";
	private static final String ART_DETAILS_URL_NEXT = "https://m.mafengwo.cn/note/note/contentChunk?id=%s&iid=%s&back=0";

	@Override
	public List<ArticleDescResp> getDataList(ArticleDescReq search) {
		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(String.format(ART_LIST_URL, search.getPage(), search.getLimit(), search.getKey())), 5000);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<ArticleDescResp> respList = new ArrayList<>();
		Elements select = doc.select("section.articles > article.article > a");
		for (Element e : select) {
			ArticleDescResp descResp = new ArticleDescResp();
			descResp.setTitle(e.select("dl > dd > h2").text());
			descResp.setPic(e.select("dl > dt > img[src]").attr("src"));
			descResp.setReply(e.select("dl > dd > div.post-info >span.reply").text());
			descResp.setView(e.select("dl > dd > div.post-info >span.view").text());
			descResp.setAuthorPic(e.select("dl > dd > div.author >img[src]").attr("src"));
			respList.add(descResp);
		}
		return respList;
	}

	@Override
	public String getDataDetail(String urlId) {
		try {
			Document doc = Jsoup.parse(new URL(String.format(ART_DETAILS_URL, urlId)), 5000);
			Matcher m = Pattern.compile("\"new_iid\":\"(\\d*)\"").matcher(doc.toString());
			String newId = "0";
			while (m.find()) {
				newId = m.group(1);
			}
			// "new_iid":"305418636"
			Elements mainDiv = doc.select("div.vi_con");
			mainDiv.addAll(doc.select("div._j_content_box"));
			String json = KK.http.get(String.format(ART_DETAILS_URL_NEXT, newId, urlId));
			JSONObject data = JSONObject.parseObject(json).getJSONObject("data");
			mainDiv.append(data.getString("html"));
			mainDiv.select("a[href]").attr("href", "#");
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

	public static void main(String[] args) {
//		String a=" <a href=\"/travel-scenic-spot/mafengwo/10132.html\" class=\"link _j_keyword_mdd\" data-kw=\"厦门\" target=\"_self\">厦门</a>";
//		System.out.println(a.replaceAll("href=\\\".+?\\\"", "href=\"#\""));
		new MaFengWoWebDataServiceImpl().getDataDetail("8796846");
	}

}
