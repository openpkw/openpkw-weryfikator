# User story - kompletna lista 


- dane z protokołów wyborcyzch zostaną przekazane za pomocą telefonów komorkówych i specialnie do tego zaprojektowanej aplikacji mobilnej
- Dodatkowo System Backend pobierze  te same dane w celu porówniania otrzymanych danych za pomocą aplikacji mobilnych z danymi z OKW.  Dane z OKW zostaną udostępnnione w jeden z nastepującyh sposobów (REST, WWW, MAIL, FTP - do wyspecyfikowania )


Pojedyncze user story organizujemy z perspektywy użytkownika.  
Schemant nazewnictwa: {perspektywa: A, U, G, S -{numer}. 

A-Administrator, U-Użytkownik, G-Gość, S-System


## Wariant prosty Wybory Parlamentarne 2015 
Jest to lista minimalnych wymagań by obsłużyć nadchodzące wybory. 

### Administrator
* A-1 : Jako administrator mogę zalogować się do systemu i uzyskać dostęp do (GUI)
* A-2 : Jako administrator definiuję / importuję komisje obwodowe (nr komisji, adres, przynależność do komisji okręgowej)
* -A-3 : Jako administrator definiuję / importuję komisje okręgowe (nr komisji oraz adresy)
* -A-4 : Jako administrator jestem w stanie dokonać wirtualnego podziału państwa na okręgi i obwody wyborcze 
* A-5 : Jako administrator jestem w stanie dodać pojedyncze konto użytkownika w przeglądarce
* A-6 : Jako administrator jestem w stanie wykonać import listy użytkowników z pliku CSV
* A-7 : Jako administrator chcę wykonać import komisji okręgowych bezpośrednio z pliku CSV 
* A-8:  Jako administrator chce wysyłac hasła do grup użytkownkow (masowe wysyłanie)
* A-9: Jako administartor chce miec mozliwosc zablokowania/odblokowania konta uzytkownka 
* A-10:Jako administartor chce miec mozliwosc resetu hasła dla uzytkownika


### Użytkownik (Aplikacji) 

U-1 .  Jako użytkownik mogę uwierzytelnić się w systemie poprzez aplikację mobilną za pomocą uprzednio otrzymanego loginu i hasła od Administratora

U-2. Jako Użytkownik mogę przesłać dane automatycznie robiąc zdjecie kodu QR. Przekazuje następujące dane:   
 * Ilość uprawnionych do głosowania
 * Ilość wydanych kart do głosowania
 * Ilość kart ważnych
 * Ilość głosów nieważnych
 * Ilość głosów ważnych
 * Ilość głosów dla poszczególnych kandydatow / komitetów wyborczej 

U-3 Jako użytkownik  mogę wykonać i przesłać zdjecia samych stron protokołu wyborczego


### Gość

* G-1 : Jako gość jestem w stanie wejść na stronę www i wyświetlić wyniki z danej komisji wyborczej / gminy / powiatu / okręgu / województwa / Polski.
* G-2 : Jako gość mam limitowany dostęp do serwera i możliwość wyswietlania wyników, aby nie przeciążyć serwera  
* G-3 : Jako gość mogę założyć nowe konto w systemie  


### System 

S-1 System powinien zapisac do bazy danych - dane przekazne przez aplikacje mobilne 
S-2 System musi przetworzyc odebrane dane z protokołow wyborczych ( Algorytm rodzialu mandatów)
S-3 Dane beda pobierane z zaproponowanych zródel


### Technologia
* T-1 : Wykonanie dokumentacji REST API w swagger.io 
* T-2 : Integracja SFL4J jako loggera
* -T-3 : Migracja z EclipseLink na Hibernate +envers 
* -T-4 : Generowanie E-Mail 
* -T-5 : Rozbicie kolumny `name` w bazie danych na `firstname` i `lastname` 
* -T-6 : Autoryzacja SPRING SECURITY
* -T-7 : Logowanie dla REST 
* -T-8 : Serwis REST pozwalający na rejestrację nowego użytkownika. 
* -T-9 : Serwis REST pozwalający na sprawdzenie czy adres E-Mail jest dostępny. 
* -T-11 DB Odwzorowanie koncepcji 'group' i 'permission'.
* -T-13 Dodanie do systemu koncepcji grup użytkowników. Definicja następujących grupy wolontariusz, operator, administrator,gosc  
* -T-14 Rozbudowa serwisu REST dla klienta obwodowego
* -T-15 Rozbudowa serwisu REST dla klienta dashboard 







