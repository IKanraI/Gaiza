package exception;

public class BadInputException extends Exception {

    public BadInputException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
