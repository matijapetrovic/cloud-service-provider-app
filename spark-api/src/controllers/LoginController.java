package controllers;

import org.eclipse.jetty.http.HttpStatus;
import services.LoginService;
import authentication.TokenResponse;
import main.App;
import models.User;
import org.jose4j.lang.JoseException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

import static main.App.userService;
import static spark.Spark.halt;

public class LoginController {
	
	public static Route handlePost = (Request request, Response response) -> {
		User toLogin = App.g.fromJson(request.body(), User.class);

		Optional<User> user = userService.getUser(toLogin.getEmail());
		if (!user.isPresent()) {
			response.status(404);
			return App.g.toJson("No user found");
		}

		if (!toLogin.getPassword().equals(user.get().getPassword())) {
			response.status(401);
			return App.g.toJson(new TokenResponse(false, null));
		}

		String token;
		try {
			token = LoginService.createToken(user.get().getEmail());
		} catch(JoseException e) {
			response.status(500);
			return App.g.toJson("Internal server error");
		}

		response.status(200);
		return App.g.toJson(new TokenResponse(true, token)); // verovatno treba dodati usera u response
	};

	public static void ensureUserIsLoggedIn(Request request, Response response) {
		if (request.pathInfo().equalsIgnoreCase("/api/login"))
			return;

		String token = request.headers("Authorization"); // mozda menjamo
		String email = null;
		try {
			email = LoginService.getEmail(token);
		} catch(JoseException e) {
			halt(HttpStatus.INTERNAL_SERVER_ERROR_500, App.g.toJson("Internal server error"));
		}

		Optional<User> user = userService.getUser(email);

		if (!user.isPresent())
			halt(HttpStatus.UNAUTHORIZED_401, App.g.toJson("Invalid token"));

		request.attribute("loggedIn", user.get());
	}

	public static void ensureUserRole(Request request, Response response, User.Role role) {
		User user = request.attribute("loggedIn");

		if (user.getRole().compare(role) < 0) {
			response.status(403);
			halt(HttpStatus.FORBIDDEN_403,"Forbidden");
		}
	}
}
