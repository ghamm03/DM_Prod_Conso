package jus.poc.prodcons.v3;

import java.util.ArrayList;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	private ArrayList<Message> list = new ArrayList<Message>();
	private int size;
	protected Semaphore conso;
	protected Semaphore mutex;

	public ProdCons(int nbBuffer) {
		setSize(nbBuffer);
		conso = new Semaphore(0);
		mutex = new Semaphore(1);
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
