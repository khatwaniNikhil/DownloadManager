package com.agoda.downloader.integration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agoda.downloadmanager.downloader.protocol.FTPDownloader;
import com.agoda.downloadmanager.downloader.protocol.HttpDownloader;
import com.agoda.downloadmanager.manager.DownloadManagerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beans.xml" })
public class DownloadE2ETest {

    @Autowired
    DownloadManagerImpl manager;

    @Test
    public void testSingleHTTPDownload() throws MalformedURLException {
        String downloadURL = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";
        File fileToDownload = new HttpDownloader(downloadURL).getUniqueDestFilePathFromUrl();
        if (fileToDownload.exists())
            fileToDownload.delete();
        List<String> sources = new ArrayList<String>();
        sources.add(downloadURL);
        manager.downloadFromSources(sources);

        assertEquals(fileToDownload.exists(), true);
        fileToDownload.delete();
    }

    @Test
    public void testExistingSingleHTTPDownloadSkipped() throws MalformedURLException {
        String downloadURL = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";
        File fileToDownload = new HttpDownloader(downloadURL).getUniqueDestFilePathFromUrl();
        if (fileToDownload.exists())
            fileToDownload.delete();

        List<String> sources = new ArrayList<String>();
        sources.add(downloadURL);
        manager.downloadFromSources(sources);
        assert (fileToDownload.exists() == true);
        long creationTimeStamp = fileToDownload.lastModified();
        manager.downloadFromSources(sources);

        assertEquals(fileToDownload.exists(), true);
        assertEquals(creationTimeStamp, fileToDownload.lastModified());
        fileToDownload.delete();
    }

    @Test
    public void testMultipleHTTPDownload() throws MalformedURLException {
        String downloadURL1 = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";
        String downloadURL2 = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_2mb.mp4";
        File fileToDownload1 = new HttpDownloader(downloadURL1).getUniqueDestFilePathFromUrl();
        File fileToDownload2 = new HttpDownloader(downloadURL2).getUniqueDestFilePathFromUrl();
        if (fileToDownload1.exists())
            fileToDownload1.delete();
        if (fileToDownload2.exists())
            fileToDownload2.delete();
        List<String> sources = new ArrayList<String>();
        sources.add(downloadURL1);
        sources.add(downloadURL2);
        manager.downloadFromSources(sources);

        assertEquals(fileToDownload1.exists(), true);
        assertEquals(fileToDownload2.exists(), true);
        fileToDownload1.delete();
        fileToDownload2.delete();
    }

    @Test
    public void testDiffProtocolDownload() throws MalformedURLException {
        String downloadURL1 = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4";
        String downloadURL2 = "ftp://nikhil:niknit123@localhost/track.json";
        File fileToDownload1 = new HttpDownloader(downloadURL1).getUniqueDestFilePathFromUrl();
        File fileToDownload2 = new FTPDownloader(downloadURL2).getUniqueDestFilePathFromUrl();
        if (fileToDownload1.exists())
            fileToDownload1.delete();
        if (fileToDownload2.exists())
            fileToDownload2.delete();
        List<String> sources = new ArrayList<String>();
        sources.add(downloadURL1);
        sources.add(downloadURL2);
        manager.downloadFromSources(sources);

        assertEquals(fileToDownload1.exists(), true);
        assertEquals(fileToDownload2.exists(), true);
        fileToDownload1.delete();
        fileToDownload2.delete();
    }

}
