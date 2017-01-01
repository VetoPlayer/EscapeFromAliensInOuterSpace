package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import server.RemoteNotifier;
import controller.StatiGioco;

/**
 * 
 * Class which is used to instantiate the client.
 * This client creates a RMI registry to be called by the server.
 * 
 */
public class Client {
	boolean connesso;
	private StatiGioco statoAttuale;
	private int numRoom;//Numero ServerRoom
	private int id;//Numero giocatore
	private int porta;
	private RMIInterface ni;
	public static final int MINVALUE=1027;
	public static final int MAXVALUE=6000;
	private static final int INPUT_LENGHT=3;
	/**
	 * Constructor
	 */
	public Client(){	
		
	}
			
	/** Called by the main. It creates a Notifier from a Client, it creates an rmi registry accessible on a random generated port and finally puts the notifier on the registry.
	 * 
	 * @throws IOException
	 */
	public void connect() throws IOException{
		//Starting RMI registry  
        try {
        	//Crea un notifier e lo mette a disposizione su 3030--> potrebbe essere un qualsiasi numero casuale
            //Binding the notifier
        	Registry registry = null;
    		String notName = "Notifier";
            RemoteNotifier notifier = new Notifier(this);
            RemoteNotifier stub = (RemoteNotifier) UnicastRemoteObject.exportObject(notifier, 0);            
            Random random = new Random();
            //Salvo le informazioni su una porta compresa tra 1027 e 7024 generata in maniera casuale
            porta=random.nextInt(MINVALUE)+MAXVALUE;
            registry = LocateRegistry.createRegistry(porta);            
            registry.bind(notName, stub);
            
            
        } catch (Exception e) {
            System.err.println("RMI exception:");
            e.printStackTrace();
        }
		
		//Mi collego con RMI
		ni= new RMIInterface();
		ni.connect(porta);
				
		//Loop to query/stop the client
		boolean end = false;
		String read;
		System.out.println("Client avviato correttamente: se vuoi disconnetterti digita exit");
		while(!end){
			//Ricevo input in ingresso
			read = readLine("");
			//Guardo se non vuole uscire
			if("exit".equals(read))
				end = true;
			//Controllo che l'input sia formalmente corretto e se lo è codifico l'input in base al mio stato attuale
			if(validInput(read)){
				String command=CodificaClient.codifica(read,numRoom,id,statoAttuale);
				//invio l'ordine al server
				ni.getCommand().handleCommand(command);
			}
			else{
				System.out.println("Input non valido");
			}
		}
		System.exit(0);
	}
	
	/**
	 * Helper method to read a line from the console when the 
	 * program is started in Eclipse
	 * @param format the format string to be read
	 * @param args arguments for the format
	 * @return the read string
	 * @throws IOException if the program cannot access the stdin
	 */
	private static String readLine(String format, Object... args) throws IOException{
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


	/**Getter
	 * @return the current phase of his turn, (StatiGioco)
	 */
	public StatiGioco getStatoAttuale() {
		return statoAttuale;
	}




	
	/**Setter of StatiGioco. It also ask to the player if he wants see his object cards, if he wants to attack and where he wants to move, and show him a confirm of his actions in the various phases of the turn
	 * @param statoAttuale An instance of the enum StatiGioco, the current phase of the turn of the player
	 * @throws RemoteException
	 */
	public void setStatoAttuale(StatiGioco statoAttuale) throws RemoteException {
		this.statoAttuale = statoAttuale;
		if(statoAttuale==StatiGioco.STARTSTATE){
			System.out.println("STARTSTATE: In quale settore desideri spostarti?");
		}
		if(statoAttuale==StatiGioco.OBJECTSTATE){
			System.out.println("OBJECTSTATE:Vuoi vedere che oggetti possiedi? Y N");
		}
		if(statoAttuale==StatiGioco.ASKSTATE){
			System.out.println("ASKSTATE: Intendi attaccare? Y N");
		}
		if(statoAttuale==StatiGioco.ATTACKSTATE){
			System.out.println("ATTACKSTATE:Hai attaccato");
		}
		if(statoAttuale==StatiGioco.DRAWSTATE){
			System.out.println("DRAWSTATE:");
		}
		if(statoAttuale==StatiGioco.LUXSTATE){
			System.out.println("LUXSTATE");
		}
		if(statoAttuale==StatiGioco.DISCARDSTATE){
			System.out.println("DISCARDSTATE");
		}
		if(statoAttuale==StatiGioco.NOISESTATE){
			System.out.println("NOISESTATE");
		}//Se ho finito il mio turno di gioco, lo dico al CommandHandler: penserà lui a chiamare il prossimo giocatore di turno
		if(statoAttuale.equals(StatiGioco.ENDSTATE)){
			System.out.println("Hai finito il turno");
			//invio un messaggio generato automaticamente in cui comunico la fine del mio turno
			String command=CodificaClient.codifica("End",numRoom, id,statoAttuale);
			//invio l'ordine al server
			ni.getCommand().handleCommand(command);
		}
				
	}

	/**
	 * Initialize the state of the player when he is created, putting it on ENDSTATE
	 */
	public void setStatoGCreato(){
		this.statoAttuale=StatiGioco.ENDSTATE;
	}

	/**Getter
	 * @return Id of the player
	 */
	public int getId() {
		return id;
	}

	private boolean validInput(String value){
		boolean validInput=false;
		if(StatiGioco.STARTSTATE.equals(statoAttuale) || StatiGioco.LUXSTATE.equals(statoAttuale) || StatiGioco.NOISESTATE.equals(statoAttuale)){
			if( value.length()==INPUT_LENGHT && Character.isUpperCase(value.charAt(0)) && Character.isDigit(value.charAt(1)) && Character.isDigit(value.charAt(2))
					&& !"X".equals(value) && !"Y".equals(value) && !"Z".equals(value))
				validInput=true;
		}
		else if(StatiGioco.ASKSTATE.equals(statoAttuale)){
			if("Y".equals(value) || "N".equals(value))
				validInput=true;
		}
		else if(StatiGioco.OBJECTSTATE.equals(statoAttuale) || StatiGioco.DISCARDSTATE.equals(statoAttuale) ){
			if("Y".equals(value) || "N".equals(value) ||
			"A".equals(value)  || "S".equals(value) || "R".equals(value)  || "D".equals(value) || "T".equals(value) || "L".equals(value))
				validInput=true;
		}
		return validInput;
	}

	/**Setter
	 * @param id Id of the player
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**Main of the client: it creates a client and calls the method connect
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		Client client= new Client();
		client.connect();
	}



	/**Getter
	 * @return the number of the server room where the client plays
	 */
	public int getNumRoom() {
		return numRoom;
	}



	/**Setter
	 * @param numRoom the number of the server room where the client plays
	 */
	public void setNumRoom(int numRoom) {
		this.numRoom = numRoom;
	}
	
	
}
