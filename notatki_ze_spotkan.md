# Notatki ze spotkań zespołu Java

## Wtorek, 22 grudnia 2015 r.

Obecni: Rafał Reguła, Kamil Szestowicki, Kamil Gr, Tomasz Dowgielewicz, Sebastian Pogorzelski, Remigiusz Mrozek, Mirosław Gołda

Postępy:

1. Serwer UAT skonfigurowany na maszynie Rumcajs

2. Docker, wszędzie Docker.

Tematy dyskusji:

1. Wprowadzenie nowych osób do projektu.

2. Do testowania będziemy używać narzędzia Spock.

3. Do autentykacji będziemy używać OAuth.


## Wtorek, 15 grudnia 2015 r.

Obecni: Bartek Smok, Rafał Reguła, Karol Dzięgiel, Kamil Gr, Remigiusz Mrozek, Kamil Szestowicki, Łukasz Franczuk, Sebastian Pogorzelski, Sebastian Celejewski

Postępy:

1. Serwer buildów przeniesiony z maszyny Dobromir na maszynę Cypisek.

2. Prace nad przygotowaniem następnych maszyn w toku.


Tematy dyskusji:

1. Problemy z infrastrukturą: za mało RAMu na maszynach, pozamykane porty, wygasły certyfikat, brak kontaktu z administratorami infrastruktury OpenPKW.

2. Nie pojawiają się nowi ludzie. Trzeba uporządkować kod (bo aktualny stan kodu odstrasza potencjalnych nowych ludzi), zgrać wszystkie napisane dotychczas komponenty, przeprowadzić test całego procesu i można to pokazywać, co powinno zachęcić nowych ludzi.

3. Jeśli trudno skontaktować się z administratorami domeny i hosta maszyn wirtualnych, warto zdobyć dostęp do domeny i hosta.

4. Najpilniejsza potrzeba: specjaliści od technologii frontendowych.



## Wtorek, 27 październia 2015 r.

Obecni: Rafał R., Łukasz F., Remek M., Sebastian C., Sebastian P., Kamil D., Kamil G.

Postępy:

1. Generator Dokumentów został przerobiony tak, aby oprócz starych i niepotrzebnych już protokołów na wybory prezydenckie 2015 generował protokoły na wybory parlamentarne 2015 (Sebastian C.).

2. Prawie ukończone jest narzędzie, które wysyła do Generatora Dokumentów dane z zawierających historyczne wyniki wyborów plików CSV, dzięki czemu mamy wygenerowane protokoły w postaci plików PDF zawierające kod QR (Remek M.).

3. Powstała aplikację na Androida, która odczytuje kod QR i wysyła go do web serwisu. Aplikacja nie jest jeszcze połączona z kodem OpenPKW (Kamil G.).

4. Powstał wstępny projekt struktury bazy danych Weryfikatora (Łukasz F.).

(coś jeszcze?)


Przebieg spotkania:

1. Przeanalizowaliśmy co pozostało do zrobienia w rozwijanej przez Remka aplikacji, która wysyła do Generatora Dokumentów historyczne dane, aby dostać protokoły komisji obwodowej z kodami QR.

2. Zaprojektowaliśmy strukturę bazy danych Weryfikatora (rozwijając pomysł przyniesiony przez Łukasza).

3. Stworzyliśmy nowy projekt: openpkw-weryfikator-android, do którego Kamil G. wrzuci swoją aplikację androidową, która skanuje kody QR i wysyła do backendu Weryfikatora.


Dalsze kroki:

1. Utworzenie bazy danych na podstawie projektu, stworzenie klas domenowych w backendzie Weryfikatora.

2. Stworzenie narzędzia, które załaduje dane wszystkich komisji i kandydatów z plików CSV do bazy danych Weryfikatora.

3. Napisanie serwisu, który odbierze dane z kodu QR i zapisze je do bazy danych Weryfikatora. Początkiem tego serwisu będzie kod, który wniósł Kamil G.

## Środa, 7 październia 2015 r.

Obecni: Rafał, Łukasz, Bartek, Remek, Sebastian, Kamil

1. Przeprowadziliśmy code review projektu openpkw-weryfikator i usunęliśmy wszystko, co nie dotyczy aspektów funkcjonalnych dla najbliższych wyborów parlamentarnych. Czyli usunęliśmy rzeczy związane z referendum, rzeczy związane z bezpieczeństwem, tworzeniem użytkowników, walidacją haseł i e-maili itd. Usunęliśmy projekt openpkw-core, stworzyliśmy openpkw-utils itd.

2. Ustaliliśmy code conventions (standardowe Java conventions + użycie czterech spacji zamiast tabulacji + szerokość wierszy 480 znaków zamiast 76 znaków). Plik konfigurujący automatyczne formatowanie kodu w Eclipse i IntelliJ IDEA jest w gicie w openpkw-etc.

3. Ustaliliśmy, że będziemy używać bazy danych MySQL, ponieważ a) wszyscy ją znamy, b) zna ją wiele innych osób, a chcemy w OpenPKW likwidować wszelkie bariery, które mogłyby utrudnić innym osobom dołączenie do projektu.

4. Przedyskutowaliśmy ponownie główne komponenty OpenPKW: kalkulator, generator dokumentów, aplikacja mobilna, weryfikator, strona do prezentowania wyników. Sytuacja tutaj jest o tyle trudna do połapania się, że część komponentów piszemy dla PKW, a część dla wolontariuszy, mamy dwa komponenty backendowe, w tym jeden stateless, a drugi stateful itd. Na pewno będzie trzeba to jeszcze wyjaśniać w przyszłości.

5. Używane języki: cały kod piszemy wyłącznie po angielsku. Rozpoczynamy tworzenie słownika pojęć, aby mieć spójne nazewnictwo w całym projekcie. Dzięki temu będzie wiadomo jak przetłumaczyć ,,komisja obwodowa'' i ,,komisja okręgowa'' na język angielski. Natomiast komentarze w githubie piszemy po polsku.

6. Wykonaliśmy krótki przegląd tasków w Trello. Po wykonaniu tego code review i zmianie priorytetów na funkcjonalność z pominięciem aspektów typu bezpieczeństwo czy tworzenie użytkownikó część zadań się zdezaktualizowała, część zaktualizowaliśmy itd.

7. Najbliższe zadanie: wygenerowanie protokołu dla komisji obwodowej w postaci pliku PDF z kodem QR. Sebastian adaptuje generator dokumentów do wyborów parlamentarnych, Remek robi aplikację, która będzie strzelać do generatora po PDFy podając dane z CSV.
