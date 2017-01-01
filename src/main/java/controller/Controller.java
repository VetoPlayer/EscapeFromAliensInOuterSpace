package controller;

/**
 * @author Andrea
 * This class implements all the methods called by CommandHandler and calls the methods of the model (those methods are almost all defined in the class Gioco). Along
 * with Filter this class can be considered the controller of MVC
 */
public class Controller{
	Gioco partita;
	public static final int FINEGIOCO=9;
		
	/**Constructor
	 * @param partita instance of the class Gioco, the match the controller supervise
	 */
	public Controller(Gioco partita) {
		this.partita=partita;
	}
	//NOTA: in gioco esiste una funzione che ti porta ad uno stato del gioco successivo
	//NON faccio il cambiamento di stato!! 
	//In ogni caso quando cambio lo stato filter se ne accorge

	
	//StartController
	/** Controller of the movement of a player
	 * @param idGioc Id of the player who does the movement on the board
	 * @param value sector of destination
	 */
	public void spostamento(int idGioc, String value) {
		//In questo caso value è la stringa di una casella
		partita.spostamento(idGioc, value);
		
	}
	
	//askController Se Y attacca, altrimenti pesca il suono
	/** Controller of the phase of the game when an alien has to answer if he wants to attack or pick a sound card
	 * @param idGioc Id of the player who is doing the turn
	 * @param value string containing "Y" or "X". In the first case the method attacco() is called, in the second case the next phase of the turn becomes DRAWSTATE and the method suono() is called
	 */
	public void ask(int idGioc, String value){
		if("Y".equals(value)){
			attacco(idGioc);
		}
		if("N".equals(value)){
			suono(idGioc);
		}
		return;

	}
	
	//endController: viene invocata quando un giocatore ha terminato il proprio turno: quello che fa è chiamare il prossimo giocatore che deve svolgere il proprio turno
	
	/**Controller that let the start of the first turn of the game happen and controls if the match is over or if another turn has to be played (calling eseguiPartita() from class Gioco)
	 * @param idGioc id of the player
	 * @param value string containing "End"
	 */
	public void end() {
		boolean ended=partita.eseguiPartita();
		//Faccio un controllo per vedere che il gioco non sia già finito
		if(ended){
			ended=true;
		}
			
	}

	//suonoController
	//risolve l'effetto della carta suono pescato
	/**Controller of the pick of the sound card from a deck
	 * @param idGioc id of the player
	 */
	private void suono(int idGioc) {
		partita.effettoCasella(idGioc);
	}
	
	
	/**Getter for the actual player turn number
	 * @param idGioc id of the player
	 */
	public int playerTurn() {
		return partita.getPlayerTurnNumber();
	}
	
	
	//attaccoController
	/**Controller of the attack. It also set the next phase of the turn (StatiGioco)
	 * @param idGioc id of the player
	 */
	private void attacco(int idGioc) {
		partita.attacco(idGioc);
		StatiGioco prossimoStato=partita.nextPhase(StatiGioco.ATTACKSTATE,StatiGioco.DONTKNOW, idGioc);
		partita.getPlayers().getGiocatore(idGioc).setStatoAttuale(prossimoStato);
	}
	
	
	//oggettoController
	 /**Controller of the part of the turn where a human player has to choose to visualize his object cards or not
	 * @param idGioc id of the player
	 * @param value string containing "Y" or "N"
	 */
	public void mostraOggetto(int idGioc, String value){
		 if("Y".equals(value))
			 partita.getPlayers().getGiocatore(idGioc).mostraEquipaggiamento();
		 if("N".equals(value)){
			 StatiGioco prossimoStato=partita.nextPhase(StatiGioco.OBJECTSTATE,StatiGioco.DONTKNOW, idGioc);
			 partita.getPlayers().getGiocatore(idGioc).setStatoAttuale(prossimoStato);
			 if(prossimoStato.equals(StatiGioco.DRAWSTATE))
				suono(idGioc); 
			}
			return;

	 }
	 //Vado nella fase successiva se e soltanto se il giocatore mi dice che non intende utilizzare più carte oggetto
	 /**Controller of the activation of an object card (luce excluded)
	 * @param idGioc id of the player
	 * @param value string containing a capital letter indicating the type of object card to activate
	 */
	public void usaOggetto(int idGioc, String value){
		 partita.attivaEquipaggiamento(value,idGioc);
		 
	 }
	
	//in antichità questa classe era un observer per cambiare il turno da START a quello successivo, implementando observers	
		
	 /**Controller of the activation of the object card "luce"
	 * @param idGioc id of the player
	 * @param value string containing "luce"
	 */
	public void luce(int idGioc, String value){
		 partita.attivaLuce(idGioc,value);
	 }
		
	/**Controller about a player discarding a card
	 * @param idGioc id of the player
	 * @param value string containing "rumoreAltroSettore"
	 */
	public void discard(int idGioc, String value){
		 partita.discard( idGioc, value);
	}
	 
	 
	 
	 
	 /**Controller of the declaration of the sector where a sound card "noise in another sector" is picked up
	 * @param idGioc id of the player
	 * @param value string containing "rumoreAltroSettore"
	 */
	public void rumoreAltroSettore(int idGioc, String value){
		 partita.rumore(idGioc, value);
	}
	
}
	
	
	