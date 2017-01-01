package giocatori;

import board.Casella;
import board.Mappa;
import carte.CartaEquipaggiamento;
import carte.Oggetto;
/**This class extends Giocatore. It has got the inherited attributes of that class, plus two constants for the control of "adrenalina"'s object card effect and the methods
 * regarding the activation of the object cards
 * @author Lollo
 *
 */
public class Umano extends Giocatore {
	public static final int PASSI_UMANO=1;
	public static final int PASSIADRENALINA=2;
	
	/**Constructor of an instance of Umano
	 * @param spawnUmani :initial sector
	 * @param id :id of the player created
	 */
	public Umano(Casella spawnUmani, int id) {
		super(id,spawnUmani,PASSI_UMANO,new TurnoUmano());
	}
	
	/**This method sends to the observer the string "NuovoUmano" when a new human player is created
	 * 
	 */
	public void umanoCreato(){
		setChanged();
		this.notifyObservers("nuovoUmano");
	}
	/**This method verifies if the player has an object card of the type declared in input
	 * @param oggetto a type of object card described in the enum class Oggetto
	 * @return a boolean; true if the card of that type is in the hand of the player, false in the other situation.
	 */
	public boolean hasOggetto(Oggetto oggetto){
		boolean result=false;
		for(CartaEquipaggiamento e : equipaggiamento)
			if(e.getOggetto().equals(oggetto))
				result=true;
		return result;
	}


	
	/**This method is activated without asking to the client unlike the other object cards, and set the object card difesa as activated (setAttivata(true)).
	 * Then it sends the string "Difesa" to the observer in the class Filter
	 */
	public void difesa(){
		for(CartaEquipaggiamento equip: equipaggiamento)
			if(equip.getOggetto().equals(Oggetto.DIFESA)){
				equip.setAttivata(true);
				setChanged();
				notifyObservers("Difesa");
			}
	}
	
	
	
	
	
	// Manca difesa: è una carta che si attiva automaticamente e non va quindi attivata manualmente
	/**This method set the object card, of the type indicated by the string s, as activated; it calls the methods of the class CartaEquipaggiamento
	 * and notifies to the observer in Filter class if the object has been activated or not
	 * @param s string that says which type of object has to be activated
	 * @param map board of the game
	 * @param giocatori all the players of a game
	 */
	public void usaOggetto(String s, Mappa map, Giocatori giocatori){
		boolean oggettoUsato=false;
		boolean difesa=false;
		for(CartaEquipaggiamento e : equipaggiamento){
			if("A".equals(s) && e.getOggetto().equals(Oggetto.ATTACCO)){
				e.attacco(this, giocatori);
				oggettoUsato=true;
			}
			else if("T".equals(s) && e.getOggetto().equals(Oggetto.TELETRASPORTO)){
				e.teletrasporto(this, map.getSpawnumani());
				oggettoUsato=true;
			}
			else if("R".equals(s) && e.getOggetto().equals(Oggetto.ADRENALINA)){
				e.adrenalina(this);
				oggettoUsato=true;
			}
			else if("S".equals(s)&& e.getOggetto().equals(Oggetto.SEDATIVO)){
				e.sedativo(this);
				oggettoUsato=true;
			}
			else if("L".equals(s)&& e.getOggetto().equals(Oggetto.LUCE)){
				oggettoUsato=true;
			}//non si può utilizzare la difesa!
			else if("D".equals(s) && e.getOggetto().equals(Oggetto.DIFESA)){
				difesa=true;
			}
		}
		setChanged();
		if(oggettoUsato){
			notifyObservers("OggettoAttivato");
		}
		else if(difesa){			
			notifyObservers("DifesaNonAttivabile");
		}
		else{
			notifyObservers("OggettoNonAttivato");
		}
	}


	
}

	