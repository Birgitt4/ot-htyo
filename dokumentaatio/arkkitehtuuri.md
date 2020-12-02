# Arkkitehtuurikuvaus

### Rakenne

![pakkaus](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/pakkaukset.jpg)

ohjelmistotekniikka.ui tulee sisältämään javaFX:llä toteutetun graafisen käyttöliittymän. Sovelluslogiikka on eriyttetynä pakkaukseen ohjelmistotekniikka.domain. Tiedon tallennukseen ei ole vielä toteuttuna mitään.

### Käyttöliittymä

Käyttöliittymä tulee sisältämään kolme eri näkymää. Aloitusvalikon, josta voi aloittaa uuden pelin tai mennä katsomaan pistetilastoja. Toinen näkymä on siis itse peli ja kolmantena nämä pistetilastot.

### Sovelluslogiikka

Shape luokka kuvaa yhtä tetromino palaa. Tetris taas vastaa sitten toiminnasta eli esim. palojen kiertämisestä, liikkuttamisesta ja rivien tyhjentämisestä, kun kokonainen rivi ollaan saatu täytettyä. 

![luokat](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/luokat.jpg)


### Perustoiminnallisuuksista

Kun käyttäjä painaa aloitusnäkymässä nappia start, peli lähtee heti käyntiin. Aluksi luodaan pelialusta eli Tetris luokan olio, joka luo uuden Shape olion ensimmäiseksi tetrominoksi. Tämän jälkeen tarkistetaan, että meillä on tetromino pelissä, jos getCurrentShape palauttaa arvon null, niin peli pysäytetään. Muuten tietyin väliajoin tetromino putoaa yhden askeleen automaattisesti alaspäin. Tetris luokan moveDown metodi huolehtii uusien tetrominojen luomisista ja rivien tyhjentämisistä aina, kun edellinen tetromino ei voinut enää mennä alaspäin.
![alas](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/Pelinperustoiminnallisuus2.jpg)
