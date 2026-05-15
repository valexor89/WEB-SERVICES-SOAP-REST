package it.scuola.soap;

import it.scuola.model.Libro;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  SERVIZIO SOAP - Libreria
 * ============================================================
 *
 * Questo servizio gestisce un catalogo di libri.
 * Dimostra come SOAP può lavorare con oggetti complessi (Libro).
 *
 * Operazioni:
 *   - getTuttiLibri()           → catalogo completo
 *   - getLibroById(id)          → un libro specifico
 *   - cercaPerAutore(autore)    → libri di un autore
 *   - aggiungiLibro(...)        → nuovo libro
 *   - aggiornaAnno(id, anno)    → modifica l'anno di un libro
 */
@WebService(serviceName = "LibreriaService")
public class LibreriaSOAP {

    // -------------------------------------------------------
    // Dati di esempio in memoria
    // -------------------------------------------------------
    private static List<Libro> catalogo = new ArrayList<>();
    private static int prossimoId = 4;

    static {
        catalogo.add(new Libro(1, "Il Nome della Rosa",        "Umberto Eco",    1980));
        catalogo.add(new Libro(2, "I Promessi Sposi",          "Alessandro Manzoni", 1827));
        catalogo.add(new Libro(3, "Se questo è un uomo",       "Primo Levi",     1947));
    }

    // -------------------------------------------------------
    // OPERAZIONI DEL SERVIZIO
    // -------------------------------------------------------

    /**
     * Restituisce tutti i libri del catalogo.
     */
    @WebMethod(operationName = "getTuttiLibri")
    public List<Libro> getTuttiLibri() {
        return catalogo;
    }

    /**
     * Cerca un libro per ID.
     * Restituisce null se non trovato.
     */
    @WebMethod(operationName = "getLibroById")
    public Libro getLibroById(
            @WebParam(name = "id") int id) {
        for (Libro l : catalogo) {
            if (l.getId() == id) return l;
        }
        return null; // non trovato
    }

    /**
     * Cerca libri per autore (ricerca parziale).
     */
    @WebMethod(operationName = "cercaPerAutore")
    public List<Libro> cercaPerAutore(
            @WebParam(name = "autore") String autore) {
        List<Libro> risultati = new ArrayList<>();
        for (Libro l : catalogo) {
            if (l.getAutore().toLowerCase().contains(autore.toLowerCase())) {
                risultati.add(l);
            }
        }
        return risultati;
    }

    /**
     * Aggiunge un libro al catalogo.
     * Restituisce l'ID assegnato.
     */
    @WebMethod(operationName = "aggiungiLibro")
    public int aggiungiLibro(
            @WebParam(name = "titolo")  String titolo,
            @WebParam(name = "autore")  String autore,
            @WebParam(name = "anno")    int anno) {

        int nuovoId = prossimoId++;
        catalogo.add(new Libro(nuovoId, titolo, autore, anno));
        System.out.println("[SOAP Libreria] Aggiunto libro: " + titolo + " (ID=" + nuovoId + ")");
        return nuovoId;
    }

    /**
     * Aggiorna l'anno di pubblicazione di un libro.
     */
    @WebMethod(operationName = "aggiornaAnno")
    public boolean aggiornaAnno(
            @WebParam(name = "id")    int id,
            @WebParam(name = "anno")  int anno) {
        for (Libro l : catalogo) {
            if (l.getId() == id) {
                l.setAnno(anno);
                return true;
            }
        }
        return false;
    }
}
