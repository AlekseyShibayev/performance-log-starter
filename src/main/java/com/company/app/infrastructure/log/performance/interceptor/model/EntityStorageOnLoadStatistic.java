package com.company.app.infrastructure.log.performance.interceptor.model;


import java.util.ArrayList;
import java.util.List;

import com.company.app.infrastructure.log.performance.interceptor.util.QueryInfoUtil;
import com.company.app.infrastructure.log.performance.PerformanceLogConfiguration;


/**
 * {@link PerformanceLogConfiguration}
 */
public class EntityStorageOnLoadStatistic {

    private int totalEntityLoadAttemptCounter;
    private final List<String> entityNames = new ArrayList<>();

    public void changeOnLoad(Object entity) {
        String entityName = entity.getClass().getName();
        entityNames.add(entityName);
        this.totalEntityLoadAttemptCounter = totalEntityLoadAttemptCounter + 1;
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();

        stringBuilder.append(("\n" +
                "onLoad: total: [%s], types: ").formatted(totalEntityLoadAttemptCounter));

        var list = QueryInfoUtil.getSortedAndGroupedList(entityNames);
        list.forEach(pair -> stringBuilder.append("\n").append("x").append(pair.getValue()).append(" ").append(pair.getKey()));

        return stringBuilder.toString();
    }

}