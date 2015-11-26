package jus.poc.prodcons.v1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur{


	public int nbProd;
	public int nbCons;

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
			//if(affichage == 1){
			System.out.println("key " + key+ " valeurs : "+value);
			//}
			thisOne.getDeclaredField(key).set(this,value);

		}
	}

	@Override
	protected void run() throws Exception{
		//this.init("src/jus/poc/prodcons/options/optionv1.xml");

	}

}
