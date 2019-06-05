package cn.travel.world.util;

import java.io.File;
import java.net.URL;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ImagePreProcess3 {

	private static Tesseract tessreact = new Tesseract();
	private static String STEP1 = "step1";
	// private static String URL="http://game.tom.com/checkcode.php";
	private static String URL = "http://218.246.96.172:801/chongzhi/common/web/verifycode.jsp";

	public static void main(String[] args) {
		tessreact.setDatapath("tessdata");
		for (int i = 0; i < 10; i++) {
			try {
				KK.http.download(URL, STEP1 + "/aaa.jpg");
				File file= new File(STEP1 + "/aaa.jpg");
				String result = tessreact.doOCR(file);
				System.out.println(file.getName() + "测验结果：" + result.replaceAll("[^a-z,A-Z,0-9]", ""));
			} catch (TesseractException e) {
				e.printStackTrace();
			}
		}
//		downloadImage();
//		trainData();
	}

	public static void downloadImage() {
		for (int i = 0; i < 10; i++) {
			KK.http.download(URL, STEP1 + "/" + i + ".jpg");
		}

	}

	public static void trainData() {
		File dir = new File(STEP1);
		File[] files = dir.listFiles();
		for (File file : files) {
			if (!file.exists()) {
				System.out.println("图片不存在");
			}
			try {
				String result = tessreact.doOCR(file);
				System.out.println(file.getName() + "测验结果：" + result.replaceAll("[^a-z,A-Z,0-9]", ""));
			} catch (TesseractException e) {
				e.printStackTrace();
			}
		}
	}

}