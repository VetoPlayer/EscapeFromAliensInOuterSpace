package it.polimi.ingsw.brunatocostantini;

import giocatori.Giocatori;
import giocatori.Umano;

import org.junit.Assert;
import org.junit.Test;

import board.CasellaPericolosa;
import board.CasellaUmani;
import carte.CartaEquipaggiamento;
import carte.Oggetto;

/**
 * @author Lollo
 * This class test the methods in the class CarteEquipaggiamento, apart the implementation of the card "attacco"
 */
public class TestCarteEquipaggiamento {

	CasellaUmani spawnUmani = new CasellaUmani(8,12);
	CasellaPericolosa casella = new CasellaPericolosa(11,4);
	Umano umano = new Umano(casella,1);
	public static final int PASSIADRENALINA=2;
	Giocatori giocatori = new Giocatori();
	
	/**
	 * Test of the method "teletrasporto"
	 */
	@Test
	public void TestTeletrasporto(){
		
		Oggetto oggetto= Oggetto.TELETRASPORTO;
		CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto);
		equip.teletrasporto(umano, spawnUmani);
		Assert.assertTrue("The human doesn't land on the initial sector",umano.getPosizioneAttuale().equals(spawnUmani));
	}
	
	/**
	 * Test of the method "adrenalina"
	 */
	@Test
	public void TestAdrenalina(){
		
		Oggetto oggetto= Oggetto.ADRENALINA;
		CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto);
		equip.adrenalina(umano);
		Assert.assertTrue("The human doesn't start to move two steps a turn",umano.getNumPassi()==PASSIADRENALINA);
	}
	
	/**
	 * Test of the method "sedativo"
	 */
	@Test
	public void TestSedativo(){
		
		Oggetto oggetto= Oggetto.SEDATIVO;
		CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto);
		equip.sedativo(umano);
		Assert.assertTrue("The human can't apply the effect of sedativo",umano.hasSedativo());
	}
	
	/*public void TestLuceCaselleAdiacentiUgualeAUno(){
		
		Oggetto oggetto=Oggetto.LUCE;
		CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto);
		Mappa mappa =Mappa.getMappa();
		Casella casellaluce = mappa.getCasella(9,3);//C09
		Umano umano = new Umano(mappa.getCasella(9,3),1,filter, humanturn, alienturn);
		Alieno alieno = new Alieno(mappa.getCasella(9,2),2,filter, humanturn, alienturn);
		Alieno alieno2 = new Alieno(mappa.getCasella(9,3),3,filter, humanturn, alienturn);
		Umano umano2 = new Umano(mappa.getCasella(9,2),4,filter, humanturn, alienturn);
		equip.luce(casellaluce,giocatori,mappa);
		Assert.assertTrue(umano.);
	}*/
	
}
