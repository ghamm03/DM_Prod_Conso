package jus.poc.prodcons.v3;

public class Semaphore {

	protected int res;

	public Semaphore(int val){
		res = val;
	}

	/**
	 * Wait	décrémente le sémaphore
	 * @throws InterruptedException
	 */
	public synchronized void p() throws InterruptedException{
		res--;
		if(res<0){
			wait();
		}
	}

	/**
	 * Signal incrémente le sémaphore
	 */
	public synchronized void signal(){
		res++;
		if(res<=0){
			notify();
		}
	}

}
