package com.zltel.broadcast.area_manage.util;

/**
 * 区域冲突异常
 * @author imzhy
 *
 */
public class AreaConflictException extends Exception {
	private static final long serialVersionUID = 1L;

	public AreaConflictException() {}
	
	public AreaConflictException(String msg) {
		super(msg);
	}
}
