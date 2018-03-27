package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kassa;
    Maksukortti kortti;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(500);
    }

    //konstruktorin testit
    @Test
    public void konstruktoriAlustaaOikeanSaldon() {
        int rahaa = 100000;
        assertEquals(rahaa, kassa.kassassaRahaa());
    }

    @Test
    public void konstruktoriAlustaaOikeanEdulliset() {

        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void konstruktoriAlustaaOikeanMaukkaat() {

        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    // käteismaksu kasvattaa kassan rahamäärää.
    @Test
    public void syoEdullisestiKateinenKassaRahaa() {
        kassa.syoEdullisesti(240);
        assertEquals(100000 + 240, kassa.kassassaRahaa());
    }

    @Test
    public void syoMaukkaastiKateinenKassaRahaa() {
        kassa.syoMaukkaasti(400);
        assertEquals(100000 + 400, kassa.kassassaRahaa());
    }

    // vaihtorahan testit maksettaessa käteisellä
    @Test
    public void syoEdullisestiVaihtoraha() {
        int maksu = 425;
        int edullinen = 240;
        int vaihtoraha = kassa.syoEdullisesti(maksu);
        assertEquals(vaihtoraha, (maksu - edullinen));

    }

    @Test
    public void syoMaukkaastiVaihtoraha() {
        int maksu = 425;
        int maukas = 400;
        int vaihtoraha = kassa.syoMaukkaasti(maksu);
        assertEquals(vaihtoraha, (maksu - maukas));

    }

    // Myyntiluvut maksettaessa käteisellä
    @Test
    public void syoEdullisestiKateinenEdullisiaMyyty() {
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(240);

        assertEquals(kassa.edullisiaLounaitaMyyty(), 3);

    }

    @Test
    public void syoMaukkaastiKateinenMaukkaitaMyyty() {
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);

        assertEquals(kassa.maukkaitaLounaitaMyyty(), 3);

    }

    // rahan palauttaminen käteismaksun ollessa liian vähän
    @Test
    public void syoEdullisestiLiianPieniMaksuVaihtoraha() {
        int vaihtoraha = kassa.syoEdullisesti(239);
        assertEquals(vaihtoraha, 239);
    }

    @Test
    public void syoMaukkaastiLiianPieniMaksuVaihtoraha() {
        int vaihtoraha = kassa.syoMaukkaasti(399);
        assertEquals(vaihtoraha, 399);
    }

    //myyntilukujen muuttumattomuus käteismaksun ollessa liian vähän
    @Test
    public void syoEdullisestiLiiaPieniMaksuEdullisiaMyyty() {
        kassa.syoEdullisesti(239);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void syoMaukkaastiLiiaPieniMaksuMaukkaitaMyyty() {
        kassa.syoEdullisesti(339);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    // kassan saldo ei muutu käteismaksunmaksun ollessa liian vähän
    @Test
    public void syoEdullisestiLiiaPieniMaksuKassaRahaaEiMuutu() {
        kassa.syoEdullisesti(239);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void syoMaukkaastiLiiaPieniMaksuKassaRahaaEiMuutu() {
        kassa.syoMaukkaasti(339);
        assertEquals(100000, kassa.kassassaRahaa());
    }

    // Korttillamaksamisen testit alkavat
    // kortilla maksaminen veloittaa kortilta jos tarpeeksi saldoa
    @Test
    public void syoEdullisestiKortillaMaksaminenVeloittaa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(500 - 240, kortti.saldo());

    }

    @Test
    public void syoMaukkaastiKortillaMaksaminenVeloittaa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(500 - 400, kortti.saldo());

    }
    // kassan rahamäärä ei muutu korttia käytettäessä
    @Test
    public void syoEdullisestiKortillaMaksaminenEiMuutaKassaRahaa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());

    }
    
    @Test
    public void syoMaukkaastiKortillaMaksaminenEiMuutaKassaRahaa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());

    }
    //kortilta maksaminen palauttaa true jos tarpeeksi saldoa

    @Test
    public void syoEdullisestiKortillaMaksaminenPalauttaaTrue() {
        assertEquals(true, kassa.syoEdullisesti(kortti));

    }

    @Test
    public void syoMaukkaastiKortillaMaksaminenPalauttaaTrue() {
        assertEquals(true, kassa.syoMaukkaasti(kortti));

    }

    // Myyntiluvut kasvavat kun saldo on tarpeeksi 
    @Test
    public void syoEdullisestiKortillaEdullisiaMyyty() {
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());

    }

    @Test
    public void syoMaukkaastiKortillaEdullisiaMyyty() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());

    }

    // Liia pieni saldo. Ei veloiteta kortilta
    @Test
    public void syoEdullisestiKortillaLiianPieniSaldoEiVeloiteta() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        // saldo 20
        kassa.syoEdullisesti(kortti);
        assertEquals(20, kortti.saldo());

    }

    @Test
    public void syoMaukkaastiKortillaLiianPieniSaldoEiVeloiteta() {
        kassa.syoMaukkaasti(kortti);
        // saldo 100
        kassa.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());

    }
    // Myynti luvut eivät muutu saldon ollessa liian pieni
    @Test
    public void syoEdullisestiKortillaLiianPieniSaldoEdullisiaMyytyEiMuutu() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        // saldo 20 // myyty 2
        kassa.syoEdullisesti(kortti);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());

    }

    @Test
    public void syoMaukkastiKortillaLiianPieniSaldoMaukkaitaMyytyEiMuutu() {
        kassa.syoMaukkaasti(kortti);
        // saldo 100 
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());

    }
    
        @Test
    public void syoEdullisestiKortillaLiianPieniSaldoPalauttaaFalse() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        // saldo 20 
        assertEquals(false, kassa.syoEdullisesti(kortti));

    }
    
        @Test
    public void syoMaukkastiKortillaLiianPieniSaldoPalauttaaFalse() {
        kassa.syoMaukkaasti(kortti);
        // saldo 100 
        assertEquals(false, kassa.syoMaukkaasti(kortti));

    }
    @Test
    public void lataaRahaaKortilleKortinSaldoMuuttuu() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void lataaRahaaKortilleKassanRahaKasvaa() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(100000 + 500, kassa.kassassaRahaa());
    }
    
    @Test
    public void lataaRahaaKortilleNegatiivinenArvoSaldoEiMuuttuu() {
        kassa.lataaRahaaKortille(kortti, -500);
        assertEquals(500, kortti.saldo());
    }
    
    @Test
    public void lataaRahaaKortilleNegatiivinenKassanRahaEiMuuttuu() {
        kassa.lataaRahaaKortille(kortti, -500);
        assertEquals(100000 , kassa.kassassaRahaa());
    }

}
