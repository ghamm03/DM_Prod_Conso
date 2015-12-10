package jus.poc.prodcons.v1;

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


	public int nbProd = 2;
	public int nbCons = 10;
	public int nbBuffer = 5;
	public int tempsMoyenProduction = 10;
	public int deviationTempsMoyenProduction = 1;
	public int tempsMoyenConsommation = 10;
	public int deviationTempsMoyenConsommation = 1;
	public int nombreMoyenDeProduction = 5;
	public int deviationNombreMoyenDeProduction = 1;
	public int nombreMoyenNbExemplaire = 5;
	public int deviationNombreMoyenNbExemplaire = 3;
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
		//this.init("src/jus/poc/prodcons/options/optionv1.xml");
		Tampon tamp = new ProdCons(nbBuffer);
		for(int i=0; i<nbCons; i++){
			Consommateur c = new Consommateur(observateur, tempsMoyenConsommation, deviationTempsMoyenConsommation, tamp);
			consos.put(c.identification(),c);
			c.start();
			System.out.println("consommateur : " + c.identification());
		}
		for(int i=0; i<nbProd; i++){
			Aleatoire nb_mess = new Aleatoire(nombreMoyenDeProduction,deviationNombreMoyenDeProduction);
			Producteur p = new Producteur(observateur, tempsMoyenProduction, deviationTempsMoyenProduction,nb_mess.next(),tamp);
			prods.put(p.identification(), p);
			TestProdCons.prodActif++;
			p.start();
			System.out.println("producteur : " + p.identification()+" nb mess "+p.nb_message_max);
		}

	}

}
