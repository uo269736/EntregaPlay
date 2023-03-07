package validators;

import models.Receta;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.F;
import views.RecetaResource;

import javax.inject.Inject;
import java.util.List;

public class DescripcionRecetaValidator extends Constraints.Validator{

    /**
     * Método que comprueba si la descripción tiene algún espacio y si tiene más de 20 caracteres
     * @param object El String a comprobar
     * @return Devuelve true si cumple los requisitos y false en caso contrario
     */
    @Override
    public boolean isValid(Object object) {
        if(object instanceof String){
            String descripcion = (String) object;

            if (descripcion.contains(" ")){
                if (descripcion.length() >= 20){
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Devuelve un mensaje de error en caso de qué el string no sea válido
     * @return Mensaje de error
     */
    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<>("descripcion-receta-incompleta",new Object[]{""});
    }

}
