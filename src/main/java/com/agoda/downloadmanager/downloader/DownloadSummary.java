package com.agoda.downloadmanager.downloader;

import java.io.File;

import com.agoda.downloadmanager.manager.entity.DownloadStatus;

public class DownloadSummary {

    private DownloadStatus status;
    protected File destFile;
    private String downloadURL;
    private String failureMessage = "";

    public DownloadSummary(DownloadStatus status, File destFile, String downloadURL) {
        super();
        this.status = status;
        this.destFile = destFile;
        this.downloadURL = downloadURL;
    }

    public DownloadSummary() {
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadStatus status) {
        this.status = status;
    }

    public File getDestFile() {
        return destFile;
    }

    public void setDestFile(File destFile) {
        this.destFile = destFile;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    @Override
    public String toString() {
        return "DownloadSummary [status=" + status + ", destFile=" + destFile.getAbsolutePath() + ", downloadURL=" + downloadURL.toString() + ", failureMessage=" + failureMessage + "]";
    }
}
