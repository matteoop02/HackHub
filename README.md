# HackHub

Backend Spring Boot per la creazione e la gestione di hackathon, sviluppato come progetto per il corso di Ingegneria del Software dell'Universita di Camerino (Unicam), anno accademico 2025/2026

![Java 17](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-6DB33F?logo=springboot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-Build-02303A?logo=gradle&logoColor=white)
![H2](https://img.shields.io/badge/Database-H2-1E88E5)
![Swagger](https://img.shields.io/badge/API-Swagger-85EA2D?logo=swagger&logoColor=black)
![License](https://img.shields.io/badge/License-MIT-blue)

## Autori

- Matteo Fagnani
- Andrea Pistolesi
- Marco Solustri

## Panoramica

HackHub gestisce il ciclo di vita di un hackathon dalla pubblicazione dell'evento fino alla valutazione finale dei progetti e alla gestione del premio.

### Funzionalità principali

- Autenticazione e autorizzazione tramite JWT
- Creazione e gestione degli hackathon
- Gestione di team, membri e inviti
- Invio e aggiornamento delle sottomissioni
- Valutazione dei progetti da parte dei giudici
- Gestione di slot e call di supporto con i mentori
- Segnalazione di violazioni e supporto ai flussi organizzativi
- Integrazione con Swagger UI e H2 Console per testing e debug
- Modellazione UML e documentazione con Visual Paradigm e PlantUML

## Obiettivi del progetto

- Semplificare l'organizzazione di hackathon
- Centralizzare utenti, team, progetti e staff in un'unica piattaforma
- Automatizzare alcune fasi operative del processo organizzativo
- Esporre API documentate e facilmente testabili
- Affiancare allo sviluppo software una documentazione di analisi e progettazione

## Stack tecnologico

| Area | Tecnologie |
| --- | --- |
| Linguaggio | Java 17 |
| Framework | Spring Boot 3, Spring Web |
| Sicurezza | Spring Security, JWT, OAuth2 Resource Server |
| Persistenza | Spring Data JPA, Hibernate |
| Database | H2 |
| Documentazione API | Swagger / OpenAPI |
| Build | Gradle |
| Supporto al codice | Lombok, MapStruct |
| Modellazione | Visual Paradigm, PlantUML |

## Struttura del repository

```text
HackHub/
├── code/
│   ├── src/
│   ├── build.gradle
│   ├── gradlew
│   └── gradlew.bat
├── model/
│   └── visual-paradigm/
├── README.md
└── LICENSE
```

- `code/` contiene l'applicazione backend Spring Boot
- `model/` contiene la modellazione UML e la documentazione di progetto

## Avvio rapido

### Requisiti

- Java 17
- Gradle Wrapper già incluso nel progetto

### Avvio con Gradle

```powershell
cd code
.\gradlew.bat bootRun --no-daemon --no-configuration-cache --console=plain
```

### Avvio tramite jar

```powershell
cd code
.\gradlew.bat bootJar --no-daemon --no-configuration-cache --console=plain
java -jar .\build\libs\HackHub-1.0-SNAPSHOT.jar
```

### Documentazione e strumenti locali

- Swagger UI: [http://127.0.0.1:8080/swagger-ui.html](http://127.0.0.1:8080/swagger-ui.html)
- Swagger UI alternativa: [http://127.0.0.1:8080/swagger-ui/index.html](http://127.0.0.1:8080/swagger-ui/index.html)
- OpenAPI JSON: [http://127.0.0.1:8080/api-docs](http://127.0.0.1:8080/api-docs)
- H2 Console: [http://127.0.0.1:8080/h2-console](http://127.0.0.1:8080/h2-console)

### Configurazione database H2

| Campo | Valore |
| --- | --- |
| JDBC URL | `jdbc:h2:mem:hackhub` |
| Username | `sa` |
| Password | vuota |

## Autenticazione e ruoli

Le API protette usano JWT. Il token viene restituito dagli endpoint di registrazione e login e può essere riutilizzato nelle chiamate successive tramite il pulsante `Authorize` di Swagger.

### Ruoli supportati

- `ORGANIZER` / `ORGANIZZATORE`
- `JUDGE` / `GIUDICE`
- `MENTOR`
- `REGISTERED_USER` / `REGISTERED` / `USER`

## Flusso base di utilizzo

1. Registrare gli utenti necessari tramite `/api/auth/register`
2. Eseguire il login con `/api/auth/login`
3. Creare un hackathon come organizzatore
4. Assegnare giudici e mentori all'hackathon
5. Creare un team e iscriverlo all'hackathon
6. Inviare una sottomissione
7. Valutare la sottomissione come giudice
8. Proclamare il team vincitore e gestire il premio

## API disponibili

Di seguito sono riportate le chiamate esposte dal backend, organizzate per area funzionale.

### Autenticazione - `/api/auth`

| Metodo | Endpoint | Auth | Ruolo | Descrizione |
| --- | --- | --- | --- | --- |
| `POST` | `/api/auth/register` | No | Pubblico | Registra un nuovo utente e restituisce il token JWT |
| `POST` | `/api/auth/login` | No | Pubblico | Autentica un utente esistente e restituisce il token JWT |
| `GET` | `/api/auth/supported-roles` | No | Pubblico | Restituisce ruoli e alias accettati in registrazione |
| `GET` | `/api/auth/me` | Sì | Utente autenticato | Restituisce il profilo dell'utente autenticato |

### Hackathon - `/api/hackathon`

| Metodo | Endpoint | Auth | Ruolo | Descrizione |
| --- | --- | --- | --- | --- |
| `GET` | `/api/hackathon/public` | No / opzionale | Pubblico | Restituisce la lista degli hackathon visibili |
| `GET` | `/api/hackathon/{id}` | No / opzionale | Pubblico o autenticato | Restituisce il dettaglio di un hackathon |
| `POST` | `/api/hackathon/organizzatore/create` | Sì | Organizzatore | Crea un nuovo hackathon |
| `POST` | `/api/hackathon/{id}/start` | Sì | Organizzatore assegnato | Avvia l'hackathon |
| `POST` | `/api/hackathon/{id}/closeSubscriptions` | Sì | Organizzatore assegnato | Chiude le iscrizioni |
| `POST` | `/api/hackathon/{id}/winner` | Sì | Organizzatore assegnato | Proclama il team vincitore |
| `POST` | `/api/hackathon/{id}/pay-prize` | Sì | Organizzatore assegnato | Avvia il pagamento del premio |
| `GET` | `/api/hackathon/{id}/payment-status` | Sì | Organizzatore assegnato | Verifica lo stato del pagamento |
| `POST` | `/api/hackathon/{id}/judge` | Sì | Organizzatore assegnato | Assegna un giudice all'hackathon |
| `DELETE` | `/api/hackathon/{id}/judge/{judgeId}` | Sì | Organizzatore assegnato | Rimuove il giudice assegnato |
| `POST` | `/api/hackathon/{id}/mentors` | Sì | Organizzatore assegnato | Assegna uno o più mentori |
| `DELETE` | `/api/hackathon/{id}/mentors/{mentorId}` | Sì | Organizzatore assegnato | Rimuove un mentore |

### Team - `/api/team`

| Metodo | Endpoint | Auth | Ruolo | Descrizione |
| --- | --- | --- | --- | --- |
| `POST` | `/api/team/utente/create` | Sì | Utente autenticato | Crea un nuovo team |
| `DELETE` | `/api/team/members/me` | Sì | Membro del team | Permette all'utente di lasciare il proprio team |
| `GET` | `/api/team/members/me` | Sì | Membro del team | Restituisce i membri del team corrente |
| `DELETE` | `/api/team/members/{memberId}` | Sì | Leader del team | Rimuove un membro dal team |
| `POST` | `/api/team/{teamId}/subscribe/{hackathonId}` | Sì | Leader del team | Iscrive un team a un hackathon |
| `POST` | `/api/team/{teamId}/violation/report` | Sì | Mentore | Segnala una violazione del team |

### Inviti - `/api/invites`

| Metodo | Endpoint | Auth | Ruolo | Descrizione |
| --- | --- | --- | --- | --- |
| `POST` | `/api/invites/outside/rejectOutsideInvite` | No | Pubblico con token | Rifiuta un invito esterno |
| `POST` | `/api/invites/outside/accept` | No | Pubblico con token | Accetta un invito esterno |
| `POST` | `/api/invites/leaderDelTeam/inviteToTeam` | Sì | Leader del team | Invita un utente registrato a unirsi al team |
| `POST` | `/api/invites/inviteManage/acceptTeamInvite` | Sì | Destinatario invitato | Accetta un invito a entrare in un team |
| `POST` | `/api/invites/inviteManage/rejectTeamInvite` | Sì | Destinatario invitato | Rifiuta un invito a entrare in un team |
| `POST` | `/api/invites/outside/create` | Sì | Utente autenticato | Crea e invia un invito esterno via email |

### Calendar - `/api/calendar`

| Metodo | Endpoint | Auth | Ruolo | Descrizione |
| --- | --- | --- | --- | --- |
| `POST` | `/api/calendar/slots` | Sì | Mentore | Crea uno slot di disponibilità |
| `POST` | `/api/calendar/support-calls` | Sì | Mentore assegnato | Propone una call di supporto a un team |
| `POST` | `/api/calendar/slots/{slotId}/book` | Sì | Leader del team | Prenota uno slot di supporto |
| `GET` | `/api/calendar/support-calls/me` | Sì | Mentore | Restituisce richieste e prenotazioni associate |
| `DELETE` | `/api/calendar/support-calls/{supportCallId}` | Sì | Mentore | Annulla una richiesta o prenotazione di supporto |

### Sottomissioni - `/api/submission`

| Metodo | Endpoint | Auth | Ruolo | Descrizione |
| --- | --- | --- | --- | --- |
| `GET` | `/api/submission/staff/listByStaffMember` | Sì | Giudice o mentore | Restituisce le sottomissioni associate allo staff autenticato |
| `POST` | `/api/submission/team/send` | Sì | Leader del team | Invia la sottomissione del team |
| `POST` | `/api/submission/team/update` | Sì | Team | Aggiorna una sottomissione esistente |
| `POST` | `/api/submission/staff/evaluate` | Sì | Giudice | Inserisce una valutazione |
| `PUT` | `/api/submission/staff/evaluate` | Sì | Giudice | Modifica una valutazione esistente |

## Note operative

- Molti endpoint richiedono autenticazione JWT e dipendono dal ruolo dell'utente autenticato
- Alcune operazioni richiedono prerequisiti di stato, ad esempio hackathon in iscrizione, in corso o in valutazione
- La fase di valutazione dipende anche dalle date configurate per l'hackathon
- Il database usato in sviluppo è H2 in-memory, quindi i dati vengono persi allo spegnimento dell'applicazione
