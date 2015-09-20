### Instalacja

#### Konfiguracja środowiska deweloperskiego

- Ściągnąć i zainstalować:
  - Java 8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
  - WildFly 9 (http://wildfly.org/downloads/)
- Konfiguracja WildFly
  - Uruchomić serwer (/bin/standalone.bat)
  - Sprawdzić czy uruchomił się poprawnie (np. czy wszystkie standardowe porty tcp były wolne: 8080, 9990, 9999 itd.)
  - Utworzyć użytkownika administracyjnego komendą /bin/add-user.bat
  - Sprawdzić czy można się zalogować tym użytkownikiem do konsoli administracyjnej (http://localhost:9999)
- Utworzenie bazy danych
  - %JBOSS_HOME%/bin/jboss-cli.bat --connect --file=h2_database.cli
- Zbudowanie aplikacji
  - mvn clean install
- Rozproszenie aplikacji na serwerze
  - Zalogować się do konsoli administracyjnej, a potem Deployments/Add/Upload a new deployment/Next/Browse.../Znaleźć plik war /openpkw-weryfikator/openpkw-rest/target/

**Podstawowa instalacja komponentów serwera Wildfly**

W katalogu openpkw-etc/src/main/etc/wildfly znajdują się polecenia CLI
konfigurujące podstawowe usługi serwera wymagane przez aplikację.  
W pierwszym kroku należy uruchomić serwer (instrukcja w dokumentacji producenta).
Kolejny krok to wykonanie poleceń przy pomocy narzędzia jboss-cli.bat(sh).  
Przykładowe wywołanie na lokalnym środowisku:  
*jboss-cli.bat --connect --file=ścieżka_do_pliku_cli*

Pliki \*_database.cli odpowiadają za instalację datasource. Prefiks oznacza wersję bazy danych.  
UWAGA!!! Należy zwrócić uwagę na parametry połączenia zdefiniowane w plikach. Należy je dostosować
do swojego środowiska.   
Plik openpkw-queue.cli odpowiada za założenie kolejki zgodnej z JMS do przesyłania komunikatów

### Biblioteki
Przy dodawaniu odpowiednich dependency do pom-ów proszę korzystać z głównego pom-a, gdzie większość została zawarta. W projektach dziedziczących należy dodać do pom-ów tylko groupId i artifactId - wersja i scope zostaną odziedziczone z parenta.
Gdy będzie potrzeba dodania nowej biblioteki do projektu prosiłbym o zgłaszanie do luk.franczuk@gmail.com.  Chciałbym abyśmy zachowali spójność używanych bibliotek
