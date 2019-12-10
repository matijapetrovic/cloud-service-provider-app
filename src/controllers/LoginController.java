package controllers;

import spark.Request;
import spark.Response;
import spark.Route;

public class LoginController {
	public static Route serveLoginPage = (Request request, Response response) -> {
		if (request.session().attribute("user") != null)
			response.redirect("/");
		response.redirect("login.html");
		return null;
	};
	
	public static Route handleLoginPost = (Request request, Response response) -> {
		
		if (!UserController.authenticate(request.queryParams("email")))
			return "user does not exist";
		request.session().attribute("user", request.queryParams("email"));
		if(request.session().attribute("loginRedirect") != null)
			response.redirect("/");
		return "Successfully logged in";
	};
}
