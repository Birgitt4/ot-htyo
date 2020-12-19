## Tetris
Helsingin yliopiston syksyn 2020 ohjelmistotekniikka kurssin harjoitustyö. Oma versioni pelistä Tetris.


## Dokumentaatio
[Käyttöohje](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/Birgitt4/ot-htyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)


## Viimeisin release

[Loppupalautus](https://github.com/Birgitt4/ot-htyo/releases/tag/1.0)

## Komentorivitoiminnot
### Testaus
Testit saa suoritettua komennolla: mvn test

Testikattavuuden saa luotua komennolla: mvn jacoco:report

Kattavuusraportti voidaan avata selaimelle. Polku kattavuusraporttiin on Tetris/target/site/jacoco/ ja tiedosto on index.html

### Jarin generointi
Suoritettavan jar tiedoston saa generoitua komentoriviltä komennolla: mvn package

Tämän jälkeen hakemistoon target ilmestyy tiedosto Tetris-1.0-SNAPSHOT.jar
Koodin voi ajaa terminaalista käsin komennolla: java -jar Tetris-1.0-SNAPSHOT.jar

### Checkstyle
Checkstyle raportin saa suorittamalla komennon: mvn jxr:jxr checkstyle:checkstyle
Tämä luo tiedoston checkstyle.html hakemistoon target/site.

### Javadoc
Javadoc:n saa generoitua komennolla: mvn javadoc:javadoc
