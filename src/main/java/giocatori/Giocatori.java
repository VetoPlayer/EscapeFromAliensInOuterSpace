package giocatori;

import java.util.ArrayList;
import java.util.List;

import board.Casella;
/**
 * @author Lollo
 *This class contains the method for the creation of the lists of humans and aliens in relation to the users that partecipate to the game, and the getters of these informations.
 *This is the only class of the package giocatori (except Turno) used by the class Gioco and its purpose is to have this class as the only point of access for every class outside that package
 *
 */
public class Giocatori {
	
	private List<Umano> umani;
	private List<Alieno> alieni;
	private boolean lastUman;
	/**This method creates two arraylist, one for the humans in the game, the other for the aliens in the game
	 * 
	 */
	public Giocatori() {
		umani = new ArrayList<Umano>();
		alieni = new ArrayList<Alieno>();
		lastUman=false;
	}
	/**This method creates the correct number of aliens and humans in relation to the users that will partecipate in the match
	 * @param numeroGiocatori :number of users that will partecipate to the match that's going to start
	 * @param spawnumani :initial sector of human players created
	 * @param spawnalieni :initial sector of alien players created
	 */
		public void creaGiocatori(int numeroGiocatori,Casella spawnumani, Casella spawnalieni){
			int i,j,num;
			//il troncamento da float a int gestisce il caso della creazione di un Alieno in più nel caso di giocatori dispari
			for(i=0,num=1; i<numeroGiocatori/2; i++,num++){
				Umano nuovoUmano= new Umano(spawnumani, num);
				nuovoUmano.umanoCreato();
				umani.add(nuovoUmano);
				}
			for(j=i; j<numeroGiocatori; j++,num++){
				Alieno nuovoAlieno= new Alieno(spawnalieni, num);
				nuovoAlieno.alienoCreato();
				alieni.add(nuovoAlieno);
			}
			return;	
	}
		/**This method returns a list of the players of the match, both humans and aliens
		 * @return a list of the players of the match
		 */
		public List<Giocatore> getPlayers() {
			List<Giocatore> players= new ArrayList<Giocatore>();
			for(Umano e : umani)
				players.add(e);
			for(Alieno e : alieni)
				players.add(e);
			return players;
		}
		/**This method is a getter for the list of the humans in the game
		 * @return a list with the human players
		 */
		public List<Umano> getUmani(){
			return umani;
		}
		/**This method is a getter for the list of the aliens in the game
		 * @return a list with the alien players
		 */
		public List<Alieno> getAlieni(){
			return alieni;
		}
		
		/**This method is a getter for a specific alien with the ID declared in input
		 * @param idGiocatore Id of the alien
		 * @return
		 */
		public Alieno getAlieno(int idGiocatore){
			for(Alieno e : alieni)
				if(e.getIdGiocatore()==idGiocatore)
					return e;
			return null;
		}
		/**This method is a getter for a specific alien with the ID declared in input
		 * @param idGiocatore Id of the human
		 * @return
		 */
		public Umano getUmano(int idGiocatore){
			for(Umano e : umani)
				if(e.getIdGiocatore()==idGiocatore)
					return e;
			return null;
		}
		/**This method is a getter for a specific player (human or alien) with the ID declared in input
		 * @param idGiocatore :Id of the player
		 * @return
		 */
		public Giocatore getGiocatore(int idGiocatore){
			Giocatore selezionato=null;
			for(Alieno e : alieni)
				if(e.getIdGiocatore()==idGiocatore)
					selezionato=e;
			for(Umano e : umani)
				if(e.getIdGiocatore()==idGiocatore)
					selezionato=e;
			return selezionato;
			
		}
		/**Getter
		 * @return the value of the boolean lastUman. It's true if the last human has been killed, so the alien would win the match
		 */
		public boolean isLastUman() {
			return lastUman;
		}
		/**Setter
		 * @param lastUman the value of the boolean lastUman. This method is called with the boolean value "true" when the last human has just been killed, so the alien would win the match
		 */
		public void setLastUman(boolean lastUman) {
			this.lastUman = lastUman;
		}
		
		
		
		
		
		
		
		
		
		
		
		
}
