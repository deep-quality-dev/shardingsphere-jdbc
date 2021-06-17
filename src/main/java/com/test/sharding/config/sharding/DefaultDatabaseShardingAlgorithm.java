package com.test.sharding.config.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author lr
 * 分库的自定义类(精确)
 */
public class DefaultDatabaseShardingAlgorithm extends CommonShardDataBase implements PreciseShardingAlgorithm<Integer> {
    /**
     * @param collection 存放的是所有的库的列表
     * @return 将数据写入的哪个库
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        Integer shopId = preciseShardingValue.getValue();
        String database = getDatabaseByShopId(shopId % 2 + "");
        for (String each : collection) {
            if (database.equals(each)) {
                return database;
            }
        }
        throw new UnsupportedOperationException(" database not found by shopId ,please config first ");
    }
}