package com.agoda.downloadmanager.manager;

import java.util.List;

public interface DownloadManager {

    void downloadFromSources(List<String> downloadURLs);

    void shutdown();
}
