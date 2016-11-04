package com.lionxxw.utils;

public class HTMLForm {
	// -------------常量定义--------------
	// charset
	public static String CHARSET_GBK = "gbk";
	public final static String CHARSET_UTF8 = "utf-8";
	public final static String CHARSET_GB2312 = "gb2312";
	// method
	public final static String METHOD_GET = "GET";
	public final static String METHOD_POST = "POST";
	// ----------------------------------
	private String url; // 提交地址
	private String charset;// 字符编码
	private String method;// 提交方式GET或者POST
	private String[] args_NameEqualValue;// 形如"param1=value1,param2=value2"

	//
	public HTMLForm() {

	}

	//
	public HTMLForm(String url, String charset, String method,
			String[] args_NameEqualValue) {
		//
		this.url = url;
		this.charset = charset;
		this.method = method;
		this.args_NameEqualValue = args_NameEqualValue;
	}

	/**
	 * 填充表单并自动提交
	 * 
	 * @return
	 */
	public String getformAutoSendHtml() {
		return HtmlUtil.getformAutoSendHtml(this);
	}

	//
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String[] getArgs_NameEqualValue() {
		return args_NameEqualValue;
	}

	public void setArgs_NameEqualValue(String[] args_NameEqualValue) {
		this.args_NameEqualValue = args_NameEqualValue;
	}
}
