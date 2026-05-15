package it.scuola.model;

/**
 * MODELLO - Libro
 * Questa classe rappresenta un libro nella nostra libreria.
 * Viene usata sia dai servizi SOAP che dai servizi REST.
 */
public class Libro {

    private int id;
    private String titolo;
    private String autore;
    private int anno;

    // Costruttore vuoto (obbligatorio per JAX-WS e JAX-RS / serializzazione JSON)
    public Libro() {}

    // Costruttore con tutti i campi (comodo per creare oggetti velocemente)
    public Libro(int id, String titolo, String autore, int anno) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.anno = anno;
    }

    // Getter e Setter
    public int getId()              { return id; }
    public void setId(int id)       { this.id = id; }

    public String getTitolo()               { return titolo; }
    public void setTitolo(String titolo)    { this.titolo = titolo; }

    public String getAutore()               { return autore; }
    public void setAutore(String autore)    { this.autore = autore; }

    public int getAnno()            { return anno; }
    public void setAnno(int anno)   { this.anno = anno; }

    @Override
    public String toString() {
        return "Libro{id=" + id + ", titolo='" + titolo + "', autore='" + autore + "', anno=" + anno + "}";
    }
}
