package controllers;

import actions.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import models.Propiedad;
import models.Receta;
import play.cache.SyncCacheApi;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.*;
import play.twirl.api.Content;
import views.RecetaResource;

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

    private Boolean hayCambios = false;


    /**
     * Método POST que crea una receta con todos los datos
     * @param req Request
     * @return Devuelve la receta que se ha creado
     */
    @Timed
    public Result create(Http.Request req) {
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
            hayCambios = true;

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
            return compruebaJsonXML(recetaModel,req);
        }
    }


    /**
     * Método GET para obtener una receta por id
     * @param id Id de la receta que se desea obtener
     * @param req Request
     * @return La receta que coincida con el id especificado
     */
    public Result get(Integer id, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        Receta rec = Receta.findById(Long.valueOf(id));

        if (rec == null){
            return Results.notFound(messages.at("receta-no-encontrada"));
        }
        return compruebaJsonXML(rec,req);
    }

    /**
     * Método UPDATE para actualizar una receta por id
     * @param id Id de la receta que se desea modificar
     * @param req Request
     * @return La recta actualizada en caso de que exista
     */
    @Timed
    public Result update(Integer id, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        Receta rec = Receta.findById(Long.valueOf(id));

        if (rec == null){
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
                hayCambios = true;

                return compruebaJsonXML(recetaModel,req);
            }
        }
    }

    /**
     * Método DELETE para eliminar una receta por id
     * @param id Id de la receta que se desea eliminar
     * @param req Request
     * @return La receta eliminada en caso de que exista
     */
    @Timed
    public Result delete(Integer id, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        Receta rec = Receta.findById(Long.valueOf(id));

        if (rec == null){
            return Results.notFound(messages.at("receta-no-encontrada"));
        } else {

            RecetaResource recetaResource = new RecetaResource(rec);
            Receta recetaModel = recetaResource.toModel();
            recetaModel.delete();
            hayCambios = true;

            return compruebaJsonXML(recetaModel,req);
        }

    }

    // @Cached(key="all-recetas-view") --> Cache de 1 Nivel (lo guarda una vez y ya, lo malo es que no cambia al poner json o xml, lo deja como este la primera vez
    /**
     * Método GET para obtener todas las recetas
     * @param req Request
     * @return Devuelve todas las recetas
     */
    @Timed
    public Result getAll(Http.Request req) {
        Messages messages = messagesApi.preferred(req);

        // Cache de la lista de la bbdd de las recetas (Acceso a la bbdd) 2 Nivel
        List<Receta> recetas;
        Optional<Object> optRecetas = cache.get("all-recetas");
        if (optRecetas.isPresent() && !hayCambios) {
            System.out.println("Valor cacheado");
            recetas = (List<Receta>) optRecetas.get();
        } else {
            System.out.println("Valor NO cacheado");
            recetas = Receta.findAll();
            cache.set("all-recetas", recetas);
            hayCambios = false;
        }

        return compruebaJsonXML(recetas,req);

    }

    /**
     * Método GET que filtra las recetas con tiempo menor o igual al especificado
     * @param tiempoEnMinutos Tiempo de realización de la receta
     * @param req Request
     * @return Lista de recetas que tengan un tiempo menor o igual al especificado
     */
    public Result getRecetasConTiempoMenorA(Integer tiempoEnMinutos, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        List<Receta> recetas = Receta.findRecetaConTiempoMenorA(tiempoEnMinutos);

        return compruebaJsonXML(recetas,req);
    }

    /**
     * Método GET que filtra las recetas por ingrediente
     * @param nombreIngrediente Nombre del ingrediente que debe contener la receta
     * @param req Request
     * @return Lista de recetas que contengan el ingrediente especificado
     */
    public Result getRecetasConIngrediente(String nombreIngrediente, Http.Request req) {
        Messages messages = messagesApi.preferred(req);
        List<Receta> recetas = Receta.findbyIngrediente(nombreIngrediente);

        return compruebaJsonXML(recetas,req);
    }

    /**
     * Método para comprobar el formato de la respuesta (Json o Xml) con una receta
     * @param receta Receta
     * @param req Request
     * @return Devuelve la respuesta en el formato correspondiente
     */
    private Result compruebaJsonXML(Receta receta, Http.Request req){
        Result res;
        if (req.accepts("application/json")){
            res = Results.ok(receta.toJson());
        } else if (req.accepts("application/xml")) {
            Content content = views.xml.receta.render(receta);
            res = Results.ok(content);
        } else {
            res = Results.unsupportedMediaType();
        }
        return res;
    }

    /**
     * Método para comprobar el formato de la respuesta (Json o Xml) con una lista de recetas
     * @param recetas Lista de recetas
     * @param req Request
     * @return Devuelve la respuesta en el formato correspondiente
     */
    private Result compruebaJsonXML(List<Receta> recetas, Http.Request req){
        List<RecetaResource> resources = recetas.stream().map(RecetaResource::new).collect(Collectors.toList());
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
