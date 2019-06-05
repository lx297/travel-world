package cn.travel.world.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class KK {
	private static Logger log = LoggerFactory.getLogger(KK.class);

	public static class http {

		// 默认字符集
		private static final String ENCODING = "UTF-8";

		/**
		 * @Title: sendPost @Description: TODO(发送post请求) @param url 请求地址 @param
		 *         headers 请求头 @param data 请求实体 @param encoding 字符集 @author
		 *         wangxy @return String @date 2018年5月10日 下午4:36:17 @throws
		 */
		public static String sendPost(String url, Map<String, String> headers, JSONObject data, String encoding) {
			log.info("进入post请求方法...");
			log.info("请求入参：URL= " + url);
			log.info("请求入参：headers=" + JSON.toJSONString(headers));
			log.info("请求入参：data=" + JSON.toJSONString(data));
			// 请求返回结果
			String resultJson = null;
			// 创建Client
			CloseableHttpClient client = HttpClients.createDefault();
			// 创建HttpPost对象
			HttpPost httpPost = new HttpPost();

			try {
				// 设置请求地址
				httpPost.setURI(new URI(url));
				// 设置请求头
				if (headers != null) {
					Header[] allHeader = new BasicHeader[headers.size()];
					int i = 0;
					for (Map.Entry<String, String> entry : headers.entrySet()) {
						allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
						i++;
					}
					httpPost.setHeaders(allHeader);
				}
				// 设置实体
				httpPost.setEntity(new StringEntity(JSON.toJSONString(data)));
				// 发送请求,返回响应对象
				CloseableHttpResponse response = client.execute(httpPost);
				// 获取响应状态
				int status = response.getStatusLine().getStatusCode();
				if (status == HttpStatus.SC_OK) {
					// 获取响应结果
					resultJson = EntityUtils.toString(response.getEntity(), encoding);
				} else {
					log.error("响应失败，状态码：" + status);
				}

			} catch (Exception e) {
				log.error("发送post请求失败", e);
			} finally {
				httpPost.releaseConnection();
			}
			return resultJson;
		}
	    //链接url下载图片
	    public static void download(String urlList,String imageName) {
	        URL url = null;
	        int imageNumber = 0;

	        try {
	            url = new URL(urlList);
	            DataInputStream dataInputStream = new DataInputStream(url.openStream());


	            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
	            ByteArrayOutputStream output = new ByteArrayOutputStream();

	            byte[] buffer = new byte[1024];
	            int length;

	            while ((length = dataInputStream.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	            byte[] context=output.toByteArray();
	            fileOutputStream.write(output.toByteArray());
	            dataInputStream.close();
	            fileOutputStream.close();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		/**
		 * @Title: sendPost @Description:
		 *         TODO(发送post请求，请求数据默认使用json格式，默认使用UTF-8编码) @param url
		 *         请求地址 @param data 请求实体 @author wangxy @return String @date
		 *         2018年5月10日 下午4:37:28 @throws
		 */
		public static String sendPost(String url, JSONObject data) {
			// 设置默认请求头
			Map<String, String> headers = new HashMap<>();
			headers.put("content-type", "application/json");

			return sendPost(url, headers, data, ENCODING);
		}

		/**
		 * @Title: sendPost @Description:
		 *         TODO(发送post请求，请求数据默认使用json格式，默认使用UTF-8编码) @param url
		 *         请求地址 @param params 请求实体 @author wangxy @return String @date
		 *         2018年5月10日 下午6:11:05 @throws
		 */
		public static String sendPost(String url, Map<String, Object> params) {
			// 设置默认请求头
			Map<String, String> headers = new HashMap<>();
			headers.put("content-type", "application/json");
			// 将map转成json
			JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
			return sendPost(url, headers, data, ENCODING);
		}

		/**
		 * @Title: sendPost @Description: TODO(发送post请求，请求数据默认使用UTF-8编码) @param
		 *         url 请求地址 @param headers 请求头 @param data 请求实体 @author
		 *         wangxy @return String @date 2018年5月10日 下午4:39:03 @throws
		 */
		public static String sendPost(String url, Map<String, String> headers, JSONObject data) {
			return sendPost(url, headers, data, ENCODING);
		}

		/**
		 * @Title: sendPost @Description:(发送post请求，请求数据默认使用UTF-8编码) @param url
		 *         请求地址 @param headers 请求头 @param params 请求实体 @author
		 *         wangxy @return String @date 2018年5月10日 下午5:58:40 @throws
		 */
		public static String sendPost(String url, Map<String, String> headers, Map<String, String> params) {
			// 将map转成json
			JSONObject data = JSONObject.parseObject(JSON.toJSONString(params));
			return sendPost(url, headers, data, ENCODING);
		}

		/**
		 * @Title: sendGet @Description: TODO(发送get请求) @param url 请求地址 @param
		 *         params 请求参数 @param encoding 编码 @author wangxy @return
		 *         String @date 2018年5月14日 下午2:39:01 @throws
		 */
		public static String sendGet(String url, Map<String, String> headers, String encoding) {
			log.info("进入get请求方法...");
			log.info("请求入参：URL= " + url);
			log.info("请求入参：headers=" + JSON.toJSONString(headers));
			// 请求结果
			String resultJson = null;
			// 创建client
			CloseableHttpClient client = HttpClients.createDefault();
			// 创建HttpGet
			HttpGet httpGet = new HttpGet();
			try {
				// 创建uri
				URIBuilder builder = new URIBuilder(url);
				// 封装参数
				// if (params != null) {
				// for (String key : params.keySet()) {
				// builder.addParameter(key, params.get(key).toString());
				// }
				// }
				if (headers != null) {
					// Header[] allHeader = new BasicHeader[headers.size()];
					// int i = 0;
					for (String key : headers.keySet()) {
						httpGet.addHeader(key, headers.get(key));
						// for (Map.Entry<String, String> entry :
						// headers.entrySet()) {
						// allHeader[i] = new BasicHeader(key,headers.get(key));
						// i++;
					}
					// httpGet.setHeaders(allHeader);
				}
				URI uri = builder.build();
				log.info("请求地址：" + uri);
				// 设置请求地址
				httpGet.setURI(uri);
				// 发送请求，返回响应对象
				CloseableHttpResponse response = client.execute(httpGet);
				// 获取响应状态
				int status = response.getStatusLine().getStatusCode();
				if (status == HttpStatus.SC_OK) {
					// 获取响应数据
					resultJson = EntityUtils.toString(response.getEntity(), encoding);
				} else {
					log.error("响应失败，状态码：" + status);
				}
			} catch (Exception e) {
				log.error("发送get请求失败", e);
			} finally {
				httpGet.releaseConnection();
			}
			return resultJson;
		}

		/**
		 * @Title: sendGet @Description: TODO(发送get请求) @param url 请求地址 @param
		 *         params 请求参数 @author wangxy @return String @date 2018年5月14日
		 *         下午2:32:39 @throws
		 */
		public static String sendGet(String url, Map<String, String> headers) {
			return sendGet(url, headers, ENCODING);
		}

		/**
		 * @Title: sendGet @Description: TODO(发送get请求) @param url 请求地址 @author
		 *         wangxy @return String @date 2018年5月14日 下午2:33:45 @throws
		 */
		public static String sendGet(String url) {
			return sendGet(url, null, ENCODING);
		}

		public static String sendPxoy(String uri, String pxoyIp, int pxoyPort) throws IOException {
			URL url = new URL(uri);
			// /创建代理服务器
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(pxoyIp, pxoyPort)); // http
																								// 代理
			// Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr); // Socket 代理
			// Authenticator.setDefault(new MyAuthenticator("username",
			// "password"));// 设置代理的用户和密码
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);// 设置代理访问
			connection.setConnectTimeout(3000);
			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(in);
			StringBuffer sb = new StringBuffer();
			String s;
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			return sb.toString();
		}

		public static String postPxoy(String uri, String pxoyIp, int pxoyPort,String json) throws IOException {
			URL url = new URL(uri);
			// /创建代理服务器
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(pxoyIp, pxoyPort)); // http
																								// 代理
			// Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr); // Socket 代理
			// Authenticator.setDefault(new MyAuthenticator("username",
			// "password"));// 设置代理的用户和密码
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);// 设置代理访问
			connection.setDoOutput(true);
			// 设置是否从HttpURLConnection读入内容，默认为true
			connection.setDoInput(true);
			// 设置是否使用缓存，post请求不使用缓存
			connection.setUseCaches(false);
			// 设置请求方法 注意大小写!
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(10000);
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			// !!!重点部分，设置参数
			
			OutputStream os = connection.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.flush();
			os.close();

			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(in);
			StringBuffer sb = new StringBuffer();
			String s;
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			return sb.toString();
		}
	}

	public static class file {
		/**
		 * 获取windows/linux的项目根目录
		 * 
		 * @return
		 */
		public static String getConTextPath() {
			String fileUrl = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			if ("usr".equals(fileUrl.substring(1, 4))) {
				fileUrl = (fileUrl.substring(0, fileUrl.length() - 16));// linux
			} else {
				fileUrl = (fileUrl.substring(1, fileUrl.length() - 16));// windows
			}
			return fileUrl;
		}

		/**
		 * 字符串转数组
		 * 
		 * @param str
		 *            字符串
		 * @param splitStr
		 *            分隔符
		 * @return
		 */
		public static String[] StringToArray(String str, String splitStr) {
			String[] arrayStr = null;
			if (!"".equals(str) && str != null) {
				if (str.indexOf(splitStr) != -1) {
					arrayStr = str.split(splitStr);
				} else {
					arrayStr = new String[1];
					arrayStr[0] = str;
				}
			}
			return arrayStr;
		}

		/**
		 * 读取文件
		 * 
		 * @param Path
		 * @return
		 */
		public static String ReadFile(String Path) {
			BufferedReader reader = null;
			String laststr = "";
			try {
				FileInputStream fileInputStream = new FileInputStream(Path);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
				reader = new BufferedReader(inputStreamReader);
				String tempString = null;
				while ((tempString = reader.readLine()) != null) {
					laststr += tempString;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return laststr;
		}

		/**
		 * 获取文件夹下所有文件的名称 + 模糊查询（当不需要模糊查询时，queryStr传空或null即可）
		 * 1.当路径不存在时，map返回retType值为1
		 * 2.当路径为文件路径时，map返回retType值为2，文件名fileName值为文件名
		 * 3.当路径下有文件夹时，map返回retType值为3，文件名列表fileNameList，文件夹名列表folderNameList
		 * 
		 * @param folderPath
		 *            路径
		 * @param queryStr
		 *            模糊查询字符串
		 * @return
		 */
		public static HashMap<String, Object> getFilesName(String folderPath, String queryStr) {
			HashMap<String, Object> map = new HashMap<>();
			List<String> fileNameList = new ArrayList<>();// 文件名列表
			List<String> folderNameList = new ArrayList<>();// 文件夹名列表
			File f = new File(folderPath);
			if (!f.exists()) { // 路径不存在
				map.put("retType", "1");
			} else {
				boolean flag = f.isDirectory();
				if (flag == false) { // 路径为文件
					map.put("retType", "2");
					map.put("fileName", f.getName());
				} else { // 路径为文件夹
					map.put("retType", "3");
					File fa[] = f.listFiles();
					queryStr = queryStr == null ? "" : queryStr;// 若queryStr传入为null,则替换为空（indexOf匹配值不能为null）
					for (int i = 0; i < fa.length; i++) {
						File fs = fa[i];
						if (fs.getName().indexOf(queryStr) != -1) {
							if (fs.isDirectory()) {
								folderNameList.add(fs.getName());
							} else {
								fileNameList.add(fs.getName());
							}
						}
					}
					map.put("fileNameList", fileNameList);
					map.put("folderNameList", folderNameList);
				}
			}
			return map;
		}

		/**
		 * 以行为单位读取文件，读取到最后一行
		 * 
		 * @param filePath
		 * @return
		 */
		public static List<String> readFileContent(String filePath) {
			BufferedReader reader = null;
			List<String> listContent = new ArrayList<>();
			try {
				reader = new BufferedReader(new FileReader(filePath));
				String tempString = null;
//				int line = 1;
				// 一次读入一行，直到读入null为文件结束
				while ((tempString = reader.readLine()) != null) {
					listContent.add(tempString);
//					line++;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
			return listContent;
		}

		/**
		 * 读取指定行数据 ，注意：0为开始行
		 * 
		 * @param filePath
		 * @param lineNumber
		 * @return
		 */
		public static String readLineContent(String filePath, int lineNumber) {
			BufferedReader reader = null;
			String lineContent = "";
			try {
				reader = new BufferedReader(new FileReader(filePath));
				int line = 0;
				while (line <= lineNumber) {
					lineContent = reader.readLine();
					line++;
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e1) {
					}
				}
			}
			return lineContent;
		}

		/**
		 * 读取从beginLine到endLine数据（包含beginLine和endLine），注意：0为开始行
		 * 
		 * @param filePath
		 * @param beginLineNumber
		 *            开始行
		 * @param endLineNumber
		 *            结束行
		 * @return
		 */
		public static List<String> readLinesContent(String filePath, int beginLineNumber, int endLineNumber) {
			List<String> listContent = new ArrayList<>();
			try {
				int count = 0;
				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				String content = reader.readLine();
				while (content != null) {
					if (count >= beginLineNumber && count <= endLineNumber) {
						listContent.add(content);
					}
					content = reader.readLine();
					count++;
				}
			} catch (Exception e) {
			}
			return listContent;
		}

		/**
		 * 读取若干文件中所有数据
		 * 
		 * @param listFilePath
		 * @return
		 */
		public static List<String> readFileContent_list(List<String> listFilePath) {
			List<String> listContent = new ArrayList<>();
			for (String filePath : listFilePath) {
				File file = new File(filePath);
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					String tempString = null;
					int line = 1;
					// 一次读入一行，直到读入null为文件结束
					while ((tempString = reader.readLine()) != null) {
						listContent.add(tempString);
						line++;
					}
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e1) {
						}
					}
				}
			}
			return listContent;
		}

		/**
		 * 文件数据写入（如果文件夹和文件不存在，则先创建，再写入）
		 * 
		 * @param filePath
		 * @param content
		 * @param flag
		 *            true:如果文件存在且存在内容，则内容换行追加；false:如果文件存在且存在内容，则内容替换
		 */
		public static void fileLinesWrite(String filePath, String content, boolean flag) {
			FileWriter fw = null;
			try {
				File file = new File(filePath);
				// 如果文件夹不存在，则创建文件夹
				// if (!file.getParentFile().exists()){
				// file.getParentFile().mkdirs();
				// }
				if (!file.exists()) {// 如果文件不存在，则创建文件,写入第一行内容
					file.createNewFile();
					fw = new FileWriter(file);
				} else {// 如果文件存在,则追加或替换内容
					fw = new FileWriter(file, flag);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			try {
				fw.flush();
				pw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 写文件
		 * 
		 * @param ins
		 * @param out
		 */
		public static void writeIntoOut(InputStream ins, OutputStream out) {
			byte[] bb = new byte[10 * 1024];
			try {
				int cnt = ins.read(bb);
				while (cnt > 0) {
					out.write(bb, 0, cnt);
					cnt = ins.read(bb);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					out.flush();
					ins.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * 判断list中元素是否完全相同（完全相同返回true,否则返回false）
		 * 
		 * @param list
		 * @return
		 */
		private static boolean hasSame(List<? extends Object> list) {
			if (null == list)
				return false;
			return 1 == new HashSet<Object>(list).size();
		}

		/**
		 * 判断list中是否有重复元素（无重复返回true,否则返回false）
		 * 
		 * @param list
		 * @return
		 */
		private static boolean hasSame2(List<? extends Object> list) {
			if (null == list)
				return false;
			return list.size() == new HashSet<Object>(list).size();
		}

		/**
		 * 增加/减少天数
		 * 
		 * @param date
		 * @param num
		 * @return
		 */
		public static Date DateAddOrSub(Date date, int num) {
			Calendar startDT = Calendar.getInstance();
			startDT.setTime(date);
			startDT.add(Calendar.DAY_OF_MONTH, num);
			return startDT.getTime();
		}
	}
}
