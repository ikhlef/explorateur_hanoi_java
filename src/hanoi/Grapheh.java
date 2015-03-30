package hanoi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import explorateurs.Files;
import explorateurs.Sommet;

public class Grapheh {
	
private ArrayList<Configuration> liste;
private byte [] tableau_visite;
private ArrayList<Configuration> liste_parcours;
ArrayList<Configuration> liste_plus_court_chemin;
private int taille;
private Configuration finale;
private int [] tableau_distance; // tableau des distance de chaque configuration valide de la configuration de départ
private Configuration [] tableau_pere_configuration; // tableau des peres de chaque configuration du parcours.
int [] VNV=null;
int taille_pile=0;
private static Scanner sc;
   public Grapheh(int n){
	   taille_pile=n;
	   liste_parcours = new ArrayList<Configuration>();
	   liste_plus_court_chemin=new ArrayList<Configuration>();	
	   liste=new ArrayList<Configuration>(n*n*n);
	   taille=(int) Math.pow(3, n);
	   tableau_visite = new byte[taille];
	   tableau_distance = new int [taille];
		tableau_pere_configuration = new Configuration[taille];
		for(int i=0;i<taille;i++){
			tableau_visite[i]=0;    //initialiser le tabelau de visite a 0 
			tableau_distance [i]= 10000000;  //initialiser le tableau des distances a l infini 
			tableau_pere_configuration[i]=null;
		}
		creer_graphe();
   }
  public int getTaille(){
	  return taille;
  }
  public byte [] getTab_VNV(){ return tableau_visite;} 
  public Configuration getFinale(){return finale;}
  public int[] getTableau_distance(){return tableau_distance;}
  
  
  public void creer_graphe(){
	  	Pile a=new Pile(taille_pile);
	  	Pile b =new Pile(taille_pile);
	  	Pile c =new Pile(taille_pile);
	
	a.initialiser(taille_pile);
	Configuration coo = new Configuration(a,b,c);
	coo.setIndice(0);
	this.getListe().add(coo);
	  int j=0;
	  while(j<getTaille()){
			ArrayList<Configuration> liste_v= voisin(this.getListe().get(j));
		  if(liste_v!=null){
		   for(Configuration co : liste_v){
			   int oui=0;
		    	for(Configuration d : this.getListe()){
		    		if(d.equals(co)){
		    			oui=1;
		    			}
		    	}
		    	if(oui==0){
		    		co.setIndice(this.getListe().size());
		    		this.getListe().add(co);
		    	}
		    }
		  }
		   j++;
	   }
	  for(Configuration f : getListe()){
		  if(f.getP1().vide() && f.getP2().vide() && f.getP3().validepile()){
			  finale= f;
			  break;
		  }
	  }
   }
   public ArrayList<Configuration> getListe(){return liste;}

   // la liste des voisins possible d'une configuration
   public ArrayList<Configuration> voisin(Configuration c){
	   ArrayList<Configuration> liste_voisin= new ArrayList<Configuration>();  
	  if(c!=null){
	   if(!c.getP1().vide() && (c.getP2().vide() || c.getP1().getTete()<c.getP2().getTete())){
		   Configuration k = c.clone();
		   Pile p11 = k.getP1(); Pile p22 = k.getP2();	//Pile p33 = k.getP3();
			  int a = p11.depiler();
			  p22.empiler(a);
			  liste_voisin.add(k);
	   }
	   if(!c.getP1().vide() && (c.getP3().vide() || c.getP1().getTete()<c.getP3().getTete())){
			  Configuration k = c.clone();
		       
		   Pile p11 = k.getP1(); Pile p22 = k.getP2();
		   Pile p33 = k.getP3();
			  int a = p11.depiler();
			  p33.empiler(a);
			  liste_voisin.add(k);
		}
		 if(!c.getP2().vide() && (c.getP1().vide() || c.getP2().getTete()<c.getP1().getTete())){
			   Configuration k = c.clone();
			   Pile p11 = k.getP1(); Pile p22 = k.getP2();	Pile p33 = k.getP3();
			  int a = p22.depiler(); 
			  p11.empiler(a);
				 liste_voisin.add(k);
		 }
		 
		  if(!c.getP2().vide() && (c.getP3().vide() || c.getP2().getTete()<c.getP3().getTete())){
			   Configuration k = c.clone();
			  Pile p11 = k.getP1(); 
			   Pile p22 = k.getP2();	Pile p33 = k.getP3();
			   int a = p22.depiler();
			  p33.empiler(a);
			  liste_voisin.add(k);
		   }
		  
		  if(!c.getP3().vide() && (c.getP2().vide() || c.getP3().getTete()<c.getP2().getTete())){
			   Configuration k = c.clone();
			  Pile p11 = k.getP1(); 
			   Pile p22 = k.getP2();	Pile p33 = k.getP3();
			  int a = p33.depiler();
			  p22.empiler(a);
			  liste_voisin.add(k);
		  }
		  if(!c.getP3().vide() && (c.getP1().vide() || c.getP3().getTete()<c.getP1().getTete())){
			   Configuration k = c.clone();
			  Pile p11 = k.getP1(); Pile p22 = k.getP2();
			  Pile p33 = k.getP3();
				  int a = p33.depiler();
				  p11.empiler(a);
				  liste_voisin.add(k); 
			  }
	  }
	  for(Configuration a : getListe()){
		  for(Configuration d : liste_voisin){
			  if(d.equals(a)){
				  d.setIndice(a.getIndice());
			  }
		  }  
	  }
		  return liste_voisin; 
	  }
/*  // la fonction qui renvoie les voisins valides d'une configuration.   
public ArrayList<Configuration> voisin_valide(Configuration c){
	   ArrayList<Configuration> voisinvalide= new ArrayList<Configuration>();
	   for( Configuration s : getListe()){
		   if(c!=null){
		    if(c.getP1().validepile() && c.getP2().vide() && c.getP3().vide()){
			   if(!s.getP1().vide() && s.getP3().vide() && !s.getP2().vide()){
				   Configuration t = c.clone(); Configuration t1 = s.clone();
				   t.getP1().depiler();  t1.getP2().depiler();
					     if(t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())&&t.getP3().equals(t1.getP3())){
					    voisinvalide.add(s); 
					     }			     
			   }
			   if(!s.getP1().vide() &&!s.getP3().vide() && s.getP2().vide()){
				   Configuration t = c.clone(); Configuration t1 = s.clone();
				   
				   int d= t.getP1().depiler(); int z = t1.getP3().depiler();
				     if(t.getP1().equals(t1.getP1()) && t.getP3().equals(t1.getP3())){
				    	 voisinvalide.add(s); 
				     }	     
		     }
		  }   
		 if(c.getP2().validepile() && c.getP1().vide() && c.getP3().vide()){
				   if(s.getP3().vide() && !s.getP1().vide()){
					   Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP2().depiler();t1.getP1().depiler();
						     if(t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
					    	 voisinvalide.add(s); 
						     }			     
				   }
				   if(!s.getP3().vide() && s.getP1().vide()){
					   Configuration t = c.clone(); Configuration t1 = s.clone();
					    t.getP2().depiler();  t1.getP3().depiler();
					     if(t.getP2().equals(t1.getP2()) && t.getP3().equals(t1.getP3())){
					    	 voisinvalide.add(s); 
					     }	     
			     }   
		}
		if(c.getP3().validepile() && c.getP1().vide() && c.getP2().vide()){
			   if(s.getP2().vide() && !s.getP1().vide()){
				   Configuration t = c.clone(); Configuration t1 = s.clone();		   
				   t.getP3().depiler();t1.getP1().depiler();
					     if(t.getP3().equals(t1.getP3()) && t.getP1().equals(t1.getP1())){
				    	 voisinvalide.add(s); 
					     }			     
			   }
			   if(!s.getP2().vide() && s.getP1().vide()){
				   Configuration t = c.clone(); Configuration t1 = s.clone();
				    t.getP3().depiler();  t1.getP2().depiler();
				     if(t.getP3().equals(t1.getP3()) && t.getP2().equals(t1.getP2())){
				    	 voisinvalide.add(s); 
				     }	     
		     }   
	   }
		if(c.getP1().vide()&&!c.getP3().vide() &&!c.getP2().vide()){
			if(c.getP2().getTete()>c.getP3().getTete()){
				if(s.getP1().vide() && !s.getP2().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP3().depiler();t1.getP2().depiler();
						     if(t.getP3().equals(t1.getP3()) && t.getP2().equals(t1.getP2())){
					    	 voisinvalide.add(s); 
						     }
				}
				if(!s.getP1().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP3().depiler();t1.getP1().depiler();
						     if(t.getP3().equals(t1.getP3()) && t.getP1().equals(t1.getP1())&&t.getP2().equals(t1.getP2())){
					    	 voisinvalide.add(s); 
						     }	
				}
				if(c.getP3().equals(s.getP3())&& !s.getP1().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP2().depiler();t1.getP1().depiler();
						     if(t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
					    	 voisinvalide.add(s); 
						     }
				}
	       }
			if(c.getP2().getTete()<c.getP3().getTete()){
				if(s.getP1().vide()&& !s.getP3().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP2().depiler();t1.getP3().depiler();
						     if(t.getP2().equals(t1.getP2()) && t.getP3().equals(t1.getP3())){
					    	 voisinvalide.add(s); 
						     }
				}
				if(!s.getP1().vide()&&c.getP3().equals(s.getP3())){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP2().depiler();t1.getP1().depiler();
						     if(t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
					    	 voisinvalide.add(s); 
						     }	
				}
				if(c.getP2().equals(s.getP2()) && !s.getP1().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP3().depiler();t1.getP1().depiler();
						     if(t.getP3().equals(t1.getP3()) && t.getP1().equals(t1.getP1())){
					    	 voisinvalide.add(s); 
						     }
				}
	       }
			
		}
		if(!c.getP1().vide()&&c.getP3().vide() &&!c.getP2().vide()){
			if(c.getP1().getTete()>c.getP2().getTete()){
				if(!s.getP3().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP1().depiler();t1.getP3().depiler();
						     if(t.getP1().equals(t1.getP1()) && t.getP3().equals(t1.getP3())&&t.getP2().equals(t1.getP2())){
					    	 voisinvalide.add(s); 
						     }
				}
				if(!s.getP3().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP2().depiler();t1.getP3().depiler();
						     if(t.getP2().equals(t1.getP2()) && t.getP3().equals(t1.getP3())&&t.getP1().equals(t1.getP1())){
					    	 voisinvalide.add(s); 
						     }	
				}
				if(c.getP3().equals(s.getP3())&& !s.getP1().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP2().depiler();t1.getP1().depiler();
						     if(t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
					    	 voisinvalide.add(s); 
						     }
				}
	       }
		  if(c.getP1().getTete()<c.getP2().getTete()){
				if(!s.getP2().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP1().depiler();t1.getP2().depiler();
						     if(t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())&&t.getP3().equals(t1.getP3())){
					    	 voisinvalide.add(s); 
						     }
				}
				if(!s.getP3().vide()&&c.getP2().equals(s.getP2())){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP1().depiler();t1.getP3().depiler();
						     if(t.getP3().equals(t1.getP3()) && t.getP1().equals(t1.getP1())){
					    	 voisinvalide.add(s); 
						     }	
				}
				if(c.getP1().equals(s.getP1()) && !s.getP3().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP2().depiler();t1.getP3().depiler();
						     if(t.getP2().equals(t1.getP2()) && t.getP3().equals(t1.getP3())){
					    	 voisinvalide.add(s); 
						     }
				}
	       }
			
		}	
		
		if(!c.getP1().vide()&&!c.getP3().vide() &&c.getP2().vide()){
			if(c.getP1().getTete()>c.getP3().getTete()){
				if(!s.getP1().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP3().depiler();t1.getP1().depiler();
						     if(t.getP1().equals(t1.getP1()) && t.getP3().equals(t1.getP3())&&t.getP2().equals(t1.getP2())){
					    	 voisinvalide.add(s); 
						     }
				}
				if(!s.getP2().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP3().depiler();t1.getP2().depiler();
						     if(t.getP2().equals(t1.getP2()) && t.getP3().equals(t1.getP3())&&t.getP1().equals(t1.getP1())){
					    	 voisinvalide.add(s); 
						     }	
				}
				if(c.getP3().equals(s.getP3())&& !s.getP2().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP1().depiler();t1.getP2().depiler();
						     if(t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
					    	 voisinvalide.add(s); 
						     }
				}
	       }
		  if(c.getP1().getTete()<c.getP3().getTete()){
				if(!s.getP2().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP1().depiler();t1.getP2().depiler();
						     if(t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())&&t.getP3().equals(t1.getP3())){
					    	 voisinvalide.add(s); 
						     }
				}
				if(!s.getP3().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP1().depiler();t1.getP3().depiler();
						     if(t.getP3().equals(t1.getP3()) && t.getP1().equals(t1.getP1())&&c.getP2().equals(s.getP2())){
					    	 voisinvalide.add(s); 
						     }	
				}
				if(!s.getP2().vide()){
					Configuration t = c.clone(); Configuration t1 = s.clone();		   
					   t.getP3().depiler();t1.getP2().depiler();
						     if(t.getP3().equals(t1.getP3()) && t.getP2().equals(t1.getP2())&&c.getP1().equals(s.getP1())){
					    	 voisinvalide.add(s); 
						     }
				}
	       }
			
		}
		//
	   if(!c.vide()){
		   if(c.getP1().getTete()<c.getP2().getTete()  && c.getP2().getTete()<c.getP3().getTete()){
			   Configuration t = c.clone(); Configuration t1 = s.clone();		   
			   if(!s.getP2().vide()){
				   int a= t.getP1().depiler(); int b= t1.getP2().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
			   			voisinvalide.add(s);
			   		}
			   	    t.getP1().empiler(a);
				    t1.getP2().empiler(b);
			   }	
			   	if(!s.getP3().vide()){
			   		int a = t.getP1().depiler(); int e = t1.getP3().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			 		t.getP1().empiler(a);t1.getP3().empiler(e);
			   	
			   	
			 		t.getP2().depiler(); t1.getP3().depiler();
			    	if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			   	} 
		   }
		   if(c.getP1().getTete()<c.getP3().getTete()  && c.getP3().getTete()<c.getP2().getTete()){
			   Configuration t = c.clone(); Configuration t1 = s.clone();		      
			if(!s.getP2().vide()){
				int a= t.getP1().depiler(); int b= t1.getP2().depiler();
			   	if(t.getP3().equals(t1.getP3())&& t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
			   		voisinvalide.add(s);
			   	}
			   	t.getP1().empiler(a);t1.getP2().empiler(b);
			} 	
			 if(!s.getP3().vide()){ 	
				  int a= t.getP1().depiler(); int e = t1.getP3().depiler();
			    if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   	}
			 	t.getP1().empiler(a);
			    t1.getP3().empiler(e);
			 }  
			  if(!s.getP2().vide()){
			    int a=t.getP3().depiler(); int e=t1.getP2().depiler();
			    if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   	}
			    t.getP3().empiler(a);
			    t1.getP3().empiler(e);
			  }  
		   }
		   
		  //
		   if(c.getP2().getTete()<c.getP1().getTete()  && c.getP1().getTete()<c.getP3().getTete()){
			   Configuration t = c.clone(); Configuration t1 = s.clone();		   
			   if(!s.getP3().vide()){
				   int a= t.getP2().depiler(); int b= t1.getP3().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
			   			voisinvalide.add(s);
			   		}
			   	    t.getP2().empiler(a);
				    t1.getP3().empiler(b);
			   }	
			   	if(!s.getP1().vide()){
			   		int a = t.getP2().depiler(); int e = t1.getP1().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			 		t.getP2().empiler(a);t1.getP1().empiler(e);
			   	}
			   	if(!s.getP3().vide()){
			 		t.getP1().depiler(); t1.getP3().depiler();
			    	if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			   	} 
		   }
		
		   if(c.getP2().getTete()<c.getP3().getTete()  && c.getP3().getTete()<c.getP1().getTete()){
			   Configuration t = c.clone(); Configuration t1 = s.clone();		   
			   if(!s.getP3().vide()){
				   int a= t.getP2().depiler(); int b= t1.getP3().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
			   			voisinvalide.add(s);
			   		}
			   	    t.getP2().empiler(a);
				    t1.getP3().empiler(b);
			   }	
			   	if(!s.getP1().vide()){
			   		int a = t.getP2().depiler(); int e = t1.getP1().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			 		t.getP2().empiler(a);t1.getP1().empiler(e);
			   	}
			   	if(!s.getP1().vide()){
			 		t.getP3().depiler(); t1.getP1().depiler();
			    	if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			   	} 
		   }
		   
		   if(c.getP3().getTete()<c.getP1().getTete()  && c.getP1().getTete()<c.getP2().getTete()){
			   Configuration t = c.clone(); Configuration t1 = s.clone();		   
			   if(!s.getP1().vide()){
				   int a= t.getP3().depiler(); int b= t1.getP1().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
			   			voisinvalide.add(s);
			   		}
			   	    t.getP3().empiler(a);
				    t1.getP1().empiler(b);
			   }	
			   	if(!s.getP2().vide()){
			   		int a = t.getP3().depiler(); int e = t1.getP2().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			 		t.getP3().empiler(a);t1.getP2().empiler(e);
			   	}
			   	if(!s.getP2().vide()){
			 		t.getP1().depiler(); t1.getP2().depiler();
			    	if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			   	} 
		   }
		   if(c.getP3().getTete()<c.getP2().getTete()  && c.getP2().getTete()<c.getP1().getTete()){
			   Configuration t = c.clone(); Configuration t1 = s.clone();		   
			   if(!s.getP1().vide()){
				   int a= t.getP3().depiler(); int b= t1.getP1().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP2().equals(t1.getP2()) && t.getP1().equals(t1.getP1())){
			   			voisinvalide.add(s);
			   		}
			   	    t.getP3().empiler(a);
				    t1.getP1().empiler(b);
			   }	
			   	if(!s.getP2().vide()){
			   		int a = t.getP3().depiler(); int e = t1.getP2().depiler();
			   		if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			 		t.getP3().empiler(a);t1.getP2().empiler(e);
			   	}
			   	if(!s.getP1().vide()){
			 		t.getP2().depiler(); t1.getP1().depiler();
			    	if(t.getP3().equals(t1.getP3())&& t.getP1().equals(t1.getP1()) && t.getP2().equals(t1.getP2())){
			   		voisinvalide.add(s);
			   		}
			   	} 
		   }
	   }
	   }
	   }
	   return voisinvalide ;
   }
 */ 
   // récupération d'un sommet non visté de la liste des sommets voisins du sommet passé en parametre,
 	// c'est comme la fonction choisir voisin non visité dans le parcours en largeur
   public Configuration choisir_voisin_non_visite(Configuration c, byte[] tab){
		ArrayList<Configuration> list_vois = voisin(c);		
	   if(!list_vois.isEmpty()){
		   for(Configuration q : list_vois){
	    		if(tab[q.getIndice()]!=1){
	    			tab[q.getIndice()]=1;
	    			return q;
	    		}
	    	}
	   }
	   return null;
    }
// la fonction hanoi1 qui renvoie la plus court chaine, la séquence minimale de déplacements de disques 
public ArrayList<Configuration> hanoi1 (Grapheh g, Configuration begin){
	int indice=0; // pour recuperer le pere de la configuration d'arrivee (p1,p2,p3)
	
	liste_parcours.add(begin);   // ajout de la configuration initiale a liste du parcours
	 getTab_VNV()[begin.getIndice()]=1;
	 tableau_distance[begin.getIndice()]=0;  //initialiser la distance de la configuration initiale (la configuration du départ)à 0
	 tableau_pere_configuration[begin.getIndice()]=null; // par definition le pere du sommet initial est null.
	 FileHanoi F = new FileHanoi(10000);  // instanciation de la file des configurations visitées 
	 
	 VNV= new int[g.getListe().size()];// tableau de nombre de configuration non visité pour chaque configuration

	 for(Configuration x : g.getListe()){  	
	 // initialisation du tableau des degrés de chaque configuration
		 VNV[x.getIndice()]= g.voisin(x).size();
	 }	 

		                                           //décrémenter le degres de chaque configuration voisine de la configuration initiale
	 for(Configuration s : g.voisin(begin)){	  //décrémenter le degres de chaque configuration voisine de la configuration initiale		
	 VNV[s.getIndice()]-=1;
	}
	if(VNV[begin.getIndice()]>0){
		F.enfiler(begin);                                 // enfiler la configuration initiale dans la file si elle est ouverte
	}
	
	Configuration z  ;
	while(!F.vide()){
		while (F.tete()!=null && VNV[F.tete().getIndice()]==0){
			F.defiler();
		}
		 z = choisir_voisin_non_visite(F.tete(), getTab_VNV());  //choisir une configuration voisine non visitée de la configuration tete de file		
		 if(z!=null) {
			 liste_parcours.add(z);        //ajouter la configuration choisie  dans la liste du parcours  
					 
			 tableau_distance[z.getIndice()]=tableau_distance[F.tete().getIndice()]+1; //augmanter de 1 la distance 
			 tableau_pere_configuration[z.getIndice()]=F.tete();    // associé le pere a la configuration choisie, le pere est la tete de la file 
			 if(z.equals(getFinale())){  // z est la configuration finale, je l'ajoute a la liste de la plus courte chaine
				 indice= z.getIndice();     // recuperé l indice de la configuration finale 
				 liste_plus_court_chemin.add(0, z); // ajout de la configuration d'arrivee a la liste pcc
			 }
			   ArrayList<Configuration> l= g.voisin(z);			
			 for(Configuration t : l){            // décrémenter le degré des voisins non visité de 1 pour chaque configuration visitée
			 VNV[t.getIndice()]-=1;
			 }
		}    
		 // remettre dans la file la configuration visitée si elle est toujours ouverte, 
		if( z!=null && VNV[z.getIndice()]>0){	
			F.enfiler(z);
		}
	}
	// construction de la plus courte chaine
	while(tableau_pere_configuration[indice]!=null){
		liste_plus_court_chemin.add(0,tableau_pere_configuration[indice]); // ajout de la configuration pere(mere) de chaque configuration, jusqu'a la 1ere configuration nulle, qui est par initialisation la configuration du départ
		indice=tableau_pere_configuration[indice].getIndice();           // l'ajout ce fait toujours en debut de liste, jusqu'a l'ajout de la configuration du depart qui n'a pas de pere (mere)
	}
	

	System.out.println(liste_plus_court_chemin);
	System.out.println("le nombre de déplacement minimal est la distance a la configuration finale depuis la configuration du départ : " +tableau_distance[getFinale().getIndice()]);
	return liste_plus_court_chemin;
	//return Liste_Parcours;
}    
   public static void main(String[] args) throws IOException {
	   double [] temps;
	   double debut, fin;
	   sc = new Scanner(System.in);
		System.out.println("veuillez saisir le nombre d'exploratuers");
		int nbre = sc.nextInt();	  	
 	String fic="C:/Users/foufi2012/workspace/IKHLEF_projet_LI311/foufahanoi1.txt";
		temps= new double[nbre+1];
		PrintWriter ecrivain;
		
	 for(int j=0; j<(nbre+1);j++){
		  Grapheh h = new Grapheh(j);			 
			debut=System.currentTimeMillis(); 
			 ArrayList <Configuration> ha= h.hanoi1(h,h.getListe().get(0));	 
			 fin=System.currentTimeMillis(); 
			 temps[j]=fin-debut;
	}
	 ecrivain =  new PrintWriter(new BufferedWriter(new FileWriter(fic)));
     for(int a =0; a<temps.length;a++)
     ecrivain.println(a+" "+temps[a]);
     ecrivain.close();
	}
}
