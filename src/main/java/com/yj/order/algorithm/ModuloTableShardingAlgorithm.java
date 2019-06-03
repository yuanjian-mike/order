package com.yj.order.algorithm;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 分表规则
 */
public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<String> {

    private static final int TABLE_NUM = 2;

    @Override
    public String doEqualSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        for (String each : collection) {
            if (each.endsWith(uuid2Id(shardingValue.getValue()) % TABLE_NUM + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(collection.size());
        for (String value : shardingValue.getValues()) {
            for (String tableName : collection) {
                if (tableName.endsWith(uuid2Id(value) % TABLE_NUM + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> collection, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(collection.size());
        Range<String> range = shardingValue.getValueRange();
        for (int i = uuid2Id(range.lowerEndpoint()); i <= uuid2Id(range.upperEndpoint()); i++) {
            for (String each : collection) {
                if (each.endsWith(i % TABLE_NUM + "")) {
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
        if(uuid.length() > 8) {
            uuid = uuid.substring(0, 8);
        }
        return Integer.parseInt(uuid) % TABLE_NUM;
    }
}
