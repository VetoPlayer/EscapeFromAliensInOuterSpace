package server;

import giocatori.Giocatore;

import java.rmi.RemoteException;
import java.util.List;

import controller.Controller;
import controller.Filter;
import controller.Gioco;

/**
 * @author Lollo
 * The instances of this class are server rooms, i.e. single matches running and controlled by only one server at the same time. This class contains
 * Gioco, Filter, Controller, CommandHandler as attributes, and methods for the creation of a server room (matches) and for receiving players before the match starts
 * 
 */
public class ServerRoom {
	private boolean started;
	private Gioco partita;
	private Filter filter;
	private Controller controller;
	private CommandHandler commandHandler;
	private ConnectionHandler connectionHandler;
	
	/**Constructor
	 * @param numRoom the number that identifies the server room which will be created
	 */
	
	public ServerRoom() {
		partita= new Gioco();
		filter= new Filter();
		controller=new Controller(partita);
		connectionHandler= new ConnectionHandler(partita);
		commandHandler = new CommandHandler();
		started=false;
	}
	
	//Aggiunge un giocatore alla stanza e restituisce l'intero che rappresenta l'id giocatore
	//Fa opportuni controlli riguardo a quando iniziare la partita, deve chiamare StartGame()
	/** Adds the player to the list of notifiers, an attribute of the class Filter. Then
	 * @param rn a remote notifier, that will be added to the list of notifiers in the class Filter
	 * @return the number of players in the match 
	 * @throws RemoteException
	 */
	public int addPlayer(RemoteNotifier rn) throws RemoteException{
		//passo a filter il giocatore aggiunto
		filter.addNotifier(rn);
		//passo al gestore della connessione il giocatore aggiunto
		connectionHandler.addNotifier(rn);
		//getter del numero di giocatori di una partita (nel suo codice c'è un incremento numgioc++)
		return partita.getNumeroG();
	}

	/**
	 * This method starts the match, calls creagiocatori() of the class Gioco to create the right number of aliens and humans and calls the method eseguipartita(), which controls all the match from beginning to end at an high level of abstraction
	 */
	public void start(){
			started=true;
			//creo i giocatori assegnandoli un ruolo
			partita.creaGiocatori(); 
			//aggiungo filter come observer del giocatore
			List<Giocatore> players= partita.getPlayers().getPlayers();
			for(Giocatore g: players){
				g.addObserver(filter);
				filter.nuovoGiocatore(g);
			}
			//aggingo connectionHandler come osservatore della partita
			partita.addObserver(connectionHandler);
			//Fa iniziare la partita, viene invocato una volta per il primissimo turno di gioco. Per i restanti turni ci pensa il command handler
			//Non eseguo dei controlli per vedere se la partita è conclusa poichè la invoco appunto solo il primissimo turno
			//Dato che sono stati creati tutti i giocatori in ENDSTATE, quello che va a fare ora è impostare STARTSTATE al giocatore 1
			// imposto filter su quel giocatore, lo avviso che è il suo turno e imposto il suo stato in STARTSTATE
			partita.eseguiPartita();
	}
	
	
	

	/**Getter
	 * @return the command handler of the server room
	 */
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}
	/**Getter
	 * @return a true boolean if the server room has been created and started
	 */
	public boolean isStarted() {
		return started;
	}



	/**Getter
	 * @return the controller correspondent to the server room
	 */
	public Controller getController() {
		return controller;
	}



}
