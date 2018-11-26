package com.zltel.broadcast.um.util;

public class JumpOutRecursionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	public JumpOutRecursionException() {}
	public JumpOutRecursionException(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
