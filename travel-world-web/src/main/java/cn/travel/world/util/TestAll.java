package cn.travel.world.util;

import java.util.List;

public class TestAll {
	public static void main(String[] args) throws InterruptedException {
		int[] arrId = { 77550408, 77168050, 76782728, 76529566, 76257771, 75970248, 75685955, 75416749,78055024};
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
