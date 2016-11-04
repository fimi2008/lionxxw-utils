package com.lionxxw.utils.http;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by Dinglulu on 2016/8/2.
 */
public class SimpleHttpClient {
    private HttpClient httpclient;
    private int connectionTimeout;
    private int soTimeout;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
    private static final int DEFAULT_SO_TIMEOUT = 60000;

    public SimpleHttpClient() {
        this.connectionTimeout = 30000;
        this.soTimeout = '\uea60';
        this.httpclient = new DefaultHttpClient();
        this.httpclient.getParams().setParameter("http.connection.timeout", Integer.valueOf(this.connectionTimeout));
        this.httpclient.getParams().setParameter("http.socket.timeout", Integer.valueOf(this.soTimeout));
    }

    public SimpleHttpClient(int connectionTimeout, int soTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
        this.httpclient = new DefaultHttpClient();
        this.httpclient.getParams().setParameter("http.connection.timeout", Integer.valueOf(connectionTimeout));
        this.httpclient.getParams().setParameter("http.socket.timeout", Integer.valueOf(soTimeout));
    }

    public void enableSSL() {
        try {
            SSLContext e = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            e.init((KeyManager[])null, new TrustManager[]{tm}, (SecureRandom)null);
            SSLSocketFactory ssf = new SSLSocketFactory(e, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = this.httpclient.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
        } catch (Exception var6) {
            ;
        }

    }

    public HttpClient getHttpclient() {
        return this.httpclient;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        this.httpclient.getParams().setParameter("http.connection.timeout", Integer.valueOf(connectionTimeout));
    }

    public int getSoTimeout() {
        return this.soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
        this.httpclient.getParams().setParameter("http.socket.timeout", Integer.valueOf(soTimeout));
    }
}
