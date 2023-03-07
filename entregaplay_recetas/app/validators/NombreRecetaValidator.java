package validators;

import models.Receta;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.F;
import views.RecetaResource;

import javax.inject.Inject;
import java.util.List;

public class  NombreRecetaValidator extends Constraints.Validator{

    /**
     * Método que comprueba si ya existe una receta con el mismo nombre
     * @param object La receta a comprobar
     * @return Devuelve true si no hay recetas con el mismo nombre y false si ya existe una
     */
    @Override
    public boolean isValid(Object object) {
        if(object instanceof String){
            String nombre = (String) object;
            List<Receta> recetas = Receta.findAll();
            for(Receta r : recetas) {
                if(r.getNombre().trim().equals(nombre.trim())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Devuelve un mensaje de error en caso de qué la receta no sea válido
     * @return Mensaje de error
     */
    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<>("nombre-receta-repetido",new Object[]{""});
    }
}
