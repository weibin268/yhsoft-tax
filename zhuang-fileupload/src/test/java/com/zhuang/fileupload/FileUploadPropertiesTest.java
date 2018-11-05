package com.zhuang.fileupload;

import org.junit.Test;

import com.zhuang.fileupload.config.FileUploadProperties;

public class FileUploadPropertiesTest {

	@Test
	public void test() {
		
		FileUploadProperties fileUploadProperties=new FileUploadProperties();
		System.out.println(fileUploadProperties.getFtpIp());
		System.out.println(fileUploadProperties.getFtpUserName());
		System.out.println(fileUploadProperties.getFtpPassword());
		
	}
	
}
