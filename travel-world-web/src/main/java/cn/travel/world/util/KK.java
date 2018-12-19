package cn.travel.world.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KK {
	private static Logger log = LoggerFactory.getLogger(KK.class);

	public static class http {
		public static String postJson(String json, String url) throws Exception {

			HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
			try {
				// 设置请求和传输超时时间
				CloseableHttpClient httpclient = HttpClients.custom().build();
				httppost.setEntity(new StringEntity(json, "UTF-8"));
				CloseableHttpResponse response = httpclient.execute(httppost);
				String result = null;
				InputStreamReader reader = null;
				try {
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						result = EntityUtils.toString(entity);
					} else {
						throw new IOException("HTTP返回码[" + response.getStatusLine().getStatusCode() + "]，HTTP返回异常");
					}
				} finally {
					response.close();
					if (reader != null) {
						reader.close();
					}
				}
				// 返回报文
				return result;
			} catch (ClientProtocolException e) {
				log.error("HTTP网络错误" + e);
				throw new IOException("HTTP网络错误" + e);
			} catch (IOException e) {
				log.error("IO网络错误" + e);
				throw new IOException("IO网络错误" + e);
			} catch (Exception e) {
				log.error("系统错误" + e);
				throw new Exception("系统错误" + e);
			} finally {
				// 关闭连接
				if (httppost != null) {
					httppost.releaseConnection();
				}
			}
		}

		/**
		 * 发送http get请求
		 * 
		 * @param getUrl
		 * @return
		 */
		public static String get(String getUrl) {
			StringBuffer sb = new StringBuffer();
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				URL url = new URL(getUrl);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setAllowUserInteraction(false);
				isr = new InputStreamReader(url.openStream());
				br = new BufferedReader(isr);
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(br!=null)
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(isr!=null)
					try {
						isr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return sb.toString();
		}
	}
}
