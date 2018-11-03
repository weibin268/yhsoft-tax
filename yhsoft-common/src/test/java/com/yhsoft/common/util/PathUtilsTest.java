package com.yhsoft.common.util;

import org.junit.Test;

public class PathUtilsTest {

    @Test
    public void getPathByPackage() {

        System.out.println(PathUtils.getPathByPackage("com.zhuang.upms"));
    }

    @Test
    public void combine() {
        System.out.println(PathUtils.combine("a\\a", "/b\\b", "c\\c"));
    }

    @Test
    public void getAbsolutePath() {
        System.out.println(PathUtils.getAbsolutePath("./com"));
    }
}