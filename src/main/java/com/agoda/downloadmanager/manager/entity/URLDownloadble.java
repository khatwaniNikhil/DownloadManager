package com.agoda.downloadmanager.manager.entity;

import java.net.URL;

public class URLDownloadble implements Downloadable {

    private URL url;

    public URLDownloadble(URL url) {
        if (!url.getProtocol().equals("http")) {
            throw new IllegalArgumentException();
        }
        this.url = url;
    }

    public URL getURL() {
        return url;
    }

    public String getUserName() {
        throw new UnsupportedOperationException();
    }

    public String getPassword() {
        throw new UnsupportedOperationException();
    }

}
