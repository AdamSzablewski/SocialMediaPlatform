# Aplikacja mediów społecznościowych

## Przegląd

Aplikacja mediów społecznościowych to platforma prezentująca moje umiejętności w zakresie programowania w języku Java, korzystania z frameworka Spring oraz architektury mikroserwisów. Umożliwia użytkownikom dzielenie się treściami wideo, obrazami lub tekstami, angażowanie się w treść poprzez komentowanie postów i odpowiedzi na komentarze, a także wyrażanie polubień dla wpływania na popularność treści. Aplikacja pozwala użytkownikom komunikować się za pomocą wbudowanej funkcji wiadomości między znajomymi.

## Używane technologie

- **Java:** Główny język programowania.
- **Framework Spring**
- **Kafka:** Obsługa zdarzeń i komunikatów.
- **Architektura mikroserwisów**
- **AOP (Programowanie zorientowane aspektowo):** Wykorzystuje kombinację programowania zorientowanego aspektowo i niestandardowych adnotacji do implementacji bezpieczeństwa na poziomie metody, co zapewnia dużą elastyczność i łatwość implementacji autoryzacji w celu ograniczenia uprawnień tam, gdzie jest to potrzebne.
- **JUnit i Mockito:** Używane do pełnego testowania jednostkowego mikroserwisów w celu zapewnienia ich niezawodności.
- **PostgreSQL:** Zarządzanie danymi, w tym wpisami, danymi wideo, informacjami o obrazach i informacjami użytkowników, za pomocą systemu zarządzania bazą danych PostgreSQL.
- **Resilience4J:** Zapewnienie odporności i solidności komunikacji między mikroserwisami poprzez uwzględnienie Resilience4J jako przełącznika.

## Kluczowe funkcje

- **Posty:** Użytkownicy mogą tworzyć i udostępniać wpisy wideo, obrazowe lub tekstowe swojej sieci.
- **Transmisje wideo:** Wpisy wideo mogą być przesyłane przez użytkowników.
- **Komentarze i odpowiedzi:** Użytkownicy mogą uczestniczyć w rozmowach, komentując wpisy lub odpowiadając na inne komentarze.
- **System polubień:** Aplikacja zawiera system polubień dla wpisów i komentarzy, wpływający na popularność treści.
- **Zarządzanie znajomymi:** Użytkownicy mogą nawiązywać kontakty z innymi, wysyłając i akceptując zaproszenia do znajomych, co poszerza ich sieć społeczną.
- **Funkcjonalność wiadomości:** Funkcjonalność wiadomości w czasie rzeczywistym pozwala użytkownikom komunikować się za pomocą wiadomości tekstowych i obrazowych.
- **Miernik popularności:** Popularność wpisów i komentarzy jest określana przez liczbę polubień i komentarzy, wpływając na ich widoczność.
- **Rejestracja i uwierzytelnianie użytkowników:** Rejestracja i uwierzytelnianie użytkowników za pomocą JWT (JSON Web Tokens) w celu bezpiecznej autentykacji.
- **Testy jednostkowe z użyciem JUnit i Mockito:** Każdy mikroserwis przechodzi gruntowne testy jednostkowe za pomocą JUnit i Mockito w celu zapewnienia funkcjonalności oraz zidentyfikowania i naprawy potencjalnych problemów.

## Mikroserwisy

### Usługa wideo
- Transmisje wideo.
- Przechowywanie nagrań wideo.
- Zabezpieczanie zasobów użytkowników.

### Bramka API
- Odpowiedzialna za kierowanie ruchem i przekierowywanie do różnych mikroserwisów.
- Walidacja przychodzących żądań za pomocą Usługi Zabezpieczeń.

### Security-Service
- Zarządzanie uwierzytelnianiem i autoryzacją, wydawanie JWT w celu zapewnienia bezpiecznej komunikacji.

### User-Service
- Zarządzanie danymi użytkowników, używane do przechowywania i przetwarzania informacji osobistych użytkowników.
- Zabezpieczanie zasobów użytkowników.

### Post-Service
- Zarządzanie tworzeniem, polubieniami i komentowaniem wpisów.
- Odpowiedzialność za tworzenie strumieni opartych na mierniku popularności dla wpisów.
- Zabezpieczanie zasobów użytkowników.

### Messaging-Service
- Kompresowanie obrazów i ich przechowywanie w bazie danych PostgreSQL.
- Podczas pobierania obrazy są dekompresowane.
- Zapisywanie obrazów do wiadomości i obrazów profilowych oraz odpowiedzi z unikalnymi identyfikatorami obrazów dla innych usług do późniejszego pobierania.

### Usługa Wiadomości
- Umożliwia użytkownikom wysyłanie wiadomości, w tym tekstowych i obrazowych, do siebie nawzajem.
- Pozwala użytkownikom usuwać wiadomości od siebie lub dla wszystkich w konwersacji.
- Zarządza komunikatami systemowymi dotyczącymi nadchodzącej książki.

### Friend-Service
- Odpowiada za obsługę zaproszeń do znajomych itp.
