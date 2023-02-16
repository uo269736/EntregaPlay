package views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import models.ImagenReceta;
import models.Ingrediente;
import models.Receta;
import org.hibernate.validator.constraints.URL;
import play.data.validation.Constraints;
import play.i18n.MessagesApi;
import play.libs.Json;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class IngredienteResource {

    private Long id;

    @Inject
    private MessagesApi messagesApi;

    @JsonProperty("nombre")
    @Constraints.Required
    @NotBlank(message="nombre vacio")
    private String nombre;

    @Constraints.Required
    @NotBlank(message = "unidad vacio")
    private String unidad;

    @Constraints.Required
    @Constraints.Min(1)
    private Integer cantidad;


    public IngredienteResource(){
        super();
    }

    public IngredienteResource(Ingrediente ingrediente){
        super();
        this.nombre = ingrediente.getNombre();
        this.unidad = ingrediente.getUnidad();
        this.cantidad = ingrediente.getCantidad();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }

    public Ingrediente toModel(){
        Ingrediente ing = new Ingrediente();
        ing.setNombre(this.nombre);
        ing.setCantidad(this.cantidad);
        ing.setUnidad(this.unidad);

        return ing;
    }

}
