package carte;

import giocatori.Giocatore;

/**
 * @author Lollo
 *One of the three subclasses of CartaSuono. It implements the sound card "noise in no sector"
 */
public class CartaSilenzio extends CartaSuono {
	/**
	 * Constructor of this type of card. It is the only type of sound card that couldn't have an object card symbol on it, so super(boolean) is always super(false)
	 */
	public CartaSilenzio(){
		super(false);
	}

	@Override
	public void effettoCarta(Giocatore giocatore) {
		giocatore.setSuono(Suono.SILENZIO);
		
	}



	
	
	
}