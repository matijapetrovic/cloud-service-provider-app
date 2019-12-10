package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import models.User;
import models.User.Role;

public class UserService {
	private final List<User> users = loadUsersFromFile("users.txt");
	
	public Optional<User> getUser(String email) {
		return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
	}
	
	private List<User> loadUsersFromFile(String path) {
		List<User> users = new ArrayList<User>();
		users.add(new User("mattheo777@gmail.com", "Admin", "Adminovic", null, Role.SUPER_ADMIN));
		return users;
	}
}
