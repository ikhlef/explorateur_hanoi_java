package explorateurs;

public class Sommet {
	private int ne;
	private int na;
	private char position;
	private static int cpt=0;
	private int indice=0;
	
	public Sommet(){
		super();
	}
	
	public Sommet( int a, int b, char c){
		ne=a;
		na=b;
		position=c;
		indice=cpt;
		cpt++;
	}
	public Sommet(Sommet s){
		ne= s.ne;
		na=s.na;
		position=s.position;
		indice= s.getIndice();
	}
	
	public int getNe(){
		return ne;
	}
	public int getNa(){
		return na;
	}
	public char getPosition(){
		return position;
	}
	public int getIndice(){
		return this.indice;
	}
	public static void SetCpt(int compteur){
		 cpt= compteur;
	}
	public String toString(){
		return "(" + ne + "," + na + "," + this.getPosition() + ")";
	}
	public boolean equals(Sommet s){
		return (this.na==s.getNa() && this.ne == s.getNe() && this.position == s.getPosition() && this.indice == s.getIndice() );
	}
	public boolean barque (char d){
		if(d=='I')
			return true;
		return false;
	}
}
