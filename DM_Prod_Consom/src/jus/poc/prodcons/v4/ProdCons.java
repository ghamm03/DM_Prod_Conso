package jus.poc.prodcons.v4;

import java.util.ArrayList;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	private ArrayList<Message> list = new ArrayList<Message>();
	private int size;
	protected Semaphore conso;
	protected Semaphore mutex;
	protected Observateur obs;

	public ProdCons(int nbBuffer, Observateur observateur) {
		setSize(nbBuffer);
		conso = new Semaphore(0);
		mutex = new Semaphore(1);
		obs = observateur;
	}

	@Override
	public int enAttente() {
		return list.size();
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception,
	InterruptedException {
		Message m ;
		conso.p();
		mutex.p();

		m = list.remove(enAttente()-1);
		obs.retraitMessage(arg0, m);
		if(TestProdCons.prodActif == 0 && noMessage())
			TestProdCons.end = true;

		mutex.signal();
		return m;
	}


	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception,
	InterruptedException {

		mutex.p();
		list.add(arg1);
		obs.depotMessage(arg0, arg1);
		mutex.signal();
		conso.signal();
	}

	private boolean isFull() {
		return list.size()==getSize();
	}

	@Override
	public int taille() {
		return getSize();
	}

	private boolean noMessage() {
		return list.size()==0;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
