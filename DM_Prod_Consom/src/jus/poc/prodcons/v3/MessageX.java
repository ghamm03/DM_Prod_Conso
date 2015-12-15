package jus.poc.prodcons.v3;

import jus.poc.prodcons.Message;


public class MessageX implements Message{
	private int idP;
	
	/**
	 * construit un message
	 * @param idProd identifiant du producteur qui écrit le message
	 */
	public MessageX(int idProd){
		this.setIdP(idProd);
	}
	
	/**
	 * donne l'identifiant du producteur qui est en train d'écrire un message
	 * @return l'identifiant du producteur
	 */
	public int getIdP() {
		return idP;
	}
	
	/**
	 * modifie le producteur qui écrit le message
	 * @param idP : identifiant du producteur qui écrit le message
	 */
	public void setIdP(int idP) {
		this.idP = idP;
	}

}
