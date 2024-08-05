package dataacess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerAdapter;
public final class DatabaseConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/scdproject";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoggerAdapter.class.getName());

    // Static instance
    private static DatabaseConnector instance;

    private Connection connection;

    // Private constructor
    private DatabaseConnector() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            logger.info("Connected to the database successfully.");
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    // Public static method to get the instance
    public static synchronized DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Connection closed successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Failed to close the database connection", e);
                throw new RuntimeException("Failed to close the database connection");
            }
        }
    }

    public PreparedStatement prepareStatement(String sql, int returnGeneratedKeys) throws SQLException {
        if (connection != null) {
            return connection.prepareStatement(sql, returnGeneratedKeys);
        }
        logger.error("Connection is null while preparing statement.");
        throw new SQLException("Connection is null");
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (connection != null) {
            return connection.prepareStatement(sql);
        }
        logger.error("Connection is null while preparing statement.");
        throw new SQLException("Connection is null");
    }

}
