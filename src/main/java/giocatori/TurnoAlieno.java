package giocatori;

import java.util.ArrayList;
import java.util.List;

import controller.StatiGioco;
/**
 * @author Lollo
 * This class implements Turno for the alien players. It contains a List of "Arco" objects that sets the possible changes of the phases (Statigioco) of an alien's turn
 */
public class TurnoAlieno implements Turno {
	private List<Arco> stati;
	//Non gestisco qui gli autoanelli ma a livello di controller, è lui che deve gestire eventuali errori
	/**
	 * Constructor that initializes and covers the ArrayList of "Arco" with the possible changes of the phases (Statigioco) of an alien's turn
	 */
	public TurnoAlieno() {	
		this.stati= new ArrayList<Arco>();
		stati.add(new Arco(StatiGioco.STARTSTATE, StatiGioco.ASKSTATE));
		stati.add(new Arco(StatiGioco.ASKSTATE, StatiGioco.ATTACKSTATE));	
		stati.add(new Arco(StatiGioco.ASKSTATE, StatiGioco.DRAWSTATE));
		stati.add(new Arco(StatiGioco.ATTACKSTATE, StatiGioco.ENDSTATE));	
		stati.add(new Arco(StatiGioco.DRAWSTATE, StatiGioco.ENDSTATE));
		}
		
		
	//Il Metodo riceve in ingresso lo stato del gioco attuale e quello in cui l'utente desidera andare( se ha la possibilità di scegliere)
	// e restituisce il prossimo stato in cui deve andare la FSM
	@Override
	public StatiGioco nextPhase(StatiGioco statoAttuale, StatiGioco statoProssimo){
		StatiGioco next = StatiGioco.ERRORE;
		Arco arco = new Arco(statoAttuale, statoProssimo);
		//Ramo del ""Se non sai dove andare""
		if(statoProssimo.equals(StatiGioco.DONTKNOW)){
			for(Arco e: stati){
				if(arco.getFaseCorrente().equals(e.getFaseCorrente()))
					next=e.getProssimaFase();
			}
		}
		else{
			for(Arco e: stati){
			if(statoAttuale.equals(e.getFaseCorrente()) && statoProssimo.equals(e.getProssimaFase()))
				next= statoProssimo;
			}
		}
		return next;
			
				
	}

}
