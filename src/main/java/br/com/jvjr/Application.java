package br.com.jvjr;

import br.com.jvjr.exception.TransactionalException;
import br.com.jvjr.person.PersonController;
import br.com.jvjr.person.PersonService;
import br.com.jvjr.exception.TransactionalExceptionHandler;
import br.com.jvjr.interceptor.OpenTransactionHandler;
import br.com.jvjr.interceptor.CloseTransactionHandler;
import io.javalin.Javalin;

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(7000);

        // vinculando os callbacks (Handlers)
        app.before("/api/*", new OpenTransactionHandler());
        app.after("/api/*", new CloseTransactionHandler());
        app.exception(TransactionalException.class, new TransactionalExceptionHandler());

        PersonController controller = new PersonController(new PersonService());
        app.get("/api/person", controller::getAll);
        app.post("/api/person", controller::create);

    }

}
