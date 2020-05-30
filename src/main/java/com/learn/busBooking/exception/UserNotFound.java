package com.learn.busBooking.exception;

public class UserNotFound extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserNotFound(String string) {
		super(string);
	}
}
