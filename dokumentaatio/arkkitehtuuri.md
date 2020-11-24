# Arkkitehtuurikuvaus

### Rakenne

![pakkaus](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/pakkaukset.jpg)

ohjelmistotekniikka.ui tulee sisältämään javaFX:llä toteutetun graafisen käyttöliittymän. Sovelluslogiikka on eriyttetynä pakkaukseen ohjelmistotekniikka.domain. Tiedon tallennukseen ei ole vielä toteuttuna mitään.

### Käyttöliittymä

Käyttöliittymä tulee sisältämään kolme eri näkymää. Aloitusvalikon, josta voi aloittaa uuden pelin tai mennä katsomaan pistetilastoja. Toinen näkymä on siis itse peli ja kolmantena nämä pistetilastot.

### Sovelluslogiikka

Shape luokka kuvaa yhtä tetromino palaa. Tetris taas vastaa sitten toiminnasta eli esim. palojen kiertämisestä, liikkuttamisesta ja rivien tyhjentämisestä, kun kokonainen rivi ollaan saatu täytettyä. 

![luokat](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/luokat.jpg)
