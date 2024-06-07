package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import java.util.NoSuchElementException;

public class DepositarViandaController {
    private FachadaHeladeras fachadaHeladeras;
    public DepositarViandaController(FachadaHeladeras fachadaHeladeras) {
        this.fachadaHeladeras = fachadaHeladeras;
    }

    public void depositarVianda(Context context) throws BadRequestResponse {
        //ViandaDTO viandaDTO = context.bodyAsClass(ViandaDTO.class); // Rompe pq ViandaDTO no tiene constructor. Esperar a que en Slack digan qué hacer..
        //this.fachadaHeladeras.depositar(viandaDTO.getHeladeraId(), viandaDTO.getCodigoQR());

        // Ahora tira error de solicitud, xq no existe ninguna vianda que pueda guardar..
        try {
            ViandaDTO viandaDTO = context.bodyAsClass(ViandaDTO.class); // Rompe pq ViandaDTO no tiene constructor. Esperar a que en Slack digan qué hacer..
            this.fachadaHeladeras.depositar(viandaDTO.getHeladeraId(), viandaDTO.getCodigoQR());
            context.status(200).result("Vianda depositada correctamente.");
        } catch (NoSuchElementException e) {
            throw new BadRequestResponse("Error de solicitud.");
        }


    }
}
