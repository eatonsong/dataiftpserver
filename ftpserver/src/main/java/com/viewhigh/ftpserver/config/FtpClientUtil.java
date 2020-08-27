package com.viewhigh.ftpserver.config;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.*;

public class FtpClientUtil {

    // ftp服务器ip地址
    private static String FTP_ADDRESS = "localhost";
    // 端口号
    private static int FTP_PORT = 3131;
    // 用户名
    private static String FTP_USERNAME = "bxl";
    // 密码
    private static String FTP_PASSWORD = "123456";
    // 相对路径
    private static String FTP_BASEPATH = "";

    public static boolean uploadFile(String remoteFileName, InputStream input) {
        boolean flag = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            System.out.println("登录ftp服务返回状态码为：" + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return flag;
            }
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
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return flag;
    }

//    public static Boolean uploadFile(String remoteFileName, InputStream inputStream, String ftpAddress, int ftpPort,
//                                     String ftpName, String ftpPassWord, String ftpBasePath) {
//        FTP_ADDRESS = ftpAddress;
//        FTP_PORT = ftpPort;
//        FTP_USERNAME = ftpName;
//        FTP_PASSWORD = ftpPassWord;
//        FTP_BASEPATH = ftpBasePath;
//        uploadFile(remoteFileName,inputStream);
//        return true;
//    }

    public static boolean deleteFile(String filename) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            // 连接FTP服务器
            ftpClient.connect(FTP_ADDRESS, FTP_PORT);
            // 登录FTP服务器
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD);
            // 验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(FTP_BASEPATH);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {

                }
            }
        }
        return flag;
    }

    public static boolean downloadFile(String filename, String localPath) {
        boolean flag = false;
//        FTPSClient ftpClient = new FTPSClient("TLS", true);
        FTPClient ftpClient = new FTPClient();
        try {
            // 连接FTP服务器
            ftpClient.connect(FTP_ADDRESS, FTP_PORT);
            // 登录FTP服务器
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD);
            // 验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                return flag;
            }
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(FTP_BASEPATH);
            //此处为demo方法，正常应该到数据库中查询fileName
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localPath + "/" + file.getName());
                    OutputStream os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
            System.out.println("文件下载完成！！！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                } catch (IOException e) {

                }
            }
        }
        return flag;
    }
}
