## Testausdokumentti
### Sovelluslogiikka
Sovelluslogiikan toimintaa on testattu JUnit testein. Kaikelle toiminnalle on pyritty tekemään jokin testi, joka tarkastaa, että metodit toimivat oikein. Testien painotus on tetrominoihin koostuvista liikkeistä. Nämä aiheuttivat helposti poikkeuksia kuten viittaaminen taulukon/pelilaudan ulkopuolisiin indekseihin sekä null poikkeus null arvoisen Shape-olion kiertämisestä.

Sovelluslogiikkaa testaa testi TetrisTest. Node luokalle ei ole erikseen kirjoitettu omia testejä, koska node koostuu oikeastaan vain settereistä ja gettereistä. TetrisTest ei testaa paria metodia, jotka ovat tekemisissä Daon kanssa. Nämä tulevat testattu testissä TetrisDaoTest.

### DAO-luokat
Dao rajapinnan toteutukset testataan testeissä TetrisDaoTest. Metodien kutsuja testataan kutsumalla suoraan sekä Tetris luokan metodien kautta. JUnit testeissä ei ole testattu tilanteita, kuten tiedostoihin ei ole luku oikeutta. Testit luo väliaikaisen testi tietokannan, joka poistetaan testien suorittamisen jälkeen. Tämä on toteutettu hyödyntäen TemporaryFolder-Ruleja.

### Jacoco



### Asennus ja konfigurointi
Sovellusta on testattu manuaalisesti cubbli-linuxilla. Sovelluksen jar tiedostoa on käynnistetty oikein asennettuna, mutta myös tilanteissa, jolloin käynnistyshakemistosta ei löydy tiedostoa config.properties. Ohjelmaa on ajettu myös tilanteissa, jossa ohjelmalla ei ole kirjoitusoikeuksia. Tällöin itse tetristä pystyy pelaamaan, mutta tietokantaan liittyvät ominaisuudet, kuten pisteiden tallennus, eivät ole käytössä.

Ohjelmaa on testattu tilanteissa, jossa tietokantatiedosto on jo olemassa sekä, kun tietokantaa ei ole. Jos tietokantatiedostoa ei ole, ohjelma luo sellaisen.
