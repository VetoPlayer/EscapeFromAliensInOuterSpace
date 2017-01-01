package carte;

/**This class contains the methods regarding the escape cards and their effect to the players who pick up these cards from the deck
 * @author Lollo
 * 
 */
public class CartaScialuppa {
	private boolean funzionante;

	/**Constructor
	 * @param funzionante :if it's true, the escape card created will permit the escape to the player, otherwise no.
	 */
	public CartaScialuppa(boolean funzionante) {
		super();
		this.funzionante = funzionante;
	}

	/**Getter
	 * @return a true boolean if the card allows to escape successfully from the board, otherwise returns false
	 */
	public boolean isFunzionante() {
		return funzionante;
	}

	/**Setter
	 * @param funzionante a boolean which set the Carta Scialuppa's attribute "funzionante" to true or false
	 */
	public void setFunzionante(boolean funzionante) {
		this.funzionante = funzionante;
	}
	
}
