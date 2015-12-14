package jus.poc.prodcons.v5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;
import jus.poc.prodcons.Tampon;

public class TestProdCons extends Simulateur{


	public int nbProd;
	public int nbCons;
	public int nbBuffer;
	public int tempsMoyenProduction;
	public int deviationTempsMoyenProduction;
	public int tempsMoyenConsommation;
	public int deviationTempsMoyenConsommation;
	public int nombreMoyenDeProduction;
	public int deviationNombreMoyenDeProduction;
	public int nombreMoyenNbExemplaire;
	public int deviationNombreMoyenNbExemplaire;
	public HashMap<Integer,Consommateur> consos = new HashMap<Integer, Consommateur>();
	public HashMap<Integer, Producteur> prods = new HashMap<Integer, Producteur>();
	public static int prodActif = 0;
	public static boolean end = false;

	public TestProdCons(Observateur observateur) {
		super(observateur);
	}

	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();
	}

	protected void init(String file) throws InvalidPropertiesFormatException, FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Properties properties = new Properties();
		//properties.loadFromXML(ClassLoader.getSystemResourceAsStream(file));
		properties.loadFromXML(new FileInputStream(file));
		String key;
		int value;
		Class<?> thisOne = getClass();
		for(Map.Entry<Object,Object> entry : properties.entrySet()) {
			key = (String)entry.getKey();
			value = Integer.parseInt((String)entry.getValue());
			System.out.println("key " + key+ " valeurs : "+value);
			thisOne.getDeclaredField(key).set(this,value);

		}
	}

	@Override
	protected void run() throws Exception{
		this.init("src/jus/poc/prodcons/options/option1.xml");
		Tampon tamp = new ProdCons(nbBuffer);
		observateur.init(nbProd, nbCons, nbBuffer);
		for(int i=0; i<nbCons; i++){
			Consommateur c = new Consommateur(observateur, tempsMoyenConsommation, deviationTempsMoyenConsommation, tamp);
			consos.put(c.identification(),c);
			observateur.newConsommateur(c);
			c.start();
			System.out.println("consommateur : " + c.identification());
		}
		for(int i=0; i<nbProd; i++){
			Aleatoire nb_mess = new Aleatoire(nombreMoyenDeProduction,deviationNombreMoyenDeProduction);
			Producteur p = new Producteur(observateur, tempsMoyenProduction, deviationTempsMoyenProduction,nb_mess.next(),tamp);
			prods.put(p.identification(), p);
			observateur.newProducteur(p);
			TestProdCons.prodActif++;
			p.start();
			System.out.println("producteur : " + p.identification()+" nb mess "+p.nb_message_max);
		}

	}

}
