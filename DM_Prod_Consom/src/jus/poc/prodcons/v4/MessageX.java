package jus.poc.prodcons.v4;

import jus.poc.prodcons.Message;


public class MessageX implements Message{
	private int idP;
	private int nbCopy;
	private int nbconso;

	/**
	 * construit un message
	 * @param idProd identifiant du producteur qui écrit le message
	 */
	public MessageX(int idProd,int copy){
		this.setIdP(idProd);
		this.setNbCopy(copy);
		nbconso = 0;
	}

	/**
	 * incremente le nombre d'exemplaire d'un message consomme
	 */
	public void consumExemplaire(){
		nbconso++;
	}

	/**
	 *	Indique si il reste des exemplaires disponibles
	 * @return true si il reste des exemplaires a consomme
	 */
	public boolean noExemplaire(){
		return (getNbCopy()-nbconso)==0;
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

	/**
	 * Getter du nombre d'exemplaire max
	 * @return nombre d'exemplaires max
	 */
	public int getNbCopy() {
		return nbCopy;
	}

	/**
	 *	Setter du nombre d'exemplaire max
	 * @param nbCopy nombre d'exemplaire max
	 */
	public void setNbCopy(int nbCopy) {
		this.nbCopy = nbCopy;
	}

}
