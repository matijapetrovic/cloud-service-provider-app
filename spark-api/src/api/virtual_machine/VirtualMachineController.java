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

import java.util.ArrayList;
import java.util.List;

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
                post("/toggle/:name", handleToggle);
                put("/:name", handlePut);
                delete("/:name", handleDelete);
            });
        });
    }

    private Route handleGetAll = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.USER);

        List<VirtualMachine> virtualMachines = new ArrayList<>(service.getAll());
        applyRoleFilter(request, virtualMachines);
        applyQuery(request, virtualMachines);
        return App.g.toJson(VirtualMachineMapper.toVirtualMachineDTOList(virtualMachines));
    };

    private Route handleGetSingle = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.USER);

        String name = request.params(":name");
        VirtualMachine virtualMachine = service.getSingle(name);
        ensureUserCanAccessVirtualMachine(request, virtualMachine);

        response.status(200);
        return createVirtualMachineResponse(virtualMachine);
    };

    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        VirtualMachine toAdd = parseVirtualMachine(request);
        ensureUserCanAccessVirtualMachine(request, toAdd);

        response.status(201);
        return createVirtualMachineResponse(service.post(toAdd));
    };

    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        VirtualMachine virtualMachine = service.getSingle(name);
        ensureUserCanAccessVirtualMachine(request, virtualMachine);

        VirtualMachine toUpdate = parseVirtualMachine(request);
        response.status(200);
        return createVirtualMachineResponse(service.put(name, toUpdate));
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

    private Route handleToggle = (Request request, Response response) -> {
        String name = request.params(":name");
        VirtualMachine virtualMachine = service.getSingle(name);
        ensureUserCanAccessVirtualMachine(request, virtualMachine);

        response.status(200);
        return createVirtualMachineResponse(service.toggle(name));
    };

    private VirtualMachine parseVirtualMachine(Request request) {
        try {
            VirtualMachineDTO dto = App.g.fromJson(request.body(), VirtualMachineDTO.class);
            VirtualMachine parsed = VirtualMachineMapper.fromVirtualMachineDTO(dto);
            if (parsed == null)
                halt(400, "Required fields missing");
            return parsed;
        } catch(Exception e) {
            halt(400, "Bad request");
        }
        // Unreachable
        return null;
    }

    private String createVirtualMachineResponse(VirtualMachine virtualMachine) {
        return App.g.toJson(VirtualMachineMapper.toVirtualMachineDTO(virtualMachine));
    }

    private void applyRoleFilter(Request request, List<VirtualMachine> virtualMachines) {
        User user = request.attribute("loggedIn");
        if (user.getRole().equals(User.Role.SUPER_ADMIN))
            return;

        Organization org = user.getOrganization();
        virtualMachines.removeIf(vm -> !vm.getOrganization().equals(org));
    }

    private void applyQuery(Request request, List<VirtualMachine> virtualMachines) {
        if (request.queryParams("organization") != null)
            VirtualMachineFilter.filterByOrganization(request.queryParams("organization"), virtualMachines);
        if (request.queryParams("name") != null)
            VirtualMachineFilter.filterByName(request.queryParams("name"), virtualMachines);
        if (request.queryParams("cpuFrom") != null)
            VirtualMachineFilter.filterByCpuFrom(Integer.parseInt(request.queryParams("cpuFrom")), virtualMachines);
        if (request.queryParams("cpuTo") != null)
            VirtualMachineFilter.filterByCpuTo(Integer.parseInt(request.queryParams("cpuTo")), virtualMachines);
        if (request.queryParams("ramFrom") != null)
            VirtualMachineFilter.filterByRamFrom(Integer.parseInt(request.queryParams("ramFrom")), virtualMachines);
        if (request.queryParams("ramTo") != null)
            VirtualMachineFilter.filterByRamTo(Integer.parseInt(request.queryParams("ramTo")), virtualMachines);
        if (request.queryParams("gpuFrom") != null)
            VirtualMachineFilter.filterByGpuFrom(Integer.parseInt(request.queryParams("gpuFrom")), virtualMachines);
        if (request.queryParams("gpuTo") != null)
            VirtualMachineFilter.filterByGpuTo(Integer.parseInt(request.queryParams("gpuTo")), virtualMachines);
    }

    private static void ensureUserCanAccessVirtualMachine(Request request, VirtualMachine virtualMachine) {
        User loggedIn = request.attribute("loggedIn");
        if (loggedIn.getRole().equals(User.Role.SUPER_ADMIN))
            return;
        if (!virtualMachine.getOrganization().equals(loggedIn.getOrganization()))
            halt(403, "Forbidden");
    }
}