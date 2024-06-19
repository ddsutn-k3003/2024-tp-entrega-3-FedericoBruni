package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;


public class RegistrarTemperaturaController {
    private Fachada fachadaHeladeras;
    public RegistrarTemperaturaController(Fachada fachadaHeladeras) {
        this.fachadaHeladeras = fachadaHeladeras;
    }

    public void registrarTemperatura(Context context) {

        try {
            TemperaturaDTO temperaturaDTO = context.bodyAsClass(TemperaturaDTO.class);
            this.fachadaHeladeras.temperatura(temperaturaDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("Mensaje", "Temperatura registrada correctamente");
            response.put("Temperatura", temperaturaDTO);
            context.status(200).json(response);

        } catch (Exception e) {
            EntityManager em = this.fachadaHeladeras.getEntityManager();
            em.getTransaction().commit();
            em.close();
            throw new BadRequestResponse("Error de solicitud.");
        }
    }
}
