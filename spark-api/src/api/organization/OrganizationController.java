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
        return createOrganizationResponse(service.getSingle(name));
    };

    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.SUPER_ADMIN);

        Organization toAdd = parseOrganization(request);
        response.status(201);
        return createOrganizationResponse(service.post(toAdd));
    };

    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        ensureUserCanAccessOrganization(request, name);

        Organization toUpdate = parseOrganization(request);
        response.status(200);
        return createOrganizationResponse(service.put(name, toUpdate));
    };

    private Organization parseOrganization(Request request) {
        try {
            OrganizationDTO dto = App.g.fromJson(request.body(), OrganizationDTO.class);
            Organization parsed = OrganizationMapper.fromOrganizationDTO(dto);
            if (parsed == null)
                halt(400, "Required fields missing");
            return parsed;
        } catch(Exception e) {
            halt(400, "Bad request");
        }
        // Unreachable
        return null;
    }

    private String createOrganizationResponse(Organization organization) {
        return App.g.toJson(OrganizationMapper.toOrganizationDTO(organization));
    }

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