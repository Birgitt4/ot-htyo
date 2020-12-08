# Käyttöohje

Lataa (uusin release)

## Konfigurointi

Ohjelmalla on konfiguraatiotiedosto config.properties. Tiedostossa on tietokantatiedoston nimi. Tiedosto on muotoa:

database=points.db

## Käynnistäminen

Ohjelman voi käynnistää terminaalista käsin käyttämällä käskyä:

java -jar todoapp.jar

## Pelaaminen

Kaikki ohjelmassa olevat napit toimivat hiiren klikkauksella. Pelissä tetrominoja pystyy liikuttamaan alas, oikealle ja vasemmalle käyttämällä nuolinäppäimiä. Oikealle kiertäminen tapahtuu näppäimestä D, ja vasemmalle kiertäminen näppäimestä A. Pelin tuloksen voi tallentaa antamalla nimimerkin ja painamalla save nappia. Tulosta ei kuitenkaan ole pakko tallentaa vaan käyttäjä voi aloittaa suoraan uuden pelin napista again.

Aloitussivulta pääsee myös katsomaan pistetilastoja. Pistetilastoissa näkyy aina sen hetkinen kärkikolmikko (jos peliä on pelattu sen verran), voit myös hakea nimimerkin perusteella kaikki tämän nimimerkin tulokset.
