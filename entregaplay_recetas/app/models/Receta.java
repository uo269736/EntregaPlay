package models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.Finder;
import io.ebean.Model;

import play.libs.Json;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Receta extends Model{

    public static final Finder<Long, Receta> find = new Finder<>(Receta.class); //Esto nos permite leer de la bbdd

    @Id
    private Long id;
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentReceta")
    @JsonManagedReference
    private List<Ingrediente> ingredientes;
    private String descripcion;
    private String pasos;
    private Integer tiempoEnMinutos;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private ImagenReceta imagen;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Propiedad> propiedades;


    // Métodos de acceso
    public static Receta findById(Long id){
        return find.byId(id);
    }

    public static Receta findByNombre(String nombre){
        return find.query().where().eq("nombre", nombre).findOne();
    }

    public static List<Receta> findAll(){
        return find.all();
    }

    public static List<Receta> findRecetaConTiempoMenorA(Integer tiempoEnMinutos){
        return find.query()
                .where()
                .le("tiempoEnMinutos", tiempoEnMinutos)
                .findList();
    }

    // Getters & Setters
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

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
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

    public Integer getTiempoEnMinutos() {
        return tiempoEnMinutos;
    }

    public void setTiempoEnMinutos(Integer tiempoEnMinutos) {
        this.tiempoEnMinutos = tiempoEnMinutos;
    }


    public ImagenReceta getImagen() {
        return imagen;
    }

    public void setImagen(ImagenReceta imagen) {
        this.imagen = imagen;
    }

    public void addIngredientes (Ingrediente ingrediente){
        if(this.ingredientes == null){
            this.ingredientes = new ArrayList<>();
        }
        this.ingredientes.add(ingrediente);
        ingrediente.setParentReceta(this);
    }

    public List<Propiedad> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(List<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }

    public void addPropiedad (Propiedad propiedad){
        if(this.propiedades == null){
            this.propiedades = new ArrayList<>();
        }
        this.propiedades.add(propiedad);
        propiedad.addReceta(this);
    }


    public JsonNode toJson() {
        return Json.toJson(this);
    }


}