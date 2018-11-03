package com.yhsoft.common.web.model;

public class MyJsonResult {

	private boolean success;
	private boolean valid;
	private String message;
	private Object data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "MyJsonResult{" +
				"success=" + success +
				", valid=" + valid +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}

}
