package cardGA;

public class MinhaThread extends Thread{
	
	public Ga a;
	int POP=40, LEN=10;
	final int THREAD = 10;
	
	public void run() {
		synchronized(this) {
		a = new Ga();
		a.run();
		this.notify();
		}
	}


}
