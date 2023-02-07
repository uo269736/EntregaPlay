package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Receta extends Model{


    public static final Finder<Long, Receta> find = new Finder<>(Receta.class); //Esto nos permite leer de la bbdd


    @Id
    private Long id;
    private String name;
    //private String ingrediente;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "parentReceta")
    private List<Ingrediente> ingredientes;


    // Métodos de acceso
    public static Receta findById(Long id){
        //Receta.findById(1)
        return find.byId(id);
    }

    public static Receta findByName(String name){
        //Receta.findByName(pepe)
        return find.query().where().eq("name", name).findOne();
    }

    public static List<Receta> findAll(){
        //Receta.findByName(receta1)
        return find.all();
    }



    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public JsonNode toJson() {
        return Json.toJson(this);
    }









    /*
    private String descripcion;
    private ArrayList<Ingrediente> ingredientes;
    private String pasos;
    private Integer tiempo;
*/


    //@OneToOne (cascade = CascadeType.ALL)
   // private ImagenReceta imagen;
/*
    // métodos de acceso


    // getters & setters
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public ImagenReceta getImagen() {
        return imagen;
    }

    public void setImagen(ImagenReceta imagen) {
        this.imagen = imagen;
    }

    */

}