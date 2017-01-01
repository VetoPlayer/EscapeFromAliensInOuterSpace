package giocatori;

import board.Casella;
import carte.CartaEquipaggiamento;
import carte.Oggetto;
/** This class extends Giocatore. It inherits the attributes of that class, adding a boolean that controls if the alien has already eaten a human or not, the number of
 * possible steps in a turn in both cases and the object card just discarded because the limit of maximum object cards in the hand has been reached
 * @author Lollo
 *
 */
public class Alieno extends Giocatore {
	public static final int PASSI_ALIENO=2;
	public static final int PASSI_SUPERALIENO=3;
	private boolean superAlieno;
	private CartaEquipaggiamento discardedCard;
	
	/**Constructor of an alien. It initializes the ID of the player, his sector (at the start of the game), his number of possible steps (two, according to the rules), and
	 * it initializes the iterator on the ArrayList<Statigioco> filling it with all the phases of alien's turn in the correct order, and finally set the boolean "superAlieno" to false
	 * @param spawnAlieni :initial sector of the alien
	 * @param id :id of the player
	 */
	public Alieno(Casella spawnAlieni, int id) {
		super(id,spawnAlieni,PASSI_ALIENO,new TurnoAlieno());
		this.setSuperAlieno(false);
	}
	/**Getter
	 * @return it returns true if the alien has already eaten a human player
	 */
	public boolean isSuperAlieno() {
		return superAlieno;
	}
	/**Setter
	 * @param superAlieno if it's true, the method sets the number of possible moves of the alien to the value "PASSI_SUPERALIENO (3 steps according to the rules)". If it's false, it sets the value PASSI_ALIENO (2 according to the rules of the game)
	 */
	public void setSuperAlieno(boolean superAlieno) {
		this.superAlieno = superAlieno;
		if(superAlieno)
			setNumPassi(PASSI_SUPERALIENO);
		else
			setNumPassi(PASSI_ALIENO);
	}
	
	
	/**This method calls the observer sending a string "nuovoAlieno" when a new alien player has been created
	 * 
	 */
	public void alienoCreato(){
		setChanged();
		this.notifyObservers("nuovoAlieno");
	}
	
	
	//ho messo la notify in setstato per notificare ad un giocatore che muore
	/**This method, unlike attacco(Umano,Giocatori) who implements the human attack thanks to the object card "attacco", implements the alien attack.
	 * It is called by attacco(int) and notifies the attack to the observers. It modifies the state of a player from VIVO to MORTO, it calls a method that sets the possible moves of an alien in a turn when he defeats the first human,
	 * it manages the case in which an human use the defense card and it takes the object cards of a dead player to the deck
	 * @param giocatori an instance of class Giocatori, which contains all the players of the match
	 */
	public void attacco(Giocatori giocatori){
		setChanged();
		notifyObservers("Attacco");
		for(Umano u: giocatori.getUmani()){
			if(u.getPosizioneAttuale()==this.getPosizioneAttuale())
				if(!u.hasOggetto(Oggetto.DIFESA)){
					u.setStato(StatoGiocatore.MORTO);
					u.rimuoviGiocatoreDaBoard();
					u.scartacarte();
					this.setSuperAlieno(true);
					boolean last=true;
					for(Umano um: giocatori.getUmani()){ //Se l'umano che ho ucciso era l'ultimo e quindi non ce ne sono più di vivi
						if(um.getStato() == StatoGiocatore.VIVO)
							last=false;
					}
					if(last)
						giocatori.setLastUman(true);
				}
				else{
					u.difesa();
				}					
		}
		for(Alieno a: giocatori.getAlieni())
			if(a.getPosizioneAttuale()==this.getPosizioneAttuale() && !a.equals(this)){
				a.setStato(StatoGiocatore.MORTO);
				a.rimuoviGiocatoreDaBoard();
				a.scartacarte();
			}
			
		return;
	}
		

	/**This method discards the fourth object card of an alien
	 * @param s string that represents an object card type in the alien's hand
	 */
	public void scartaCartaEquipaggiamento(String s){
		boolean scartata=false;
		int i;
		for(i=equipaggiamento.size()-1;i>=0 && !scartata;i--){
			if(("A".equals(s) && equipaggiamento.get(i).getOggetto().equals(Oggetto.ATTACCO)) || ("T".equals(s) && equipaggiamento.get(i).getOggetto().equals(Oggetto.TELETRASPORTO)) ||
				("R".equals(s) && equipaggiamento.get(i).getOggetto().equals(Oggetto.ADRENALINA)) || ("S".equals(s) && equipaggiamento.get(i).getOggetto().equals(Oggetto.SEDATIVO))
				|| ("L".equals(s)&& equipaggiamento.get(i).getOggetto().equals(Oggetto.LUCE)) || ("D".equals(s) && equipaggiamento.get(i).getOggetto().equals(Oggetto.DIFESA))){
				discardedCard= equipaggiamento.get(i);
				scartata=true;
				equipaggiamento.remove(i);
				setChanged();
				this.notifyObservers("OggettoScartato");
			}
		}
		return;
	}
	
	/**Getter
	 * @return the card discarded by the alien as it is the fourth object card in the hand of the player
	 */
	public CartaEquipaggiamento getDiscardedCard() {
		return discardedCard;
	}
	
}
