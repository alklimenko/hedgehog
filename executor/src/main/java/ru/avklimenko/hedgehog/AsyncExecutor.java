package ru.avklimenko.hedgehog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;

class AsyncExecutor {
    protected static Logger logger = LoggerFactory.getLogger(AsyncExecutor.class);

    protected Statement statement;
    protected HHConnection connection;
    protected String sql;

    AsyncExecutor(String sql, HHConnection connection, Statement statement) {
        this.statement = statement;
        this.connection = connection;
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
