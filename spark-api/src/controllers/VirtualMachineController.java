package controllers;

import main.App;
import models.Organization;
import models.User;
import models.VirtualMachine;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VirtualMachineController {
    public static Route handleGetAll = (Request request, Response response) -> {
        User user = request.attribute("loggedIn");
        List<VirtualMachine> vms = applyRoleFilter(App.vmService.findAll(), user);

        response.type("application/json");
        return App.g.toJson(vms);
    };

    public static Route handleGetSingle = (Request request, Response response) -> {
        String name = request.params(":name");
        Optional<VirtualMachine> vm = App.vmService.findByKey(name);

        response.type("application/json");
        if (!vm.isPresent()) {
            response.status(404);
            return "Virtual machine with the name " + name + " doesn't exist";
        }

        User user = request.attribute("loggedIn");
        if (!user.getOrganization().getVirtualMachines().contains(vm.get())) {
            response.status(403);
            return "Forbidden";
        }

        response.status(200);
        return App.g.toJson(vm.get());
    };

    // TODO : SUPER_ADMIN adds a VM, how to send organization??
    public static Route handlePost = (Request request, Response response) -> {
        VirtualMachine vm = App.g.fromJson(request.body(), VirtualMachine.class);

        response.type("application/json");
        if (!App.vmService.add(vm)) {
            response.status(400);
            return "Virtual machine with the name " + vm.getName() + " already exists";
        }

        User user = request.attribute("loggedIn");
        user.getOrganization().addVirtualMachine(vm);
        response.status(200);
        return "OK";
    };

    public static Route handlePut = (Request request, Response response) -> {
        VirtualMachine vm = App.g.fromJson(request.body(), VirtualMachine.class);
        String name = request.params(":name");
        Optional<VirtualMachine> toUpdate = App.vmService.findByKey(name);

        response.type("application/json");
        if (!toUpdate.isPresent()) {
            response.status(404);
            return "Virtual machine with the name " + name + " doesn't exist";
        }

        User user = request.attribute("loggedIn");
        if (!user.getOrganization().getVirtualMachines().contains(toUpdate.get())) {
            response.status(403);
            return "Forbidden";
        }

        App.vmService.update(name, vm);
        response.status(200);
        return "OK";
    };

    private static List<VirtualMachine> applyRoleFilter(List<VirtualMachine> vms, User user) {
        if (user.getRole().equals(User.Role.SUPER_ADMIN))
            return vms;

        Organization org = user.getOrganization();
        return vms
            .stream()
            .filter(x -> org.getVirtualMachines().contains(x))
            .collect(Collectors.toList());
    }
}