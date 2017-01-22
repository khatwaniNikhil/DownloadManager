package com.agoda.downloadmanager.manager.entity;

import java.net.URL;

public class URLWithCredentialsDownloadable implements Downloadable {

    private URL url;
    private String userName;
    private String password;

    URLWithCredentialsDownloadable(URL url, String userName, String password) {
        if (url == null || userName == null || password == null) {
            throw new IllegalArgumentException();
        }
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public URL getURL() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
