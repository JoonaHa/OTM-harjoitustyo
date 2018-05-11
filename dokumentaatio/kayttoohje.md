## Ohjelman käynnistäminen
Ohjelma käynnistyy komennolla

```
java -jar OtmPitchBlackv1.0.jar
```
Käynnistyessään ohjelma luo sijaitsemaansa kansioon tietokannan *highScores.db*.Tietokanta säilyttää pelaajien tuloksia ja jos se poistetaan niin sen mukana myös poistuvat huipputulokset.

## Aloitusvalikkko

Ohjelman käynnistämisen jälkeen aukeaa aloitusvalikko:

<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShot_Menu.png" width="800">

* HIGH SCORES-painikkeesta voit tarkastella pelaajien tuloksia.
* NEW GAME-painike aloittaa uuden pelin.

## Tulosten tarkastelu
* HIGH SCORES-painikkeen takaa löytyvässä ikkunassa voit tarkastella tuloksia ja muuttaa niiden esitysjärjestystä painamalla ikkunan yläreunassa olevia sarakkeita.
* Alareunassa olevasta BACK-painikkeesta pääsee takaisin aloitusvalikkoon.
<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShot_Scores.png.png" width="800">

## Pelin aloittaminen
* NEW GAME-painiketta painamalla pääsee pelikentälle.
<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShot_Game.png" width="800">

* Hahmon taskulamppu seuraa hiiren kursorin liikettä **kunhan kursori pysyy ikkunan reunojen sisäpuolella**.
* Ampuminen tapahtuu hiiren vasemmasta näppäimestä.

* Hahmoa pystyy liikuttamaan näppäimistä:


|Näppäin | Toiminto |
|:---:|:-----:|
|  W  | ylös |
|  S  | alas |
|  A  | vasemmalle |
| D   | oikealle |


## Pelin Pelaaminen
* Pelissä yritetään tuhota mahdollisimman monta vihollista.
* Vihollisen tuhoamisesta saa aina yhden pisteen.
- Pisteet näkyvät oikeassa alakulmassa.
* Viholliset syntyvät aalloissa. Sinun pitäää tuhota kaikki viholliset ennen kuin seuraava aalto syntyy.
* Jos vihollinen koskee pelaajan hahmoon peli päättyy.

* Vihollisia on kahta eri tyyppiä
- Ensinmäinen tyyppi on rauhallisempi ja huomaa pelaajan vasta sopivalla etäisyydellä.
<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShotEnemy2.png" width="800">

- Toinen tyyppi on agressiivinen ja tulee suoraan kohti.
<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShotEnemy1.png" width="800">

* Pelin saa laitettua tauolle *Escape*-näppäimestä.
- Pelaamista voi jatkaa RESUME-painikkessta
- Käynnissä olevan pelin voi lopettaa QUIT-painikkessta. Peli palaa takaisin aloitusruutuun
<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShot_PauseMenu.png" width="800">

## Tulosten tallentaminen
* Pelaajan hahmon tuhouduttua voit tallentaa tuloksesi HIGH SCORES-luetteoloon
<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShot_AddHS.png" width="800">

* Tekstikenttään voit kirjoittaa haluamasi nimimerkin
* Nimimerkki tallentuu tietokantaa ADD-painikkeesta. MAIN MENU-painike palaa takasin aloitusruutuun

* Antamasi nimimerkki ei koostua pelkistä välilyönneistä, eikä saa sisältää erikoismerkkejä tai ääkkösiä.
* Nimimerkin maksimipituus saa olla 100 merkkiä.
- Ohjelma antaa palautetta jos nimimerkki ei kelpaa
<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShot_HSFeedBack.png" width="800">

<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShot_HSFeedBack2.png" width="800">
