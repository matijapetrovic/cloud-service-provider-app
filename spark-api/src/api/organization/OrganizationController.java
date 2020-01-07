package api.organization;

import domain.organization.Organization;
import application.App;
import domain.organization.OrganizationService;
import domain.organization.OrganizationStorage;
import domain.user.User;
import spark.Request;
import spark.Response;
import spark.Route;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static api.authentication.LoginController.ensureUserHasPermission;
import static spark.Spark.*;
import static spark.Spark.put;

public class OrganizationController {
    private OrganizationService service;

    public OrganizationController(OrganizationStorage storage) {
        this.service = new OrganizationRESTService(storage);
        setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/organizations", () -> {
                get("", handleGetAll);
                get("/:name", handleGetSingle);
                post("/add", handlePost);
                put("/update/:name", handlePut);
            });
        });
    }

    private Route handleGetAll = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.SUPER_ADMIN);

        response.status(200);
        return App.g.toJson(OrganizationMapper.toOrganizationDTOList(service.getAll()));
	};

    private Route handleGetSingle = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        ensureUserCanAccessOrganization(request, name);

        response.status(200);
        return App.g.toJson(OrganizationMapper.toOrganizationDTO(service.getSingle(name)));
    };

    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.SUPER_ADMIN);

        Organization organization = App.g.fromJson(request.body(), Organization.class);
        service.post(organization);
        response.status(201);
        return "Created";
    };

    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        ensureUserCanAccessOrganization(request, name);

        Organization organization = App.g.fromJson(request.body(), Organization.class);
        service.put(name, organization);
        response.status(200);
        return "OK";
    };

    private static void ensureUserCanAccessOrganization(Request request, String name) {
        User loggedIn = request.attribute("loggedIn");
        if (userOrganizationNameNotEqualTo(name, loggedIn))
            halt(403, "Forbidden");
    }

    private static boolean userOrganizationNameNotEqualTo(String name, User loggedIn) {
        return !loggedIn
                .getOrganization()
                .getName()
                .equalsIgnoreCase(name);
    }
}