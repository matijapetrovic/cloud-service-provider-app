package api.organization;

import domain.organization.Organization;
import api.authentication.LoginController;
import application.App;
import domain.organization.OrganizationService;
import domain.organization.OrganizationStorage;
import domain.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

import static spark.Spark.*;
import static spark.Spark.put;

public class OrganizationController {
    private OrganizationStorage storage;
    private OrganizationService service;

    public OrganizationController(OrganizationStorage storage) {
        this.storage = storage;
        setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/organizations", () -> {
                before ("*", (q, a) -> setService(q.attribute("loggedIn")));
                get("", handleGetAll);
                get("/:name", handleGetSingle);
                post("/add", handlePost);
                put("/update/:name", handlePut);
            });
        });
    }

    private void setService(User user) {
        switch(user.getRole()) {
            case SUPER_ADMIN:
                 service = new SuperAdminOrganizationService(storage);
            case ADMIN:
                service = new AdminOrganizationService(storage);
            case USER:
                service = new RegularOrganizationService(storage);
            default:
                halt(500, "Something went wrong");
        }
    }

    private Route handleGetAll = (Request request, Response response) -> {
        response.status(200);
        return App.g.toJson(service.getAll());
	};

    private Route handleGetSingle = (Request request, Response response) -> {
        String name = request.params(":name");
        Organization organization = service.getSingle(name);
        // dodaj null check ovde ili u service
        response.status(200);
        return App.g.toJson(organization);
    };

    private Route handlePost = (Request request, Response response) -> {
        Organization organization = App.g.fromJson(request.body(), Organization.class);
        service.post(organization);
        response.status(201);
        return "Created";
    };

    private Route handlePut = (Request request, Response response) -> {
        LoginController.ensureUserRole(request, response, User.Role.ADMIN);

        Organization toUpdate = App.g.fromJson(request.body(), Organization.class);
        String name = request.params(":name");

        Optional<Organization> org = storage.findByName(name);
        if (org.isPresent()) {
            User loggedIn = request.attribute("loggedIn");
            if (!org.get().getUsers().contains(loggedIn)) {
                response.status(403);
                return App.g.toJson("Forbidden");
            }
        }

        if (!storage.update(toUpdate)) {
            response.status(400);
            return "Organization with the name " + name + " doesn't exist";
        }

        response.status(200);
        return "OK";
    };
}