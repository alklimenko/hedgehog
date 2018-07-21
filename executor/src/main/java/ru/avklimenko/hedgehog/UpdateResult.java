package ru.avklimenko.hedgehog;

import ru.avklimenko.hedgehog.exceptions.HHSQLSyntaxErrorException;

import java.sql.SQLException;
import java.sql.Statement;

public class UpdateResult extends Result {
    private int rowsAffected;

    UpdateResult(String sql, HHConnection connection, Statement statement) {
        super(sql, connection, statement);
        rowsAffected = -1;
    }

    void updateAsync() {
        try {
            logger.debug("[UPDATE]==> " + sql.replaceAll("\r?\n", " "));
            rowsAffected = statement.executeUpdate(sql);
            logger.debug("[UPDATE]<== " + rowsAffected + " rows affected");
            connection.setFree();
        } catch (SQLException e) {
            setSuccess(false);
            logger.error("[SELECT] Error while execute select query: " + e.toString());
            throw new HHSQLSyntaxErrorException("[SELECT] Error while execute select query: " + e.toString(), e);
        }
    }

    public int getRowsAffected() {
        return rowsAffected;
    }
}
