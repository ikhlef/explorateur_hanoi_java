package explorateurs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
public class Graphe {

	private ArrayList<Sommet> liste_Sommet; //liste des sommets possible
	private ArrayList<Sommet> liste_parcours; // liste des sommets du parcours en largeur
	private ArrayList<Sommet> liste_plus_court_chemin;
	private byte [] tableau_visite;   // tableau des sommets visités
	private int [] tableau_distance; // tableau des distance de chaque sommet valide du sommet de départ
	private Sommet [] tableau_pere_sommet; // tableau des peres de chaque sommet du parcours.
	private int n,taille, capacite;
	int [] VNV=null;
	
	public Graphe(int ne, int cap){
		liste_Sommet = new ArrayList<Sommet>();
		liste_parcours = new ArrayList<Sommet>();
		liste_plus_court_chemin=new ArrayList<Sommet>();
		n=ne;
		taille = 6*ne;
		capacite=cap;
		tableau_visite = new byte [taille];
		tableau_distance = new int [taille];
		tableau_pere_sommet = new Sommet[taille];
		for(int i=0;i<taille;i++){
			tableau_visite[i]=0;    //initialiser le tabelau de visite a 0 
			tableau_distance [i]= 100000;  //initialiser le tableau des distances a l infini 
			tableau_pere_sommet[i]=null;
		}
		liste_Sommet = creer_configuration();
	}
	
	
	public ArrayList<Sommet> getListe_Sommet(){
		return liste_Sommet;
	}
	
	public ArrayList<Sommet> getListe_Parcours(){
		return liste_parcours;
	}
	
	public int getN(){return n;}
	public int getSize(){return liste_Sommet.size();}
	public int getCapacite(){return capacite;}
	public byte [] getTab_VNV(){ return tableau_visite;}
	public int [] getTableau_distance(){return tableau_distance;}
	public Sommet [] getTableau_pere_sommet(){return tableau_pere_sommet;}
	public String Distance_tab(int tab[]){
		String chaine= "[";
		for(int c=0;c<tab.length;c++){
			chaine=chaine +","+ tab[c];
		}
		chaine=chaine+"]";
		return chaine;
	}
	
	// fonction qui renvoie la liste des configurations valides possible pour n explorateurs et n adorateurs
	public ArrayList<Sommet> creer_configuration(){
		for(int i=0;i<=getN();i++){
			for(int j=0;j<=getN();j++){
				if(i==0){
						if(j==0){
							liste_Sommet.add(new Sommet(i,j,'R'));
						}else{
							liste_Sommet.add(new Sommet(i,j,'I'));
							liste_Sommet.add(new Sommet(i,j,'R'));
						}
				}else if(i==j){
						if(j!=getN()){
							liste_Sommet.add(new Sommet(i,j,'I'));	
							liste_Sommet.add(new Sommet(i,j,'R'));
						}else{
							liste_Sommet.add(new Sommet(i,j,'I'));	
							
						}
				} else if(i==getN()){
					liste_Sommet.add(new Sommet(i,j,'I'));
					liste_Sommet.add(new Sommet(i,j,'R'));
				} 
			}
		}	
		return liste_Sommet;	
	}
	
// fonction qui renvoie la liste des sommets voisins valides d'un sommet s
 public ArrayList<Sommet> voisin_valide(Sommet s){
	ArrayList<Sommet> voisin_valide = new ArrayList<Sommet>();
		 
 for(Sommet l : getListe_Sommet()){ 
		
	 if(s!=null && s.getPosition()!=l.getPosition()){
	  
			if(s.getPosition()=='R' ){
				if((l.getNe()-s.getNe()==0 && (l.getNa() - s.getNa() <=getCapacite() && (0 < l.getNa() - s.getNa())))||
						(((0<l.getNe()-s.getNe())&&((l.getNe()-s.getNe())<=getCapacite())) && ((l.getNa() - s.getNa() <= (getCapacite() - (l.getNe() -s.getNe()))&&(0<=l.getNa() - s.getNa()) )))){
					voisin_valide.add(l);
				}
			}else{
				if((s.getNe()-l.getNe()==0 && (s.getNa() - l.getNa() <=getCapacite() && (0 < s.getNa() - l.getNa())))||
						(((0<s.getNe()-l.getNe())&&((s.getNe()-l.getNe())<=getCapacite()))&&((s.getNa() - l.getNa() <= (getCapacite() - (s.getNe() -l.getNe()))&&(0<=s.getNa() - l.getNa()))))){
					voisin_valide.add(l);
				}
			}
		}
 }
return voisin_valide;
}
    // récupération d'un sommet non visté de la liste des sommets voisins du sommet passé en parametre,
 	// c'est comme la fonction choisir voisin non visité dans le parcours en largeur
   public Sommet choisir_voisin_non_visite(Sommet s, byte[] tab){
    	Sommet z = null; int i=0;
	    ArrayList<Sommet> list_vois = voisin_valide(s);
	   
	    if(!list_vois.isEmpty()){
		   while(tab[list_vois.get(i).getIndice()]==1){    
			   i++;
	   		}
	   		z=list_vois.get(i);
	   		tab[z.getIndice()]=1;    // marquer le sommet choisi comme sommet vistité
    	return z;
	   }
	   return null;
    }
   
   
    // traversée : fonction qui renvoie la plus courte chaine, on utilisant un parcours en largeur et un tableau de pere. 
	public ArrayList<Sommet> traversee(Graphe g, Sommet begin){
		int indice=0; // pour recuperer le pere du sommet d'arrivee (n,n,I)
		liste_parcours.add(begin);   // ajout du sommet initial a liste du parcours
		 getTab_VNV()[begin.getIndice()]=1;
		 tableau_distance[begin.getIndice()]=0;  //initialiser la distance du sommet initial à 0
		 tableau_pere_sommet[begin.getIndice()]=null; // par definition le pere du sommet initial est null.
		 Files F = new Files(1000);  // instanciation de la file des sommets visités 
		 
		 VNV= new int[g.getListe_Sommet().size()];// tableau de nombre de sommet non visité pour chaque sommet
		 for(Sommet x : g.getListe_Sommet()){  
			VNV[x.getIndice()]= g.voisin_valide(x).size(); // initialisation du tableau des degrés de chaque sommet
		}
		
		for(Sommet s : g.voisin_valide(begin)){            // décrémenter le degres de chaque sommet voisin du sommet initial        
			VNV[s.getIndice()]-=1;
		}
		
		if(VNV[begin.getIndice()]>0){
			F.enfiler(begin);                                 // enfiler le sommet initial dans la file s il est ouvert
		}
		Sommet z ;
		while(!F.vide()){
			while (F.tete()!=null && VNV[F.tete().getIndice()]==0){
				F.defiler();	
			}
			
			 z = choisir_voisin_non_visite(F.tete(), getTab_VNV());  //choisir un sommet voisin non visité du sommet tete de file
			if(z!=null) {
			liste_parcours.add(z);
			tableau_distance[z.getIndice()]=tableau_distance[F.tete().getIndice()]+1;
			tableau_pere_sommet[z.getIndice()]=F.tete();
			
			if(z.equals(liste_Sommet.get(taille-1))){
				indice= z.getIndice();
				liste_plus_court_chemin.add(0, z);; // ajout du sommet d'arrivee a la liste pcc
			}
			}
			
			for(Sommet t : g.voisin_valide(z)){
				VNV[t.getIndice()]-=1;
			}
			if( z!=null && VNV[z.getIndice()]>0){	
				F.enfiler(z);
			}
		}
		// construction de la plus courte chaine
		while(tableau_pere_sommet[indice]!=null){
			liste_plus_court_chemin.add(0,tableau_pere_sommet[indice]); // ajout du sommet pere de chaque sommet, jusqu'au 1er sommet null, qui est par initialisation le sommet du départ
			indice=tableau_pere_sommet[indice].getIndice();           // l'ajout ce fait toujours en debut de liste, jusqu'a l'ajout du sommet du depart qui n'a pas de pere
		}
	
		
		//System.out.println ("la taille du tableau des distances est " + tableau_distance.length+ "et la tailles de la liste parcours est :"+getListe_Parcours().size());
		System.out.println ("la taille de la plus courte chaine est  " + ((liste_plus_court_chemin.size())-1) );
		System.out.println ("le nombre d'aretes " + ((getListe_Parcours().size())-1));
		return liste_plus_court_chemin;
		//return getListe_Parcours();
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		Graphe gr=null; PrintWriter ecrivain;
		long debut, fin;
		double []temps;
		String fic="C:/Users/foufi2012/workspace/IKHLEF_projet_LI311/foufa.txt";
		Scanner sc = new Scanner(System.in);
		System.out.println("veuillez saisir le nombre d'exploratuers");
		int nbre = sc.nextInt();
		System.out.println("je viens de lire" + nbre + "explorateurs");
		temps= new double[nbre+1];
		
		for(int i=0;i<=nbre;i++){
			if(4<2*i){
				System.out.println("une plus courte chaine possible pour (n="+i+ ",k= 4) est :");
				 
				gr = new Graphe(i,4); 
				debut=System.currentTimeMillis(); 
				ArrayList<Sommet> t=gr.traversee(gr,gr.getListe_Sommet().get(0));
				fin=System.currentTimeMillis();
				System.out.println(t);
				temps[i]=fin-debut;
				Sommet.SetCpt(0);		
			}
		}
	     ecrivain =  new PrintWriter(new BufferedWriter(new FileWriter(fic)));
	     for(int a =0; a<temps.length;a++)
	     ecrivain.println(a+" "+temps[a]);
	     ecrivain.close();
	}
}
