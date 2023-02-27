package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.ebean.Finder;
import  io.ebean.Model;
import io.ebeaninternal.server.util.Str;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ingrediente extends Model{

    public static final Finder<Long, Ingrediente> find = new Finder<>(Ingrediente.class); //Esto nos permite leer de la bbdd

    @Id
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Receta parentReceta;

    private String nombreIngrediente;
    private Integer cantidad;
    private String unidad;



    // métodos de acceso
    public static List<Long> findParentRecetabyNombreIngrediente(String nombreIngrediente){
        return find.query()
                .select("parentReceta")
                .setDistinct(true)
                .where()
                .eq("nombreIngrediente", nombreIngrediente)
                .findSingleAttributeList();
    }


    // getters & setters
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Receta getParentReceta() {
        return parentReceta;
    }

    public void setParentReceta(Receta parentReceta) {
        this.parentReceta = parentReceta;
    }



}