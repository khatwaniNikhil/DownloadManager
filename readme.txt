Approach - 

ClassDiagram
src/main/java/com/agoda/downloadmanager/ClassDiagram.cld

DownloadManager 
1) Receives List<String> as input.
2) Refers to DownloaderFactory to select the protocol specific downloader.
3) Executes the download task (Protocol specific Downloader) via ExecutorCompletionService.
4) Observes the completed/failed downloads and print DownloadSummary as soon as download is finished/failed.
4) Manages ThreadPool with configurable no. of threads for parallel downloads.

Downloader
1) Receives URL String as input.
2) Fetches protocol and current URL domain specific details 
    a) login credentials if any (required for ftp, sftp) from some data source(config file or database).
    b) target base location/folder to save file.
    
3) Generate unique filename's using URL string's hashcode(). 


Assumptions

1) Protocol specific downloader might do a lookup:
    a) some data source like database. For example sftp based download will need URL domain specific login credentials for different sftp based URL's.
    b) some config file for protocol specific target base location/folder to save file.

2) If we try to download from the same URL for which another download is currently in progress in separate thread or already completed in past, new download will be ignored silently.
3) Any client using this software program can configure no. of parallel downloads that can run at any time.
4) Bandwidth of the host where downloads are running and bandwidth in use at any point in time is not monitored and supported by the current implementation of software program.

Unit Test
com.agoda.downloader.unit.HttpDownloaderTest.java

Integration Test
com.agoda.downloader.integration.DownloadE2ETest.java

How to Run the project
1) mvn clean install
 
 