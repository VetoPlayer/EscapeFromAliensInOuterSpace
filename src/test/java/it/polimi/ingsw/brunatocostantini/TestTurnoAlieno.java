package it.polimi.ingsw.brunatocostantini;

import giocatori.TurnoAlieno;

import org.junit.Assert;
import org.junit.Test;

import controller.StatiGioco;

public class TestTurnoAlieno {

	@Test
	public void TestnextPhase(){
		TurnoAlieno umanTurn= new TurnoAlieno();
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.STARTSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.ASKSTATE));
	}
	@Test
	public void TestnextPhase2(){
		TurnoAlieno umanTurn= new TurnoAlieno();
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.ASKSTATE, StatiGioco.ATTACKSTATE);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.ATTACKSTATE));
	}

	@Test
	public void TestnextPhase3(){
		TurnoAlieno umanTurn= new TurnoAlieno();
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.ASKSTATE, StatiGioco.DRAWSTATE);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.DRAWSTATE));
	}
	
	@Test
	public void TestnextPhase4(){
		TurnoAlieno umanTurn= new TurnoAlieno();
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.ATTACKSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.ENDSTATE));
	}
	@Test
	public void TestnextPhase5(){
		TurnoAlieno umanTurn= new TurnoAlieno();
		StatiGioco prossimoStato= umanTurn.nextPhase(StatiGioco.DRAWSTATE, StatiGioco.DONTKNOW);
		Assert.assertTrue(prossimoStato.equals(StatiGioco.ENDSTATE));
	}
	
	
}
