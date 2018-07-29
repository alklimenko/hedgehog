package ru.avklimenko.hedgehog.exceptions;

public class HHInvalidParameterException extends HHException {
    static final long serialVersionUID = -8383265217403282720L;

    public HHInvalidParameterException() {
        super();
    }

    public HHInvalidParameterException(String message) {
        super(message);
    }

    public HHInvalidParameterException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HHInvalidParameterException(Throwable throwable) {
        super(throwable);
    }

    public HHInvalidParameterException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
