# Notatki ze spotkañ zespo³u Java

## Œroda, 7 paŸdziernia 2015 r.

Obecni: Rafa³, £ukasz, Bartek, Remek, Sebastian, Kamil

1. Przeprowadziliœmy code review projektu openpkw-weryfikator i usunêliœmy wszystko, co nie dotyczy aspektów funkcjonalnych dla najbli¿szych wyborów parlamentarnych. Czyli usunêliœmy rzeczy zwi¹zane z referendum, rzeczy zwi¹zane z bezpieczeñstwem, tworzeniem u¿ytkowników, walidacj¹ hase³ i e-maili itd. Usunêliœmy projekt openpkw-core, stworzyliœmy openpkw-utils itd.

2. Ustaliliœmy code conventions (standardowe Java conventions + u¿ycie czterech spacji zamiast tabulacji + szerokoœæ wierszy 480 znaków zamiast 76 znaków). Plik konfiguruj¹cy automatyczne formatowanie kodu w Eclipse i IntelliJ IDEA jest w gicie w openpkw-etc.

3. Ustaliliœmy, ¿e bêdziemy u¿ywaæ bazy danych MySQL, poniewa¿ a) wszyscy j¹ znamy, b) zna j¹ wiele innych osób, a chcemy w OpenPKW likwidowaæ wszelkie bariery, które mog³yby utrudniæ innym osobom do³¹czenie do projektu.

4. Przedyskutowaliœmy ponownie g³ówne komponenty OpenPKW: kalkulator, generator dokumentów, aplikacja mobilna, weryfikator, strona do prezentowania wyników. Sytuacja tutaj jest o tyle trudna do po³apania siê, ¿e czêœæ komponentów piszemy dla PKW, a czêœæ dla wolontariuszy, mamy dwa komponenty backendowe, w tym jeden stateless, a drugi stateful itd. Na pewno bêdzie trzeba to jeszcze wyjaœniaæ w przysz³oœci.

5. U¿ywane jêzyki: ca³y kod piszemy wy³¹cznie po angielsku. Rozpoczynamy tworzenie s³ownika pojêæ, aby mieæ spójne nazewnictwo w ca³ym projekcie. Dziêki temu bêdzie wiadomo jak przet³umaczyæ ,,komisja obwodowa'' i ,,komisja okrêgowa'' na jêzyk angielski. Natomiast komentarze w githubie piszemy po polsku.

6. Wykonaliœmy krótki przegl¹d tasków w Trello. Po wykonaniu tego code review i zmianie priorytetów na funkcjonalnoœæ z pominiêciem aspektów typu bezpieczeñstwo czy tworzenie u¿ytkownikó czêœæ zadañ siê zdezaktualizowa³a, czêœæ zaktualizowaliœmy itd.

7. Najbli¿sze zadanie: wygenerowanie protoko³u dla komisji obwodowej w postaci pliku PDF z kodem QR. Sebastian adaptuje generator dokumentów do wyborów parlamentarnych, Remek robi aplikacjê, która bêdzie strzelaæ do generatora po PDFy podaj¹c dane z CSV.