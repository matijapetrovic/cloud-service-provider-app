package api.drive;

import application.App;
import domain.drive.Drive;
import domain.drive.DriveService;
import domain.drive.DriveStorage;
import domain.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

import static api.authentication.LoginController.ensureUserHasPermission;
import static spark.Spark.*;

public class DriveController {
    private DriveService service ;

    public DriveController(DriveStorage storage) {
        this.service = new DriveRESTService(storage);
        setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path ("/drives", () -> {
                get("", handleGetAll);
                get("/:name", handleGetSingle);
                get("/organizations/:name", handleDrivesOrganization);
                post("/add", handlePost);
                put("/update/:name", handlePut);
                delete("/delete/:name", handleDelete);
            });
        });
    }

    private  Route handleGetAll = (Request request, Response response) -> {
        User currentUser = request.attribute("loggedIn");

        switch(currentUser.getRole()) {
            case SUPER_ADMIN:
                return App.g.toJson(DriveMapper.toDriveDTOList(service.getAll()));
            case ADMIN:
            case USER:
                return App.g.toJson(DriveMapper.toDriveDTOList(service.getAllDrivesFromSameOrganization(currentUser)));
            default:
                response.status(500);
                return "Something went wrong!";
        }
    };

    private Route handleGetSingle = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");

        response.status(200);
        return App.g.toJson(DriveMapper.toDriveDTO(service.getSingle(name)));
    };

    private Route handleDrivesOrganization = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);
        User user = App.g.fromJson(request.body(), User.class);

        response.type("application/json");
        response.status(200);
        return App.g.toJson(DriveMapper.toDriveDTOList(service.getAllDrivesFromSameOrganization(user)));
    };

    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.SUPER_ADMIN);

        Drive drive = App.g.fromJson(request.body(), Drive.class);
        service.post(drive);

        response.status(200);
        return "OK";
    };

    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        Drive drive = App.g.fromJson(request.body(), Drive.class);
        String name = request.params(":name");

        service.put(name, drive);
        response.status(200);
        return "OK";
    };

    private Route handleDelete = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        Drive drive = App.g.fromJson(request.body(), Drive.class);
        String name = request.params(":name");

        service.delete(name);
        response.status(200);
        return "OK";
    };
}
