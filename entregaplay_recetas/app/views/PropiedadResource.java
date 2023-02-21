package views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import models.Ingrediente;
import models.Propiedad;
import play.data.validation.Constraints;
import play.i18n.MessagesApi;
import play.libs.Json;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;

public class PropiedadResource {

    private Long id;

    @Inject
    private MessagesApi messagesApi;

    @JsonProperty("nombrePropiedad")
    @Constraints.Required
    @NotBlank(message="nombre-propiedad-vacio")
    private String nombrePropiedad;

    public PropiedadResource(){
        super();
    }

    public PropiedadResource(Propiedad propiedad){
        super();
        this.id = propiedad.getId();
        this.nombrePropiedad = propiedad.getNombrePropiedad().trim().toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePropiedad() {
        return nombrePropiedad;
    }

    public void setNombrePropiedad(String nombrePropiedad) {
        this.nombrePropiedad = nombrePropiedad;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }

    public Propiedad toModel(){
        Propiedad pro = new Propiedad();
        pro.setId(this.id);
        pro.setNombrePropiedad(this.nombrePropiedad);
        return pro;
    }
}
