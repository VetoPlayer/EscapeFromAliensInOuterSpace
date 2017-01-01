package server;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

/**	
 *
 * Class which implements the CallableClient interface.
 * It allows the server to call the remote client.
 */
public class RemoteCallableClient implements CallableClient {
	public static final int NUMMAXGIOCATORI=2;
	public static final int NUMMINGIOCATORI=2;
	
	@Override
	public void setClientPort(int port) throws RemoteException {
		//TODO se il booleano started==true non possono essere aggiunti nuovi giocatori
		
		Registry registry;
		try {
			//Locate the registry and get the remote notifier
			registry = LocateRegistry.getRegistry(port);
			RemoteNotifier rn = (RemoteNotifier)registry.lookup("Notifier");
			//Aggiungo il client alla prima stanza disponibile e gli restituisco il suo idGiocatore
			int idGioc;
			idGioc=Server.getServer().addNotifier(rn);
			String msg= "Ti sei collegato con successo: sei il giocatore numero "+idGioc+"\n";
			rn.setId(idGioc);
			rn.notifyMessage(msg);
			//imposto dei controlli che fanno iniziare la partita se ho raggiunto il massimo numero di giocatori
			//oppure per il timer
			if(idGioc==NUMMINGIOCATORI){
				Timer t= new Timer();
				t.schedule(new GameInitializier(), 120000);
			}
			if(idGioc==NUMMAXGIOCATORI)
				Server.getServer().startServerRoom();
			//imposto il numero della stanza a cui il client è collegato
			int numRoom= Server.getServer().getNumServerRoom();
			rn.setServerRoomNumber(numRoom);
			
		} catch (Exception ex){
			ex.printStackTrace();
		}

	}
	
	/**
	 * @author Lollo
	 * Inner class of Remote Callable Client that extends TimerTask. It has the "run()" method that calls the method startServerRoom on the class Server. The timer
	 * let the match start only if it hasn't started yet, only if the number of players has not reached in time the maximum number (this control is done on the method setClientPort which initialize the timer).
	 */
	private class GameInitializier extends TimerTask{

		@Override
		public void run() {
			//Il timer fa partire la partita se e soltanto se non è ancora iniziata, non la fa incominciare una seconda volta!
			Server.getServer().startServerRoom();			
		}
		
	}

		
	}
	

		
		
		

