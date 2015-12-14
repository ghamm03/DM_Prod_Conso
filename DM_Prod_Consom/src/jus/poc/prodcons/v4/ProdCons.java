package jus.poc.prodcons.v4;

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
	protected Semaphore disabledProd;

	public ProdCons(int nbBuffer) {
		setSize(nbBuffer);
		buffer = new Message[nbBuffer];
		notFull = new Semaphore(nbBuffer);
		notEmpty = new Semaphore(0);
		mutex = new Semaphore(1);
		disabledProd = new Semaphore(0);
	}

	@Override
	public int enAttente() {
		return nbmsg;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception,
	InterruptedException {
		MessageX m ;
		notEmpty.p();
		mutex.p();

		m = (MessageX) buffer[out];
		m.consumExemplaire();

		if(m.noExemplaire()){
			out = (out+1)%size;
			nbmsg--;
			mutex.signal();
			notFull.signal();
			disabledProd.signal();
		}
		else{
			mutex.signal();
			notEmpty.signal();
		}

		if(TestProdCons.prodActif == 0 && noMessage())
			TestProdCons.end = true;

		return m;
	}


	@Override
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
		disabledProd.p();//bloque prod tant que les exemplaires n'ont pas ete conso
	}

	private boolean isFull() {
		return nbmsg==getSize();
	}


	@Override
	public int taille() {
		return getSize();
	}

	private boolean noMessage() {
		return nbmsg==0;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
