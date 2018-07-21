package ru.avklimenko.hedgehog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static ru.avklimenko.hedgehog.Utils.delay;

/**
 * Connection pool to databases.
 * @author Alexey Klimenko
 * @since 2018.05.04
 **/
public final class HHConnectionPool extends Thread {
    private ConcurrentHashMap<String, List<HHConnection>> connections = new ConcurrentHashMap<>();
    private static HHConnectionPool instance = null;
    private static Logger logger = LoggerFactory.getLogger(HHConnectionPool.class);
    private static boolean isLogged = false;

    public static void debug(String text) {
        if (isLogged) {
            logger.debug(text);
        }
    }

    public static void setLogger(boolean isLoggedVal) {
        isLogged = isLoggedVal;
    }

    private HHConnectionPool() {
        //
    }

    public static HHConnectionPool getInstance() {
        if (instance == null) {
            synchronized (HHConnectionPool.class) {
                if (instance == null) {
                    instance = new HHConnectionPool();
                    instance.start();
                }
            }
        }
        return instance;
    }

    public int getTotalSize() {
        int size = 0;
        for (String key : connections.keySet()) {
            size += connections.get(key).size();
        }
        return size;
    }

    public HHConnection getConnection(String connectionString) {
        if (!connections.containsKey(connectionString)) {
            connections.put(connectionString, new ArrayList<>());
        }
        if (connections.get(connectionString).size() > 0) {
            synchronized (HHConnectionPool.class) {
                for (HHConnection connection : connections.get(connectionString)) {
                    if (connection.isFree()) {
                        connection.setBusy();
                        debug("Get connection " + connectionString + "from pool");
                        return connection;
                    }
                }
            }
        }

        HHConnection connection = new HHConnection(connectionString);
        connections.get(connectionString).add(connection);
        debug("Create new connection " + connectionString);
        return connection;
    }

    /**
     * Every second check all connections and remove overdue.
     */
    @Override
    public void run() {
        while (true) {
            if (!delay(10000)) {
                return;
            }
            for (String key : connections.keySet()) {
                for (int i = connections.get(key).size() - 1; i > -1; i--) {
                    if (connections.get(key).get(i).isTimeForKill()) {
                        debug("Remove connection " + key + "from pool");
                        connections.get(key).remove(i);
                    }
                }
            }
        }
    }
}
