package br.com.jvjr.connection;

import br.com.jvjr.exception.TransactionalException;
import java.sql.Connection;

public class ConnectionHolder {

    private static final ThreadLocal<Connection> currentConnection = new InheritableThreadLocal<>();
    
    public static Connection get() {
        return currentConnection.get();
    }
    
    public static void set(Connection connection) {
        final Connection current = get();
        if(current != null && connection != current) {
            throw new TransactionalException("Cannot bind more than one transaction per thread.");
        }
        
        currentConnection.set(connection);
        
    }

    public static void clear() {
        currentConnection.set(null);
    }
}
