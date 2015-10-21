# Notatki ze spotka� zespo�u Java

## �roda, 7 pa�dziernia 2015 r.

Obecni: Rafa�, �ukasz, Bartek, Remek, Sebastian, Kamil

1. Przeprowadzili�my code review projektu openpkw-weryfikator i usun�li�my wszystko, co nie dotyczy aspekt�w funkcjonalnych dla najbli�szych wybor�w parlamentarnych. Czyli usun�li�my rzeczy zwi�zane z referendum, rzeczy zwi�zane z bezpiecze�stwem, tworzeniem u�ytkownik�w, walidacj� hase� i e-maili itd. Usun�li�my projekt openpkw-core, stworzyli�my openpkw-utils itd.

2. Ustalili�my code conventions (standardowe Java conventions + u�ycie czterech spacji zamiast tabulacji + szeroko�� wierszy 480 znak�w zamiast 76 znak�w). Plik konfiguruj�cy automatyczne formatowanie kodu w Eclipse i IntelliJ IDEA jest w gicie w openpkw-etc.

3. Ustalili�my, �e b�dziemy u�ywa� bazy danych MySQL, poniewa� a) wszyscy j� znamy, b) zna j� wiele innych os�b, a chcemy w OpenPKW likwidowa� wszelkie bariery, kt�re mog�yby utrudni� innym osobom do��czenie do projektu.

4. Przedyskutowali�my ponownie g��wne komponenty OpenPKW: kalkulator, generator dokument�w, aplikacja mobilna, weryfikator, strona do prezentowania wynik�w. Sytuacja tutaj jest o tyle trudna do po�apania si�, �e cz�� komponent�w piszemy dla PKW, a cz�� dla wolontariuszy, mamy dwa komponenty backendowe, w tym jeden stateless, a drugi stateful itd. Na pewno b�dzie trzeba to jeszcze wyja�nia� w przysz�o�ci.

5. U�ywane j�zyki: ca�y kod piszemy wy��cznie po angielsku. Rozpoczynamy tworzenie s�ownika poj��, aby mie� sp�jne nazewnictwo w ca�ym projekcie. Dzi�ki temu b�dzie wiadomo jak przet�umaczy� ,,komisja obwodowa'' i ,,komisja okr�gowa'' na j�zyk angielski. Natomiast komentarze w githubie piszemy po polsku.

6. Wykonali�my kr�tki przegl�d task�w w Trello. Po wykonaniu tego code review i zmianie priorytet�w na funkcjonalno�� z pomini�ciem aspekt�w typu bezpiecze�stwo czy tworzenie u�ytkownik� cz�� zada� si� zdezaktualizowa�a, cz�� zaktualizowali�my itd.

7. Najbli�sze zadanie: wygenerowanie protoko�u dla komisji obwodowej w postaci pliku PDF z kodem QR. Sebastian adaptuje generator dokument�w do wybor�w parlamentarnych, Remek robi aplikacj�, kt�ra b�dzie strzela� do generatora po PDFy podaj�c dane z CSV.