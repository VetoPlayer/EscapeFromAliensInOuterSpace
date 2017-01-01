package board;

/**
 * @author Lollo
 * It's the sector where every human is placed at the start of the game: after that turn, it won't be crossable, so EffettoCasella returns an error
 *
 */
public class CasellaUmani extends Casella {

	/**Constructor. False refers to the attribute crossable
	 * @param y row
	 * @param x column
	 */
	public CasellaUmani(int y, int x) {
		super(y, x, false);
	}

	@Override
	public Effetto effettoCasella() {
		return Effetto.ERRORE;
	}
	
}
