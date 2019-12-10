package controllers;

import javax.websocket.Session;

import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController {
	public static Route serveIndexPage = (Request request, Response response) -> {
		if(request.session().attribute("user") == null)
			return false;
		
		return "Hello from index page";
	};
}
