package webshop.exception;

public class NegativeBalanceException extends RuntimeException{

    public NegativeBalanceException(){

    }
    public NegativeBalanceException(String message){
        super(message);
    }
    public NegativeBalanceException(String message, Throwable cause) {
        super(message,cause);
    }

    public NegativeBalanceException(Throwable cause) {
        super(cause);
    }
}
