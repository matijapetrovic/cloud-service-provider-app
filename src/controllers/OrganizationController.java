package controllers;

import spark.Request;
import spark.Response;
import spark.Route;

public class OrganizationController {
    public static Route serveOrganizationsPage = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        
        response.redirect("organizations.html");
        return null;
	};
}