package it.scuola.soap;

import it.scuola.model.Contatto;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  SERVIZIO SOAP - Rubrica
 * ============================================================
 *
 * Questo servizio gestisce una rubrica di contatti.
 * I dati sono tenuti in memoria (lista statica) per semplicità.
 *
 * Operazioni disponibili:
 *   - getTuttiContatti()       → legge tutta la rubrica
 *   - getContattoPerNome(nome) → cerca un contatto per nome
 *   - aggiungiContatto(...)    → aggiunge un nuovo contatto
 *   - eliminaContatto(id)      → elimina un contatto
 */
@WebService(serviceName = "RubricaService")
public class RubricaSOAP {

    // -------------------------------------------------------
    // Dati di esempio in memoria (simulano un database)
    // -------------------------------------------------------
    private static List<Contatto> rubrica = new ArrayList<>();
    private static int prossimoId = 4; // counter per gli ID

    static {
        // Dati pre-caricati all'avvio
        rubrica.add(new Contatto(1, "Mario Rossi",    "333-1111111", "mario@email.it"));
        rubrica.add(new Contatto(2, "Luca Bianchi",   "347-2222222", "luca@email.it"));
        rubrica.add(new Contatto(3, "Anna Verdi",     "320-3333333", "anna@email.it"));
    }

    // -------------------------------------------------------
    // OPERAZIONI DEL SERVIZIO
    // -------------------------------------------------------

    /**
     * Restituisce tutti i contatti nella rubrica.
     */
    @WebMethod(operationName = "getTuttiContatti")
    public List<Contatto> getTuttiContatti() {
        return rubrica;
    }

    /**
     * Cerca un contatto per nome (ricerca parziale, case-insensitive).
     * Es: cerca("mario") trova "Mario Rossi"
     */
    @WebMethod(operationName = "cercaPerNome")
    public List<Contatto> cercaPerNome(
            @WebParam(name = "nome") String nome) {
        List<Contatto> risultati = new ArrayList<>();
        for (Contatto c : rubrica) {
            if (c.getNome().toLowerCase().contains(nome.toLowerCase())) {
                risultati.add(c);
            }
        }
        return risultati;
    }

    /**
     * Aggiunge un nuovo contatto alla rubrica.
     * Restituisce l'ID assegnato al nuovo contatto.
     */
    @WebMethod(operationName = "aggiungiContatto")
    public int aggiungiContatto(
            @WebParam(name = "nome")      String nome,
            @WebParam(name = "telefono")  String telefono,
            @WebParam(name = "email")     String email) {

        int nuovoId = prossimoId++;
        rubrica.add(new Contatto(nuovoId, nome, telefono, email));
        System.out.println("[SOAP Rubrica] Aggiunto contatto: " + nome + " (ID=" + nuovoId + ")");
        return nuovoId;
    }

    /**
     * Elimina un contatto dall'ID.
     * Restituisce true se eliminato, false se non trovato.
     */
    @WebMethod(operationName = "eliminaContatto")
    public boolean eliminaContatto(
            @WebParam(name = "id") int id) {
        return rubrica.removeIf(c -> c.getId() == id);
    }
}
