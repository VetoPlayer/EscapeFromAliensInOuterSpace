package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that extends Remote and contains only the declaration of the method handleCommand, which will be implemented in the class CommandHandler
 *
 */
public interface RemoteCommandHandler extends Remote {

	/** This method takes the string from the client through the rmi interface, analizes it and calls the right methods of the class Controller
	 * @param input string received by the client through the rmi interface and constructed before being sent by the class CodificaClient in order to be read and handled by this method
	 * @throws RemoteException
	 */
	public void handleCommand(String input) throws RemoteException;
}
