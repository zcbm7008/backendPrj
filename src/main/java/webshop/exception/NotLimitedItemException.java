package webshop.exception;

@SuppressWarnings("serial")
public class NotLimitedItemException extends RuntimeException {

    public NotLimitedItemException(){

    }
    public NotLimitedItemException(String message){
        super(message);
    }
    public NotLimitedItemException(String message, Throwable cause) {
        super(message,cause);
    }

    public NotLimitedItemException(Throwable cause) {
        super(cause);
    }
}
