/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author birgi
 */
public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(400);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void kassaPohjaOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void lounaitaEiVielaMyyty() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty()+kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void kassaKasvaaEdullisessalounaassa() {
        int vaihto = kassa.syoEdullisesti(500);
        assertEquals("kassassa 100240 ja vaihtorahaa 260", "kassassa "+kassa.kassassaRahaa()+" ja vaihtorahaa "+vaihto);
    }
    @Test
    public void rahaEiRiitaEdulliseenLounaaseen() {
        int vaihto = kassa.syoEdullisesti(200);
        assertEquals("kassassa 100000 ja vaihtorahaa 200", "kassassa "+kassa.kassassaRahaa()+" ja vaihtorahaa "+vaihto);
    }
    @Test
    public void rahaEiRiitaLounaatEiKasva() {
        int vaihto = kassa.syoEdullisesti(200);
        assertEquals("edullisia myyty 0 ja takaisin 200", "edullisia myyty "+ kassa.edullisiaLounaitaMyyty()+" ja takaisin "+vaihto);
    }
    @Test
    public void edullisetLounaatKasvaaKunRahaRiitti() {
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(240);
        assertEquals("edullisia myyty 2", "edullisia myyty "+kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void kassaKasvaaMaukkaassalounaassa() {
        int vaihto = kassa.syoMaukkaasti(500);
        assertEquals("kassassa 100400 ja vaihtorahaa 100", "kassassa "+kassa.kassassaRahaa()+" ja vaihtorahaa "+vaihto);
    }
    @Test
    public void rahaEiRiitaMaukkaaseenLounaaseen() {
        int vaihto = kassa.syoMaukkaasti(200);
        assertEquals("kassassa 100000 ja vaihtorahaa 200", "kassassa "+kassa.kassassaRahaa()+" ja vaihtorahaa "+vaihto);
    }
    @Test
    public void rahaEiRiitaMaukasLounaatEiKasva() {
        int vaihto = kassa.syoMaukkaasti(200);
        assertEquals("maukkaita myyty 0 ja takaisin 200", "maukkaita myyty "+ kassa.maukkaitaLounaitaMyyty()+" ja takaisin "+vaihto);
    }
    @Test
    public void maukkaatLounaatKasvaaKunRahaRiitti() {
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        assertEquals("maukkaita myyty 2", "maukkaita myyty "+kassa.maukkaitaLounaitaMyyty());
    }
    
    //Korttitestit
    @Test
    public void korttiOstoToimiiKunRahaRiittiEdullinen() {
        boolean onnistui = kassa.syoEdullisesti(kortti);
        assertEquals("veloitettu true ja kortilla enää 160", "veloitettu "+onnistui+" ja kortilla enää "+kortti.saldo());
    }
    @Test
    public void korttiOstoToimiiKunRahaRiittiMaukas() {
        boolean onnistui = kassa.syoMaukkaasti(kortti);
        assertEquals("veloitettu true ja kortilla enää 0", "veloitettu "+onnistui+" ja kortilla enää "+kortti.saldo());
    }
    @Test
    public void edullisetKasvaaKorttiOstossa() {
        kassa.syoEdullisesti(kortti);
        assertEquals("edullisia ostettu 1", "edullisia ostettu "+kassa.edullisiaLounaitaMyyty());
    }
    @Test
    public void maukkaatKasvaaKorttiOstossa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals("maukkaita ostettu 1", "maukkaita ostettu "+kassa.maukkaitaLounaitaMyyty());
    }
    @Test
    public void kunRahatEiRiitaEdulliseen() {
        kassa.syoEdullisesti(kortti);
        boolean onnistui = kassa.syoEdullisesti(kortti);
        assertEquals("edullisia ostettu 1 ja kortilla rahaa 160 ja palautettiin false", "edullisia ostettu "+kassa.edullisiaLounaitaMyyty()+" ja kortilla rahaa "+kortti.saldo()+" ja palautettiin "+onnistui);
    }
    @Test
    public void kunRahatEiRiitaMaukkaaseen() {
        kassa.syoEdullisesti(kortti);
        boolean onnistui = kassa.syoMaukkaasti(kortti);
        assertEquals("maukkaita ostettu 0 ja kortilla rahaa 160 ja palautettiin false", "maukkaita ostettu "+kassa.maukkaitaLounaitaMyyty()+" ja kortilla rahaa "+kortti.saldo()+" ja palautettiin "+onnistui);
    }
    @Test
    public void kassaEiKasvaKorttiOstossa() {
        kortti = new Maksukortti(800);
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals("kassassa 100000", "kassassa "+kassa.kassassaRahaa());
    }
    
    //lataustestit
    @Test
    public void rahaaLadatessaKassaJaKorttiKasvattaaSaldoa() {
        kassa.lataaRahaaKortille(kortti, 200);
        assertEquals("kassassa 100200 ja kortilla 600", "kassassa "+kassa.kassassaRahaa()+" ja kortilla "+kortti.saldo());
    }
    @Test
    public void negatiivistaSummaaEiVoiLadata() {
        kassa.lataaRahaaKortille(kortti, -20);
        assertEquals("kassassa 100000 ja kortilla 400", "kassassa "+kassa.kassassaRahaa()+" ja kortilla "+kortti.saldo());
    }
}
