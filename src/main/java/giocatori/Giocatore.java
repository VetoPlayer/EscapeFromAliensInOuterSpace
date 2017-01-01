package giocatori;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import board.Casella;
import board.Effetto;
import carte.CartaEquipaggiamento;
import carte.CartaScialuppa;
import carte.CartaSuono;
import carte.Oggetto;
import carte.Suono;
import controller.StatiGioco;

/**This is an abstract class that represents the player without making a distinction on the role. It has two subclasses that represents aliens and humans. 
 * A player is characterized by an int value (ID of the player), a Casella (sector where the player is at the moment), an int (numPassi) that says how many moves
 * a player can do in his turn (one for the humans, two for the aliens, without counting special effects), the state of the player(dead, winner or still in the game),
 * a list of object cards in his hand, the current phase of his turn, the sound card and the escape card he has just picked up, and the action he has to do after having picked up a card (sound or escape).
 * The class Filter, with the method "update", receives the many strings passed by this class thanks to the notifyObservers method.
 * @author Lollo
 *
 */

public abstract class Giocatore extends Observable {
	private int idGiocatore;
	private Casella posizioneAttuale;
	private int numPassi;
	private StatoGiocatore stato;
	List<CartaEquipaggiamento> equipaggiamento;
	private CartaSuono suonoInMano;
	private Suono suono;
	private Casella casellaRumore;
	private CartaScialuppa scialuppaInMano;
	private Effetto effettoCasella;
	public List<Observer> observers;
	private StatiGioco statoAttuale;
	private Turno turn;
	private boolean sedativo;

	/**This method creates a player, initialize his state and his attributes and puts it in the right sector for the start of the game. Alieno and Umano will inherit the method
	 * and they will set a different int value (numPassi) and a different initial sector.
	 * @param id :number that identifies the player created. This attribute is unique for every player that plays in the same match
	 * @param spawn :sector where players are placed at the beginning
	 * @param passi :number of possible moves for a player in a single turn
	 */
	public Giocatore(int id, Casella spawn,int passi,Turno turn) {
		this.observers = new ArrayList<Observer>();
		this.equipaggiamento= new ArrayList<CartaEquipaggiamento>();
		posizioneAttuale= spawn;
		this.idGiocatore=id;
		stato=StatoGiocatore.VIVO;
		statoAttuale=StatiGioco.ENDSTATE;
		numPassi=passi;
		this.turn=turn;
		sedativo=false;
	
		
	}
	
		
	/**Getter
	 * @return the current sector of the board occupied by the player
	 */
	public Casella getPosizioneAttuale() {
		return posizioneAttuale;
	}

	/**This setter method notifies to the observer if the player has effectively moved in a new position or not, thanks to two different messages
	 * @param posizioneAttuale
	 */
	public void setPosizioneAttuale(Casella posizioneAttuale) {
		setChanged();
		if(this.posizioneAttuale.equals(posizioneAttuale))
			notifyObservers("MossaNonLecita");
		else{
			this.posizioneAttuale = posizioneAttuale;
			notifyObservers("NuovaPosizione");
		}
		
	}
	
	/**This method calls the nextPhase method of the human turn class "TurnoUmano", if the player is a human. If he is an alien, it calls the nextPhase method of the player turn class "TurnoAlieno".
	 * @param statoAttuale current phase of the turn
	 * @param statoProssimo next phase of the turn
	 * @return an attribute of the enum StatiGioco, the result of the human's or alien's nextPhase method
	 */
	public StatiGioco nextPhase(StatiGioco statoAttuale,StatiGioco statoProssimo){
		StatiGioco next=StatiGioco.ERRORE;
		next=turn.nextPhase(statoAttuale, statoProssimo);
		return next;
	}
	

	/**Getter
	 * @return the current phase of the turn (a value of the enum StatiGioco)
	 */
	public StatoGiocatore getStato() {
		return stato;
	}

	/**This setter method notifies to the observer (Filter) what is the new state of the player, a value of the enum StatiGioco, with two different messages ("Giocatore Morto" when the player
	 * dies, "Giocatore Vincitore" if the player wins). This method can't set to "VIVO" the state of the player at the beginning of the match. 
	 * @param stato
	 */
	public void setStato(StatoGiocatore stato) {
		this.stato = stato;
		setChanged();
		if(stato==StatoGiocatore.MORTO)
			notifyObservers("GiocatoreMorto");
		if(stato==StatoGiocatore.VINCITORE)
			notifyObservers("GiocatoreVincitore");
	}
	
	/**This method sends to the observer (class Filter) the message "Mostra Equipaggiamento" when a player picks up an object card.
	 * 
	 */
	public void mostraEquipaggiamento(){
		setChanged();
		notifyObservers("MostraEquipaggiamento");
	}
	
	/**Getter
	 * @return all the object cards in the hand of a player
	 */
	public List<CartaEquipaggiamento> getEquipaggiamento(){
		return equipaggiamento;
	}
	
	/**This method eliminates an object card in the player's hand of a type declared in input
	 * @param equip the type of the instance of an object card that will be removed from the player's hand
	 */
	public void scartaOggetto(CartaEquipaggiamento equip){
		int i;
		for(i=equipaggiamento.size()-1;i>=0;i--)
			if(equipaggiamento.get(i).equals(equip) && equipaggiamento.get(i).isAttivata()){
				equipaggiamento.remove(i);
			}	
		return;
	}
	
	/**This method eliminates an object card in the player's hand of a type declared in input
	 * @param o the instance of the enum Oggetto that corresponds to the type of the object card that will be removed from the player's hand
	 */
	public void scartaOggetto(Oggetto o){
		int i;
		for(i=equipaggiamento.size()-1;i>=0;i--)
			if(equipaggiamento.get(i).getOggetto().equals(o)){
				equipaggiamento.remove(i);
				return;
			}
		return;
	}
	
	
	//Ho paura esca fuori e provi a scartare dei null
	/**
	 * Clears the list of all the object card in the hand of a player. This method is called after a successful attack when a player dies
	 */
	public void scartacarte(){
		equipaggiamento.clear();
	}
	
	
	/**This method is a getter for one of the object cards in the hand of a player, the only activated one
	 * @return an object card of the type declared in input if there is one, otherwise returns null
	 */
	public CartaEquipaggiamento getOggettoAttivato(){
		for(CartaEquipaggiamento e : equipaggiamento)
			if(e.isAttivata())
				return e;
		return null;
	}
	
	/**This method is a getter for one of the object cards in the hand of a player of the type declared in the input
	 * @param oggetto a type of object card described in the enumeration class "Oggetto"
	 * @return an object card of the type declared in input if there is one, otherwise returns null
	 */
	public CartaEquipaggiamento getCartaEquipaggiamento(Oggetto o){
		for(CartaEquipaggiamento e : equipaggiamento)
			if(e.getOggetto().equals(o))
				return e;
		return null;
	}
	
	
	
	//Il caso in cui il giocatore pesca il 4° equipaggiamento viene gestito a livello di Filter
	//Se il giocatore è un alieno deve scegliere quale tra le 4 carte scartare
	//Se il giocatore è un umano deve scegliere quale tra le 4 carte utilizzare
	/**This method adds an object card on the hand of a player
	 * @param carta :the object card that the method adds to the hand of the player
	 */
	public void aggiungiEquipaggiamento(CartaEquipaggiamento carta){
			equipaggiamento.add(carta);		
			setChanged();
			notifyObservers("NuovoEquipaggiamento");
	}

	/** Getter
	 * @return the id of the player
	 */
	public int getIdGiocatore() {
		return idGiocatore;
	}

	/**Getter
	 * @return the sound card in the hand of the player
	 */
	public CartaSuono getSuonoInMano() {
		return suonoInMano;
	}

	/**Getter
	 * @return the escape card in the hand of the player
	 */
	public CartaScialuppa getScialuppaInMano() {
		return scialuppaInMano;
	}


	/** Adds the sound card just picked up in the hand of the player
	 * @param suonoInMano sound card added to the hand of the player. It will be discarded immediately, or after picking up an object card from the deck if the sound card has the equipment symbol on it
	 */
	public void addSuonoInMano(CartaSuono suonoInMano) {
		this.suonoInMano = suonoInMano;
	}


	
	/** Adds the escape card just picked up in the hand of the player and apply the state of winner to the player who has picked up a "funzionante" escape card and notifies it to the observers, otherwise only notify to the observers that the escape sector "wasn't working"
	 * @param ScialuppaInMano escape card added to the hand of the player. It will be discarded immediately after his effect is concluded
	 */
	public void addScialuppaInMano(CartaScialuppa scialuppa) {
		this.scialuppaInMano = scialuppa;
		if(scialuppaInMano.isFunzionante()){
			setChanged();
			notifyObservers("ScialuppaFunzionante");
			this.setStato(StatoGiocatore.VINCITORE);
			this.setPosizioneAttuale(null);
			this.scartacarte();
		}
		else
			notifyObservers("ScialuppaNonFunzionante");
			
	}

	/**This method set to null the attribute CartaScialuppa of a player (class Giocatore)
	 * 
	 */
	public void scartaScialuppa(){
		this.scialuppaInMano=null;
	}
	/**This method set to null the attribute CartaSuono of a player(class Giocatore)
	 * 
	 */
	public void scartaSuono(){
		suonoInMano=null;
	}


	/**Getter
	 * @return the number of steps a player can do in a single move
	 */
	public int getNumPassi() {
		return numPassi;
	}


	/**Setter
	 * @param numPassi the number of steps a player can do in a single move
	 */
	public void setNumPassi(int numPassi) {
		this.numPassi = numPassi;
	}

	/**Getter
	 * @return the attribute effettoCasella of the class Giocatore (one of the values of the enum Effetto)
	 */
	public Effetto getEffettoCasella() {
		return effettoCasella;
	}

	/**Setter
	 * @param effettoCasella the attribute effettoCasella of the class Giocatore (one of the values of the enum Effetto)
	 */
	public void setEffettoCasella(Effetto effettoCasella) {
		this.effettoCasella = effettoCasella;
	}


	/**Getter
	 * @return the current state of the player's turn (one of the values of the enum StatiGioco)
	 */
	public StatiGioco getStatoAttuale() {
		return statoAttuale;
	}


	/** It's called only when the state of the player's turn changes. This method sets the new state and notifies it to the observer sending the string "NuovoStato"
	 * @param statoAttuale the new current state of the turn
	 */
	public void setStatoAttuale(StatiGioco statoAttuale) {
		this.statoAttuale = statoAttuale;
		setChanged();
		notifyObservers("NuovoStato");
	}


	/**Getter
	 * @return one of the values of the enum Suono
	 */
	public Suono getSuono() {
		return suono;
	}


	/**It's called when a player picks up a sound card, and set the new value of the attribute Suono.
	 * The Filter who reads the message "Suono", thanks to the notifyObservers method, will implement a different code for every value of the enum Suono
	 * @param suono one of the values of the enum Suono
	 */
	public void setSuono(Suono suono) {
		this.suono = suono;
		setChanged();
		notifyObservers("Suono");
	}


	/**Getter of a dangerous sector
	 * @return The dangerous sector where the player has just ended his turn
	 */
	public Casella getCasellaRumore() {
		return casellaRumore;
	}


	/**Setter of the dangerous method where the player has just landed. It notifies to the observer (Filter) the string "RumoreAltroSettore"
	 * @param casellaRumore object of the class Casella where the player has just landed.
	 */
	public void setCasellaRumore(Casella casellaRumore) {
		this.casellaRumore = casellaRumore;
		setChanged();
		notifyObservers("RumoreAltroSettore");
	}

//Nota: ho levato isIlluminato poichè se filter viene chiamato è perchè quel giocatore è stato illuminato
	/**
	 * It's called by the method "luce" of the class CartaEquipaggiamento and sends to the Filter class (the observer) the string "illuminato". The player who calls this method has
	 * had his position revealed by the object card "luce".
	 */
	public void setIlluminato() {
		setChanged();
		notifyObservers("illuminato");
	}

	
	/**Getter
	 * @return the value of the boolean sedativo, attribute of this class. It's true if the player has activated a card "sedativo" in this turn.
	 */
	public boolean hasSedativo() {
		return sedativo;
	}

	/**It's called by the method in CartaEquipaggiamento with the same name when the card "sedativo" is activated
	 * @param sedativo a boolean: if it's true, the method sets true the attribute "sedativo" in this class
	 */
	public void setSedativo(boolean sedativo) {
		this.sedativo = sedativo;
	}
	
	/**
	 * Method called when a player dies by an attack, and set to null the sector occupied by the player on the board
	 */
	public void rimuoviGiocatoreDaBoard(){
		posizioneAttuale=null;
	}

	
		

}

	