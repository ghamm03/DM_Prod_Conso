package jus.poc.prodcons.v6;

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
	protected MyObservateur myObs;
	
	/**
	 * Construit un producteur
	 * @param observateur observateur de la hiérarchie donné
	 * @param moyenneTempsDeTraitement moyenne des tirages de la variable aléatoire
	 * @param deviationTempsDeTraitement écart-type des tirages de la variable aléatoire
	 * @param nb_mess nombre de message à produire ou écrire
	 * @param tamp définit un tampon entre un producteur et un consommateur
	 * @param obs observateur de guillaume
	 * @throws ControlException .
	 */
	protected Producteur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, int nb_mess, Tampon tamp, MyObservateur obs)
					throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);

		alea_temps = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		t = tamp;
		nb_message_max = nb_mess;
		myObs = obs;
	}

	/**
	 * ecrit ou produit un nombre de message déterminé dans le constructeur et le dépose dans le tampon pendant un temps aléatoire suivant une loi gaussienne
	 */
	public void run() {
		while(prod()){
			try {
				int temps = alea_temps.next();
				Message msg = prodM();
				observateur.productionMessage(this, msg, temps);
				myObs.productionMessage(this, msg, temps);
				depotM(msg);
				this.setNb_message_ecrit(nb_message_ecrit+1);
				sleep(temps);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * dépose un message dans le tampon
	 * @param msg message à déposer
	 * @throws InterruptedException .
	 * @throws Exception .
	 */
	public void depotM(Message msg) throws InterruptedException, Exception {
		t.put(this, msg);
		observateur.depotMessage(this, msg);
		myObs.depotMessage(this, msg);
	}
	
	/**
	 * retourne un nouveau message écrit ou produit par un producteur
	 * @return nouveau message créé par un producteur
	 */
	public MessageX prodM() {
		return new MessageX(identification());
	}

	/**
	 * Retourne le nombre de message restant à écrire ou produire
	 */
	public int nombreDeMessages() {
		return this.nb_message_max - this.getNb_message_ecrit();
	}
	
	/**
	 * vrai si il reste des messages à écrire, sinon faux
	 * @return boolean qui teste s'il reste de messages à écrire
	 */
	public boolean prod(){
		return nombreDeMessages()-1 >= 0;
	}
	
	/**
	 * renvoie le nombre de message écrit ou produit par un producteur
	 * @return nb_message_ecrit
	 */
	public int getNb_message_ecrit() {
		return nb_message_ecrit;
	}
	
	/**
	 * modifie le nombre de message à écrire
	 * @param nb_message_ecrit nombre restant de message à écrire
	 */
	public void setNb_message_ecrit(int nb_message_ecrit) {
		this.nb_message_ecrit = nb_message_ecrit;
	}
	
	/**
	 * vrai si le producteur lui reste des messages à écrire, sinon faux
	 * @return boolean teste si le producteur est actif ou non
	 */
	public boolean actif(){
		return nombreDeMessages()-1>0;
	}


}
