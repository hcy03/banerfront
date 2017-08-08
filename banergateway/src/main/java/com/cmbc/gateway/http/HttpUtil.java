package com.cmbc.gateway.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * Created by qi on 2016/9/5.
 */
@Slf4j
public class HttpUtil {

	/**
	 * 发送GET请求
	 * 
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, new HashMap<String, String>());
	}

	/**
	 * 发送GET请求
	 * 
	 * @param url
	 * @param params
	 *            input a map object
	 * @return
	 */
	public static String doGet(String url, Map<String, String> params) {
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();

		String apiUrl = url;
		if (params != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : params.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(params.get(key));
				i++;
			}
			apiUrl += param;
		}
		log.info(apiUrl);

		HttpGet request = new HttpGet(apiUrl);
		// HttpHost proxy = new HttpHost("197.3.187.71", 3128);
		// builder.setProxy(proxy);
		CloseableHttpResponse response = null;
		BufferedReader reader = null;
		InputStreamReader intputStream = null;
		InputStream responseInput = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			response = httpClient.execute(request);
			// 结果处理
			int statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				log.info("请求成功!");
			} else {
				log.info("statusCode=[{}]", statusCode);
				log.info(response.getStatusLine().getReasonPhrase());
			}
			responseInput = response.getEntity().getContent();
			intputStream = new InputStreamReader(responseInput, "UTF-8");
			reader = new BufferedReader(intputStream);
			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				stringBuilder.append(tempStr);
			}
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			safeClose(responseInput, intputStream, reader, response, httpClient);
		}
		return stringBuilder.toString();
	}

	public static Document doGet4Instream(String url, Map<String, String> params) {
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();

		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		apiUrl += param;

		log.info(apiUrl);

		CloseableHttpResponse response = null;
		BufferedReader reader = null;
		InputStreamReader intputStream = null;
		InputStream responseInput = null;

		Document doc = null;
		SAXReader saxReader = new SAXReader();
		try {
			HttpGet request = new HttpGet(apiUrl);
			response = httpClient.execute(request);
			// 结果处理
			int statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				log.info("请求成功!");
			} else {
				log.info("statusCode=[{}]", statusCode);
				log.info(response.getStatusLine().getReasonPhrase());
			}
			responseInput = response.getEntity().getContent();
			intputStream = new InputStreamReader(responseInput, "UTF-8");

			doc = (Document) saxReader.read(intputStream);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			safeClose(responseInput, intputStream, reader, response, httpClient);
		}
		return doc;
	}

	public static void saveImg(String url, String imgName) {
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();
		CloseableHttpResponse response = null;
		InputStream intputStream = null;
		FileOutputStream outStream = null;
		try {
			HttpGet httpPost = new HttpGet(url);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();

			System.out.println("StatusCode:" + statusCode);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				intputStream = entity.getContent();
				byte[] data = readInputStream(intputStream);
				File imgFile = new File(imgName);
				outStream = new FileOutputStream(imgFile);
				outStream.write(data);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (intputStream != null) {
				try {
					intputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			// 创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			// 每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			// 使用一个输入流从buffer里把数据读取出来
			while ((len = inStream.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			// 关闭输入流
			inStream.close();
			// 把outStream里的数据写入内存
			return outStream.toByteArray();
		} finally {
			if (null != outStream) {
				outStream.close();
			}
		}
	}

	/**
	 * ���� POST ����HTTP����������������
	 * 
	 * @param apiUrl
	 * @return
	 */
	public static String doPost(String apiUrl) {
		return doPost(apiUrl, new HashMap<String, Object>());
	}

	/**
	 * ���� POST ����HTTP����K-V��ʽ
	 * 
	 * @param apiUrl
	 *            API�ӿ�URL
	 * @param params
	 *            ����map
	 * @return
	 */
	public static String doPost(String apiUrl, Map<String, Object> params) {
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();
		StringBuilder sb = new StringBuilder();
		CloseableHttpResponse response = null;
		BufferedReader reader = null;
		InputStream responseInput = null;
		InputStreamReader intputStream = null;
		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("Connection", "close");
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
			log.info("pairList==========" + pairList.toString());
			response = httpClient.execute(httpPost);
			responseInput = response.getEntity().getContent();
			intputStream = new InputStreamReader(responseInput, "UTF-8");
			reader = new BufferedReader(intputStream);
			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				sb.append(tempStr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			safeClose(responseInput, intputStream, reader, response, httpClient);
		}
		return sb.toString();
	}

	public static String doPost(String apiUrl, Object json) {
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();
		StringBuilder sb = new StringBuilder();
		CloseableHttpResponse response = null;
		BufferedReader reader = null;
		InputStream responseInput = null;
		InputStreamReader intputStream = null;
		try {
			HttpPost httpPost = new HttpPost(apiUrl);
			StringEntity stringEntity = new StringEntity(json.toString());
			stringEntity.setContentEncoding("GBK");
			stringEntity.setContentType("application/json");
			// stringEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			responseInput = response.getEntity().getContent();
			intputStream = new InputStreamReader(responseInput, "UTF-8");
			reader = new BufferedReader(intputStream);
			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				sb.append(tempStr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			safeClose(responseInput, intputStream, reader, response, httpClient);
		}
		return sb.toString();
	}


	public static String doPostContent(String dataContent,String url){
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();
       	CloseableHttpResponse response = null;
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            
            httpPost.addHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Connection" , "close");
            HttpEntity reqentity = new StringEntity(dataContent,ContentType.create("application/json", "UTF-8") );
            httpPost.setEntity(reqentity);
            
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
            
            return result;
        } catch(ConnectTimeoutException e){
        	log.info("连接超时请重试");
        }catch (Exception e) {
        	log.info(e.getMessage());
        } finally{
        	if(response!=null)
            try {
				response.close();
			} catch (IOException e) {
			}
        }
        return "";
    }
	
	public static void safeClose(InputStream responseInput, InputStreamReader intputStream, BufferedReader reader, CloseableHttpResponse response,
			CloseableHttpClient httpClient) {
		if (responseInput != null) {
			try {
				responseInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (intputStream != null) {
			try {
				intputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (response != null) {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		Map<String, String> map = new HashMap<String, String>();
		map.put("grant_type", "client_credential");
		map.put("appid", "wxf05bf7eb8481bb74");
		map.put("secret", "23971c8d7089b4d9892682ac30c372b5");
		// String res = doPostSSL(url, map);
		String res = doGet(url, map);
		log.info("result:" + res);
	}

}
