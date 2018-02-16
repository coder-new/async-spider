package com.farmer.async.spider.save;

import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/19
 */
@Component
public class FastDfsConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastDfsConfig.class);

    @Value("${fastdfs.server}")
    private String fastdfsServer;

    @Value("${fastdfs.port}")
    private int fastdfsPort;

    private StorageClient1 storageClient1;

    @PostConstruct
    public void init() {

        ClientGlobal.setG_charset("UTF-8");
        InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
        tracker_servers[0] = new InetSocketAddress(fastdfsServer, fastdfsPort);
        ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));

        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = null;
        try {
            trackerServer = tracker.getConnection();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        StorageServer storageServer = null;
        storageClient1 = new StorageClient1(trackerServer, storageServer);
    }

    public StorageClient1 getStorageClient1() {

        return storageClient1;
    }

}
