package exceptions;

public class UnknownRequestException extends Exception {
    public UnknownRequestException(String errorMessage) {
        super(errorMessage);
    }
}
