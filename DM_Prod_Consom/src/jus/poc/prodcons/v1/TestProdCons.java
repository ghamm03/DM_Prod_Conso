package jus.poc.prodcons.v1;
//prout
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {

	public TestProdCons(Observateur observateur){
		super(observateur);}

	@Override
	protected void run() throws Exception{
	}

	public static void main(String[] args){
		new TestProdCons(new Observateur()).start();
	}

}
