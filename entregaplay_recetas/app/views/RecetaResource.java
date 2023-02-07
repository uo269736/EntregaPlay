package views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import models.Ingrediente;
import models.Receta;
import play.data.validation.Constraints;
import play.libs.Json;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class RecetaResource {

    @JsonProperty("name")
    @Constraints.Required
    @NotBlank
    private String name;
    private List<Ingrediente> ingrediente;


    // Para permitir crear una receta:
    public RecetaResource(Receta receta){
        super();
        this.name = receta.getName();
        this.ingrediente = receta.getIngredientes();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Ingrediente> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(List<Ingrediente> ingrediente) {
        this.ingrediente = ingrediente;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }

    public Receta toModel(){
        Receta rec = new Receta();
        rec.setName(this.name);
        rec.setIngredientes(this.ingrediente);
        return rec;
    }

}
