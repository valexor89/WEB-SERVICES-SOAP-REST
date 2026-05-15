package it.scuola.rest;

import it.scuola.model.Libro;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================
 *  SERVIZIO REST - Libreria
 * ============================================================
 *
 * Questo servizio gestisce un catalogo libri.
 * Introduce @QueryParam per ricerche/filtri nella lista.
 *
 * URL BASE: http://localhost:8081/api/libri
 *
 * OPERAZIONI:
 *   GET    /api/libri                     → tutti i libri
 *   GET    /api/libri?autore=Eco          → filtra per autore
 *   GET    /api/libri/{id}               → un libro specifico
 *   POST   /api/libri                    → aggiunge un libro
 *   PUT    /api/libri/{id}              → modifica un libro
 *   DELETE /api/libri/{id}             → elimina un libro
 */
@Path("/libri")
public class LibreriaREST {

    // -------------------------------------------------------
    // Dati di esempio in memoria
    // -------------------------------------------------------
    private static List<Libro> catalogo = new ArrayList<>();
    private static int prossimoId = 4;

    static {
        catalogo.add(new Libro(1, "Il Nome della Rosa",   "Umberto Eco",        1980));
        catalogo.add(new Libro(2, "I Promessi Sposi",     "Alessandro Manzoni", 1827));
        catalogo.add(new Libro(3, "Se questo è un uomo",  "Primo Levi",         1947));
    }

    // -------------------------------------------------------
    // GET /api/libri  →  tutti i libri (con filtro opzionale per autore)
    // Esempi:
    //   GET /api/libri              → tutti
    //   GET /api/libri?autore=Eco  → solo libri di Eco
    // -------------------------------------------------------
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Libro> getLibri(@QueryParam("autore") String autore) {
        if (autore == null || autore.isEmpty()) {
            return catalogo; // nessun filtro, restituisco tutto
        }
        // Filtro per autore (case-insensitive)
        return catalogo.stream()
                .filter(l -> l.getAutore().toLowerCase().contains(autore.toLowerCase()))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------
    // GET /api/libri/{id}  →  un libro specifico
    // -------------------------------------------------------
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLibroById(@PathParam("id") int id) {
        for (Libro l : catalogo) {
            if (l.getId() == id) {
                return Response.ok(l).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"errore\":\"Libro con ID " + id + " non trovato\"}")
                       .build();
    }

    // -------------------------------------------------------
    // POST /api/libri  →  aggiunge un nuovo libro
    // Body JSON: {"titolo":"1984","autore":"George Orwell","anno":1949}
    // -------------------------------------------------------
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response aggiungiLibro(Libro nuovo) {
        nuovo.setId(prossimoId++);
        catalogo.add(nuovo);
        System.out.println("[REST Libreria] Aggiunto: " + nuovo);
        return Response.status(Response.Status.CREATED).entity(nuovo).build();
    }

    // -------------------------------------------------------
    // PUT /api/libri/{id}  →  aggiorna un libro esistente
    // -------------------------------------------------------
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response aggiornaLibro(@PathParam("id") int id, Libro aggiornato) {
        for (int i = 0; i < catalogo.size(); i++) {
            if (catalogo.get(i).getId() == id) {
                aggiornato.setId(id);
                catalogo.set(i, aggiornato);
                return Response.ok(aggiornato).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"errore\":\"Libro non trovato\"}")
                       .build();
    }

    // -------------------------------------------------------
    // DELETE /api/libri/{id}  →  elimina un libro
    // -------------------------------------------------------
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminaLibro(@PathParam("id") int id) {
        boolean rimosso = catalogo.removeIf(l -> l.getId() == id);
        if (rimosso) {
            return Response.ok("{\"messaggio\":\"Libro eliminato con successo\"}").build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                       .entity("{\"errore\":\"Libro non trovato\"}")
                       .build();
    }
}
