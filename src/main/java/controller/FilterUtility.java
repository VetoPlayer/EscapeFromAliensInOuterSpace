package controller;

import java.util.List;

import board.Casella;
import carte.CartaEquipaggiamento;
import carte.Oggetto;

/**This class contains the methods called by the class Filter that transforms the format of the various objects in strings, to let them be readable and understandable by the user from the command line interface
 * @author Lollo
 *
 */
public class FilterUtility {
	
	/**Constructor of Filter Utility, private because this is an utility class
	 * 
	 */
	private FilterUtility() {
	}
	
	/**Converts an object card to a string readable by the user
	 * @param equipaggiamento object card that has to be converted
	 * @return a string (object card attacco becomes "A", object card teletrasporto becomes "T"...)
	 */
	public static String convertiEquip(List<CartaEquipaggiamento> equipaggiamento){
		String oggetti="";
		for(CartaEquipaggiamento e : equipaggiamento){
			if(e.getOggetto().equals(Oggetto.ATTACCO))
				oggetti= oggetti + "A";
			if(e.getOggetto().equals(Oggetto.TELETRASPORTO))
				oggetti= oggetti + "T";
			if(e.getOggetto().equals(Oggetto.ADRENALINA))
				oggetti= oggetti + "R";
			if(e.getOggetto().equals(Oggetto.SEDATIVO))
				oggetti= oggetti + "S";
			if(e.getOggetto().equals(Oggetto.LUCE))
				oggetti= oggetti + "L";
			if(e.getOggetto().equals(Oggetto.DIFESA))
				oggetti= oggetti + "D";
		}
		if("".equals(oggetti))
			oggetti="Non hai alcun oggetto";
		return oggetti;
	}
		
	/**For every type of object card, it shows a different message
	 * @param o object card activated
	 * @return string containing a message for the user where it's written that he has activated successfully the object card, and the type of object card activated.
	 * If the object card luce is activated, the message contains also an answer regarding which sector the player wants to illuminate
	 */
	public static String notifyOggetto(CartaEquipaggiamento o){
			String risposta="";	
			if(o.getOggetto().equals(Oggetto.ADRENALINA))
				risposta="Hai attivato con successo Adrenalina: puoi muoverti di 2 settori in questo turno";
			if(o.getOggetto().equals(Oggetto.ATTACCO))
				risposta="Hai attivato con successo attacco";
			if(o.getOggetto().equals(Oggetto.LUCE))
				risposta="Stai attivando l'oggetto luce: quale settore vuoi illuminare?";
			if(o.getOggetto().equals(Oggetto.SEDATIVO))
				risposta="Hai attivato con successo sedativo: in questo turno non peschi carte suono";
			if(o.getOggetto().equals(Oggetto.TELETRASPORTO))
				risposta="Hai attivato con successo Teletrasporto";
			if(o.getOggetto().equals(Oggetto.DIFESA))
				risposta="Hai attivato con successo Difesa: ti sei salvato da morte certa";
		return risposta;
	}
	
	/**Show an object card as a string of this type: "Adrenalina", "Attacco", etc.
	 * @param o object card
	 * @return a string
	 */
	public static String decodeOggetto(CartaEquipaggiamento o){
		String risposta = "";
		if(o.getOggetto() == Oggetto.ADRENALINA)
			risposta="Adrenalina";
		if(o.getOggetto() == Oggetto.ATTACCO)
			risposta="Attacco";
		if(o.getOggetto() == Oggetto.LUCE)
			risposta="Luce";
		if(o.getOggetto() == Oggetto.SEDATIVO)
			risposta="Sedativo";
		if(o.getOggetto() == Oggetto.TELETRASPORTO)
			risposta="Teletrasporto";
		if(o.getOggetto() == Oggetto.DIFESA)
			risposta="Difesa";
	return risposta;
	}
		
	/**Shows a sector object as a string readable by the user. Examples of strings are "[A,06]", "[L,12]"...
	 * @param casella sector to be converted
	 * @return a string representing the sector as it's written on the map of the game
	 */
	public static String codificaCasella(Casella casella){
		String result="";
		result= result.concat("[");
		result= result.concat(aggiungiColonna(casella.getX()));
		result= result.concat(",");
		result= result.concat(aggiungiRiga(casella.getY()));
		result= result.concat("]");
		return result;
	}
	/**Private method called by codificaCasella
	 * @param in row of the sector
	 * @return the correspondent row written as in the map of the game
	 */
	private static String aggiungiRiga(int in){
		String result="";
		if(in < 10)
			result=result.concat("0"+in);
		else
			result=result.concat(""+in);
		return result;
	}
	
	/**Private method called by codificaCasella
	 * @param in column of the sector
	 * @return the correspondent column written as in the map of the game
	 */
	private static String aggiungiColonna(int in){
		String colonna="";
		int result = in + 'A' -1;
		char car;
		car= (char)result;
		colonna= colonna.concat(""+car+"");
		return colonna;
		
	}
}
