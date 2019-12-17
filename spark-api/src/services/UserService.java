package services;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

import spark.Request;

import models.User;
import models.User.Role;

public class UserService {
	private final Set<User> users = loadUsersFromFile("users.txt");
	
	public Iterable<User> getAllUsers() {
		return users;
	}

	public Iterable<User> getAllUsersFromSameOrganization(User user) {
		return users.stream().filter(u -> u.getOrganization().equals(user.getOrganization())).collect(Collectors.toSet());
	}

	public Optional<User> getUser(String email) {
		return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
	}

	
	public boolean updateUser(String email){
		Optional<User> u = getUser(email);
		if(!u.isPresent()) {
			return false;
		}
		
		u.get().setEmail(email);
		users.removeIf(us -> us.getEmail().equals(email));
		users.add(u.get());
		return true;	
	}
	
	// Super admin
	public boolean deleteUser(String email) {
		if(!users.contains(getUser(email).get())) {
			return false;
		}
		users.removeIf(u -> u.getEmail().equals(email));
		return true;
	}

	private Set<User> loadUsersFromFile(String path) {
		Set<User> users = new HashSet<User>();
		users.add(new User("mattheo777@gmail.com", "Admin", "Adminovic", null, Role.SUPER_ADMIN));
		users.add(new User("nikola@gmail.com", "Admino", "Adminovovski", null, Role.ADMIN));
		return users;
	}

	public static User getCurrentUser(Request request){
		return request.session().attribute("user");
	}
}
