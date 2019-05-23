package cn.travel.world.util;

import java.util.Arrays;
import java.util.List;

import cn.travel.world.impl.KProxyWebDataServiceImpl;
import cn.travel.world.request.QueryListReq;

public class TestAll2 {
	public static void main(String[] args) throws InterruptedException {
		int[]  arrId={78055024,78169536,78479915,78781764,79089286,79556963,80071318,80177887,80487444,80811214};
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
