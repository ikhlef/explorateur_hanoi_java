package hanoi;

public class FileHanoi {

		public static final int MAX= 10000;
		private Configuration [] tab;
		private int debut, fin, nombre;
		public FileHanoi (){
			tab= new Configuration[MAX];
			debut=fin=nombre=0;
		}
		public FileHanoi (int n){
			tab= new Configuration[n];
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
		
		public void enfiler( Configuration x){
			nombre++;
			tab[fin]=x;
			fin=(fin+1)%tab.length;
		}
		public Configuration tete(){
			return tab[debut];
		}
		public Configuration defiler(){
			Configuration tmp;
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
	

