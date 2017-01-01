package board;

/**
 * @author Lollo
 * It extends Casella. It's a sector that can't be crossable by any player in every moment of the game
 *
 */
public class CasellaVuota extends Casella {

	/**
	 * @param y row
	 * @param x column
	 */
	public CasellaVuota(int y, int x) {
		super(y, x,false);
		}
	
	@Override	
	public Effetto effettoCasella(){
		return Effetto.ERRORE;
	}
	
}
