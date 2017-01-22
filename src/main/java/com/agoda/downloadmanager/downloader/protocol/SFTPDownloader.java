package com.agoda.downloadmanager.downloader.protocol;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.agoda.downloadmanager.downloader.AbstractDownloader;
import com.agoda.downloadmanager.downloader.DownloadSummary;

public class SFTPDownloader extends AbstractDownloader {

    private Properties prop;

    public SFTPDownloader(String url) {
        super(url);
        try {
            prop = new Properties();
            InputStream is = this.getClass().getResourceAsStream("/config.properties");
            prop.load(is);
            protocolMetadata.put(BASE_SAVE_LOCATION, prop.get("sftp.save.location").toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, String> getProtocolMetaDataFor(String url) {
        /**
         *  TODO
         *  Lookup some repository to fetch current URL domain specific login credentials and path etc
         *  update protocolMetadata map accordingly
         */
        return protocolMetadata;
    }

    @Override
    public DownloadSummary downloadDataToLocation(String url, Map<String, String> protocolMetaData) {
        // TODO SFTP specifics to download data
        return null;
    }
}
