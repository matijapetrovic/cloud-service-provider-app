package api.user;

import application.App;
import domain.user.User;
import domain.user.UserService;
import domain.user.UserStorage;
import spark.Request;
import spark.Response;
import spark.Route;

import static api.authentication.LoginController.ensureUserHasPermission;
import static spark.Spark.*;
import static spark.Spark.put;

public class UserController {
	private UserService service;

	public UserController(UserStorage storage) {
		this.service = new UserRESTService(storage);
		setUpRoutes();
	}

	private void setUpRoutes() {
		path("api", () -> {
			path("/users", () -> {
				get("", serveUserPage);
				get("/currentUser", serveCurrentUser);
				get("/:email", handleGetSingle);
				post("/add", handlePost);
				put("/update/:email", handlePut);
				delete("/delete/:email", handleDelete);
			});
		});
	}

	private Route serveUserPage = (Request request, Response response) -> {
		response.type("application/json");
		User currentUser = request.attribute("loggedIn");

		response.status(200);

		return App.g.toJson(UserMapper.toUserDTOList(service.getAll()));
	};

	private Route serveCurrentUser = (Request request, Response response) -> {
		User currentUser = request.attribute("loggedIn");
		return App.g.toJson(currentUser);
	};

	private Route handleGetSingle = (Request request, Response response) -> {
		ensureUserHasPermission(request, User.Role.ADMIN);

		String email = request.params(":email");

		response.status(200);
		return App.g.toJson(UserMapper.toUserDTO(service.getSingle(email)));
	};

	private Route handlePost = (Request request, Response response) -> {
		ensureUserHasPermission(request, User.Role.SUPER_ADMIN);

		User user = App.g.fromJson(request.body(), User.class);
		service.post(user);

		response.status(200);
		return "OK";
	};

	private Route handlePut = (Request request, Response response) -> {
		ensureUserHasPermission(request, User.Role.ADMIN);

		User user = App.g.fromJson(request.body(), User.class);
		String email = request.params(":email");

		service.put(email, user);
		response.status(200);
		return "OK";
	};

	private Route handleDelete = (Request request, Response response) -> {
		ensureUserHasPermission(request, User.Role.ADMIN);

		User user = App.g.fromJson(request.body(), User.class);
		String email = request.params(":email");

		service.delete(email);
		response.status(200);
		return "OK";
	};

}
