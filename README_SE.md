# Bokningsapplikation baserad på mikrotjänster

- [English](README.md)
## Översikt

Bokningsapplikationen är ett exempel på mitt arbete inom Java-utveckling, Spring-ramverket och mikrotjänstarkitektur. Den möjliggör för företag att erbjuda sina tjänster och för kunder att enkelt boka dessa tjänster. Denna applikation innehåller även funktioner för att ladda upp portföljbilder för tjänster och för användare att kommunicera genom meddelanden.

## Nyckelfunktioner

- **Användarregistrering och autentisering**: Säker användarregistrering och autentisering för att skydda användardata och reglera åtkomst. Applikationen använder JWT (JSON Web Tokens) för autentisering.

- **Publicering av tjänster och portföljbilder**: Tjänsteleverantörer kan enkelt publicera sina tjänster och förbättra sin portfölj genom att lägga till bilder för varje tjänst.

- **Bokning**: Användare kan smidigt bläddra bland tillgängliga tjänster, söka efter specifika tjänster och boka sina favoriter.

- **Tidsschemaöversikt**: Användare kan enkelt kontrollera lediga tider för specifika tjänster på valfri dag. De kan även filtrera för att se vilka tider som är tillgängliga för olika anställda.

- **Meddelandehantering med RabbitMQ**: Applikationen använder RabbitMQ för effektiv meddelandehantering mellan mikrotjänster.

- **Händelsestyrd arkitektur**: Applikationen använder RabbitMQ för händelsestyrd arkitektur, vilket möjliggör effektiv hantering av data mellan tjänsterna.

- **Meddelandefunktion**: Användare kan skicka text- och bildmeddelanden till varandra.

- **Enhetstestning med JUnit och Mockito**: Varje mikrotjänst är noggrant testad med JUnit och Mockito för att säkerställa korrekt funktionalitet och för att upptäcka och åtgärda potentiella fel.

## Teknologier som används

- **Java**: Det primära programmeringsspråket.

- **Spring Framework**: Projektet utnyttjar Spring-ekosystemet, inklusive Spring Cloud och Spring Data JPA för att bygga skalbara mikrotjänster.

- **Mikrotjänstarkitektur**: Applikationen använder mikrotjänstarkitektur effektivt för att bryta ner komplexa funktioner i hanterbara komponenter och för att säkerställa skalbarhet.

- **JUnit och Mockito**: Enhetstestning utförs med hjälp av JUnit och Mockito för att säkerställa att mikrotjänsterna fungerar korrekt och att potentiella fel upptäcks och åtgärdas.

- **PostgreSQL**: Datahantering, inklusive hantering av bilder, uppnås med hjälp av PostgreSQL som databashanteringssystem.

- **RabbitMQ**: Meddelandetjänster underlättas av RabbitMQ och stödjer realtidskommunikation mellan mikrotjänster samt möjliggör händelsestyrd datahantering.

- **Resilience4J**: Applikationen använder Resilience4J som en kretsbrytare för att säkerställa robusthet och felhantering i kommunikationen mellan mikrotjänsterna.

## Mikrotjänster

### Api Gateway
- Ansvarig för routing och vägledning.

### Security-Service
- Hanterar autentisering och auktorisering för inkommande förfrågningar.
- Svarar med en JWT vid inloggning för senare autentisering och auktorisering.
- Utför JWT-verifikation.
- Använder SHA-256 för att kryptera lösenord.

- **Enhetstestning med JUnit och Mockito**: Autentisering och auktorisering är noggrant testade med JUnit och Mockito för att säkerställa säkerhet och tillförlitlighet.

### User-Service
- Hanterar all användardata, inklusive kontaktinformation, namn och lösenord.
- Använder "Event Driven Architecture" med hjälp av RabbitMQ för att meddela andra tjänster om händelser såsom radering av användare och därmed ta bort all associerad data från andra databaser som används av de andra tjänsterna
### Booking-Service
- Returnerar tillgängliga tider för tjänster och genererar Timeslot-objekt för de anställda som är tillgängliga under en viss dag för att utföra tjänsten.
- Returnerar företag/tjänster baserat på namn och popularitet i deras tjänstkategori.
- Skickar meddelanden med information om bokningar genom RabbitMQ.

### Image-Service
- Komprimerar bilder och lagrar dem i PostgreSQL-databasen.
- Vid hämtning av bilder från databasen dekomprimeras de.
- Sparar bilder för meddelanden och portföljbilder och svarar med unika bild-ID:n till andra tjänster för senare hämtning.

### Messaging-Service
- Möjliggör användare att skicka meddelanden, inklusive text och bilder, till varandra.
- Möjliggör användare att radera meddelanden hos sig själv eller för alla i konversationen
- Hanterar systemmeddelanden om kommande bok