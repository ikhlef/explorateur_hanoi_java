package hanoi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;



public class Hanoi {
	
	private int N;
	private int[] p, pf, pi;
	
	public Hanoi(int N){
		this.N = N;
		p = new int[N];
		pi=new int[N];
		for(int i=0; i<N-1; i++){
			pi[i] = 1;
		}
		pf=new int[N];
		pf[N-1]=3;
	}
	
	public void  hanoi(int N, int piquet1, int piquet2){
		if(N>0){
			hanoi(N-1, piquet1, 6-piquet1-piquet2);
			
			bouger(piquet1, piquet2);
			hanoi(N-1, 6-piquet1-piquet2, piquet1);
		}
		
	}

	public void bouger(int origine, int destination) {
		// TODO Auto-generated method stub
		
		//System.out.println(origine +"->" + destination);
		
	}
	//calcul de pi(d)
	public int getPosInitiale(int d){
			return pi[d];
	}
	
	
	
	public  int[] getPositionFinale(int N){
		//Initiaisation de pf[n]
		//pf[N-1] = 3;
		
		for(int i=N-2; i>=0; i--){
		
			if(pi[i+1] == pf[i+1]){
				pf[i]=pf[i+1];
				
			//System.out.println(" " + pf[i] + "init " +pi[i]);	
			}else{
				pf[i]=6-getPosInitiale(i+1)-pf[i+1];
			System.out.println(" " + pf[i] + "init " +pi[i]);
			}
		}
		return pf;
			
	}
	/*
	public void hanoi2(int n){
		int[] pff = getPositionFinale(n);
		for(int i=1; i<n-1; i++){
			if(pi[i] != pff[i]){
				bouger(pi[i], pff[i]);
				hanoi(i-1, pff[i-1] , pff[i]);
		    }else{
		    	hanoi2(n-1);
		    }
		}
	}
	
*/	public void hanoi2(int n,int depart, int arrivee){
		int[] pff = getPositionFinale(n);
		if(n>0)
		if(pff[n-1]==arrivee){
			System.out.println("rien a faire, le disque est a la bonne position");
			hanoi2(n-1,depart,arrivee);
		}else{
			   hanoi2(n-1,depart,6-depart-arrivee); 
				bouger(pf[n], arrivee);
			//	hanoi(n-1 ,depart, arrivee);
		   		hanoi(n-1, 6-depart-arrivee, depart);
	
		}
	}
	
	public static void main(String[] args) throws IOException{
		PrintWriter ecrivain;
		long debut, fin;
		double []temps;
		String fic="C:/Users/foufi2012/workspace/IKHLEF_projet_LI311/hanoi2.txt";
		Scanner sc = new Scanner(System.in);
		System.out.println("entrez le nombre de disques");
		int nbre = sc.nextInt();
		temps= new double[nbre+1];
		//Hanoi h = new Hanoi(nbre);
		for(int i=1;i<nbre-1;i++){
			if(i>0){
					Hanoi h = new Hanoi(i);		
				debut=System.currentTimeMillis(); 
				//h.hanoi2(nbre);
				h.hanoi2(i, 1,3);
				fin=System.currentTimeMillis();
 				temps[i]=fin-debut;
 				System.out.println("voici le ieme passage de boucle n = " + i);
				//Sommet.SetCpt(0);		
			}
		}
	     ecrivain =  new PrintWriter(new BufferedWriter(new FileWriter(fic)));
	     for(int a =0; a<temps.length;a++)
	     ecrivain.println(a+" "+temps[a]);
	     ecrivain.close();
	

//		Hanoi h = new Hanoi(50);
		//h.getPositionFinale(4);
		//h.hanoi2(0);
		//h.hanoi(4, 1, 3);
		//System.out.println(h.getPositionFinale(4));
	
	}
} 
	
	
	

					