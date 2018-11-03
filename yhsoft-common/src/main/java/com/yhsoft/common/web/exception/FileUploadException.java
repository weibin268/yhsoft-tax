package com.yhsoft.common.web.exception;

public class FileUploadException extends RuntimeException {

    public FileUploadException() {
        super();
    }

    public FileUploadException(String s) {
        super(s);
    }

    public FileUploadException(String s, Throwable casue) {
        super(s, casue);
    }
}
