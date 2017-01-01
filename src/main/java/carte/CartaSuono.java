package carte;

import giocatori.Giocatore;

/**
 * @author Lollo
 *Abstract class for the sound card. It implements the Observer interface and has an abstract method effettoCarta who is implemented in every subclass (CartaRumoreTuoSettore, CartaSilenzio, CartaRumoreAltroSettore)
 */
public abstract class CartaSuono {
	private boolean equipaggiamento;
		
	
	/**Constructor of sound cards
	 * @param equipaggiamento a boolean indicating if the sound card created has the object symbol on it
	 */
	public CartaSuono(boolean equipaggiamento){
		this.equipaggiamento=equipaggiamento; 
		
	}
	/**
	 * @return a boolean indicating if the sound card has the object card symbol on it
	 */
	public boolean hasEquipaggiamento() {
		return equipaggiamento;
	}
	/**Abstract method where his implementation in the sub-classes always calls the observers saying which type of sound card has been picked up in order to resolve his effect later
	 * @param giocatore :player who has just made a movement on the board
	 */
	public abstract void effettoCarta(Giocatore giocatore);
	
	
}