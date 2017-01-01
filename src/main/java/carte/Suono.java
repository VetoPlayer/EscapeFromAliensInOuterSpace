package carte;

/**
 * @author Lollo
 *Enum that distinguish the effects after picking a sound card (SILENZIO,RUMORE,RUMOREALTROSETTORE) and the case in which a player end his turn on a safe sector (NESSUNRUMORE)
 */
public enum Suono {
	//NessunRumore è relativo ai settori sicuri, Silenzio, rumore e rumore altro settore sono relativi ai settori non sicuri
	NESSUNRUMORE,SILENZIO,RUMORE,RUMOREALTROSETTORE

}
