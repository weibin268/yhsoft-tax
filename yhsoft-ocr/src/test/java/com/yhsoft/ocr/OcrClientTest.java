package com.yhsoft.ocr;

import com.yhsoft.ocr.aliyun.AliyunOcrClient;
import com.yhsoft.ocr.model.CommonResult;
import com.yhsoft.ocr.util.ImgUtils;
import com.yhsoft.ocr.util.JsonUtils;
import org.junit.Test;

public class OcrClientTest {

    OcrClient ocrClient = new AliyunOcrClient();

    @Test
    public void recognizeCommon() {
        String filePath = "D:\\zhuang\\temp\\test001.jpg";
        CommonResult commonResult = ocrClient.recognizeCommon(ImgUtils.toBase64String(filePath));
        System.out.println(JsonUtils.toJsonString(commonResult));
    }

    @Test
    public void recognizeIdCard() {
    }
}