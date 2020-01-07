package api.virtual_machine;

import application.App;
import domain.organization.Organization;
import domain.user.User;
import domain.virtual_machine.VirtualMachine;
import domain.virtual_machine.VirtualMachineService;
import domain.virtual_machine.VirtualMachineStorage;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static api.authentication.LoginController.ensureUserHasPermission;
import static spark.Spark.*;
import static spark.Spark.put;

public class VirtualMachineController {
    private VirtualMachineService service;

    public VirtualMachineController(VirtualMachineStorage storage) {
        this.service = new VirtualMachineRESTService(storage);
        setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/virtualmachines", () -> {
                get("", handleGetAll);
                get("/:name", handleGetSingle);
                post("/add", handlePost);
                put("/update/:name", handlePut);
                delete("delete/:name", handleDelete);
            });
        });
    }

    private Route handleGetAll = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.USER);

        List<VirtualMachine> virtualMachines = applyRoleFilter(request, service.getAll());
        return App.g.toJson(VirtualMachineMapper.toVirtualMachineDTOList(virtualMachines));
    };

    private Route handleGetSingle = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.USER);

        String name = request.params(":name");
        VirtualMachine virtualMachine = service.getSingle(name);
        ensureUserCanAccessVirtualMachine(request, virtualMachine);

        response.status(200);
        return App.g.toJson(VirtualMachineMapper.toVirtualMachineDTO(virtualMachine));
    };

    // TODO : SUPER_ADMIN adds a VM, how to send domain.organization?
    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);
        VirtualMachine virtualMachine = App.g.fromJson(request.body(), VirtualMachine.class);
        // dodaj za admina da mu se poveze vm sa organizacijom

        service.post(virtualMachine);
        response.status(201);
        return "Created";
    };
    // TODO : check
    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        VirtualMachine virtualMachine = service.getSingle(name);
        ensureUserCanAccessVirtualMachine(request, virtualMachine);

        VirtualMachine toUpdate = App.g.fromJson(request.body(), VirtualMachine.class);
        service.put(name, toUpdate);
        response.status(200);
        return "OK";
    };

    private Route handleDelete = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        VirtualMachine virtualMachine = service.getSingle(name);
        ensureUserCanAccessVirtualMachine(request, virtualMachine);

        service.delete(name);
        response.status(204);
        return "No content";
    };

    private List<VirtualMachine> applyRoleFilter(Request request, List<VirtualMachine> virtualMachines) {
        User user = request.attribute("loggedIn");
        if (user.getRole().equals(User.Role.SUPER_ADMIN))
            return virtualMachines;

        Organization org = user.getOrganization();
        return virtualMachines
            .stream()
            .filter(x -> org.getVirtualMachines().contains(x))
            .collect(Collectors.toList());
    }

    private static void ensureUserCanAccessVirtualMachine(Request request, VirtualMachine virtualMachine) {
        User loggedIn = request.attribute("loggedIn");
        if (!userOrganizationContainsVirtualMachine(virtualMachine, loggedIn))
            halt(403, "Forbidden");
    }

    private static boolean userOrganizationContainsVirtualMachine(VirtualMachine virtualMachine, User loggedIn) {
        return loggedIn
                .getOrganization()
                .getVirtualMachines()
                .contains(virtualMachine);
    }
}