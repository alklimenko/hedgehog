package ru.avklimenko.hedgehog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.avklimenko.hedgehog.exceptions.HHSQLSyntaxErrorException;

import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;


/**
 * Query executor.
 **/
public class HHExecutor {
    private static Logger logger = LoggerFactory.getLogger(HHExecutor.class);
    private String connectionString;
    private String packageName;
    private int timeout = 30;

    public HHExecutor(String connectionString, String packageName) {
        this.connectionString = connectionString;
        this.packageName = packageName.replaceAll("^/*", "");
    }

    public HHExecutor(String connectionString) {
        this.connectionString = connectionString;
        this.packageName = null;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    ////////// UPDATE //////////
    /** Execution of queries that do not return a result (INSERT/UPDATE/DELETE etc). */
    public UpdateResult update(String sql) {
        HHConnection connection = HHConnectionPool.getInstance().getConnection(connectionString);
        try (Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(timeout);
            UpdateResult result = new UpdateResult(sql, connection, statement);
            result.runAsync(result::updateAsync);
            return result;
        } catch (SQLException e) {
            connection.setFree();
            throw new HHSQLSyntaxErrorException("[UPDATE] Error while preparing update query: " + e.toString(), e);
        }
    }

    /** Execute update query from resource file. */
    public UpdateResult updateFromResource(String resourceName, Map<String, Object> context) {
        String sql = SQLBuilder.getInstance().getSQLFromResource(packageName + resourceName, context);
        return update(sql);
    }

    /** Execute update query from file. */
    public UpdateResult updateFromFile(File file, Map<String, Object> context) {
        String sql = SQLBuilder.getInstance().getSQLFromFile(file.getName(), context);
        return update(sql);
    }

    /** Execute update query from file. */
    public UpdateResult updateFromFile(String filename, Map<String, Object> context) {
        String sql = SQLBuilder.getInstance().getSQLFromFile(filename, context);
        return update(sql);
    }

    ////////// SELECT //////////
    /** Execution of queries that return a result.
     * @param sql Query text
     * @return result
     * */
    public SelectResult select(String sql) {
        HHConnection connection = HHConnectionPool.getInstance().getConnection(connectionString);
        try (Statement statement = connection.createStatement()) {
            statement.setQueryTimeout(timeout);
            SelectResult result = new SelectResult(sql, connection, statement);
            result.runAsync(result::selectAsync);
            return result;
        } catch (SQLException e) {
            connection.setFree();
            throw new HHSQLSyntaxErrorException("[SELECT] Error while prepare select query: " + e.toString(), e);
        }
    }

    /**
     * Execute select query from resource file.
     * @param resourceName the name of a resource with a text of the request
     * @param context query parameters
     * @return result
     * */
    public SelectResult selectFromResource(String resourceName, Map<String, Object> context) {
        String sql = SQLBuilder.getInstance().getSQLFromResource(packageName + resourceName, context);
        return select(sql);
    }

    /**
     * Execute select query from file.
     * @param file the file with a text of the request
     * @param context query parameters
     * @return result
     * */
    public SelectResult selectFromFile(File file, Map<String, Object> context) {
        String sql = SQLBuilder.getInstance().getSQLFromFile(file.getName(), context);
        return select(sql);
    }

    /**
     * Execute select query from file.
     * @param filename the file with a text of the request
     * @param context query parameters
     * @return result
     * */
    public SelectResult selectFromFile(String filename, Map<String, Object> context) {
        String sql = SQLBuilder.getInstance().getSQLFromFile(filename, context);
        return select(sql);
    }
}
