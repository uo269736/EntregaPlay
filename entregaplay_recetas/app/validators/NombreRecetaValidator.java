package validators;

import models.Receta;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.libs.F;
import views.RecetaResource;

import javax.inject.Inject;
import java.util.List;

public class NombreRecetaValidator extends Constraints.Validator{

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

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<>("nombre-receta-repetido",new Object[]{""});
    }
}
