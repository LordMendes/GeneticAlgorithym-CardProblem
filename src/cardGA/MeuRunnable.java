package cardGA;

public class MeuRunnable implements Runnable{
	public Ga a;
	
	@Override
	public void run() {
		a = new Ga();
		a.run();
		
	}

}
