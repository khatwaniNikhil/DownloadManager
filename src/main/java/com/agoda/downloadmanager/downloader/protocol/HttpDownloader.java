package com.agoda.downloadmanager.downloader.protocol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import com.agoda.downloadmanager.downloader.AbstractDownloader;
import com.agoda.downloadmanager.downloader.DownloadSummary;
import com.agoda.downloadmanager.manager.entity.DownloadStatus;

public class HttpDownloader extends AbstractDownloader {

    private Properties prop;

    public HttpDownloader(String url) {
        super(url);
        try {
            prop = new Properties();
            InputStream is = this.getClass().getResourceAsStream("/config.properties");
            prop.load(is);
            protocolMetadata.put(BASE_SAVE_LOCATION, prop.get("http.save.location").toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DownloadSummary downloadDataToLocation(String url, Map<String, String> protocolMetaData) {
        try {
            URL downloadURL = new URL(url);
            if (!downloadURL.getProtocol().equals("http"))
                throw new IllegalArgumentException();
            File destFile = getUniqueDestFilePathFromUrl();
            setDestinationFile(destFile);
            if (destFile.exists()) {
                setState(DownloadStatus.ALREADY_EXISTS);
            } else {
                String baseLocation = protocolMetadata.get(BASE_SAVE_LOCATION);
                File baseFolder = Paths.get(baseLocation, new String[] {}).toFile();
                baseFolder.mkdirs();
                destFile.createNewFile();
                try {
                    FileUtils.copyURLToFile(downloadURL, destFile);
                    setState(DownloadStatus.COMPLETED);
                } catch (IOException ioException) {
                    destFile.delete();
                    setState(DownloadStatus.FAILED);
                    getSummary().setFailureMessage(ioException.getMessage());
                }

            }
        } catch (IOException ioException) {
            setState(DownloadStatus.FAILED);
            getSummary().setFailureMessage(ioException.getMessage());
        }
        return getSummary();
    }

}
