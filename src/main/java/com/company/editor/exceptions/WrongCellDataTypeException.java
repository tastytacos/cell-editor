package com.company.editor.exceptions;

public class WrongCellDataTypeException extends WrongDataTypeException {
    WrongCellDataTypeException() {
        super();
    }

    WrongCellDataTypeException(String message) {
        super(message);
    }

    public WrongCellDataTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
