package api.authentication;


import java.util.Optional;

import domain.user.UserStorage;
import org.eclipse.jetty.http.HttpStatus;
import org.jose4j.lang.JoseException;
import spark.Request;
import spark.Response;
import spark.Route;

import application.App;
import domain.user.User;

import static spark.Spark.*;

public class LoginController {
	private UserStorage storage;

	public LoginController(UserStorage storage) {
		this.storage = storage;
		setUpRoutes();
	}

	private void setUpRoutes() {
		path("api", () -> {
			before("/*", this::ensureUserIsLoggedIn);
			path("/login", () -> {
				post("", handlePost);
			});
		});
	}

	private Route handlePost = (Request request, Response response) -> {
		User toLogin = App.g.fromJson(request.body(), User.class);

		User user = validateEmail(toLogin);
		validatePassword(toLogin, user);
		String token = createToken(user.getEmail());

		response.status(200);
		return App.g.toJson(new TokenResponse(true, token, new UserResponse(user)));
	};

	private User validateEmail(User toLogin) {
		Optional<User> user = storage.findByName(toLogin.getEmail());
		if (!user.isPresent())
			halt(HttpStatus.UNAUTHORIZED_401, "Invalid e-mail");
		return user.get();
	}

	private static void validatePassword(User toLogin, User user) {
		if (!toLogin.getPassword().equals(user.getPassword()))
			halt(HttpStatus.UNAUTHORIZED_401, "Invalid password");
	}

	public static void ensureUserHasPermission(Request request, User.Role role) {
		User user = request.attribute("loggedIn");
		if (user.getRole().weakerThan(role))
			halt(403,"Forbidden");
	}

	public void ensureUserIsLoggedIn(Request request, Response response) {
		if (request.pathInfo().equalsIgnoreCase("/api/login"))
			return;

		String token = request.headers("Authorization");
		User user = validateToken(token);
		request.attribute("loggedIn", user);
	}

	private static String createToken(String email) {
		String token = "";
		try {
			token = JWTUtil.createToken(email);
		} catch(JoseException e) {
			halt(HttpStatus.INTERNAL_SERVER_ERROR_500, "Internal server error");
		}

		return token;
	}

	private User validateToken(String token) {
		String email = getEmail(token);
		Optional<User> user = storage.findByName(email);
		if (!user.isPresent())
			halt(HttpStatus.UNAUTHORIZED_401, App.g.toJson("Invalid token"));
		return user.get();
	}

	private static String getEmail(String token) {
		String email = "";
		try {
			email = JWTUtil.getEmail(token);
		} catch(JoseException e) {
			halt(HttpStatus.INTERNAL_SERVER_ERROR_500, App.g.toJson("Internal server error"));
		}

		return email;
	}
}
