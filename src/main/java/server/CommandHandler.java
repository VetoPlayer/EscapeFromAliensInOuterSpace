package server;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import controller.Controller;

/**
 * @author Andrea
 * This class implements RemoteCommandHandler and his only method handleCommand
 */
public class CommandHandler implements RemoteCommandHandler{

	@Override
	public void handleCommand(String input) throws RemoteException {
		String[] splitted = input.split("&");
		Map<String, String> param = new HashMap<String, String>();
		for(String s : splitted)
			param.put(s.split("=")[0],s.split("=")[1]);
		//Prendo i valori dalla mappa e agisco di conseguenza
		String serverRoom= param.get("serverRoom");// ora in serverRoom ho il numero della Room 
		int numRoom= Integer.parseInt(serverRoom); //trasformo la stringa in un intero
		String player = param.get("player");// ora in player ho il numero del player come stringa
		int idGiocatore= Integer.parseInt(player); //trasformo la stringa in un intero
		String action = param.get("action");// ora in action ho la stringa corrispondente all'azione
		//prendo il controller corrispondente alla serverRoom giusta
		Controller controller=Server.getServer().getServerRoomController(numRoom);
		String value = param.get("value");// ora in value ho la stringa relativa al corrispondente al valore
		if ("spostamento".equals(action)) {
			controller.spostamento(idGiocatore, value);
		}
		if ("oggetto".equals(action)){
			if("Y".equals(value) || "N".equals(value))
				controller.mostraOggetto(idGiocatore, value);
			else
				controller.usaOggetto(idGiocatore, value);
		}
		if ("rumoreAltroSettore".equals(action)){
			controller.rumoreAltroSettore(idGiocatore, value);
		}
		if ("ask".equals(action)){
			controller.ask(idGiocatore, value);
		}
		if ("luce".equals(action)) {
			controller.luce(idGiocatore, value);
		}
		if ("discard".equals(action)) {
			controller.discard(idGiocatore, value);
		}
		
		if ("End".equals(action)) {
			controller.end();
		}
		return;
	}
	
	
	
	
	
}
