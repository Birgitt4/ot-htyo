## Testausdokumentti
### Sovelluslogiikka
Sovelluslogiikan toimintaa on testattu JUnit testein. Kaikelle toiminnalle on pyritty tekemään jokin testi, joka tarkastaa, että metodit toimivat oikein. Testien painotus on eniten tetrominoihin koostuvista liikkeistä. Manuaalisesti testaillessani tetrominon kiertämistä, sain harmillisen usein poikkeuksen aiheutettua taulukon (pelilaudan) reunoilla. Poikkeukset tulivat yleensä vääristä indekseistä sekä null arvoista.

Sovelluslogiikkaa testaa testi TetrisTest. Node luokalle ei ole erikseen kirjoitettu omia testejä, koska node koostuu oikeastaan vain settereistä ja gettereistä. TetrisTest ei testaa paria metodia, jotka ovat tekemisissä Daon kanssa. Nämä tulevat testattu testissä TetrisDaoTest.

### DAO-luokat
