package carte;

import giocatori.Alieno;
import giocatori.Giocatore;
import giocatori.Giocatori;
import giocatori.StatoGiocatore;
import giocatori.Umano;
import board.Casella;
import board.Mappa;


/**
 * @author Lollo
 * This class includes all the methods which implement the effects of the Object Cards (Carta Equipaggiamento). 
 * 
 */
public class CartaEquipaggiamento {
	public static final int PASSIADRENALINA=2;
	public static final int CASELLE_ADIACENTI=1;
	private boolean attivata;
	private Oggetto oggetto;

	/**Constructor of all the object cards
	 * @param oggetto this parameter distinguish the type of object cards created 
	 */
	public CartaEquipaggiamento(Oggetto o){
		this.oggetto=o;
		attivata=false;
	
	}
	
	/**Getter
	 * @return an instance of the enum Oggetto, the type of the object card
	 */
	public Oggetto getOggetto(){
		return oggetto;
	}
	
	
	/**Getter
	 * @return a boolean which indicates if the object card is activated or not
	 */
	public boolean isAttivata() {
		return attivata;
	}
	/**Setter
	 * @param attivata a boolean which is set to true if the object card is activated
	 */
	public void setAttivata(boolean attivata) {
		this.attivata = attivata;
	}
	
	/**This method implements the effect of the object card "teletrasporto". The player who uses it can return to an established sector, moving from any of the sectors of the map where he is at the moment
	 * In the original game the sector is CasellaUmani, the sector where humans are placed at the start of the game
	 * @param giocatore the player who uses the card
	 * @param spawnUmani the sector where the player ends after using the object
	 */
	public void teletrasporto(Umano giocatore, Casella spawnUmani){
		setAttivata(true);
		giocatore.setPosizioneAttuale(spawnUmani);
	}
	

	/** The effect of the card "adrenalina" is simply raising the possible number of movements in a single turn to a default value (2, according to the actual rules), for a human who use this card.
	 * At the end of the turn another method will restore the original situation
	 * @param giocatore player who uses the object card "adrenalina"
	 * 
	 */
	public void adrenalina(Umano giocatore){
		setAttivata(true);
		giocatore.setNumPassi(PASSIADRENALINA);
		
	}
	
	/**This method is activated when a human uses the object card "sedativo" and calls the method setSedativo(true)
	 * @param u human who uses the object card "sedativo"
	 */
	public void sedativo(Umano u){
		setAttivata(true);
		u.setSedativo(true);
	}
	
	
	
	/**This method implements the effect of the object card "luce". This method calls setIlluminato for the players who are in the sectors illuminated
	 * @param casella the six sectors around this one, including itself, will be illuminated, i.e. every player in those seven sectors will be viewed and their position will be discovered by all the players
	 * @param players arraylist of the players of the match where the card is activated
	 * @param map board of the game
	 */
	
	public void luce(Casella casella,Giocatori players, Mappa map){
		setAttivata(true);
		for(Giocatore g : players.getPlayers()){
			if(map.raggiungibile(casella, casella, g.getPosizioneAttuale(),CASELLE_ADIACENTI )	|| g.getPosizioneAttuale()==casella){
				g.setIlluminato();
		return;		
			}
		}
			
	}

	/** This method implements the effect of the card "attacco", usable only by the humans. The death could be avoided only if the player attacked is an human with the card "difesa" in his hands.
	 * The card "difesa" is activated without asking an input to the client 
	 * If the player attacked dies, his state in the game is set as "MORTO", he is not placed anymore in a sector of the board (rimuoviGiocatoreDaBoard()) and all his object cards in his hand are eliminated("scartacarte")
	 * @param attaccante the player who use the card, who attacks, in the sector where he is at that moment
	 * @param giocatori all the players of a game
	 */
	
	public void attacco(Umano attaccante, Giocatori giocatori){
		setAttivata(true);
		for(Umano e: giocatori.getUmani()){
			if(e.getPosizioneAttuale()==attaccante.getPosizioneAttuale() && !e.equals(attaccante))
				if(!e.hasOggetto(Oggetto.DIFESA)){
					e.setStato(StatoGiocatore.MORTO);
					e.rimuoviGiocatoreDaBoard();
					e.scartacarte();
				}
				else{
					e.difesa();
				}					
		}
		for(Alieno e: giocatori.getAlieni())
			if(e.getPosizioneAttuale()==attaccante.getPosizioneAttuale()){
				e.setStato(StatoGiocatore.MORTO);
				e.rimuoviGiocatoreDaBoard();
				e.scartacarte();
			}	
		return;	
	}
	
	
	
	
	
	
	
	
}
