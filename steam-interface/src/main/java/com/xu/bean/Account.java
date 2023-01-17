package com.xu.bean;

import java.math.BigDecimal;

public class Account extends DtoBase{
    private Long userId;
    private BigDecimal gold;
    private BigDecimal diamond;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    public BigDecimal getDiamond() {
        return diamond;
    }

    public void setDiamond(BigDecimal diamond) {
        this.diamond = diamond;
    }
}
