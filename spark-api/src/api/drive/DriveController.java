package api.drive;

import api.vm_category.CategoryDTO;
import api.vm_category.CategoryMapper;
import application.App;
import domain.drive.Drive;
import domain.drive.DriveService;
import domain.drive.DriveStorage;
import domain.organization.Organization;
import domain.user.User;
import domain.vm_category.VMCategory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.DriverManager;
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
        if (request.queryParams("organization") != null) {
            result = result
                    .stream()
                    .filter(drive -> drive
                            .getOrganization()
                            .getName()
                            .equalsIgnoreCase(request.queryParams("organization"))
                            && drive.getVm() == null)
                    .collect(Collectors.toList());
        }
        return result;
    }

    private List<Drive> applyFilterQuery(Request request, List<Drive> drives) {
        List<Drive> result = drives;
        if (request.queryParams("name") != null && !request.queryParams("name").equals("null")) {
            result = result
                    .stream()
                    .filter(vm -> vm.getName().toLowerCase().contains(request.queryParams("name").toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (request.queryParams("type") != null && !request.queryParams("type").equals("null")) {
            result = result
                    .stream()
                    .filter(vm -> vm.getTypeToString().toLowerCase().equals(request.queryParams("type").toLowerCase()))
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
        String name = request.params(":name");
        Drive drive = service.getSingle(name);
        ensureUserCanAccessDrive(request, drive);

        response.status(200);
        return App.g.toJson(DriveMapper.toDriveDTO(service.getSingle(name)));
    };

    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        Drive drive = parseDrive(request);
        service.post(drive);
        response.status(200);
        return "OK";
    };

    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        Drive drive = parseDrive(request);

        service.put(name, drive);
        response.status(200);
        return "OK";
    };

    private Route handleDelete = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

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

    private static void ensureUserCanAccessDrive(Request request, Drive drive) {
        User loggedIn = request.attribute("loggedIn");
        if (loggedIn.getRole().equals(User.Role.SUPER_ADMIN))
            return;
        if (!drive.getOrganization().equals(loggedIn.getOrganization()))
            halt(403, "Forbidden");
    }

    private Drive parseDrive(Request request) {
        try {
            DriveDTO dto = App.g.fromJson(request.body(), DriveDTO.class);
            Drive parsed = DriveMapper.fromDriveDTO(dto);
            if (parsed == null)
                halt(400, "Required fields missing");
            return parsed;
        } catch(Exception e) {
            halt(400, "Bad request");
        }
        // Unreachable
        return null;
    }
}