package io.github.alekseyshibayev.infrastructure.log.performance.interceptor.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;


class QueryInfoUtilTest {

    @Test
    void test() {
        List<String> strings = List.of("1", "2", "1");

        Map<String, Integer> stringIntegerMap = QueryInfoUtil.mapListToMapIdentityVsCounter(strings);

        Assertions.assertEquals(2, stringIntegerMap.get("1"));
        Assertions.assertEquals(1, stringIntegerMap.get("2"));
    }

    @Test
    void test2() {
        List<String> strings = List.of("1", "2", "1", "3", "3", "3");

        List<Map.Entry<String, Integer>> immutablePairList = QueryInfoUtil.getSortedAndGroupedList(strings);

        Assertions.assertEquals("3", immutablePairList.get(0).getKey());
    }

}