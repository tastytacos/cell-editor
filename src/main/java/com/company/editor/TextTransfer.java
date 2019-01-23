package java.com.company.editor;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


/**
 * This class uses the {@link Clipboard} class in order to get text which was copied/cut/pasted by the user and
 * provides handling it.
 */
class TextTransfer {
    /**
     * Get the String residing on the clipboard.
     * @return any text found on the {@link Clipboard}; if none found, return an
     * empty {@link String}.
     */
    public static String getClipboardContents() throws TextTransferException{
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        try {
            result = (String)contents.getTransferData(DataFlavor.stringFlavor);
        }
        catch (UnsupportedFlavorException | IOException ex){
            throw new TextTransferException(ex.getMessage(), ex.getCause());
        }
        return result;
    }
}
