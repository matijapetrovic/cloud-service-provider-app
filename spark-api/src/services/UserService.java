package services;

import repositories.UserRepository;

import models.User;

import java.util.Collection;

public class UserService extends Service<String, User> {
	public UserService() {
		super(new UserRepository("./data/users.json"));
		}

	public Collection<User> getAllUsersFromSameOrganization(User user){
		return user.getOrganization().getUsers();
	}

}
