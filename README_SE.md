# Sociala medieapplikation
- [English](README.md)

## Översikt
Sociala medieapplikationen är en plattform som visar upp min kompetens inom Java-utveckling, Spring-ramverket och mikrotjänstarkitektur. Den ger användare möjlighet att dela video, bild eller textinlägg, engagera sig i innehållet genom att kommentera inlägg och andra kommentarer samt gilla innehåll för att påverka dess popularitet. Applikationen tillåter användare att kommunicera med den inbyggda meddelandefunktionen mellan vänner. Appliktationen använder även Twilio fär att skicka SMS till användare i fall som bortglämt läsenord.


## Använda teknologier
- **Java:** Det primära programmeringsspråket.
- **AWS s3** För lagring av bild-data.
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
Ansvarar för hantering av videoinlägg och videostreaming. Denna tjänst tillhandahåller funktionalitet för att ladda upp,
lagra och strömma videor till användarna.

### Image-Service
Ansvarar för lagring av bilddata i AWS S3. Denna tjänst möjliggör uppladdning, lagring och hämtning av bilder för användare 
i applikationen. Genom att använda AWS S3 som lagringsplats för bilddata, möjliggör den effektiv hantering och snabb åtkomst 
till bilder i applikationen.

### Api Gateway
Ansvarar för ruttning och vägledning till olika mikrotjänster. Dessutom ansvarar den för att validera inkommande 
förfrågningar genom Security-Service för att säkerställa att endast behöriga användare har tillgång till skyddade resurser.

### Security-Service
Ansvarar för autentisering och auktorisation av användare. Denna tjänst utfärdar JWT för säker kommunikation och ger ett lager
av säkerhet för att skydda applikationens resurser från obehörig åtkomst. Dessutom hanterar den processen vid bortglömt 
lösenord genom att generera engångslösenord och hjälpa användare att återställa sina konton.

### User-Service
Ansvarar för hantering av användardata och personlig information. Denna tjänst används för att registrera användare, lagra
deras personliga information och säkerställa att användarresurser är skyddade och åtkomliga endast för behöriga användare.

### Post-Service-Command
Ansvarar för "write" funktionaliteten för inlägg, gillanden och kommentarer. Denna tjänst hanterar skapande, gillande och 
kommentering av inlägg samt genomför de nödvändiga åtgärderna för att uppdatera databasen med dessa ändringar. Dessutom 
skickar den händelser via Kafka för att meddela andra delar av systemet om förändringar och uppdateringar relaterade till 
inlägg och interaktioner från användare. Genom att använda Kafka för händelsebaserad kommunikation säkerställer denna tjänst 
att andra delar av systemet kan reagera på dessa händelser i realtid och vidta lämpliga åtgärder, såsom uppdatering av läsmodellen 
eller notifiering av användare om nya aktiviteter.

### Post-Service-ReadModel-Projector
Ansvarar för att hantera händelser från Kafka och uppdatera databasen med nya data. Denna tjänst arbetar i bakgrunden för
att upprätthålla konsistens mellan olika delar av systemet och säkerställa att uppdateringar och ändringar reflekteras korrekt 
i databasen.

### Post-Service-Read
Ansvarar för "read" funktionaliteten för inlägg och hämtning av relaterad data. Denna tjänst använder en egen databas för 
att optimera prestandan vid hämtning av inlägg och skapar flöden baserade på popularitetsmätare för att presentera relevant innehåll för användarna.

### Messaging-Service
Ansvarar för att möjliggöra kommunikation mellan användare genom text- och bildmeddelanden. Denna tjänst gör det möjligt 
för användare att skicka meddelanden till varandra och håller koll på konversationer och meddelanden.

### Friend-Service
Ansvarar för hantering av vänförfrågningar och vänrelationer. Denna tjänst gör det möjligt för användare att skicka och 
acceptera vänförfrågningar, vilket utvidgar deras sociala nätverk och möjliggör interaktion med andra användare i applikationen.

### Notification-Service
Ansvarar för att hantera händelser från Kafka och skicka SMS via Twilio till användare. Denna tjänst notifierar användare
om viktiga händelser i applikationen, såsom nya meddelanden, vänförfrågningar och bortglömda lösenord.

### UniqueID-Service
Genererar unika identifikations nummer som hjälper att sedan hitta båden bilder och video i datbasen.