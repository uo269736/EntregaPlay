package controllers;

import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class RecetaController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result create(Http.Request req) {
        return ok(views.html.index.render());
    }

    public Result get(Integer id, Http.Request req) {
        return ok(views.html.index.render());
    }

    public Result update(Integer id, Http.Request req) {
        return ok(views.html.index.render());
    }

    public Result delete(Integer id, Http.Request req) {
        return ok(views.html.index.render());
    }

    public Result getAll(Http.Request req) {
        return ok(views.html.index.render());
    }


}
