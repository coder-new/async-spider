package com.farmer.async.spider.storage;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.InetSocketAddress;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2017/11/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFastdfs {

    @Value("${fastdfs.server}")
    private String fastdfsServer;

    @Value("${fastdfs.port}")
    private int fastdfsPort;

    @Test
    public void test() throws IOException {

        ClientGlobal.setG_tracker_http_port(8888);

        InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
        tracker_servers[0] = new InetSocketAddress(fastdfsServer, fastdfsPort);
        ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));

        File file = new File("D:\\blog-page\\db.json");

        byte[] fileBuff = getFileBuffer(new FileInputStream(file), file.length());
        String fileExtName = "";
        String uploadFileName = "db.json";
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        } else {
            System.out.println("Fail to upload file, because the format of filename is illegal.");
            return;
        }

        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 client = new StorageClient1(trackerServer, storageServer);

        // 设置元信息
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", uploadFileName);
        metaList[1] = new NameValuePair("fileExtName", fileExtName);
        metaList[2] = new NameValuePair("fileLength", String.valueOf(file.length()));

        // 上传文件
        try {
            String p = client.upload_file1(fileBuff, fileExtName, metaList);
            System.out.println(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        trackerServer.close();
    }

    private static byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {

        byte[] buffer = new byte[256 * 1024];
        byte[] fileBuffer = new byte[(int) fileLength];

        int count = 0;
        int length = 0;

        while ((length = inStream.read(buffer)) != -1) {
            for (int i = 0; i < length; ++i) {
                fileBuffer[count + i] = buffer[i];
            }
            count += length;
        }
        return fileBuffer;
    }
}
