package controllers;

import java.util.Optional;

import main.App;
import models.User;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

	public static Route serveUserPage = (Request request, Response response) -> {
		//LoginController.ensureUserIsLoggedIn(request, response);
		
		response.type("application/json");
		
		// ne UserService.getCurrentUser(request) ne vraca User type
		// if(UserService.getCurrentUser(request).getRole() == Role.ADMIN){
		// 	return App.g.toJson(App.userService.getAllUsersFromSameOrganization(UserService.getCurrentUser(request)));
		// }
        return App.g.toJson(App.userService.findAll());
	};

	public static Route serveCurrentUser = (Request request, Response response) -> {
		response.type("application/json");
		return App.g.toJson(App.userService.getCurrentUser(request));
	};

	public static Route serveGetSingle = (Request request, Response response) -> {
		String email = request.params("/:name");
		Optional<User> user = App.userService.findByKey(email);

		response.type("application/json");

		if(!user.isPresent()){
			response.status(400);
			return "User with email " + email + " does not exist!";
		}

		response.status(200);
		return App.g.toJson(user.get());
	};
}
