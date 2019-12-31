package cn.travel.world.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class KProxyWebDataService  {
	public static final String[] K_PROXY = { 
			"https://www.xicidaili.com/wt/%s",
			"https://www.kuaidaili.com/free/inha/%s/",
			"https://www.kuaidaili.com/free/intr/%s/",
			"http://www.89ip.cn/index_%s.html"
	};

	public List<Object> getDataList(QueryListReq req) {
		List<Object> respList = new ArrayList<>();
		for (int i = 0; i < K_PROXY.length; i++) {
			Document doc=null;
			try {
				doc = Jsoup.parse(new URL(String.format(K_PROXY[i], req.getPage())), 5000);
			} catch (Exception e1) {
				continue;
			} 
			Elements select = doc.select("table tr");
			if(select==null)continue;
			for (Element e : select) {
				String text = e.select("td").text();
				if (StringUtils.isEmpty(text))
					continue;
				respList.add(e.select("td").text());
			}
		}
		return respList;
	}

	public String getDataDetail(QueryDetailsReq req) {
		return null;
	}

	public static void main(String[] args) throws InterruptedException {
		int[] arrId = { 77550408, 77168050, 76782728, 76529566, 76257771, 75970248, 75685955, 75416749, 78055024 };
		KProxyWebDataService kProxyWebDataServiceImpl = new KProxyWebDataService();
		QueryListReq req = new QueryListReq();
		int c = 0;
		for (int i = 1; i < 1000; i++) {
			req.setPage(i + "");
			List<Object> dataList = kProxyWebDataServiceImpl.getDataList(req);
			for (Object object : dataList) {
				String[] arr = object.toString().split(" ");
				System.out.println(Arrays.toString(arr));
				try {
					boolean isW = true;
					for (int id : arrId) {
						long s = System.currentTimeMillis();
						KK.http.postPxoy("https://www.6tiantian.com/share/student/hw/like", arr[0], Integer.parseInt(arr[1]), "{\"id\":\"" + id + "\",\"token\":\"\"}");
						if (isW) {
							isW = false;
							System.err.println((c++) + object.toString() + "耗时：" + (System.currentTimeMillis() - s));
							KK.file.fileLinesWrite("proxy.txt", object.toString(), true);
						}
					}
				} catch (Exception e) {
					continue;
				}
			}
			Thread.sleep(1000);
		}
	}

}
