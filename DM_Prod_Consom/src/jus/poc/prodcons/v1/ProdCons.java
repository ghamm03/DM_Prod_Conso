package jus.poc.prodcons.v1;

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

	public ProdCons(int nbBuffer) {
		setSize(nbBuffer);
		buffer = new Message[nbBuffer];
	}

	@Override
	public int enAttente() {
		return nbmsg;
	}

	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception,
	InterruptedException {
		while(noMessage()){
			wait();
		}
		Message m = buffer[out];
		out = (out+1)%size;
		nbmsg--;

		if(TestProdCons.prodActif == 0 && noMessage())
			TestProdCons.end = true;
		notifyAll();
		return m;
	}


	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception,
	InterruptedException {
		while(isFull()){
			wait();
		}

		if(!((Producteur) arg0).actif()){
			TestProdCons.prodActif--;
		}

		buffer[in] = arg1;
		in = (in+1)%size;
		nbmsg++;

		notifyAll();
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
