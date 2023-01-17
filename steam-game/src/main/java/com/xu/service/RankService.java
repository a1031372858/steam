package com.xu.service;

import com.xu.bean.Rank;
import com.xu.bean.RankCustomWebDto;

import java.sql.Timestamp;
import java.util.List;

public interface RankService {

    Rank insertRank(long gameId, long userId, int grade);

    public List<RankCustomWebDto> selectRanks(long gameId, int grade, Timestamp updateDate);

    public List<RankCustomWebDto> selectPageRanks(int pageNum,int pageSize);

    String test();
}
