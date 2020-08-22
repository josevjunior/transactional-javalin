package br.com.jvjr;

import br.com.jvjr.exception.TransactionalException;
import br.com.jvjr.person.PersonController;
import br.com.jvjr.person.PersonService;
import br.com.jvjr.exception.TransactionalExceptionHandler;
import br.com.jvjr.interceptor.OpenTransactionInterceptor;
import br.com.jvjr.interceptor.CloseTransactionInterceptor;
import io.javalin.Javalin;

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(7000);

        app.before("/api/*", new OpenTransactionInterceptor());
        app.after("/api/*", new CloseTransactionInterceptor());
        app.exception(TransactionalException.class, new TransactionalExceptionHandler());

        PersonController controller = new PersonController(new PersonService());
        app.get("/api/person", controller::getAll);
        app.post("/api/person", controller::create);

    }

}
