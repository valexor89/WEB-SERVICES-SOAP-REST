# 🌐 Web Services Didattici — SOAP + REST in Java

Progetto scolastico che mostra la differenza tra **SOAP** (JAX-WS) e **REST** (JAX-RS/Jersey)
usando tre esempi concreti: Calcolatrice, Rubrica, Libreria.

---

## 📋 Requisiti

| Strumento | Versione minima | Download |
|-----------|----------------|----------|
| Java JDK  | 11 o superiore | https://adoptium.net |
| Maven     | 3.6+           | https://maven.apache.org |
| VS Code   | qualsiasi      | https://code.visualstudio.com |
| Wizdler (Chrome) | — | Chrome Web Store → cerca "Wizdler" |
| Thunder Client (VS Code) | — | VS Code → Estensioni → cerca "Thunder Client" |

Verifica installazione:
```bash
java -version
mvn -version
```

---

## 🚀 Come avviare il progetto

### 1. Apri il progetto in VS Code
```
File → Apri Cartella → seleziona la cartella "webservices-scuola"
```

### 2. Apri il terminale in VS Code
```
Terminale → Nuovo Terminale  (oppure CTRL+`)
```

### 3. Compila il progetto
```bash
mvn package
```
> Prima volta: Maven scarica le dipendenze (~2 min). Le volte successive è istantaneo.

### 4. Avvia tutti i servizi
```bash
java -jar target/webservices-scuola-1.0.jar
```

Dovresti vedere nel terminale:
```
✔ CalcolatriceService → http://localhost:8080/calcolatrice?wsdl
✔ RubricaService      → http://localhost:8080/rubrica?wsdl
✔ LibreriaService     → http://localhost:8080/libreria?wsdl
✔ Calcolatrice REST   → http://localhost:8081/api/calcolatrice/somma?a=5&b=3
✔ Rubrica REST        → http://localhost:8081/api/contatti
✔ Libreria REST       → http://localhost:8081/api/libri
```

### 5. Per fermare il server
Premi **CTRL+C** nel terminale.

---

## 🔵 Testare i servizi SOAP con Wizdler

Wizdler è un'estensione Chrome che legge il WSDL e genera automaticamente i form per chiamare il servizio.

EDIT: Widzler non sembra funzionare oppure essere supportata.
Usare al suo posto l'estensione Boomerang

### Come usarlo:
1. Apri **Google Chrome**
2. Vai all'URL del WSDL, ad esempio:
   ```
   http://localhost:8080/calcolatrice?wsdl
   ```
3. Clicca sull'icona di **Wizdler** in alto a destra
4. Vedrai l'elenco delle operazioni disponibili (somma, sottrazione, ecc.)
5. Clicca su un'operazione → compila i campi → clicca "Go"
6. Wizdler invia la richiesta SOAP e mostra la risposta XML

### Servizi SOAP disponibili:
| Servizio | URL WSDL |
|----------|----------|
| Calcolatrice | http://localhost:8080/calcolatrice?wsdl |
| Rubrica | http://localhost:8080/rubrica?wsdl |
| Libreria | http://localhost:8080/libreria?wsdl |

---

## 🟢 Testare i servizi REST con Thunder Client (VS Code)

Thunder Client è un'estensione VS Code simile a Postman, ma integrata nell'IDE.

### Come usarlo:
1. In VS Code, clicca sull'icona di Thunder Client nella barra laterale (⚡)
2. Clicca su **"New Request"**
3. Scegli il metodo HTTP (GET, POST, PUT, DELETE)
4. Inserisci l'URL
5. Per POST/PUT: vai su tab **"Body"** → seleziona **"JSON"** → incolla il JSON
6. Clicca **"Send"**

---

### 🔢 Calcolatrice REST

| Operazione | Metodo | URL |
|-----------|--------|-----|
| Somma | GET | `http://localhost:8081/api/calcolatrice/somma?a=10&b=5` |
| Sottrazione | GET | `http://localhost:8081/api/calcolatrice/sottrazione?a=10&b=5` |
| Moltiplicazione | GET | `http://localhost:8081/api/calcolatrice/moltiplicazione?a=4&b=3` |
| Divisione | GET | `http://localhost:8081/api/calcolatrice/divisione?a=10&b=2` |
| Divisione per zero | GET | `http://localhost:8081/api/calcolatrice/divisione?a=10&b=0` |

---

### 📞 Rubrica REST

| Operazione | Metodo | URL | Body |
|-----------|--------|-----|------|
| Tutti i contatti | GET | `http://localhost:8081/api/contatti` | — |
| Un contatto (ID=1) | GET | `http://localhost:8081/api/contatti/1` | — |
| Contatto non esistente | GET | `http://localhost:8081/api/contatti/99` | — |
| Aggiungi contatto | POST | `http://localhost:8081/api/contatti` | vedi sotto |
| Aggiorna contatto | PUT | `http://localhost:8081/api/contatti/1` | vedi sotto |
| Elimina contatto | DELETE | `http://localhost:8081/api/contatti/3` | — |

**Body per POST (aggiungi):**
```json
{
  "nome": "Paolo Neri",
  "telefono": "345-4444444",
  "email": "paolo@email.it"
}
```

**Body per PUT (aggiorna):**
```json
{
  "nome": "Mario Rossi Aggiornato",
  "telefono": "333-9999999",
  "email": "mario.nuovo@email.it"
}
```

---

### 📚 Libreria REST

| Operazione | Metodo | URL | Body |
|-----------|--------|-----|------|
| Tutti i libri | GET | `http://localhost:8081/api/libri` | — |
| Filtra per autore | GET | `http://localhost:8081/api/libri?autore=Eco` | — |
| Un libro (ID=2) | GET | `http://localhost:8081/api/libri/2` | — |
| Aggiungi libro | POST | `http://localhost:8081/api/libri` | vedi sotto |
| Aggiorna libro | PUT | `http://localhost:8081/api/libri/1` | vedi sotto |
| Elimina libro | DELETE | `http://localhost:8081/api/libri/3` | — |

**Body per POST:**
```json
{
  "titolo": "1984",
  "autore": "George Orwell",
  "anno": 1949
}
```

---

## 📐 Struttura del progetto

```
webservices-scuola/
├── pom.xml                          ← dipendenze Maven
└── src/main/java/it/scuola/
    ├── Main.java                    ← avvia tutti i servizi
    ├── model/
    │   ├── Contatto.java            ← dati rubrica
    │   └── Libro.java               ← dati libreria
    ├── soap/
    │   ├── CalcolatriceSOAP.java    ← servizio SOAP calcolatrice
    │   ├── RubricaSOAP.java         ← servizio SOAP rubrica
    │   └── LibreriaSOAP.java        ← servizio SOAP libreria
    └── rest/
        ├── RestApplication.java     ← configurazione Jersey
        ├── CalcolatriceREST.java    ← servizio REST calcolatrice
        ├── RubricaREST.java         ← servizio REST rubrica
        └── LibreriaREST.java        ← servizio REST libreria
```

---

## 🧠 Concetti chiave

### Differenze SOAP vs REST

| | SOAP | REST |
|--|------|------|
| **Protocollo** | XML su HTTP | HTTP nativo |
| **Formato dati** | XML (SOAP Envelope) | JSON (tipicamente) |
| **Contratto** | WSDL (formale) | Nessuno (o OpenAPI) |
| **Operazioni** | Definite nel WSDL | Metodi HTTP (GET/POST/PUT/DELETE) |
| **URL** | Sempre lo stesso endpoint | URL diversa per ogni risorsa |
| **Uso tipico** | Sistemi enterprise, banche | Web, mobile, microservizi |

### Annotazioni JAX-WS (SOAP)
- `@WebService` → questa classe è un servizio SOAP
- `@WebMethod` → questo metodo è un'operazione del servizio
- `@WebParam` → nome del parametro nel messaggio XML

### Annotazioni JAX-RS (REST)
- `@Path` → URL del servizio o dell'operazione
- `@GET / @POST / @PUT / @DELETE` → metodo HTTP
- `@PathParam` → parametro nell'URL (es. `/contatti/{id}`)
- `@QueryParam` → parametro nella query string (es. `?autore=Eco`)
- `@Produces` → formato della risposta (JSON, testo...)
- `@Consumes` → formato del body in arrivo (JSON...)
