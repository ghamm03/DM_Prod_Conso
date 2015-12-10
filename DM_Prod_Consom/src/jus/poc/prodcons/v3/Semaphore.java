package jus.poc.prodcons.v3;

public class Semaphore {

	protected int res;

	public Semaphore(int val){
		res = val;
	}

	/**
	 * Wait	d�cr�mente le s�maphore
	 * @throws InterruptedException
	 */
	public synchronized void p() throws InterruptedException{
		res--;
		if(res<0){
			wait();
		}
	}

	/**
	 * Signal incr�mente le s�maphore
	 */
	public synchronized void signal(){
		res++;
		if(res<=0){
			notify();
		}
	}

}
