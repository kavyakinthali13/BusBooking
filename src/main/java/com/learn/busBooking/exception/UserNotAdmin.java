package com.learn.busBooking.exception;

public class UserNotAdmin extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public UserNotAdmin(String string) {
		super(string);
	}
}
