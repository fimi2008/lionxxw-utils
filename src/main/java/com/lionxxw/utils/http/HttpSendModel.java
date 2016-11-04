package com.lionxxw.utils.http;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dinglulu on 2016/8/2.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HttpSendModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String charSet = "UTF-8";
    private HttpMethod method = HttpMethod.getDefault();
    private List<HttpFormParameter> params;

    public HttpSendModel() {
    }

    public HttpSendModel(String url) {
        int index = url.indexOf("?");
        if(index != -1) {
            this.url = url.substring(0, index);
            String paramsString = url.substring(index + 1);
            this.buildParams(paramsString);
        } else {
            this.url = url;
        }

    }

    public HttpSendModel(String url, String urlPath) {
        if(StringUtils.isBlank(urlPath)) {
            this.url = url;
        } else {
            if(!url.startsWith(urlPath)) {
                System.out.println("地址不匹配");
            }

            if(url.length() == urlPath.length()) {
                this.url = url;
                return;
            }

            if(url.charAt(urlPath.length()) != 63) {
                System.out.println("地址不匹配");
            }

            this.url = urlPath;
            String paramsString = url.substring(urlPath.length() + 1);
            this.buildParams(paramsString);
        }

    }

    private void buildParams(String paramsString) {
        if(!StringUtils.isBlank(paramsString)) {
            this.params = new ArrayList();
            String[] theParams = paramsString.split("&");
            String[] var6 = theParams;
            int var7 = theParams.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String p = var6[var8];
                String[] keyValue = p.split("=", 2);
                String key = keyValue[0];
                String value;
                if(keyValue.length < 2) {
                    value = "";
                } else {
                    value = keyValue[1];
                }

                this.params.add(new HttpFormParameter(key, value));
            }

        }
    }

    public String buildGetRequestUrl() {
        String url = this.getUrl();
        String charSet = this.getCharSet();
        List params = this.getParams();
        String requestUrl = url;
        if(params != null && params.size() != 0) {
            ArrayList qparams = new ArrayList();
            Iterator appender = params.iterator();

            while(appender.hasNext()) {
                HttpFormParameter param = (HttpFormParameter)appender.next();
                qparams.add(new BasicNameValuePair(param.getName(), param.getValue() == null?"":param.getValue()));
            }

            String appender1 = URLEncodedUtils.format(qparams, charSet);
            if(url.indexOf("?") == -1) {
                requestUrl = url + "?" + appender1;
            } else {
                requestUrl = url + "&" + appender1;
            }
        }

        return requestUrl;
    }

    public String buildPostRequestForm(String formName) {
        String url = this.getUrl();
        String charSet = this.getCharSet();
        List params = this.getParams();
        StringBuilder buffer = new StringBuilder();
        buffer.append("<form id=\"").append(formName).append("\" name=\"").append(formName).append("\" template=\"").append(url).append("\" accept-charset=\"").append(charSet).append("\" method=\"" + this.getMethod().value() + "\">\n");
        Iterator var6 = params.iterator();

        while(var6.hasNext()) {
            HttpFormParameter param = (HttpFormParameter)var6.next();
            if(param.isHidden()) {
                buffer.append("<input type=\"hidden\" name=\"").append(param.getName()).append("\" value=\"").append(param.getValue()).append("\" />\n");
            } else {
                buffer.append("<input type=\"text\" name=\"").append(param.getName()).append("\" value=\"").append(param.getValue()).append("\" />\n");
            }
        }

        buffer.append("<input type=\"submit\" value=\"submit\" />");
        buffer.append("</form>");
        return buffer.toString();
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharSet() {
        return this.charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public List<HttpFormParameter> getParams() {
        if(this.params == null) {
            this.params = new ArrayList();
        }

        return this.params;
    }

    public void setParams(List<HttpFormParameter> params) {
        this.params = params;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[url]" + this.url);
        if(this.method != null) {
            sb.append("[method]" + this.method);
        }

        if(this.params != null && this.params.size() > 0) {
            sb.append("[params]-");
            Iterator var2 = this.params.iterator();

            while(var2.hasNext()) {
                HttpFormParameter param = (HttpFormParameter)var2.next();
                sb.append("[" + param.getName() + "]" + param.getValue());
            }
        }

        return sb.toString();
    }
}

