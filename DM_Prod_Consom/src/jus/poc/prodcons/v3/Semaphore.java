package jus.poc.prodcons.v3;

public class Semaphore {

	protected int res;
	
	/**
	 * Constructeur semaphore
	 * @param val nombre de ressources
	 */
	public Semaphore(int val){
		res = val;
	}

	/**
	 * décrémente le sémaphore. Si le nombre de ressources est négatif , les threads sont mis en liste d'attente.
	 * @throws InterruptedException .
	 */
	public synchronized void p() throws InterruptedException{
		res--;
		if(res<0){
			wait();
		}
	}

	/**
	 * incrémente le sémaphore. Si le nombre de ressource est disponible, alors réveil des threads
	 */
	public synchronized void signal(){
		res++;
		if(res<=0){
			notify();
		}
	}

}
