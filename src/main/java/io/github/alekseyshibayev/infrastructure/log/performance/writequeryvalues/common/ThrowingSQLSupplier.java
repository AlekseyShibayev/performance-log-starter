package io.github.alekseyshibayev.infrastructure.log.performance.writequeryvalues.common;

import java.sql.SQLException;

@FunctionalInterface
public interface ThrowingSQLSupplier<T> {

    T executeQuery() throws SQLException;

}
