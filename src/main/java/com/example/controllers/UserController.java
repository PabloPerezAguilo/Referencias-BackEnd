package com.example.controllers;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

import com.example.dao.UserDAO;
import com.example.models.User;

public class UserController {

	// Singleton instances
	private UserDAO dao;
	private static UserController singleton;

	private UserController() throws Exception {
		dao = UserDAO.getInstance();
	}

	public static UserController getInstance() throws Exception {
		if (singleton == null) {
			singleton = new UserController();
		}
		return singleton;
	}

	/**
	 * Check user/password and return the role
	 */
	public String loginUser(String idUser, String pass) throws Exception {
		// Get the user hashed and salted password
		User user = dao.getUser(idUser);
		if (user == null) {
			throw new Exception("User not found");
		}
		String hashedAndSalted = user.getPassword();
		String salt = hashedAndSalted.split(",")[1];
		String newHashedAndSalted = makePasswordHash(pass, salt);
		// Takes both hashed and salted passwords and compare it
		if (!hashedAndSalted.equals(newHashedAndSalted)) {
			throw new Exception("Password wrong");
		}
		// If its all right return the role
		return user.getRole();
	}

	/**
	 * Create new user
	 */
	public void createUser(String name, String role, String password) throws Exception {
		dao.insertUser(new User(name, role, this.makePasswordHash(password, this.generateSalting())));
	}

	/**
	 * Get all users
	 */
	public List<User> getUsers() throws Exception {
		List<User> list = new ArrayList<User>();
		Iterator<User> i = dao.getUsers();
		while (i.hasNext()) {
			list.add(i.next());
		}
		return list;
	}

	// PRIVATE METHODS TO GENERATE PASSWORDS

	/**
	 * Generate a Hash for given Password.
	 */
	private String makePasswordHash(String password, String salt) throws Exception {

		String saltedAndHashed = password + "," + salt;
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(saltedAndHashed.getBytes("UTF-8"));
		Base64 encoder = new Base64();
		byte[] hashedBytes = (new String(digest.digest(), "UTF-8")).getBytes("UTF-8");
		return new String(encoder.encode(hashedBytes), "UTF-8") + "," + salt;
	}

	/**
	 * Generate a random salting.
	 */
	private String generateSalting() {
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

}