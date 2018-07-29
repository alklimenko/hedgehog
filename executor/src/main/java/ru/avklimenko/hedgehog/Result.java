package ru.avklimenko.hedgehog;

import java.sql.Statement;
import java.util.concurrent.CompletableFuture;

class Result extends AsyncExecutor {
    private CompletableFuture<Void> asyncRes = null;
    private boolean success;

    Result(String sql, HHConnection connection, Statement statement) {
        super(sql, connection, statement);
        success = true;
    }

    void runAsync(Runnable runnable) {
        asyncRes = CompletableFuture.runAsync(runnable);
    }

    public void join() {
        if (asyncRes != null) {
            asyncRes.join();
            asyncRes = null;
        }
    }

    public boolean isSuccess() {
        join();
        return success;
    }

    void setSuccess(boolean success) {
        this.success = success;
    }
}
