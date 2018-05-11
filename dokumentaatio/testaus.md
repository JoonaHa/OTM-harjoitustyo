# Testaudokumentti

Ohjelman testaus tapahtuu automaattisilla yksikkö- ja integraatiotestein Junittia hyödyntäen. Ohjelman toimintaa järjestelmätasolla on testattu manuaalisesti.

## Yksikkö- ja integraatiotestaus

* Sovelluslogiikan testit löytyvät testikansiosta [domain](https://github.com/JoonaHa/OTM-harjoitustyo/tree/master/PitchBlack/src/test/java/Domain).
Sovelluslogiikka testaavat luokat [BulletTest](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/test/java/Domain/BulletTest.java), [Enemy1Test](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/test/java/Domain/Enemy1Test.java), [Enemy2Test](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/test/java/Domain/Enemy2Test.java), [PlayerTest](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/test/java/Domain/PlayerTest.java) [ScoreTest](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/test/java/Domain/ScoreTest.java) ja [SpriteTest](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/test/java/Domain/SpritetTest.java).
Testit simuloivat luokkien toimintaa pelin pyöriessä. [Sprite-luokan](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/domain/Sprite.java) perivien luokkien testauksessa painotetaan sijainnin, nopeuksien ja kääntymisen toimimista. Myös rajoituksia testataan, esimerkiksi Pelaajan poistumista ulos ruudusta.

* Dao-luokkien testit löytyvät kansiosta [Dao](https://github.com/JoonaHa/OTM-harjoitustyo/tree/master/PitchBlack/src/test/java/Dao)
Testit testaavat samalla tietokannan luomista, sekä tulosten tallentamista ja noutamista. Testejä tehdään normaalin sovelluksen käyttään tietokantaan, mutta testitulokset poistetaan testien lopuksi. Testit muodostavat myös väliaikaistietokannan, jolla testataan tietokanna luomista ja sen taulujen alustamista.

## Testauskattavuus
* Oheisessa kuvassa näkyy ohjelman testikattavuus
* Testaamatta jäi monimutkainen [GameMotor-luokka](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/domain/GameMotor.java), joka on vastuussa pelint tilojen päivittämisestä.

<img src="https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/ScreenShot_Jacoco.png" width="800">

## Järjestelmätestaus
Ohjelmaa on testattu vain Linux-ympäristöissä. Tilanteissa, jossa ohjelman tarvitsemaa tietokantaa ei ollut olemassa tai ohjelma oli luonot sen automaattisesti.

## Toiminnallisuus
Ohjelma toteuttama toiminallisuus vastaa [vaatimusmäärittelyä](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md). Additional features sisältää jatkokehitysideoita.

## Sovellukseen jääneet laatuongelmat
* Testien pitäisi kattaa myös [GameMotor-luokka](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/PitchBlack/src/main/java/pitchblack/domain/GameMotor.java) ja sen useat tilat. 
* Vihollisten käyttäytymislogiikan testejä pitäisi myös laajentaa
* Sovellusta ei ole testattu tilanteessa jossa tietokantaan ei ole kirjoitusoikeuksia
