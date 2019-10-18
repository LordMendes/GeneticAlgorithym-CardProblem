package cardGA;

import java.util.ArrayList;

public class Principal {
	static int POP=40, LEN=10;
	static final int THREAD = 10;
	static int []teste = {1,2,3,4,5,6,7,8,9,10};
	
	public static void main(String[] args) {
		ArrayList<MinhaThread> threads = new ArrayList<MinhaThread>();	
		
		for (int i=0; i<THREAD; i++) {
			threads.add(new MinhaThread());
		}
		
		for(int i=0; i<THREAD; i++) {
			threads.get(i).start();
		}
	}
}

		

