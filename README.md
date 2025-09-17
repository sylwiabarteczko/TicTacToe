## 🧩 System Architecture Diagram

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

The `Logic` component provides serialization mechanisms that allow saving the game state to a file, which is used indirectly by the `CLI` component. It is important to note that the `Web` component persists the state using the database — in this case, it does not rely on the serialization mechanisms offered by `Logic`.
