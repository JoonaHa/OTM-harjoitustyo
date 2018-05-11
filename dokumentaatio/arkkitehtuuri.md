# Arkkitehtuurikuvaus
HUOM! Tämä arkkitehtuurikuvaus on alustava ja tulee muutumaan ohjelman kehittyesssä.

## Rakenne

Ohjelman rakenne ilmenee sen pakkauskaaviosta:

<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/Pakkauskaavio.png" width="600">

- Pakkaus _pitchblack.gui_ vastaa sovelluksen graaffisen käyttöliittymän ikkunoista ja pelin hahmojen piirtämisestä.
- Pakkaus _pitchblack.domain_ hallitsee pelilogiikkaa. Esimerkiksi käyttäjän syötteen käsittelemisen ja pelin hahmojen    sijainnin päivittämisen.
- Pakkaus _pitchblack.dao_ käsittelee käyttäjien tuloksia sisältävää tietokantaa.
  - _pitchblack.gui_ hyödyntää pakkausta huipputulosruudun näyttämiseen.
- Pakkaus _pitchblack.database_ on vastuussa tietokantayhteydestä ja luo uuden tietokannan ohjelman juurikansioon jos tietokantaa ei löydy.
  - _pitchblack.dao_ hyödyntää pakkausta tietokantayhteyden muodostamiseen.

## Sovelluslogiikka

Ohjelman logiikka on eristetty käyttöliittymäkoodista. Pelin tiloja hallitsee luokka [GameMotor](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/domain/GameMotor.java). Kyseinen luokka tarkistaa pelin tilan ja päivittää hahmojen sijainnit aina kun metodia [void update()](https://github.com/JoonaHa/OTM-harjoitustyo/blob/36ec3c7a1182219d29c78d6ccf6d271b406ba258/PitchBlack/src/main/java/pitchblack/domain/GameMotor.java#L51) kutsutaan. Luokka on toteutettu singleton patternia hyödyntäen, jotta luokasta on olemassa pelkästään yksi instanssi. Instanssiin pääsee käsiksi metodilla [GameMotor getInstance(...)](https://github.com/JoonaHa/OTM-harjoitustyo/blob/36ec3c7a1182219d29c78d6ccf6d271b406ba258/PitchBlack/src/main/java/pitchblack/domain/GameMotor.java#L40). Parametrina sille annetaan pelaajan hahmo, sekä näppäimistön ja hiiren syöte.
Käyttöliitymästä kutsutaan ainostaan pelien tilojen päivittämistä animaattorin kierroksen aikana ja vahditaan pelin loppumista, jotta loppumisruutu voidaan näyttää.

Hahmot perivtä abstraktin luokan [Sprite](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/domain/Sprite.java), joka tallentaa niiden nopeuden, tilan ja muodon Shape-oliona.

[Score](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/domain/Score.java)-luokkaa hyödynnetään pelaajan pistetuloksen tallentamisessa ja huipputulosten näyttämisessä.

<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/Luokkakaavio(alustava).png" width="220">

* Oheinen sekvenssikaavio esittää, kuinka [Ui](https://github.com/JoonaHa/OTM-harjoitustyo/tree/master/PitchBlack/src/main/java/pitchblack/gui)-luokassa oleva animaattori kutsuu [GameMotor](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/domain/GameMotor.java)-luokan instanssin päivistysmetodia [void update()](https://github.com/JoonaHa/OTM-harjoitustyo/blob/36ec3c7a1182219d29c78d6ccf6d271b406ba258/PitchBlack/src/main/java/pitchblack/domain/GameMotor.java#L51), kun käyttäjä painaa W-näppäintä.
* [Sprite](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/domain/Sprite.java)-luokan perivän [Player](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/domain/Player.java)-olion nopeus ensiksi nollataan ennen kuin se päivitetään ylöspäin etenevää liikettä kuvaavaan arvoon.
* Playerin [update()](https://github.com/JoonaHa/OTM-harjoitustyo/blob/36ec3c7a1182219d29c78d6ccf6d271b406ba258/PitchBlack/src/main/java/pitchblack/domain/Player.java#L67)-metodi päivittää pelaajan hahmon sijaintia uuden nopeuden perusteella.
* Lopuksi [Ui](https://github.com/JoonaHa/OTM-harjoitustyo/tree/master/PitchBlack/src/main/java/pitchblack/gui) piirtää pelaajan hahmon päivitettyyn sijaintiin.

<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/Sekvenssikaavio.png" width="800">

## Käyttöliittymä
- Käyttöliittymä kostuu luokista
  - [Ui](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/gui/Ui.java)
  - [Darkness](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/gui/Darkness.java)
  - [ScoreMeter](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/gui/ScoreMeter.java)
  
  [Ui](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/gui/Ui.java) hyödyntää luokkia pelin pimeyden ja pistemittarin päivittämiseen. Ikkunoiden toteutukset löytyvät [Ui-luokasta](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/gui/Ui.java)
