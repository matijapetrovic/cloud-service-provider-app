package api.organization;

import domain.organization.Organization;
import api.authentication.LoginController;
import application.App;
import domain.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

public class OrganizationController {
    public static Route handleGetAll = (Request request, Response response) -> {
        LoginController.ensureUserRole(request, response, User.Role.SUPER_ADMIN);

        response.type("application/json");
        return App.g.toJson(App.organizationService.findAll());
	};

    public static Route handleGetSingle = (Request request, Response response) -> {
        LoginController.ensureUserRole(request, response, User.Role.SUPER_ADMIN);

        String name = request.params(":name");
        Optional<Organization> org = App.organizationService.findByKey(name);

        response.type("application/json");
        if (!org.isPresent()) {
            response.status(400);
            return "Organization with the name " + name + " doesn't exist";
        }

        response.status(200);
        return App.g.toJson(org.get());
    };

    public static Route handlePost = (Request request, Response response) -> {
        LoginController.ensureUserRole(request, response, User.Role.SUPER_ADMIN);

        Organization org = App.g.fromJson(request.body(), Organization.class);

        response.type("application/json");
        if (!App.organizationService.add(org)) {
            response.status(400);
            return "Organization with the name " + org.getName() + " already exists";
        }

        response.status(200);
        return "OK";
    };

    public static Route handlePut = (Request request, Response response) -> {
        LoginController.ensureUserRole(request, response, User.Role.ADMIN);

        Organization toUpdate = App.g.fromJson(request.body(), Organization.class);
        String name = request.params(":name");

        Optional<Organization> org = App.organizationService.findByKey(name);
        if (org.isPresent()) {
            User loggedIn = request.attribute("loggedIn");
            if (!org.get().getUsers().contains(loggedIn)) {
                response.status(403);
                return App.g.toJson("Forbidden");
            }
        }


        response.type("application/json");
        if (!App.organizationService.update(name, toUpdate)) {
            response.status(400);
            return "Organization with the name " + name + " doesn't exist";
        }

        response.status(200);
        return "OK";
    };
}