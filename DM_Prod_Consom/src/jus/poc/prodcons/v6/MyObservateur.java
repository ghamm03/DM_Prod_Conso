package jus.poc.prodcons.v6;

import java.util.HashMap;
import java.util.HashSet;

import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;


public class MyObservateur{

	private int nbProd, nbConso, taille;
	private HashSet<_Producteur> prodList;
	private HashSet<_Consommateur> consList;
	private HashSet<Message> buffer;
	private HashMap<_Consommateur, Message> msgWaitConso; //Msg retire en attente de consommation
	private HashMap<_Producteur, Message> msgWaitDepot; //Msg produit en attente de depot


	public void	init(int nbproducteurs, int nbconsommateurs, int nbBuffers)throws ControlException{
		if(nbproducteurs<=0 || nbconsommateurs<=0 || nbBuffers <= 0)
			throw new ControlException(getClass(), "init");

		this.nbProd = nbproducteurs;
		this.nbConso = nbconsommateurs;
		this.taille = nbBuffers;

		prodList = new HashSet<_Producteur>();
		consList = new HashSet<_Consommateur>();
		buffer = new HashSet<Message>();

		msgWaitConso = new HashMap<_Consommateur, Message>();
		msgWaitDepot = new HashMap<_Producteur, Message>();
	}

	public void newProducteur(_Producteur p)throws ControlException{
		if(p!=null){
			//Trop de producteur
			if(this.prodList.size()+1>nbProd)
				throw new ControlException(getClass(), "newProducteur");
			prodList.add(p);
		}
		else
			throw new ControlException(getClass(), "newProducteur");
	}

	public void newConsommateur(_Consommateur c) throws ControlException{
		if(c!=null){
			//Trop de consommateur
			if(this.consList.size()+1>nbConso)
				throw new ControlException(getClass(), "newConsommateur");
			consList.add(c);
		}
		else
			throw new ControlException(getClass(), "newConsommateur");
	}

	public void retraitMessage(_Consommateur c, Message m) throws ControlException{
		if(c==null || m==null)
			throw new ControlException(getClass(), "retraitMessage");
		//Si le message n'est pas le buffer
		if(!buffer.remove(m))
			throw new ControlException(getClass(), "retraitMessage");
		msgWaitConso.put(c, m);
	}

	//Un message ne peut etre consomme que si il a ete enleve du buffer
	public void consommationMessage(_Consommateur c, Message m, int tempsDeTraitement) throws ControlException{
		//Si Valeurs invalides, consommateur n'est pas dans la liste, message encore dans le buffer
		if(c==null || m==null || tempsDeTraitement<=0 || !consList.contains(c) || buffer.contains(m))
			throw new ControlException(getClass(), "consommationMessage");
		msgWaitConso.remove(c, m);

	}

	public void depotMessage(_Producteur p, Message m) throws ControlException{
		//Si valeurs invalides, producteur n'est pas dans la liste
		if(p==null || m==null || !prodList.contains(p))
			throw new ControlException(getClass(), "depotMessage");
		msgWaitDepot.remove(p, m);
		buffer.add(m);
	}

	public void productionMessage(_Producteur p, Message m, int tempsDeTraitement)throws ControlException{
		//Si valeurs invalides, producteur n'est pas dans la liste
		if(p==null || m==null || tempsDeTraitement<=0 || !prodList.contains(p))
			throw new ControlException(getClass(), "consommationMessage");
		msgWaitDepot.put(p, m);
	}

}
