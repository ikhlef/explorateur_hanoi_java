package hanoi;


public class Pile {
	
		public static final int MAX=300000;
		private int top;  //indice du 1er element de la pile 
		private int[]tab ;
		
		public Pile(int n){
		top=-1;
		tab = new int[n];
		}
		
		public Pile(){
		top=-1;
		tab = new int[MAX];
		}
		public Pile(Pile p){
			top=p.getTop();
			tab=p.getTab().clone();
		}
		public int getTop(){return top;}
		public void empiler(int x) {tab[++top] = x;}
		
		public int depiler() {
			int k=tab[top];
			tab[top]=0;
			//tab[top--];
			top--;
			return(k);}
		
		public boolean vide() {return(top == -1);}
		public boolean pleine() {return(top == tab.length-1);}
		public int getTaille(){return tab.length;} 
	    public int [] getTab(){return tab;}
	    public int getTete(){return tab[top];}
		
	    public Pile getPile(){return this;}
		
		public void setPile(Pile p){this.tab = p.getTab();
			this.top=p.top;
		}
	    public boolean validepile(){
			int v1,v2,i;
			boolean ok=true;
			if(getTab().length==0 || getTab().length==1){
				return true;
			}
			if(getTab().length==2){
				if(getTab()[0]>getTab()[1]){
					return true;
				}
			
			}
			i=2; v1 = this.getTab()[0]; v2 = this.getTab()[1];
			while(i<this.getTaille()){
				if(v1>v2){
					v1=v2;
					v2=this.getTab()[i];
					i++;
				}else{
					return false;}
			}
			return ok;
		}
	    
		
	    public void initialiser(int i){
	    	while(!this.pleine()){
	    		this.empiler(i);
	    		i--;
	    	}
	    }
	    public boolean equals(Pile p){
			if(tab.length==p.getTab().length){
				for(int i=0; i<tab.length;i++){
					if(tab[i]!=p.getTab()[i]){
						return false;
					}
				}
			}
			return true;
		}
	 public String toString(){
		 String chaine="[";
		 for(int i=0;i<tab.length;i++){
			 chaine+=tab[i]+",";
		 }
		 return chaine + "]";
	 }
	public Pile clone(){
		Pile p = new Pile();
		p.top = this.top;
		p.tab= this.tab.clone();
		return p;
	}
}
