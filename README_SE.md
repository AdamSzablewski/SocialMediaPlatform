# Sociala medieapplikation
- [English](README.md)
- [Polski](README_PL.md)

## Översikt
Sociala medieapplikationen är en plattform som visar upp min kompetens inom Java-utveckling, Spring-ramverket och mikrotjänstarkitektur. Den ger användare möjlighet att dela video, bild- eller textinlägg, engagera sig i innehållet genom att kommentera inlägg och andra kommentarer samt gilla innehåll för att påverka dess popularitet. Applikationen tillåter användare att kommunicera med den inbyggda meddelandefunktionen mellan vänner.

## Nyckelfunktioner
- **Inläggskreation:** Användare kan skapa och dela video, bild eller textbaserade inlägg med sitt nätverk.
- **Videostreaming:** Videoinlägg kan streamas av användare.
- **Kommentering och svar:** Användare kan delta i konversationer genom att kommentera inlägg eller svara på andra kommentarer.
- **Gilla-system:** Applikationen inkluderar ett gilla-system för både inlägg och kommentarer, vilket påverkar innehållets popularitet.
- **Vänhantering:** Användare kan ansluta sig till andra genom att skicka och acceptera vänförfrågningar, vilket utvidgar deras sociala nätverk.
- **Meddelandefunktionalitet:** Realtidsmeddelandefunktionalitet gör att användare kan kommunicera med varandra genom text- och bildmeddelanden.
- **Popularitetsmätare:** Populariteten för inlägg och kommentarer bestäms av antalet gillanden och kommentarer, vilket påverkar deras synlighet.
- **Användarregistrering och autentisering:** Användarregistrering och autentisering med JWT (JSON Web Tokens) för säker användarautentisering.
- **Enhetsprovning med JUnit och Mockito:** Varje mikrotjänst genomgår omfattande enhetsprovning med JUnit och Mockito för att säkerställa funktionalitet och identifiera och åtgärda potentiella problem.

## Använda teknologier
- **Java:** Det primära programmeringsspråket.
- **Spring-ramverket:** Utvecklar ekosystemet Spring, inklusive Spring Boot, Spring Data JPA och Spring Security, för att bygga skalbara mikrotjänster.
- **Mikrotjänstarkitektur:** Använder mikrotjänstarkitektur för modulär och skalbar utveckling, vilket möjliggör effektiv hantering av komplexa funktioner.
- **AOP (Aspektinriktad programmering):** Använder en kombination av aspektinriktad programmering och anpassade anmärkningar för att implementera säkerhet på metodnivå, vilket ger stor flexibilitet och enkelhet vid implementering av auktorisation för begränsad data.
- **JUnit och Mockito:** Använder JUnit och Mockito för grundlig enhetstestning för att säkerställa mikrotjänsternas pålitlighet.
- **PostgreSQL:** Hanterar data, inklusive inlägg, videodata och bildinformation samt användarinformation, med PostgreSQL som databashanterningssystem.
- **RabbitMQ:** Underlättar kommunikationen mellan mikrotjänster, särskilt för hantering av händelser och meddelanden.
- **Resilience4J:** Säkerställer motståndskraft och robusthet i kommunikationen mellan mikrotjänster genom att inkludera Resilience4J som en brytare.

## Mikrotjänster

### Video-Service
- Videostreaming.
- Lagrar videor.
- Säkrar användarresurser.

### Api Gateway
- Ansvarig för ruttning och vägledning till olika mikrotjänster.
- Validerar inkommande förfrågningar genom Security-Service.

### Security-Service
- Hanterar autentisering och auktorisation, utfärdar JWT för säker kommunikation.

### User-Service
- Hanterar användardata, används för lagring och bearbetning av personlig information för användare.
- Säkrar användarresurser.

### Post-Service
- Hanterar skapande, gillande och kommentering av inlägg.
- Ansvarig för skapande av flöden baserade på popularitetsmätare för inlägg.
- Säkrar användarresurser.

###
