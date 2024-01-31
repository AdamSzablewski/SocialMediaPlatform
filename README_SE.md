# Sociala medieapplikation
- [English](README.md)
- [Polski](README_PL.md)

## Översikt
Sociala medieapplikationen är en plattform som visar upp min kompetens inom Java-utveckling, Spring-ramverket och mikrotjänstarkitektur. Den ger användare möjlighet att dela video, bild eller textinlägg, engagera sig i innehållet genom att kommentera inlägg och andra kommentarer samt gilla innehåll för att påverka dess popularitet. Applikationen tillåter användare att kommunicera med den inbyggda meddelandefunktionen mellan vänner. Appliktationen använder även Twilio fär att skicka SMS till användare i fall som bortglämt läsenord.


## Använda teknologier
- **Java:** Det primära programmeringsspråket.
- **Spring-framework**
- **Kafka:** Hantering av händelser.
- **Mikrotjänstarkitektur** CQRS arkitektur för att separera read/write funktionalitet för vissa tjänster.
- **Twilio** Använder Twilio för att skicka sms till användare i situtaioner som bortglämt läsenord.
- **AOP (Aspektinriktad programmering):** Använder en kombination av aspektinriktad programmering och anpassade annoteringar för att implementera säkerhet på metodnivå, vilket ger stor flexibilitet och enkelhet vid implementering av auktorisation för att begränsad tillstånd där det behövs.
- **JUnit och Mockito:** Använder JUnit och Mockito för grundlig enhetstestning för att säkerställa mikrotjänsternas pålitlighet.
- **PostgreSQL:** Hanterar data, inklusive inlägg, videodata och bildinformation samt användarinformation, med PostgreSQL som databashanterningssystem.

## Nyckelfunktioner
- **Inlägg:** Användare kan skapa och dela video, bild eller textbaserade inlägg med sitt nätverk.
- **Videostreaming:** Videoinlägg kan streamas av användare.
- **Kommentering och svar:** Användare kan delta i konversationer genom att kommentera inlägg eller svara på andra kommentarer.
- **Gilla-system:** Applikationen inkluderar ett gilla-system för både inlägg och kommentarer, vilket påverkar innehållets popularitet.
- **Vänhantering:** Användare kan ansluta sig till andra genom att skicka och acceptera vänförfrågningar, vilket utvidgar deras sociala nätverk.
- **Meddelandefunktionalitet:** Realtidsmeddelandefunktionalitet gör att användare kan kommunicera med varandra genom text- och bildmeddelanden.
- **Popularitetsmätare:** Populariteten för inlägg och kommentarer bestäms av antalet gillanden och kommentarer, vilket påverkar deras synlighet.
- **Användarregistrering och autentisering:** Användarregistrering och autentisering med JWT (JSON Web Tokens) för säker användarautentisering.
- **Enhetsprovning med JUnit och Mockito:** Varje mikrotjänst genomgår omfattande enhetsprovning med JUnit och Mockito för att säkerställa funktionalitet och identifiera och åtgärda potentiella problem.

## Mikrotjänster

### Video-Service
- Video streaming.
- Lagrar videor.

### Api Gateway
- Ansvarig för ruttning och vägledning till olika mikrotjänster.
- Validerar inkommande förfrågningar genom Security-Service.

### Security-Service
- Hanterar autentisering och auktorisation, utfärdar JWT för säker kommunikation.
- Generar engångslösenord och tar hand om processen vid bortglömt lösenord.

### User-Service
- Hanterar användardata, används för lagring och bearbetning av personlig information för användare.
- Säkrar användarresurser.

### Post-Service-Command
- Ansvarig för "write" funktionaliteten vid inlägg gillningar och kommentarer.
- Hanterar skapande, gillande och kommentering av inlägg.
### Post-Service-Read
- Ansvarig för "read" funktionaliteten vid hämtning av data relaterat till inlägg.
- Använder en egen databas för optimaliserad prestanda vid hämtning av inlägg.
- Ansvarig för skapande av flöden baserade på popularitetsmätare för inlägg.
### Post-Service-ReadModel-Projector
- Ansvarig för hantering av händelser från Kafka.
- Ansvarig för "write" funktionaliteten till Post-service-Read's databas.
### Image-Service
- Tar hand om komprimering av bilder och sparar dem i Postgre SQL databasen.
### Messaging-Service
- Möjliggör användare att skicka meddelanden, inklusive text och bilder, till varandra.
- Möjliggör användare att radera meddelanden hos sig själv eller för alla i konversationen

### Friend-Service
- Tar hand om vänförfrågningar med mera. 

### Notification-Service
- Hanterar händelser från Kafka.
- Skickar SMS via Twilio till användare.

### UniqueID-Service
- Genererar unika identifikations nummer som hjälper att sedan hitta båden bilder och video i datbasen.