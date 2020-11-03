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
    public void saldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
    }
    @Test
    public void saldonLatausToimii() {
        kortti.lataaRahaa(20);
        assertEquals(30, kortti.saldo());
    }
    @Test
    public void saldoVaheneeKunRahaRiittaa() {
        kortti.otaRahaa(4);
        assertEquals("saldo: 0.6", kortti.toString());
    }
    @Test
    public void saldoEiMuutuKunRahaEiRiita() {
        kortti.otaRahaa(11);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
}
