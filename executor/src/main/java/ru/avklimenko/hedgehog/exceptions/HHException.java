package ru.avklimenko.hedgehog.exceptions;

public class HHException extends RuntimeException {
    static final long serialVersionUID = -2847866688563455248L;

    public HHException() {
        super();
    }

    public HHException(String message) {
        super(message);
    }

    public HHException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HHException(Throwable throwable) {
        super(throwable);
    }

    public HHException(String message, Throwable cause,
                       boolean enableSuppression,
                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
