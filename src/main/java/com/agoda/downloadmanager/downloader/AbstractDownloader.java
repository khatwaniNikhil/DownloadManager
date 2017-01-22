package com.agoda.downloadmanager.downloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.agoda.downloadmanager.manager.entity.DownloadStatus;

public abstract class AbstractDownloader implements Downloader {
    private DownloadSummary summary;
    protected Map<String, String> protocolMetadata;

    protected static final String BASE_SAVE_LOCATION = "BASE_LOCATION";

    public AbstractDownloader(String url) {
        this.summary = new DownloadSummary();
        this.summary.setDownloadURL(url);
        this.summary.setStatus(DownloadStatus.INPROGRESS);
        protocolMetadata = new HashMap<String, String>();
    }

    public String getDownloadURL() {
        return summary.getDownloadURL();
    }

    @Override
    public Map<String, String> getProtocolMetaDataFor(String url) {
        return protocolMetadata;
    }

    public File getUniqueDestFilePathFromUrl() {
        String urlAsString = getDownloadURL().toString();
        String baseLocation = protocolMetadata.get(BASE_SAVE_LOCATION);
        String shortFileName = String.valueOf(urlAsString.hashCode());
        int indexOfLastSlash = urlAsString.lastIndexOf("/");
        int indexOfLastDot = urlAsString.lastIndexOf(".");
        try {
            if (new URL(urlAsString).getPath().contains(".") && indexOfLastDot > indexOfLastSlash) {
                String extension = urlAsString.substring(urlAsString.lastIndexOf("."));
                shortFileName += extension;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        File targetFile = Paths.get(baseLocation, shortFileName).toFile();
        return targetFile;
    }

    @Override
    public DownloadSummary call() throws Exception {
        return downloadDataToLocation(getDownloadURL(), protocolMetadata);
    }

    protected void setState(DownloadStatus status) {
        this.summary.setStatus(status);
    }

    protected void setDestinationFile(File destFile) {
        this.summary.setDestFile(destFile);
    }

    public DownloadSummary getSummary() {
        return summary;
    }

    public Map<String, String> getProtocolMetadata() {
        return protocolMetadata;
    }
}
