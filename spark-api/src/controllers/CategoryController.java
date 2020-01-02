package controllers;

import main.App;
import models.Drive;
import models.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Locale;
import java.util.Optional;

public class CategoryController {
    public static Route handleGetAll = (Request request, Response response) -> {
        response.type("application/json");
        User currentUser = request.attribute("loggedIn");

        switch(currentUser.getRole()) {
            case SUPER_ADMIN:
                return App.g.toJson(App.driveService.findAll());
            default:
                response.status(400);
                return "Something went wrong!";
        }
    };

    public static Route handleGetSingle = (Request request, Response response) -> {
        String name = request.params(":name");
        Optional<Category> category = App.categoryService.findByKey(name);

        response.type("application/json");

        if(!category.isPresent()){
            response.status(400);
            return "Category with name " + name + " does not exist!";
        }

        response.status(200);
        return App.g.toJson(category.get());
    };

    public static Route handlePost = (Request request, Response response) -> {
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
