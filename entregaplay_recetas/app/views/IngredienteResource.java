package views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import models.Ingrediente;
import play.data.validation.Constraints;
import play.i18n.MessagesApi;
import play.libs.Json;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;

public class IngredienteResource {

    private Long id;

    @Inject
    private MessagesApi messagesApi;

    @JsonProperty("nombreIngrediente")
    @Constraints.Required
    @NotBlank(message="nombre-ingrediente-vacio")
    private String nombreIngrediente;

    @Constraints.Required
    @NotBlank(message = "unidad-ingrediente-vacio")
    private String unidad;

    @Constraints.Required
    @Constraints.Min(value=1, message="cantidad-ingrediente-vacio")
    private Integer cantidad;


    public IngredienteResource(){
        super();
    }

    public IngredienteResource(Ingrediente ingrediente){
        super();
        this.id = ingrediente.getId();
        this.nombreIngrediente = ingrediente.getNombre();
        this.unidad = ingrediente.getUnidad();
        this.cantidad = ingrediente.getCantidad();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
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
        ing.setId(this.id);
        ing.setNombre(this.nombreIngrediente);
        ing.setCantidad(this.cantidad);
        ing.setUnidad(this.unidad);

        return ing;
    }

}
