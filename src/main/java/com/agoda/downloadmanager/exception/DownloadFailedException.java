package com.agoda.downloadmanager.exception;

import java.io.IOException;

public class DownloadFailedException extends IOException {

    private String downloadURL;

    public DownloadFailedException(String downloadURL) {
        super();
        this.downloadURL = downloadURL;
    }

    public String getDownloadURL() {
        return downloadURL;
    }
}
