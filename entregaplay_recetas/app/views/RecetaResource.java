package views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import models.ImagenReceta;
import models.Ingrediente;
import models.Receta;
import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.hibernate.validator.constraints.URL;
import org.jboss.logging.Message;
import play.api.i18n.Messages$;
import play.data.validation.Constraints;
import play.i18n.MessagesApi;
import play.libs.Json;


import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class RecetaResource {

    private Long id;

    @Inject
    private MessagesApi messagesApi;

    @JsonProperty("nombre")
    @Constraints.Required
    @NotBlank(message="nombre vacio")
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

    //private ImagenReceta imagen;

    @URL()
    @Constraints.Required
    private String imagenUrl;

    /*
    @Constraints.Required
    private String descripcionImagen;
*/


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

        //this.imagenUrl = receta.getImagen();

        ImagenReceta ir = receta.getImagen();
        if (ir != null){
            this.imagenUrl = ir.getUrl();
        }


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
    public ImagenReceta getImagen() {
        return imagen;
    }

    public void setImagen(ImagenReceta imagen) {
        this.imagen = imagen;
    }

     */



    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String urlImagen) {
        this.imagenUrl = urlImagen;
    }

    /*
    public String getDescripcionImagen() {
        return descripcionImagen;
    }

    public void setDescripcionImagen(String descripcionImagen) {
        this.descripcionImagen = descripcionImagen;
    }

     */

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


        //rec.setImagen(this.imagen);


        ImagenReceta imagenReceta = new ImagenReceta();
        imagenReceta.setUrl(this.imagenUrl);
        imagenReceta.setParentReceta(rec);
        rec.setImagen(imagenReceta);



        return rec;
    }

}
