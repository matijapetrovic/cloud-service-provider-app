package controllers;

import main.App;
import models.VirtualMachine;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

public class VirtualMachineController {
    public static Route handleGetAll = (Request request, Response response) -> {
        response.type("application/json");
        return App.g.toJson(App.vmService.findAll());
    };

    public static Route handleGetSingle = (Request request, Response response) -> {
        String name = request.params(":name");
        Optional<VirtualMachine> vm = App.vmService.findByKey(name);

        response.type("application/json");
        if (!vm.isPresent()) {
            response.status(400);
            return "Virtual machine with the name " + name + " doesn't exist";
        }

        response.status(200);
        return App.g.toJson(vm.get());
    };

    public static Route handlePost = (Request request, Response response) -> {
        VirtualMachine vm = App.g.fromJson(request.body(), VirtualMachine.class);

        response.type("application/json");
        if (!App.vmService.add(vm)) {
            response.status(400);
            return "Virtual machine with the name " + vm.getName() + " already exists";
        }

        response.status(200);
        return "OK";
    };

    public static Route handlePut = (Request request, Response response) -> {
        VirtualMachine vm = App.g.fromJson(request.body(), VirtualMachine.class);
        String name = request.params(":name");

        response.type("application/json");
        if (!App.vmService.update(name, vm)) {
            response.status(400);
            return "Virtual machine with the name " + name + " doesn't exist";
        }

        response.status(200);
        return "OK";
    };
}
