package services;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import models.User;
import models.User.Role;

public class UserService {
	private final Set<User> users = loadUsersFromFile("users.txt");
	
	public Iterable<User> getAllUsers() {
		return users;
	}

	public Optional<User> getUser(String email) {
		return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
	}

	// TODO - update i delete
	
	private Set<User> loadUsersFromFile(String path) {
		Set<User> users = new HashSet<User>();
		users.add(new User("mattheo777@gmail.com", "Admin", "Adminovic", null, Role.SUPER_ADMIN));
		return users;
	}
}
