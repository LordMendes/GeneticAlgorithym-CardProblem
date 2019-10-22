package cardGA;

import java.util.Random;

public class Ga {
	private double SUMTARG = 36;
    private double PRODTARG = 360;
	int POP = 40;
	int LEN = 10;
	int GEN = 25;
	double mR = 0.1;
	double cR = 0.75;
	int CHILDREN = 40;
	Random r = new Random();
	public int[][] genotipo = new int[POP][LEN];
	double[] rank = new double[POP];
	int[] elite = new int[LEN];
	
	void init_pop(){
		
		for(int i=0 ; i < POP ; i++) {
			for(int j=0; j < LEN ; j++) {
				if(r.nextDouble() < 0.5) {
					genotipo[i][j] = 0;
				}else{
					genotipo[i][j] = 1;
				}
			}
		}
	}
	Double fitness(int p) {
		
		int sum=0;
		double mult=1;
		double scaled_sum_error, scaled_prod_error, combined_error;
		//1- BARALHO DA SOMA 2- BARALHO DA MULTIPLICAÇÃO	
		for(int i = 0 ; i<LEN;i++) {
			if(genotipo[p][i] == 1){
				sum+=i+1;
			}else {
				mult*=(i+1);
			}
		}
		scaled_sum_error = (sum - SUMTARG) / SUMTARG;
        scaled_prod_error = (mult - PRODTARG) / PRODTARG;
        combined_error = Math.abs(scaled_sum_error) + 
                         Math.abs(scaled_prod_error);
		
		return combined_error;
	}
	
	void selection (int[][]pop) {
		
		int[]aux1 = null;
		double aux2;
		for(int i = 0; i < POP ; i++) {
				rank[i] = fitness(i);
		}
		for(int i = 0 ; i < POP ; i++) {
			for(int j = 1 ; j < POP ; j++) {
				if(rank[i] > rank[j]) {
					
					aux1 = pop[j];
					pop[j] = pop[i];
					pop[i] = aux1;
					
					aux2 = rank[j];
					rank[j] = rank[i];
					rank[i] = aux2;
					
				}
			}
		}				
	}
	
	int tournament() {
		
		double c1,c2;
		
		c1 = r.nextDouble();
		c2 = r.nextDouble();
		
		if(fitness((int) (POP*c1)) < fitness((int) (POP*c2))) {
			return ((int) (POP*c1));
		}else {
			return ((int) (POP*c2));
		}
			
	}
	
	int roulette() {
		
		int p = 0;
		double sum=0;
		double c;
		
		for(int i = 0 ; i < POP ; i++) {
			sum+=rank[i];
		}
		
		c = r.nextDouble()*sum;
		
		if(c > 0 && c < rank[0] ) {
			return 0;
		}else {
			for(int i = 0 ; i < POP-1; i++) {		
				if(rank[i] > 0 && c < rank[i+1] ) {
					return (i+1);
				}
			}
		}
		return p;
		
	}
	void printaBest(int n) {
		
		double sum=0,mult=1;
			for(int j=0; j < LEN ; j++) {
				System.out.print(genotipo[0][j]);
				if(genotipo[0][j] == 1){
					sum+=j+1;
				}else {
					mult*=(j+1);
				}
			}
			System.out.println(" -> Score: "+rank[0]+" Soma : " + sum + " Mult : " + mult);		
			
		
		System.out.println("Geração : "+ n );
	}

	
	
	
	void printaAll(int n) {
		for(int i=0 ; i < POP ; i++) {
			for(int j=0; j < LEN ; j++) {
				System.out.print(genotipo[i][j]);
			}
			//PRINTADA BRABA
			double sum=0,mult=1;
			for(int i1 = 0 ; i1<LEN;i1++) {
				if(genotipo[i][i1] == 1){
					sum+=i1+1;
				}else {
					mult*=(i1+1);
				}
			}
			System.out.println(" -> Score: "+rank[i]+" Soma : " + sum + " Mult : " + mult);
			
			
		}
		System.out.println("Geração : "+ n );
	}
	
	void run() {
		
		init_pop();
		int n = 0;
		int x1, x2 ,mp;
		int[][] aux = new int[POP][LEN];
		int cut;
		double m,c;
		//boolean ext = false; //QUANDO ESTACA
		selection(genotipo);
		
		
		while(n<GEN) {
					
			
			if(n==1) {
				elite = genotipo[0];
			}
			
			for(int i = 0 ; i < POP-1 ; i++) {
				m = r.nextDouble();
				mp = r.nextInt(LEN);
				c = r.nextDouble();
				cut = r.nextInt(LEN);
				//x1 = roulette();
				//x2 = roulette();
				x1 = tournament();
				x2 = tournament();
				if(c < cR) {
					for(int j = 0 ; j < LEN ; j++) {
						
						if(j<cut) {
							aux[i][j] = genotipo[x1][j];					
						}else {
							aux[i][j] = genotipo[x2][j];
						}
						
					}
				}
				if(m < mR) {
					if(genotipo[x1][mp] == 0) {
						genotipo[x1][mp] = 1;
					}else {
						genotipo[x1][mp] = 0;
					}
				}
			}
			genotipo[POP-1] = elite;
			selection(aux);
			
			for(int i = 0 ; i < POP ; i++) {
				genotipo[i]=aux[i];
			}
			n++;
		printaAll(n);	
		}
		//printaBest(n);
		
		
	}
	
	public static void main(String[] args) {
		
		Ga a = new Ga();
		
		a.run();
		

	}
	
		
}
