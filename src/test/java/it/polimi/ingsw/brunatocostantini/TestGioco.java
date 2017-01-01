package it.polimi.ingsw.brunatocostantini;

import giocatori.Giocatori;

import org.junit.Assert;
import org.junit.Test;

import controller.Gioco;


/**Test of the core methods of class Gioco
 * @author Lollo
 *
 */
public class TestGioco {
	
	
	/**
	 * Test if the players are correctly added to the game in the list of players in the class Gioco thanks to getNumeroG()
	 */
	@Test
	public void TestGetNumeroG(){
		Gioco gioco= new Gioco();
		int i=gioco.getNumeroG();
		Assert.assertTrue("Players are not correctly added to the game",i==1);
		int j= gioco.getNumeroG();
		Assert.assertTrue("Players are not correctly added to the game",j==2);
		
	}
	
	/**
	 * Test the method creaGiocatore and getPlayers in the class Gioco
	 */
	@Test
	public void TestGetPlayers(){
		
		Gioco gioco = new Gioco();
		gioco.getNumeroG();
		gioco.getNumeroG();
		gioco.creaGiocatori();
		Giocatori players= gioco.getPlayers();//ci sono due get players diversi e due creagiocatori diversi nelle classi gioco e giocatori
		Assert.assertTrue("There are not two players in this game",players.getPlayers().size()==2);
		
	}
	
	/**
	 * Test the method creaGiocatore and getUmani in the class Gioco
	 */
	@Test
	public void TestGetUmani(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<7;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		Giocatori players= gioco.getPlayers();//ci sono due get players diversi e due creagiocatori diversi nelle classi gioco e giocatori
		Assert.assertTrue("There are not three humans in a game with seven players",players.getUmani().size()==3);
		
	}
	
	/**
	 * Test the method creaGiocatore and getUmani in the class Gioco. This test should return null because there aren't players in the game
	 */
	@Test
	public void TestGetUmaniNull(){
		
		Gioco gioco = new Gioco();
		gioco.creaGiocatori();
		Giocatori players= gioco.getPlayers();
		Assert.assertTrue("There are humans in the game",players.getUmani().isEmpty());
		
	}
	
	/**
	 * Test the method creaGiocatore and getAlieni in the class Gioco
	 */
	@Test
	public void TestGetAlieni(){
		
		Gioco gioco = new Gioco();
		int i;
		for(i=0;i<7;i++)
			gioco.getNumeroG();
		gioco.creaGiocatori();
		Giocatori players= gioco.getPlayers();//ci sono due get players diversi e due creagiocatori diversi nelle classi gioco e giocatori
		Assert.assertTrue("There are not four aliens in a game with seven players",players.getAlieni().size()==4);
		
	}
	
	/**
	 * Test the method creaGiocatore and getUmano in the class Gioco. This test should return null because there aren't players in the game
	 */
	@Test
	public void TestGetUmanoNull(){
		
		Gioco gioco = new Gioco();
		gioco.creaGiocatori();
		Giocatori players= gioco.getPlayers();
		Assert.assertNull("There are humans in the game",players.getUmano(1));
		
	}
	
	/**
	 * Test the method creaGiocatore and getAlieno in the class Gioco. This test should return null because there aren't players in the game
	 */
	@Test
	public void TestGetAlienoNull(){
		
		Gioco gioco = new Gioco();
		gioco.creaGiocatori();
		Giocatori players= gioco.getPlayers();
		Assert.assertNull("There are aliens in the game",players.getAlieno(2));
		
	}
	
	/**
	 * Test the method creaGiocatore and getAlieno in the class Gioco. This test should return null because there aren't players in the game
	 */
	@Test
	public void TestGetGiocatoreNull(){
		
		Gioco gioco = new Gioco();
		gioco.creaGiocatori();
		Giocatori players= gioco.getPlayers();
		Assert.assertNull("There are aliens in the game",players.getGiocatore(1));
		
	}
	
	
}
