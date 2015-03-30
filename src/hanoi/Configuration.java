package hanoi;


public class Configuration {
	private static int cpt=0;
	private int indice=0;
	
	private Pile p1, p2, p3;
	public Configuration (){
		super();
	}
	public Configuration(int n){
		p1=new Pile(n);
		p2=new Pile(n);
		p3= new Pile(n);
		indice=cpt;
		cpt++;
	
	}
	public Configuration(Pile p4, Pile p5, Pile p6){
		p1=p4.clone();
		p2=p5.clone();
		p3= p6.clone();
		indice=cpt;
		cpt++;
		
	}
	public Configuration (Configuration c){
		p1=c.p1;
		p2=c.p2;
		p3=c.p3;
		indice=c.getIndice();
		cpt++;
	}
	
	public int getIndice(){
		return indice;
	}
	public void setIndice(int a){
		this.indice=a;
	}
  public Pile getP1(){return p1;}
  public void setP1(Pile p){p1=p;}
  public Pile getP2(){return p2;}
  public void setP2(Pile p){p2=p;}
  public Pile getP3(){return p3;}
  public void setP3(Pile p){p3=p;}
  public boolean equals(Configuration c){
	  return (p1.equals(c.getP1())&& p2.equals(c.getP2())&& p3.equals(c.getP3()));
  }
  public Configuration clone(){
	  Configuration c = new Configuration() ;
	  c.p1=p1.clone();
	  c.p2 = p2.clone();
	  c.p3=p3.clone();
	  return c;
  }
  
   public String toString(){
	   return "["+ p1.toString() +", "+p2.toString()+", "+ p3+ "]";
   }
   public boolean vide(){
	   return (p1.vide() || p2.vide() || p3.vide());
   }

}
