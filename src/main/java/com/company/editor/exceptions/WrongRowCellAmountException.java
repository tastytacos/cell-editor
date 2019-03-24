package com.company.editor.exceptions;

public class WrongRowCellAmountException extends TextTransferException {
    WrongRowCellAmountException() {
        super();
    }

    public WrongRowCellAmountException(String message) {
        super(message);
    }

    WrongRowCellAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
