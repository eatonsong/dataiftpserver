package com.viewhigh.ftpserver.controller;

import com.viewhigh.ftpserver.util.FtpClientUtil;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FtpController {


    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/list")
    @ResponseBody
    public FTPFile[] getListByPath(String path) throws Exception{
        return FtpClientUtil.listFile(path);
    }

    @GetMapping("/delete")
    public boolean deleteFileByPathFileName(String fileName) throws Exception{
        FtpClientUtil.deleteFile(fileName);
        return true;
    }
}
