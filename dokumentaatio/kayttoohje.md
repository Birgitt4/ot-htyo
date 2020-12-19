# Käyttöohje

Lataa [Tetris.jar](https://github.com/Birgitt4/ot-htyo/releases/tag/1.0)

## Konfigurointi

Ohjelmalla on konfiguraatiotiedosto config.properties. Tiedostossa on tietokantatiedoston nimi. Tiedosto on muotoa:

database=points.db

## Käynnistäminen

Ohjelman voi käynnistää terminaalista käsin käyttämällä käskyä:

java -jar Tetris.jar

## Ohjelmassa

Aloitusnäkymästä voit aloittaa uuden pelin painamalla nappia, jossa lukee "start". Tällöin näkymä vaihtuu ja peli alkaa heti. Pelin oikealla puolella on napit pelin pausettamiselle "pause", jatkamiselle "continue", sekä uuden pelin aloittamiselle "again". Voit myös palata takaisin aloitusnäkymään painamalla oikealla alhaalla olevaa nappia "back".

### Pelaaminen
Pelissä tetrominoja pystyy liikuttamaan alas, oikealle ja vasemmalle käyttämällä nuolinäppäimiä. Oikealle kiertäminen tapahtuu näppäimestä D, ja vasemmalle kiertäminen näppäimestä A (kiertäminen oikealle = tetromino ikäänkuin kaatuu yläpäästään oikealle).

Peli päättyy, kun ohjelma ei saa enää luotua laudalle uutta tetrominoa, eli laudan keskiosa ylhäältä on täynnä. Tällöin oikealle tulee mahdollisuus tallentaa äskeisen pelin tulos. Pelin tuloksen voi tallentaa antamalla nimimerkin ja painamalla save nappia. Tulosta ei kuitenkaan ole pakko tallentaa vaan käyttäjä voi aloittaa suoraan uuden pelin napista again.

### Pisteiden tarkastelu
Aloitussivulta pääsee myös katsomaan pistetilastoja napista "highscores". Pistetilastoissa näkyy aina sen hetkinen kärkikolmikko (jos peliä on pelattu sen verran), voit myös hakea nimimerkin perusteella kaikki tämän nimimerkin tulokset.
