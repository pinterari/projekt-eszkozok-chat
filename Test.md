# Chat tesztelés

| Teszt | Elvárt Eredmény | Helyes eredmény |
| :------------: | :-------------: | :-------------: |
| ***Kliens*** | | |
| **Regisztráció** | A program bekéri a regisztrációhoz szükséges adatokat, visszajelez a felhasználónak, majd a felhasználónév és a jelszó megadásával beléptetni | :white_check_mark: |
| *Belépés*, tárolt felhasználónévvel és jelszóval | A program bekéri a felhasználónevet és a jelszót, majd belépteti a felhasználót | :white_check_mark: |
| *Belépés*, rossz adatokkal | A szerver értesíti a felhasználót, majd újra bekéri az adatokat |  :white_check_mark: |
| **Főmenü** | Bejelentkezést követően, a "[1] Szoba létrehozása / [2] Belépés egy létező szobába / [3] Kilépés" főmenüt hozza be a program és kilistázza az elérhető szobákat | :white_check_mark: |
| *"[1] Szoba létrehozása"* menüpontot választjuk | A szerver bekér egy nevet a szobához, hozzárendeli a következő azonosítószámot | :white_check_mark: |
| Új szobát hozunk létre | Megadjuk a szoba nevét, a program kilistázza az elérhető szobákat a új szobával kiegészítve | :white_check_mark: |
| Új szobát hozunk létre, már használt azonosítónévvel | A program engedi, a szobák száma különbözik, így egyértelműen azonosíthatóak | :white_check_mark: |
| *"[2] Belépés egy létező szobába"* menüpontot választjuk | A szerver bekéri a szoba azonosítószámát | :white_check_mark: |
| Belépünk egy szobába | Megadjuk a szoba azonosítóját, a program beléptet a szobába, ahol kilistázza a szobában küldött utolsó üzeneteket, küldési időponttal együtt | :white_check_mark: |
