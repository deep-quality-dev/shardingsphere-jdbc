package com.test.sharding.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.test.sharding.model.InsertMonth;
import com.test.sharding.service.InsertTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class InsertTestController {
    @Autowired
    InsertTestService insertTestService;

    @GetMapping(value = "insert")
    public Object insert() {
        return insertTestService.insert(createData());
    }


    @GetMapping(value = "one")
    public Object one() {
        InsertMonth insertMonth = createData().get(0);
        return insertTestService.one(insertMonth);
    }


    @GetMapping(value = "get")
    public Object get() {
        LocalDateTime now1 = LocalDateTime.now().minusMonths(1);
        LocalDateTime now2 = LocalDateTime.now().minusMonths(6);
        LocalDateTime min = LocalDateTime.of(now2.toLocalDate(), LocalTime.MIN);
        LocalDateTime max = LocalDateTime.of(now1.toLocalDate(), LocalTime.MAX);
        return insertTestService.getAllDataByMonth(min, max);
    }


    private List<InsertMonth> createData() {
        List<InsertMonth> months = new ArrayList<>();

//        LocalDateTime now = LocalDateTime.now().minusMonths(1);
//        LocalDateTime min = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
//        LocalDateTime max = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);
        for (int i = 1; i < 7; i++) {
            InsertMonth m = new InsertMonth();
            m.setFinanceNo(IdWorker.getIdStr());
            m.setShopId(i);
            m.setRemark(i + "");
            m.setCreateDateTime(LocalDateTime.now().minusMonths(i));
            months.add(m);
        }
        return months;
    }

}
