package it.polimi.ingsw.brunatocostantini;

import giocatori.Giocatore;
import giocatori.Umano;

import org.junit.Assert;
import org.junit.Test;

import board.Casella;
import board.CasellaPericolosa;
import carte.CartaEquipaggiamento;
import carte.CarteDelGioco;
import carte.MazzoEquipaggiamento;
import carte.Oggetto;
import controller.Gioco;

/**Test of the method EffettoCasella in the class Gioco
 * @author Lollo
 *
 */
public class TestGiocoEffettoCasella {

	Casella casella = new CasellaPericolosa(3,3);
	Giocatore giocatore = new Umano(casella,3);
	CarteDelGioco decks = new CarteDelGioco();
	
	/**
	 * Test if the player picks up or not an object card after picking up a sound card "noise in another sector" with the object symbol on it
	 */
	@Test
	public void TestPescaEquipaggiamentoGetEquipaggiamento(){
		
		Oggetto oggetto = Oggetto.ADRENALINA;
		CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto);
		MazzoEquipaggiamento mazzoequip = decks.getMazzoEquipaggiamento();
		mazzoequip.getMazzo().push(equip);
		decks.pescaEquipaggiamento(giocatore);
		Assert.assertTrue("The player didn't picked up the object card",giocatore.getCartaEquipaggiamento(oggetto).equals(equip));
	}
	
	/**
	 * Test of the null return of the method GetEquipaggiamento in the class Giocatore, when there are already two object cards in the hand of the player
	 */
	@Test
	public void TestPescaEquipaggiamentoGetEquipaggiamentoNull(){
		
		Oggetto oggetto = Oggetto.DIFESA;
		Oggetto oggetto2 = Oggetto.TELETRASPORTO;
		Oggetto oggetto3 = Oggetto.ATTACCO;
		CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto3);
		giocatore.aggiungiEquipaggiamento(equip);
		CartaEquipaggiamento equip2 = new CartaEquipaggiamento(oggetto);
		MazzoEquipaggiamento mazzoequip = decks.getMazzoEquipaggiamento();
		mazzoequip.getMazzo().push(equip2);
		decks.pescaEquipaggiamento(giocatore);
		Assert.assertNull("The method getEquipaggiamento doesn't work",giocatore.getCartaEquipaggiamento(oggetto2));
	}
	
	/**
	 * Test the method pescaCarta when the deck is empty
	 */
	@Test
	public void TestPescaCartaMazzoEquipaggiamentoVuoto(){
		
		MazzoEquipaggiamento mazzoequip = decks.getMazzoEquipaggiamento();
		mazzoequip.getMazzo().clear();
		Assert.assertTrue("The deck is not empty", mazzoequip.getMazzo().isEmpty());
		decks.pescaEquipaggiamento(giocatore);
		Assert.assertFalse("The deck is empty", mazzoequip.getMazzo().isEmpty());
	}
	

	/**
	 * Test the activation of sedativo and the discard of the card after his effect has been successful
	 */
	@Test
	public void TestPescaEquipaggiamentoSedativoScartaOggetto(){
		
		Gioco gioco = new Gioco();
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		Oggetto oggetto = Oggetto.SEDATIVO;
		CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto);
		gioco.getPlayers().getUmano(1).aggiungiEquipaggiamento(equip);
		Assert.assertFalse("The object card sedativo is not in the hand of the player",gioco.getPlayers().getGiocatore(1).getEquipaggiamento().isEmpty());
		gioco.discard(1,"S");
		gioco.effettoCasella(gioco.getPlayers().getUmani().get(0).getIdGiocatore());
		Assert.assertTrue("There's an object card in the hand of the player",gioco.getPlayers().getGiocatore(1).getEquipaggiamento().isEmpty());
	}
	
	/**
	 * Test the method pescaEquipaggiamento using the getter of gioco GetDecks
	 */
	@Test
	public void TestGetDecksPescaEquipaggiamento(){
		
		Gioco gioco = new Gioco();
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		Oggetto oggetto = Oggetto.ADRENALINA;
		CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto);
		MazzoEquipaggiamento mazzoequip = gioco.getDecks().getMazzoEquipaggiamento();
		mazzoequip.getMazzo().push(equip);
		gioco.getDecks().pescaEquipaggiamento(gioco.getPlayers().getUmano(1));
		Assert.assertTrue("The player didn't picked up the object card",gioco.getPlayers().getUmano(1).getCartaEquipaggiamento(oggetto).equals(equip));
	}
	
}
