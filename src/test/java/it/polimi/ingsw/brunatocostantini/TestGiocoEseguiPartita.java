package it.polimi.ingsw.brunatocostantini;


import giocatori.StatoGiocatore;

import org.junit.Assert;
import org.junit.Test;

import controller.Gioco;
import controller.StatiGioco;

/**Test of all the branches of the method eseguiPartita() of the class Gioco
 * @author Lollo
 *
 */
public class TestGiocoEseguiPartita {

	/**
	 * Test that the humans start their turn in the Object State
	 */
	@Test
	public void TestObjectStatePrimoUmano(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<8;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.eseguiPartita();
		Assert.assertTrue("Humans don't start their turn in Object State",gioco.getPlayers().getGiocatore(1).getStatoAttuale().equals(StatiGioco.OBJECTSTATE));
		
	}
	
	/**
	 * Test that the aliens start their turn in the Start State
	 */
	@Test
	public void TestStartStatePrimoAlieno(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<2;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.getPlayers().getGiocatore(1).setStato(StatoGiocatore.MORTO);
		gioco.eseguiPartita();
		Assert.assertTrue("Aliens don't start their turn in Start State",gioco.getPlayers().getGiocatore(2).getStatoAttuale().equals(StatiGioco.STARTSTATE));
		
	}
	
	/**
	 * Test the correct increment of player turn number
	 */
	@Test
	public void TestPlayerTurnNumber(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<2;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.getPlayers().getGiocatore(1).setStato(StatoGiocatore.MORTO);//il primo umano muore, il primo alieno (id=2) gioca e alla fine di esegui partita il valore corretto di playerturnnumber è 3, pronto per gestire il terzo player
		gioco.eseguiPartita();
		Assert.assertTrue("The method doesn't increment correctly the id of the player who is playing the turn at the moment",gioco.getPlayerTurnNumber()==3);
		
	}
	
	/**
	 * Test what happen after the maximum number of turns have passed; it verifies if the humans left on the board after the last turn are turned to dead in "Morto" state 
	 */
	@Test
	public void TestTurnoScadutoSconfittaUmaniRimasti(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<4;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.setNumturni(Gioco.NUMEROTURNI+1);
		gioco.eseguiPartita();
		Assert.assertTrue("GetNumTurni doesn't work",gioco.getNumturni()==Gioco.NUMEROTURNI+1);
		Assert.assertTrue("Humans left on the board after 39 turns are winners",gioco.getPlayers().getUmani().get(0).getStato().equals(StatoGiocatore.MORTO));
		Assert.assertTrue("Humans left on the board after 39 turns are winners",gioco.getPlayers().getUmani().get(1).getStato().equals(StatoGiocatore.MORTO));
	}
	
	/**
	 * Test what happen after the maximum number of turns have passed; it verifies that only the humans left on the board after the last turn are turned to dead in "Morto" state 
	 */
	@Test
	public void TestTurnoScadutoUmaniGiàVincitoriOMorti(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<4;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.setNumturni(Gioco.NUMEROTURNI+1);
		gioco.getPlayers().getGiocatore(1).setStato(StatoGiocatore.VINCITORE);
		gioco.eseguiPartita();
		Assert.assertTrue("This player is not anymore a winner",gioco.getPlayers().getUmani().get(0).getStato().equals(StatoGiocatore.VINCITORE));
		Assert.assertTrue("Humans left on the board after 39 turns are winners",gioco.getPlayers().getUmani().get(1).getStato().equals(StatoGiocatore.MORTO));
	}
	
	/**
	 * Test what happen after the maximum number of turns have passed; it verifies that the aliens after the last turn become winners 
	 */
	@Test
	public void TestTurnoScadutoAlieniVincitori(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<4;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.setNumturni(Gioco.NUMEROTURNI+1);
		gioco.eseguiPartita();
		Assert.assertTrue("Aliens aren't winners",gioco.getPlayers().getAlieni().get(0).getStato().equals(StatoGiocatore.VINCITORE));
		Assert.assertTrue("Aliens aren't winners",gioco.getPlayers().getAlieni().get(1).getStato().equals(StatoGiocatore.VINCITORE));
	}
	
	/**
	 * Test what happen after the maximum number of turns have passed with also the parameter isLastUman of the class Giocatori set to true (vittoriaAliena becomes true)
	 */
	@Test
	public void TestVittoriaAlienaETurnoScaduto(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<4;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.setNumturni(Gioco.NUMEROTURNI+1);
		gioco.getPlayers().setLastUman(true);
		gioco.eseguiPartita();
		Assert.assertTrue("Aliens aren't winners",gioco.getPlayers().getAlieni().get(0).getStato().equals(StatoGiocatore.VINCITORE));
		Assert.assertTrue("Humans left on the board after 39 turns are winners",gioco.getPlayers().getUmani().get(0).getStato().equals(StatoGiocatore.MORTO));
		Assert.assertTrue("LastUman wasn't set to true",gioco.getPlayers().isLastUman());
	}
	
	/**
	 * Test what happen when the parameter isLastUman of the class Giocatori set to true (vittoriaAliena becomes true)
	 */
	@Test
	public void TestVittoriaAliena(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<4;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		gioco.getPlayers().setLastUman(true);
		gioco.eseguiPartita();
		Assert.assertTrue("Aliens aren't winners",gioco.getPlayers().getAlieni().get(0).getStato().equals(StatoGiocatore.VINCITORE));
		Assert.assertTrue("Humans left on the board after 39 turns are winners",gioco.getPlayers().getUmani().get(0).getStato().equals(StatoGiocatore.MORTO));
		
	}
}
