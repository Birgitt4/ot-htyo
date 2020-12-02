## Vaatimusmäärittely

Legendaarinen Tetris tuo tylsien koti-iltojen pelastaja. Pelissä tetrominoista yritetään muodostaa kokonaisia vaakarivejä pelialueelle. Kun kokonainen rivi on koottu, rivi katoaa, ja näin tyhjentää pelialuetta. Pelissä häviää, kun alueelle ei mahdu enää uutta tetrominoa.

### Perustoiminallisuus
#### Aloitus näkymässä:
* Käyttäjä voi aloittaa normaalin pelin (Tehty)
* Käyttäjä voi tarkastella omia pistetilastojaan
* Mahdolliset vaikeammat tasot?
#### Pelissä:
* Tetromino palikoita pystyy kiertämään ja liikuttamaan sivulta sivulle käyttäjän toimesta (Tehty, mahdollinen arrayindexoutofboundexception, joka tulee esiin pelin pysähtymisenä. Tällöin voi aloittaa uuden pelin. Itse ainakin saan välillä aikaiseksi, kun yrittää mennä yli sivuilta ja rämpyttää kirtonappeja. Metodeissa kyllä tarkistukset, mutta onko vain liian hidas ja tekee samanaikaisesti jotain?)
* Palikat putoaa ylhäältä alas automaattisesti niin pitkälle kuin palikka pystyää putoamaan (Tehty)
* Käyttäjä voi halutessaan nopeuttaa putoamista (Tehty)
* Pelissä on pisteenlasku, joka näkyy käyttäjälle. (Pisteiden tuloperusteet!)
* Tetrominoja seitsemää erilaista, uusi palikka lähtee putoamaan aina, kun edellinen on pysähtynyt (Tehty)
### Mahdollisia lisäominaisuuksia peliin:
* Tuhoutumattomat peliä vaikeuttavat palikat
* Kaikenlaiset "spesiaali" palikat, esim. pommipalikka
* (Samalla näppäimistöllä pelattava 1v1 peli)
