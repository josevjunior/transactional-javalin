package br.com.jvjr;

import br.com.jvjr.connection.ConnectionHolder;
import br.com.jvjr.connection.ConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ThreadSafetyTest {

    private ConnectionProvider connectionProvider;

    @Before
    public void init() {
        connectionProvider = ConnectionProvider.getInstance();
    }

    private void bindConnectionInThread() throws SQLException{
        ConnectionHolder.set(connectionProvider.createConnection());
    }
    
    @Test
    public void connectionInEachThreadShouldBeDifferent() throws Exception{
        
        Map<Integer, Connection> connections = new HashMap<>();
        
        Thread th1 = new Thread(()->{
            try {
                bindConnectionInThread();
                connections.put(1, ConnectionHolder.get());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
       
        Thread th2 = new Thread(()->{
            try {
                bindConnectionInThread();
                connections.put(2, ConnectionHolder.get());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        
        th1.start();
        th2.start();
        
        th1.join();
        th2.join();
        
        Connection connection1 = connections.get(1);
        Connection connection2 = connections.get(2);
        assertTrue(connection1 != connection2);
        
        try {
            connection1.close();
            connection2.close();
        } catch(SQLException e){
            
        }
    }
}
