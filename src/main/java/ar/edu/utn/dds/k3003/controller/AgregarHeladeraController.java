package ar.edu.utn.dds.k3003.controller;
import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.HeladeraDTO;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

public class AgregarHeladeraController {
    private Fachada fachadaHeladeras;
    public AgregarHeladeraController(Fachada fachadaHeladeras) {
        super();
        this.fachadaHeladeras = fachadaHeladeras;
    }

    public void agregar(Context context) throws BadRequestResponse {
        try {
            HeladeraDTO heladeraDTO = context.bodyAsClass(HeladeraDTO.class);
            context.status(200).result("Heladera agregada correctamente.").json(this.fachadaHeladeras.agregar(heladeraDTO));
        } catch (Exception e) {
            throw new BadRequestResponse("Error de solicitud.");
        }

    }
}