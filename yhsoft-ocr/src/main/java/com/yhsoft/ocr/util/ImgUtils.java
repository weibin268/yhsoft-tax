package com.yhsoft.ocr.util;

import org.apache.commons.codec.binary.Base64;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImgUtils {
    public static String toBase64String(String filePath) {
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            return new String(Base64.encodeBase64(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
