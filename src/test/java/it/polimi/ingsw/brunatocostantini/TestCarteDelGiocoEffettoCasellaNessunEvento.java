package it.polimi.ingsw.brunatocostantini;

import giocatori.Giocatore;
import giocatori.Umano;

import org.junit.Assert;
import org.junit.Test;

import board.Casella;
import board.CasellaPericolosa;
import board.Effetto;
import carte.CarteDelGioco;
import carte.Suono;

/**
 * @author Lollo
 *Test the branch of the method effettoCasella in the "CarteDelGioco" class where the player doesn't pick up anything from the deck
 */
public class TestCarteDelGiocoEffettoCasellaNessunEvento {

	Casella casella = new CasellaPericolosa(3,3);
	Giocatore giocatore = new Umano(casella,1);
	CarteDelGioco decks = new CarteDelGioco();
	
	/**
	 * Test if the player doesn't pick an object card
	 */
	@Test
	public void TestSetSuonoNessunRumore(){
		
		Effetto effettoCasella = Effetto.NESSUN_EVENTO;
		giocatore.setEffettoCasella(effettoCasella);
		decks.effettoCasella(effettoCasella, giocatore);
		Assert.assertTrue("The player has picked up something",giocatore.getSuono().equals(Suono.NESSUNRUMORE));
	}
}
