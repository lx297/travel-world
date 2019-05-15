package cn.travel.world.impl;

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
public class KProxyWebDataServiceImpl implements WebDataService {
//	private static final String K_PROXY = "https://www.xicidaili.com/wt/%s";
//	private static final String K_PROXY = "https://www.kuaidaili.com/free/inha/%s/";
//	private static final String K_PROXY = "https://www.kuaidaili.com/free/intr/%s/";
	private static final String K_PROXY = "http://www.89ip.cn/index_%s.html";

	@Override
	public List<Object> getDataList(QueryListReq req) {
		try {
		Document doc = Jsoup.parse(new URL(String.format(K_PROXY, req.getPage())), 5000);
		List<Object> respList = new ArrayList<>();
		Elements select = doc.select("table tr");
		for (Element e : select) {
			String text = e.select("td").text();
			if (StringUtils.isEmpty(text))
				continue;
			respList.add(e.select("td").text());
		}
		return respList;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public String getDataDetail(QueryDetailsReq req) {
		return null;
	}


	public static void main(String[] args) throws InterruptedException {
		int[]  arrId={77550408,77168050,76782728,76529566,76257771,75970248,75685955,75416749,78055024};
		KProxyWebDataServiceImpl kProxyWebDataServiceImpl = new KProxyWebDataServiceImpl();
		QueryListReq req = new QueryListReq();
		int c=0;
		for (int i = 1; i < 1000; i++) {
			req.setPage(i + "");
			List<Object> dataList = kProxyWebDataServiceImpl.getDataList(req);
			for (Object object : dataList) {
				String[] arr = object.toString().split(" ");
				System.out.println(Arrays.toString(arr));
				try {
					boolean isW=true;
					for (int id : arrId) {
						long s=System.currentTimeMillis();
						KK.http.postPxoy("https://www.6tiantian.com/share/student/hw/like", arr[0], Integer.parseInt(arr[1]),"{\"id\":\""+id+"\",\"token\":\"\"}");
						if(isW){
							isW=false;
							System.err.println((c++)+object.toString()+"耗时："+(System.currentTimeMillis()- s));
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
