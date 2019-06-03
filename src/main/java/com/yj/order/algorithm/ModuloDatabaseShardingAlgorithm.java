package com.yj.order.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 分库规则
 */
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<String> {

    private static final int DATABASE_NUM = 2;

    /**
     * sql == 规则
     * @param collection
     * @param shardingValue
     * @return
     */
    @Override
    public String doEqualSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        for (String each : collection) {
            if (each.endsWith(uuid2Id(shardingValue.getValue()) % DATABASE_NUM + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * sql in 规则
     * @param collection
     * @param shardingValue
     * @return
     */
    @Override
    public Collection<String> doInSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(collection.size());
        for (String value : shardingValue.getValues()) {
            for (String tableName : collection) {
                if (tableName.endsWith(uuid2Id(value) % DATABASE_NUM + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    /**
     * sql between 规则
     * @param collection
     * @param shardingValue
     * @return
     */
    @Override
    public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(collection.size());
        Range<String> range = shardingValue.getValueRange();
        for (int i = uuid2Id(range.lowerEndpoint()); i <= uuid2Id(range.upperEndpoint()); i++) {
            for (String each : collection) {
                if (each.endsWith(i % DATABASE_NUM + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    private int uuid2Id(String uuid) {
        if(uuid == null || "".equals(uuid)) {
            return 0;
        }
        uuid = uuid.replaceAll("[a-zA-Z]", "");
        char[] nums = uuid.toCharArray();
        int result = 0;
        for(char num : nums) {
            result += Integer.parseInt(String.valueOf(num));
        }
        return result % DATABASE_NUM;
    }
}
