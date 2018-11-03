package com.yhsoft.common.util;

import org.junit.Test;

/**
 * Created by zhuang on 9/2/2017.
 */
public class EncryptionUtilsTest {

    @Test
    public void encryptByMD5()
    {
        String result = EncryptionUtils.encryptByMD5("庄伟斌");
        System.out.println(result);
    }

    @Test
    public void encryptByAES()
    {
        String result = EncryptionUtils.encryptByAES("庄伟斌","zwb");
        System.out.println(result);
    }

    @Test
    public void decryptByAES()
    {
        String result = EncryptionUtils.decryptByAES("G9/ihVwl62ix4+Mpek+EyQ==","zwb");
        System.out.println(result);
    }
}
