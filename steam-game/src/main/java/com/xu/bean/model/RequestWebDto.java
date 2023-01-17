package com.xu.bean.model;

import java.io.Serializable;

public class RequestWebDto implements Serializable {
    private String terminalId;
    private Object param;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
