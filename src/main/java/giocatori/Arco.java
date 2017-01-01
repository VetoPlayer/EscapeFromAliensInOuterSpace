package giocatori;

import controller.StatiGioco;

/**
 * @author Andrea
 * The methods of this class are called by the classes TurnoUmano and TurnoAlieno, in order to know which is the current and the next phase of the game in a particular moment of an alien's or human's turn.
 * The name of the class symbolizes the arrow in a finite state machine, where the nodes are the phases of a turn.
 */
/**
 * @author Lollo
 *
 */
public class Arco {
	private StatiGioco faseCorrente;
	private StatiGioco prossimaFase;
	
	/**Constructor of the arrow between two states of a turn. This class is called by TurnoUmano to create the finite state machine of the human's turn and by TurnoAlieno to create the same for the alien's turn
	 * @param faseCorrente the current phase of the turn
	 * @param prossimaFase the next phase of the turn
	 */
	public Arco(StatiGioco faseCorrente,StatiGioco prossimaFase) {
		this.setFaseCorrente(faseCorrente);
		this.setProssimaFase(prossimaFase);
	}
	/**Getter for the current phase of a player's turn. It is used both for aliens and humans, but the returned value could be different for the two types of players (for example OBJECTSTATE is never present in alien's arrows (instances of Arco))
	 * @return the current phase (an instance of the enum StatiGioco)
	 */
	StatiGioco getFaseCorrente() {
		return faseCorrente;
	}
	/**Setter for the current phase of a player's turn. It is used both for aliens and humans, but the parameter could be different for the two types of players (for example OBJECTSTATE is never present in alien's arrows (instances of Arco))
	 * @param faseCorrente current phase of the game
	 */
	void setFaseCorrente(StatiGioco faseCorrente) {
		this.faseCorrente = faseCorrente;
	}
	
	/**Getter for the next phase of a player's turn. It is used both for aliens and humans, but the returned value could be different for the two types of players (for example OBJECTSTATE is never present in alien's arrows (instances of Arco))
	 * @return the current phase (an instance of the enum StatiGioco)
	 */
	StatiGioco getProssimaFase() {
		return prossimaFase;
	}
	/**Setter for the next phase of a player's turn. It is used both for aliens and humans, but the parameter could be different for the two types of players (for example OBJECTSTATE is never present in alien's arrows (instances of Arco))
	 * @param prossimaFase next phase of the game
	 */
	void setProssimaFase(StatiGioco prossimaFase) {
		this.prossimaFase = prossimaFase;
	}

}
