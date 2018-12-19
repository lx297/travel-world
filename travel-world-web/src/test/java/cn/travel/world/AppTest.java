package cn.travel.world;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.travel.world.response.ArticleDescResp;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) throws Exception {

//		String res= KK.http.get( "https://m.mafengwo.cn/group/s.php?key=天天&type=1");
//		String res= KK.http.get( "https://m.mafengwo.cn/i/8796846.html");
//		Document doc = Jsoup.parse(new URL("https://m.mafengwo.cn/i/8796846.html"), 5000);
		Document doc = Jsoup.parse(new URL("https://m.mafengwo.cn/group/s.php?key=天天&type=1"), 5000);
//		Document doc = Jsoup.parse(new File("Xml-Util.xml"),"UTF-8");
		List<ArticleDescResp> respList=new ArrayList<>();
		Elements select = doc.select("section.articles > article.article > a");
		for (Element e : select) {
			ArticleDescResp descResp=new ArticleDescResp();
			descResp.setTitle(e.select("dl > dd > h2").text());
			descResp.setPic(e.select("dl > dt > img[src]").attr("src"));
			descResp.setReply(e.select("dl > dd > div.post-info >span.reply").text());
			descResp.setView(e.select("dl > dd > div.post-info >span.view").text());
			descResp.setAuthorPic(e.select("dl > dd > div.author >img[src]").attr("src"));
			respList.add(descResp);
		}
	
		
	}
}
