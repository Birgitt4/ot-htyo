# Arkkitehtuurikuvaus

Kuvat eivät ole nyt ajantasalla lisättyäni tietokannan tällä viikolla (viikko 6)!

### Rakenne

![pakkaus](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/pakkaukset.jpg)

ohjelmistotekniikka.ui tulee sisältämään javaFX:llä toteutetun graafisen käyttöliittymän. Sovelluslogiikka on eriyttetynä pakkaukseen ohjelmistotekniikka.domain. Tiedon tallennukseen ei ole vielä toteuttuna mitään.

### Käyttöliittymä

Käyttöliittymä sisältää kolme eri päänäkymää. Aloitusvalikon, josta voi aloittaa uuden pelin tai mennä katsomaan pistetilastoja. Toinen näkymä on siis itse peli ja kolmantena pistetilastot. Näkymät on toteutettu omina Scene olioinaan. Käyttöliittymä löytyy pakkauksesta ohjelmistotekniikka.ui ja on toteutettu kokonaisuudessaan luokassa TetrisUi.

Käyttöliittymä on eristetty sovelluslogiikasta. Sovelluslogiikassa peliä kuvaa taulukko, joka muuttuu pelin tilanteiden mukaan. Käyttöliittymä päivittää näkymää vain tuon taulukon mukaisesti.

### Sovelluslogiikka

Sovelluslogiikasta vastaa luokat [Tetris](https://github.com/Birgitt4/ot-htyo/blob/master/Tetris/src/main/java/ohjelmistotekniikka/domain/Tetris.java) ja [Shape](https://github.com/Birgitt4/ot-htyo/blob/master/Tetris/src/main/java/ohjelmistotekniikka/domain/Shape.java).

Shape luokka kuvaa yhtä tetromino palaa. Pala on ilmoitettu (x,y) koordinaatteina, mikä mahdollistaa muodon mallinnuksen kaksiuloitteiseen taulukkoon Tetris luokkaan.

Tetris taas vastaa pelin toiminnasta eli esim. palojen kiertämisestä, liikuttamisesta ja rivien tyhjentämisestä, kun kokonainen rivi ollaan saatu täytettyä. Itse peli on vain kaksiuloitteinen taulukko, jossa tyhjissä ruuduissa on 0 ja ruuduissa, jossa on jonkin tetrominon pala, on numero 1.

![luokat](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/luokat.jpg)

### Tietojen pysyväistallennus

Pakkauksesta ohjelmistotekniikka.dao löytyy Dao rajapinta ja TetrisDao, joka toteuttaa Dao rajapinnan. Pelissä tallennamme tietoa pelituloksista. Pelin loppuessa näytölle ponnahtaa ikkuna, jossa käyttäjä voi antaa oman nimimerkin ja tallentaa pelin tuloksen tietokantaan. Aloitussivulta napista "Highscores" pääsee tarkastelemaan kaikkien aikojen top pelaajien tuloksia, sekä käyttäjä pystyy myös hakemaan tietyllä nimimerkillä pelattuja pelejä.

Tietokanta on SQL tietokanta, joka sisältää ainoastaan yhden tietokantataulun. Tietokannan nimi löytyy sovelluksen juuren config.properties tiedostosta.

### Perustoiminnallisuuksista

#### Pelin kulku ilman käyttäjältä tulevia käskyjä
Kun käyttäjä painaa aloitusnäkymässä nappia start, peli lähtee heti käyntiin. Aluksi luodaan pelialusta eli Tetris luokan olio, joka luo uuden Shape olion ensimmäiseksi tetrominoksi. Tämän jälkeen tarkistetaan, että meillä on tetromino pelissä, jos metodi getCurrentShape palauttaa arvon null, niin peli pysäytetään. Muuten tietyin väliajoin tetromino putoaa yhden askeleen automaattisesti alaspäin. Tetris luokan moveDown metodi huolehtii uusien tetrominojen luomisista ja rivien tyhjentämisistä aina, kun edellinen tetromino ei voinut enää mennä alaspäin.
![alas](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/Pelinperustoiminnallisuus2.jpg)

#### Pisteiden tallennus
Kun peli loppuu, käyttäjä pystyy valita tallennetaanko tulos muistiin vai ei. Jos käyttäjä päättää tallentaa tuloksen, hänen tulee antaa tulokselle nimimerkki. Tämän jälkeen nimimerkki ja tulos annetaan parametrina Tetris luokan metodille savePoints, joka tallentaa tuloksen TetrisDaon kautta pelin taustalla olevaan tietokantaan.

(Tähän vielä sekvenssikaavio)

### Ohjelmaan jääneet heikkoudet

Käyttöliittymä!


