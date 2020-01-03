package api.user;

import api.Service;
import domain.user.User;
import storage.user.UserRepository;

import java.util.Collection;

public class UserService extends Service<String, User> {
	public UserService() {
		super(new UserRepository("./data/users.json"));
		}

	public Collection<User> getAllUsersFromSameOrganization(User user){
		return user.getOrganization().getUsers();
	}

}
