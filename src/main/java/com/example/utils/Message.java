package com.example.utils;

import java.io.Serializable;

/**
 * The Class Message to response errors.
 */
public class Message implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String message;

	public Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}