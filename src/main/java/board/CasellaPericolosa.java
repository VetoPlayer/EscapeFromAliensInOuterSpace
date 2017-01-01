package board;

/**
 * @author Lollo
 *It's a class which inherits from the abstract class casella: when a player walks into this type of sector, he has to pick a Sound Card
 */
public class CasellaPericolosa extends Casella {

	/**Constructor. See Javadoc of Casella. True refers to the attribute crossable of Casella
	 * @param y: row
	 * @param x: column
	 */
	public CasellaPericolosa(int y, int x) {
		super(y, x, true);
	}

	@Override
	public Effetto effettoCasella() {
		return Effetto.PESCA_SUONO;
	}

	
}
