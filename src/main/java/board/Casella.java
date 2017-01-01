package board;

/**
 * @author Lollo
 * This abstract class represents a sector of the board: every other class with "Casella" in his name is derived from this class
 * and inherits the abstract method effettoCasella which returns a different "Effetto" in every subclass.
 * 
 */
public abstract class Casella {		
		private int x;
		private int y;
		private boolean crossable;
		
	/**Constructor called during the creation of the map, which is represented in a matrix structure
	 * @param y number of the row of the sector in the map (matrix)
	 * @param x number of the column of the sector in the map (matrix)
	 * @param crossable: it's true if a player can walk in that type of sector
	 */
	public Casella(int y, int x,boolean crossable){
		this.x= x;
		this.y= y;
		this.setCrossable(crossable);
	}
		
	/**Getter
	 * @return an int which is the column of the sector (column A is 1, column B is 2, etc.)
	 */
	public int getX() {
		return x;
	}

	/**Getter
	 * @return an int which is the row of the sector
	 */
	public int getY() {
		return y;
	}

	/** Getter
	 * @return true if the sector is crossable, otherwise false
	 */
	public boolean isCrossable() {
		return crossable;
	}
	
		
	/**
	 * @return result, which is a boolean. It is true if the column of the sector is odd. It's a method useful for the algorithm
	 * of the movement of the players onto the board (called by the method raggiungibile)
	 */
	public boolean dispari(){
		boolean result;
		if(this.getX() %2 != 0)
			result= true;
		else
			result=false;
		return result;
	}
	
	
	/**This method is abstract and returns the future effects on the player after he has reached a certain type of sector during his turn 
	 * @return Effetto, an enum in package "board"
	 */
	public abstract Effetto effettoCasella();
	
	public void setCrossable(boolean crossable) {
		this.crossable = crossable;
	}

}