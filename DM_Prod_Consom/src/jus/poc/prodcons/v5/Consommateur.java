package jus.poc.prodcons.v5;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur {
	protected Tampon t;
	protected Aleatoire alea_temps;
	private int nb_msg_lu = 0;
	
	/**
	 * Construit un consommateur
	 * @param observateur observateur
	 * @param moyenneTempsDeTraitement moyenne des tirages de la variable aléatoire
	 * @param deviationTempsDeTraitement écart-type des tirages de la variable aléatoire
	 * @param tamp définit un tampon entre un producteur et un consommateur
	 * @throws ControlException .
	 */
	protected Consommateur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, Tampon tamp)
					throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		t = tamp;
		alea_temps = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
	}

	/**
	 * retire et lis ou consomme un nombre indéterminé de messages pendant un temps aléatoire suivant une loi gaussienne
	 */
	public void run() {
		while(true){
			try {
				int temps = alea_temps.next();
				Message msg = retraitM();
				observateur.retraitMessage(this, msg);
				consumM(msg);
				observateur.consommationMessage(this, msg, temps);
				this.setNb_msg_lu(getNb_msg_lu() + 1);
				sleep(temps);
				Thread.yield();
				if(TestProdCons.end){
					System.exit(0);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * affiche le message consommé
	 * @param msg message à consommer
	 */
	public void consumM(Message msg) {
		System.out.println("Consommateur : "+identification()+" msg: "+msg);
	}
	
	/**
	 * retire un message du tampon
	 * @return message retiré
	 * @throws InterruptedException .
	 * @throws Exception .
	 */
	private Message retraitM() throws InterruptedException, Exception {
		return t.get(this);
	}

	/**
	 * Retourne nombre de messages consommés ou lus
	 */
	public int nombreDeMessages() {
		return this.getNb_msg_lu();
	}
	
	/**
	 * donne le nb de message lus ou consommés
	 * @return nb_msg_lu
	 */
	public int getNb_msg_lu() {
		return nb_msg_lu;
	}
	
	/**
	 * modifie le nombre de message lus ou consommés
	 * @param nb_msg_lu le nombre de message lus ou consommés
	 */
	public void setNb_msg_lu(int nb_msg_lu) {
		this.nb_msg_lu = nb_msg_lu;
	}


}