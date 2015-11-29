package jus.poc.prodcons.v1;

import jus.poc.prodcons.*;

public class Consommateur extends Acteur implements _Consommateur {
	
	private int nbMessLu; 
	private int loi_fixee;
	private int m;
	private int sigma;
	private int date;
	private Tampon tampon;
	private Aleatoire p;
	
	
	/*0 pour loi uniforme
	 *1 pour loi gaussienne*/
	protected Consommateur(Observateur observateur,
			int moyenneTempsDeTraitement, int deviationTempsDeTraitement, Tampon tampon, int loi)
			throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		
		this.nbMessLu = 0;
		this.tampon = tampon;
		this.p = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.loi_fixee = loi;
		this.m = moyenneTempsDeTraitement;
		this.sigma = deviationTempsDeTraitement;
		this.date = 0;
	}

	/*renvoie le nombre de message a traiter*/
	public int nombreDeMessages() {
		return nbMessLu;
	}
	
	/*renvoie un tps aleatoire de seconde suivant une loi fixées*/
	private int tps_aleatoire(){
		if(this.loi_fixee == 1)
			return p.next();
		return Aleatoire.valeur(m,sigma);
	}
	
	/*retire un message du tampon*/
	public Message retire()
	{	
		try {
			return tampon.get(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	/*consomme un message pendant un temps aléatoire*/
	public void consomme(Message msg){
		try {
			//attends
			int tps_attente;
			tps_attente = tps_aleatoire();
			sleep(tps_attente);
			
			//consomme
			System.out.println("IDCons"+identification() + "a consomme : "+ msg.toString() + "pendant" + tps_attente + "a la date" + this.date);
			
			//mise à jour de la date et nbMesslu
			this.date = this.date + tps_attente;
			nbMessLu++;
			
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void fin(){
		int n = nbMessLu/date;
		System.out.println("En tout le consommateur : " + identification() + " a consomme " + nbMessLu + " messages" + "pendant" + date + "soit" + n + "message/secondes");
	} 
	
	public int date_cons(){
		return date;
	}
}