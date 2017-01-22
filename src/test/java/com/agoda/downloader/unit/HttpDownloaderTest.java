package com.agoda.downloader.unit;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.agoda.downloadmanager.downloader.DownloadSummary;
import com.agoda.downloadmanager.downloader.factory.DownloaderFactory;
import com.agoda.downloadmanager.downloader.protocol.HttpDownloader;
import com.agoda.downloadmanager.manager.entity.DownloadStatus;

public class HttpDownloaderTest {

    private HttpDownloader httpDownloader;

    @Mock
    private DownloaderFactory downloaderFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDownloadFile() throws MalformedURLException {
        // TODO change URL to locally hosted very small kB file, so that tests can run without internet also
        String downloadURL = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";
        httpDownloader = new HttpDownloader(downloadURL);
        Mockito.doReturn(httpDownloader).when(downloaderFactory).getDownloaderFor(downloadURL);

        if (httpDownloader.getUniqueDestFilePathFromUrl().exists()) {
            httpDownloader.getUniqueDestFilePathFromUrl().delete();
        }
        DownloadSummary summary = httpDownloader.downloadDataToLocation(downloadURL, new HashMap<String, String>());

        assertEquals(summary.getStatus(), DownloadStatus.COMPLETED);
        assertEquals(summary.getDestFile().exists(), true);
        assertEquals(summary.getDownloadURL(), downloadURL);
        assertEquals(summary.getFailureMessage(), "");
    }

    @Test
    public void testGetUniqueDestFilePathFromUrl() throws MalformedURLException {
        String url1 = "http://www.sample-videos.com/abc";
        String url2 = "http://www.sample-videos.com/abc.mp3";
        String url3 = "http://www.sample-videos.com/abc.txt";
        String url4 = "http://www.sample-videos.com/def";

        httpDownloader = new HttpDownloader(url1);
        Mockito.doReturn(httpDownloader).when(downloaderFactory).getDownloaderFor(url1);
        File file1 = httpDownloader.getUniqueDestFilePathFromUrl();

        httpDownloader = new HttpDownloader(url2);
        Mockito.doReturn(httpDownloader).when(downloaderFactory).getDownloaderFor(url2);
        File file2 = httpDownloader.getUniqueDestFilePathFromUrl();

        httpDownloader = new HttpDownloader(url3);
        Mockito.doReturn(httpDownloader).when(downloaderFactory).getDownloaderFor(url3);
        File file3 = httpDownloader.getUniqueDestFilePathFromUrl();

        httpDownloader = new HttpDownloader(url4);
        Mockito.doReturn(httpDownloader).when(downloaderFactory).getDownloaderFor(url4);
        File file4 = httpDownloader.getUniqueDestFilePathFromUrl();

        assertEquals(!file1.getAbsolutePath().equals(file2.getAbsolutePath()), true);
        assertEquals(!file2.getAbsolutePath().equals(file3.getAbsolutePath()), true);
        assertEquals(!file3.getAbsolutePath().equals(file4.getAbsolutePath()), true);
    }
}
