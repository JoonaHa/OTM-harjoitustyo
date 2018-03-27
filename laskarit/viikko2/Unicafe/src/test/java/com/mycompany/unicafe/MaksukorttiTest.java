package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void konstruktoriAsettaaSaldonOIkein() {
        assertTrue(kortti.saldo() == 10);
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(15);
        assertTrue(kortti.saldo() == 25);
    }
    
    @Test
    public void SaldoVaheneeOikein() {
        Boolean tosi = kortti.otaRahaa(10);
        assertTrue(kortti.saldo() == 0 && tosi);
    }
    
    @Test
    public void rahaaEiOtetaJosLiianVahan() {
        Boolean vaarin = kortti.otaRahaa(20);
        assertTrue(kortti.saldo() == 10 && !vaarin);
    }
    
    @Test
    public void toStringToimiiOikein() {
        kortti.lataaRahaa(1005);
        assertEquals("saldo: 10.15", kortti.toString());
    }
}
