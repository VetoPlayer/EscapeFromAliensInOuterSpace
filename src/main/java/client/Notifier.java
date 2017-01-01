package client;

import java.rmi.RemoteException;

import server.RemoteNotifier;
import controller.StatiGioco;

/**
 * @Andrea
 * Method of the client package that implements the RemoteNotifier interface in the server package
 */
public class Notifier implements RemoteNotifier {

	private Client client;

	public Notifier(Client client){
		this.client=client;
	}
	@Override
	public void notifyMessage(String message) throws RemoteException {
		System.out.println(message);

	}
	@Override
	public StatiGioco getStatoAttuale() throws RemoteException {
		return client.getStatoAttuale();
	}
	@Override
	public void setStatoAttuale(StatiGioco statoAttuale) throws RemoteException {
		client.setStatoAttuale(statoAttuale);
		return;
	}
	
//Potrebbe non essere utilizzato il getter
	@Override
	public int getId() throws RemoteException {
		return client.getId();
	}
	@Override
	public void setId(int id) throws RemoteException {
		client.setId(id);
	}
	@Override
	public void setStatoGCreato() {
		client.setStatoGCreato();
		
	}
	@Override
	public void setServerRoomNumber(int numRoom) throws RemoteException {
		client.setNumRoom(numRoom);
		
	}
	
	

}
