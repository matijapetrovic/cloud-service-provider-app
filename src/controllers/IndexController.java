package controllers;

import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController {
	public static Route serveIndexPage = (Request request, Response response) -> {
		if(request.session().attribute("user") == null){
			request.session().attribute("loginRedirect", true);
			response.redirect("/login");
		}
		
		return "Hello , "+ request.session().attribute("user");
	};	
}
