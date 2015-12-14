package jus.poc.prodcons.v6;

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

	protected Consommateur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, Tampon tamp)
					throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		t = tamp;
		alea_temps = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
	}

	@Override
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

	public void consumM(Message msg) {
		System.out.println("Consommateur : "+identification()+" msg: "+msg);
	}

	private Message retraitM() throws InterruptedException, Exception {
		return t.get(this);
	}

	/**
	 * Retourne nombre de message consomm
	 */
	@Override
	public int nombreDeMessages() {
		return this.getNb_msg_lu();
	}

	public int getNb_msg_lu() {
		return nb_msg_lu;
	}

	public void setNb_msg_lu(int nb_msg_lu) {
		this.nb_msg_lu = nb_msg_lu;
	}


}