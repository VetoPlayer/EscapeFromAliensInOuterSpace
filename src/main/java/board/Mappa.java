package board;

import giocatori.Alieno;
import giocatori.Giocatore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Lollo
 * It's the class that represents the Map of the game. It implements the creational pattern Singleton.
 * The data structure used to implement the hexagonal map is a matrix of sectors (Casella [][]) and the movement of the player is described
 * in the method spostamento, which calls the method "raggiungibile". 
 *
 */
public class Mappa {
	private static Mappa mappa;
	private Casella[][] field;
	private int dimr;//number of rows
	private int dimc;//number of columns
	public static final int DIMR=15;
	public static final int DIMC=24;
	
	
	/**
	 * Private constructor called by the Singleton. It reads a file where there are characters representing the type of
	 * sectors ordinated following the map of the game called Galilei
	 * It calls the method nuovaCasella that creates the sector of the right type in the right square of the matrix
	 */
	private Mappa() {
		this.setDimr(DIMR);
		this.setDimc(DIMC);
		this.field= new Casella[DIMR][DIMC];
		File file = new File("mappagalilei.txt");
		try {
	        Scanner sc = new Scanner(file);
	        	String in;
	             for(int i=0; i<DIMR && sc.hasNextLine() ;i++)
	            	for(int j=0; j<DIMC && sc.hasNextLine() ; j++){
	            		in = sc.next();
	            		nuovaCasella(in, i, j);
	               	}
	        sc.close();
	    } 
	    catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
		
	}

	/**Singleton design pattern: it creates and returns an instance of Map only if it has not been created before
	 * @return mappa
	 */
	public static Mappa getMappa(){
		if(mappa==null)
			mappa=new Mappa();
		return mappa;
	}
	
	/**
	 * @param in: character read in the file by the constructor method
	 * @param i: row of the matrix where the sector of the type denoted by the string "in" has to be created
	 * @param j: column of the matrix where the sector of the type denoted by the string "in" has to be created
	 */
	private void nuovaCasella(String in,int i,int j){
		if("V".equals(in))
			this.field[i][j] = new CasellaVuota(i,j);
		if("S".equals(in))
			this.field[i][j] = new CasellaSicura(i,j);
		if("N".equals(in))
			this.field[i][j] = new CasellaPericolosa(i,j);
		if("U".equals(in))
			this.field[i][j] = new CasellaUmani(i,j);
		if("A".equals(in))
			this.field[i][j] = new CasellaAlieni(i,j);
		if("F".equals(in))
			this.field[i][j] = new CasellaScialuppa(i,j);
	}
	
	

	
	/**This recursive method verify if a sector (Casella destinazione) is reachable from another in which the player is at the start of the turn
	 * (Casella Partenza), using a certain number of moves ("passi").
	 * @param partenza :sector in which the player stays before doing the movement in his turn
	 * @param attuale :while "partenza" remains constant, "attuale" changes at every recursive call. At the first call of raggiungibile
	 * "attuale" and "partenza" are always the same sector
	 * @param destinazione :the sector that has to be reached. This attribute is constant, like "partenza", in every recursive call
	 * @param passi : a primitive integer that decreases at every recursive call and, when it becomes zero, the method cannot be called recursivibly anymore
	 * If an human calls the method "spostamento", which calls this method, the originary number of passi is 1. If an alien who has already eaten a human calls it,
	 * passi will be 3 at the first call, and 2 if the alien hasn't eaten a human yet.
	 * @return This method returns true if at least one sector "attuale", reached from "partenza" by recursive calls, equals the destination sector
	 */
	public boolean raggiungibile(Casella partenza, Casella attuale ,Casella destinazione, int passi){
		if( attuale.equals(destinazione) && !partenza.equals(attuale))
			return true;
		else
		{
			boolean result=false;
			//ALTO(Valido sia se sono in una colonna dispari o pari)
			if(lecita(attuale,-1, 0,passi))
				result= result || this.raggiungibile(partenza,this.field[attuale.getY()-1][attuale.getX()], destinazione, passi - 1);
			//ALTO SX
			if(attuale.dispari()){
				if(lecita(attuale,-1, -1,passi))
					result= result || this.raggiungibile(partenza,this.field[attuale.getY()-1][attuale.getX()-1], destinazione, passi - 1);
			}
			else{//PARI ALTO SX
				if(lecita(attuale,0, -1,passi))
					result= result || this.raggiungibile(partenza,this.field[attuale.getY()][attuale.getX()-1], destinazione, passi - 1);
			}
			//ALTO DX
			if(attuale.dispari()){
				if(lecita(attuale,-1, +1,passi))
					result= result || this.raggiungibile(partenza,this.field[attuale.getY()-1][attuale.getX()+1], destinazione, passi - 1);
			}else{//PARI ALTO DX
				if(lecita(attuale,0, +1,passi))
					result= result || this.raggiungibile(partenza,this.field[attuale.getY()][attuale.getX()+1], destinazione, passi - 1);
			}
			//BASSO(Valido sia se sono in una colonna dispari o pari)
			if(lecita(attuale,+1, 0,passi))
				result= result || this.raggiungibile(partenza,this.field[attuale.getY()+1][attuale.getX()], destinazione, passi - 1);
			//BASSO SX
			if(attuale.dispari()){
				if(lecita(attuale, 0, -1,passi))
					result= result || this.raggiungibile(partenza,this.field[attuale.getY()][attuale.getX()-1], destinazione, passi - 1);
			}else{//PARI BASSO SX
				if(lecita(attuale,+1, -1,passi))
					result= result || this.raggiungibile(partenza,this.field[attuale.getY()+1][attuale.getX()-1], destinazione, passi - 1);
			}//BASSO DX
			if(attuale.dispari()){
				if(lecita(attuale,0, +1,passi))
					result= result || this.raggiungibile(partenza,this.field[attuale.getY()][attuale.getX()+1], destinazione, passi - 1);
			}else{//PARI BASSO DX
				if(lecita(attuale,+1, +1,passi))
					result= result || this.raggiungibile(partenza,this.field[attuale.getY()+1][attuale.getX()+1], destinazione, passi - 1);
			}
			return result;
		}
	}


	/**Getter
	 * @return The maximum number of the row in the map
	 */
	public int getDimr() {
		return dimr;
	}

	/**Setter
	 * @param dimr The maximum number of the row in the map
	 */
	public void setDimr(int dimr) {
		this.dimr = dimr;
	}

	/**Getter
	 * @return The maximum number of the column in the map
	 */
	public int getDimc() {
		return dimc;
	}

	/**Setter
	 * @param dimc The maximum number of the row in the map
	 */
	public void setDimc(int dimc) {
		this.dimc = dimc;
	}
	

	/**Getter for a sector in the map with the coordinates written in the two input parameters
	 * @param riga row
	 * @param colonna column
	 * @return The sector with (riga,colonna) as row and columns
	 */
	public Casella getCasella(int riga,int colonna){
		return this.field[riga][colonna];
	}
	
		/**Method called by the method "raggiungibile"
		 * @param giocatore
		 * @param destinazione
		 * @return result, which is a boolean: it's true if an alien player ("giocatore") attempts to move to an Escape Sector ("destinazione")
		 */
		private boolean alienoscialuppa(Giocatore giocatore,Casella destinazione){
			boolean result=false;
			if(giocatore instanceof Alieno && destinazione instanceof CasellaScialuppa)
				result=true;
			else
				result=false;
			return result;
		}
		
		/**
		 * @return the method returns the sector where aliens are placed at the start of the game
		 */
		public Casella getSpawnalieni(){
			for(int i=0; i<DIMR ;i++)
		        for(int j=0; j<DIMC ; j++)
		        	if(this.field[i][j] instanceof CasellaAlieni)
		        		return this.field[i][j];
			return null;
		}
		/**
		 * @return the method returns the sector where humans are placed at the start of the game
		 */
		public Casella getSpawnumani(){
			for(int i=0; i<DIMR ;i++)
		       	for(int j=0; j<DIMC ; j++)
		       		if(this.field[i][j] instanceof CasellaUmani)
		       			return this.field[i][j];
			return null;
		}
	
	
	
	/**The method controls if the sector where the player could go(as a result of the method "raggiungibile") exists and is not out of the map
	 * @param i :row of the matrix
	 * @param j :column of the matrix
	 * @return the method returns true to the method "lecita" if the player attempts to move outside the map
	 */
	private boolean indicevalido(int i, int j){
		boolean result;
		if(i >= 0 && i<this.getDimr() && j>= 0 && j < this.getDimc() )
			result=true;
		else
			result=false;
		return result;
	}
	
	/**Private method called by "raggiungibile" in order to control if the sectors around the sector CasellaAttuale are crossable.
	 * During every cicle of "raggiungibile", the method "lecita" is called six times, for every direction around the hexagonal sector,
	 * in order to control the crossability of all the sectors around the one considered at the moment (CasellaAttuale)
	 * @param attuale : sector from where "raggiungibile" wants to control if the sectors around it are crossable
	 * @param i : an int which can assume the value 1, 0 or -1. It indicates the value of the shift in the row from "Casella Attuale"
	 * @param j : an int which can assume the value 1, 0 or -1. It indicates the value of the shift in the column from "Casella Attuale"
	 * @param passi :number of times left to call again the method "raggiungibile" recursively. If it's equal to 0, the method returns false immediately
	 * @return the method returns true if the sector considered near casellaAttuale are crossable
	 */
	private boolean lecita(Casella attuale, int i, int j, int passi){
		boolean result=false;
		if(indicevalido(attuale.getY()+i , attuale.getX()+j) && passi > 0 && this.field[attuale.getY()+i][attuale.getX()+j].isCrossable())
			result=true;
		return result;
	}
	
	/**This method, with all his private sub-methods, implements the movement of the player on the bidimensional array representation of the hexagonal map viewed by the player
	 * @param giocatore the player who has just chosen where to move in his turn
	 * @param coordinate a string containing the next sector where the player wants to move
	 * @return this method returns an "Effetto", an enum which indicates the possible actions of a player right after having reached the next sector (to pick an Escape or Sound Card, to do nothing, or to rewrite the input because it is not valid)
	 */
	public Effetto spostamento(Giocatore giocatore,String coordinate){
		Casella destinazione=convertitore(coordinate);
		Effetto effetto;
		if(raggiungibile(giocatore.getPosizioneAttuale(), giocatore.getPosizioneAttuale(), destinazione, giocatore.getNumPassi())
				&& !alienoscialuppa(giocatore, destinazione)){
			giocatore.setPosizioneAttuale(destinazione);
			effetto = giocatore.getPosizioneAttuale().effettoCasella();
		}	
		else{//Se non è una mossa lecita il giocatore "ritorna" alla casella di partenza
			giocatore.setPosizioneAttuale(giocatore.getPosizioneAttuale());//questo set di una stessa get va lasciato per avvisare l'observer della mossa non lecita
			effetto= Effetto.ERRORE;
		}
			return effetto;
			
	}
	/** This method transforms a string formed by three characters (an uppercase letter and two digits) into a sector object ("Casella")
	 * @param coordinate: a string that comes as an input from the client, indicating the coordinates of the next sector where the player wants to move
	 * @return This method returns the generic sector ("Casella") where the player wants to move in his turn
	 */
	public Casella convertitore(String coordinate){
		char colonna= coordinate.charAt(0);
		int numcolonna = toIntero(colonna);
		char rigadecina = coordinate.charAt(1);
		char rigaunita = coordinate.charAt(2);
		int numriga = toRiga( rigadecina, rigaunita);
		return getCasella(numriga, numcolonna);
			
	}
	
	/**This method (with toIntero) transforms the input of the client when he writes in which sector he wants to move, in order to fit with the internal data structure of the matrix representing the map
	 * @param rigadecina represent one of the two digits, the one with major value between the two. Both the digits are char, but the client will repeat his input if they won't be both integers
	 * @param rigaunita rigaunita represent the other digit, the unit
	 * @return This method returns a number lower than one hundred, starting from two separate char indicating tens and unit
	 */
	private int toRiga( char rigadecina, char rigaunita){
		Character decina= rigadecina;
		Character unita = rigaunita;
		return Character.getNumericValue(decina)*10 + Character.getNumericValue(unita);
	}
	
	/**This method (with toRiga) transforms the input of the client when he writes in which sector he wants to move, in order to fit with the internal data structure of the matrix representing the map
	 * @param carattere: an upper-case char written by the client
	 * @return The method returns an int value equivalent to the input "carattere", which will be used on the algorithm "spostamento" for the movement on the player
	 */
	private int toIntero(char carattere){
		byte c;
		int numero;
		c=(byte)carattere;
		numero = (char)c;
		numero = numero-'A'+1;
		return numero;
	}
	
}
