package it.scuola.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * ============================================================
 *  CONFIGURAZIONE JAX-RS
 * ============================================================
 *
 * Questa classe dice a Jersey (l'implementazione JAX-RS):
 *   - quale è il prefisso URL di tutti i servizi REST → "/api"
 *   - di trovare automaticamente tutte le classi con @Path
 *
 * @ApplicationPath("/api") → tutti i REST saranno sotto /api/...
 * Non serve aggiungere nulla altro: Jersey scannerizza il classpath.
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    // Vuota: Jersey trova automaticamente le classi @Path
}
