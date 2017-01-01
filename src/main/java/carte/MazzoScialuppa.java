package carte;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**This class represents the deck of escape cards, implemented with a Stack. It contains the creation of the deck and the method for picking up a card from the deck
 * @author Lollo
 *
 */
public class MazzoScialuppa {
	public static final int NUMSCIALUPPEBUONE=3;
	public static final int NUMSCIALUPPEROTTE=3;
	private Deque<CartaScialuppa> mazzo;

	/**Constructor of the deck. The amount of working and not working escape cards is declared in the constant int values NUMSCIALUPPEBUONE and NUMSCIALUPPEROTTE
	 * Cards are first added to an Arraylist and then shuffled and put on a Stack
	 */
	public MazzoScialuppa() {
		this.mazzo= new ArrayDeque<CartaScialuppa>();
		List<CartaScialuppa> arrayS = new ArrayList<CartaScialuppa>();
		CartaScialuppa carta;
		int i;		
		for(i=0;i<NUMSCIALUPPEBUONE;i++){
			arrayS.add(new CartaScialuppa(true));
		}
		for(i=0;i<NUMSCIALUPPEROTTE;i++){
			arrayS.add(new CartaScialuppa(false));
		}
		Collections.shuffle(arrayS);
		for(i=arrayS.size()-1;i>=0;i--){
			  carta=arrayS.get(i);
			  arrayS.remove(i);
			  mazzo.push(carta);
		}
	}
	
	/**This method is the equivalent of the pop method on a classic Stack, applied to this particular deck (escape cards deck)
	 * @return the first object card at the top of the stack
	 */
	public CartaScialuppa pescaCarta(){
		return mazzo.pop();
	}

	/**Getter
	 * @return the Deque of escape cards, the attribute of the class MazzoScialuppa
	 */
	public Deque<CartaScialuppa> getMazzo() {
		return mazzo;
	}
	
	
}