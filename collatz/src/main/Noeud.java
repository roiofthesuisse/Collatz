package main;

import java.util.ArrayList;

public class Noeud {
	int x;
	int y;
	long valeur;
	boolean fantome;
	String valeurBinaire;
	Noeud parentHaut;
	Noeud parentGauche;
	Noeud filsBas;
	Noeud filsDroit;
	boolean enfanteEnBas;
	boolean laBrancheADejaEnfante;
	boolean premiereBrancheDeLaBrancheParente;
	
	public ArrayList<Noeud> getFils(){
		ArrayList<Noeud> fils = new ArrayList<Noeud>();
		fils.add(filsDroit);
		if(filsBas!=null){
			fils.add(filsBas);
		}
		return fils;
	}
	
	public Noeud getParent(){
		if(parentHaut==null){
			return parentGauche;
		}
		return parentHaut;
	}
	
	public void ajouterDescendanceALArbre(int xmax, ArrayList<Noeud> arbre){
		//ajouter le noeud
		arbre.add(this);
		if(this.dansLArbre(arbre)){
			this.fantome = true;
		}
		this.enfanteEnBas = (this.valeur-1)%3==0 && this.valeur>1;
		
		//créer le fils droit
		Noeud fd = new Noeud();
		fd.parentGauche = this;
		this.filsDroit = fd;
		fd.fantome = this.fantome;
		if(this.enfanteEnBas){
			fd.laBrancheADejaEnfante = true;
		}
		fd.premiereBrancheDeLaBrancheParente = this.premiereBrancheDeLaBrancheParente;
		fd.x = this.x+1;
		fd.y = this.y;
		fd.valeur = 2*this.valeur;
		fd.valeurBinaire = this.valeurBinaire+"0";
		if(fd.x<=xmax){
			fd.ajouterDescendanceALArbre(xmax, arbre);
		}
		
		//créer le fils bas
		if(enfanteEnBas){
			Noeud fb = new Noeud();
			fb.parentHaut = this;
			this.filsBas = fb;
			fb.fantome=this.fantome;
			if(this.parentHaut!=null){
				fb.fantome = true;
			}
			if(!this.laBrancheADejaEnfante){
				fb.premiereBrancheDeLaBrancheParente = true;
			}
			fb.x = this.x;
			fb.y = this.y+1;
			fb.valeur = (this.valeur-1)/3;
			fb.valeurBinaire = calculerValeurBinaire(fb.valeur);
			fb.ajouterDescendanceALArbre(xmax, arbre);
		}
	}
	
	public int oblicite(){
		return this.valeurBinaire.length() - this.x + 2*(this.y-1);
	}
	
	public String calculerValeurBinaire(long a){
		if(a==0){
			return "0";
		}
		if(a==1){
			return "1";
		}
		
		if(a%2==0){
			return calculerValeurBinaire(a/2)+"0";
		}else{
			return calculerValeurBinaire(a/2)+"1";
		}
	}
	
	public boolean dansLArbre(ArrayList<Noeud> arbre){
		/*if(this.valeur==5){
			String arbreString= "";
			for(Noeud n : arbre){
				arbreString+=n.valeur+",";
			}
			System.out.println("5 est dans l'arbre "+arbreString);
		}*/
		for(Noeud n : arbre){
			if(!this.equals(n) && !n.fantome && this.valeur == n.valeur){
				return true;
			}
		}
		return false;
	}
}
