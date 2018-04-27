package com.puneet.test.game.exception;

public class IllegalMoveException extends RuntimeException {
	private static final long serialVersionUID = -7944599861438674787L;

		public IllegalMoveException(String message) {
	        super(message);
	    }
}
