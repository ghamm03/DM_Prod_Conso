package jus.poc.prodcons.v1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur{

	public TestProdCons(Observateur observateur) {
		super(observateur);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();
	}

	protected void init(String file) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InvalidPropertiesFormatException, IOException {
		Properties properties = new Properties();
		properties.loadFromXML(ClassLoader.getSystemResourceAsStream(file));
		String key;
		int value;
		Class<?> thisOne = getClass();
		for(Map.Entry<Object,Object> entry : properties.entrySet()) {
			key = (String)entry.getKey();
			value = Integer.parseInt((String)entry.getValue());
			thisOne.getDeclaredField(key).set(this,value);

		}
	}

	@Override
	protected void run() throws Exception{

	}

}
