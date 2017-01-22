package com.agoda.downloadmanager.downloader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

import com.agoda.downloadmanager.exception.AlreadyDownloadedException;

public interface Downloader extends Callable<DownloadSummary> {

    Map<String, String> getProtocolMetaDataFor(String url);

    DownloadSummary downloadDataToLocation(String url, Map<String, String> protocolMetaData) throws AlreadyDownloadedException;

    File getUniqueDestFilePathFromUrl() throws IOException;
}
