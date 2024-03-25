package be.akimts.test.trad.service;

import java.io.File;

public class SaveException extends Exception {
    private final File toFile;

    public SaveException(File toFile, Throwable cause) {
        super("Cannot save to file : " + toFile.getAbsolutePath(), cause);
        this.toFile = toFile;
    }

    public File getToFile() {
        return toFile;
    }
}
