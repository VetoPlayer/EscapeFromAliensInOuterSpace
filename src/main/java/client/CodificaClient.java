package client;

import controller.StatiGioco;



/**
 * @author Andrea
 * This class contains the methods that transforms the format of the input of the client, captured by the method "readline" in the class Client, in order to let
 * those strings to be understood by the class Command Handler on the server side
 */
public class CodificaClient {
	
	private static final String PLAYERSTRING= "player=";
	private static final String ACTIONSTRING= "&action=";
	private static final String VALUESTRING= "&value=";
	/**
	 * Private constructor, never called
	 */
	private CodificaClient() {
		
	}
	//in base al tipo di comando codifico l'informazione in maniera diversa
	/** Method called by the class Client to form a string readable and understandable by the class Command Handler on the server, for every type of command in every phase of the turn of a player
	 * @param string the string written by the user and captured in the class Client, who calls this method
	 * @param numRoom number of server room that identifies the match that the client is playing, among all other matches
	 * @param idGioc identifier of the player (number from 1 to eight at maximum)
	 * @param statoAttuale current phase of the turn played by the client in exam
	 * @return a string ready to be received by the server through the rmi interface
	 */
	public static String codifica(String string,int numRoom,int idGioc,StatiGioco statoAttuale){
		String risposta="";
		String id=String.valueOf(idGioc);
		String roomNum=String.valueOf(numRoom);
		if(StatiGioco.STARTSTATE.equals(statoAttuale))
			risposta=spostamento(string, id);
		else if(StatiGioco.ASKSTATE.equals(statoAttuale))
			risposta=ask(string, id);
		else if(StatiGioco.OBJECTSTATE.equals(statoAttuale))
			risposta=oggetto(string, id);
		else if(StatiGioco.NOISESTATE.equals(statoAttuale))
			risposta=noise(string, id);
		else if(StatiGioco.LUXSTATE.equals(statoAttuale))
			risposta=lux(string, id);
		else if(StatiGioco.ENDSTATE.equals(statoAttuale) && string=="End")
			risposta=end(string, id);
		else if(StatiGioco.DISCARDSTATE.equals(statoAttuale) && string=="End")
			risposta=discard(string, id);
		else if(StatiGioco.ENDSTATE.equals(statoAttuale) && string!="End")
			risposta="Non è il tuo turno,aspetta";
		String server= "serverRoom=".concat(roomNum);
		risposta=server.concat("&"+risposta);
		return risposta;
	}
	
	//Ritorna l'ordine di spostarsi su una casella
	/** Private method called by "codifica" to handle the string of the client related to the movement during the phase Startstate
	 * @param casella string written by the client and captured with the readline method in the class client, indicating the sector where the player wants to move
	 * @param id id of the player-client
	 * @return a string of the format "player=..idOfThePlayer..&action=spostamento&value=..sectorChosen.., where the terms ..xxx.. are variables
	 */
	private static String spostamento(String casella,String id){
		String string= PLAYERSTRING.concat(id); //occhio, possibile bug
		string= string.concat(ACTIONSTRING).concat("spostamento");
		string= string.concat(VALUESTRING).concat(casella);
		return string;
	}
	//La risposta è o Y (Fase Attacco) o N (Pesca Carta Suono) o scegli tu una convenzione adatta
	/** Private method called by "codifica" to handle the string of the client, containing the symbol Y if the alien-client wants to attack, or the symbol N if he doesn't want to
	 * @param oggetto string containing Y or N
	 * @param id id of the player
	 * @return a string of the format "player=..idOfThePlayer..&action=ask&value=.."Y" or "N".., where the terms ..xxx.. are variables
	 */
	private static String ask(String oggetto, String id){
		String string= new String();
		string= PLAYERSTRING.concat(id);
		string= string.concat(ACTIONSTRING).concat("ask");
		string= string.concat(VALUESTRING).concat(oggetto);
		return string;
	}
	
	/** Private method called by "codifica" to handle the string of the client, containing "End"
	 * @param oggetto string containing "End"
	 * @param id id of the player
	 * @return a string of the format "player=..idOfThePlayer..&action=ask&value=.."End".., where the terms ..xxx.. are variables
	 */
	private static String end(String oggetto, String id){
		String string= new String();
		string= PLAYERSTRING.concat(id);
		string= string.concat(ACTIONSTRING).concat("End");
		string= string.concat(VALUESTRING).concat(oggetto);
		return string;
	}
	
	//può ricevere Y, N, oppure il nome dell'oggetto da attivare, tipo A , T
	/**Private method called by "codifica" to handle the string of the client, containing the symbol Y if the human-client wants to see the objects he has in his hand, or
	 * the symbol N if he doesn't want to, or the symbols A,T, etc.. that stand for the type of object cards the y want to activate
	 * @param oggetto string containing Y or N or A...
	 * @param id id of the player
	 * @return a string of the format "player=..idOfThePlayer..&action=ask&value=.."Y","N","A".., where the terms ..xxx.. are variables
	 */
	private static String oggetto(String oggetto, String id){
		String string= new String();
		string= PLAYERSTRING.concat(id);
		string= string.concat(ACTIONSTRING).concat("oggetto");
		string= string.concat(VALUESTRING).concat(oggetto);
		return string;
	}
	
	/** Private method called by "codifica" to handle the string of the client, containing the sector the client wants to enlight to discover if there are players there
	 * @param oggetto string containing a sector
	 * @param id id of the player
	 * @return a string of the format "player=..idOfThePlayer..&action=ask&value=.."ChosenSector".., where the terms ..xxx.. are variables
	 */
	private static String lux(String value, String id){
		String string= new String();
		string= PLAYERSTRING.concat(id);
		string= string.concat(ACTIONSTRING).concat("luce");
		string= string.concat(VALUESTRING).concat(value);
		return string;
	}
	
	//Riceve in ingresso la casella su cui il giocatore intende far rumore per effetto della carta "RumoreAltroSettore"
	/** Private method called by "codifica" to handle the string of the client, containing the sector the client wants to declare pretending he is really there for the other players
	 * @param oggetto string containing a sector
	 * @param id id of the player
	 * @return a string of the format "player=..idOfThePlayer..&action=ask&value=.."ChosenSector".., where the terms ..xxx.. are variables
	 */
	private static String noise(String value, String id){
		String string= new String();
		string= PLAYERSTRING.concat(id);
		string= string.concat(ACTIONSTRING).concat("rumoreAltroSettore");
		string= string.concat(VALUESTRING).concat(value);
		return string;
	}
	
	/** Private method called by "codifica" to handle the string of the client, containing "End"
	 * @param oggetto string containing "End"
	 * @param id id of the player
	 * @return a string of the format "player=..idOfThePlayer..&action=ask&value=.."discard".., where the terms ..xxx.. are variables
	 */
	private static String discard(String oggetto, String id){
		String string= new String();
		string= PLAYERSTRING.concat(id);
		string= string.concat(ACTIONSTRING).concat("discard");
		string= string.concat(VALUESTRING).concat(oggetto);
		return string;
	}
	

	
	
	
	
}
