package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Ingrediente;
import models.Receta;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.*;
import views.RecetaResource;
import views.xml.receta;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


public class RecetaController extends Controller {


    @Inject
    private FormFactory formFactory;

    @Inject
    private MessagesApi messagesApi;

    public Result create(Http.Request req) {
        // Los datos de la receta para crearlo vienen en el body
        /* JSON DE PRUEBA
{
    "nombre":"pasta2",
    "ingredientes":[
        {
            "nombre":"pasta",
            "cantidad":100,
            "unidad":"g"
        },
        {
            "nombre":"nata",
            "cantidad":30,
            "unidad":"centilitros"
        }
    ],
    "descripcion":"Receta rica",
    "pasos":"Primer cueces la pasta y luego echas la nata",
    "tiempo":10,
    "imagen":
        {
            "url":"123",
            "descripcionImagen":"Plato de pasta"
        }
}
         */
        Messages messages = messagesApi.preferred(req.acceptLanguages());
        Form<RecetaResource> recetaForm = formFactory.form(RecetaResource.class).bindFromRequest(req);
        RecetaResource recetaResource;

        if (recetaForm.hasErrors()){
            return Results.badRequest(recetaForm.errorsAsJson());
        } else {
            recetaResource = recetaForm.get();

            Receta recetaModel = recetaResource.toModel();
            recetaModel.save();

            // Añadimos el id que va a tener cada receta en nuestro array (Hacer comprobacion de repetidos)
            ObjectNode jsonRes = Json.newObject();
            jsonRes.put("id", recetaModel.getId());

            Result res;
            if(req.accepts("application/json")){
                res = Results.created(messages.at("receta-creada") + jsonRes);
            } else if (req.accepts("application/xml")){
                res = Results.ok(receta.render(recetaModel));
            } else {
                res = Results.unsupportedMediaType("No soporta el tipo");
            }

            return res;


        }


    }


    public Result get(Integer id, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        Receta rec = Receta.findById(Long.valueOf(id));

        if (rec == null){
            // Nos piden uno que no existe
            return Results.notFound();
        }

        RecetaResource recetaResource = new RecetaResource(rec);

        return Results.ok(recetaResource.toJson());

    }


    public Result update(Integer id, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        Receta rec = Receta.findById(Long.valueOf(id));

        Form<RecetaResource> recetaForm = formFactory.form(RecetaResource.class).bindFromRequest(req);
        RecetaResource recetaResource;

        if (recetaForm.hasErrors()){
            return Results.badRequest(recetaForm.errorsAsJson());
        } else {
            recetaResource = recetaForm.get();

            Receta recetaModel = recetaResource.toModel();
            recetaModel.setId(rec.getId());
            recetaModel.getImagen().setId(rec.getImagen().getId());

            for (Integer i = 0; i<rec.getIngredientes().size(); i++ ) {
                recetaModel.getIngredientes().get(i).setId(rec.getIngredientes().get(i).getId());
            }

            recetaModel.update();

            System.out.println("Receta model: " + recetaModel);

            // Añadimos el id que va a tener cada receta en nuestro array (Hacer comprobacion de repetidos)
            ObjectNode jsonRes = Json.newObject();
            jsonRes.put("id", recetaModel.getId());
            return Results.created(messages.at("receta-modificada") + jsonRes);

        }


    }

    public Result delete(Integer id, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        Receta rec = Receta.findById(Long.valueOf(id));

        if (rec == null){
            // Nos piden uno que no existe
            return Results.notFound("La receta no existe");
        } else {

            //RecetaResource recetaResource = new RecetaResource(rec);
            //Receta recetaModel = recetaResource.toModel();
           // recetaModel.delete();

            rec.delete();
            return Results.ok(messages.at("receta-eliminada") + id);
        }



    }

    public Result getAll(Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        List<Receta> recetas = Receta.findAll();

        List<RecetaResource> resources = recetas.stream().map(RecetaResource::new).collect(Collectors.toList());
        JsonNode json = Json.toJson(resources);
        Result res = Results.ok(json);
        return res;

    }





}
