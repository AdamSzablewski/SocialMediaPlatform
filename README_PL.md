# Aplikacja do Rezerwacji oparta na Mikrousługach

- [English](README.md)

## Przegląd
Aplikacja do rezerwacji usług prezentuje moje umiejętności w zakresie programowania w języku Java, korzystania z ramy Spring oraz architektury mikrousługowej. Umożliwia firmom oferowanie swoich usług, umożliwiając klientom łatwe dokonywanie rezerwacji. Aplikacja ta zawiera funkcje pozwalające dostawcom usług przesyłać zdjęcia do portfolio oraz umożliwiające użytkownikom komunikację za pomocą wiadomości.

## Główne Funkcje
- **Rejestracja i Autentykacja Użytkowników:** Bezpieczna rejestracja i autentykacja mają na celu ochronę danych użytkowników oraz regulację dostępu. Aplikacja wykorzystuje JWT (JSON Web Tokens) do autentykacji.

- **Publikacja Usług i Zdjęć do Portfolio:** Dostawcy usług mogą łatwo publikować swoje usługi i ulepszać swoje portfolio poprzez dodawanie zdjęć do każdej usługi.

- **Rezerwacja:** Użytkownicy mogą swobodnie przeglądać dostępne usługi, szukać konkretnych ofert i rezerwować swoje ulubione.

- **Harmonogram Dostępności:** Użytkownicy mogą łatwo sprawdzać dostępność terminów dla konkretnych usług w dowolny dzień. Mogą również filtrować dostępność terminów dla różnych pracowników.

- **Zarządzanie Wiadomościami z Wykorzystaniem RabbitMQ:** Aplikacja używa RabbitMQ do efektywnego zarządzania wiadomościami między mikrousługami.

- **Architektura Zdarzeniowa:** Aplikacja wykorzystuje RabbitMQ do architektury zdarzeniowej, umożliwiającej efektywne zarządzanie danymi między usługami.

- **Funkcjonalność Wiadomości:** Użytkownicy mogą wysyłać sobie wiadomości tekstowe i graficzne.

- **Testy Jednostkowe z Użyciem JUnit i Mockito:** Każda mikrousługa przechodzi dokładne testy z użyciem JUnit i Mockito, aby zapewnić poprawne działanie i wykryć ewentualne problemy.

## Użyte Technologie
- **Java:** Główny język programowania.

- **Spring Framework:** Projekt wykorzystuje ekosystem Spring, w tym Spring Cloud i Spring Data JPA, do budowy skalowalnych mikrousług.

- **Architektura Mikrousług:** Aplikacja efektywnie wykorzystuje architekturę mikrousługową do podziału skomplikowanych funkcji na zarządzalne komponenty i zapewnienia skalowalności.

- **JUnit i Mockito:** Testy jednostkowe są przeprowadzane przy użyciu JUnit i Mockito, aby zapewnić poprawne działanie mikrousług i wykryć ewentualne problemy.

- **PostgreSQL:** Zarządzanie danymi, w tym obsługa obrazów, odbywa się przy użyciu PostgreSQL jako systemu zarządzania bazą danych.

- **RabbitMQ:** Usługi wiadomości są ułatwiane przez RabbitMQ, wspierając komunikację w czasie rzeczywistym między mikrousługami i umożliwiając zarządzanie danymi zdarzeniowymi.

- **Resilience4J:** Aplikacja wykorzystuje Resilience4J jako zabezpieczenie obwodów, aby zapewnić solidność i obsługę błędów w komunikacji między mikrousługami.

## Mikrousługi

### Api Gateway
Odpowiada za kierowanie i prowadzenie.

### Security-Service
Zajmuje się autentykacją i autoryzacją dla przychodzących żądań.
Odpowiada JWT po zalogowaniu dla późniejszej aut
