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
	}

	private Route handleGetAll = (Request request, Response response) -> {
		response.status(200);
		List<User> users = applyRoleFilter(request, service.getAll());
		List<UserDTO> users1 =  UserMapper.toUserDTOList(allExceptSuperAdmin(users));
		return App.g.toJson(users1);
	};

	private Route handleGetSingle = (Request request, Response response) -> {
		ensureUserHasPermission(request, User.Role.USER);

		String email = request.params(":email");

		response.status(200);
		return App.g.toJson(UserMapper.toUserDTO(service.getSingle(email)));
	};

	private Route handlePost = (Request request, Response response) -> {
		UserDTO user = App.g.fromJson(request.body(), UserDTO.class);
		if(!ensureUserGetHimself(request, user.getEmail())){
			ensureUserHasPermission(request, User.Role.ADMIN);
		}

		service.post(UserMapper.fromUserDTO(user));

		response.status(200);
		return "OK";
	};

	private Route handlePut = (Request request, Response response) -> {
		String email = request.params(":email");

		if(!ensureUserGetHimself(request, email)){
			ensureUserHasPermission(request, User.Role.ADMIN);
		}

		UserDTO user = App.g.fromJson(request.body(), UserDTO.class);

		service.put(email, UserMapper.fromUserDTO(user));
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

	private static boolean ensureUserGetHimself(Request request, String email){
		User user = request.attribute("loggedIn");
		if(user.getEmail().equals(email)){
			return true;
		}
		return false;
	}
}
