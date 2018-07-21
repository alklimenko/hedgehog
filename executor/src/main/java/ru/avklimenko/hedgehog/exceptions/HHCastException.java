package ru.avklimenko.hedgehog.exceptions;

public class HHCastException extends HHException {
    static final long serialVersionUID = 2759779746657219899L;

    public HHCastException() {
        super();
    }

    public HHCastException(String message) {
        super(message);
    }

    public HHCastException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HHCastException(Throwable throwable) {
        super(throwable);
    }

    public HHCastException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
