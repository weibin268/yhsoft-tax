package com.yhsoft.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zhuang
 * @create 6/3/18 12:56 AM
 **/
public class ReflectionUtils {

    public static Object getFieldValue(Object target, Class clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException(clazz.getName() + "." + fieldName, e);
        }
    }

    public static Object invokeMethod(Object target, Class clazz, String methodName, Object... args) {
        try {
            Method method = clazz.getMethod(methodName);
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException(clazz.getName() + "." + methodName, e);
        }
    }

    public static boolean hasField(Class clazz, String fieldName) {
        boolean result = false;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
