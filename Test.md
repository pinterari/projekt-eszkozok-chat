# Chat tesztelés

| Teszt | Elvárt Eredmény | Helyes eredmény |
| :------------: | :-------------: | :-------------: |
| **Belépés / Regisztráció** | | |
| *[1] Belépés* menüpontot választjuk, tárolt felhasználónévvel és jelszóval | A program bekéri a felhasználónevet és a jelszót, majd belépteti a felhasználót | :white_check_mark: |
| *[1] Belépés* menüpontot választjuk, rossz adatokkal | A szerver értesíti a felhasználót, majd újra bekéri az adatokat |  :white_check_mark: |
| *[2] Regisztráció* menüpontot választjuk | A program bekéri a regisztrációhoz szükséges adatokat, visszajelez a felhasználónak, majd a felhasználónév és a jelszó megadásával beléptetni | :white_check_mark: |
| **Főmenü** | | |
|Bejelentkezés | A [1] Szoba létrehozása / [2] Belépés egy létező szobába / [3] Kilépés főmenüt hozza be a program és kilistázza az elérhető szobákat | :white_check_mark: |
| *[1] Szoba létrehozása* menüpontot választjuk | A szerver bekér egy nevet a szobához, hozzárendeli a következő azonosítószámot | :white_check_mark: |
| Új szobát hozunk létre | Megadjuk a szoba nevét, a program kilistázza az elérhető szobákat a új szobával kiegészítve | :white_check_mark: |
| Új szobát hozunk létre, már használt azonosítónévvel | A program engedi, a szobák száma különbözik, így egyértelműen azonosíthatóak | :white_check_mark: |
| *[2] Belépés egy létező szobába* menüpontot választjuk | A szerver bekéri a szoba azonosítószámát | :white_check_mark: |
| Belépünk egy szobába | Megadjuk a szoba azonosítóját, a program beléptet a szobába, ahol kilistázza a szobában küldött utolsó üzeneteket, küldési időponttal együtt | :white_check_mark: |
| *[3] Kilépés* menüpontot választjuk | A szerver és a kliens között megszűnik a kapcsolat, a többi aktív kliens számára megjelenik egy üzenet: " < Felhasználónév > kijelentkezett | :white_check_mark: |
| **Chatszoba** | | |
|Belépünk egy szobába | A program két használható parancsot kínál fel: [EXIT] Kilépés / [INVITEUSER] Felhasználó meghívása a szobába | :white_check_mark: |
| *[INVITEUSER]* parancsot választjuk | Megjelennek az elérhető felhasználók, azonosítószámmal együtt | :white_check_mark: |
| Beírjuk a meghívni kivánt felhasználó azonosítóját | A meghivott felhasználónak megjelenik egy új szoba az *Elérhető chatszobák:* listájában | :white_check_mark: |
| A meghívott felhasználó kiválasztja az újonnan megjelenő szobát | A program belépteti a szobába és jelzi a szobában lévő felhasználóknak *< Felhasználónév > belépett* üzenettel | :white_check_mark: |
| Üzenetet küldünk | A szobában lévő felhasználóknál megjelenik az üzenet *< Felhasználónév >:* után | :white_check_mark: |
| *[EXIT]* parancsot választjuk | A program kiléptet a szobából és megjeleníti a főmenüt | :white_check_mark: |
