package com.example.leo.Exception;

public class UsernameOrIdNotFound extends Exception{

	private static final long serialVersionUID = 1L;

	public UsernameOrIdNotFound() {
		super("Usuario o Id no encontrado");
	}
	public UsernameOrIdNotFound(String message) {
		super(message);
	}

}
