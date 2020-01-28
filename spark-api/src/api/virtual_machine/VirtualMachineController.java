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
                post("", handlePost);
                put("/:name", handlePut);
                delete("/:name", handleDelete);
            });
        });
    }

    private Route handleGetAll = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.USER);

        List<VirtualMachine> virtualMachines = applyQuery(request, applyRoleFilter(request, service.getAll()));
        return App.g.toJson(VirtualMachineMapper.toVirtualMachineDTOList(virtualMachines));
    };

    private Route handleGetSingle = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.USER);

        String name = request.params(":name");
        VirtualMachine virtualMachine = service.getSingle(name);
        ensureUserCanAccessVirtualMachine(request, virtualMachine);

        // temporary
        VirtualMachineDTO dto = VirtualMachineMapper.toVirtualMachineDTO(virtualMachine);
        response.status(200);
        return App.g.toJson(dto);
    };

    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);
        VirtualMachineDTO toAdd = App.g.fromJson(request.body(), VirtualMachineDTO.class);
        VirtualMachine added = service.post(VirtualMachineMapper.fromVirtualMachineDTO(toAdd));
        response.status(201);
        return App.g.toJson(VirtualMachineMapper.toVirtualMachineDTO(added));
    };
    // TODO : check
    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        VirtualMachine virtualMachine = service.getSingle(name);
        ensureUserCanAccessVirtualMachine(request, virtualMachine);

        VirtualMachineDTO dto = App.g.fromJson(request.body(), VirtualMachineDTO.class);
        VirtualMachine updated = service.put(name, VirtualMachineMapper.fromVirtualMachineDTO(dto));
        response.status(200);
        return App.g.toJson(VirtualMachineMapper.toVirtualMachineDTO(updated));
    };

    private Route handleDelete = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        VirtualMachine virtualMachine = service.getSingle(name);
        ensureUserCanAccessVirtualMachine(request, virtualMachine);

        service.delete(name);
        response.status(204);
        return "";
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

    private List<VirtualMachine> applyQuery(Request request, List<VirtualMachine> virtualMachines) {
        List<VirtualMachine> result = virtualMachines;
        if (request.queryParams("name") != null) {
            result = virtualMachines
                        .stream()
                        .filter(vm -> vm.getName().contains(request.queryParams("name")))
                        .collect(Collectors.toList());
        }
        return result;
    }

    private static void ensureUserCanAccessVirtualMachine(Request request, VirtualMachine virtualMachine) {
        User loggedIn = request.attribute("loggedIn");
        if (loggedIn.getRole().equals(User.Role.SUPER_ADMIN))
            return;
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