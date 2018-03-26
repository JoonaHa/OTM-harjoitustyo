/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mina
 */
public class MaksukorttiTest {
    Maksukortti kortti;


    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    
    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
        
    }
    
    @Test
    public void syoEdullisestiVahentaaSaldoaOikein() {
        
        kortti.syoEdullisesti();
        
        assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
    }
    
    @Test
    public void syoMaukkaastiVahentaaSaldoaOikein() {

        kortti.syoMaukkaasti();
        
        assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
        
    }
    
    @Test
    public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
        
        
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        // Kortilla 2 euroa
        kortti.syoEdullisesti();
        
        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }
    
     @Test
    public void syoMaukkkaastiEiVieSaldoaNegatiiviseksi() {
        
        
        kortti.syoEdullisesti();
        kortti.syoEdullisesti();
        kortti.syoEdullisesti();
        //Kortilla 2.5 euroa
        kortti.syoMaukkaasti();
        
        assertEquals("Kortilla on rahaa 2.5 euroa", kortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataRahaa(){
        kortti.lataaRahaa(25);
        assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
        
    }
    
    @Test
    public void kortinSaldoEiYlitaMaksimiarvoa(){
        kortti.lataaRahaa(200);
        assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
    }
    
    @Test
    public void kortilleEiVoiLadataNegatiivisiaSummia() {
        kortti.lataaRahaa(-5);
          assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }
    
    @Test
    public void syoEdullisestiKunSaldoRiittaaVainEdulliseen(){
        kortti.syoEdullisesti();
        kortti.syoEdullisesti();
        kortti.syoEdullisesti();
        //Kortilla 2.5 euroa
        kortti.syoEdullisesti();
        
       assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
    }
    
    @Test
    public void syoMaukkaastiKunSaldoRiittaaVainMaukkaaseen(){
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        kortti.lataaRahaa(2);
        //Kortilla 4.0 euroa
        kortti.syoMaukkaasti();
        
       assertEquals("Kortilla on rahaa 0.0 euroa", kortti.toString());
    }
}
