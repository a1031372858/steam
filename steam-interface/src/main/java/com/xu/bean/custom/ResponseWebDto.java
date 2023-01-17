package com.xu.bean.custom;

import java.io.Serializable;

public class ResponseWebDto implements Serializable {
    private Object responseContent;
    private int status;
    private String message;

    public Object getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(Object responseContent) {
        this.responseContent = responseContent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
