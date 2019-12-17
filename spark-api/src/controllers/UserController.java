package controllers;

import static main.App.userService;

import java.util.Optional;

import models.User;
import models.User.Role;
import services.UserService;

import main.App;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {
	public static boolean authenticate(String email) {
		if (email == null || email.isEmpty())
			return false;
		
		Optional<User> user = userService.getUser(email);
		return user.isPresent();
	}

	public static Route serveUserPage = (Request request, Response response) -> {
		//LoginController.ensureUserIsLoggedIn(request, response);
		
		response.type("application/json");
		
		// ne UserService.getCurrentUser(request) ne vraca User type
		// if(UserService.getCurrentUser(request).getRole() == Role.ADMIN){
		// 	return App.g.toJson(App.userService.getAllUsersFromSameOrganization(UserService.getCurrentUser(request)));
		// }
        return App.g.toJson(App.userService.getAllUsers());
	};
}
