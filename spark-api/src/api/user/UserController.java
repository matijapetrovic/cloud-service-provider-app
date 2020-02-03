package api.user;

import application.App;
import domain.organization.Organization;
import domain.user.User;
import domain.user.UserService;
import domain.user.UserStorage;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.stream.Collectors;

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
				get("", handleGetAll);
				get("/:email", handleGetSingle);
				post("/add", handlePost);
				put("/update/:email", handlePut);
				delete("/delete/:email", handleDelete);
			});
		});
		service.post(new User("admin@gmail.com", "admin", "Admin", "Adminovic", null, User.Role.SUPER_ADMIN));
	}

	private Route handleGetAll = (Request request, Response response) -> {
		response.status(200);
		List<User> users = applyRoleFilter(request, service.getAll());
		List<UserDTO> users1 =  UserMapper.toUserDTOList(allExceptSuperAdmin(users));
		return App.g.toJson(users1);
	};

	private Route handleGetSingle = (Request request, Response response) -> {
		String email = request.params(":email");
		User user = service.getSingle(email);
		ensureUserCanAccessUser(request, user);

		response.status(200);
		return createUserResponse(service.getSingle(email));
	};

	private Route handlePost = (Request request, Response response) -> {
		ensureUserHasPermission(request, User.Role.ADMIN);

		User toAdd = parseUser(request);
		ensureUserCanAccessUser(request, toAdd);

		service.post(toAdd);
		response.status(200);
		return "OK";
	};

	private Route handlePut = (Request request, Response response) -> {
		String email = request.params(":email");
		User user = service.getSingle(email);
		ensureUserCanAccessUser(request, user);

		User toUpdate = parseUser(request);
		service.put(email, toUpdate);
		response.status(200);
		return "OK";
	};

	private Route handleDelete = (Request request, Response response) -> {
		ensureUserHasPermission(request, User.Role.ADMIN);

		String email = request.params(":email");
		User user = service.getSingle(email);
		ensureUserCanAccessUser(request, user);

		service.delete(email);
		response.status(200);
		return "OK";
	};

	private User parseUser(Request request) {
		try {
			UserDTO dto = App.g.fromJson(request.body(), UserDTO.class);
			User user = UserMapper.fromUserDTO(dto);
			if(user == null)
				halt(400, "Required fields missing");
			return user;
		} catch(Exception e) {
			halt(400, "Bad request");
		}
		// Unreachable
		return null;
	}

	private String createUserResponse(User user) {
		return App.g.toJson(UserMapper.toUserDTO(user));
	}

	private List<User> applyRoleFilter(Request request, List<User> users) {
		User user = request.attribute("loggedIn");
		if (user.getRole().equals(User.Role.SUPER_ADMIN))
			return users.stream().
					filter(x -> !x.getEmail().equals(user.getEmail())
					).collect(Collectors.toList());

		Organization org = user.getOrganization();
		return users
				.stream()
				.filter(x -> org.getUsers().contains(x) && !x.getEmail().equals(user.getEmail()))
				.collect(Collectors.toList());
	}

	private List<User> allExceptSuperAdmin (List<User> users) {
		return users.stream().filter(x -> x.getRole() != User.Role.SUPER_ADMIN).collect(Collectors.toList());
	}

	private static void ensureUserCanAccessUser(Request request, User user) {
		User loggedIn = request.attribute("loggedIn");
		User.Role role = loggedIn.getRole();
		if (role == User.Role.ADMIN) {
			if (!user.getOrganization().equals(loggedIn.getOrganization()))
				halt(403, "Forbidden");
		}
		else if (role == User.Role.USER) {
			if (user != loggedIn)
				halt(403, "Forbidden");
		}
	}
}
