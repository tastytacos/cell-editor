package com.company.editor;

public class FilenameContainingDotException extends Exception{
    public FilenameContainingDotException() {
    }

    public FilenameContainingDotException(String message) {
        super(message);
    }

    public FilenameContainingDotException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilenameContainingDotException(Throwable cause) {
        super(cause);
    }

    public FilenameContainingDotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
