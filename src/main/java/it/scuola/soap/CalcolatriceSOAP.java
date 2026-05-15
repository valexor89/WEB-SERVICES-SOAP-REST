package it.scuola.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * ============================================================
 *  SERVIZIO SOAP - Calcolatrice
 * ============================================================
 *
 * SOAP (Simple Object Access Protocol) usa messaggi XML per
 * comunicare. Ogni metodo esposto diventa un'operazione del
 * servizio, descritta in modo formale nel file WSDL.
 *
 * @WebService  → dice a JAX-WS che questa classe è un servizio SOAP
 * @WebMethod   → espone il metodo come operazione del servizio
 * @WebParam    → dà un nome ai parametri nel messaggio XML
 */
@WebService(serviceName = "CalcolatriceService")
public class CalcolatriceSOAP {

    /**
     * Somma due numeri interi.
     * Nel WSDL questa operazione si chiamerà "somma".
     */
    @WebMethod(operationName = "somma")
    public int somma(
            @WebParam(name = "a") int a,
            @WebParam(name = "b") int b) {
        return a + b;
    }

    /**
     * Sottrae b da a.
     */
    @WebMethod(operationName = "sottrazione")
    public int sottrazione(
            @WebParam(name = "a") int a,
            @WebParam(name = "b") int b) {
        return a - b;
    }

    /**
     * Moltiplica due numeri.
     */
    @WebMethod(operationName = "moltiplicazione")
    public int moltiplicazione(
            @WebParam(name = "a") int a,
            @WebParam(name = "b") int b) {
        return a * b;
    }

    /**
     * Divide a per b.
     * Attenzione: se b è 0 restituisce un messaggio di errore.
     */
    @WebMethod(operationName = "divisione")
    public String divisione(
            @WebParam(name = "a") double a,
            @WebParam(name = "b") double b) {
        if (b == 0) {
            return "Errore: divisione per zero non consentita!";
        }
        return String.valueOf(a / b);
    }
}
