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

    @Override
    public F.Tuple<String, Object[]> getErrorMessageKey() {
        return new F.Tuple<>("descripcion-receta-incompleta",new Object[]{""});
    }

}
