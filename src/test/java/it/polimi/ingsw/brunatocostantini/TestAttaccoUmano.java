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

/** The tests of this class are all related to the method "attacco" in "CartaEquipaggiamento" class. The alien attack is not tested here
 * @author Lollo
 *
 */
public class TestAttaccoUmano {
	
	Oggetto dif= Oggetto.DIFESA;
	CartaEquipaggiamento cartadifesa = new CartaEquipaggiamento(dif);
	Oggetto att= Oggetto.ATTACCO;
	CartaEquipaggiamento cartaattacco = new CartaEquipaggiamento(att);
	CasellaPericolosa casella = new CasellaPericolosa(3,3);
	Umano umano = new Umano(casella,1);
	Umano umano2 = new Umano(casella,2);
	Umano umano3 = new Umano(casella,3);
	Alieno alieno = new Alieno(casella,2);
	Giocatori giocatori = new Giocatori();
	
	/**
	 * Test of the attack versus a human with the "difesa" card in the same sector of the attacking human
	 */
	@Test
	public void TestAttaccoVsUmanoConDifesa(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		umano2.aggiungiEquipaggiamento(cartadifesa);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertTrue("The attacked human with defense card has died",umano2.getStato().equals(StatoGiocatore.VIVO));
	}
	
	/**
	 * Test that the attacking human doesn't die after the attack
	 */
	@Test
	public void TestAttaccoNonMuoreColuiCheAttacca(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertTrue("The attacking human has died",umano.getStato().equals(StatoGiocatore.VIVO));
		
	}
	
	/**
	 * Test of the attack versus a human without the defense object card in the same sector of the attacking human
	 */
	@Test
	public void TestAttaccoVsUmanoSenzaDifesa(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertTrue("The human has survived the attack, but he should have died",umano2.getStato().equals(StatoGiocatore.MORTO));
	}
	
	/**
	 * Test the human attack versus an alien in the same sector, who's got the defense card. The alien attacked should die because he can't use the card
	 */
	@Test
	public void TestAttaccoVsAlienoConDifesaInutilizzabile(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		alieno.aggiungiEquipaggiamento(cartadifesa);
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertTrue("The alien attack has survived",alieno.getStato().equals(StatoGiocatore.MORTO));
	}
	
	/**
	 * Test the human attack versus an human without defense object card. His position in the board should be set to null at the end of the attack
	 */
	@Test
	public void TestAttaccoVsUmanoSenzaDifesaPosizAttuale(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertNull("The dead human is still on the board",umano2.getPosizioneAttuale());
	}
	
	/**
	 * Test the attack of a human versus a human with defense card and controls that his position on the board after the attack remains the same
	 */
	@Test
	public void TestAttaccoVsUmanoConDifesaPosizAttuale(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		umano2.aggiungiEquipaggiamento(cartadifesa);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertTrue("The human attacked, who has the defense object card, is not anymore on the sector where he was before the attack",umano2.getPosizioneAttuale().equals(casella));
		
	}
	
	/**
	 * Test that an human attacked by a human has his object card removed from his hand
	 */
	@Test
	public void TestAttaccoVsUmanoSenzaDifesaScartacarte(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertTrue("The cards of a died player has not been discarded",umano2.getEquipaggiamento().isEmpty());
	}
	
	/**
	 * Test that the game realizes that the human player killed by the attacking human is the last human on the board
	 */
	@Test
	public void TestAttaccoVsUmanoSenzaDifesaNonLastUman(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertFalse("The game doesn't realize that the human killed was the last human on the board",giocatori.isLastUman());
	}
	
	/**
	 * Test that the game realizes that the human player killed by the attacking human is not the last human on the board, because another human attacked in the same sector used the defense card
	 */
	@Test
	public void TestAttaccoVsDueUmaniSenzaEConDifesaNonLastUman(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		umano2.aggiungiEquipaggiamento(cartadifesa);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		giocatori.getUmani().add(umano3);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertFalse("The game realizes that the human player killed by the alien is last human on the board when he is not",giocatori.isLastUman());
	}
	
	/**
	 * Test that the game realizes that the two human players killed by the attacking human were the last humans on the board
	 */
	@Test
	public void TestAttaccoVsDueUmaniSenzaDifesaLastUman(){
		
		umano.aggiungiEquipaggiamento(cartaattacco);
		giocatori.getUmani().add(umano);
		giocatori.getUmani().add(umano2);
		giocatori.getUmani().add(umano3);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertFalse("The game doesn't realize that the humans killed were the last humans on the board",giocatori.isLastUman());
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
		umano.aggiungiEquipaggiamento(cartaattacco);
		giocatori.getUmani().add(umano);
		giocatori.getAlieni().add(alieno3);
		giocatori.getUmani().add(umano3);
		cartaattacco.attacco(umano, giocatori);
		Assert.assertTrue("The alien dies",alieno3.getStato().equals(StatoGiocatore.VIVO));
		Assert.assertTrue("The human not attacked dies",umano3.getStato().equals(StatoGiocatore.VIVO));
	}
	
	
}
