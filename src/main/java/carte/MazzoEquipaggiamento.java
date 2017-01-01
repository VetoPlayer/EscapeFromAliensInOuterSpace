package carte;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**This class represents the deck of object cards, implemented with a Stack. It contains the creation of the deck and the method for picking up a card from the deck
 * @author Lollo
 *
 */
public class MazzoEquipaggiamento {
	private static final int ATTACCO=1;
	private static final int DIFESA=2;
	private static final int TELETRASPORTO=3;
	private static final int SEDATIVO=3;
	private static final int LUCE=1;
	private static final int ADRENALINA=2;
	private Deque<CartaEquipaggiamento> mazzo;

	
	/**Constructor of the class: it initializes the attribute mazzo and then calls the constructor of the deck
	 * 
	 */
	public MazzoEquipaggiamento() {
			this.mazzo= new ArrayDeque<CartaEquipaggiamento>();
			creaMazzoEquipaggiamento();
		}
	
	/**This method picks an object card from the top of the deck, and if there's no cards left it creates again the deck before picking up the card
	 * @return an object card
	 */
	public CartaEquipaggiamento pescaCarta(){
		if(mazzo.isEmpty())
			creaMazzoEquipaggiamento();
		return mazzo.pop();
	}
	
	/**Constructor of the deck: the are six types of cards and twelve cards in total. The amount of cards on the deck for every type is declared in the constant int values like ATTACCO,DIFESA..
	 * Cards are first added to an Arraylist and then shuffled and put on a Stack
	 */
	private void creaMazzoEquipaggiamento(){
		List<CartaEquipaggiamento> arrayE = new ArrayList<CartaEquipaggiamento>();
		CartaEquipaggiamento carta;
		int i;		
		for(i=0;i<ATTACCO;i++)
			arrayE.add(new CartaEquipaggiamento(Oggetto.ATTACCO));
		for(i=0;i<DIFESA;i++)
			arrayE.add(new CartaEquipaggiamento(Oggetto.DIFESA));
		for(i=0;i<TELETRASPORTO;i++)
			arrayE.add(new CartaEquipaggiamento(Oggetto.TELETRASPORTO));
		for(i=0;i<SEDATIVO;i++)
			arrayE.add(new CartaEquipaggiamento(Oggetto.SEDATIVO));
		for(i=0;i<LUCE;i++)
			arrayE.add(new CartaEquipaggiamento(Oggetto.LUCE));
		for(i=0;i<ADRENALINA;i++)
			arrayE.add(new CartaEquipaggiamento(Oggetto.ADRENALINA));
		Collections.shuffle(arrayE);
		for(i=arrayE.size()-1;i>=0;i--){
			  carta=arrayE.get(i);
			  arrayE.remove(i);
			  mazzo.push(carta);
		}	
	}
	
	/**Getter
	 * @return the Deque of object cards, the attribute of the class MazzoEquipaggiamento
	 */
	public Deque<CartaEquipaggiamento> getMazzo() {
		return mazzo;
	}
}
