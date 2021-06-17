package com.test.sharding.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.sharding.model.InsertMonth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 */
@Repository
public interface InsertMonthMapper extends BaseMapper<InsertMonth> {

    int insertList(@Param("subList") List<InsertMonth> subList);

    List<InsertMonth> getAllDataByMonth(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
