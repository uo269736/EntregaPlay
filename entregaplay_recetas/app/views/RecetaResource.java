package views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import models.ImagenReceta;
import models.Ingrediente;
import models.Receta;
import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class RecetaResource {

    @JsonProperty("nombre")
    @Constraints.Required
    @NotBlank(message = "nombre vacio")
    private String nombre;

    @Constraints.Required
    private List<Ingrediente> ingredientes;

    @Constraints.Required
    @NotBlank(message = "descripcion receta vacia")
    private String descripcion;

    @Constraints.Required
    @NotBlank(message = "pasos receta vacio")
    private String pasos;

    @Constraints.Required
    @Constraints.Min(1)
    private Integer tiempo;



    private ImagenReceta urlImagen;

    //@URL(message = "introduce una url correcta")
    //@Constraints.Required
    //private String urlImagen;

    // Para permitir crear una receta:
    public RecetaResource(){
        super();
    }
    public RecetaResource(Receta receta){
        super();
        this.nombre = receta.getNombre();
        this.ingredientes = receta.getIngredientes();
        this.descripcion = receta.getDescripcion();
        this.pasos = receta.getPasos();
        this.tiempo = receta.getTiempo();
        this.urlImagen = receta.getImagen();
        /*
        ImagenReceta ir = receta.getImagen();
        if (ir != null){
            this.urlImagen = ir.getUrl();
        }

         */
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    /*

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

     */

    public ImagenReceta getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(ImagenReceta urlImagen) {
        this.urlImagen = urlImagen;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }

    public Receta toModel(){
        Receta rec = new Receta();
        rec.setNombre(this.nombre);

        // Preguntar si esta bien
        /*
        for ( Ingrediente i : this.ingredientes
             ) {
            i.setParentReceta(rec);
        }

         */
        rec.setIngredientes(this.ingredientes);

        rec.setDescripcion(this.descripcion);
        rec.setPasos(this.pasos);
        rec.setTiempo(this.tiempo);

        rec.setImagen(this.urlImagen);

        /*
        ImagenReceta imagenReceta = new ImagenReceta();
        imagenReceta.setUrl(this.urlImagen);
        imagenReceta.setParentReceta(rec);
        rec.setImagen(imagenReceta);
         */



        return rec;
    }

}
