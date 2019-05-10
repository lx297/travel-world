package cn.travel.world.impl;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.groovy.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.travel.world.request.QueryDetailsReq;
import cn.travel.world.request.QueryListReq;
import cn.travel.world.service.WebDataService;
import cn.travel.world.util.KK;

@Service
public class KProxyWebDataServiceImpl implements WebDataService {
	private static final String K_PROXY = "https://www.kuaidaili.com/free/intr/%s/";

	@Override
	public List<Object> getDataList(QueryListReq req) {
		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(String.format(K_PROXY, req.getPage())), 5000);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Object> respList = new ArrayList<>();
		Elements select = doc.select("#list table tr");
		for (Element e : select) {
			String text = e.select("td").text();
			if (StringUtils.isEmpty(text))
				continue;
			respList.add(e.select("td").text());
		}
		return respList;
	}

	@Override
	public String getDataDetail(QueryDetailsReq req) {
		return null;
	}

	public static void main(String[] args) throws InterruptedException {

		KProxyWebDataServiceImpl kProxyWebDataServiceImpl = new KProxyWebDataServiceImpl();
		QueryListReq req = new QueryListReq();
		for (int i = 1; i < 50; i++) {
			req.setPage(i + "");
			List<Object> dataList = kProxyWebDataServiceImpl.getDataList(req);
			for (Object object : dataList) {
				String[] arr = object.toString().split(" ");
				System.out.println(Arrays.toString(arr));
				// String html= KK.http.sendPxoy("http://myip.kkcha.com",
				// "112.95.22.83", 8888);
				try {
					String html = KK.http.sendPxoy("http://www.baidu.com", arr[0], Integer.parseInt(arr[1]));
//					String ip = html.substring(html.indexOf("sRemoteAddr = '") + 15, html.indexOf("';"));
//					System.out.println(ip);
				} catch (Exception e) {
					continue;
				}
				KK.file.fileLinesWrite("proxy.txt",object.toString() , true);
				System.err.println(object);
			}
			Thread.sleep(1000);
		}
	}

}
