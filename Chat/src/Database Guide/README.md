# Adatbázishoz való csatlakozás
*Készítette Katona Bence (D7JO4Q)*

## Telepítés
Szükség lesz a MySql Workbench-re ([link](https://dev.mysql.com/downloads/workbench/)). Ha letöltöttétek és telepítettétek, akkor az src/Database Guide/ mappában találtok egy init.sql nevű fájlt. Ezt ha futtatjátok az MySql Workbenchből, akkor az létrehozza a program futásához szükséges adatbázist, táblákat és felhasználót. Továbbá a project a Mavennek köszönhetően minden dependenciát beránt, ami a futtatáshoz kell, tehát nem kell kézzel telepíteni a Hibernatet meg az egyéb adatbázis pluginokat.

## Magyarázat
A Hibernatet összekötöttem az előbb létrehozott adatbázissal (bővebben hibernate.cfg.xml) és az adatbázisban található táblákat összepárosítottam egy velük megegyező POJO Entityvel (bővebben hu.elte.project.eszkozok.chat.entity). Továbbá a tesztek futtatásánál a piros wall-of-text az feature, nem bug. Ilyen furán logolja a dolgokat. Akkor van probléma csak, ha a jUnit teszt elhasal.

## Próba
Elméletileg, ha mindent jól csináltatok, akkor ha futtatjátok a hu.elte.project.eszkozok.chat.database.TestJdbc.java és TestHibernate.java osztályokat, akkor az elsővel tudjátok tesztelni, hogy egyáltalán létrejött-e a kapcsolat az adatbázissal. A másodikkal meg, hogy a Hibernate ORM (Object/Relational Mapping), hogy megfogja könnyíteni az életünk.
Esetleg azt még ki lehet próbálni, hogy az első commit után beraktok valami console input függvényt, hogy megtudjátok nézni az MySql Workbenchben, hogy tényleg létrejön a rekord.
A táblákra 'ON DELETE CASCADE'-et raktam, így a törlés is elég egyszerű lesz. (Pl.: Kitörölsz egy User-t és elméletileg kitörlődik az összes Üzenet, amit ő írt és az összes csoportból is kitörlődik, amiben benne volt. Itt esetleg egy triggerrel lehet még azt, hogyha ha egy user törlése miatt egy csoport létszáma 1-re csökken, akkor a csoport is kitörlődik, vagy felőlem maradhat és akkor lehet önmagaddal diskurálni :D)