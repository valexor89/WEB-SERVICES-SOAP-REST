package it.scuola.rest;

import it.scuola.model.Contatto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  SERVIZIO REST - Rubrica
 * ============================================================
 *
 * Questo servizio gestisce la rubrica seguendo le convenzioni REST.
 * In REST, ogni "risorsa" (es. un contatto) ha una URL univoca.
 *
 * URL BASE: http://localhost:8081/api/contatti
 *
 * OPERAZIONI CRUD:
 *   GET    /api/contatti           → leggi TUTTI i contatti
 *   GET    /api/contatti/{id}      → leggi UN contatto specifico
 *   POST   /api/contatti           → CREA un nuovo contatto
 *   PUT    /api/contatti/{id}      → AGGIORNA un contatto esistente
 *   DELETE /api/contatti/{id}      → ELIMINA un contatto
 *
 * @PathParam  → legge un parametro dalla URL (es. /contatti/2 → id=2)
 * @Consumes   → formato dei dati in arrivo (JSON nel POST/PUT)
 */
@Path("/contatti")
public class RubricaREST {

    // -------------------------------------------------------
    // Dati in memoria condivisi (static = stessi dati per tutte le richieste)
    // -------------------------------------------------------
    private static List<Contatto> rubrica = new ArrayList<>();
    private static int prossimoId = 4;

    static {
        rubrica.add(new Contatto(1, "Mario Rossi",  "333-1111111", "mario@email.it"));
        rubrica.add(new Contatto(2, "Luca Bianchi", "347-2222222", "luca@email.it"));
        rubrica.add(new Contatto(3, "Anna Verdi",   "320-3333333", "anna@email.it"));
    }

    // -------------------------------------------------------
    // GET /api/contatti  →  tutti i contatti
    // -------------------------------------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contatto> getTuttiContatti() {
        return rubrica;
    }

    // -------------------------------------------------------
    // GET /api/contatti/{id}  →  un contatto specifico
    // Esempio: GET /api/contatti/2
    // -------------------------------------------------------
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContattoById(@PathParam("id") int id) {
        for (Contatto c : rubrica) {
            if (c.getId() == id) {
                return Response.ok(c).build(); // HTTP 200 OK
            }
        }
        // Non trovato → HTTP 404 Not Found
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"errore\":\"Contatto con ID " + id + " non trovato\"}")
                       .build();
    }

    // -------------------------------------------------------
    // POST /api/contatti  →  crea un nuovo contatto
    // Il corpo della richiesta deve essere JSON:
    // {"nome":"Paolo Neri","telefono":"345-4444444","email":"paolo@email.it"}
    // -------------------------------------------------------
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response aggiungiContatto(Contatto nuovo) {
        nuovo.setId(prossimoId++);
        rubrica.add(nuovo);
        System.out.println("[REST Rubrica] Aggiunto: " + nuovo);
        // HTTP 201 Created
        return Response.status(Response.Status.CREATED).entity(nuovo).build();
    }

    // -------------------------------------------------------
    // PUT /api/contatti/{id}  →  aggiorna un contatto
    // Esempio: PUT /api/contatti/2  con body JSON aggiornato
    // -------------------------------------------------------
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response aggiornaContatto(@PathParam("id") int id, Contatto aggiornato) {
        for (int i = 0; i < rubrica.size(); i++) {
            if (rubrica.get(i).getId() == id) {
                aggiornato.setId(id); // mantengo l'ID originale
                rubrica.set(i, aggiornato);
                return Response.ok(aggiornato).build(); // HTTP 200 OK
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"errore\":\"Contatto non trovato\"}")
                       .build();
    }

    // -------------------------------------------------------
    // DELETE /api/contatti/{id}  →  elimina un contatto
    // Esempio: DELETE /api/contatti/3
    // -------------------------------------------------------
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminaContatto(@PathParam("id") int id) {
        boolean rimosso = rubrica.removeIf(c -> c.getId() == id);
        if (rimosso) {
            return Response.ok("{\"messaggio\":\"Contatto eliminato con successo\"}").build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"errore\":\"Contatto non trovato\"}")
                       .build();
    }
}
