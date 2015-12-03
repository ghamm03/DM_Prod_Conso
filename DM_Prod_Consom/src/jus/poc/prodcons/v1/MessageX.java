package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;


public class MessageX implements Message{
	private int idP;

	public MessageX(int idProd){
		this.setIdP(idProd);
	}

	public int getIdP() {
		return idP;
	}

	public void setIdP(int idP) {
		this.idP = idP;
	}

}
