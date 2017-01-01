package it.polimi.ingsw.brunatocostantini;

import giocatori.Giocatore;
import giocatori.StatoGiocatore;
import giocatori.Umano;

import org.junit.Assert;
import org.junit.Test;

import board.Casella;
import board.CasellaPericolosa;
import board.Effetto;
import carte.CartaScialuppa;
import carte.CarteDelGioco;
import carte.MazzoScialuppa;

/**Test regarding the act of picking up an escape card from the escape cards deck
 * @author Lollo
 *
 */
public class TestCarteDelGiocoEffettoCasellaPescaScialuppa {
	
	Casella casella = new CasellaPericolosa(3,3);
	Giocatore giocatore = new Umano(casella,1);
	CarteDelGioco decks = new CarteDelGioco();

	/**
	 * Test if the player adds in his hand the escape card picked up
	 */
	@Test
	public void TestGiocatoreAddScialuppaInMano(){
		
		Effetto effettoCasella = Effetto.PESCA_SCIALUPPA;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoScialuppa mazzoscialuppa = decks.getMazzoScialuppa();
		CartaScialuppa cartascialuppa = new CartaScialuppa(false);
		mazzoscialuppa.getMazzo().push(cartascialuppa);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertTrue("The player has not this sound card in his hand", giocatore.getScialuppaInMano().equals(cartascialuppa));
	}
	
	/**
	 * Test if the player adds in his hand the escape card picked up and if the game recognizes the type of the escape card picked up ("not working" escape card type)
	 */
	@Test
	public void TestGiocatoreAddScialuppaInManoNonFunzionante(){
		
		Effetto effettoCasella = Effetto.PESCA_SCIALUPPA;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoScialuppa mazzoscialuppa = decks.getMazzoScialuppa();
		CartaScialuppa cartascialuppa = new CartaScialuppa(false);
		mazzoscialuppa.getMazzo().push(cartascialuppa);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertFalse("The escape card works, but it mustn't work", giocatore.getScialuppaInMano().isFunzionante());
	}
	
	/**
	 * Test if the player adds in his hand the escape card picked up and if the game recognizes that the player has won the game
	 */
	@Test
	public void TestGiocatoreAddScialuppaInManoFunzionanteSetVincitore(){
		
		Effetto effettoCasella = Effetto.PESCA_SCIALUPPA;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoScialuppa mazzoscialuppa = decks.getMazzoScialuppa();
		CartaScialuppa cartascialuppa = new CartaScialuppa(true);
		mazzoscialuppa.getMazzo().push(cartascialuppa);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertTrue("The escape card doesn't work", giocatore.getStato().equals(StatoGiocatore.VINCITORE));
	}
	
	/**
	 * Test if the player adds in his hand the escape card picked up and if the game recognizes that the player has won and now is not anymore on the board
	 */
	@Test
	public void TestGiocatoreAddScialuppaInManoFunzionanteSetPosizioneAttualeNull(){
		
		Effetto effettoCasella = Effetto.PESCA_SCIALUPPA;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoScialuppa mazzoscialuppa = decks.getMazzoScialuppa();
		CartaScialuppa cartascialuppa = new CartaScialuppa(true);
		mazzoscialuppa.getMazzo().push(cartascialuppa);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertNull("The escape card doesn't work", giocatore.getPosizioneAttuale());
	}
	
//	/**
//	 * Test if the player adds in his hand the escape card picked up and if the game recognizes that the player has won and now he hasn't got cards in his hand anymore
//	 */
/*	@Test
	public void TestGiocatoreAddScialuppaInManoFunzionanteScartaCarte(){
		
		Effetto effettoCasella = Effetto.PESCA_SCIALUPPA;
		giocatore.setEffettoCasella(effettoCasella);
		MazzoScialuppa mazzoscialuppa = decks.getMazzoScialuppa();
		CartaScialuppa cartascialuppa = new CartaScialuppa(false);
		mazzoscialuppa.getMazzo().push(cartascialuppa);
		Oggetto oggetto = Oggetto.ADRENALINA;
		CartaEquipaggiamento equip = new CartaEquipaggiamento(oggetto);
		giocatore.aggiungiEquipaggiamento(equip);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertNull("The escape card doesn't work", giocatore.getEquipaggiamento());
	}*/
}
