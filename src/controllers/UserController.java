package controllers;

import static main.App.userService;

import java.util.Optional;

import models.User;

public class UserController {
	public static boolean authenticate(String email) {
		if (email == null || email.isEmpty())
			return false;
		
		Optional<User> user = userService.getUser(email);
		return user.isPresent();
	}
}
