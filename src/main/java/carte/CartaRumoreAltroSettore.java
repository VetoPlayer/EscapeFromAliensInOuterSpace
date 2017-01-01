package carte;

import giocatori.Giocatore;

/**
 * @author Lollo
 *This method extends CartaSuono and implements the effect of the sound card "noise in another
 *sector" implementing the abstract method effettoCarta
 */
public class CartaRumoreAltroSettore extends CartaSuono {
	
	/**Constructor
	 * @param equipaggiamento boolean which indicates if the sound card contains or not the symbol of object card
	 */
	public CartaRumoreAltroSettore(boolean equipaggiamento){
		super(equipaggiamento);
		
	}

	@Override
	public void effettoCarta(Giocatore giocatore) {
		giocatore.setSuono(Suono.RUMOREALTROSETTORE);
		
	}





	
	
	
	
	
	

}
