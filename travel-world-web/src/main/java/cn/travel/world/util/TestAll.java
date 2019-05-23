package cn.travel.world.util;

import java.util.List;

public class TestAll {
	public static void main(String[] args) throws InterruptedException {
		int[] arrId = { 78055024,78169536,78479915,78781764,79089286,79556963,80071318,80177887,80487444,80811214};
		List<String> readFileContent = KK.file.readFileContent("proxy2.txt");
		for (int i = 0; i < 20; i++) {
			new Thread( new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 20; j++) {
						System.out.println(Thread.currentThread().getName());
						startRun(arrId, readFileContent);
					}
				}
			}).start();
			Thread.sleep(30000);
		}
	}

	private static void startRun(int[] arrId, List<String> readFileContent) {
		for (String string : readFileContent) {
			String[] arr = string.split(" |	|@|:");
			try {
				for (int id : arrId) {
					long s = System.currentTimeMillis();
					String postPxoy = KK.http.postPxoy("https://www.6tiantian.com/share/student/hw/like", arr[0], Integer.parseInt(arr[1]), "{\"id\":\"" + id + "\",\"token\":\"\"}");
					System.err.println(Thread.currentThread().getName()+postPxoy + string + "-->耗时：" + (System.currentTimeMillis() - s));
				}
			} catch (Exception e) {
				continue;
			}
		}
	}
}
