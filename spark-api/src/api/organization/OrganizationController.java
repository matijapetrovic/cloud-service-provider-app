package api.organization;

import domain.organization.Organization;
import application.App;
import domain.organization.OrganizationService;
import domain.organization.OrganizationStorage;
import domain.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

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
                post("", handlePost);
                put("/:name", handlePut);
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

        OrganizationDTO dto = App.g.fromJson(request.body(), OrganizationDTO.class);
        Organization added = service.post(OrganizationMapper.fromOrganizationDTO(dto));
        response.status(201);
        return App.g.toJson(OrganizationMapper.toOrganizationDTO(added));
    };

    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        ensureUserCanAccessOrganization(request, name);

        OrganizationDTO dto = App.g.fromJson(request.body(), OrganizationDTO.class);
        Organization updated = service.put(name, OrganizationMapper.fromOrganizationDTO(dto));
        response.status(200);
        return App.g.toJson(OrganizationMapper.toOrganizationDTO(updated));
    };

    private static void ensureUserCanAccessOrganization(Request request, String name) {
        User loggedIn = request.attribute("loggedIn");
        if (loggedIn.getRole().equals(User.Role.SUPER_ADMIN))
            return;

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