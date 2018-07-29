package ru.avklimenko.hedgehog;

import ru.avklimenko.hedgehog.exceptions.HHInvalidParameterException;
import ru.avklimenko.hedgehog.exceptions.HHSQLSyntaxErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SelectResultData extends Result {
    private List<String> titles;
    private Map<String, Integer> columnIndices = new HashMap<>();
    private List<List<Object>> data = new ArrayList<>();

    SelectResultData(String sql, HHConnection connection, Statement statement) {
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
        columnIndices.put(title, column);
    }

    private void addRow(List<Object> row) {
        data.add(row);
    }

    protected List<List<Object>> getData() {
        join();
        return data;
    }

    protected List<String> getTitles() {
        join();
        return titles;
    }

    protected Map<String, Integer> getColumnIndices() {
        join();
        return columnIndices;
    }

    public String getTitle(int column) {
        return getTitles().get(column);
    }

    public int getRowsCount() {
        return getData().size();
    }

    public int getColumnsCount() {
        return getTitles().size();
    }

    public List<Object> getRow(int row) {
        return getData().get(row);
    }

    public int getColumnIndex(String title) {
        if (!getColumnIndices().containsKey(title)) {
            throw new HHInvalidParameterException("The result does not contains \"" + title + "\" column");
        }
        return getColumnIndices().get(title);
    }
}
