package com.viewhigh.ftpserver.controller;

import com.viewhigh.ftpserver.consts.UsersConst;
import com.viewhigh.ftpserver.entity.FTPFileBO;
import com.viewhigh.ftpserver.util.FtpClientUtil;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FtpController {


    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/list")
    public List<FTPFileBO> getListByPath(String path) throws Exception{
        FTPFile[] ftpFiles = FtpClientUtil.listFile(path);
        List result = new ArrayList();
        for(FTPFile ff:ftpFiles){
            FTPFileBO ffbo = new FTPFileBO();
            String downloadUrl = null;
            if(path!=null&&!"".equals(path)){
                downloadUrl = UsersConst.ftp + UsersConst.ip + ":" + UsersConst.port + "/"+path+"/"+ff.getName();
            }else{
                downloadUrl = UsersConst.ftp + UsersConst.ip + ":" + UsersConst.port + "/"+ff.getName();
            }
            ffbo.setDownloadUrl(downloadUrl);
            BeanUtils.copyProperties(ff,ffbo);
            result.add(ffbo);
        }
        return result;
    }

    @GetMapping("/delete")
    public boolean deleteFileByPathFileName(String fileName) throws Exception{
        FtpClientUtil.deleteFile(fileName);
        return true;
    }
}
