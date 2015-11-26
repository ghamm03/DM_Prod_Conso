package jus.poc.prodcons.v1;

import jus.poc.prodcons.*;

public class Consommateur extends Acteur implements _Consommateur {
	
	private int nbMessLu;
	private Tampon tampon;
	
	protected Consommateur(int type, Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, Tampon tampon)
			throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.nbMessLu = 0;
		this.tampon = tampon;
	}

	/*renvoie le nombre de message a traiter*/
	public int nombreDeMessages() {
		return nbMessLu;
	}
	
	/*lis un seul message*/
	public void run()
	{
		try {	
			//recupere le message depuis le tampon
			Message msg = tampon.get(this);
				
			//le consommateur a lu un message
			nbMessLu++;
				
			//affichage du message
			System.out.println("\t\tLecture IDCons "+identification() + " : "+msg);
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
		//System.out.println("Stop : consommateur : " + identification() + " ayant consomme " + nbMessLu + " messages");

}