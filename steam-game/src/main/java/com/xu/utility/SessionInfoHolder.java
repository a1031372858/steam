package com.xu.utility;

import com.xu.bean.model.SessionInfo;

import java.util.Map;

public class SessionInfoHolder {
    private static final ThreadLocal<SessionInfo>  CONTEXT = new ThreadLocal<>();;

    public static SessionInfo getSessionInfo() {
        return CONTEXT.get();
    }

    public static void setSessionInfo(SessionInfo sessionInfo) {
        SessionInfoHolder.CONTEXT.set(sessionInfo);
    }

    public static void clear(){
        CONTEXT.remove();
    }
}
