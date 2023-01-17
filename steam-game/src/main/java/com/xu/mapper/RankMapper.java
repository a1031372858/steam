package com.xu.mapper;

import com.xu.bean.Rank;
import com.xu.bean.RankCustomWebDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface RankMapper {

    int insertRank(Rank rank);

    List<RankCustomWebDto> selectRanks(@Param("gameId") Long gameId, @Param("grade") Integer grade, @Param("updateDate")Timestamp updateDate);


    List<RankCustomWebDto> selectRank();
}
