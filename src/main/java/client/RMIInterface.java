package client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.CallableClient;
import server.RemoteCommandHandler;

/**
 * 
 * Class that implements the NetworkInterface.
 * It implements all the methods such that they can communicate
 * with the server through the RMI protocol.
 */
public class RMIInterface  {
	
	private RemoteCommandHandler command; 
	
	
	/**Method that register the client to the registry, in order to be callable by the server
	 * @param porta random generated port number that identifies the client on the registry, in order to be callable by the server
	 * @return a boolean true if there aren't errors while getting the registry, getting the remote command handler reference and registering the client to the server through the rmi registry
	 */
	public boolean connect(int porta) {
		String name = "CommandHandler";
		String clientName = "Client";
        Registry registry;
		try {
			//Get the Registry
			registry = LocateRegistry.getRegistry(2020);
		} catch (RemoteException e) {			
			e.printStackTrace();
			return false;
		}
        try {
        	//Get the RemoteCommandHandler reference
			command = (RemoteCommandHandler) registry.lookup(name);
			//Register the client to the server.
			((CallableClient) registry.lookup(clientName)).setClientPort(porta);
		} catch (AccessException e) {
			e.printStackTrace();
			return false;
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	/**Getter
	 * @return the remote command handler
	 */
	public RemoteCommandHandler getCommand() {
		return command;
	}

	

	

}
