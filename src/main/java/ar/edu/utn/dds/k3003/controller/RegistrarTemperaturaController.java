package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import javax.persistence.EntityManager;


public class RegistrarTemperaturaController {
    private Fachada fachadaHeladeras;
    public RegistrarTemperaturaController(Fachada fachadaHeladeras) {
        this.fachadaHeladeras = fachadaHeladeras;
    }

    public void registrarTemperatura(Context context) {

        try {
            TemperaturaDTO temperaturaDTO = context.bodyAsClass(TemperaturaDTO.class);
            this.fachadaHeladeras.temperatura(temperaturaDTO);
            context.status(200).result("Temperatura registrada correctamente.");
        } catch (Exception e) {
            EntityManager em = this.fachadaHeladeras.getEntityManager();
            em.getTransaction().commit();
            em.close();
            throw new BadRequestResponse("Error de solicitud.");
        }
    }
}
