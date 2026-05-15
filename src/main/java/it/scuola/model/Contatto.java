package it.scuola.model;

/**
 * MODELLO - Contatto
 * Questa classe rappresenta una persona nella rubrica.
 * Viene usata sia dai servizi SOAP che dai servizi REST.
 */
public class Contatto {

    private int id;
    private String nome;
    private String telefono;
    private String email;

    // Costruttore vuoto (obbligatorio per la serializzazione)
    public Contatto() {}

    public Contatto(int id, String nome, String telefono, String email) {
        this.id = id;
        this.nome = nome;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId()              { return id; }
    public void setId(int id)       { this.id = id; }

    public String getNome()               { return nome; }
    public void setNome(String nome)      { this.nome = nome; }

    public String getTelefono()                   { return telefono; }
    public void setTelefono(String telefono)      { this.telefono = telefono; }

    public String getEmail()              { return email; }
    public void setEmail(String email)    { this.email = email; }

    @Override
    public String toString() {
        return "Contatto{id=" + id + ", nome='" + nome + "', telefono='" + telefono + "', email='" + email + "'}";
    }
}
