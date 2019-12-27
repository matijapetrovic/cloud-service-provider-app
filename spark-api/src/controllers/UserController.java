package controllers;

import java.util.Optional;

import main.App;
import models.Organization;
import models.User;
import models.VirtualMachine;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

	public static Route serveUserPage = (Request request, Response response) -> {
		//LoginController.ensureUserIsLoggedIn(request, response);

		response.type("application/json");
		User currentUser = App.userService.getCurrentUser(request);

		switch(App.userService.getCurrentUser(request).getRole()) {
			case SUPER_ADMIN:
				return App.g.toJson(App.userService.findAll());
			case ADMIN:
				return App.g.toJson(App.userService.getAllUsersFromSameOrganization(currentUser));
			case USER:
				break;
			default:
				return "User "; // ovde vracam za obicnog usera
		}


		response.status(400);
        return "Something went wrong!";
	};

	public static Route serveCurrentUser = (Request request, Response response) -> {
		response.type("application/json");
		return App.g.toJson(App.userService.getCurrentUser(request));
	};

	public static Route handleGetSingle = (Request request, Response response) -> {
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

	public static Route handleUsersOrganization = (Request request, Response response) -> {
		String email = request.params(":name");
		Optional<User> user = App.userService.findByKey(email);

		response.type("application/json");

		if(!user.isPresent()){
			response.status(400);
			return "User with email " + email + " does not exist!";
		}
		Organization org = user.get().getOrganization();

		response.status(200);
		return App.g.toJson(org);

	};

	public static Route handlePost = (Request request, Response response) -> {
		User user = App.g.fromJson(request.body(), User.class);

		response.type("application/json");

		if (!App.userService.add(user)) {
			response.status(400);
			return "Virtual machine with the name " + user.getEmail() + " already exists";
		}

		response.status(200);
		return "OK";
	};

	public static Route handlePut = (Request request, Response response) -> {
		User user = App.g.fromJson(request.body(), User.class);
		String email = request.params(":name");
		Optional<User> toFind = App.userService.findByKey(email);

		response.type("applicatioj/json");

		if(!toFind.isPresent()){
			response.status(400);
			return "User with email " + email + " does not exist !";
		}

		App.userService.update(email, user);

		response.status(200);
		return "OK";
	};
}
