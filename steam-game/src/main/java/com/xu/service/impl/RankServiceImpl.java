package com.xu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xu.bean.Rank;
import com.xu.bean.RankCustomWebDto;
import com.xu.common.Constant;
import com.xu.mapper.RankMapper;
import com.xu.service.RankService;
import com.xu.utility.RedisUtil;
import com.xu.utility.SnowFlakeUtility;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class RankServiceImpl implements RankService {
    private static final Logger logger = LoggerFactory.getLogger(RankServiceImpl.class);

    @Autowired
    private RankMapper rankMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public Rank insertRank(long gameId, long userId, int grade){
        Rank rank = new Rank();
        rank.setUserId(userId);
        rank.setRankId(new SnowFlakeUtility(1,1).nextId());
        rank.setGrade(grade);
        rank.setGameId(gameId);
        rank.setCreateUser(userId);
        rank.setUpdateUser(userId);
        return rank;
    }

    public List<RankCustomWebDto> selectRanks(long gameId, int grade, Timestamp updateDate){
//        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
//        RankMapper rankMapper2 = sqlSession.getMapper(RankMapper.class);
//        return rankMapper2.selectRanks(gameId,grade,updateDate);
        List<RankCustomWebDto> list = new ArrayList<>();
        PageHelper.startPage(1,10);
        Page<RankCustomWebDto> pages = (Page<RankCustomWebDto>)rankMapper.selectRank();
        PageInfo<RankCustomWebDto> pageInfo = new PageInfo<RankCustomWebDto>(pages);
        int nextPage = pageInfo.getNextPage();
        return list;
    }

    @Override
    public List<RankCustomWebDto> selectPageRanks(int pageNum,int pageSize) {
        List<RankCustomWebDto> list = new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize,true);
        List<RankCustomWebDto> pages = rankMapper.selectRank();
        PageInfo<RankCustomWebDto> pageInfo = new PageInfo<RankCustomWebDto>(pages);
        int nextPage = pageInfo.getNextPage();
        long total = pageInfo.getTotal();
        logger.info("总数={}",total);
        return pages;
    }

    public String test(){
        logger.info("测试成功,Game服务器1");
        redisUtil.increment(Constant.GAME_TEST_COUNT);
        return "测试成功,Game服务器1";
    }
}
