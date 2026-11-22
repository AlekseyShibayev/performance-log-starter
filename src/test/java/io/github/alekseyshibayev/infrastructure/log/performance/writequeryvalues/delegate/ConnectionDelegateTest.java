package io.github.alekseyshibayev.infrastructure.log.performance.writequeryvalues.delegate;

import io.github.alekseyshibayev.infrastructure.log.performance.writequeryvalues.common.ThrowingSQLSupplier;
import io.github.alekseyshibayev.infrastructure.log.performance.writequeryvalues.service.PerformanceLogStatisticStorage;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionDelegateTest {

    private static final String SQL = "select 1";

    private final PreparedStatement preparedStatement = stub(PreparedStatement.class);
    private final Statement statement = stub(Statement.class);
    private final RecordingStatisticStorage statisticStorage = new RecordingStatisticStorage();
    private final ConnectionDelegate connectionDelegate = ConnectionDelegate.of(connection(), statisticStorage);

    @Test
    void allPrepareStatementOverloadsReturnDelegate() throws SQLException {
        assertTrue(connectionDelegate.prepareStatement(SQL) instanceof PreparedStatementDelegate);
        assertTrue(connectionDelegate.prepareStatement(SQL, 1) instanceof PreparedStatementDelegate);
        assertTrue(connectionDelegate.prepareStatement(SQL, new int[]{1}) instanceof PreparedStatementDelegate);
        assertTrue(connectionDelegate.prepareStatement(SQL, new String[]{"id"}) instanceof PreparedStatementDelegate);
        assertTrue(connectionDelegate.prepareStatement(SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY) instanceof PreparedStatementDelegate);
        assertTrue(connectionDelegate.prepareStatement(SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT) instanceof PreparedStatementDelegate);
    }

    @Test
    void allCreateStatementOverloadsReturnDelegate() throws SQLException {
        assertTrue(connectionDelegate.createStatement() instanceof StatementDelegate);
        assertTrue(connectionDelegate.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY) instanceof StatementDelegate);
        assertTrue(connectionDelegate.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT) instanceof StatementDelegate);
    }

    @Test
    void prepareStatementWithResultSetTypeWritesStatistic() throws SQLException {
        connectionDelegate.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery();

        assertSame(preparedStatement, statisticStorage.lastExecutedStatement);
    }

    private Connection connection() {
        return (Connection) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{Connection.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "prepareStatement" -> preparedStatement;
                    case "createStatement" -> statement;
                    case "toString" -> "ConnectionStub";
                    case "hashCode" -> System.identityHashCode(proxy);
                    case "equals" -> proxy == args[0];
                    default -> null;
                });
    }

    private static <T> T stub(Class<T> type) {
        return type.cast(Proxy.newProxyInstance(ConnectionDelegateTest.class.getClassLoader(), new Class<?>[]{type},
                (proxy, method, args) -> switch (method.getName()) {
                    case "toString" -> type.getSimpleName() + "Stub";
                    case "hashCode" -> System.identityHashCode(proxy);
                    case "equals" -> proxy == args[0];
                    default -> null;
                }));
    }

    private static class RecordingStatisticStorage extends PerformanceLogStatisticStorage {

        private PreparedStatement lastExecutedStatement;

        private RecordingStatisticStorage() {
            super(null);
        }

        @Override
        public <T> T execute(PreparedStatement preparedStatement, ThrowingSQLSupplier<T> supplier) throws SQLException {
            lastExecutedStatement = preparedStatement;
            return supplier.executeQuery();
        }
    }
}
