package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Propiedad extends Model {

    public static final Finder<Long, Propiedad> find = new Finder<>(Propiedad.class); //Esto nos permite leer de la bbdd

    @Id
    private Long id;

    private String nombrePropiedad;

    @JsonBackReference
    @ManyToMany(mappedBy = "propiedades")
    private Set<Receta> recetas;


    /**
     * Método para obtener todas las propiedades del sistema
     * @return Lista de propiedades
     */
    public static List<Propiedad> findAll(){
        return find.all();
    }

    // Getters & Setters
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

    public Set<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(Set<Receta> recetas) {
        this.recetas = recetas;
    }

    public void addReceta (Receta receta){
        if(this.recetas == null){
            this.recetas = new HashSet<>();
        }
        this.recetas.add(receta);
    }
}
