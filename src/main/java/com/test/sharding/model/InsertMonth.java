package com.test.sharding.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("insert_month")
public class InsertMonth implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 流水号
     */
    @TableId(value = "finance_no", type = IdType.ASSIGN_ID)
    private String financeNo;

    /**
     * 备注
     */
    private String remark;
    /**
     * 店铺id 分库字段
     */
    private Integer shopId;

    /**
     * 时间 分表字段
     */
    private LocalDateTime createDateTime;


}
