package models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.Finder;
import io.ebean.Model;
import org.hibernate.validator.constraints.URL;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ImagenReceta extends Model {

    public static final Finder<Long, ImagenReceta> find = new Finder<>(ImagenReceta.class);

    @OneToOne(mappedBy = "imagen")
    @JsonBackReference
    //@JsonBackReference
    private Receta parentReceta;

    @Id
    private Long id;

    private String url;

//    private String descripcionImagen;



    public Receta getParentReceta() {
        return parentReceta;
    }

    public void setParentReceta(Receta parentReceta) {
        this.parentReceta = parentReceta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }
}