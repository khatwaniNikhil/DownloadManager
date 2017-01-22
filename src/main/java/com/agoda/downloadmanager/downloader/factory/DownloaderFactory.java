package com.agoda.downloadmanager.downloader.factory;

import java.net.MalformedURLException;

import org.springframework.stereotype.Component;

import com.agoda.downloadmanager.downloader.AbstractDownloader;
import com.agoda.downloadmanager.downloader.protocol.FTPDownloader;
import com.agoda.downloadmanager.downloader.protocol.HttpDownloader;

@Component
public class DownloaderFactory {

    public AbstractDownloader getDownloaderFor(String url) throws MalformedURLException {
        if (url.startsWith("http")) {
            return new HttpDownloader(url);
        } else if (url.startsWith("ftp")) {
            return new FTPDownloader(url);
        } else if (url.startsWith("sftp")) {
            return null;
        } else {
            return null;
        }
    }
}
