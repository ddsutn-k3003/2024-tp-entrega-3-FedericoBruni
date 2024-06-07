package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.dtos.TemperaturaDTO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;


public class RegistrarTemperaturaController {
    private FachadaHeladeras fachadaHeladeras;
    public RegistrarTemperaturaController(Fachada fachadaHeladeras) {
        this.fachadaHeladeras = fachadaHeladeras;
    }

    public void registrarTemperatura(Context context) {

        try {
            TemperaturaDTO temperaturaDTO = context.bodyAsClass(TemperaturaDTO.class);
            this.fachadaHeladeras.temperatura(temperaturaDTO);
            context.status(200).result("Temperatura registrada correctamente.");
        } catch (Exception e) {
            throw new BadRequestResponse("Error de solicitud.");
        }
    }
}
