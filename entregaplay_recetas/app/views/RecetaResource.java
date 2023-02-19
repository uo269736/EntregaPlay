package views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import models.ImagenReceta;
import models.Ingrediente;
import models.Receta;
import org.checkerframework.checker.compilermsgs.qual.CompilerMessageKey;
import org.hibernate.validator.constraints.URL;
import org.jboss.logging.Message;
import org.springframework.validation.annotation.Validated;
import play.api.i18n.Messages$;
import play.data.validation.Constraints;
import play.i18n.MessagesApi;
import play.libs.Json;
import validators.NombreRecetaValidator;


import javax.inject.Inject;
import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class RecetaResource {

    private Long id;

    @Inject
    private MessagesApi messagesApi;

    @JsonProperty("nombre")
    @Constraints.Required
    @NotBlank(message="nombre-receta-vacio")
    @Constraints.ValidateWith(NombreRecetaValidator.class)
    private String nombre;

    @Constraints.Required
    @Valid
    private List<IngredienteResource> ingredientes;

    @Constraints.Required
    @NotBlank(message = "descripcion-receta-vacio")
    private String descripcion;

    @Constraints.Required
    @NotBlank(message = "pasos-receta-vacio")
    private String pasos;

    @Constraints.Required
    @Constraints.Min(message="tiempo-receta-vacio",value=1)
    private Integer tiempo;

    @URL(message="url-receta-valida")
    @Constraints.Required
    private String imagenUrl;

    private Long imagenUrlId;

    // Para permitir crear una receta:
    public RecetaResource(){
        super();
    }

    public RecetaResource(Receta receta){
        super();
        this.id = receta.getId();
        this.nombre = receta.getNombre();
        this.ingredientes = new ArrayList<>();
        for(Ingrediente i : receta.getIngredientes()) {
            IngredienteResource ir = new IngredienteResource(i);
            this.ingredientes.add(ir);
        }
        this.descripcion = receta.getDescripcion();
        this.pasos = receta.getPasos();
        this.tiempo = receta.getTiempo();

        //this.imagenUrl = receta.getImagen();

        ImagenReceta ir = receta.getImagen();
        if (ir != null){
            this.imagenUrl = ir.getUrl();
            this.imagenUrlId = ir.getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public List<IngredienteResource> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteResource> ingredientes) {
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
        rec.setId(this.id);
        rec.setNombre(this.nombre);

        for (IngredienteResource i : this.ingredientes) {
            rec.addIngredientes(i.toModel());
        }

        rec.setDescripcion(this.descripcion);
        rec.setPasos(this.pasos);
        rec.setTiempo(this.tiempo);

        ImagenReceta imagenReceta = new ImagenReceta();
        imagenReceta.setId(this.imagenUrlId);
        imagenReceta.setUrl(this.imagenUrl);
        imagenReceta.setParentReceta(rec);
        rec.setImagen(imagenReceta);

        return rec;
    }

}
