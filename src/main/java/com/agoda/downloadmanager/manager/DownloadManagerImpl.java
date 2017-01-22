package com.agoda.downloadmanager.manager;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.agoda.downloadmanager.downloader.AbstractDownloader;
import com.agoda.downloadmanager.downloader.DownloadSummary;
import com.agoda.downloadmanager.downloader.factory.DownloaderFactory;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Component("DownloadManager")
public class DownloadManagerImpl implements DownloadManager {

    @Autowired
    private DownloaderFactory downloaderFactory;

    private int poolThreadCount = 10;

    final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("Downloader-%d").setDaemon(true).build();

    private ExecutorService downloadThreadPool = Executors.newFixedThreadPool(poolThreadCount, threadFactory);

    private final ExecutorCompletionService<DownloadSummary> completionService = new ExecutorCompletionService<>(downloadThreadPool);

    public static void main(String[] args) throws MalformedURLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        DownloadManagerImpl manager = (DownloadManagerImpl) applicationContext.getBean("DownloadManager");
        ((AbstractApplicationContext) applicationContext).registerShutdownHook();
        List<String> sources = new ArrayList<String>();
        sources.add("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4");
        manager.start(sources);
    }

    public void start(List<String> sources) throws MalformedURLException {
        downloadFromSources(sources);
    }

    public void downloadFromSources(List<String> downloadURLs) {
        for (String url : downloadURLs) {
            try {
                AbstractDownloader downloader = downloaderFactory.getDownloaderFor(url);
                completionService.submit(downloader);
            } catch (MalformedURLException urlException) {
                urlException.printStackTrace();
            }
        }
        for (String url : downloadURLs) {
            try {
                final Future<DownloadSummary> downloadSummaryFuture = completionService.take();
                System.out.println(downloadSummaryFuture.get());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (ExecutionException executionException) {
                executionException.printStackTrace();
            }
        }
    }

    @Override
    public void shutdown() {
        downloadThreadPool.shutdown();
        try {
            downloadThreadPool.awaitTermination(0, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
