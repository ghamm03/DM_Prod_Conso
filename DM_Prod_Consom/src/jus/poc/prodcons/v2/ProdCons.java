package jus.poc.prodcons.v2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;


public class ProdCons implements Tampon {
	private Message[] buffer;
	private int size;
	private int out = 0;
	private int in = 0;
	private int nbmsg = 0;
	protected Semaphore notFull;
	protected Semaphore notEmpty;
	protected Semaphore mutex;
	
	/**
	 * Constructeur du buffer - les producteurs placent les messages dans le tampon et les consommateurs retirent des messages
	 * @param nbBuffer taille du buffer
	 */
	public ProdCons(int nbBuffer) {
		setSize(nbBuffer);
		buffer = new Message[nbBuffer];
		notFull = new Semaphore(nbBuffer);
		notEmpty = new Semaphore(0);
		mutex = new Semaphore(1);
	}

	/**
	 * retourne le nombre de message dans le buffer
	 */
	public int enAttente() {
		return nbmsg;
	}

	/**
	 * retire un message du buffer par un consommateur
	 */
	public Message get(_Consommateur arg0) throws Exception,
	InterruptedException {
		Message m ;
		notEmpty.p();
		mutex.p();

		m = buffer[out];
		out = (out+1)%size;
		nbmsg--;

		if(TestProdCons.prodActif == 0 && noMessage())
			TestProdCons.end = true;

		mutex.signal();
		notFull.signal();
		return m;
	}


	/**
	 * place un message dans un buffer par un producteur
	 */
	public void put(_Producteur arg0, Message arg1) throws Exception,
	InterruptedException {
		notFull.p();
		mutex.p();

		if(!((Producteur) arg0).actif()){
			TestProdCons.prodActif--;
		}

		buffer[in] = arg1;
		in = (in+1)%size;
		nbmsg++;

		mutex.signal();
		notEmpty.signal();
	}
	
	/**
	 * vrai si le buffer est plein, sinon faux
	 * @return boolean teste si le buffer est plein
	 */
	private boolean isFull() {
		return nbmsg==getSize();
	}


	/**
	 * retourne la taille du buffer
	 */
	public int taille() {
		return getSize();
	}
	
	/**
	 * vrai si le buffer est vide sinon faux
	 * @return boolean teste si le buffer est vide ou plein
	 */
	private boolean noMessage() {
		return nbmsg==0;
	}
	

	/**
	 * retourne la taille du buffer
	 * @return la taille du buffer
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * modifie la taille du buffer
	 * @param size nouvelle taille du buffer
	 */
	public void setSize(int size) {
		this.size = size;
	}

}
