package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.FachadaHeladeras;
import ar.edu.utn.dds.k3003.facades.dtos.RetiroDTO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import java.util.NoSuchElementException;

public class RetirarViandaController {
    private FachadaHeladeras fachadaHeladeras;
    public RetirarViandaController(Fachada fachadaHeladeras) {
        this.fachadaHeladeras = fachadaHeladeras;
    }

    public void retirarVianda(Context context)  throws BadRequestResponse {
        try {
            RetiroDTO retiroDTO = context.bodyAsClass(RetiroDTO.class);
            this.fachadaHeladeras.retirar(retiroDTO);
            context.status(200).result("Vianda retirada correctamente.");
        } catch (NoSuchElementException e) {
            throw new BadRequestResponse("Error de solicitud.");
        }
    }
}
