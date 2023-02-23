package controllers;

import actions.Timed;
import actions.TimerAction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Ingrediente;
import models.Propiedad;
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
import views.PropiedadResource;
import views.RecetaResource;
import views.xml.receta;
import models.Receta;

import javax.inject.Inject;
import java.util.ArrayList;
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

    @Timed
    public Result create(Http.Request req) {
        // Los datos de la receta para crearlo vienen en el body
        /* JSON DE PRUEBA
{
    "nombre":"nueva",
    "ingredientes":[
        {
            "nombreIngrediente":"inuevo1",
            "cantidad":100,
            "unidad":"g"
        },
        {
            "nombreIngrediente":"inuevo2",
            "cantidad":200,
            "unidad":"centilitros"
        }
    ],
    "descripcion":"Descripcion nueva",
    "pasos":"Pasos receta nueva",
    "tiempo":100,
    "imagenUrl":"http://xxx.com/imgNueva.png"
}
         */
        Messages messages = messagesApi.preferred(req.acceptLanguages());
        Form<RecetaResource> recetaForm = formFactory.form(RecetaResource.class).bindFromRequest(req);
        RecetaResource recetaResource;

        if (recetaForm.hasErrors()){
            return Results.badRequest(recetaForm.errorsAsJson());
        } else {
            List<Propiedad> propiedadesBD = Propiedad.findAll();
            recetaResource = recetaForm.get();

            Receta recetaModel = recetaResource.toModel();
            List<Propiedad> propiedadesReceta = recetaModel.getPropiedades();
            recetaModel.setPropiedades(new ArrayList<Propiedad>());
            recetaModel.save();

            Boolean existe = false;
            for(Propiedad p: propiedadesReceta){
                for(Propiedad pbd: propiedadesBD) {
                    if (p.getNombrePropiedad().trim().toLowerCase().equals(pbd.getNombrePropiedad().trim().toLowerCase())) {
                        pbd.addReceta(recetaModel);
                        pbd.update();
                        existe = true;
                        break;
                    }else{
                        existe = false;
                    }
                }
                if(existe==false){
                    p.save();
                }
                existe=false;
            }

            recetaModel.setPropiedades(propiedadesReceta);

            // Añadimos el id que va a tener cada receta en nuestro array (Hacer comprobacion de repetidos)
            ObjectNode jsonRes = Json.newObject();
            jsonRes.put("id", recetaModel.getId());
            Result res;
            if (req.accepts("application/json")){
                res = Results.created(recetaResource.toJson());
            } else if (req.accepts("application/xml")) {
                Content content = views.xml.receta.render(recetaModel);
                res = Results.created(content);
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
            return Results.notFound(messages.at("receta-no-encontrada"));
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

    @Timed
    public Result update(Integer id, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        Receta rec = Receta.findById(Long.valueOf(id));

        if (rec == null){
            // Nos piden uno que no existe
            return Results.notFound(messages.at("receta-no-encontrada"));
        } else {

            Form<RecetaResource> recetaForm = formFactory.form(RecetaResource.class).bindFromRequest(req);
            RecetaResource recetaResource;

            if (recetaForm.hasErrors()) {
                return Results.badRequest(recetaForm.errorsAsJson());
            } else {
                recetaResource = recetaForm.get();

                Receta recetaModel = recetaResource.toModel();
                recetaModel.setId(rec.getId());
                recetaModel.getImagen().setId(rec.getImagen().getId());
                for (Integer i = 0; i < rec.getIngredientes().size(); i++) {
                    recetaModel.getIngredientes().get(i).setId(rec.getIngredientes().get(i).getId());
                }

                recetaModel.update();

                // Añadimos el id que va a tener cada receta en nuestro array (Hacer comprobacion de repetidos)
                ObjectNode jsonRes = Json.newObject();
                jsonRes.put("id", recetaModel.getId());
                //return Results.ok(messages.at("receta-modificada") + jsonRes);

                Result res;
                if (req.accepts("application/json")) {
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
    }

    @Timed
    public Result delete(Integer id, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        Receta rec = Receta.findById(Long.valueOf(id));

        if (rec == null){
            // Nos piden uno que no existe
            return Results.notFound(messages.at("receta-no-encontrada"));
        } else {

            RecetaResource recetaResource = new RecetaResource(rec);
            Receta recetaModel = recetaResource.toModel();
            recetaModel.delete();

            Result res;
            if (req.accepts("application/json")){
                res = Results.ok(recetaModel.toJson());
            } else if (req.accepts("application/xml")) {
                Content content = views.xml.receta.render(recetaModel);
                res = Results.ok(content);
            } else {
                res = Results.unsupportedMediaType();
            }
            return res;
        }

    }

    // @Cached(key="all-recetas-view") --> Cache de 1 Nivel (lo guarda una vez y ya, lo malo es que no cambia al poner json o xml, lo deja como este la primera vez
    @Timed
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

    public Result getRecetasConTiempoMenorA(Integer tiempoEnMinutos, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        List<Receta> recetas = Receta.findRecetaConTiempoMenorA(tiempoEnMinutos);

        List<RecetaResource> resources = recetas.stream().map(RecetaResource::new).collect(Collectors.toList()); // Convertir objeto receta a objeto recetaResource

        Result res;
        if (req.accepts("application/json")){
            JsonNode json = Json.toJson(resources);
            res = Results.ok(json);
        } else if (req.accepts("application/xml")) {
            Content content = views.xml.recetas.render(recetas);
            res = Results.ok(content);
        } else {
            res = Results.unsupportedMediaType();
        }
        return res;
    }







}
