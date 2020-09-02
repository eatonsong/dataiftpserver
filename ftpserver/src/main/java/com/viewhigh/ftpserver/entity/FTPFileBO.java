package com.viewhigh.ftpserver.entity;

import org.apache.commons.net.ftp.FTPFile;

/**
 * @Classname FTPFileBO
 * @Description
 * @Date 2020/8/31 9:41 上午
 * @Created by eatonsong
 */
public class FTPFileBO extends FTPFile {
    private String downloadUrl;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
