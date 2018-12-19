package cn.travel.world;

import java.util.HashMap;
import java.util.Map;

import cn.travel.world.util.KK;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) throws Exception {
		Map<String,String > input=new HashMap<>();
		input.put("Referer", "https://m.douban.com");
		String json= KK.http.sendGet("https://m.douban.com/rexxar/api/v2/movie/3878007/interests?count=10&order_by=hot&start=0&ck=&for_mobile=1",input);
		System.out.println(json);
//		String html= KK.http.sendGet("https://m.douban.com/search/?query=海王&type=movie",input);
//		Document parse = Jsoup.parse(html);
//		Elements elements = parse.select("ul.search_results_subjects > li > a");
//		for (Element e : elements) {
//			
//		}
//		System.out.println(parse);
	}
}
