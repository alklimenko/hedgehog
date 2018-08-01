package ru.avklimenko.hedgehog;

import ru.avklimenko.hedgehog.exceptions.HHException;

import java.math.BigDecimal;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

public class SelectResult extends SelectResultData {
    SelectResult(String sql, HHConnection connection, Statement statement) {
        super(sql, connection, statement);
    }

    public String getString(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), String.class);
    }

    public String getString(int row, String title) {
        return getString(row, getColumnIndex(title));
    }

    public Byte getByte(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), Byte.class);
    }

    public Byte getByte(int row, String title) {
        return getByte(row, getColumnIndex(title));
    }

    public Short getShort(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), Short.class);
    }

    public Short getShort(int row, String title) {
        return getShort(row, getColumnIndex(title));
    }

    public Integer getInteger(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), Integer.class);
    }

    public Integer getInteger(int row, String title) {
        return getInteger(row, getColumnIndex(title));
    }

    public Long getLong(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), Long.class);
    }

    public Long getLong(int row, String title) {
        return getLong(row, getColumnIndex(title));
    }

    public Boolean getBoolean(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), Boolean.class);
    }

    public Boolean getBoolean(int row, String title) {
        return getBoolean(row, getColumnIndex(title));
    }

    public Float getFloat(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), Float.class);
    }

    public Float getFloat(int row, String title) {
        return getFloat(row, getColumnIndex(title));
    }

    public Double getDouble(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), Double.class);
    }

    public Double getDouble(int row, String title) {
        return getDouble(row, getColumnIndex(title));
    }

    public BigDecimal getBigDecimal(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), BigDecimal.class);
    }

    public BigDecimal getBigDecimal(int row, String title) {
        return getBigDecimal(row, getColumnIndex(title));
    }

    public Date getDate(int row, int column) {
        return ObjectMapper.cast(getData().get(row).get(column), Date.class);
    }

    public Date getDate(int row, String title) {
        return getDate(row, getColumnIndex(title));
    }

    private Class byteArrayClass = (new byte[]{0}).getClass();

    public byte[] getByteArray(int row, int column) {
        return (byte[]) ObjectMapper.cast(getData().get(row).get(column), byteArrayClass);
    }

    public byte[] getByteArray(int row, String title) {
        return getByteArray(row, getColumnIndex(title));
    }

    public Object getObject(int row, int column) {
        return getData().get(row).get(column);
    }

    public Object getObject(int row, String title) {
        return getObject(row, getColumnIndex(title));
    }

    public <T> T getObject(int row, int column, Class<T> tClass) {
        return ObjectMapper.cast(getData().get(row).get(column), tClass);
    }

    public <T> T getObject(int row, String title, Class<T> tClass) {
        return getObject(row, getColumnIndex(title), tClass);
    }

    public <T> T mapTo(Class<T> tClass) {
        join();
        throw new HHException("NotImplemented");
    }

    public <T> T mapTo(Class<T> tClass, Map<String, String> mapper) {
        join();
        throw new HHException("NotImplemented");
    }
}
