package it.polimi.ingsw.brunatocostantini;

import org.junit.Assert;
import org.junit.Test;

import board.Casella;
import board.CasellaAlieni;
import board.CasellaPericolosa;
import board.CasellaScialuppa;
import board.CasellaSicura;
import board.CasellaUmani;
import board.CasellaVuota;
import board.Mappa;

public class TestMappa {
	
	/**
	 * Test of the creation of a new non crossable sector
	 */
	@Test
	public void TestNuovaCasellaVuota(){
		
		Mappa mappa = Mappa.getMappa();
		Assert.assertTrue("This sector is not a void sector",mappa.getCasella(0,0) instanceof CasellaVuota);
		
	}

	/**
	 * Test of the creation of a new dangerous sector
	 */
	@Test
	public void TestNuovaCasellaPericolosa(){//RICORDA!!! Il primo numero è la riga, il secondo la colonna. La tabella parte da 0 sia per righe che per colonne!!
		
		Mappa mappa = Mappa.getMappa();
		Assert.assertTrue("This sector is not a dangerous one",mappa.getCasella(3,1) instanceof CasellaPericolosa);
		
	}
	
	/**
	 * Test of the creation of a new non dangerous sector
	 */
	@Test
	public void TestNuovaCasellaSicura(){
		
		Mappa mappa = Mappa.getMappa();
		Assert.assertTrue("This sector is not a non dangerous sector",mappa.getCasella(4,1) instanceof CasellaSicura);
		
	}
	
	/**
	 * Test of the creation of the initial sector of aliens
	 */
	@Test
	public void TestNuovaCasellaAlieni(){
		
		Mappa mappa = Mappa.getMappa();
		Assert.assertTrue("This sector is not the initial sector for aliens",mappa.getCasella(6,12) instanceof CasellaAlieni);//L06 su mappa esagonale diventa (6,12), non si aggiunge +1 perchè la matrice conta da 0!!
		
	}
	
	/**
	 * Test of the creation of the initial sector of humans
	 */
	@Test
	public void TestNuovaCasellaUmani(){
		
		Mappa mappa = Mappa.getMappa();
		Assert.assertTrue("This sector is not the initial sector for humans",mappa.getCasella(8,12) instanceof CasellaUmani);
	}	
		
	/**
	 * Test of the creation of a new escape sector
	 */
	@Test
	public void TestNuovaCasellaScialuppa(){
		
		Mappa mappa = Mappa.getMappa();
		Assert.assertTrue("This sector is not an escape sector",mappa.getCasella(2,2) instanceof CasellaScialuppa);
	}
	
	/**
	 * Test of the method "convertitore" and of the private methods it calls
	 */
	@Test
	public void TestConvertitore(){//ricordarsi che la rappresentazione in matrice è (riga,colonna), cioè(lettera,numero), così come sono senza fare +1 o -1 alla codifica
		
		String coordinate = "I07";
		Mappa mappa = Mappa.getMappa();
		Casella casella = mappa.convertitore(coordinate);
		Assert.assertTrue(casella.getX()==9);
		Assert.assertTrue(casella.getY()==7);
		Assert.assertTrue(casella.isCrossable());
	}	
	
	/**
	 * Test of the method getSpawnUmani, a getter for the initial sector for humans
	 */
	@Test
	public void TestGetSpawnUmani(){
		
		Mappa mappa = Mappa.getMappa();
		Casella casella =mappa.getSpawnumani();
		Assert.assertTrue("This sector is not the initial sector for humans",casella instanceof CasellaUmani);
	}	
	
	/**
	 * Test of the method getSpawnAlieni, a getter for the initial sector for aliens
	 */
	@Test
	public void TestGetSpawnAlieni(){
		
		Mappa mappa = Mappa.getMappa();
		Casella casella =mappa.getSpawnalieni();
		Assert.assertTrue("This sector is not the initial sector for aliens",casella instanceof CasellaAlieni);//L06 su mappa esagonale diventa (6,12), non si aggiunge +1 perchè la matrice conta da 0!!
		
	}
	
	
}
