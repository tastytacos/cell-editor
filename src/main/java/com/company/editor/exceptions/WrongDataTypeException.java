package com.company.editor.exceptions;

public class WrongDataTypeException extends TextTransferException {
    WrongDataTypeException() {
        super();
    }

    public WrongDataTypeException(String message) {
        super(message);
    }

    WrongDataTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
