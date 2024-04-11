# Plansza gry

Plansza zrealizowana jest przy pomocy interfejsu `GameBoard` oraz jego implementacji:
- `Board`
- `Board1D`

Zakłada się, że można wykorzystać jedną dowolną z powyższych implementacji bez różnicy w działaniu programu.

Funkcje dostarczane przez obiekt reprezentujący planszę:
- pobranie znaku gracza dla pola o zadanych współrzędnych - `getCell`
- pobranie wszystkich pól jako dwuwymiarową tablicę - `getCells`
- pobranie rozmiaru planszy - `getSize`
- pobranie stanu pola - `getFieldState`
- sprawdzenie, czy plansza jest pełna - `isFull`
- zmiana stanu pola - `placeSymbol`
- sprawdzenie, czy wskazany gracz jest zwycięzcą - `isWinner`

## Implementacja Board

Przyjęto założenie, że klasa zawiera dwuwymiarową tablicę typu `Player`. Zajętość pola na planszy reprezentowana jest referencją do konkretnego obiektu `Player`, a brak zajętości jest tożsamy z wartością `null` w tablicy.

## Implementacja Board1D

Przyjęto założenie, że klasa zawiera jednowymiarową tablicę typu `Player`. Zajętość pola na planszy reprezentowana jest referencją do konkretnego obiektu `Player`, a brak zajętości jest tożsamy z wartością `null` w tablicy.

# Gracz

Gracz reprezentowany jest przez klasę `Player` i posiada dwa atrybuty:
- `name` - nazwa gracza
- `symbol` - znak gracza

# Losowanie znaku gracza

Funkcję losowania znaku gracza dostarcza klasa `CharacterPoolRandomizer`

Klasa umożliwia:
- przyjęcie zestawu znaków, z których będzie losować
- metodę losująca znak z puli, bez powtórzeń
- metoda losująca w przypadku braku dostępnych w puli znaków rzuca wyjątek

# Przechowywanie stanu gry

Podstawowym komponentem przechowującym stan gry i umożliwiającym jego dalszą serializację / deserializację, jest `StateDTO`.

Na stan gry składają się:
- atrybuty gracza (`PlayerDTO`)
- rozmiar planszy
- zajętość pól planszy
- aktualny gracz

## Mechanizmy serializacji stanu gry

Zaimplementowano kilka mechanizmów serializacji.

### Serializacja do pliku tekstowego

Zakłada się następujący format pliku tekstowego:

```text
John:X;Adam:O
-XX--O-O-
```

Pierwsza linia zawiera nazwę gracza i symbol gracza pierwszego oddzielone znakiem `:` oraz po `;` takie same atrybuty gracza drugiego.

Druga linia zawiera stan planszy, gdzie wolne pola oznaczone są znakiem `-`, a zajęte oznaczone są znakiem danego gracza. Rozmiar planszy (jako że jej wysokość i szerokość są sobie równe), możliwa jest do wyliczenia na podstawie długości linii.

### Serializacja do JSONa

### Serializacja do XMLa

### Serializacja do pliku SQLite
