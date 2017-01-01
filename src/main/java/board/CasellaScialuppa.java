package board;

/**
 * @author Lollo
 * It's the class which represents the sector where humans have to go in order to win. It extends Casella.
 * The boolean "utilizzata" becomes true after a human walks into the sector for the first time and from this moment the sector won't be crossable
 * Aliens cannot walk in this sector, and this is controlled by a method of class Mappa called "alienoscialuppa"
 */
public class CasellaScialuppa extends Casella {
	private boolean utilizzata;
	/**Constructor. True refers to the attribute crossable
	 * @param y row
	 * @param x column
	 */
	public CasellaScialuppa(int y, int x) {
		super(y, x, true);
		this.utilizzata=false;
	}
	
	@Override
	public Effetto effettoCasella() {
		if(!this.isUtilizzata()){
			this.setUtilizzata(true);
			this.setCrossable(false);
			return Effetto.PESCA_SCIALUPPA;
		}
		else
			return Effetto.ERRORE;
	}

	public boolean isUtilizzata() {
		return utilizzata;
	}

	public void setUtilizzata(boolean utilizzata) {
		this.utilizzata = utilizzata;
	}

}
