package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ImagenReceta;
import models.Ingrediente;
import models.Propiedad;
import models.Receta;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import play.twirl.api.Content;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return Helpers.fakeApplication(Helpers.inMemoryDatabase());
    }

    @Test
    public void testGetAllReceta() {
        Http.RequestBuilder req = Helpers.fakeRequest()
                .method("GET")
                .uri("/recetas");

        Result r = Helpers.route(app, req);
        assertEquals(200, r.status());
    }

    @Test
    public void testGetRecetaNoexiste() {
        Http.RequestBuilder req = Helpers.fakeRequest()
                .method("GET")
                .uri("/receta/100");

        Result r = Helpers.route(app, req);
        assertEquals(404, r.status());
    }

    @Test
    public void testRecetaView() {
        Receta r = new Receta();
        r.addIngredientes(new Ingrediente());
        r.addPropiedad(new Propiedad());
        r.setImagen(new ImagenReceta());
        Content res = views.xml.receta.render(r);
        assertEquals("application/xml", res.contentType());
        assertTrue(res.body().contains("<receta>"));
    }

}
