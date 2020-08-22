package br.com.jvjr.exception;

public class TransactionalException extends RuntimeException{

    public TransactionalException(String string) {
        super(string);
    }

    public TransactionalException(Throwable thrwbl) {
        super(thrwbl);
    }
    
}
