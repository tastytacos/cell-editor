package editor;

class TextTransferException extends Exception {
    TextTransferException() {
    }

    TextTransferException(String message) {
        super(message);
    }

    TextTransferException(String message, Throwable cause) {
        super(message, cause);
    }
}

class WrongDataTypeException extends TextTransferException {
    WrongDataTypeException() {
        super();
    }

    WrongDataTypeException(String message) {
        super(message);
    }

    WrongDataTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}

class WrongRowCellAmountException extends TextTransferException {
    WrongRowCellAmountException() {
        super();
    }

    WrongRowCellAmountException(String message) {
        super(message);
    }

    WrongRowCellAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}

class WrongCellDataTypeException extends WrongDataTypeException {
    WrongCellDataTypeException() {
        super();
    }

    WrongCellDataTypeException(String message) {
        super(message);
    }

    WrongCellDataTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}