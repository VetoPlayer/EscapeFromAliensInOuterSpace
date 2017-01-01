package board;


/**
 * @author Lollo
 * This class extends the abstract class Casella: it's the sector where every alien starts in the first turn
 *
 */
public class CasellaAlieni extends Casella {

	/**
	 * @param y:number that identifies the row
	 * @param x:number that identifies the column
	 * The constructor of CasellaAlieni overrides Casella putting the attribute "crossable" as false (aliens, and humans, can't walk there after the first turn
	 */
	public CasellaAlieni(int y, int x) {
		super(y, x, false);		
	}

	
	
	@Override
	public Effetto effettoCasella() {
		return Effetto.ERRORE;
	}
	
}
