
public class Case {
	
	public int hauteur; //valeur de hauteur entre 0 et 100
	public int[] color; //tableau rgb de couleurs (entre 0 et 255 pour les trois valeurs)
	public int arbre;	//age de l'arbre,(entre 0 et 150), 
	public int fertilite;//valeur basée entre 0 et 100(influe sur repousse arbres)
	public float moisture;//valeur basée entre 0 et 100 (influe sur repousse arbres et incendies) 
	public float temp; // valeur entre 0 et 100, influe sur proba déclenchement incendie 
	public int x; //
	public int y; //coordonnées de la case en question
	public int volWater; //si la case est de type eau, donne le volume d'eau, valeur entre 
	public int type; // donne le type de la case (voir compte rendu pour plus de détails)
	
	public Case() {
		
	}
	
	public void initCase() {
		
	}

}
