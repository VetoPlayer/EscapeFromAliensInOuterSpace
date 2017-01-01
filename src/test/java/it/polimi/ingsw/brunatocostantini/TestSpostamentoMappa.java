package it.polimi.ingsw.brunatocostantini;

import giocatori.Alieno;
import giocatori.Giocatore;
import giocatori.Umano;

import org.junit.Assert;
import org.junit.Test;

import board.Casella;
import board.CasellaScialuppa;
import board.Effetto;
import board.Mappa;
import controller.Gioco;

/**Test of the method "spostamento" and of the private methods it calls ("raggiungibile","lecita")
 * @author Lollo
 *
 */
public class TestSpostamentoMappa {
	
	Mappa mappa= Mappa.getMappa();
		
	/**
	 * Test of the movement of a human to an escape sector
	 */
	@Test
	public void TestSpostamentoUmanoScialuppa(){
		
		Casella partenza = mappa.getCasella(3,3); //sarebbe C03
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "B02";//scialuppa
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SCIALUPPA.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==2);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==2);
		
	}
	
	/**
	 * Test of the movement of a human to an escape sector already used by another human
	 */
	@Test
	public void TestSpostamentoUmanoScialuppaUtilizzata(){
		
		Casella partenza = mappa.getCasella(3,3); //sarebbe C03
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "B02";//scialuppa
		Casella destinazione = mappa.convertitore(coordinate);
		((CasellaScialuppa) destinazione).setUtilizzata(true);
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(destinazione.effettoCasella().equals(effetto));		
	}
		
	/**
	 * Test of the movement of an alien to an escape sector
	 */
	@Test
	public void TestSpostamentoAlienoScialuppa(){
		
		Casella partenza = mappa.getCasella(3,4); //sarebbe D03
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "B02";//scialuppa
		Casella destinazione = mappa.convertitore(coordinate);
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(destinazione.effettoCasella().equals(effetto));
	}	
	
	/**
	 * Test of the movement of an alien to the sector where he already is. This is not permitted by the game
	 */
	@Test
	public void TestSpostamentoAlienoStessaCasellaNonValida(){
		
		Casella partenza = mappa.getCasella(3,4); //sarebbe D03
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "D03";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==4);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==3);
	}	
	
	/**
	 * Test of the movement of a player to the initial sector of the aliens (move not permitted)
	 */
	@Test
	public void TestSpostamentoSpawnAlieni(){
		
		Casella partenza = mappa.getCasella(5,12); //sarebbe L05
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "L06";
		Casella destinazione = mappa.convertitore(coordinate);
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(destinazione.effettoCasella().equals(effetto));	
	}	
	
	/**
	 * Test of the movement of a player to the initial sector of the humans (move not permitted)
	 */
	@Test
	public void TestSpostamentoSpawnUmani(){
		
		Casella partenza = mappa.getCasella(9,12); //sarebbe L09
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "L08";
		Casella destinazione = mappa.convertitore(coordinate);
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(destinazione.effettoCasella().equals(effetto));	
	}	
	
	/**
	 * Test of the movement of a player to a void sector (move not permitted)
	 */
	@Test
	public void TestSpostamentoCasellaVuota(){
		
		Casella partenza = mappa.getCasella(9,12); //sarebbe L09
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "L07";
		Casella destinazione = mappa.convertitore(coordinate);
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(destinazione.effettoCasella().equals(effetto));	
	}	
	
	/**
	 * Test of the movement of a human on a dangerous sector
	 */
	@Test
	public void TestSpostamentoUmanoCasellaNonSicura(){
		
		Casella partenza = mappa.getCasella(3,3); //sarebbe C03
		Giocatore giocatore = new Umano(partenza,1);
		String coordinate = "D02";//pesca_suono
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertSame(Effetto.PESCA_SUONO,effetto);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==4);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==2);
	}
	
	/**
	 * Test of the movement of an alien on a non dangerous sector
	 */
	@Test
	public void TestSpostamentoAlienoCasellaSicura(){
		
		Casella partenza = mappa.getCasella(12,3); //sarebbe C12
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "B10";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertSame(Effetto.NESSUN_EVENTO,effetto);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==2);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==10);
	}
	
	/**
	 * Test of the movement of a "superalien" to a non dangerous sector
	 */
	@Test
	public void TestSpostamentoSuperAlienoCasellaSicura(){
		
		Casella partenza = mappa.getCasella(10,4); //sarebbe D10
		Giocatore giocatore = new Alieno(partenza,1);
		((Alieno) giocatore).setSuperAlieno(true);
		String coordinate = "A09";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertSame(Effetto.NESSUN_EVENTO,effetto);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==1);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==9);
	}
	
	/**
	 * Test of the movement of a "superalien" to an unreachable sector
	 */
	@Test
	public void TestSpostamentoSuperAlienoCasellaNonSicuraFalse(){
		
		Casella partenza = mappa.getCasella(6,12); //sarebbe L06
		Giocatore giocatore = new Alieno(partenza,1);
		((Alieno) giocatore).setSuperAlieno(true);
		String coordinate = "M08";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==12);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==6);
	}
	
	/**
	 * Test of the movement of a "superalien" to a void sector
	 */
	@Test
	public void TestSpostamentoSuperAlienoCasellaVuota(){
		
		Casella partenza = mappa.getCasella(4,3); //sarebbe C04
		Giocatore giocatore = new Alieno(partenza,1);
		((Alieno) giocatore).setSuperAlieno(true);
		String coordinate = "D01";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertSame(Effetto.ERRORE,effetto);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==3);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==4);
	}
	
	/**
	 * Test of the movement of a "superalien" to a crossable sector, reachable only passing next to a void sector between the initial and ending sector
	 */
	@Test
	public void TestSpostamentoSuperAlienoConOstacoloCasellaVuotaTrue(){
		
		Casella partenza = mappa.getCasella(5,9); //sarebbe I05
		Giocatore giocatore = new Alieno(partenza,1);
		((Alieno) giocatore).setSuperAlieno(true);
		String coordinate = "I07";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.PESCA_SUONO.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==9);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==7);
	}
	
	/**
	 * Test of the movement of a "superalien" to a crossable sector, unreachable even if passing next to a void sector between the initial sector and the hoped destination
	 */
	@Test
	public void TestSpostamentoAlienoConOstacoloCasellaVuotaFalse(){
		
		Casella partenza = mappa.getCasella(5,9); //sarebbe I05
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "I07";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==9);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==5);
	}
	
	/**
	 * Test of the movement of an alien to a unreachable too distant sector
	 */
	@Test
	public void TestSpostamentoAlienoFalse(){
		
		Casella partenza = mappa.getCasella(6,12); //sarebbe L06, spawnalieni
		Giocatore giocatore = new Alieno(partenza,1);
		String coordinate = "O07";
		Effetto effetto = mappa.spostamento(giocatore, coordinate);
		Assert.assertTrue(Effetto.ERRORE.equals(effetto));
		Assert.assertTrue(giocatore.getPosizioneAttuale().getX()==12);
		Assert.assertTrue(giocatore.getPosizioneAttuale().getY()==6);
	}
	
	/**
	 * Test of the method spostamento of the class Gioco
	 */
	@Test
	public void TestSpostamentoGioco(){
		
		Gioco gioco = new Gioco();
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.spostamento(1,"M08");
		Assert.assertTrue("SetEffettoCasella isn't called or doesn't work",gioco.getPlayers().getGiocatore(1).getEffettoCasella().equals(Effetto.PESCA_SUONO));
		Assert.assertTrue("SetNumPassi doesn't work correctly or it isn't called",gioco.getPlayers().getGiocatore(1).getNumPassi()==1);
	}
			
}		


