package com.yhsoft.ocr.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImgUtilsTest {

    @Test
    public void toBase64String() {
        String filePath = "D:\\zhuang\\temp\\test001.jpg";
        System.out.println(ImgUtils.toBase64String(filePath));
    }
}