package com.company.editor.exceptions;

public class TextTransferException extends Exception {
    TextTransferException() {
    }

    TextTransferException(String message) {
        super(message);
    }

    public TextTransferException(String message, Throwable cause) {
        super(message, cause);
    }
}

