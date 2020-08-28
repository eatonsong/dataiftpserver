package com.viewhigh.ftpserver.util;

import com.viewhigh.ftpserver.consts.UsersConst;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.List;

public class FtpClientUtil {

    // ftp服务器ip地址
    private static String FTP_ADDRESS = UsersConst.ipaddress;
    // 端口号
    private static int FTP_PORT = UsersConst.port;
    // 用户名
    private static String FTP_USERNAME = UsersConst.username;
    // 密码
    private static String FTP_PASSWORD = UsersConst.userpassword;
    // 相对路径
    private static String FTP_BASEPATH = "";


    private static FTPClient connect() throws IOException{
        int reply;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
        ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
        reply = ftp.getReplyCode();
        System.out.println("登录ftp服务返回状态码为：" + reply);
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new RuntimeException("登录ftp服务返回状态码为：" + reply);
        }
        return ftp;
    }
    public static void disconnect(FTPClient ftp){
        if (ftp!=null&&ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException ioe) {
            }
        }
    }
    public static FTPFile[] listFile(String path) throws Exception{
        FTPClient ftp = null;
        try {
            ftp = connect();
            FTPFile []fileList = ftp.listFiles(path);
            ftp.logout();
            return fileList;
        }finally {
            disconnect(ftp);
        }
    }

    public static void uploadFile(String remoteFileName, InputStream input) throws Exception {
        FTPClient ftp = null;
        try {
            ftp = connect();
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //设置为被动模式
            ftp.enterLocalPassiveMode();
            ftp.makeDirectory(FTP_BASEPATH);
            ftp.changeWorkingDirectory(FTP_BASEPATH);
            //originFilePath就是上传文件的文件名，建议使用生成的唯一命名，中文命名最好做转码
            boolean a = ftp.storeFile(remoteFileName, input);
//            boolean a = ftp.storeFile(new String(remoteFileName.getBytes(),"iso-8859-1"),input);
            System.out.println("要上传的原始文件名为：" + remoteFileName + ", 上传结果：" + a);
            input.close();
            ftp.logout();
        } finally {
           disconnect(ftp);
        }
    }

    public static void deleteFile(String filename) throws Exception{
        FTPClient ftp = null;
        try {
            ftp = connect();
            // 切换FTP目录
            ftp.changeWorkingDirectory(FTP_BASEPATH);
            ftp.dele(filename);
            ftp.logout();
        }finally {
            disconnect(ftp);
        }
    }

    public static void downloadFile(String filename, String localPath) throws Exception{
        FTPClient ftp = null;
        try {
            ftp = connect();
            // 切换FTP目录
            ftp.changeWorkingDirectory(FTP_BASEPATH);

            //此处为demo方法，正常应该到数据库中查询fileName
            FTPFile[] ftpFiles = ftp.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localPath + "/" + file.getName());
                    OutputStream os = new FileOutputStream(localFile);
                    ftp.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftp.logout();
            System.out.println("文件下载完成！！！");
        } finally {
            ftp.disconnect();
        }
    }
}
