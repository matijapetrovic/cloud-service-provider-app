package api.montly_report;

import application.App;
import domain.user.User;
import domain.virtual_machine.DateRange;
import spark.Request;
import spark.Response;
import spark.Route;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static spark.Spark.*;

public class ReportController {
    public ReportController() {
        setUpRoutes();
    }

    private void setUpRoutes() {
        path("api", () -> {
            get("/report", handleGet);
        });
    }

    private Route handleGet = (Request request, Response response) -> {
        ensureUserIsAdmin(request);

        DateRange dateRange = parseRequest(request);

        User loggedIn = request.attribute("loggedIn");
        Report report = new Report(loggedIn.getOrganization(), dateRange);
        return App.g.toJson(new ReportResponse(report));
    };

    private DateRange parseRequest(Request request) {
        if (request.queryParams("start") == null)
            halt(400, "Bad request");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            DateRange dateRange = new DateRange(sdf.parse(request.queryParams("start")));

            if (request.queryParams("end") != null)
                dateRange.setEndDate(sdf.parse(request.queryParams("end")));

            return dateRange;
        } catch (ParseException e) {
            halt(400, "Bad request");
        }

        return null;
    }

    private void ensureUserIsAdmin(Request request) {
        User loggedIn = request.attribute("loggedIn");
        if (loggedIn.getRole() != User.Role.ADMIN)
            halt(403, "Forbidden");
    }


}
