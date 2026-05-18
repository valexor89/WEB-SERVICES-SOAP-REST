package it.scuola;

import com.sun.net.httpserver.HttpServer;
import it.scuola.soap.CalcolatriceSOAP;
import it.scuola.soap.LibreriaSOAP;
import it.scuola.soap.RubricaSOAP;
import jakarta.xml.ws.Endpoint;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale: avvia tutti i servizi SOAP e REST.
 */
public class Main {

    private static final String SOAP_HOST = "http://localhost:8080/";
    private static final String REST_HOST = "http://localhost:8081/";

    private static final List<Endpoint> SOAP_ENDPOINTS = new ArrayList<>();
    private static HttpServer restServer;
    private static boolean stopped = false;

    public static void main(String[] args) {
        try {
            startServices();
            Runtime.getRuntime().addShutdownHook(new Thread(Main::stopServices));
            Thread.currentThread().join();
        } catch (Exception e) {
            System.err.println();
            System.err.println("Errore durante l'avvio dei servizi:");
            System.err.println(e.getMessage());
            stopServices();
            System.exit(1);
        }
    }

    private static void startServices() {
        System.out.println("================================================");
        System.out.println("     Web Services Didattici - Avvio...");
        System.out.println("================================================");

        System.out.println("\nAvvio servizi SOAP sulla porta 8080...");
        publishSoapEndpoint("calcolatrice", new CalcolatriceSOAP(), "CalcolatriceService");
        publishSoapEndpoint("rubrica", new RubricaSOAP(), "RubricaService");
        publishSoapEndpoint("libreria", new LibreriaSOAP(), "LibreriaService");

        System.out.println("\nAvvio servizi REST sulla porta 8081...");
        ResourceConfig config = new ResourceConfig()
                .packages("it.scuola.rest")
                .register(org.glassfish.jersey.jackson.JacksonFeature.class);

        restServer = JdkHttpServerFactory.createHttpServer(URI.create(REST_HOST), config);

        System.out.println("  OK Calcolatrice REST -> " + REST_HOST + "api/calcolatrice/somma?a=5&b=3");
        System.out.println("  OK Rubrica REST      -> " + REST_HOST + "api/contatti");
        System.out.println("  OK Libreria REST     -> " + REST_HOST + "api/libri");

        System.out.println("\n================================================");
        System.out.println("  Tutti i servizi sono attivi!");
        System.out.println();
        System.out.println("  SOAP (Wizdler):");
        System.out.println("    localhost:8080/calcolatrice?wsdl");
        System.out.println("    localhost:8080/rubrica?wsdl");
        System.out.println("    localhost:8080/libreria?wsdl");
        System.out.println();
        System.out.println("  REST (Thunder Client / Browser):");
        System.out.println("    localhost:8081/api/calcolatrice/somma");
        System.out.println("    localhost:8081/api/contatti");
        System.out.println("    localhost:8081/api/libri");
        System.out.println();
        System.out.println("  Premi CTRL+C per fermare il server");
        System.out.println("================================================");
    }

    private static void publishSoapEndpoint(String path, Object service, String name) {
        Endpoint endpoint = Endpoint.publish(SOAP_HOST + path, service);
        SOAP_ENDPOINTS.add(endpoint);
        System.out.println("  OK " + name + " -> " + SOAP_HOST + path + "?wsdl");
    }

    private static synchronized void stopServices() {
        if (stopped) {
            return;
        }
        stopped = true;

        if (restServer != null) {
            restServer.stop(0);
            restServer = null;
        }

        for (Endpoint endpoint : SOAP_ENDPOINTS) {
            if (endpoint.isPublished()) {
                endpoint.stop();
            }
        }
        SOAP_ENDPOINTS.clear();
    }
}
