package creation;

import editor.CellEditor;

import javax.swing.*;

public class EditorCreator {
    public static void main(String[] args){
        getCellEditor();
    }

    public static void getCellEditor(){
         SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new CellEditor();
            }
        });
    }
}
