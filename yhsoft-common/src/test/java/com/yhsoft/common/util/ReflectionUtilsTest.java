package com.yhsoft.common.util;

import com.yhsoft.common.web.model.MyJsonResult;
import org.junit.Test;

public class ReflectionUtilsTest {

    @Test
    public void getFieldValue() {

        MyJsonResult myJsonResult = new MyJsonResult();
        myJsonResult.setMessage("hello zwb!");
        String fieldName = "message";
        if (ReflectionUtils.hasField(myJsonResult.getClass(), fieldName)) {
            System.out.println(ReflectionUtils.getFieldValue(myJsonResult, myJsonResult.getClass(), fieldName));
        } else {
            System.out.println("no such field:" + fieldName);
        }
    }

    @Test
    public void invokeMethod() {
    }
}