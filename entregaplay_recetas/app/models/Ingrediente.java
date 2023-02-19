package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import  io.ebean.Model;

import javax.persistence.*;

@Entity
public class Ingrediente extends Model{

    @Id
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Receta parentReceta;

    private String nombreIngrediente;
    private Integer cantidad;
    private String unidad;

    // métodos de acceso

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