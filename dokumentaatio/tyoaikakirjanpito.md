## Työaikakirjanpito
Päivä | Tunnit | Mitä tehty?
------|------|----------------------------------------------
09.11. | 3 | Määrittelydokumentti sekä tetriksestä googlailu
09.11. | 2 | projektipohjan luominen ja pom tiedostoon lisäyksiä, testit toimii ja kattavuusraportti saatavilla
16.11. | 1 | pom tiedostossa pitäisi olla kaikki tarvittava. import ...Application ei silti toimi.
16.11. | 3 | Itse ohjelman koodausta ja netin tutkimista. Luotu Tetris luokka, joka kuvaa käynnissä olevaa peliä ja Shape luokka, joka ohjaa tetris-palikoita
17.11. | 5 | Ohjelmassa toimii tetronomien kiertäminen ja liikuttaminen. Muutamia testejä tehty näille. Ohjelman pystyn suorittamaan ainakin ubuntulla ubuntulla!
24.11. | 5 | Checkstyle kuntoon. Javadoc kommentoinnit. Opiskelua java FL:stä, vaatii vielä paljon lisää opiskelua.
24.11. | 5 | Yritetty edistää ohjelmaa. Shape luokasta liikuttamis toiminnallisuudet Tetris luokkaan. Palat eivät siirry yli rajojen, mutta siirtyvät toisten palojen päälle. Tämän estäminen on osoittautunut yllättävän hankalaksi. Tämän hetkinen toteutus tekee siirron "feikki"laudalle ja sitten vertailee oliko sallittu. Tämä ei vielä toimi, koska taulukoiden viittaukset tuntuvat menevän clone() metodista huolimatta samaan paikkaan.
25.11. | 2 | Tutkittu javaFX:n ominaisuuksia ja korjattu törmäysten havaitseminen, erittäin yksinkertaiseksi.
25.11. |1.5| Toteutettu täysien rivien tarkastus ja tyhjentäminen sekä näiden yläpuolella olevien rivien tuominen alas.
30.11. | 1 | Aloitettu hieman GUI:n koodaamista
01.12. | 9 | GUI:n koodausta. Ongelmaksi on osoittautunut indeksien tarkistus. Jos yrittää samaa aikaa mennä reunoilta yli ja kiertää palikkaa voi tulla ongelmia.
02.12. | 6 | Testausten edistäminen. Uusien checkstyle virheiden korjaaminen, sekä tappelu jarin generoimisen kanssa nyt, kun javaFX tuli käyttöön. Ohjelman pystyy ajamaan laitoksen koneilta (testattu virtuaalityöpöydässä), mutta windowsilla ei saa ajettua ubuntua käyttäen.
07.12. | 8 | Pisteidenlasku sekä tietokannan liittoa osaksi ohjelmaan.
08.12. | 9 | Muutama bugi tuli jälleen esille, sain korjattua. Javadoc pitäisi pystyä generoimaan. Highscore näkymää tehty, vähän ehkä hassu, mutta kelvatkoon. Viikon 6 vaatimukset pitäisi jokseenkin olla kunnossa. Ohjelman pystyy ajaa etätyöpöydällä käyttäen komentoa mvn compile exec...., mutta releasen jarin suorittaminen antaa ongelman SQLITE BUSY database is locked.?
yht. | 43.5| 
