package com.lionxxw.utils.http;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class HttpUtil {
	/**
	 * 得到完整的包含所有GET或者POST参数的请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String buildOriginalURL(HttpServletRequest request) {
		StringBuffer originalURL = request.getRequestURL();
		String url = originalURL.toString();
		Map<String, String[]> parameters = request.getParameterMap();
		if (parameters != null && parameters.size() > 0) {
			originalURL.append("?");
			for (Iterator<String> iter = parameters.keySet().iterator(); iter
					.hasNext();) {
				String key = iter.next();
				String[] values = parameters.get(key);
				for (int i = 0; i < values.length; i++) {
					String value = "";
					try {
						value = new String(values[i].getBytes("ISO-8859-1"),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					originalURL.append(key).append("=").append(value)
							.append("&");
				}
			}
			url = originalURL.substring(0, originalURL.length() - 1); // 去掉最后一个&
		}
		return url;
	}

	/**
	 * 获取当前应用跟路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request) {
		//
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}

	/**
	 * 页面重定向
	 * 
	 * @param toUrl
	 * @return
	 */
	public static String pageLocationTo(String toUrl) {
		//
		String ret = "";
		//
		StringBuffer bufHtml = new StringBuffer();
		bufHtml.append("<script>document.location.href='" + toUrl
				+ "'</script>");
		// set
		ret = bufHtml.toString();
		// return
		return ret;
	}
	
	
	
	
	
	/**
	 * @param httpSendModel
	 * @param getCharSet
	 * @return
	 * @throws Exception 
	 */
	public static SimpleHttpResponse doRequest(HttpSendModel httpSendModel,
			String getCharSet) throws Exception {

		// 创建默认的httpClient客户端端
		SimpleHttpClient simpleHttpclient = new SimpleHttpClient();

		try {
			return doRequest(simpleHttpclient, httpSendModel, getCharSet);
		} finally {
			simpleHttpclient.getHttpclient().getConnectionManager().shutdown();
		}

	}

	/**
	 * @param simpleHttpclient
	 * @param httpSendModel
	 * @param getCharSet
	 * @return
	 * @throws Exception 
	 */
	public static SimpleHttpResponse doRequest(
			SimpleHttpClient simpleHttpclient, HttpSendModel httpSendModel,
			String getCharSet) throws Exception {

		HttpRequestBase httpRequest = buildHttpRequest(httpSendModel);

		if (httpSendModel.getUrl().startsWith("https://")) {
			simpleHttpclient.enableSSL();
		}

		try {
			HttpResponse response = simpleHttpclient.getHttpclient().execute(
					httpRequest);
			int statusCode = response.getStatusLine().getStatusCode();

			if (isRequestSuccess(statusCode)) {
				return new SimpleHttpResponse(statusCode, EntityUtils.toString(
						response.getEntity(), getCharSet), null);
			} else {
				return new SimpleHttpResponse(statusCode, null, response
						.getStatusLine().getReasonPhrase());
			}

		} catch (Exception e) {
			throw new Exception("http请求异常", e);
		}

	}

	/**
	 * @param httpSendModel
	 * @return
	 * @throws Exception 
	 */
	protected static HttpRequestBase buildHttpRequest(
			HttpSendModel httpSendModel) throws Exception {
		HttpRequestBase httpRequest;
		if (httpSendModel.getMethod() == null) {
			throw new Exception("请求方式未设定");
		} else if (httpSendModel.getMethod() == HttpMethod.POST) {

			String url = httpSendModel.getUrl();
			String sendCharSet = httpSendModel.getCharSet();
			List<HttpFormParameter> params = httpSendModel.getParams();

			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null && params.size() != 0) {

				for (HttpFormParameter param : params) {
					qparams.add(new BasicNameValuePair(param.getName(), param
							.getValue()));
				}

			}

			HttpPost httppost = new HttpPost(url);
			try {
				httppost.setEntity(new UrlEncodedFormEntity(qparams,
						sendCharSet));
			} catch (UnsupportedEncodingException e) {
				throw new Exception("构建post请求参数失败", e);
			}

			httpRequest = httppost;
		} else if (httpSendModel.getMethod() == HttpMethod.GET) {
			HttpGet httpget = new HttpGet(httpSendModel.buildGetRequestUrl());

			httpRequest = httpget;
		} else {
			throw new Exception("请求方式不支持：" + httpSendModel.getMethod());
		}

		return httpRequest;
	}

	/**
	 * 请求是否成功
	 * 
	 * @param statusCode
	 * @return
	 */
	public static boolean isRequestSuccess(int statusCode) {
		return statusCode == 200;
	}
}
