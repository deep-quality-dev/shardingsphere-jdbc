package com.test.sharding.config.sharding;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedHashSet;


/**
 * @author lr
 * 分库的自定义类(范围)
 */
public class DefaultDatabaseRangeShardingAlgorithm extends CommonShardDataBase implements RangeShardingAlgorithm<String> {

    protected static final DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         RangeShardingValue<String> rangeShardingValue) {
        Range<String> ranges = rangeShardingValue.getValueRange();

        String lower = ranges.lowerEndpoint();
        String upper = ranges.upperEndpoint();

        LocalDateTime start = LocalDateTime.parse(lower, dtfTime);
        LocalDateTime end = LocalDateTime.parse(upper, dtfTime);

        int startYear = start.getYear();
        int endYear = end.getYear();

        Collection<String> databases = new LinkedHashSet<>();
        if (start.isBefore(end) && (endYear - startYear) == 0) {
            for (String c : availableTargetNames) {
                int cYear = Integer.parseInt(c.substring(c.length() - 4, c.length()));
                if (cYear >= startYear && cYear <= endYear) {
                    databases.add(c);
                }
            }
        }
        return databases;
    }
}