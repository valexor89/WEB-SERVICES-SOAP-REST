package it.scuola;

import com.sun.net.httpserver.HttpServer;
import it.scuola.soap.CalcolatriceSOAP;
import it.scuola.soap.RubricaSOAP;
import it.scuola.soap.LibreriaSOAP;
import it.scuola.rest.RestApplication;

import jakarta.xml.ws.Endpoint;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * ============================================================
 *  CLASSE PRINCIPALE - Avvia tutti i servizi
 * ============================================================
 *
 * Questa classe avvia:
 *
 *  SOAP (porta 8080):
 *    - CalcolatriceService → http://localhost:8080/calcolatrice?wsdl
 *    - RubricaService      → http://localhost:8080/rubrica?wsdl
 *    - LibreriaService     → http://localhost:8080/libreria?wsdl
 *
 *  REST (porta 8081):
 *    - Calcolatrice → http://localhost:8081/api/calcolatrice/somma?a=5&b=3
 *    - Rubrica      → http://localhost:8081/api/contatti
 *    - Libreria     → http://localhost:8081/api/libri
 *
 * Per avviare:
 *   mvn package
 *   java -jar target/webservices-scuola-1.0.jar
 */
public class Main {

    // Porte dei server
    private static final String SOAP_HOST = "http://localhost:8080/";
    private static final String REST_HOST = "http://localhost:8081/";

    public static void main(String[] args) throws Exception {

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║     Web Services Didattici - Avvio...        ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        // ============================================================
        //  AVVIO SERVIZI SOAP (porta 8080)
        //  Endpoint.publish() pubblica il servizio all'URL indicato
        //  e genera automaticamente il WSDL (aggiungendo ?wsdl all'URL)
        // ============================================================
        System.out.println("\n▶ Avvio servizi SOAP sulla porta 8080...");

        Endpoint.publish(SOAP_HOST + "calcolatrice", new CalcolatriceSOAP());
        System.out.println("  ✔ CalcolatriceService → " + SOAP_HOST + "calcolatrice?wsdl");

        Endpoint.publish(SOAP_HOST + "rubrica", new RubricaSOAP());
        System.out.println("  ✔ RubricaService      → " + SOAP_HOST + "rubrica?wsdl");

        Endpoint.publish(SOAP_HOST + "libreria", new LibreriaSOAP());
        System.out.println("  ✔ LibreriaService     → " + SOAP_HOST + "libreria?wsdl");

        // ============================================================
        //  AVVIO SERVIZI REST (porta 8081)
        //  Jersey scansiona il pacchetto e trova tutte le classi @Path
        // ============================================================
        System.out.println("\n▶ Avvio servizi REST sulla porta 8081...");

        ResourceConfig config = new ResourceConfig()
                .packages("it.scuola.rest") // Jersey trova automaticamente le classi @Path
                .register(org.glassfish.jersey.jackson.JacksonFeature.class); // serializzazione JSON

        JdkHttpServerFactory.createHttpServer(URI.create(REST_HOST), config);

        System.out.println("  ✔ Calcolatrice REST → " + REST_HOST + "api/calcolatrice/somma?a=5&b=3");
        System.out.println("  ✔ Rubrica REST      → " + REST_HOST + "api/contatti");
        System.out.println("  ✔ Libreria REST     → " + REST_HOST + "api/libri");

        // ============================================================
        //  RIEPILOGO
        // ============================================================
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║  Tutti i servizi sono attivi!                ║");
        System.out.println("║                                              ║");
        System.out.println("║  SOAP (Wizdler):                             ║");
        System.out.println("║    localhost:8080/calcolatrice?wsdl          ║");
        System.out.println("║    localhost:8080/rubrica?wsdl               ║");
        System.out.println("║    localhost:8080/libreria?wsdl              ║");
        System.out.println("║                                              ║");
        System.out.println("║  REST (Thunder Client / Browser):            ║");
        System.out.println("║    localhost:8081/api/calcolatrice/somma     ║");
        System.out.println("║    localhost:8081/api/contatti               ║");
        System.out.println("║    localhost:8081/api/libri                  ║");
        System.out.println("║                                              ║");
        System.out.println("║  Premi CTRL+C per fermare il server          ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        // Tieni il programma in esecuzione
        Thread.currentThread().join();
    }
}
