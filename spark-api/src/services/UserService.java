package services;

import repositories.UserRepository;
import spark.Request;

import models.User;

public class UserService extends Service<String, User> {
	public UserService() {
		super(new UserRepository("./data/users.json"));
		}


	public User getCurrentUser(Request request){
		return request.attribute("loggedIn");
	}
}
