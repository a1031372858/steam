package com.xu.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RankCustomWebDto implements InitializingBean, DisposableBean {

    private long userId ;
    private long gameId ;
    private long rankId ;
    private int grade ;
    private String displayName;

    public RankCustomWebDto() {
        System.out.println("RankCustomWebDto被创建");
    }

//    @Bean()
//    public RankCustomWebDto init(){
//        return new RankCustomWebDto();
//    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getRankId() {
        return rankId;
    }

    public void setRankId(long rankId) {
        this.rankId = rankId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("RankCustomWebDto被初始化");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("RankCustomWebDto被销毁");
    }
}
