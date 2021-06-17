package com.test.sharding.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.test.sharding.dao.InsertMonthMapper;
import com.test.sharding.model.InsertMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InsertTestService {
    @Autowired
    private InsertMonthMapper mapper;

    public Object insert(List<InsertMonth> months) {
        long startTime = System.currentTimeMillis();
        // 这就是一个批量插入的方法，只不过数据多了可以分批，100 个 分成 50，50两次插入
        //int all = BatchInsertUtil.batchInsert(months, InsertMonthMapper.class, "insertList");
        int all = mapper.insertList(months);
        long endTime = System.currentTimeMillis();
        return "插入总数: " + all + " ，耗时： " + (endTime - startTime) / 1000 + " s.";
    }

    public Object one(InsertMonth insertMonth) {
        return mapper.insert(insertMonth);
    }

    public Object getAllDataByMonth(LocalDateTime min, LocalDateTime max) {
        LambdaQueryWrapper<InsertMonth> queryWrapper = Wrappers.lambdaQuery(InsertMonth.class);
        queryWrapper.between(InsertMonth::getCreateDateTime, min, max);
        return mapper.selectList(queryWrapper);
    }
}
