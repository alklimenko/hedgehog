package ru.avklimenko.hedgehog.cache;

import org.joda.time.DateTime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SQLCache extends Thread {
    private class Data {
        Data() {
            lastUsed = DateTime.now();
        }

        Data(String sql) {
            this.sql = sql;
            lastUsed = DateTime.now();
        }

        String sql;
        DateTime lastUsed;
    }

    private final Map<Long, Data> cache = new ConcurrentHashMap<>();

    public boolean isInChache(long hash) {
        return cache.containsKey(hash);
    }

    public String getSql(long hash) {
        Data data = cache.get(hash);
        if (data == null) {
            return null;
        }
        synchronized (Data.class) {
            data.lastUsed = DateTime.now();
        }
        return data.sql;
    }

    public void putSql(long hash, String sql) {
        Data data = new Data(sql);
        cache.put(hash, data);
    }
}
