package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import controller.Gioco;

/** This class contains the methods regarding the disconnection of a player during the game
 * @author Andrea
 *
 */
public class ConnectionHandler implements Observer {

	Gioco partita;
	private List<RemoteNotifier> notifiers;
	
	
	/**Costructor of ConnectionHandler
	 * @param partita The running game
	 */
	public ConnectionHandler(Gioco partita) {
		notifiers= new ArrayList<RemoteNotifier>();
		this.partita=partita;
	}

	/**Add the notifier representing the player who just joined the server room
	 * @param rn the remote notifier who is playing his turn at the moment
	 */
	public void addNotifier(RemoteNotifier rn){
		this.notifiers.add(rn);
		return;
	}
	
	/**Announce to every player (except the disconnected one) that a player has disconnected
	 * @param id
	 * @throws RemoteException
	 */
	private void announceAll(int id) throws RemoteException{
		for(RemoteNotifier rnt: notifiers){
			if(rnt.getId()!= id)
				rnt.notifyMessage("Il giocatore numero "+id+" si è disconnesso");
				
		}
		
	}

	/**
	 * @author Andrea
	 *This class manages and implements a check on the possibility that the active player may be disconnected
	 */
	private class NextTurn extends TimerTask{
		private int numPlayer;
		private int numTurn;
		public NextTurn(int idPlayer, int TurnNumber){
			numPlayer= idPlayer;
			numTurn= TurnNumber;
		}
		
		/* (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			//Se dopo tutto questo tempo il giocatore non ha ancora finito il proprio turno
			if(numTurn== partita.getNumturni() && numPlayer== partita.getPlayerTurnNumber()-1){
				try {
					//Annuncio a tutti gli altri giocatori della disconnessione
					announceAll(numPlayer);
					//Porto avanti la partita per il prossimo turno
					partita.eseguiPartita();					
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			
			}
		}
		
	}
	
	
	//Se il giocatore di turno non invia nuovi comandi entro 50000 ms imposto il suo stato in ENDSTATE e passo il turno al prossimo giocatore
			

			//L'update inizializza il timer del turno, l'update viene chiamato all'inizio di ogni turno
			
			@Override
			public void update(Observable arg0, Object arg1) {
				Gioco g =(Gioco)arg0;
				Timer t= new Timer();
				t.schedule(new NextTurn(g.getPlayerTurnNumber()-1, g.getNumturni()), 120000);
			}
			
	
	
}
