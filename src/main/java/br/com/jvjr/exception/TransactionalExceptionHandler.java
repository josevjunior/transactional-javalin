
package br.com.jvjr.exception;

import br.com.jvjr.connection.ConnectionHolder;
import io.javalin.http.Context;
import io.javalin.http.ExceptionHandler;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionalExceptionHandler implements ExceptionHandler<TransactionalException>{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalExceptionHandler.class);

    @Override
    public void handle(TransactionalException t, Context ctx) {
        
        LOGGER.info("Rolling back transaction...");
        LOGGER.error(t.getLocalizedMessage(), t);
        
        Connection connection = ConnectionHolder.get();
        if(connection != null) {
            try {
                connection.rollback();
            }catch (SQLException e){}
            
        }
        
        
        ctx.status(500);
        ctx.json(t);
    }

    
}
