
package br.com.jvjr.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {
    
    private HikariDataSource dataSource;
    
    private static final ConnectionProvider instance = new ConnectionProvider();
    
    private ConnectionProvider() {
        init();
    }
    
    private void init() {
        HikariConfig config = new HikariConfig("/hikari.properties");
        dataSource = new HikariDataSource(config);
        
        Runtime.getRuntime().addShutdownHook(new Thread(dataSource::close));
    }
    
    public static ConnectionProvider getInstance() {
        return instance;
    }
    
    public Connection createConnection() throws SQLException{
        return dataSource.getConnection();
    }
    
}
