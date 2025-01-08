# Schemat blokowy projektu

```plantuml
@startuml
left to right direction

database "Disk" as Disk
database "Database" as Database

component Logic
component CLI
component Web

[Logic]..>[Disk] : uses
[CLI]-->[Logic] : uses
[CLI]..>[Disk] : uses via logic
[Web]-->[Logic] : uses
[Web]-->[Database] : uses
@enduml
```

Komponent `Logic` dostarcza mechanizmów serializacji pozwalających na zapis stanu gry do pliku, co jest wykorzystywane pośrednio przez `CLI`. Należy zwrócić uwagę, że komponent `Web` w celu utrwalenia stanu wykorzystuje bazę danych - w tym przypadku nie korzysta się z mechanizmów serializacji oferowanych przez `Logic`.
