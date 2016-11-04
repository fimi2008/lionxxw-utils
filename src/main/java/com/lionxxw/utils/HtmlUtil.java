package com.lionxxw.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class HtmlUtil {
	public final static String CREATE_FORM_NAME_ID = "MyForm";// 生成的form表单的name和id

	/**
	 * get请求参数转向
	 * 
	 * @param url
	 * @param request
	 * @return
	 */
	public static String getGetHtmlString(String url, HttpServletRequest request) {
		String ret = "";
		//
		url = url + "?";
		Enumeration<String> pnameEnum = request.getParameterNames();
		while (pnameEnum.hasMoreElements()) {
			String paramName = pnameEnum.nextElement();
			url += paramName;
			url += "=";
			url += request.getParameter(paramName);
			url += "&";
		}
		url += "__fromReferIP=" + request.getHeader("X-Real-IP");
		// set
		ret = "<script>location.href ='" + url + "';</script>";
		// return
		return ret;
	}

	/**
	 * post请求参数转发
	 * 
	 * @param url
	 * @param request
	 * @return
	 */
	public static String getPostHtmlString(String url,
			HttpServletRequest request) {
		String ret = "";
		//
		StringBuffer bufHtml = new StringBuffer();
		bufHtml.append("<html>");
		bufHtml.append("<head>");
		bufHtml.append("</head>");
		bufHtml.append("<body onLoad=\"document." + CREATE_FORM_NAME_ID
				+ ".submit();\">");
		bufHtml.append("<form name=\"" + CREATE_FORM_NAME_ID + "\" action=\"")
				.append(url).append("\"  method=\"post\">");
		//
		Enumeration<String> pnameEnum = request.getParameterNames();
		while (pnameEnum.hasMoreElements()) {
			String paramName = pnameEnum.nextElement();
			//
			bufHtml.append("<input type=\"hidden\" name=\"").append(paramName)
					.append("\" value=\"")
					.append(request.getParameter(paramName)).append("\"/>");
		}
		//
		bufHtml.append("</form>");
		bufHtml.append("</body>");
		bufHtml.append("</html>");
		// set
		ret = bufHtml.toString();
		// return
		return ret;
	}

	
	/**
	 * 填充表单并自动提交
	 * 
	 * @param url
	 *            自动提交地址
	 * @param charset
	 * @param method
	 * @param args_NameEqualValue
	 *            形如"param1=value1,param2=value2"
	 * @return
	 */
	public static String getformAutoSendHtml(String url, String charset,
			String method, String... args_NameEqualValue) {
		String ret = "";
		//
		StringBuffer bufHtml = new StringBuffer();
		bufHtml.append("<html>");
		bufHtml.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset="
				+ charset + "\" /></head>");
		bufHtml.append("<body onLoad=\"document." + CREATE_FORM_NAME_ID
				+ ".submit();\">");
		bufHtml.append(
				"<form id=\"" + CREATE_FORM_NAME_ID + "\" name=\""
						+ CREATE_FORM_NAME_ID + "\" action=\"").append(url)
				.append("\"  method=\"" + method + "\">");
		//
		if (args_NameEqualValue != null && args_NameEqualValue.length > 0) {
			//
			String[] nameValuePair;
			for (int i = 0; i < args_NameEqualValue.length; i++) {
				nameValuePair = args_NameEqualValue[i].split("=", 2);
				bufHtml.append("<input type=\"hidden\" name=\"")
						.append(nameValuePair[0]).append("\" value=\"")
						.append(nameValuePair[1]).append("\"/>");
			}
		}
		//
		bufHtml.append("</form>");
		bufHtml.append("</body>");
		bufHtml.append("</html>");
		// set
		ret = bufHtml.toString();
		// return
		return ret;
	}// end of func

	/**
	 * 填充表单并自动提交
	 * 
	 * @param htmlForm
	 * @return
	 */
	public static String getformAutoSendHtml(HTMLForm htmlForm) {
		// return
		return getformAutoSendHtml(htmlForm.getUrl(), htmlForm.getCharset(),
				htmlForm.getMethod(), htmlForm.getArgs_NameEqualValue());
	}
	// 
	/**
	 * 填充表单不自动提交
	 * 
	 * @param url
	 *            自动提交地址
	 * @param charset
	 * @param method
	 * @param args_NameEqualValue
	 *            形如"param1=value1,param2=value2"
	 * @return
	 */
	public static String getFormSend(String url, String charset, String method,
			String... args_NameEqualValue) {
		String ret = "";
		//
		StringBuffer bufHtml = new StringBuffer();
		bufHtml.append(
				"<form id=\"" + CREATE_FORM_NAME_ID + "\" name=\""
						+ CREATE_FORM_NAME_ID + "\" action=\"").append(url)
				.append("\"  method=\"" + method + "\">");
		//
		if (args_NameEqualValue != null && args_NameEqualValue.length > 0) {
			//
			String[] nameValuePair;
			for (int i = 0; i < args_NameEqualValue.length; i++) {
				nameValuePair = args_NameEqualValue[i].split("=", 2);
				bufHtml.append("<input type=\"hidden\" name=\"")
						.append(nameValuePair[0]).append("\" value=\"")
						.append(nameValuePair[1]).append("\"/>");
			}
		}
		//
		bufHtml.append("</form>");
		// set
		ret = bufHtml.toString();
		// return
		return ret;
	}// end of func

	/**
	 * 填充表单不自动提交
	 * 
	 * @param htmlForm
	 * @return
	 */
	public static String getFormSend(HTMLForm htmlForm) {
		// return
		return getFormSend(htmlForm.getUrl(), htmlForm.getCharset(),
				htmlForm.getMethod(), htmlForm.getArgs_NameEqualValue());
	}// end of func

	/**
	 * 页面重定向
	 * 
	 * @param toUrl
	 * @return
	 */
	public static String locationPage(String toUrl) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>document.location.href='" + toUrl + "'</script>");
		return sb.toString();
	}

	/**
	 * 弹出信息并返回页面
	 * 
	 * @param msg
	 * @return
	 */
	public static String alertMsgAndPageBack(String msg) {
		//
		return alertMsgAndPageBack(msg, "utf-8");
	}// end of func

	/**
	 * 弹出信息并返回页面
	 * 
	 * @param msg
	 * @param encode
	 * @return
	 */
	public static String alertMsgAndPageBack(String msg, String encode) {
		//
		String ret = "";
		//
		StringBuffer bufHtml = new StringBuffer();
		bufHtml.append("<html>");
		bufHtml.append("<head>");
		bufHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
				+ encode + "\" />");
		bufHtml.append("</head>");
		bufHtml.append("<body>");
		bufHtml.append("<script>alert('" + msg + "');history.go(-1)</script>");
		bufHtml.append("</body>");
		bufHtml.append("</html>");
		// set
		ret = bufHtml.toString();
		// return
		return ret;
	}// end of func

	/**
	 * 弹出信息并返回页面
	 * 
	 * @param formHtml
	 * @param encode
	 * @return
	 */
	public static String htmlAppendFormAndAutoSend(String formHtml,
			String encode) {
		//
		String ret = "";
		//
		StringBuffer bufHtml = new StringBuffer();
		bufHtml.append("<html>");
		bufHtml.append("<head>");
		bufHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset="
				+ encode + "\" />");
		bufHtml.append("</head>");
		bufHtml.append("<body onLoad=\"document." + CREATE_FORM_NAME_ID
				+ ".submit();\">");
		bufHtml.append(formHtml);
		bufHtml.append("</body>");
		bufHtml.append("</html>");
		// set
		ret = bufHtml.toString();
		// return
		return ret;
	}// end of func

	/**
	 * post请求参数转发
	 * 
	 * @param url
	 * @param args_NameEqualValue
	 *            形如"param1=value1,param2=value2"
	 * @return
	 */
	public static String getPostHtmlStringByNameValuePair(String url,
			String... args_NameEqualValue) {
		String ret = "";
		//
		StringBuffer bufHtml = new StringBuffer();
		bufHtml.append("<html>");
		bufHtml.append("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>");
		bufHtml.append("<body onLoad=\"document.MyForm.submit();\">");
		bufHtml.append("<form name=\"MyForm\" action=\"").append(url)
				.append("\"  method=\"post\">");
		//
		if (args_NameEqualValue != null && args_NameEqualValue.length > 0) {
			//
			String[] nameValuePair;
			for (int i = 0; i < args_NameEqualValue.length; i++) {
				nameValuePair = args_NameEqualValue[i].split("=", 2);
				bufHtml.append("<input type=\"hidden\" name=\"")
						.append(nameValuePair[0]).append("\" value=\"")
						.append(nameValuePair[1]).append("\"/>");
			}
		}
		//
		bufHtml.append("</form>");
		bufHtml.append("</body>");
		bufHtml.append("</html>");
		// set
		ret = bufHtml.toString();
		// return
		return ret;
	}// end of func
}

