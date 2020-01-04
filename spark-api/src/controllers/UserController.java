package controllers;

import java.util.Collection;
import java.util.Optional;

import main.App;
import models.Organization;
import models.User;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

	public static Route serveUserPage = (Request request, Response response) -> {
		response.type("application/json");
		User currentUser = request.attribute("loggedIn");

		response.status(200);
		switch(currentUser.getRole()) {
			case SUPER_ADMIN:
			case USER:
				return App.g.toJson(App.userService.findAll());
			case ADMIN:
				return App.g.toJson(App.userService.getAllUsersFromSameOrganization(currentUser));
			default:
				response.status(400);
				return "Something went wrong!";
		}


	};

	public static Route serveCurrentUser = (Request request, Response response) -> {
		response.type("application/json");
		User currentUser = request.attribute("loggedIn");
		return App.g.toJson(currentUser);
	};

	public static Route handleGetSingle = (Request request, Response response) -> {
		String email = request.params(":name");
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
		Collection<User> usersFromOrganization = org.getUsers();

		response.status(200);
		return App.g.toJson(usersFromOrganization);
	};

	public static Route handlePost = (Request request, Response response) -> {
		LoginController.ensureUserRole(request, response, User.Role.SUPER_ADMIN);

		User user = App.g.fromJson(request.body(), User.class);

		response.type("application/json");
		if (!App.userService.add(user)) {
			response.status(400);
			return "User with the email " + user.getEmail() + " already exists";
		}

		response.status(200);
		return "OK";
	};

	public static Route handlePut = (Request request, Response response) -> {
		User user = App.g.fromJson(request.body(), User.class);
		String email = request.params(":name");
		Optional<User> toFind = App.userService.findByKey(email);

		response.type("application/json");

		if(!toFind.isPresent()){
			response.status(400);
			return "User with email " + email + " does not exist !";
		}

		App.userService.update(email, user);

		response.status(200);
		return "OK";
	};

	public static Route handleDelete = (Request request, Response response) -> {
		User user = App.g.fromJson(request.body(), User.class);
		String email = request.params(":name");
		Optional<User> toFind = App.userService.findByKey(email);

		response.type("application/json");

		if(!toFind.isPresent()){
			response.status(400);
			return "User with email " + email + " does not exist !";
		}

		App.userService.delete(email);

		response.status(200);
		return "OK";
	};

}
