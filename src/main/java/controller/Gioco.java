package controller;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import giocatori.Alieno;
import giocatori.Giocatore;
import giocatori.Giocatori;
import giocatori.StatoGiocatore;
import giocatori.TurnoAlieno;
import giocatori.TurnoUmano;
import giocatori.Umano;
import board.Casella;
import board.Effetto;
import board.Mappa;
import carte.CartaEquipaggiamento;
import carte.CarteDelGioco;
import carte.Oggetto;
import controller.StatiGioco;

/**This class supervise how the game works from the inizialization of decks, map and players, to the control of the players' turn on a high level of abstraction, to the winning conditions and the end of the game
 * @author Lollo
 *
 */
/**
 * @author Lollo
 *
 */
public class Gioco extends Observable {
	
	public List<Observer> observers;
	public static final int PASSIUMANO=1;
	public static final int NUMEROTURNI=39;
	public static final int INIZIO=0;
	public static final int NUM_MAX_EQUIPAGGIAMENTO=3;
	public static final int FIRSTPLAYERTURN=1;
	private CarteDelGioco decks;
	private int numGiocatori;
	private Giocatori giocatori;
	private int numturni;
	private Mappa map;
	//Indica il numero del giocatore che sta effettuando il turno
	private int playerTurnNumber;
	private TurnoUmano humanTurn;
	private TurnoAlieno alienTurn;
		
	
	/**
	 * Constructor
	 */
	public Gioco() {
		playerTurnNumber=FIRSTPLAYERTURN;
		//numGiocatori è a inizio ma dopo faccio subito ++, quindi non conta da 0
		numturni=INIZIO;
		decks= new CarteDelGioco();
		map= Mappa.getMappa();
		numGiocatori= INIZIO;
		giocatori=new Giocatori();
		//inizializzo turno umano e alieno
		humanTurn= new TurnoUmano();
		alienTurn= new TurnoAlieno();
	}

	//esegui partita porta avanti la partita di un turno, e notifica filter di ciò
	// esegui partita viene invocato appena si inizia la partita e tutte le volte che finisce il turno di un giocatore
	/**This method supervise the turn of the game on a high level of abstraction
	 * 
	 */
	//NON c'è bisogno che il controller inizializzi il gioco, ci pensa la serverRoom
	//ritorna un booleano che rappresenta se la partita è terminata o meno
	public boolean eseguiPartita(){
		boolean ended=false;
		boolean turnoEseguito=false;
		if(numturni<NUMEROTURNI && !vittoriaAliena()){
			//quando finisce il turno dell'ultimo giocatore, si riparte dal giocatore che ha fatto il primo turno
			while(!turnoEseguito){//serve a saltare i turni dei giocatori morti o vincitori
				if(playerTurnNumber>numGiocatori){
					playerTurnNumber=FIRSTPLAYERTURN;
					numturni++;
				}	
				if(giocatori.getGiocatore(playerTurnNumber).getStato().equals(StatoGiocatore.VIVO)){
					// gli alieni incominciano da StartState
					if(giocatori.getGiocatore(playerTurnNumber) instanceof Alieno)
						giocatori.getGiocatore(playerTurnNumber).setStatoAttuale(StatiGioco.STARTSTATE);
					if(giocatori.getGiocatore(playerTurnNumber) instanceof Umano)
						giocatori.getGiocatore(playerTurnNumber).setStatoAttuale(StatiGioco.OBJECTSTATE);
					turnoEseguito=true;
				}
				playerTurnNumber++;		
			}
			
		}
		else{
			//E' finita la partita
			ended=true;
			for(Umano u : giocatori.getUmani())
				if(u.getStato().equals(StatoGiocatore.VIVO))
					u.setStato(StatoGiocatore.MORTO);
			for(Alieno a :giocatori.getAlieni())
				a.setStato(StatoGiocatore.VINCITORE);
		}
		setChanged();
		notifyObservers("NuovoTurno");
		return ended;
		
	}
		

	/**If this method returns true the game ends and the alien has surely won the game
	 * @return a true boolean if the last human has been killed, so it means that the aliens have won
	 */
	private boolean vittoriaAliena(){
		boolean result=false;
		if(giocatori.isLastUman())
			result=true;
		return result;
	}
	


	/**This method calls the method spostamento(Giocatore,String) which practically implements the movement and set the attribute "Effetto" to the player who has just moved
	 * @param idGiocatore :the id of the player who moves
	 * @param coordinate :the sector where the player wants to go
	 */
	public void spostamento(int idGiocatore, String coordinate){
		Giocatore giocatore=giocatori.getGiocatore(idGiocatore);
		Effetto effetto=map.spostamento(giocatore, coordinate);
		giocatore.setEffettoCasella(effetto);
		if(giocatore instanceof Umano){
			giocatore.setNumPassi(PASSIUMANO);
		}
		
	}
	

	
	 /**This method calls attacco(Giocatori) of the class Alieno which practically implements the attacking action with all his effects
	 * @param idGiocatore :alien player who attacks.
	 */
	 
	public void attacco(int idGiocatore){
		giocatori.getAlieno(idGiocatore).attacco(giocatori);
	}
	
	/**This method calls effettoCasella(int,String) and completes his functions with the call of the method "pescaEquipaggiamento" if the player has picked up a sound card with the object card symbol
	 * @param IdGiocatore :the id of the player to whom the effect is applied
	 */
	public void effettoCasella(int idGiocatore){
		Giocatore g= giocatori.getGiocatore(idGiocatore);
		if(!g.hasSedativo()){
			Effetto effetto=g.getEffettoCasella();
			boolean pescaEquip=decks.effettoCasella(effetto, g);
			if(pescaEquip)
				decks.pescaEquipaggiamento(g);
		}
		else
			g.scartaOggetto(Oggetto.SEDATIVO);
		return;
	}
	
	/**This method controls the actions after the player has moved onto a dangerous sector.
	 * @param IdGiocatore :the id of the player who has just done the movement on the board
	 * @param string :a string that contains the sector that the player wants to declare to all the other players. The other players will believe that him has moved on the declared sector.
	 */
	public void rumore(int idGiocatore, String string){
		Giocatore g= giocatori.getGiocatore(idGiocatore);
		Casella destinazione= map.convertitore(string);
		g.setCasellaRumore(destinazione);
	}
	
	/**This method deals with the special case in which a player have to discard or use a card beacause he's holding too many cards in hand
	 * @param s :a string which is an input of the client containing an uppercase character that stands for the type of object card intended to be used in the turn
	 * @param IdGiocatore :the ID of the player who declares to use an object card he has in his hand, of the type of the parameter "s"
	 */
	public void discard(int idGiocatore, String s){
			Giocatore g=giocatori.getGiocatore(idGiocatore);
			if(g instanceof Umano){
				attivaEquipaggiamento(s, idGiocatore);
			}
			if(g instanceof Alieno){
				Alieno a= giocatori.getAlieno(idGiocatore);
				a.scartaCartaEquipaggiamento(s);
			}
		return;
	}
	
	
	/**This method activates all the types of object card, apart of "luce", which is activated in a second moment with the method "attivaLuce" (in this method there is implemented only an update to the observers of this class)
	 * @param s :a string which is an input of the client containing an uppercase character that stands for the type of object card intended to be used in the turn
	 * @param IdGiocatore :the ID of the player who declares to use an object card he has in his hand, of the type of the parameter "s"
	 */
	public void attivaEquipaggiamento(String s, int idGiocatore){
			Umano g= giocatori.getUmano(idGiocatore);
			g.usaOggetto(s,map,giocatori);
		return;
	}
	
	
	/**This method is called when a player has ended a phase of his turn, in order to move into the next phase, which can be different for the aliens compared to the humans and viceversa
	 * (for example aliens can't use object cards and humans can attack only with an object card)
	 * @param statoAttuale actual phase of the game that is just terminated
	 * @param statoProssimo next phase of the game
	 * @param IdGiocatore an int value that identifies the player
	 * @return an enum StatiGioco, that represents the next phase of the turn
	 */
	public StatiGioco nextPhase(StatiGioco statoAttuale,StatiGioco statoProssimo, int idGiocatore){
		Giocatore g= giocatori.getGiocatore(idGiocatore);
		StatiGioco next=StatiGioco.ERRORE;
		if(g instanceof Umano)
			next=humanTurn.nextPhase(statoAttuale,statoProssimo);	
		if(g instanceof Alieno)
			next=alienTurn.nextPhase(statoAttuale,statoProssimo);
		return next;
	}
	
	
	
	//String rappresenta una casella
	/**This method activates the object card "luce".
	 * @param s :a string which is an input of the client containing the sector where he wants to discover other players positions. Not only this sector will be illuminated, but even the six around it in the hexagonal map.
	 * The string will be trasformed in a sector object thanks to the method "convertitore".
	 * @param IdGiocatore : the player who has activated the card
	 */
	public void attivaLuce(int idGiocatore, String s){
		Casella casellaLuce = map.convertitore(s);
		Umano g= giocatori.getUmano(idGiocatore);
		CartaEquipaggiamento luce= g.getCartaEquipaggiamento(Oggetto.LUCE);
			luce.luce(casellaLuce, getPlayers(), getMap());
	}
	
	
	
	
	
	/**It calls the method with the same name in the class Giocatori. It creates the right number of aliens and humans correspondant to the number of clients who will play in the game, and it places them to the initial sectors
	 *
	 */
	public void creaGiocatori(){
		giocatori.creaGiocatori(numGiocatori, map.getSpawnumani(), map.getSpawnalieni());
	}
	
	
	
	
	
	/**Getter that when is called increments the number of players in the game
	 * @return the number of players in a match
	 */
	public int getNumeroG(){//potrebbe portare a errori il fatto che dal nome sembra getter ma in realtà incrementa pure, remember it
		numGiocatori++;
		return numGiocatori;
	}
	
	
	
	
	

	/**Getter
	 * @return an instance of CarteDelGioco, who contains the three decks of the game
	 */
	public CarteDelGioco getDecks() {
		return decks;
	}




	/**Getter
	 * @return the board of the game,the only instance of Mappa
	 */
	public Mappa getMap() {
		return map;
	}


	/**Getter
	 * @return arraylist containing all the players of a match, an instance of the class Giocatori
	 */
	public Giocatori getPlayers() {
		return giocatori;
	}


	/**Getter
	 * @return the id of the player who is doing the turn at the moment
	 */
	public int getPlayerTurnNumber() {
		return playerTurnNumber;
	}

	/**Getter
	 * @return the number of the turn of the game of a player (the maximum value is the value of the constant NUMERO_TURNI)
	 */
	public int getNumturni() {
		return numturni;
	}

	/**Setter. It is used only for testing
	 * @return the number of the turn of the game of a player
	 */
	public void setNumturni(int numturni) {
		this.numturni = numturni;
	}




	


	
	





	
	
	
	
	
}