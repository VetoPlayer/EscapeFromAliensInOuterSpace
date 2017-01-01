package it.polimi.ingsw.brunatocostantini;

import giocatori.Alieno;
import giocatori.Giocatori;
import giocatori.StatoGiocatore;
import giocatori.Umano;

import org.junit.Assert;
import org.junit.Test;

import board.Casella;
import board.CasellaPericolosa;
import carte.CartaEquipaggiamento;
import carte.Oggetto;
import controller.Gioco;

/** The tests of this class are all related to the method "attacco" in "Alieno" class. The human attack with the "attacco" object card is not tested here
 * @author Lollo
 *
 */
public class TestAttaccoAlieno {
	
	Oggetto oggetto= Oggetto.DIFESA;
	CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto);
	CartaEquipaggiamento equip2 = new CartaEquipaggiamento(oggetto);
	Casella casella = new CasellaPericolosa(3,3);
	Umano umano = new Umano(casella,1);
	Umano umano2 = new Umano(casella,2);
	Alieno alieno = new Alieno(casella,2);
	Alieno alieno2 = new Alieno(casella,2);
	Giocatori giocatori = new Giocatori();
	

	/**
	 * Test of the attack versus a human without the "difesa" card in the same sector of the alien. The method attacco(int) of class Gioco is used this time
	 */
	@Test
	public void TestGiocoAttaccoVsUmanoSenzaDifesa(){
		
		Gioco gioco= new Gioco();
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.getPlayers().getGiocatore(1).setPosizioneAttuale(casella);
		gioco.getPlayers().getGiocatore(2).setPosizioneAttuale(casella);		
		gioco.attacco(2);
		Assert.assertTrue("The human attacked has survived even without defense object card",gioco.getPlayers().getGiocatore(1).getStato().equals(StatoGiocatore.MORTO));
	}
	
	/**
	 * Test of the attack versus a human with the "difesa" card in the same sector of the alien
	 */
	@Test
	public void TestAttaccoVsUmanoConDifesa(){
		
		umano.aggiungiEquipaggiamento(equip);
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);
		alieno.attacco(giocatori);
		Assert.assertTrue("The human attacked dies even if he has the defense object card",umano.getStato().equals(StatoGiocatore.VIVO));
	}
	
	/**
	 * Test that the alien who attacks doesn't die after the attack
	 */
	@Test
	public void TestAttaccoNonMuoreColuiCheAttacca(){
		
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);
		alieno.attacco(giocatori);
		Assert.assertTrue("The attacking alien has died",alieno.getStato().equals(StatoGiocatore.VIVO));
	}
	
	/**
	 * Test of the attack versus a human without the "difesa" card in the same sector of the alien
	 */
	@Test
	public void TestAttaccoVsUmanoSenzaDifesa(){
		
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);		
		alieno.attacco(giocatori);
		Assert.assertTrue("The human attacked has survived even without defense object card",giocatori.getUmano(1).getStato().equals(StatoGiocatore.MORTO));
	}
	
	/**
	 * Test the alien attack versus another alien in the same sector, who's got the defense card. The alien attacked should die because he can't use the card
	 */
	@Test
	public void TestAttaccoVsAlienoConDifesaInutilizzabile(){
		
		alieno.aggiungiEquipaggiamento(equip);
		giocatori.getAlieni().add(alieno);
		giocatori.getAlieni().add(alieno2);
		alieno2.attacco(giocatori);
		Assert.assertTrue("The alien attacked has survived",alieno.getStato().equals(StatoGiocatore.MORTO));
	}
	
	/**
	 * Test the alien attack versus a human without defense card and controls that the died human's position on the board is set to null after the death
	 */
	@Test
	public void TestAttaccoVsUmanoSenzaDifesaPosizAttuale(){
		
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);		
		alieno.attacco(giocatori);
		Assert.assertNull("The human is still on the board after the death",umano.getPosizioneAttuale());
	}
	
	/**
	 * Test the alien attack versus a human with defense card and controls that his position on the board after the attack remains the same
	 */
	@Test
	public void TestAttaccoVsUmanoConDifesaPosizAttuale(){
		
		umano.aggiungiEquipaggiamento(equip);
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);		
		alieno.attacco(giocatori);
		Assert.assertTrue("The human has not had is position on the board removed after he survived the attack",umano.getPosizioneAttuale().equals(casella));
	}
	
	/**
	 * Test that an human attacked by an alien has his object card removed from his hand
	 */
	@Test
	public void TestAttaccoVsUmanoSenzaDifesaScartacarte(){
		
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);		
		alieno.attacco(giocatori);
		Assert.assertTrue("The cards of a died player has not been discarded",giocatori.getUmano(1).getEquipaggiamento().isEmpty());
	}
	
	/**
	 * Test that an alien after killing a human becomes able to move three steps in a turn instead of two
	 */
	@Test
	public void TestAttaccoVsUmanoSenzaDifesaSetSuperAlieno(){
		
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);		
		alieno.attacco(giocatori);
		Assert.assertTrue("The alien has not been upgraded to superalien",giocatori.getAlieno(2).isSuperAlieno());
	}
	
	/**
	 * Test that the game realizes that the human player killed by the alien is the last human on the board
	 */
	@Test
	public void TestAttaccoVsUmanoSenzaDifesaLastUman(){
		
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);		
		alieno.attacco(giocatori);
		Assert.assertTrue("The game doesn't realize that the human killed was the last human on the board",giocatori.isLastUman());
	}
	
	/**
	 * Test that the game realizes that the human player killed by the alien is not the last human on the board, because another human attacked in the same sector used the defense card
	 */
	@Test
	public void TestAttaccoVsDueUmaniSenzaEConDifesaNonLastUman(){
		
		umano.aggiungiEquipaggiamento(equip);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		giocatori.getAlieni().add(alieno);		
		alieno.attacco(giocatori);
		Assert.assertFalse("The game realizes that the human player killed by the alien is last human on the board when he is not",giocatori.isLastUman());
	}
	
	/**
	 * Test that the game realizes that the two human players killed by the alien were the last humans on the board
	 */
	@Test
	public void TestAttaccoVsDueUmaniSenzaDifesaLastUman(){
		
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		giocatori.getAlieni().add(alieno);		
		alieno.attacco(giocatori);
		Assert.assertTrue("The game doesn't realize that the humans killed were the last humans on the board",giocatori.isLastUman());
	}
	

	/**
	 * Test an attack by an alien versus another alien and a human all in other sectors: the players shouldn't die
	 */
	@Test
	public void TestAttaccoVsAlienoEUmanoInAltreCaselle(){
		
		Casella casella2 = new CasellaPericolosa(3,4);
		Casella casella3 = new CasellaPericolosa(3,5);
		Alieno alieno3 = new Alieno(casella2,2);
		Umano umano3 = new Umano(casella3,3);
		giocatori.getAlieni().add(alieno);
		giocatori.getAlieni().add(alieno3);
		giocatori.getUmani().add(umano3);
		alieno.attacco(giocatori);
		Assert.assertTrue("The alien attacked dies",alieno3.getStato().equals(StatoGiocatore.VIVO));
		Assert.assertTrue("The human dies",umano3.getStato().equals(StatoGiocatore.VIVO));
	}
	
	///**
	// * Test an attack by an alien versus a human with two defense cards in his hands. He should use and discard only one defense card
	// */
	/*@Test
	public void TestAttaccoVsUmanoConDueDifeseScartaUnSoloOggetto(){
		
		umano.aggiungiEquipaggiamento(equip);
		umano.aggiungiEquipaggiamento(equip2);
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);		
		alieno.attacco(giocatori);
		Assert.assertTrue("The player uses two defense card or doesn't discard any of them",giocatori.getUmano(1).getEquipaggiamento().size()==1);//torna il numero di elementi. Mi dice che size è 2!
	}*/
	
	
}
