## Vaatimusmäärittely

Legendaarinen Tetris, tuo tylsien koti-iltojen pelastaja. Pelissä tetrominoista yritetään muodostaa kokonaisia vaakarivejä pelialueelle. Kun kokonainen rivi on koottu, rivi katoaa, ja näin tyhjentää pelialuetta. Pelissä häviää, kun alueelle ei mahdu enää uutta tetrominoa.

### Perustoiminallisuus
#### Aloitus näkymässä:
* Käyttäjä voi aloittaa normaalin pelin painamalla nappia "start".
* Käyttäjä voi mennä aloitusvalikosta tarkastelemaan sovelluskohtaisia pistetilastoja.
#### Pelitilastoissa:
* Näkyvissä sen hetkinen top kolmikko (käyttäjänimi: pisteet)
* Käyttäjä pystyy hakemaan tietyn käyttäjänimen saamia pisteitä käyttäjänimen perusteella.
#### Pelissä:
* Tetromino palikoita pystyy kiertämään ja liikuttamaan sivulta sivulle käyttäjän toimesta.
* Palikat putoaa ylhäältä alas automaattisesti niin pitkälle kuin palikka pystyää putoamaan.
* Käyttäjä voi halutessaan nopeuttaa putoamista.
* Pelissä on pisteenlasku, joka näkyy käyttäjälle.
  * Jokainen uusi tetromino kasvattaa pisteitä 25:llä ja jokainen tyhjä rivi lisää 100 pistettä.
* Tetrominoja seitsemää erilaista, uusi palikka lähtee putoamaan aina, kun edellinen on pysähtynyt.
* Peli päättyy, kun uusi tetromino ei enää mahdu muodostumaan (uusi tetromino muodostuu ylös suurinpiirtein keskelle)
  * Pelin päätyttyä käyttäjä voi halutessaan tallentaa tuloksensa jonkin käyttäjänimen alle.
* Pelin pystyy pysäyttää ja jatkaa, uuden pelin voi aloittaa vaikka vanha olisi käynnissä.
### Mahdollisia lisäominaisuuksia peliin:
* Palikat lähtee pikkuhiljaa putoamaan nopeammin.
* Mitä useamman rivin saa täydeksi kerralla, kasvattaa pistepottia.
* Kaikenlaiset "spesiaali" palikat, esim. pommipalikka
