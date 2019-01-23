package java.com.company.editor;

import javax.swing.filechooser.FileFilter;
import java.io.File;

abstract class CustomFileFilter extends FileFilter {

    protected String getExtension(File file) throws IndexOutOfBoundsException{
        String fileName = file.toString();
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        try {
            String extension = getExtension(f);
            if (extension.equals(this.getMyExtension()))
                return true;
        }catch (IndexOutOfBoundsException e){
            return false;
        }
        return false;
    }

    abstract String getMyExtension();
}


class XLSFileFilter extends CustomFileFilter{
    @Override
    String getMyExtension() {
        return ".xls";
    }

    @Override
    public String getDescription() {
        return "Excel file 1997-2003 (.xls)";
    }
}

class XLSXFileFilter extends CustomFileFilter{

    @Override
    String getMyExtension() {
        return ".xlsx";
    }

    @Override
    public String getDescription() {
        return "Excel file (.xlsx)";
    }
}

class TXTFileFilter extends CustomFileFilter{
    @Override
    String getMyExtension() {
        return ".txt";
    }

    @Override
    public String getDescription() {
        return "Normal text file (.txt)";
    }
}