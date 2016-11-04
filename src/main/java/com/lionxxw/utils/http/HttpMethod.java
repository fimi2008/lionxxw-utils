package com.lionxxw.utils.http;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Dinglulu on 2016/8/2.
 */
@XmlType(
        name = "httpMethod"
)
@XmlEnum
public enum HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    DELETE,
    TRACE,
    OPTIONS;

    private HttpMethod() {
    }

    public String value() {
        return this.name();
    }

    public static HttpMethod fromValue(String v) {
        return valueOf(v);
    }

    public static HttpMethod getDefault() {
        return POST;
    }
}

