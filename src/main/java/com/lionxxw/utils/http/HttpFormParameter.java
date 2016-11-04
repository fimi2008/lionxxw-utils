package com.lionxxw.utils.http;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Dinglulu on 2016/8/2.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HttpFormParameter implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String value;
    private boolean hidden;

    public HttpFormParameter() {
    }

    public HttpFormParameter(String name, String value) {
        this(name, value, true);
    }

    public HttpFormParameter(String name, String value, boolean hidden) {
        this.name = name;
        this.value = value;
        this.hidden = hidden;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}