package dal;

import java.io.File;

public class FileImporterStub {

    private boolean importFileCalled;
    private File importedFile;

    public void importFile(File file) {
        importFileCalled = true;
        importedFile = file;
    }

    public boolean isImportFileCalled() {
        return importFileCalled;
    }

    public File getImportedFile() {
        return importedFile;
    }
}
