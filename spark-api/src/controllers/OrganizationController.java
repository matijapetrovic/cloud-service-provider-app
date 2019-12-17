package controllers;

import main.App;
import spark.Request;
import spark.Response;
import spark.Route;

public class OrganizationController {
    public static Route serveOrganizationsPage = (Request request, Response response) -> {
        //LoginController.ensureUserIsLoggedIn(request, response);
        
        response.type("application/json");
        return App.g.toJson(App.organizationService.getAllOrganizations());
	};
}