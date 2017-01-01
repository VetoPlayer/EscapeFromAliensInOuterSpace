package server;


import java.rmi.Remote;
import java.rmi.RemoteException;

import controller.StatiGioco;

/**
 * @Andrea
 * Remote interface exposed by the clients. 
 * This allows the server to send messages to the clients.
 *
 */
public interface RemoteNotifier extends Remote {

	/**
	 * Method that receives a message from a caller
	 * @param message sent by the caller
	 * @param source who is sending the message
	 * @throws RemoteException
	 */
	void notifyMessage(String message) throws RemoteException;
	
	
	/**Getter
	 * @return the id of the player-client
	 * @throws RemoteException
	 */
	public int getId() throws RemoteException;
	
	
	/**Setter
	 * @param id the id of the player-client
	 * @throws RemoteException
	 */
	public void setId(int id) throws RemoteException;
	
	
	/**Setter
	 * @param statoAttuale the new current phase of the turn
	 * @throws RemoteException
	 */
	public void setStatoAttuale(StatiGioco statoAttuale) throws RemoteException;
	
	
	/**Set to EndState the current phase of the turn of the new player created
	 * @throws RemoteException
	 */
	public void setStatoGCreato() throws RemoteException;
	
	
	/**Getter
	 * @return the current phase of the turn of the client
	 * @throws RemoteException
	 */
	public StatiGioco getStatoAttuale() throws RemoteException;
	
	
	/**Setter
	 * @param numRoom the number of the server room where the client will play
	 * @throws RemoteException
	 */
	public void setServerRoomNumber(int numRoom) throws RemoteException;
	
}
