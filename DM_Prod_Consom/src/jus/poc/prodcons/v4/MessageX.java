package jus.poc.prodcons.v4;

import jus.poc.prodcons.Message;


public class MessageX implements Message{
	private int idP;
	private int nb_copy;

	public MessageX(int idProd, int copy){
		this.setIdP(idProd);
		this.setNb_copy(copy);
	}

	public int getIdP() {
		return idP;
	}

	public void setIdP(int idP) {
		this.idP = idP;
	}

	public int getNb_copy() {
		return nb_copy;
	}

	public void setNb_copy(int nb_copy) {
		this.nb_copy = nb_copy;
	}

}
