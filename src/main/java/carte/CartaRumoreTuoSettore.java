package carte;

import giocatori.Giocatore;

/**
 * @author Lollo
 *
 *This method extends CartaSuono and implements the effect of the sound card "noise in your sector" implementing the abstract method effettoCarta
 */
public class CartaRumoreTuoSettore extends CartaSuono {

	
	/**Constructor of this type of card. A card can be made with object card symbols on it (boolean = true) or without it.
	 * @param equipaggiamento  A card can be made with object card symbols on it (boolean = true) or without it.
	 */
	public CartaRumoreTuoSettore(boolean equipaggiamento){
		super(equipaggiamento);
		
	}

	@Override
	public void effettoCarta(Giocatore giocatore) {
		giocatore.setSuono(Suono.RUMORE);
	}

	
	
	
	
}
