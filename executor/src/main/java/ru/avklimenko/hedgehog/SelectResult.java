package ru.avklimenko.hedgehog;

import ru.avklimenko.hedgehog.exceptions.HHException;
import ru.avklimenko.hedgehog.exceptions.HHSQLSyntaxErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectResult extends Result {
    protected List<String> titles;
    protected Map<String, Integer> colIndex = new HashMap<>();
    protected List<List<Object>> data = new ArrayList<>();

    SelectResult(String sql, HHConnection connection, Statement statement) {
        super(sql, connection, statement);
    }

    void selectAsync() {
        logger.debug("[SELECT]==> " + sql.replaceAll("\r?\n", " "));
        try (ResultSet resultSet = statement.executeQuery(sql)) {
            // Get column titles
            int columns = resultSet.getMetaData().getColumnCount();
            setColumns(columns);
            for (int i = 0; i < columns; ++i) {
                setTitle(i, resultSet.getMetaData().getColumnName(i + 1));
            }

            // Get data
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 0; i < columns; ++i) {
                    row.add(resultSet.getObject(1 + i));
                }
                addRow(row);
            }

            logger.debug("[SELECT]<== " + data.size() + " rows");
            connection.setFree();
        } catch (SQLException e) {
            setSuccess(false);
            logger.error("[SELECT] Error while execute select query: " + e.toString());
            throw new HHSQLSyntaxErrorException("[SELECT] Error while execute select query: " + e.toString(), e);
        }
    }

    private void setColumns(int columns) {
        titles = new ArrayList<>(columns);
        for (int i = 0; i < columns; ++i) {
            titles.add("COLUMN N" + i);
        }
    }

    private void setTitle(int column, String title) {
        titles.set(column, title);
        colIndex.put(title, column);
    }

    private void addRow(List<Object> row) {
        data.add(row);
    }

    public String getTitle(int column) {
        join();
        return titles.get(column);
    }

    public int size() {
        join();
        return data.size();
    }

    public int getColumns() {
        join();
        return titles.size();
    }

    public List<Object> getRow(int row) {
        join();
        return data.get(row);
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
