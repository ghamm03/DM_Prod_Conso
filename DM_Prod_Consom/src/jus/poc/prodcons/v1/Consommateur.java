package jus.poc.prodcons.v1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;;

public class Consommateur extends Acteur implements _Consommateur {
	
	private int nbMessLu; 
	private int loi_fixee;
	private int m;
	private int sigma;
	private int date;
	private Tampon tampon;
	private Aleatoire p;
	
	/**
	 * Constructeur Consommateur
	 * @param observateur
	 * @param moyenneTempsDeTraitement : moyenne des temps de traitement des messages
	 * @param deviationTempsDeTraitement : ecart-type des temps de traitement des messages
	 * @param tampon 
	 * @param loi : type de la loi si loi = 1, c'est une loi gaussienne, sinon loi uniforme
	 * @throws ControlException
	 */
	protected Consommateur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, Tampon tampon, int loi) throws ControlException{
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.nbMessLu = 0;
		this.tampon = tampon;
		this.p = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.loi_fixee = loi;
		this.m = moyenneTempsDeTraitement;
		this.sigma = deviationTempsDeTraitement;
		this.date = 0;
	}

	/**
	 * renvoie le nombre de message a traiter
	 * @return nbreMessLu en integer
	 */
	public int nombreDeMessages() {
		return nbMessLu;
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
	
	/**
	 * retire un message du tampon
	 * @return un objet message
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public Message retire() throws InterruptedException, Exception
	{	
		return tampon.get(this);
	}
	
	/**
	 * consomme un message pendant un temps aléatoire
	 * @param msg message à consommer
	 * @throws InterruptedException 
	 */
	public void lire(Message msg) throws InterruptedException{
			//prends son temps pour lire
			int tps_attente;
			tps_attente = tps_aleatoire();
			sleep(tps_attente);
			
			//consomme
			System.out.println("IDCons"+identification() + "a consomme : "+ msg.toString() + "pendant" + tps_attente + "a la date" + this.date);
			
			//mise à jour de la date et nbMesslu
			this.date = this.date + tps_attente;
			nbMessLu++;
	}
	
	/**
	 * montre la consommation totale
	 */
	public void fin(){
		int n = nbMessLu/date;
		System.out.println("En tout le consommateur : " + identification() + " a consomme " + nbMessLu + " messages" + "pendant" + date + "soit" + n + "message/secondes");
	} 
	
	/**
	 * renvoie à quel moment le consommateur a lu un message
	 * @return date en integer
	 */
	public int date_cons(){
		return date;
	}
}