package jus.poc.prodcons.v1;

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
				Message msg = t.get(this);
				System.out.println("ID : "+identification()+" msg: "+msg);
				this.setNb_msg_lu(getNb_msg_lu() + 1);
				sleep(alea_temps.next());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Retourne nombre de message consommé
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