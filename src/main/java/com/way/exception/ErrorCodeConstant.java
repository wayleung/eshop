package com.way.exception;

import java.io.Serializable;

/**
 * 
* @author Way Liang 
* @Description: TODO(系统异常)   
* @date 2018年2月11日
 */

public enum ErrorCodeConstant implements Serializable {
	//common error
	E00000("E00000","操作失败"),
	E00001("E00001","操作成功"),

	;
	
	

	private final String code;

	private final String message;

	private ErrorCodeConstant(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

}