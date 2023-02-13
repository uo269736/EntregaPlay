package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Ingrediente;
import models.Receta;
import play.cache.Cached;
import play.cache.SyncCacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.*;
import play.twirl.api.Content;
import views.RecetaResource;
import views.xml.receta;
import models.Receta;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class RecetaController extends Controller {


    @Inject
    private FormFactory formFactory;

    @Inject
    private MessagesApi messagesApi;


    // Cache:
    @Inject
    private SyncCacheApi cache;

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
            //ESTO HAY QUE MIRARLO PORQUE NO SE USA Y HAY QUE MIRAR LO DE LOS MENSAJES Y VER SI HAY QUE MANDAR UNA  RECETA O UN RECETARESOURCE
            //return Results.ok(messages.at("receta-modificada") + jsonRes);
            Result res;
            if (req.accepts("application/json")){
                res = Results.created(recetaResource.toJson());
            } else if (req.accepts("application/xml")) {
                Content content = views.xml.receta.render(recetaModel);
                res = Results.created(content);
                //res = Results.created(receta.render(recetaResource.getNombre(), recetaResource.getDescripcion()));
            } else {
                res = Results.unsupportedMediaType();
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


        //return Results.ok(messages.at("receta-modificada") + jsonRes);

        RecetaResource recetaResource = new RecetaResource(rec);

        Result rest = null;
        if (req.accepts("application/json")){
            rest = Results.ok(recetaResource.toJson());
        } else if (req.accepts("application/xml")) {
            Content content = views.xml.receta.render(rec);
            return Results.ok(content);
            //rest = Results.ok(receta.render(recetaResource.getNombre(), recetaResource.getDescripcion()));
        } else {
            rest = Results.unsupportedMediaType();
        }
        return rest;
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

            // Añadimos el id que va a tener cada receta en nuestro array (Hacer comprobacion de repetidos)
            ObjectNode jsonRes = Json.newObject();
            jsonRes.put("id", recetaModel.getId());
            //return Results.ok(messages.at("receta-modificada") + jsonRes);

            Result res;
            if (req.accepts("application/json")){
                res = Results.ok(recetaModel.toJson());
            } else if (req.accepts("application/xml")) {
                Content content = views.xml.receta.render(recetaModel);
                res = Results.ok(content);
                //res = Results.created(receta.render(recetaResource.getNombre(), recetaResource.getDescripcion()));
            } else {
                res = Results.unsupportedMediaType();
            }
            return res;
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
            //return Results.ok(messages.at("receta-eliminada") + id);

            Result res;
            if (req.accepts("application/json")){
                res = Results.ok(rec.toJson());
            } else if (req.accepts("application/xml")) {
                Content content = views.xml.receta.render(rec);
                res = Results.ok(content);
                //res = Results.created(receta.render(recetaResource.getNombre(), recetaResource.getDescripcion()));
            } else {
                res = Results.unsupportedMediaType();
            }
            return res;
        }



    }

    // @Cached(key="all-recetas-view") --> Cache de 1 Nivel (lo guarda una vez y ya, lo malo es que no cambia al poner json o xml, lo deja como este la primera vez
    public Result getAll(Http.Request req) {
        Messages messages = messagesApi.preferred(req);

        // Cache de la lista de la bbdd de las recetas (Acceso a la bbdd) 2 Nivel
            // List<Receta> recetas = Receta.findAll();
        List<Receta> recetas;
        Optional<Object> optRecetas = cache.get("all-recetas");
        if (optRecetas.isPresent()) {
            System.out.println("Valor cacheado");
            recetas = (List<Receta>) optRecetas.get();
        } else {
            System.out.println("Valor NO cacheado");
            recetas = Receta.findAll();
            cache.set("all-recetas", recetas);
        }


        List<RecetaResource> resources = recetas.stream().map(RecetaResource::new).collect(Collectors.toList()); // Convertir objeto receta a objeto recetaResource


        Result res;
        if (req.accepts("application/json")){
            // Esto se puede cachear (si hay muchas recetas tardaria mucho la respuesta. Por eso se puede tener ya cacheado (Usamos EHCache en play)
            JsonNode json = Json.toJson(resources);
            res = Results.ok(json);

        } else if (req.accepts("application/xml")) {
            Content content = views.xml.recetas.render(recetas);
            res = Results.ok(content);
            //res = Results.created(receta.render(recetaResource.getNombre(), recetaResource.getDescripcion()));
        } else {
            res = Results.unsupportedMediaType();
        }
        return res;

    }





}
