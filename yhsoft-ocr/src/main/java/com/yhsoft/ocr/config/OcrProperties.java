package com.yhsoft.ocr.config;

import com.yhsoft.ocr.exception.LoadConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OcrProperties {
    public final static String DEFAULT_CONFIG_FILE_PATH = "config/ocr.properties";
    private Properties properties;
    private final static String ALIYUN_OCR_API_ADVANCED_URL = "aliyun.ocr-api-advanced-url";
    private final static String ALIYUN_OCR_API_ADVANCED_APPCODE = "aliyun.ocr-api-advanced-appcode";

    public OcrProperties() {
        this(DEFAULT_CONFIG_FILE_PATH);
    }

    public OcrProperties(String configFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(configFilePath);
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new LoadConfigException("加载“ocr.properties”配置文件出错！");
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

    public String getAliyunOcrApiAdvancedUrl() {
        return properties.getProperty(ALIYUN_OCR_API_ADVANCED_URL);
    }

    public String getAliyunOcrApiAdvancedAppCode() {
        return properties.getProperty(ALIYUN_OCR_API_ADVANCED_APPCODE);
    }

}
