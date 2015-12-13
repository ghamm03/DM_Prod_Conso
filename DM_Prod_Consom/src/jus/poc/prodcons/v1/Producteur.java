package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur{

	protected int nb_message_max;
	private int nb_message_ecrit = 0;
	protected Aleatoire alea_temps;
	protected Tampon t;

	protected Producteur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, int nb_mess, Tampon tamp)
					throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);

		alea_temps = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		t = tamp;
		nb_message_max = nb_mess;
	}

	@Override
	public void run() {
		while(prod()){
			try {
				Message msg = prodM();
				depotM(msg);
				
				this.setNb_message_ecrit(getNb_message_ecrit()+1);
				if(!actif())
					TestProdCons.prodActif--;
				sleep(alea_temps.next());
			} catch (Exception e) {
				e.printStackTrace();
			}
			//JOptionPane.showMessageDialog(null, prod());
		}
	};

	public void depotM(Message msg) throws InterruptedException, Exception {
		t.put(this, msg);
	}

	public Message prodM() {
		return new MessageX(identification());
	}

	/**
	 * Retourne le nombre de message restant
	 */
	@Override
	public int nombreDeMessages() {
		return this.nb_message_max - this.getNb_message_ecrit();
	}

	public boolean prod(){
		return nombreDeMessages()-1 >= 0;
	}

	public int getNb_message_ecrit() {
		return nb_message_ecrit;
	}

	public void setNb_message_ecrit(int nb_message_ecrit) {
		this.nb_message_ecrit = nb_message_ecrit;
	}

	public boolean actif(){
		return nombreDeMessages()>0;
	}


}
