package it.polimi.ingsw.brunatocostantini;

import giocatori.TurnoUmano;

import org.junit.Assert;
import org.junit.Test;

import controller.StatiGioco;

public class TestTurnoUmano {
	
	TurnoUmano umanTurn= new TurnoUmano();
	
	@Test
	public void TestnextPhase(){
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.STARTSTATE));
		
	}
	@Test
	public void TestnextPhase2(){
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.STARTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.STARTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.OBJECTSTATE));
	}
	@Test
	public void TestnextPhase3(){
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.STARTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.STARTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.OBJECTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.DRAWSTATE));
	}
	@Test
	public void TestnextPhase4(){
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.STARTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.STARTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.OBJECTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.DRAWSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.DRAWSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.OBJECTSTATE));
	}
	@Test
	public void TestnextPhase5(){
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.STARTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.STARTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.OBJECTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.DRAWSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.DRAWSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.OBJECTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.ENDSTATE));
	}
//TODO finire
	@Test
	public void TestnextPhase6(){
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.STARTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.STARTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.OBJECTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.DRAWSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.DRAWSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.OBJECTSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.ENDSTATE));
		prossimoStato= umanTurn.nextPhase(StatiGioco.OBJECTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.STARTSTATE));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
