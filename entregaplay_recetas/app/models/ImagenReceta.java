package models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ImagenReceta extends Model {

    @OneToOne(mappedBy = "imagen")
    @JsonBackReference
    private Receta parentReceta;

    private String url;

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Receta getParentReceta() {
        return parentReceta;
    }

    public void setParentReceta(Receta parentReceta) {
        this.parentReceta = parentReceta;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}