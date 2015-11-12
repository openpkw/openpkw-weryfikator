#### Konfiguracja środowiska deweloperskiego - szybki start

- Ściągnąć i zainstalować:
  - Java 8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
  - WildFly 9.0.1.Final (http://download.jboss.org/wildfly/9.0.1.Final/wildfly-9.0.1.Final.zip)
- Konfiguracja WildFly
  - Uruchomić serwer (/bin/standalone.bat)
  - Sprawdzić czy uruchomił się poprawnie (np. czy wszystkie standardowe porty tcp były wolne: 8080, 9990, 9999 itd.)
  - Utworzyć użytkownika administracyjnego komendą /bin/add-user.bat
  - Sprawdzić czy można się zalogować tym użytkownikiem do konsoli administracyjnej (http://localhost:9999)
- Utworzenie bazy danych
  - %JBOSS_HOME%/bin/jboss-cli.bat --connect --file=h2_database.cli
- Zbudowanie aplikacji
  - mvn clean package
- Rozproszenie aplikacji na serwerze
  - Zalogować się do konsoli administracyjnej, a potem Deployments/Add/Upload a new deployment/Next/Browse.../Znaleźć plik war /openpkw-weryfikator/openpkw-rest/target/
- Sprawdzenie czy aplikacja się rozproszyła
  - Postmanem (lub innym klientem REST) wysłać na adres http://localhost:8080/openpkw/test/echo POST z nagłówkiem Content-Type=application/json i body {"test":"OpenPKW Weryfikator zawojuje świat"} 

#### Środowiska

- TEST (automatyczne testy integracyjne i akceptacyjne): http://dobromir.openpkw.pl:9080/openpkw/
- UAT (ręczne testy integracyjne i regresyjne): http://pat.openpkw.pl:9080/openpkw/
- STAGE (ręczne testy regresyjne, demonstrowanie nowych funkcji, szkolenia): brak
- PROD (wykorzystywane podczas wyborów): brak

![Środowiska OpenPKW](https://raw.githubusercontent.com/openpkw/openpkw-devops/master/OpenPKW%20Environments.png)

#### Proces

##### Definiowanie zadań
- Zadania definiujemy w Trello. Wszystkie osoby udzielające się w OpenPKW powinny mieć konta w Trello i dostęp do tablic dla poszczególnych komponentów.
- Zadania definiujemy najczęściej podczas spotkań wtorkowych, ewentualnie podczas dyskusji mailowej lub na czacie w Skype.
- Zadania powinny być dobrze opisane, tj. mieć sensowną nazwę i kryteria akceptacyjne.
- Stosujemy prefiksy ułatwiające identyfikowanie zadań: OW-typ_zadania-nr_zadania. OW oznacza openpkw-weryfikator, a typy zadań to: U-user_story (nowa funkcjonalność lub modyfikacja istniejącej funkcjonalności), R-refactoring, B-bug, I-infrastruktura itd.

##### Tworzenie kodu
- Kod tworzony jest na branchach w repozytorium openpkw/openpkw-weryfikator. Wszyscy programiści mają prawa do zapisu do tego repozytorium. 
- Nazwy branchy powinny odpowiadać nazwom tasków w Trello, czyli albo sam prefix (np. OW-U-7), albo prefix-opis (np. OW-U-7 Bardzo fajna funkcjonalność).

##### Review kodu
- Kod przed mergem do brancha master przechodzi proces code review. Celem jest zarówno sprawdzenie czy zaimplementowane jest to, co miało być, jak i sprawdzenie jakości kodu, standardów formatowania, kodowania itd.
- Review rozpoczynamy utworzeniem pull requesta z brancha taskowego do brancha master.
- Code review wykonują inni członkowie zespołu OpenPKW. Komunikacja: github, Skype, email, telefon, co się da. Idealnie jest, jeśli na temat zmiany wypowiedzą się wszyscy.
- W ramach code review koniecznie trzeba zbudować kod z brancha na Jenkinsie, rozproszyć na środowisku TEST i uruchomić automatyczne testy akceptacyjne. Jenkins jest tak skonfigurowany, że zmiany wchodzące na wszystkich branchach są automatycznie budowane i testowane, ale jeśli infrastruktura zawiedzie (a na razie czasem jeszcze zawodzi), trzeba to wykonać ręcznie.

##### Integracja kodu
- Po zakończeniu code review akceptujemy pull request w githubie, kod trafia do brancha master i jest automatycznie budowany, testowany, rozpraszany na środowisku TEST itd.
- Jako ostatni krok można pchnąć zmianę na środowisko UAT. Robi się to ręcznie uruchamiając krok 04_Deploy_to_UAT na Jenkinsie.

#### Podstawowa instalacja komponentów serwera Wildfly

W katalogu openpkw-etc/src/main/etc/wildfly znajdują się polecenia CLI konfigurujące podstawowe usługi serwera wymagane przez aplikację. W pierwszym kroku należy uruchomić serwer (instrukcja w dokumentacji producenta). Kolejny krok to wykonanie poleceń przy pomocy narzędzia jboss-cli.bat(sh).  

Przykładowe wywołanie na lokalnym środowisku:  
*jboss-cli.bat --connect --file=ścieżka_do_pliku_cli*

Pliki \*_database.cli odpowiadają za instalację datasource. Prefiks oznacza wersję bazy danych.  
UWAGA!!! Należy zwrócić uwagę na parametry połączenia zdefiniowane w plikach. Należy je dostosować
do swojego środowiska.   
Plik openpkw-queue.cli odpowiada za założenie kolejki zgodnej z JMS do przesyłania komunikatów

#### Biblioteki
Przy dodawaniu odpowiednich dependency do pom-ów proszę korzystać z głównego pom-a, gdzie większość została zawarta. W projektach dziedziczących należy dodać do pom-ów tylko groupId i artifactId - wersja i scope zostaną odziedziczone z parenta.

Gdy będzie potrzeba dodania nowej biblioteki do projektu prosiłbym o zgłaszanie do luk.franczuk@gmail.com.  Chciałbym abyśmy zachowali spójność używanych bibliotek.
