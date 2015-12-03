package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Producteur;

//todo : message ou messageX
public class Producteur extends Acteur implements _Producteur{

	private int nbMessEcrits;
	private int loi_fixee;
	private int m;
	private int sigma;
	private int date;
	private Tampon tampon;
	private Aleatoire p;
	private MessageX msg;

	protected Producteur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, Tampon tampon, int loi)
					throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);

		this.nbMessEcrits = 0;
		this.tampon = tampon;
		this.p = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.loi_fixee = loi;
		this.m = moyenneTempsDeTraitement;
		this.sigma = deviationTempsDeTraitement;
		this.date = 0;
		this.msg = new MessageX();
	}

	/**
	 * renvoie le nombre de message a traiter
	 * @return nbreMessEcrits en integer
	 */
	@Override
	public int nombreDeMessages() {
		return nbMessEcrits;
	}

	/**
	 * renvoie un tps aleatoire suivant une loi fixées dans le constructeur
	 * @return le temps en seconde entière
	 */
	private int tps_aleatoire(){
		if(this.loi_fixee == 1)
			return p.next();
		return Aleatoire.valeur(m,sigma);
	}

	//	/**
	//	 * depose un message du tampon
	//	 * @return un objet message
	//	 * @throws Exception
	//	 * @throws InterruptedException
	//	 */
	//	public void depose(MessageX msg) throws InterruptedException, Exception
	//	{
	//
	//	}

	/**
	 *
	 * @param msg
	 * @throws Exception
	 */
	public void ecrit(String chaine) throws Exception{
		//en train d'écrire une poèsie(ca prends du temps)
		int tps_attente;

		msg.setM(chaine);
		tps_attente = tps_aleatoire();
		sleep(tps_attente);

		//consomme
		System.out.println("IDProd"+identification() + "a écrit : "+ msg.toString() + "pendant" + tps_attente + "a la date" + this.date);

		//mise à jour de la date et nbMesslu
		this.date = this.date + tps_attente;
		nbMessEcrits++;
		tampon.put(this,msg);

	}

}
