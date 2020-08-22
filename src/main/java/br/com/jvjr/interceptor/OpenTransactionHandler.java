
package br.com.jvjr.interceptor;

import br.com.jvjr.connection.ConnectionHolder;
import br.com.jvjr.connection.ConnectionProvider;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenTransactionHandler implements Handler{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenTransactionHandler.class);

    @Override
    public void handle(Context cntxt) throws Exception {
        LOGGER.info("Opening transaction...");
        ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
        ConnectionHolder.set(connectionProvider.createConnection());
    }
    
}
