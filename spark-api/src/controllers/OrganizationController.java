package controllers;

import main.App;
import models.Organization;
import spark.Request;
import spark.Response;
import spark.Route;

public class OrganizationController {
    public static Route getAllOrganizations = (Request request, Response response) -> {
        //LoginController.ensureUserIsLoggedIn(request, response);
        
        response.type("application/json");
        return App.g.toJson(App.organizationService.getAllOrganizations());
	};

    public static Route addOrganization = (Request request, Response response) -> {
          response.type("application/json");
          Organization org = App.g.fromJson(request.body(), Organization.class);
          App.organizationService.addOrganization(org);
          response.status(200);
          return "OK";
    };
}