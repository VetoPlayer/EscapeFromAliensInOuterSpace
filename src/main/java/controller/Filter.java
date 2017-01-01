/**
 * 
 */
	package controller;

	import giocatori.Alieno;
import giocatori.Giocatore;
import giocatori.Umano;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import server.RemoteNotifier;
import board.Casella;
import carte.CartaEquipaggiamento;
import carte.Oggetto;
import carte.Suono;

	/**
	 * @author Andrea
	 * Class that implements Observer, with the method "update" that takes in input many different strings written in the model in the various "notifyObservers".
	 * The if branches of the method "update" notifies messages to one player or to all the clients with the method "announce" (RemoteNotifiers)
	 */

	public class Filter implements Observer {
	
		//Notificatore del giocatore di turno
		private List<RemoteNotifier> notifiers;
		private RemoteNotifier rno;//è il client che sta eseguendo il turno. Quando cambio turno cambia rn
		public static final int NUMMAXOGGETTIINMANO=3;
		
		/**
		 * Constructor
		 */
		public Filter(){
			notifiers= new ArrayList<RemoteNotifier>();
		}
		
		/**Getter of a client with the id written in the parameter
		 * @param id id of the player
		 * @return the client with that id
		 * @throws RemoteException
		 */
		private RemoteNotifier getNotifier(int id) throws RemoteException{
			for(RemoteNotifier rnt: notifiers){
				if(rnt.getId()== id)
					return rnt;
			}
			return null;
		}
		//Funzione privata che notifica tutti i giocatori ( Da utilizzare solo per eventi "pubblici")
		/** Method used when a message should be sent to all the players of a game
		 * @param msg string sent to all the players of a game
		 * @throws RemoteException
		 */
		private void announce(String msg) throws RemoteException{
			for(RemoteNotifier rnt: notifiers)
				rnt.notifyMessage(msg);
		}
		
		@Override
		public void update(Observable o, Object arg) {
			 if((String)arg=="GiocatoreMorto"){
				try{
					Giocatore g=(Giocatore)o;
					announce("il giocatore"+g.getIdGiocatore()+"è stato ucciso nel settore"+FilterUtility.codificaCasella(g.getPosizioneAttuale())+"");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
			}else if((String)arg=="GiocatoreVincitore"){
				try{
					Giocatore g=(Giocatore)o;
					announce("Il giocatore"+g.getIdGiocatore()+"ha vinto");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
			}
			else if((String)arg=="Attacco"){
				try {
					Giocatore g=(Giocatore)o;
					announce("il giocatore"+g.getIdGiocatore()+"ha attaccato nel settore"+FilterUtility.codificaCasella(g.getPosizioneAttuale())+"");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}//suono NON fa avanzare di fase (che poi alla fine mi fa andare sul giocatore successivo) se ho pescato una carta oggetto, anche perchè altrimenti viene notificato il giocatore sbagliato di tale avvenimento
			else if((String)arg=="Suono"){
				try {
					Giocatore g =(Giocatore)o;
					Suono s=g.getSuono();
					if(Suono.SILENZIO.equals(s)){
						rno.notifyMessage("Hai pescato la carta Silenzio");
						announce("Silenzio");

					}
					if(Suono.RUMORE.equals(s)){
						rno.notifyMessage("Hai pescato la carta RumoreNelTuoSettore");
						Casella c=g.getPosizioneAttuale();
						String msg=FilterUtility.codificaCasella(c);
						announce("Rumore nel settore"+msg+"");
						
					}
					if(Suono.RUMOREALTROSETTORE.equals(s)){
						rno.notifyMessage("Hai pescato la carta RumoreAltroSettore: in quale settore vuoi far rumore?");
						rno.setStatoAttuale(StatiGioco.NOISESTATE);
					}
					if(Suono.NESSUNRUMORE.equals(s)){
						rno.notifyMessage("Il settore è sicuro, non peschi carte suono");
						announce("Silenzio");
						StatiGioco nextPhase=g.nextPhase(StatiGioco.DRAWSTATE, StatiGioco.DONTKNOW);
						rno.setStatoAttuale(nextPhase);
						
						
					}
					//Se non ha un equipaggiamento in mano o non è il rumore in un altro settore setto la prossima fase
					//altrimenti ci pensa l'avviso dell'oggetto che ho pescato a far avanzare la fase
					if(!Suono.NESSUNRUMORE.equals(s) && !g.getSuonoInMano().hasEquipaggiamento() && !Suono.RUMOREALTROSETTORE.equals(s)){
						//setto la prossima fase
						StatiGioco nextPhase=g.nextPhase(StatiGioco.DRAWSTATE, StatiGioco.DONTKNOW);
						rno.setStatoAttuale(nextPhase);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			else if((String)arg=="RumoreAltroSettore"){
				try {
					Giocatore g =(Giocatore)o;
					Casella c=g.getCasellaRumore();
					String msg=FilterUtility.codificaCasella(c);
					announce("Rumore nel settore"+msg+"");
					StatiGioco nextPhase=g.nextPhase(StatiGioco.DRAWSTATE, StatiGioco.DONTKNOW);
					//setto la prossima fase
					rno.setStatoAttuale(nextPhase);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			else if((String)arg=="OggettoAttivato"){
				try {
					Giocatore g =(Giocatore)o;
					//prende l'oggetto attivato
					CartaEquipaggiamento equip=g.getOggettoAttivato();
					//mostra il suo effetto al giocatore
					String risposta=FilterUtility.notifyOggetto(equip);
					rno.notifyMessage(risposta);
					//gestisco caso oggetto luce ed eseguo un autoanello
					if(!equip.getOggetto().equals(Oggetto.LUCE))
						rno.notifyMessage("Vuoi attivare un altro equipaggiamento? Y N");
					else
						rno.setStatoAttuale(StatiGioco.LUXSTATE);
					//rimuove l'equipaggiamento
					g.scartaOggetto(equip);		
					} catch (RemoteException e) {
					e.printStackTrace();
				}		
			}//Se il giocatore non possiede l'oggetto nell'inventario eseguo un autoanello nello stato ObjectState
			else if((String)arg=="OggettoNonAttivato"){
				try {
						rno.notifyMessage("Non hai nell'inventario questo oggetto");
						rno.setStatoAttuale(StatiGioco.OBJECTSTATE);
					} catch (RemoteException e) {
					e.printStackTrace();
				}
								
			}
			else if((String)arg=="DifesaNonAttivabile"){
				try {
						rno.notifyMessage("Non puoi attivare la carta difesa");
						rno.setStatoAttuale(StatiGioco.OBJECTSTATE);
					} catch (RemoteException e) {
					e.printStackTrace();
				}
								
			}
			 
			else if((String)arg=="OggettoScartato"){
				try {
						rno.notifyMessage("Hai scartato con successo l'oggetto");
						rno.setStatoAttuale(StatiGioco.ENDSTATE);
					} catch (RemoteException e) {
					e.printStackTrace();
				}
								
			}
		
			else if((String)arg=="illuminato"){
				try {
					Giocatore g =(Giocatore)o;
					String posizione=FilterUtility.codificaCasella(g.getPosizioneAttuale());
					announce("Luce! Il giocatore"+g.getIdGiocatore()+"si trova nel settore"+posizione+"");
					rno.notifyMessage("Vuoi attivare un altro equipaggiamento? Y N");
					
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			else if((String)arg=="Difesa"){
				try {
					Giocatore g =(Giocatore)o;
					RemoteNotifier r= getNotifier(g.getIdGiocatore());
					r.notifyMessage("Hai attivato con successo Difesa: ti sei salvato da morte certa\n");
					g.scartaOggetto(g.getOggettoAttivato());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			else if((String)arg=="MostraEquipaggiamento"){
				try{
					Giocatore g=(Giocatore)o;
					List<CartaEquipaggiamento> equip= g.getEquipaggiamento();
					String oggetti= FilterUtility.convertiEquip(equip);
					rno.notifyMessage("Scegli l'oggetto da utilizzare, altrimenti N");
					rno.notifyMessage(oggetti);
				}
				catch (RemoteException e) {
					e.printStackTrace();
				}
				
			}
			else if((String)arg=="NuovaPosizione"){
				Giocatore g= (Giocatore)o;
				Casella casella=g.getPosizioneAttuale();
				//Ottengo la posizione attuale, la codifico e la invio al giusto giocatore
				String casellaCodificata=FilterUtility.codificaCasella(casella);
				try {
					rno.notifyMessage("Ti sei spostato in "+casellaCodificata+"");
					//ora che mi sono spostato dovrei fare una funzione che imposta lo stato prossimo del RemoteClient
					//l'idea è quella di prendere la funzione già esistente in gioco e metterla sul singolo giocatore
					//così la posso invocare da qui e non ho bisogno di passarmi l'intero gioco
					StatiGioco nextPhase=g.nextPhase(rno.getStatoAttuale(), StatiGioco.DONTKNOW);
					//setto la prossima fase
					rno.setStatoAttuale(nextPhase);
					//done!!! 
				} catch (RemoteException e) {
					e.printStackTrace();
					}
			}
			else if((String)arg=="MossaNonLecita"){
				try {
					rno.notifyMessage("La casella inserita non è lecita");
					//eseguo un autoanello a livello di fase
					rno.setStatoAttuale(StatiGioco.STARTSTATE);
				} catch (RemoteException e) {
					e.printStackTrace();
					}
			}
				
			else if((String)arg=="NuovoStato"){
				try {
					Giocatore g=(Giocatore)o;
					StatiGioco statoAttuale=g.getStatoAttuale();
					// imposto filter su quel giocatore, lo avviso che è il suo turno e imposto il suo stato in STARTSTATE
					if(statoAttuale==StatiGioco.STARTSTATE || statoAttuale==StatiGioco.OBJECTSTATE)
						rno=this.getNotifier(g.getIdGiocatore());
					rno.setStatoAttuale(statoAttuale);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				}
			else if((String)arg=="ScialuppaFunzionante"){
				try {
					rno.notifyMessage("La scialuppa funziona: hai vinto la partita");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
				else if((String)arg=="ScialuppaNonFunzionante"){
					try {
						rno.notifyMessage("La scialuppa non funziona: prova ad andare in un'altra scialuppa di salvataggio");
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			else if((String)arg=="NuovoEquipaggiamento"){
				try {
					Giocatore g=(Giocatore)o;
					CartaEquipaggiamento pescata=g.getEquipaggiamento().get(g.getEquipaggiamento().size()-1);
					rno.notifyMessage("Hai pescato la carta equipaggiamento "+FilterUtility.decodeOggetto(pescata)+"");
					if(g.getEquipaggiamento().size()>NUMMAXOGGETTIINMANO){
						List<CartaEquipaggiamento> equip= g.getEquipaggiamento();
						String oggetti= FilterUtility.convertiEquip(equip);
						if(g instanceof Umano)
							rno.notifyMessage("Hai troppi oggetti in mano, scegli quello da utilizzare");
						else
							rno.notifyMessage("Hai troppi oggetti in mano, scegli quello da scartare");
						//TODO porto il giocatore in DISCARDSTATE
						rno.setStatoAttuale(StatiGioco.DISCARDSTATE);
						rno.notifyMessage(oggetti);
					}
					else if(!g.getSuono().equals(Suono.RUMOREALTROSETTORE)){
						//avanzo di stato
						StatiGioco nextPhase=g.nextPhase(StatiGioco.DRAWSTATE, StatiGioco.DONTKNOW);
						rno.setStatoAttuale(nextPhase);
					}
						
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				}
			
		}
			
			
		/**It announces to the players that they are a human or an alien
		 * @param nato player that has just been created
		 */
		public void nuovoGiocatore(Giocatore nato){	
			try {
				//Prendo l'id di quel giocatore, lo sfrutto per trovare il suo corrispondente notifier per dirgli il suo ruolo
				RemoteNotifier r= getNotifier(nato.getIdGiocatore());
				r.setStatoGCreato();
				if(nato instanceof Umano)
					r.notifyMessage("E' iniziata la partita, sei un Umano");
				if(nato instanceof Alieno)
					r.notifyMessage("E' iniziata la partita, sei un Alieno");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
		}
		
		/**Setter
		 * @param rn a remote notifier who is playing his turn at the moment
		 */
		public void setRn(RemoteNotifier rn) {
			this.rno = rn;
		}


		/**Getter
		 * @param rn the remote notifier who is playing his turn at the moment
		 */
		public void addNotifier(RemoteNotifier rn){
			this.notifiers.add(rn);
			return;
		}


	}
