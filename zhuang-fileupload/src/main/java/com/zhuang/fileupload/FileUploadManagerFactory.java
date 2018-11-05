package com.zhuang.fileupload;

import com.zhuang.fileupload.config.FileUploadProperties;
import com.zhuang.fileupload.impl.ftp.FtpStoreProvider;
import com.zhuang.fileupload.impl.local.LocalStoreProvider;
import com.zhuang.fileupload.service.impl.DefaultFileUploadService;

public class FileUploadManagerFactory {

	private static FileUploadManager fileUploadManager;

	public static synchronized FileUploadManager getDefaultFileUploadManager() {

		if (fileUploadManager == null) {

			FileUploadProperties fileUploadProperties = new FileUploadProperties();

			if (fileUploadProperties.getStoreProvider().equalsIgnoreCase("ftp")) {

				fileUploadManager = new FileUploadManager(new FtpStoreProvider(), new DefaultFileUploadService());

			} else if (fileUploadProperties.getStoreProvider().equalsIgnoreCase("local")) {

				fileUploadManager = new FileUploadManager(new LocalStoreProvider(), new DefaultFileUploadService());

			}

		}

		return fileUploadManager;
	}

}
