package jus.poc.prodcons.v1;

import java.util.ArrayList;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {
	private ArrayList<Message> list = new ArrayList<Message>();
	private int size;

	public ProdCons(int nbBuffer) {
		setSize(nbBuffer);
	}

	@Override
	public int enAttente() {
		return list.size();
	}

	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception,
	InterruptedException {
		while(noMessage()){
			wait();
		}
		Message m = list.remove(enAttente()-1);
		notifyAll();
		return m;
	}


	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception,
	InterruptedException {
		while(isFull()){
			wait();
		}
		list.add(arg1);
		notifyAll();
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
