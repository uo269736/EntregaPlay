package models;

import io.ebean.Finder;
import  io.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingrediente extends Model{


    public static final Finder<Long,Ingrediente> find = new Finder<>(Ingrediente.class);
    @Id
    private Long id;

    @ManyToOne
    private Receta parentReceta;

    private String nombre;
    private Integer cantidad;
    private String unidad;

    // métodos de acceso
    /*
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
    }
*/
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
}