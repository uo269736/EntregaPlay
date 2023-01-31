package models;

import io.ebean.Finder;
import  io.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Receta extends Model{

    //public static final Finder<Long,User> find = new Finder<>(User.class);
    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private ArrayList<Ingrediente> ingredientes;
    private String pasos;
    private Integer tiempo;



    //@OneToOne (cascade = CascadeType.ALL)
    private ImagenReceta imagen;
/*
    // métodos de acceso
    public static User findById(Long id){
        //User.findById(1)
        return find.byId(id);
    }

    public static User findByName(String name){
        //User.findByName(pepe)
        return find.query().where().eq("name", name).findOne();
    }

    public static List<User> findAll(){
        //User.findByName(pepe)
        return find.all();
    }

    public static List<User> findGreaterThanAge(Integer age){
        //User.findGreaterThanAge(18)
        return find.query()
                .where()
                .gt("age", age)
                .orderBy("name")
                .setMaxRows(25)
                .setFirstRow(0)
                //.getGeneratedSql()
                .findList();
    }*/

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
}