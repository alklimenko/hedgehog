package ru.avklimenko.hedgehog.exceptions;

public class HHSQLSyntaxErrorException extends HHException {
    static final long serialVersionUID = 7462374282362875949L;

    public HHSQLSyntaxErrorException() {
        super();
    }

    public HHSQLSyntaxErrorException(String message) {
        super(message);
    }

    public HHSQLSyntaxErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HHSQLSyntaxErrorException(Throwable throwable) {
        super(throwable);
    }

    public HHSQLSyntaxErrorException(String message, Throwable cause,
                       boolean enableSuppression,
                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
