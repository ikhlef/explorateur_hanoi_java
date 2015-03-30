package explorateurs;

public class Files {

	public static final int MAX= 100;
	private Sommet [] tab;
	private int debut, fin, nombre;
	public Files (){
		tab= new Sommet[MAX];
		debut=fin=nombre=0;
	}
	public Files (int n){
		tab= new Sommet[n];
		debut=fin=nombre=0;
	}
	public boolean vide(){
		return (nombre==0);
	}
	public boolean plein(){
		return(nombre==tab.length);
	}
	public int taille(){
		return nombre;
	} 
	
	public void enfiler( Sommet x){
		nombre++;
		tab[fin]=x;
		fin=(fin+1)%tab.length;
	}
	public Sommet tete(){
		return tab[debut];
	}
	public Sommet defiler(){
		Sommet tmp;
		tmp=tab[debut];
		debut=(debut+1)%tab.length;
		nombre--;
		return tmp;
	}
	public void afficher(){
	int i= debut;
		for(int t=nombre;t!=0;t--){
			System.out.println(tab[i]+" ");
			i=(i+1)%MAX;
		}		
	}		
}

