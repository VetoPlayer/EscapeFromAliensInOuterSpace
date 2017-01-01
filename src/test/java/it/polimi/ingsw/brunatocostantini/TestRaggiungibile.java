package it.polimi.ingsw.brunatocostantini;

import giocatori.Alieno;
import giocatori.Giocatore;
import giocatori.Umano;

import org.junit.Assert;
import org.junit.Test;

import board.Casella;
import board.Effetto;
import board.Mappa;

/**Test of the method "raggiungibile" and of the private methods it calls ("indiceValido","lecita"..)
 * @author Lollo
 *
 */
public class TestRaggiungibile {

	Mappa mappa= Mappa.getMappa();
	
	/**
	 * Test of the movement of a human in the sector above, which is void and unreachable
	 */
	@Test
	public void TestRaggiungibileUmanoAltoFalse(){
		
		Casella partenza = mappa.getCasella(7,9); //sarebbe I07
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "I06";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==9);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==7);
	}
	
	/**
	 * Test of the movement of a human in the sector above
	 */
	@Test
	public void TestRaggiungibileAlienoAltoTrue(){
		
		Casella partenza = mappa.getCasella(7,7); //sarebbe G07
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "G06";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==7);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==6);
	}
	
	/**
	 * Test of the movement of a superalien in two sector above and one sector at left
	 */
	@Test
	public void TestRaggiungibileSuperAlienoAlto(){
		
		Casella partenza = mappa.getCasella(12,8); //sarebbe H12
		Giocatore giocatore = new Alieno(partenza,1);
		((Alieno) giocatore).setSuperAlieno(true);
		String coordinate = "G11";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==7);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==11);
	}
	
	/**
	 * Test of the movement of an alien in two sector above in the nord-east direction, starting in an odd sector
	 */
	@Test
	public void TestRaggiungibileAlienoColonnaPariAltoSX(){
		
		Casella partenza = mappa.getCasella(2,4); //sarebbe D02
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "B01";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==2);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==1);
	}
	
	/**
	 * Test of the movement of a human in the sector below
	 */
	@Test
	public void TestRaggiungibileUmanoBasso(){
		
		Casella partenza = mappa.getCasella(7,7); //sarebbe I07
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "G08";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==7);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==8);
	}
	
	/**
	 * Test of the movement of a human in the nord-est sector, starting in an odd sector
	 */
	@Test
	public void TestRaggiungibileUmanoColonnaDispariAltoDX(){
		
		Casella partenza = mappa.getCasella(7,7); //sarebbe G07
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "H06";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==8);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==6);
	}
	
	/**
	 * Test of the movement of a human in the nord-ovest sector, starting in an odd sector
	 */
	@Test
	public void TestRaggiungibileUmanoColonnaDispariAltoSX(){
		
		Casella partenza = mappa.getCasella(7,7); //sarebbe G07
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "F06";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==6);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==6);
	}
		
	/**
	 * Test of the movement of a human in the sud-est sector, starting in an odd sector
	 */
	@Test
	public void TestRaggiungibileUmanoColonnaDispariBassoDX(){
		
		Casella partenza = mappa.getCasella(7,7); //sarebbe G07
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "H07";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.NESSUN_EVENTO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==8);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==7);
	}
	
	/**
	 * Test of the movement of a human in the sud-ovest sector, starting in an odd sector
	 */
	@Test
	public void TestRaggiungibileUmanoColonnaDispariBassoSX(){
		
		Casella partenza = mappa.getCasella(7,7); //sarebbe G07
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "F07";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==6);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==7);
	}
	
	/**
	 * Test of the movement of a human in the nord-est sector, starting in an even sector
	 */
	@Test
	public void TestRaggiungibileUmanoColonnaPariAltoDX(){
		
		Casella partenza = mappa.getCasella(5,22); //sarebbe V05
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "W05";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.NESSUN_EVENTO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==23);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==5);
	}
	
	/**
	 * Test of the movement of a human in the sud-est sector, starting in an even sector
	 */
	@Test
	public void TestRaggiungibileUmanoColonnaPariBassoDX(){
		
		Casella partenza = mappa.getCasella(5,22); //sarebbe V05
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "W06";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.NESSUN_EVENTO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==23);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==6);
	}
	
	/**
	 * Test of the movement of a human in the nord-ovest sector, starting in an even sector
	 */
	@Test
	public void TestRaggiungibileUmanoColonnaPariAltoSX(){
		
		Casella partenza = mappa.getCasella(5,22); //sarebbe V05
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "U05";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.NESSUN_EVENTO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==21);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==5);
	}
	/**
	 * Test of the movement of a human in the sud-ovest sector, starting in an even sector
	 */
	@Test
	public void TestRaggiungibileUmanoColonnaPariBassoSX(){
		
		Casella partenza = mappa.getCasella(5,22); //sarebbe V05
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "U06";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==21);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==6);	
	}
	
	/**
	 * Test the private method indicevalido, exploring a sector before the first column and the first row of the board
	 */
	@Test
	public void TestSpostamentoIndiceNonValidoColonnaERigaValoreBasso(){
		
		Casella partenza = mappa.getCasella(1,3);//C01
		Giocatore giocatore = new Alieno(partenza,1);
		((Alieno) giocatore).setSuperAlieno(true);
		String coordinate = "A03";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==1);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==3);	
	}
	
	/**
	 * Test the private method indicevalido, exploring a sector above the last column and the last row of the board
	 */
	@Test
	public void TestSpostamentoIndiceNonValidoColonnaERigaValoreAlto(){
		
		Casella partenza = mappa.getCasella(14,22);//V14
		Giocatore giocatore = new Alieno(partenza,1);
		((Alieno) giocatore).setSuperAlieno(true);
		String coordinate = "W12";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.NESSUN_EVENTO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==23);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==12);	
	}
	
	/**
	 * Test the private method indicevalido, exploring a sector before the first column and above the last row of the board
	 */
	@Test
	public void TestSpostamentoIndiceNonValidoColonnaValoreBassoERigaValoreAlto(){
		
		Casella partenza = mappa.getCasella(14,2);//B14
		Giocatore giocatore = new Alieno(partenza,1);
		((Alieno) giocatore).setSuperAlieno(true);
		String coordinate = "A12";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.NESSUN_EVENTO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==1);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==12);	
	}
}
