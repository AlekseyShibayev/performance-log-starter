package io.github.alekseyshibayev.infrastructure.log.performance.interceptor.model;

import java.util.ArrayList;
import java.util.List;

public class QueryWithValuesStatistic {

    private final List<String> sqlList = new ArrayList<>();

    public void add(String sql) {
        sqlList.add(sql);
    }

    @Override
    public String toString() {
        if (sqlList.isEmpty()) {
            return  "";
        }

        var stringBuilder = new StringBuilder();
        stringBuilder.append("\nsql with values: ");
        sqlList.forEach(sql -> stringBuilder.append("\n").append(sql));

        return stringBuilder.toString();
    }

}
