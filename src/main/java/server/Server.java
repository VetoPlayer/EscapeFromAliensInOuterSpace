package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;

/**
 * 
 * Executable class which creates the Socket server and the RMI registry. It contains a list of the server rooms created
 */

public class Server {
	public static final int SERVERROOMSNUMBER=0;
	private static List<ServerRoom> stanze;
	private static Server instance;
	

	/**Implementation of the Singleton pattern for the creation of the server
	 * @return an instance of Server if it hasn't been created yet
	 */
	public static Server getServer(){
		if(instance==null)
			instance=new Server();
		return instance;
	}
	//Aggiunge il giocatore alla prima stanza libera, ne crea una se opportuno e ritorna il numero del giocatore
	/**This method adds a client to a not-yet complete server room. If they are all complete, a new empty server room is created and the client (RemoteNotifier) is added to this one
	 * @param notifier
	 * @return
	 * @throws RemoteException
	 */
	public int addNotifier(RemoteNotifier notifier) throws RemoteException{
		int idGioc;
		//Se l'ultima stanza non è ancora iniziata
		if(!stanze.get(stanze.size()-1).isStarted())
			idGioc=stanze.get(stanze.size()-1).addPlayer(notifier);
		else{
			ServerRoom nuovaStanza=addNewRoom();
			idGioc=nuovaStanza.addPlayer(notifier);
		}
		return idGioc;
	}
	//costruisce una nuova room tenendo conto del loro numero
	/**Creates a new server room and adds it to the list of server rooms (an attribute of the Server class) when the others are all completed and a client wants to join a game
	 * @return a new server room
	 */
	private ServerRoom addNewRoom(){
		ServerRoom nuovaStanza= new ServerRoom();
		stanze.add(nuovaStanza);
		return nuovaStanza;
	}
	
	/**
	 * Takes the last server room of the list (the only one who could be not activated yet) and let it start if it hasn't started yet (puts the server room boolean "started" to true) 
	 */
	public void startServerRoom(){
		//prendo l'ultima stanza del server( che è l'unica che può essere avviata) e se non è già iniziata la avvio
		if(!stanze.get(stanze.size()-1).isStarted())
			stanze.get(stanze.size()-1).start();
	}
	
	//prende il controller della ServerRoom di interesse e lo restituisce
	/** Getter of the controller of the server room, which is identified by the integer numRoom
	 * @param numRoom number that identifies the server room
	 * @return the controller of the server room
	 */
	public Controller getServerRoomController(int numRoom){
		return stanze.get(numRoom).getController();
	}
	//prendo l'ultima stanza del server: è quella in cui appartiene il giocatore, le altre prima sono sicuramente già partite
	
	/**Getter
	 * @return the number of the last server room
	 */
	public int getNumServerRoom(){
		return stanze.size()-1;
	}
	
	
	/**Main of the server. Initialize the first server room and adds it to the attribute List<ServerRoom>. Publish the remote objects CallableClient and RemoteCommandHandler on the rmi registry
	 * and unbind the command handler when the server receives a string containing "Q" from the readLine() method
	 * @param args
	 * @throws IOException
	 * @throws NotBoundException
	 */
	public static void main(String[] args) throws IOException, NotBoundException{
		Registry registry = null;
		String name = "CommandHandler";
		String clientName = "Client"; 
		
		
		//Inizializzo il server:
		stanze= new ArrayList<ServerRoom>();
		ServerRoom firstRoom= new ServerRoom();
		stanze.add(firstRoom);
		
		
		//Starting RMI registry		       
        try {
            //Questa condivisione di registro dovrebbe avvenire in un secondo momento in base a se il client decide di creare una nuova stanza oppure di sfruttarne una già esistente. No, non è nei requisiti!

        	RemoteCommandHandler command = firstRoom.getCommandHandler();
            RemoteCommandHandler stub = (RemoteCommandHandler) UnicastRemoteObject.exportObject(command, 0);           
            
            //Crea il client sul registro 2020
            CallableClient client = new RemoteCallableClient();
            CallableClient clientStub = (CallableClient) UnicastRemoteObject.exportObject(client, 4040);
            //Creating the registry
            registry = LocateRegistry.createRegistry(2020);            
            //Binding the remote objects
            registry.bind(name, stub);
            registry.bind(clientName, clientStub);
                        
        } catch (Exception e) {
            System.err.println("RMI exception:");
            e.printStackTrace();
            registry = null;
        }
        boolean finish=false;
        
        while(!finish){
			String read = readLine("Press S to get the server status;\nPress Q to exit\n");
			if("Q".equals(read)){
				finish = true;
			}
			if(registry != null)
				registry.unbind(name);
        }
        //System.exit(0); errore sonar
	}
	
	/**
	 * Helper method to read a line from the console when the 
	 * program is started in Eclipse
	 * @param format the format string to be read
	 * @param args arguments for the format
	 * @return the read string
	 * @throws IOException if the program cannot access the stdin
	 */
	private static String readLine(String format, Object... args) throws IOException {
	    if (System.console() != null) {
	        return System.console().readLine(format, args);
	    }
	    System.out.print(String.format(format, args));
	    
	    BufferedReader br = null;
	    InputStreamReader isr = null;
	    String read = null;
	    
	    isr = new InputStreamReader(System.in);
	    br = new BufferedReader(isr);
	    read = br.readLine();
	    
	    return read;
	}
	
	
}
