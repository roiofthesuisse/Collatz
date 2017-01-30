package main;

import java.util.ArrayList;

public class Noeud {
	int x;
	int y;
	long valeur;
	//boolean fantome;
	String valeurBinaire;
	Noeud parentHaut;
	Noeud parentGauche;
	Noeud filsBas;
	Noeud filsDroit;
	boolean enfanteEnBas;
	boolean laBrancheADejaEnfante;
	boolean premiereBrancheDeLaBrancheParente;
	private ArrayList<Long> ordre;
	
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
		if(this.dansLArbre(arbre)){
			return;
		}
		arbre.add(this);
		
		this.enfanteEnBas = (this.valeur-1)%3==0 && this.valeur>4;
		
		//créer le fils droit
		Noeud fd = new Noeud();
		fd.parentGauche = this;
		this.filsDroit = fd;
		//fd.fantome = this.fantome;
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
		if(enfanteEnBas && this.parentHaut==null){
			Noeud fb = new Noeud();
			fb.parentHaut = this;
			this.filsBas = fb;
			/*fb.fantome=this.fantome;
			if(this.parentHaut!=null){
				fb.fantome = true;
			}*/
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
			if(!this.equals(n) && /*!n.fantome &&*/ this.valeur == n.valeur){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Long> getOrdre(){
		if(this.ordre==null){
			this.ordre = new ArrayList<Long>();
			int palier = -1;
			int i = 0;
			while( palier == -1 ){
				if(Math.pow(2, i) == valeur) {
					palier = i;
				} else if (Math.pow(2, i+1) > valeur) {
					palier = i;
				}
				i++;
			}
			//if(valeur==3){ System.out.println("le palier de "+valeur+" est "+Math.pow(2, palier));}
			
			long reste = valeur - (long) Math.pow(2, palier);
			//if(valeur==3){ System.out.println("le reste de "+valeur+" est "+reste);}
			long quartile;
			if(reste == 0){
				quartile = 0;
			} else if( reste >= Math.pow(2, palier-1)){
				quartile = 2;
			} else {
				quartile = 1;
			}
			//if(valeur==3){ System.out.println("le quartile de "+valeur+" est "+quartile);}
			
			this.ordre.add(palier+2*quartile);
			this.ordre.add(-this.valeur);
		}
		return this.ordre;
	}

	public int comparer(Noeud noeud2) {
		for(int i=0; i<getOrdre().size(); i++){
			if (this.getOrdre().get(i) > noeud2.getOrdre().get(i)) {
				return 1;
			} else if(this.getOrdre().get(i) < noeud2.getOrdre().get(i)) {
				return -1;
			}
		}
		return 0;
	}
	
	
}
