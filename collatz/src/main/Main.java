package main;

import java.util.ArrayList;

public class Main {
	static int xmax = 20;
	static int ymax = xmax/2+2;
	public static int tailleChiffres = (int) Math.floor(Math.log(Math.pow(2, xmax))/Math.log(10));
	static int len = 16;
	static boolean AFFICHER_FANTOMES = false;
	static boolean AFFICHER_FANTOMITUDE = false;
	static boolean N_AFFICHER_QUE_LES_BRANCHES_AINEES = false;
	static boolean AFFICHER_OBLICITE = false;
	
	public static void main(String[]args){
		// Creation de l'arbre
		ArrayList<Noeud> arbre = new ArrayList<Noeud>();
		Noeud un = new Noeud();
		un.valeur = 1;
		un.valeurBinaire = un.calculerValeurBinaire(un.valeur);
		un.x = 1;
		un.y = 1;
		un.ajouterDescendanceALArbre(xmax, arbre);
		
		// Arbre en tableau
		ArrayList<Noeud>[][] tableau = mettreArbreDansTableau(arbre, xmax);
		
		// Affichage
		afficherTableau(tableau);
		separation();
		//afficherOblicites(tableau);
		
		// V�rifier si les noeuds v�rifient une relation d'ordre donn�e
		verifierOrdre(arbre);
	}

	public static ArrayList<Noeud>[][] mettreArbreDansTableau(ArrayList<Noeud> arbre, int xmax){
		@SuppressWarnings("unchecked")
		ArrayList<Noeud>[][] tab = new ArrayList[xmax][xmax];
		for(int i=0; i<xmax; i++){
			for(int j=0; j<xmax; j++){
				tab[i][j] = new ArrayList<Noeud>();
			}
		}
		for(Noeud n : arbre){
			tab[n.x-1][n.y-1].add(n);
		}
		return tab;
	}
	
	public static void separation(){
		System.out.println("------------------------------------------------");
	}
	
	public static void afficherOblicites(ArrayList<Noeud>[][] tab){
		for(int j=0; j<ymax; j++){
			String ligne = "";
			for(int i=0; i<xmax; i++){
				String cell = "{";
				for(Noeud n : tab[i][j]){
					//if(AFFICHER_FANTOMES || !n.fantome){
						int oblicite = n.oblicite();
						if(cell.length()>1){
							cell=cell+",";
						}
						cell = cell+oblicite;
					//}
				}
				cell = cell + "}";
				ligne = ligne + cell;
			}
			if(!ligne.equals("")){
				System.out.println(ligne);
			}
		}
	}
	
	public static void afficherTableau(ArrayList<Noeud>[][] tab){
		String[][] tabStr = new String[xmax][ymax];
		int[] colMaxSize = new int[xmax];
		int j;
		int i;
		String cell = "";
		j=0;
		while(j<ymax){
			i=0;
			while(i<xmax){
				cell = "{";
				for(Noeud n : tab[i][j]){
					//if(AFFICHER_FANTOMES || !n.fantome){
						if(!N_AFFICHER_QUE_LES_BRANCHES_AINEES || n.premiereBrancheDeLaBrancheParente){
							if(cell.length()>1){
								cell=cell+",";
							}
							
							/*if(AFFICHER_FANTOMITUDE){
								cell = cell+(n.fantome?"F":"R");
							}*/
							String zeros = "";
							for(int k=(""+n.valeur).length(); k<tailleChiffres; k++){
								zeros += "0";
							}
							cell = cell+zeros+n.valeur;
							if(AFFICHER_OBLICITE){
								cell = cell+"_"+n.oblicite();
							}
							
						}
					//}
				}
				cell = cell + "}";
				tabStr[i][j] = cell;
				
				if(colMaxSize[i]<cell.length()){
					colMaxSize[i]=cell.length();
				}
				
				i++;
			}
			j++;
		}
		
		
		for(j=0; j<ymax; j++){
			for(i=0; i<xmax; i++){
				System.out.print(tabStr[i][j]);
				for(int k=tabStr[i][j].length(); k<colMaxSize[i]; k++){
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		
	}
	
	
	private static void verifierOrdre(ArrayList<Noeud> arbre) {
		for(Noeud n : arbre){
			if (n.comparer(n.filsDroit) > 0) {
				System.out.println( n.valeur+" ("+n.getOrdre().get(0)+";"+n.getOrdre().get(1)+") > "+
						n.filsDroit.valeur+" ("+n.filsDroit.getOrdre().get(0)+";"+n.filsDroit.getOrdre().get(1)+")" );
			} else if(n.enfanteEnBas && n.filsBas!=null && n.valeur!=4 && n.comparer(n.filsBas) > 0 && n.filsBas.valeur%2==1){
				System.out.println( n.valeur+" ("+n.getOrdre().get(0)+";"+n.getOrdre().get(1)+") > "+
						n.filsBas.valeur+" ("+n.filsBas.getOrdre().get(0)+";"+n.filsBas.getOrdre().get(1)+")" );
			}
		}
	}
	
}
