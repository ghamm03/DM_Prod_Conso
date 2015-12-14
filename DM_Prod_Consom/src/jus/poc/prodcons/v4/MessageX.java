package jus.poc.prodcons.v4;

import jus.poc.prodcons.Message;


public class MessageX implements Message{
	private int idP;
	private int nbCopy;
	private int nbconso;

	public MessageX(int idProd,int copy){
		this.setIdP(idProd);
		this.setNbCopy(copy);
		nbconso = 0;
	}

	public void consumExemplaire(){
		nbconso++;
	}

	public boolean noExemplaire(){
		return (getNbCopy()-nbconso)==0;
	}

	public int getIdP() {
		return idP;
	}

	public void setIdP(int idP) {
		this.idP = idP;
	}

	public int getNbCopy() {
		return nbCopy;
	}

	public void setNbCopy(int nbCopy) {
		this.nbCopy = nbCopy;
	}

}
