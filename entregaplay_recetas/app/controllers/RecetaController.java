package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Receta;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import views.RecetaResource;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


public class RecetaController extends Controller {


    @Inject
    private FormFactory formFactory;

    public Result create(Http.Request req) {
        // Los datos de la receta para crearlo vienen en el body


        Form<RecetaResource> recetaForm = formFactory.form(RecetaResource.class).bindFromRequest(req);
        RecetaResource recetaResource;

        if (recetaForm.hasErrors()){
            return Results.badRequest(recetaForm.errorsAsJson());
        } else {
            recetaResource = recetaForm.get();
        }

        Receta recetaModel = recetaResource.toModel();
        recetaModel.save();

        // Añadimos el id que va a tener cada receta en nuestro array (Hacer comprobacion de repetidos)
        ObjectNode jsonRes = Json.newObject();
        jsonRes.put("id", recetaModel.getId());
        return Results.created("Receta creada con ID: " + jsonRes);



    }


    public Result get(Integer id, Http.Request req) {

        Receta rec = Receta.findById(Long.valueOf(id));

        if (rec == null){
            // Nos piden uno que no existe
            return Results.notFound();
        }
        if (id <= 0){
            return Results.notFound();
        }

        RecetaResource recetaResource = new RecetaResource(rec);

        return Results.ok(recetaResource.toJson());

    }




    public Result update(Integer id, Http.Request req) {
        return null;
    }

    public Result delete(Integer id, Http.Request req) {
        return null;
    }

    public Result getAll(Http.Request req) {

        List<Receta> recetas = Receta.findAll();
        List<RecetaResource> resources = recetas.stream().map(RecetaResource::new).collect(Collectors.toList());
        JsonNode json = Json.toJson(resources);
        Result res = Results.ok(json);
        return res;

    }




}
