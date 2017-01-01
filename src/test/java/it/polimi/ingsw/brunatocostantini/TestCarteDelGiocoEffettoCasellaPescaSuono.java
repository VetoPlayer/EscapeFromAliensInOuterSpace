package it.polimi.ingsw.brunatocostantini;

import giocatori.Giocatore;
import giocatori.Umano;

import org.junit.Assert;
import org.junit.Test;

import board.Casella;
import board.CasellaPericolosa;
import board.Effetto;
import carte.CartaRumoreAltroSettore;
import carte.CartaRumoreTuoSettore;
import carte.CartaSilenzio;
import carte.CartaSuono;
import carte.CarteDelGioco;
import carte.MazzoSuono;
import carte.Suono;

/**This class contains the tests regarding the act of picking up a sound card from a deck
 * @author Lollo
 *
 */
public class TestCarteDelGiocoEffettoCasellaPescaSuono {
	
	Casella casella = new CasellaPericolosa(3,3);
	Giocatore giocatore = new Umano(casella,1);
	CarteDelGioco decks = new CarteDelGioco();
	
	/**
	 * Test if the player picks up or not an object card after picking up a sound card "noise in another sector" without the object symbol on it
	 */
	@Test
	public void TestCartaRumoreAltroSettoreNoOggetto(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		CartaSuono cartasuono = new CartaRumoreAltroSettore(false);
		mazzosuono.getMazzo().push(cartasuono);
		boolean pescaEquip=decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertFalse("The player is going to pick an object card, while he mustn't do it",pescaEquip);
		
	}

	/**
	 * Test if the player picks up or not an object card after picking up a sound card "noise in another sector" with the object symbol on it
	 */
	@Test
	public void TestCartaRumoreAltroSettoreConOggetto(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		CartaSuono cartasuono = new CartaRumoreAltroSettore(true);
		mazzosuono.getMazzo().push(cartasuono);
		boolean pescaEquip=decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertTrue("The player is not going to pick an object card, while he must do it",pescaEquip);
	}
	
	/**
	 * Test if the player picks up or not an object card after picking up a sound card "noise in your sector" without the object symbol on it
	 */
	@Test
	public void TestCartaRumoreTuoSettoreNoOggetto(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		CartaSuono cartasuono = new CartaRumoreTuoSettore(false);
		mazzosuono.getMazzo().push(cartasuono);
		boolean pescaEquip=decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertFalse("The player is going to pick an object card, while he mustn't do it",pescaEquip);
	}
	
	/**
	 * Test if the player picks up or not an object card after picking up a sound card "noise in your sector" with the object symbol on it
	 */
	@Test
	public void TestCartaRumoreTuoSettoreConOggetto(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		CartaSuono cartasuono = new CartaRumoreTuoSettore(true);
		mazzosuono.getMazzo().push(cartasuono);
		boolean pescaEquip=decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertTrue("The player is not going to pick an object card, while he must do it",pescaEquip);
	}
	
	/**
	 * Test if the player doesn't pick an object card after picking up a sound card "no noise" without the object symbol on it
	 */
	@Test
	public void TestCartaSilenzio(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		CartaSuono cartasuono = new CartaSilenzio();
		mazzosuono.getMazzo().push(cartasuono);
		boolean pescaEquip=decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertFalse("The player is going to pick an object card, while he mustn't do it",pescaEquip);
	}
	
	/**
	 * Test the method pescaCarta when the deck is empty
	 */
	@Test
	public void TestPescaCartaMazzoSuonoVuoto(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		mazzosuono.getMazzo().clear();
		Assert.assertTrue("The deck is not empty", mazzosuono.getMazzo().isEmpty());
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertFalse("The deck is empty", mazzosuono.getMazzo().isEmpty());
	}
	
	/**
	 * Test if the player adds in his hand the sound card picked up
	 */
	@Test
	public void TestGiocatoreAddSuonoInMano(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		CartaSuono cartasuono = new CartaRumoreAltroSettore(false);
		mazzosuono.getMazzo().push(cartasuono);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertTrue("The player has not this sound card in his hand", giocatore.getSuonoInMano().equals(cartasuono));
	}
	
	/**
	 * Test if the player picks up a sound card "noise in another sector"
	 */
	@Test
	public void TestSetSuonoRumoreAltroSettore(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		CartaSuono cartasuono = new CartaRumoreAltroSettore(false);
		mazzosuono.getMazzo().push(cartasuono);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertTrue("The player has not picked up a -noise in another sector- card",giocatore.getSuono().equals(Suono.RUMOREALTROSETTORE));
	}

	/**
	 * Test if the player picks up a sound card "noise in your sector"
	 */
	@Test
	public void TestSetSuonoRumoreTuoSettore(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		CartaSuono cartasuono = new CartaRumoreTuoSettore(false);
		mazzosuono.getMazzo().push(cartasuono);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertTrue("The player has not picked up a -noise in your sector- card",giocatore.getSuono().equals(Suono.RUMORE));
	}
	
	/**
	 * Test if the player doesn't pick an object card after picking up a sound card "no noise"
	 */
	@Test
	public void TestSetSuonoSilenzio(){
		
		Effetto effettoCasella = Effetto.PESCA_SUONO;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoSuono mazzosuono = decks.getMazzoSuono();
		CartaSuono cartasuono = new CartaSilenzio();
		mazzosuono.getMazzo().push(cartasuono);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertTrue("The player has not picked up a -no noise- card",giocatore.getSuono().equals(Suono.SILENZIO));
	}
}
