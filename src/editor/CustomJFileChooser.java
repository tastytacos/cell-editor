package editor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class CustomJFileChooser extends JFileChooser {
    CustomJFileChooser(String currentDirrectoryPath){
        super(currentDirrectoryPath);
        this.addChoosableFileFilter(new XLSFileFilter());
        this.addChoosableFileFilter(new XLSXFileFilter());
        this.addChoosableFileFilter(new TXTFileFilter());
        this.setAcceptAllFileFilterUsed(true);
    }

    @Override
    public FileFilter getFileFilter() {
        return super.getFileFilter();
    }
}
