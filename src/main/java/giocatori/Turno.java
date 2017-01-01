package giocatori;

import controller.StatiGioco;

/**
 * @author Lollo
 * This interface symbolize the turn of the game and has got a method for passing into the next phase of a turn
 */
public interface Turno {
	
	/** Method inheritable by the class TurnoUmano and TurnoAlieno, that applies the change of the phase of a turn
	 * @param statoAttuale current phase of the turn
	 * @param statoProssimo next phase of the turn. It will be the return value, unless this attribute of StatiGioco is DONTKNOW
	 * @return the next phase of the turn. If the return value is ERRORE (attribute of StatiGioco), this shows that an error has occurred in the algorithm
	 */
	abstract StatiGioco nextPhase(StatiGioco statoAttuale, StatiGioco statoProssimo);

}
