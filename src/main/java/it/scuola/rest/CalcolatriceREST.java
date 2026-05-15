package it.scuola.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * ============================================================
 *  SERVIZIO REST - Calcolatrice
 * ============================================================
 *
 * REST (Representational State Transfer) usa le URL e i metodi
 * HTTP (GET, POST, PUT, DELETE) per comunicare.
 * Le risposte sono tipicamente in formato JSON o testo.
 *
 * @Path        → definisce il percorso URL base del servizio
 * @GET         → risponde alle richieste HTTP GET
 * @POST        → risponde alle richieste HTTP POST
 * @QueryParam  → legge i parametri dalla URL (?a=5&b=3)
 * @Produces    → specifica il formato della risposta (JSON, testo)
 *
 * URL BASE: http://localhost:8081/api/calcolatrice
 *
 * Esempi di chiamate:
 *   GET  /api/calcolatrice/somma?a=10&b=5         → 15
 *   GET  /api/calcolatrice/sottrazione?a=10&b=5   → 5
 *   GET  /api/calcolatrice/moltiplicazione?a=4&b=3 → 12
 *   GET  /api/calcolatrice/divisione?a=10&b=2     → 5.0
 */
@Path("/calcolatrice")
public class CalcolatriceREST {

    /**
     * Somma due numeri.
     * Esempio: GET /api/calcolatrice/somma?a=10&b=5
     */
    @GET
    @Path("/somma")
    @Produces(MediaType.APPLICATION_JSON)
    public Response somma(
            @QueryParam("a") int a,
            @QueryParam("b") int b) {

        int risultato = a + b;
        // Restituiamo un oggetto JSON semplice
        String json = "{\"operazione\":\"somma\", \"a\":" + a + ", \"b\":" + b + ", \"risultato\":" + risultato + "}";
        return Response.ok(json).build();
    }

    /**
     * Sottrazione.
     * Esempio: GET /api/calcolatrice/sottrazione?a=10&b=5
     */
    @GET
    @Path("/sottrazione")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sottrazione(
            @QueryParam("a") int a,
            @QueryParam("b") int b) {

        int risultato = a - b;
        String json = "{\"operazione\":\"sottrazione\", \"a\":" + a + ", \"b\":" + b + ", \"risultato\":" + risultato + "}";
        return Response.ok(json).build();
    }

    /**
     * Moltiplicazione.
     * Esempio: GET /api/calcolatrice/moltiplicazione?a=4&b=3
     */
    @GET
    @Path("/moltiplicazione")
    @Produces(MediaType.APPLICATION_JSON)
    public Response moltiplicazione(
            @QueryParam("a") int a,
            @QueryParam("b") int b) {

        int risultato = a * b;
        String json = "{\"operazione\":\"moltiplicazione\", \"a\":" + a + ", \"b\":" + b + ", \"risultato\":" + risultato + "}";
        return Response.ok(json).build();
    }

    /**
     * Divisione. Gestisce la divisione per zero.
     * Esempio: GET /api/calcolatrice/divisione?a=10&b=2
     */
    @GET
    @Path("/divisione")
    @Produces(MediaType.APPLICATION_JSON)
    public Response divisione(
            @QueryParam("a") double a,
            @QueryParam("b") double b) {

        // Divisione per zero → HTTP 400 Bad Request
        if (b == 0) {
            String errore = "{\"errore\":\"Divisione per zero non consentita!\"}";
            return Response.status(Response.Status.BAD_REQUEST).entity(errore).build();
        }

        double risultato = a / b;
        String json = "{\"operazione\":\"divisione\", \"a\":" + a + ", \"b\":" + b + ", \"risultato\":" + risultato + "}";
        return Response.ok(json).build();
    }
}
