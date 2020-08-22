package br.com.jvjr.interceptor;

import br.com.jvjr.connection.ConnectionHolder;
import br.com.jvjr.exception.TransactionalException;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.sql.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloseTransactionHandler implements Handler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CloseTransactionHandler.class);

    @Override
    public void handle(Context cntxt) throws Exception {
        
        LOGGER.info("Closing transaction...");
        
        Connection connection = null;
        try {
            connection = ConnectionHolder.get();
            connection.commit();
        } catch (Exception e) {
            throw new TransactionalException(e);
        } finally {
            if(connection != null) connection.close();
             ConnectionHolder.clear();
        }

       
    }

}
