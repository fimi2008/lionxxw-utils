package com.lionxxw.utils.http;

import java.io.Serializable;

/**
 * Created by Dinglulu on 2016/8/2.
 */
public class SimpleHttpResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private int statusCode;
    private String entityString;
    private String errorMessage;

    public SimpleHttpResponse(int statusCode, String entityString, String errorMessage) {
        this.statusCode = statusCode;
        this.entityString = entityString;
        this.errorMessage = errorMessage;
    }

    public boolean isRequestSuccess() {
        return HttpUtil.isRequestSuccess(this.statusCode);
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getEntityString() {
        return this.entityString;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}

