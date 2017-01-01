package giocatori;

import java.util.ArrayList;
import java.util.List;

import controller.StatiGioco;

/**
 * @author Lollo
 * This class implements Turno for the human players. It contains a List of "Arco" objects that sets the possible changes of the phases (Statigioco) of an human's turn
 */
public class TurnoUmano implements Turno{
	private	List<Arco> stati;
	private int objectStateTime;

	/**
	 * Constructor that initializes and covers the ArrayList of "Arco" with the possible changes of the phases (Statigioco) of an human's turn
	 */
	public TurnoUmano() {
		objectStateTime=-1;
		this.stati= new ArrayList<Arco>();
		stati.add(new Arco(StatiGioco.OBJECTSTATE, StatiGioco.STARTSTATE));
		stati.add(new Arco(StatiGioco.STARTSTATE, StatiGioco.OBJECTSTATE));	
		stati.add(new Arco(StatiGioco.OBJECTSTATE, StatiGioco.DRAWSTATE));
		stati.add(new Arco(StatiGioco.DRAWSTATE, StatiGioco.OBJECTSTATE));
		stati.add(new Arco(StatiGioco.OBJECTSTATE, StatiGioco.ENDSTATE));		
	}
	@Override
	public StatiGioco nextPhase(StatiGioco statoAttuale, StatiGioco statoProssimo){
		StatiGioco next = StatiGioco.ERRORE;
		if(statoAttuale.equals(StatiGioco.OBJECTSTATE)){
			for(int i=0; i<stati.size(); i++){
				if(stati.get(i).getFaseCorrente().equals(statoAttuale) && i>objectStateTime){
					next=stati.get(i).getProssimaFase();
					if(next.equals(StatiGioco.ENDSTATE))
						objectStateTime=-1;
					else
						objectStateTime=i;
					return next;
				}
			}
		}
		else
			for(Arco e: stati)
				if(statoAttuale.equals(e.getFaseCorrente()))
					next=e.getProssimaFase();
		return next;
			
	}
	
	

}
