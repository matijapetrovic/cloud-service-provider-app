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
        return App.g.toJson(App.organizationService.findAll());
	};

    public static Route postOrganization = (Request request, Response response) -> {
        Organization org = App.g.fromJson(request.body(), Organization.class);

        response.type("application/json");
        if (!App.organizationService.add(org)) {
            response.status(400);
            return "Organization with the name " + org.getName() + " already exists";
        }
        response.status(200);
        return "OK";
    };

    public static Route putOrganization = (Request request, Response response) -> {
        Organization org = App.g.fromJson(request.body(), Organization.class);
        String name = request.params(":name");

        response.type("application/json");
        if (!App.organizationService.update(name, org)) {
            response.status(400);
            return "Organization with the name " + name + " doesn't exist";
        }

        response.status(200);
        return "OK";
    };
}