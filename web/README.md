# Główne założenia

Moduł `web` wykorzystuje moduł `logic` w zakresie logiki gry oraz przechowywania stanu gry. W celu utrwalenia stanu (przyjmuje się serializację do JSONa), dodatkowo stosuje się bazę danych PostgreSQL.

# Diagram przepływu

```plantuml
@startuml
|Klient|
start
:`GET /game/new`;
|Web|
:Przygotowanie widoku `newGame`;
|Klient|
:Przedstawienie widoku 'newGame';
:Wprowadzenie nazw graczy i rozmiaru planszy;
:`POST /game/start`;
|Web|
:Przygotowanie gry;
|Logic|
:Przygotowanie planszy - klasa GameBoard;
:Losowanie znaków graczy - klasa CharacterPoolRandomizer;
:Przygotowanie graczy - klasa Player;
:Przygotowanie stanu gry - klasa StateDTO;
|Web|
:Serializacja stanu gry;
|Database|
:Utrwalenie stanu gry;
floating note right: nadanie id 
|Web|
:Redirect 302 do `/game/:id`;
|Klient|
:`GET /game/:id`;
|Web|
:Pobranie gry z bazy na podstawie id;
|Database|
:Pobranie gry;
|Web|
:Deserializacja do StateDto;
:Przygotowanie widoku `game`;
|Klient|
:Przedstawienie widoku `game`;
:Wykonanie ruchu;
:`POST /game/move`;
|Web|
:Pobranie gry z bazy na podstawie id;
|Database|
:Pobranie gry;
|Web|
:Deserializacja do StateDto;
|Logic|
:Wykonanie ruchu;
|Web|
:Przygotowanie widoku `game`;
|Klient|
:Przedstawienie widoku `game`;
floating note right: Gracze kontynuują grę j/w
@enduml
```
