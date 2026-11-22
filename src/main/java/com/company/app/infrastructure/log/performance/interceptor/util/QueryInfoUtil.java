package com.company.app.infrastructure.log.performance.interceptor.util;

import lombok.experimental.UtilityClass;
import com.company.app.infrastructure.log.performance.PerformanceLogConfiguration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link PerformanceLogConfiguration}
 */
@UtilityClass
public class QueryInfoUtil {

    /**
     * Example:
     * input: List: select1, select2, select1, select1
     * output: List:
     * select1 - 3
     * select2 - 1
     */
    public static List<Map.Entry<String, Integer>> getSortedAndGroupedList(List<String> source) {
        Map<String, Integer> map = QueryInfoUtil.mapListToMapIdentityVsCounter(source);
        List<Map.Entry<String, Integer>> list = QueryInfoUtil.getEntryList(map);
        QueryInfoUtil.sort(list);
        return list;
    }

    /**
     * Words counter
     */
    static Map<String, Integer> mapListToMapIdentityVsCounter(List<String> list) {
        Map<String, Integer> map = new HashMap<>();
        for (String string : list) {
            if (map.containsKey(string)) {
                Integer integer = map.get(string);
                map.put(string, integer + 1);
            } else {
                map.put(string, 1);
            }
        }
        return map;
    }

    static List<Map.Entry<String, Integer>> getEntryList(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            list.add(Map.entry(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    static void sort(List<Map.Entry<String, Integer>> list) {
        list.sort(Comparator.comparing(QueryInfoUtil::apply).reversed());
    }

    private static Integer apply(Map.Entry<String, Integer> pair) {
        return pair.getValue();
    }

}