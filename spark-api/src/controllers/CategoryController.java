package controllers;

import main.App;
import models.VMCategory;
import models.User;
import spark.Request;
import spark.Response;
import spark.Route;
import sun.misc.VM;

import java.util.Optional;

public class CategoryController {
    public static Route handleGetAll = (Request request, Response response) -> {
        response.type("application/json");
        User currentUser = request.attribute("loggedIn");

        switch(currentUser.getRole()) {
            case SUPER_ADMIN:
                return App.g.toJson(App.categoryService.findAll());
            default:
                response.status(400);
                return "Something went wrong!";
        }
    };

    public static Route handleGetSingle = (Request request, Response response) -> {
        String name = request.params(":name");

        Optional<VMCategory> vmCategory = App.categoryService.findByKey(name);

        response.type("application/json");

        if(!vmCategory.isPresent()){
            response.status(400);
            return "Category with name " + name + " does not exist!";
        }

        response.status(200);
        return App.g.toJson(vmCategory.get());
    };

    public static Route handlePost = (Request request, Response response) -> {
        VMCategory vmCategory = App.g.fromJson(request.body(), VMCategory.class);

        response.type("application/json");

        if (!App.categoryService.add(vmCategory)) {
            response.status(400);
            return "Category with the name " + vmCategory.getName() + " already exists";
        }

        response.status(200);
        return "OK";
    };

    public static Route handlePut = (Request request, Response response) -> {
        VMCategory category = App.g.fromJson(request.body(), VMCategory.class);
        String name = request.params(":name");
        Optional<VMCategory> toFind = App.categoryService.findByKey(name);

        response.type("application/json");

        if(!toFind.isPresent()){
            response.status(400);
            return "Drive with name " + name + " does not exist !";
        }

        App.categoryService.update(name, category);

        response.status(200);
        return "OK";
    };

    public static Route handleDelete = (Request request, Response response) -> {
        VMCategory category = App.g.fromJson(request.body(), VMCategory.class);
        String name = request.params(":name");
        Optional<VMCategory> toFind = App.categoryService.findByKey(name);

        response.type("application/json");

        if(!toFind.isPresent()){
            response.status(400);
            return "Category with name " + name + " does not exist !";
        }

        App.categoryService.delete(name);

        response.status(200);
        return "OK";
    };
}
