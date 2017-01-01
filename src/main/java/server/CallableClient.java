package server;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * Remote interface exposed by the Server 
 * to allow a client to register on the server.
 */
public interface CallableClient extends Remote{
	
	/**
	 * Method used by the client to register itself on the server. It is called in the "connect" method on the class RMIInterface on the package "Client"
	 * @param port the port that can be used by RMI to call the client
	 * @throws RemoteException
	 */
	void setClientPort(int port) throws RemoteException;
}
