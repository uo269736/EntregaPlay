package unitarios;

import models.Ingrediente;
import models.Propiedad;
import models.Receta;
import org.junit.Test;
import validators.DescripcionRecetaValidator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestUnitarios {

    /**
     * Test para comprobar que los ingredientes se añaden correctamente a la lista
     */
    @Test
    public void addIngrediente() {
        Receta r = new Receta();
        Ingrediente i = new Ingrediente();
        List<Ingrediente> listaVacia = new ArrayList<>();
        //Comprobar que no hay ningun ingrediente en la receta
        assertEquals(r.getIngredientes(),listaVacia );
        //Comprobar que el ingrediente no esta asociado a ninguna receta
        assertEquals(i.getParentReceta(),null );
        r.addIngredientes(i);
        //Comprobar que se ha añadido una receta
        assertEquals(1, r.getIngredientes().size());
        //Comprobar que el ingrediente esta asociado a la receta
        assertEquals(i.getParentReceta(), r);
    }

    /**
     * Test para comprobar que la descripcion cumple con los requisitos del validador (número de caracteres y espacio)
     */
    @Test
    public void validadorDescripcion() {
        DescripcionRecetaValidator drv = new DescripcionRecetaValidator();
        //Comprobar que la descripción no es válida (no llega a 20 caracteres y no tiene espacio)
        assertEquals(drv.isValid("Hola"), false);
        //Comprobar que la descripción es válida (tien 20 caracteres y un espacio)
        assertEquals(drv.isValid("Hola aaaaaaaaaaaaaaaaaaaaaaaaaaaaa"), true);
    }

    /**
     * Test para comprobar que la propiedad se añade correctamente a la receta
     */
    @Test
    public void addPropiedadReceta() {
        Receta r = new Receta();
        Propiedad p = new Propiedad();
        Set<Receta> listaVaciaReceta = new HashSet<>();
        List<Propiedad> listaVaciaPropiedad = new ArrayList<>();
        //Comprobar que la propiedad no esta asociada a ninguna receta
        assertEquals(p.getRecetas(),listaVaciaReceta );
        //Comprobar que la receta no tiene ninguna propiedad
        assertEquals(r.getPropiedades(),listaVaciaPropiedad );
        r.addPropiedad(p);
        //Comprobar que la propiedad tiene una receta asociada
        assertEquals(p.getRecetas().size(),1 );
        //Comprobar que la receta tiene una propiedad
        assertEquals(r.getPropiedades().size(),1 );
    }
}
