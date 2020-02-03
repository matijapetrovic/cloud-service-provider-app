package api.vm_category;

import application.App;
import domain.user.User;
import domain.vm_category.CategoryService;
import domain.vm_category.CategoryStorage;
import domain.vm_category.VMCategory;
import spark.Request;
import spark.Response;
import spark.Route;

import static api.authentication.LoginController.ensureUserHasPermission;
import static spark.Spark.*;

public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryStorage storage) {
        this.service = new CategoryRESTService(storage);
        setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            path("/categories", () -> {
                get("", handleGetAll);
                get("/:name", handleGetSingle);
                post("/add", handlePost);
                put("/update/:name", handlePut);
                delete("/delete/:name", handleDelete);
            });
        });
    }

    private Route handleGetAll = (Request request, Response response) -> {
        response.type("application/json");
        return App.g.toJson(CategoryMapper.toCategoryDTOList(service.getAll()));
    };

    private Route handleGetSingle = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");

        response.status(200);
        return App.g.toJson(CategoryMapper.toCategoryDTO(service.getSingle(name)));
    };

    private Route handlePost = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.SUPER_ADMIN);

        VMCategory category = parseCategory(request);
        service.post(category);

        response.status(200);
        return "OK";
    };

    private Route handlePut = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");
        VMCategory category = parseCategory(request);

        service.put(name, category);
        response.status(200);
        return "OK";
    };

    private Route handleDelete = (Request request, Response response) -> {
        ensureUserHasPermission(request, User.Role.ADMIN);

        String name = request.params(":name");

        service.delete(name);
        response.status(204);
        return "";
    };

    private VMCategory parseCategory(Request request) {
        try {
            CategoryDTO dto = App.g.fromJson(request.body(), CategoryDTO.class);
            VMCategory parsed = CategoryMapper.fromCategoryDTO(dto);
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
