package com.agoda.downloadmanager.manager.entity;

import java.net.URL;

public interface Downloadable {
    URL getURL();

    String getUserName();

    String getPassword();
}
