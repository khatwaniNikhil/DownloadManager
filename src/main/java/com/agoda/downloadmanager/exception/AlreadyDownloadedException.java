package com.agoda.downloadmanager.exception;

import java.io.File;

public class AlreadyDownloadedException extends Exception {
    private static final long serialVersionUID = 1L;
    private File existingFile;

    public File getExistingFile() {
        return existingFile;
    }

    public AlreadyDownloadedException(File existingFile) {
        super();
        this.existingFile = existingFile;
    }

}
