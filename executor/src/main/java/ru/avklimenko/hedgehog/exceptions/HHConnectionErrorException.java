package ru.avklimenko.hedgehog.exceptions;

public class HHConnectionErrorException extends HHException {
    static final long serialVersionUID = -3239495399532756532L;

    public HHConnectionErrorException() {
        super();
    }

    public HHConnectionErrorException(String message) {
        super(message);
    }

    public HHConnectionErrorException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HHConnectionErrorException(Throwable throwable) {
        super(throwable);
    }

    public HHConnectionErrorException(String message, Throwable cause,
                       boolean enableSuppression,
                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
