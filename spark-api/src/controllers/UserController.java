package controllers;


import main.App;
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
        return App.g.toJson(App.userService.getAllUsers());
	};

	public static Route serveCurrentUser = (Request request, Response response) -> {
		response.type("application/json");
		return App.g.toJson(App.userService.getCurrentUser(request));
	};

}
