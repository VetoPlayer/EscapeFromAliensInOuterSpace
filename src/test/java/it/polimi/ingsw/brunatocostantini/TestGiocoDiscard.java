package it.polimi.ingsw.brunatocostantini;

import org.junit.Assert;
import org.junit.Test;

import carte.CartaEquipaggiamento;
import carte.Oggetto;
import controller.Gioco;

/**This class contains the tests regarding the activation of the object card (the method discard and attivaEquipaggiamento in the class Gioco)
 * @author Lollo
 *
 */
public class TestGiocoDiscard {

	Gioco gioco= new Gioco();
		
	/**
	 * Test the activation of a generic object card
	 */
	@Test
	public void TestSetAttivata(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.TELETRASPORTO);
		gioco.getPlayers().getUmano(1).aggiungiEquipaggiamento(carta);
		CartaEquipaggiamento carta2 = new CartaEquipaggiamento(Oggetto.LUCE);
		gioco.getPlayers().getUmano(1).aggiungiEquipaggiamento(carta2);
		gioco.discard(1,"T");
		Assert.assertTrue("The card is not activated",carta.isAttivata());		
	}
	
	/**
	 * Test the activation of teletrasporto object card
	 */
	@Test
	public void TestAttivaTeletrasporto(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.TELETRASPORTO);
		gioco.getPlayers().getGiocatore(1).aggiungiEquipaggiamento(carta);
		gioco.discard(1,"T");
		Assert.assertTrue("The human doesn't land on the initial sector",gioco.getPlayers().getGiocatore(1).getPosizioneAttuale().equals(gioco.getMap().getSpawnumani()));
		
	}
	
	/**
	 * Test the activation of adrenalina object card
	 */
	@Test
	public void TestAttivaAdrenalina(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.ADRENALINA);
		gioco.getPlayers().getGiocatore(1).aggiungiEquipaggiamento(carta);
		gioco.discard(1,"R");
		Assert.assertTrue("The human doesn't start to move two steps a turn",gioco.getPlayers().getGiocatore(1).getNumPassi()==(CartaEquipaggiamento.PASSIADRENALINA));
		
	}
	
	/**
	 * Test the activation of attacco object card
	 */
	@Test
	public void TestAttivaAttacco(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.ATTACCO);
		gioco.getPlayers().getGiocatore(1).aggiungiEquipaggiamento(carta);
		gioco.discard(1,"A");
		Assert.assertTrue("The card is not activated",carta.isAttivata());
		
	}
	
	
	/**
	 * Test the activation of a sedativo object card controlling if the attribute sedativo of the class Giocatore is true
	 */
	@Test
	public void TestAttivaSedativo(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.SEDATIVO);
		gioco.getPlayers().getGiocatore(1).aggiungiEquipaggiamento(carta);
		gioco.discard(1,"S");
		Assert.assertTrue("The card is not activated",gioco.getPlayers().getGiocatore(1).hasSedativo());
		
	}
	
	/**
	 * Test the non-activation of the usaOggetto's branch related to the difesa object card
	 */
	@Test
	public void TestAttivaDifesa(){//probabilmente è inutile e non testabile
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.DIFESA);
		gioco.getPlayers().getGiocatore(1).aggiungiEquipaggiamento(carta);
		gioco.getPlayers().getUmano(1).usaOggetto("D",gioco.getMap(), gioco.getPlayers());
		Assert.assertFalse(gioco.getPlayers().getGiocatore(1).hasChanged());
		Assert.assertFalse("The card is activated, but it mustn't be activated",carta.isAttivata());
		
		
	}
	
	/**
	 * Test the discard of a "teletrasporto" card by an alien
	 */
	@Test
	public void TestScartaEquipaggiamentoTeletrasporto(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.TELETRASPORTO);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta);
		CartaEquipaggiamento carta2 = new CartaEquipaggiamento(Oggetto.LUCE);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta2);
		gioco.discard(2,"T");
		Assert.assertTrue("The card is not discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta));
		Assert.assertFalse("The card is discarded, but it mustn't be discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta2));
		
		
	}
	
	/**
	 * Test the discard of a "luce" object card by an alien
	 */
	@Test
	public void TestScartaEquipaggiamentoLuce(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.TELETRASPORTO);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta);
		CartaEquipaggiamento carta2 = new CartaEquipaggiamento(Oggetto.LUCE);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta2);
		CartaEquipaggiamento carta3 = new CartaEquipaggiamento(Oggetto.SEDATIVO);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta3);
		gioco.discard(2,"L");
		Assert.assertTrue("The card is not discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta2));
		Assert.assertFalse("The card is discarded, but it mustn't be discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta));
		
	}
	
	/**
	 * Test the discard of a "sedativo" object card by an alien
	 */
	@Test
	public void TestScartaEquipaggiamentoSedativo(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.SEDATIVO);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta);
		CartaEquipaggiamento carta2 = new CartaEquipaggiamento(Oggetto.ATTACCO);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta2);
		gioco.discard(2,"S");
		Assert.assertTrue("The card is not discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta));
		Assert.assertFalse("The card is discarded, but it mustn't be discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta2));
		
	}
	
	/**
	 * Test the discard of an "attacco" object card by an alien
	 */
	@Test
	public void TestScartaEquipaggiamentoAttacco(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.SEDATIVO);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta);
		CartaEquipaggiamento carta2 = new CartaEquipaggiamento(Oggetto.ATTACCO);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta2);
		gioco.discard(2,"A");
		Assert.assertTrue("The card is not discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta2));
		Assert.assertFalse("The card is discarded, but it mustn't be discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta));
		
	}
	
	/**
	 * Test the discard of an "adrenalina" object card by an alien
	 */
	@Test
	public void TestScartaEquipaggiamentoAdrenalina(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.ADRENALINA);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta);
		CartaEquipaggiamento carta2 = new CartaEquipaggiamento(Oggetto.DIFESA);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta2);
		gioco.discard(2,"R");
		Assert.assertTrue("The card is not discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta));
		Assert.assertFalse("The card is discarded, but it mustn't be discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta2));
		
	}
	
	/**
	 * Test the discard of an "adrenalina" object card by an alien
	 */
	@Test
	public void TestScartaEquipaggiamentoDifesa(){
		
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		CartaEquipaggiamento carta = new CartaEquipaggiamento(Oggetto.ADRENALINA);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta);
		CartaEquipaggiamento carta2 = new CartaEquipaggiamento(Oggetto.DIFESA);
		gioco.getPlayers().getAlieno(2).aggiungiEquipaggiamento(carta2);
		gioco.discard(2,"D");
		Assert.assertTrue("The card is not discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta2));
		Assert.assertFalse("The card is discarded, but it mustn't be discarded",gioco.getPlayers().getAlieno(2).getDiscardedCard().equals(carta));
		
	}
	
	
}
