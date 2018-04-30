package Environnement;
import java.util.ArrayList;

public class Case {
	
	public int hauteur; //valeur de hauteur entre 0 et 100
	public int[] color; //tableau rgb de couleurs (entre 0 et 255 pour les trois valeurs)
	public int arbre;	//age de l'arbre,(entre 1 et 150), 0 si pas d'arbre
	public int fertilite;//valeur basée entre 0 et 100(influe sur repousse arbres)
	public int moisture;//valeur basée entre 0 et 100 (influe sur repousse arbres et incendies) 
	public int temp; // valeur entre 0 et 100, influe sur proba déclenchement incendie 
	public int x; //
	public int y; //coordonnées de la case en question
	public int volWater; //si la case est de type eau, donne le volume d'eau, valeur entre 
	public int type; // donne le type de la case (voir compte rendu pour plus de détails)
	
	public Case(int x, int y) {
		hauteur =0;
		color = new int[3];
		int arbre = 0;
		int fertilite = 0;
		int moisture = 0;
		int temp = 0;
		x =x;
		y = y;
		volWater = 0;
		type = 0;
	}
	
	public ArrayList<Case> getautour(Island world,int x, int y) {
        
		ArrayList<Case>maliste = new ArrayList<Case>();
        maliste.add(world.Buffer0[x+world._dx%world._dx][y]);// case en haut
        maliste.add(world.Buffer0[x+world._dx%world._dx][y]);//en bas
        maliste.add(world.Buffer0[x][y-1%world._dy]);//gauche
        maliste.add(world.Buffer0[x][y+1%world._dy]);//droite
        return maliste;
        
    }

	public int getType() {
		
		return type;
	}
	public int getHauteur() {
		return hauteur;
	}

}
