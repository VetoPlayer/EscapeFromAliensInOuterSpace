package carte;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**This class represents the deck of sound cards, implemented with a Stack. It contains the creation of the deck and the method for picking up a card from the deck
 * @author Lollo
 *
 */
public class MazzoSuono {
	public static final int ALTROSETTORECONOGGETTO=4;
	public static final int TUOSETTORECONOGGETTO=4;
	public static final int ALTROSETTORESENZAOGGETTO=6;
	public static final int TUOSETTORESENZAOGGETTO=6;
	public static final int SILENZIO=5;
	private Deque<CartaSuono> mazzo;
	
	/**Constructor of the class: it initializes the attribute mazzo and then calls the constructor of the deck
	 * 
	 */
	public MazzoSuono() {
		this.mazzo= new ArrayDeque<CartaSuono>();
		creaMazzoSuono();
	}
	
	/**This method picks a sound card from the top of the deck, and if there's no cards left it creates again the deck before picking up the card
	 * @return a sound card
	 */
	public CartaSuono pescaCarta(){
		if(mazzo.isEmpty())
			creaMazzoSuono();
		return mazzo.pop();
	}
	
	/**Constructor of the deck. The amount of different types of cards created are defined by the integer constants of this class
	 * Cards are first added to a List and then shuffled and put on a Stack
	 */
	private void creaMazzoSuono(){
		List<CartaSuono> arrayS = new ArrayList<CartaSuono>();
		CartaSuono carta;
		int i;	
		  for(i=0;i<ALTROSETTORECONOGGETTO;i++)	
			arrayS.add(new CartaRumoreAltroSettore(true));
		  for(i=0;i<TUOSETTORECONOGGETTO;i++)	
			arrayS.add(new CartaRumoreTuoSettore(true));
		  for(i=0;i<ALTROSETTORESENZAOGGETTO;i++)	
			arrayS.add(new CartaRumoreAltroSettore(false));
		  for(i=0;i<TUOSETTORESENZAOGGETTO;i++)	
			arrayS.add(new CartaRumoreTuoSettore(false));
		  for(i=0;i<SILENZIO;i++)	
			arrayS.add(new CartaSilenzio());			  
		  Collections.shuffle(arrayS);
		  for(i=arrayS.size()-1;i>=0;i--){
			  carta=arrayS.get(i);
			  arrayS.remove(i);
			  mazzo.push(carta);
		  }
	}
	
	/**Getter
	 * @return the Deque of sound cards, the attribute of the class MazzoSuono
	 */
	public Deque<CartaSuono> getMazzo() {
		return mazzo;
	}

}
