# OpenPKW JAVA

W obecnej chwili podstawowym celem zespołu developerów Java jest stworzenie rozwiązania umożliwiającego niezależne i obiektywne zweryfikowanie wyników wyborów. Rozwiązanie to przeznaczone będzie dla ok. 27800 ochotników będących mężami zaufania w obwodowych komisjach wyborczych. Użytkownicy ci mają za zadanie za pomocą aplikacji mobilnych przesłać dane z udostępnionych publicznie protokołów wyborczych do zbiorczego systemu agregacji danych (backend). System ten prócz zbierania danych ma zadanie wizualizować pozyskane informacje w postaci diagramów wyborczych z podziałem na województwa. 
 
![OpenPKW Weryfikator Components](https://raw.githubusercontent.com/openpkw/openpkw-devops/master/OpenPKW%20Weryfikator%20Components.png)

## Członkowie w porzadku alfabetycznym 

| Członek  | Rola  |
| ------------- | ------------- |
|Kamil Gr|   Developer Android|
|Karol_D | Developer Java BackEnd |
|Lukasz_F |  Developer Java  BackEnd  |
|Rafal_R | Koordynacja/Organizacja  |
|Remek M |  Developer Java  BackEnd |
|Sebastian C|  Developer Java  BackEnd  |
|Sebastian P|   Developer Java  BackEnd|
|Waldek M|  Developer Java  BackEnd |


## RoadMap

To Be Done


## Jak do nas dołączyć?
Aby do nas dołączyć skontaktuj się z nami poprzez E-Mail i Skype. 

Kontakt:
  - Rafał Reguła: <rafal.regula@open-pkw.pl>
  - Skype: rafal.regula
  
## Informacje o projekcie OpenPKW Java

Strona główna projektu: https://openpkw.pl/ 

GitHub: https://github.com/openpkw/openpkw/tree/master/java

## Oranizacja pracy i linki

- Do modelowania wykorzystujemy Draw.io
- Do zmian w dokumentacji wykorzystujemy  Dillinger.io oraz GitHub
- Do zadań wykorzystujemy Trello
- Wszyscy developerzy mają pełny dostęp do repozytorium
- Każdy feature implementowany jest na branchu. Przed mergem do mastera deweloperzy spotykają się i omawiają propozycje zmian
- Wykorzystujemy metodologie Agile i spotykamy sie w sprinatch co wtorek o 20
- Jesli zajdzie potrzeba spotykamy się częsciej komunikując sie na Skype (grupa OpenPKWJava ) bądz przez liste mailingową ( openpkw-java@openpkw.pl ) 


| Element  | Link  |
| ------------- | ------------- |
| Tablica Trello Java | https://trello.com/b/eX0kOIwp/openpkw-java|
|  Draw.io |    https://www.draw.io  |
| Dillinger.io  |  http://dillinger.io |
|  GitHub  |  https://github.com/openpkw|
|  Mailing List|http://openpkw.pl/mailman/listinfo/openpkw-java|
| Serwer buildów | http://cypisek.open-pkw.pl:8080/view/openpkw-weryfikator-backend%20pipeline/ |
| Środowisko TEST | http://dobromir.openpkw.pl:9080/openpkw/ |
| Środowisko UAT | http://pat.openpkw.pl:9080/openpkw/ (jeszcze nie działa|


**Aktualnie poszukujemy!**  

1. Doświadczonych (bądz tez nie - ale pełnych zapału) developerów JAVA
2. Programistę HTML, JavaScript, Angular, Bootstrap  


## Wizja Systemu

1. Za pomocą aplikacji mobilnej użytkownicy (mężowie zaufania) skanują kody QR znajdujące się na wydrukowanych i wywieszonych w publicznie dostępnym miejscu protokołach wyborczych i wysyłają je na serwer OpenPKW.
2. System OpenPKW odbiera dane od użytkowników, zapisuje je w bazie danych, a także udostępnia na stronie internetowej wszystkim wyborcom w czasie rzeczywistym.
3. Państwowa Komisja Wyborcza przekazuje wyniki głosowania do serwera OpenPKW (technicznie: w jeden z nastepujących sposobów przez PKW: REST, FTP, WWW, MAIL).
4. System OpenPKW odbiera dane od Państwowej Komisji Wyborczej, zapisuje je w bazie danych, porównuje z danymi zebranymi od użytkowników OpenPKW i również udostępnia na stronie internetowej.
5. Wszyscy wyborcy na bieżąco są w stanie porównać wyniki zebrane przez użytkowników OpenPKW z wynikami Państwowej Komisji Wyborczej. 

## Proces

**Perspektywa Administratora / Przygotowanie do wyborów** (Backend)

1. Administrator zakłada konta użytkownikom (mężom zaufania)
2. Administrator rozsyła hasła użytkowikom 
3. Administrator zbiera dane użytkownika bądź/i importuje je  z XLS  (Imie, nazwisko, mail , telefon, nr komisji) 
4. System automatycznie wysyła link do aktywacji kont, login i hasło użytkownikom na podany adres e-mail
5. Administrator systemu ma możliwość edycji i weryfikacji kont użytkownków wraz ich danymi (duża fluktuacja użytkownków przed wyborami)
6. Administrator ma możliwość weryfikacji i podglądu listy użytkownikow, ktorzy odebrali hasła i poprawnie przesli procedurę testowego logowania do systemu


 **Perspektywa Użytkownika / Dzień Wyborczy** (Aplikacje Mobilne)

1. Użytkownik uwierzytelnia się w aplikacji mobilnej za pomocą uprzednio otrzymanego loginu i hasła od Administratora
2. Po poprawnym uwierzytelnieniu się system rozpoznaje użytkownika i przypisuje automatycznie wczesniej  numer obwodowej komisji z której przekazuje dane za pomoca QR
3. Użytkownik przesyła dane automatycznie robiąc zdjecie kodu QR a także samych stron protokoły wyborczego. Przekazuje następujące dane:   
 * Ilość uprawnionych do głosowania
 * Ilość wydanych kart do głosowania
 * Ilość kart ważnych
 * Ilość głosów nieważnych
 * Ilość głosów ważnych
 * Ilość głosów dla poszczególnych kandydatow / komitetów wyborczej 
 

## Specyfikacja

### Grupy użytkowników 
1. Administrator Systemu (admin)
2. Użytkownik Aplikacji (user)
3. Operator  Systemu (operator)
4. Użytkownik www (frontend)

## Risk & Issue Logs
1. Wysyłanie protokołów i zakonczenie liczenia głosów w 25000 komisjach zazwyczaj konczy się o tej samej porze +/- 15 min w przypadku prostych wyborów jakimi są Wybory Prezydenckie.Potrzebna Analiza wydajnosciowa serwerów  w tym (SQL, Moc Obliczeniowa maszyny oraz sama przepustowość łącza i-net). Zastanowić się nad modułem synchronizacji otrzymywania wyników.

2.  W przypadku gdy usługa będzie cieszyć sie bardzo dużą popularnoscią, Goście (wyborcy), którzy będą chcieli sprawdzić niezależne wyniki wyborów na stronie www - mogą spowodować problemy wydajnosciowe i/lub zawieszenie sie serwerów. Do rozważenia wprowadzenie modułu logowania na gości i ograniczenie ilości połaczeń dla danego gościa.   

##Wymagane oprogramowanie, środowiska DEV oraz technologie

### Mobile
1. Android 4.x - Lolipop
2. Ios 7.xx
3. WindowsPhone 8.x


### Backend (Przewidziana Architektura)
1. Java EE 8 (9.0,JPA2, Spring 4, JAX-RS, JAAS, JavaMail)
2. MySql
3. Maven 3
4. Json 2.0 (revised version)

### Frontend 
1. HTML5
2. JavaScript (AngularJS)
3. CSS3
4. Bootstrap 

## Informacje techniczne dla programistów

https://github.com/openpkw/openpkw-weryfikator/blob/master/TECHNICAL_README.MD
