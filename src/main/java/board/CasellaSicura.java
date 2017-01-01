package board;

/**
 * @author Lollo
 * When a player walks into this sector, he doesn't pick any Sound Card (it returns Nessun_Evento)
 *
 */
public class CasellaSicura extends Casella {

	/**Constructor. True refers to the attribute crossable
	 * @param y row
	 * @param x column
	 */
	public CasellaSicura(int y, int x) {
		super(y, x, true);
	}

	@Override
	public Effetto effettoCasella() {
		// In una casella sicura non accade automaticamente alcun evento
		return Effetto.NESSUN_EVENTO;
	}
	
}
