# OTM-harjoitustyö

Harjoitustyön, sekä viikottaisten tehtävien, *repo* **ohjelmistotekniikan mekaniikat** -kurssia varten.


## Dokumentaatio
* [Vaatimusmaarittely](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)
* [Tuntikirjanpito](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)
* [Arkkitehtuurikuvaus](https://github.com/JoonaHa/OTM-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)
## Komentorivitoiminot

Ohjelman JUnit-testit voi suorittaa komennolla:
```
mvn test
```
Testeistä voi luoda kattavuusraportin komennolla:

```
mvn jacoco:report
```
Raportti on html-dokumentti _target/site/jacoco/index.html_ jota voi tarkastella selaimella. 

Checkstyle tarkistukset suoritetaan komennolla.Tarkistuksen määritykset ovat [checkstyle.xml](https://github.com/JoonaHa/OTM-harjoitustyo/commit/7c27f14ffdb24160d2a4caa23cc9638d1af4f51f) tiedostossa.
```
 mvn jxr:jxr checkstyle:checkstyle
```
Tarkistuksen tulokset löytyvät target/site/checkstyle.html tiedostosta.


