package controllers;

import static main.App.userService;

import java.util.Optional;

import models.User;

public class UserController {
	public boolean authenticate(String email) {
		if (email.isEmpty())
			return false;
		
		Optional<User> user = userService.getUser(email);
		return user.isPresent();
	}
}
