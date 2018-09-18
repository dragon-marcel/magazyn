
Aplikacja Java – magazyn towaru 
(prosta aplikacja magazynowa umożliwiającą wprowadzanie towaru i wydawanie go klientom) 

I.Wymagania projektu: 
a.Frontend: Thymeleaf 
b.Backend: Spring Boot 
c.ORM: Hibernate/JPA 
d.Baza danych: współpraca z  MS SQLServer i PostgreSQL – możliwość konfiguracji 
e.Narzędzie budowania: Maven/Gradle 

II.
Elementy aplikacji: 
1.Logowanie użytkownika przed rozpoczęciem pracy w aplikacji 
2.Podgląd rejestru stanu towaru na 
danym magazynie zawierający:  
a.Nazwę magazynu 
b.Nazwę towaru 
c.Aktualną ilość towaru na magazynie – uzyskaną na podstawie dokumentów 
3.Przyjęcie towaru na magazyn przez użytkownika poprzez dodanie d
okumentu z możliwością ewentualnej edycji: 
a.Dodawanie, usuwanie pozycji oraz modyfikacja ilości towaru podczas edycji dokumentu 
b.Podczas zatwierdzenia dokumentu wprowadzone zmiany powinny zaktualizować stan magazynu 
4.
Przeniesienie towaru na inny magazyn poprzez dodanie przez użytkownika dokumentu przesunięcia 
a.Dodawanie pozycji z  towarem która ma być przesunięta na inny magazyn (bez możliwości późniejszej edycji dodanego dokumentu) 
b.Podczas zatwierdzenia dokumentu wprowadzone zmiany powinny zaktualizować stan magazynu 
5.
Wydawanie towaru z magazynu klientowi poprzez dodanie przez użytkownika dokumentu wydania: 
a.Dodawanie, usuwanie pozycji oraz modyfikacja ilości towaru podczas edycji dokumentu 
b.Podczas zatwierdzenia dokumentu wprowadzone zmiany powinny zaktualizować stan magazynu 
6.Wyświetlenie listy utworzonych dokumentów z wybranego magazynu 
a.Z możliwością prostego filtrowania po dacie, typie dokumentu i użytkowniku.  
b.Użytkownik powinien mieć możliwość  podglądu, edycji i usunięcia wybranego dokumentu z listy 
c.Prezentowana lista powinna zawierać: 
i.
Rodzaj dokumentu 
ii.
Użytkownika który go dodał 
iii.
Datę utworzenia dokumentu 
