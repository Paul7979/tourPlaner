package at.technikum.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SQLConnectionProvider {
    private static final List<Connection> connectionPool = new ArrayList<>(10);
    private static final List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = AppProperties.getCONNECTION_POOL_SIZE();

    public static void initialize() throws SQLException, ClassNotFoundException {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection(AppProperties.getDB_URL(), AppProperties.getDB_USER(), AppProperties.getDB_PASSWORD()));
        }
    }

    public static Connection getConnection() {
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        if (System.currentTimeMillis() % 7 == 0) {
            log.info("Connections in use: {}", INITIAL_POOL_SIZE - connectionPool.size());
        }
        return connection;
    }

    public static boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}
