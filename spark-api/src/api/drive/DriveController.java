package api.drive;

import api.user.UserController;
import application.App;
import domain.drive.Drive;
import domain.drive.DriveStorage;
import domain.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Collection;
import java.util.Optional;

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
            path ("/users", () -> {
                get("", UserController.serveUserPage);
                get("/currentUser", UserController.serveCurrentUser);
                get("/:name", UserController.handleGetSingle);
                get("/organizations/:name", UserController.handleUsersOrganization);
                post("/add", UserController.handlePost);
                put("/update/:name", UserController.handlePut);
                delete("/delete/:name",UserController.handleDelete);
            });
        });
    }

    public static Route handleGetAll = (Request request, Response response) -> {
        response.type("application/json");
        User currentUser = request.attribute("loggedIn");

        switch(currentUser.getRole()) {
            case SUPER_ADMIN:
                return App.g.toJson(App.driveService.findAll());
            case ADMIN:
            case USER:
                return App.g.toJson(App.driveService.getAllDrivesFromSameOrganization(currentUser));
            default:
                response.status(400);
                return "Something went wrong!";
        }
    };

    public static Route handleGetSingle = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);
        String name = request.params(":name");
        Optional<Drive> drive = App.driveService.findByKey(name);

        response.type("application/json");

        if(!drive.isPresent()){
            response.status(400);
            return "Drive with name " + name + " does not exist!";
        }

        response.status(200);
        return App.g.toJson(drive.get());
    };

    public static Route handleDrivesOrganization = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);
        String name = request.params(":name");
        Optional<User> user = App.userService.findByKey(name);

        response.type("application/json");

        if(!user.isPresent()){
            response.status(400);
            return "User with email " + name + " does not exist!";
        }

        Collection<Drive> drives = user.get().getOrganization().getDrives();
            
        response.status(200);
        return App.g.toJson(drives);
    };
    public static Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);
        Drive drive = App.g.fromJson(request.body(), Drive.class);

        response.type("application/json");

        if (!App.driveService.add(drive)) {
            response.status(400);
            return "Drive with the name " + drive.getName() + " already exists";
        }

        response.status(200);
        return "OK";
    };

    public static Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);
        Drive drive = App.g.fromJson(request.body(), Drive.class);
        String name = request.params(":name");
        Optional<Drive> toFind = App.driveService.findByKey(name);

        response.type("application/json");

        if(!toFind.isPresent()){
            response.status(400);
            return "Drive with name " + name + " does not exist !";
        }

        App.driveService.update(name, drive);

        response.status(200);
        return "OK";
    };

    public static Route handleDelete = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);
        Drive drive = App.g.fromJson(request.body(), Drive.class);
        String name = request.params(":name");
        Optional<Drive> toFind = App.driveService.findByKey(name);

        response.type("application/json");

        if(!toFind.isPresent()){
            response.status(400);
            return "Drive with name " + name + " does not exist !";
        }

        App.driveService.delete(name);

        response.status(200);
        return "OK";
    };
}
