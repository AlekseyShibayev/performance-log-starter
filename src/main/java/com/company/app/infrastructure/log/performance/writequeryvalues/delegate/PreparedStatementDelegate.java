package com.company.app.infrastructure.log.performance.writequeryvalues.delegate;

import com.company.app.infrastructure.log.performance.writequeryvalues.service.PerformanceLogStatisticStorage;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class PreparedStatementDelegate implements PreparedStatement {

    /**
     * All work do original PreparedStatement.
     */
    private final PreparedStatement preparedStatement;
    private final PerformanceLogStatisticStorage statisticStorage;

    private PreparedStatementDelegate(PreparedStatement preparedStatement, PerformanceLogStatisticStorage statisticStorage) {
        this.preparedStatement = preparedStatement;
        this.statisticStorage = statisticStorage;
    }

    public static PreparedStatementDelegate of(PreparedStatement preparedStatement, PerformanceLogStatisticStorage statisticStorage) {
        if (preparedStatement instanceof PreparedStatementDelegate preparedStatementDelegate) {
            return preparedStatementDelegate;
        } else {
            return new PreparedStatementDelegate(preparedStatement, statisticStorage);
        }
    }

    /**
     * Overriding
     */
    @Override
    public ResultSet executeQuery() throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeQuery());
    }

    @Override
    public ResultSet executeQuery(String s) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeQuery(s));
    }

    @Override
    public int executeUpdate() throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeUpdate());
    }

    @Override
    public boolean execute() throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.execute());
    }

    @Override
    public boolean execute(String s) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.execute(s));
    }

    @Override
    public long executeLargeUpdate() throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeLargeUpdate());
    }

    @Override
    public int executeUpdate(String s) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeUpdate(s));
    }

    @Override
    public int executeUpdate(String s, int i) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeUpdate(s, i));
    }

    @Override
    public int executeUpdate(String s, int[] ints) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeUpdate(s, ints));
    }

    @Override
    public int executeUpdate(String s, String[] strings) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeUpdate(s, strings));
    }

    @Override
    public boolean execute(String s, int i) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.execute(s, i));
    }

    @Override
    public boolean execute(String s, int[] ints) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.execute(s, ints));
    }

    @Override
    public boolean execute(String s, String[] strings) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.execute(s, strings));
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeBatch());
    }

    @Override
    public void setNull(int i, int i1) throws SQLException {
        preparedStatement.setNull(i, i1);
    }

    @Override
    public void setBoolean(int i, boolean b) throws SQLException {
        preparedStatement.setBoolean(i, b);
    }

    @Override
    public void setByte(int i, byte b) throws SQLException {
        preparedStatement.setByte(i, b);
    }

    @Override
    public void setShort(int i, short i1) throws SQLException {
        preparedStatement.setShort(i, i1);
    }

    @Override
    public void setInt(int i, int i1) throws SQLException {
        preparedStatement.setInt(i, i1);
    }

    @Override
    public void setLong(int i, long l) throws SQLException {
        preparedStatement.setLong(i, l);
    }

    @Override
    public void setFloat(int i, float v) throws SQLException {
        preparedStatement.setFloat(i, v);
    }

    @Override
    public void setDouble(int i, double v) throws SQLException {
        preparedStatement.setDouble(i, v);
    }

    @Override
    public void setBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {
        preparedStatement.setBigDecimal(i, bigDecimal);
    }

    @Override
    public void setString(int i, String s) throws SQLException {
        preparedStatement.setString(i, s);
    }

    @Override
    public void setBytes(int i, byte[] bytes) throws SQLException {
        preparedStatement.setBytes(i, bytes);
    }

    @Override
    public void setDate(int i, Date date) throws SQLException {
        preparedStatement.setDate(i, date);
    }

    @Override
    public void setTime(int i, Time time) throws SQLException {
        preparedStatement.setTime(i, time);
    }

    @Override
    public void setTimestamp(int i, Timestamp timestamp) throws SQLException {
        preparedStatement.setTimestamp(i, timestamp);
    }

    @Override
    public void setAsciiStream(int i, InputStream inputStream, int i1) throws SQLException {
        preparedStatement.setAsciiStream(i, inputStream, i1);
    }

    @Override
    @Deprecated(since = "1.2")
    public void setUnicodeStream(int i, InputStream inputStream, int i1) throws SQLException {
        preparedStatement.setUnicodeStream(i, inputStream, i1);
    }

    @Override
    public void setBinaryStream(int i, InputStream inputStream, int i1) throws SQLException {
        preparedStatement.setBinaryStream(i, inputStream, i1);
    }

    @Override
    public void clearParameters() throws SQLException {
        preparedStatement.clearParameters();
    }

    @Override
    public void setObject(int i, Object o, int i1) throws SQLException {
        preparedStatement.setObject(i, o, i1);
    }

    @Override
    public void setObject(int i, Object o) throws SQLException {
        preparedStatement.setObject(i, o);
    }

    @Override
    public void addBatch() throws SQLException {
        preparedStatement.addBatch();
    }

    @Override
    public void setCharacterStream(int i, Reader reader, int i1) throws SQLException {
        preparedStatement.setCharacterStream(i, reader, i1);
    }

    @Override
    public void setRef(int i, Ref ref) throws SQLException {
        preparedStatement.setRef(i, ref);
    }

    @Override
    public void setBlob(int i, Blob blob) throws SQLException {
        preparedStatement.setBlob(i, blob);
    }

    @Override
    public void setClob(int i, Clob clob) throws SQLException {
        preparedStatement.setClob(i, clob);
    }

    @Override
    public void setArray(int i, Array array) throws SQLException {
        preparedStatement.setArray(i, array);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return preparedStatement.getMetaData();
    }

    @Override
    public void setDate(int i, Date date, Calendar calendar) throws SQLException {
        preparedStatement.setDate(i, date, calendar);
    }

    @Override
    public void setTime(int i, Time time, Calendar calendar) throws SQLException {
        preparedStatement.setTime(i, time, calendar);
    }

    @Override
    public void setTimestamp(int i, Timestamp timestamp, Calendar calendar) throws SQLException {
        preparedStatement.setTimestamp(i, timestamp, calendar);
    }

    @Override
    public void setNull(int i, int i1, String s) throws SQLException {
        preparedStatement.setNull(i, i1, s);
    }

    @Override
    public void setURL(int i, URL url) throws SQLException {
        preparedStatement.setURL(i, url);
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return preparedStatement.getParameterMetaData();
    }

    @Override
    public void setRowId(int i, RowId rowId) throws SQLException {
        preparedStatement.setRowId(i, rowId);
    }

    @Override
    public void setNString(int i, String s) throws SQLException {
        preparedStatement.setNString(i, s);
    }

    @Override
    public void setNCharacterStream(int i, Reader reader, long l) throws SQLException {
        preparedStatement.setNCharacterStream(i, reader, l);
    }

    @Override
    public void setNClob(int i, NClob nClob) throws SQLException {
        preparedStatement.setNClob(i, nClob);
    }

    @Override
    public void setClob(int i, Reader reader, long l) throws SQLException {
        preparedStatement.setClob(i, reader, l);
    }

    @Override
    public void setBlob(int i, InputStream inputStream, long l) throws SQLException {
        preparedStatement.setBlob(i, inputStream, l);
    }

    @Override
    public void setNClob(int i, Reader reader, long l) throws SQLException {
        preparedStatement.setNClob(i, reader, l);
    }

    @Override
    public void setSQLXML(int i, SQLXML sqlxml) throws SQLException {
        preparedStatement.setSQLXML(i, sqlxml);
    }

    @Override
    public void setObject(int i, Object o, int i1, int i2) throws SQLException {
        preparedStatement.setObject(i, o, i1, i2);
    }

    @Override
    public void setAsciiStream(int i, InputStream inputStream, long l) throws SQLException {
        preparedStatement.setAsciiStream(i, inputStream, l);
    }

    @Override
    public void setBinaryStream(int i, InputStream inputStream, long l) throws SQLException {
        preparedStatement.setBinaryStream(i, inputStream, l);
    }

    @Override
    public void setCharacterStream(int i, Reader reader, long l) throws SQLException {
        preparedStatement.setCharacterStream(i, reader, l);
    }

    @Override
    public void setAsciiStream(int i, InputStream inputStream) throws SQLException {
        preparedStatement.setAsciiStream(i, inputStream);
    }

    @Override
    public void setBinaryStream(int i, InputStream inputStream) throws SQLException {
        preparedStatement.setBinaryStream(i, inputStream);
    }

    @Override
    public void setCharacterStream(int i, Reader reader) throws SQLException {
        preparedStatement.setCharacterStream(i, reader);
    }

    @Override
    public void setNCharacterStream(int i, Reader reader) throws SQLException {
        preparedStatement.setNCharacterStream(i, reader);
    }

    @Override
    public void setClob(int i, Reader reader) throws SQLException {
        preparedStatement.setClob(i, reader);
    }

    @Override
    public void setBlob(int i, InputStream inputStream) throws SQLException {
        preparedStatement.setBlob(i, inputStream);
    }

    @Override
    public void setNClob(int i, Reader reader) throws SQLException {
        preparedStatement.setNClob(i, reader);
    }

    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        preparedStatement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
        preparedStatement.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public void close() throws SQLException {
        preparedStatement.close();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return preparedStatement.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int i) throws SQLException {
        preparedStatement.setMaxFieldSize(i);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return preparedStatement.getMaxRows();
    }

    @Override
    public void setMaxRows(int i) throws SQLException {
        preparedStatement.setMaxRows(i);
    }

    @Override
    public void setEscapeProcessing(boolean b) throws SQLException {
        preparedStatement.setEscapeProcessing(b);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return preparedStatement.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int i) throws SQLException {
        preparedStatement.setQueryTimeout(i);
    }

    @Override
    public void cancel() throws SQLException {
        preparedStatement.cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return preparedStatement.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        preparedStatement.clearWarnings();
    }

    @Override
    public void setCursorName(String s) throws SQLException {
        preparedStatement.setCursorName(s);
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return preparedStatement.getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return preparedStatement.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return preparedStatement.getMoreResults();
    }

    @Override
    public void setFetchDirection(int i) throws SQLException {
        preparedStatement.setFetchDirection(i);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return preparedStatement.getFetchDirection();
    }

    @Override
    public void setFetchSize(int i) throws SQLException {
        preparedStatement.setFetchSize(i);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return preparedStatement.getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return preparedStatement.getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return preparedStatement.getResultSetType();
    }

    @Override
    public void addBatch(String s) throws SQLException {
        preparedStatement.addBatch(s);
    }

    @Override
    public void clearBatch() throws SQLException {
        preparedStatement.clearBatch();
    }


    @Override
    public Connection getConnection() throws SQLException {
        return preparedStatement.getConnection();
    }

    @Override
    public boolean getMoreResults(int i) throws SQLException {
        return preparedStatement.getMoreResults(i);
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return preparedStatement.getGeneratedKeys();
    }


    @Override
    public int getResultSetHoldability() throws SQLException {
        return preparedStatement.getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return preparedStatement.isClosed();
    }

    @Override
    public void setPoolable(boolean b) throws SQLException {
        preparedStatement.setPoolable(b);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return preparedStatement.isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        preparedStatement.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return preparedStatement.isCloseOnCompletion();
    }

    @Override
    public long getLargeUpdateCount() throws SQLException {
        return preparedStatement.getLargeUpdateCount();
    }

    @Override
    public void setLargeMaxRows(long max) throws SQLException {
        preparedStatement.setLargeMaxRows(max);
    }

    @Override
    public long getLargeMaxRows() throws SQLException {
        return preparedStatement.getLargeMaxRows();
    }

    @Override
    public long[] executeLargeBatch() throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeLargeBatch());
    }

    @Override
    public long executeLargeUpdate(String sql) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeLargeUpdate(sql));
    }

    @Override
    public long executeLargeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeLargeUpdate(sql, autoGeneratedKeys));
    }

    @Override
    public long executeLargeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeLargeUpdate(sql, columnIndexes));
    }

    @Override
    public long executeLargeUpdate(String sql, String[] columnNames) throws SQLException {
        return statisticStorage.execute(preparedStatement, () -> preparedStatement.executeLargeUpdate(sql, columnNames));
    }

    @Override
    public String enquoteLiteral(String val) throws SQLException {
        return preparedStatement.enquoteLiteral(val);
    }

    @Override
    public String enquoteIdentifier(String identifier, boolean alwaysQuote) throws SQLException {
        return preparedStatement.enquoteIdentifier(identifier, alwaysQuote);
    }

    @Override
    public boolean isSimpleIdentifier(String identifier) throws SQLException {
        return preparedStatement.isSimpleIdentifier(identifier);
    }

    @Override
    public String enquoteNCharLiteral(String val) throws SQLException {
        return preparedStatement.enquoteNCharLiteral(val);
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return preparedStatement.unwrap(aClass);
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return preparedStatement.isWrapperFor(aClass);
    }
}
