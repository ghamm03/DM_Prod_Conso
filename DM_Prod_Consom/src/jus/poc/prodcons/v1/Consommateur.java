package jus.poc.prodcons.v1;

import jus.poc.prodcons.*;

public class Consommateur extends Acteur implements _Consommateur {
	
	private int nbMessLu;
	private Tampon tampon;
	private Aleatoire p;
	
	protected Consommateur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, Tampon tampon)
			throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.nbMessLu = 0;
		this.tampon = tampon;
		this.p = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
	}

	/*renvoie le nombre de message a traiter*/
	public int nombreDeMessages() {
		return nbMessLu;
	}
	
	/*lis un ou plusieurs messages, temps dattente suit une loi gaussienne*/
	public void run()
	{	
		int tpsAttente = 0, duree = 0;
		
		//while(){
			try {
				//recupere le message depuis le tampon
				Message msg = tampon.get(this);
				
				//le consommateur a lu un message
				nbMessLu++;
				
				//affichage du message
				System.out.println("IDCons"+identification() + "a consomme : "+ msg.toString() + "pendant" + tpsAttente);
				
				//le consommateur attends un temps indeterminé
				tpsAttente = 10*p.next();
				sleep(tpsAttente);
				
				//duree = duree + tpsAttente;
				
				} catch ( Exception e) {
					e.printStackTrace();
				}
		//}
	}
		//System.out.println("Stop : consommateur : " + identification() + " ayant consomme " + nbMessLu + " messages");
}