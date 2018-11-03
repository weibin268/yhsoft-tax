package com.yhsoft.common.web.restapi.args;


import com.yhsoft.common.web.restapi.BaseArgs;

public class DataArgs<T> extends BaseArgs {

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
