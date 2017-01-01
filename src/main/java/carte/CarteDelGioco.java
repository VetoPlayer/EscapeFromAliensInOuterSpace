/**
 * 
 */
package carte;

import giocatori.Giocatore;
import board.Effetto;
/**
 * @author Lollo
 *This class contains the method that creates the three decks of the game, and other methods for picking object cards from the deck after a movement on the board
 *This is the only class of the package carte used by the class Gioco and its purpose is to have this class as the only point of access for every class outside that package
 */
public class CarteDelGioco{
	private MazzoEquipaggiamento mazzoEquipaggiamento;
	private MazzoScialuppa mazzoScialuppa;
	private MazzoSuono mazzoSuono;
	
	/**Constructor of all the three decks of the game
	 * 
	 */
	public CarteDelGioco() {
		mazzoEquipaggiamento = new MazzoEquipaggiamento();
		mazzoSuono = new MazzoSuono();
		mazzoScialuppa = new MazzoScialuppa();
	}
	/**This method controls what happens after the end of the movement and decide if the player will pick an escape card, a sound card or do nothing. 
	 * @param effetto :enum that contains all the possible actions after a movement of a player in regards to the choice of picking a card or not
	 * @param giocatore :the player who has just done a movement on the board
	 * @return This method returns a boolean parameter: it is true if a player has just ended his movement on a dangerous sector and he has picked a sound card with the object card symbol, otherwise it's false (the player has picked a sound card without the object card symbol or an escape card).
	 * The return value will be read by a method EffettoCasella, who picks only one parameter in input (ID giocatore) instead of two.
	 */
	public boolean effettoCasella(Effetto effetto, Giocatore giocatore){
		boolean pescaEquipaggiamento=false;
		if(effetto==Effetto.NESSUN_EVENTO){
			giocatore.setSuono(Suono.NESSUNRUMORE);
		}
			
		else{
			if(effetto==Effetto.PESCA_SUONO){
				CartaSuono cartapescata=mazzoSuono.pescaCarta();
				giocatore.addSuonoInMano(cartapescata);
				cartapescata.effettoCarta(giocatore);
				if(cartapescata.hasEquipaggiamento())
					pescaEquipaggiamento=true;
			}
			if(effetto==Effetto.PESCA_SCIALUPPA){
				CartaScialuppa cartapescata= mazzoScialuppa.pescaCarta();
				giocatore.addScialuppaInMano(cartapescata);
			
			}
		}
		return pescaEquipaggiamento;
	}
		
	/** This method adds the object card picked up to the hand of the player
	 * @param giocatore the player who has to pick the object card
	 */
	public void pescaEquipaggiamento(Giocatore giocatore){
		CartaEquipaggiamento carta=mazzoEquipaggiamento.pescaCarta();
		giocatore.aggiungiEquipaggiamento(carta);
	}
	
	/**Getter
	 * @return the object card deck
	 */
	public MazzoEquipaggiamento getMazzoEquipaggiamento() {
		return mazzoEquipaggiamento;
	}
	
	/**Getter
	 * @return the escape card deck
	 */
	public MazzoScialuppa getMazzoScialuppa() {
		return mazzoScialuppa;
	}
	
	/**Getter
	 * @return the sound card deck
	 */
	public MazzoSuono getMazzoSuono() {
		return mazzoSuono;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
