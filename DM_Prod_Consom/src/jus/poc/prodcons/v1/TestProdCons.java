package jus.poc.prodcons.v1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

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
		nbProd = 1;
		nbCons = 10;
		tempsMoyenConsommation = 10;
		deviationTempsMoyenConsommation = 1;
		nbBuffer = 1;
		Tampon tamp = new ProdCons(nbBuffer);

		for(int i=0; i<nbCons; i++){
			Consommateur c = new Consommateur(observateur, tempsMoyenConsommation, deviationTempsMoyenConsommation, tamp);
			consos.put(c.identification(),c);
			c.start();
		}
		for(int i=0; i<nbProd; i++){
			Producteur p = new Producteur(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		}
	}

}
