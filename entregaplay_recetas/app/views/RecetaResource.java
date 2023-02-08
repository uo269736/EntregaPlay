package views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import models.ImagenReceta;
import models.Ingrediente;
import models.Receta;
import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints;
import play.libs.Json;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class RecetaResource {

    @JsonProperty("name")
    @Constraints.Required
    @NotBlank
    private String name;
    private List<Ingrediente> ingredientes;

    private String descripcion;

    private String pasos;
    private Integer tiempo;

//    @URL
//    private String urlImagen;

    // Para permitir crear una receta:
    public RecetaResource(){
        super();
    }
    public RecetaResource(Receta receta){
        super();
        this.name = receta.getName();
        this.ingredientes = receta.getIngredientes();
        this.descripcion = receta.getDescripcion();
        this.pasos = receta.getPasos();
        this.tiempo = receta.getTiempo();
//        ImagenReceta ir = receta.getImagen();
//        if (ir != null){
//            this.urlImagen = ir.getUrl();
//        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

//    public String getUrlImagen() {
//        return urlImagen;
//    }
//
//    public void setUrlImagen(String urlImagen) {
//        this.urlImagen = urlImagen;
//    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }

    public Receta toModel(){
        Receta rec = new Receta();
        rec.setName(this.name);
        rec.setIngredientes(this.ingredientes);
        rec.setDescripcion(this.descripcion);
        rec.setPasos(this.pasos);
        rec.setTiempo(this.tiempo);

//        ImagenReceta imagenReceta = new ImagenReceta();
//        imagenReceta.setUrl(this.urlImagen);
//        imagenReceta.setParentReceta(rec);
//        rec.setImagen(imagenReceta);
        return rec;
    }

}
