package com.zhuang.fileupload.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.zhuang.fileupload.exception.LoadConfigException;

public class FileUploadProperties {

    private Properties properties;

    private final static String STORE_PROVIDER = "zhuang.fileupload.store-provider";

    private final static String FTP_IP = "zhuang.fileupload.ftp-ip";
    private final static String FTP_USERNAME = "zhuang.fileupload.ftp-username";
    private final static String FTP_PASSWORD = "zhuang.fileupload.ftp-password";
    private final static String FTP_BASEPATH = "zhuang.fileupload.ftp-basepath";

    private final static String LOCAL_BASEPATH = "zhuang.fileupload.local-basepath";

    public FileUploadProperties()
    {
        this("config/zhuang-fileupload.properties");
    }

    public FileUploadProperties(String configFile) {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(configFile);
            properties = new Properties();
            properties.load(inputStream);

        } catch (IOException e) {
            throw new LoadConfigException("加载“zhuang-fileupload.properties”配置文件出错！");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public String getFtpIp() {
        return properties.getProperty(FTP_IP);
    }

    public String getFtpUserName() {
        return properties.getProperty(FTP_USERNAME);
    }

    public String getFtpPassword() {
        return properties.getProperty(FTP_PASSWORD);
    }

    public String getFtpBasePath() {
        return properties.getProperty(FTP_BASEPATH);
    }

    public String getStoreProvider() {
        return properties.getProperty(STORE_PROVIDER);
    }

    public String getLocalBasePath() {
        return properties.getProperty(LOCAL_BASEPATH);
    }

}
