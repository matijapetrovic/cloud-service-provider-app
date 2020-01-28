package api.drive;

import application.App;
import domain.drive.Drive;
import domain.drive.DriveService;
import domain.drive.DriveStorage;
import domain.organization.Organization;
import domain.user.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.stream.Collectors;

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
                get("/filter", handleFilter);
                get("/:name", handleGetSingle);
                get("/organizations/:name", handleDrivesOrganization);
                post("/add", handlePost);
                put("/update/:name", handlePut);
                delete("/delete/:name", handleDelete);
            });
        });
    }

    private  Route handleGetAll = (Request request, Response response) -> {
        List<Drive> drives = applyQuery(request, applyRoleFilter(request, service.getAll()));
        return App.g.toJson(DriveMapper.toDriveDTOList(drives));
    };

    private  Route handleFilter = (Request request, Response response) -> {
        List<Drive> drives = applyFilterQuery(request, applyRoleFilter(request, service.getAll()));
        return App.g.toJson(DriveMapper.toDriveDTOList(drives));
    };

    private List<Drive> applyQuery(Request request, List<Drive> drives) {
        List<Drive> result = drives;
        if (request.queryParams("name") != null) {
            result = drives
                    .stream()
                    .filter(vm -> vm.getName().contains(request.queryParams("name")))
                    .collect(Collectors.toList());
        }
        return result;
    }

    private List<Drive> applyFilterQuery(Request request, List<Drive> drives) {
        List<Drive> result = drives;
        if (request.queryParams("type") != null && !request.queryParams("type").equals("null")) {
            result = result
                    .stream()
                    .filter(vm -> vm.getTypeToString().equals(request.queryParams("type")))
                    .collect(Collectors.toList());
        }
        if (request.queryParams("from") != null && !request.queryParams("from").equals("null")) {
            String fromS = request.queryParams("from");
            int from = Integer.parseInt(fromS);
            result = result
                    .stream()
                    .filter(vm -> vm.getCapacity() >= from)
                    .collect(Collectors.toList());
        }
        if (request.queryParams("to") != null && !request.queryParams("to").equals("null")) {
            String toS = request.queryParams("to");
            int to = Integer.parseInt(toS);
        result = result
                .stream()
                .filter(vm -> vm.getCapacity() <= to)
                .collect(Collectors.toList());
    }
        return result;
    }

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

    private List<Drive> applyRoleFilter(Request request, List<Drive> drives) {
        User user = request.attribute("loggedIn");
        if (user.getRole().equals(User.Role.SUPER_ADMIN))
            return drives;

        Organization org = user.getOrganization();
        return drives
                .stream()
                .filter(x -> org.getDrives().contains(x))
                .collect(Collectors.toList());
    }
}
