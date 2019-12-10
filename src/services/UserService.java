package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import models.User;

public class UserService {
	private final List<User> users = loadUsersFromFile("users.txt");
	
	public Optional<User> getUser(String email) {
		return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
	}
	
	private List<User> loadUsersFromFile(String path) {
		return new ArrayList<User>();
	}
	
}
