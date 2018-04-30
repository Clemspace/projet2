package exe;
import CAImageBuffer;
import CAtoolbox;
import ImageFrame;

public class ForestFire extends CAtoolbox {


	public static void main(String[] args) {

		int dx = 400;
		int dy = 400;
		
		int[][] tableauCourant = new int[dx][dy];
		int[][] nouveauTableau = new int[dx][dy];
		
		int delai = 100;//100;
		
		int nombreDePasMaximum = 10000;//1000;
		int it = 0;
		
		double densite = 0.55; //0.55; // seuil de percolation � 0.55
		
		double incendie = 0.0004;
		double naissance = 0.001;
		
		// optionnel: initialise la visualisation dans une fenetre
		
		CAImageBuffer image = new CAImageBuffer(dx,dy);
	    ImageFrame imageFrame =	ImageFrame.makeFrame( "Ma mixtape", image, delai, 600, 600 );

		// initialisation (peuple la foret)
	    
	    for ( int x = 0 ; x != dx ; x++ ) {
	    	for ( int y = 0 ; y != dy ; y++ ) {
	    		if ( densite >= Math.random() )
	    			tableauCourant[(int)x][(int)y]=1; // tree
	    	}
	    }

	    tableauCourant[dx/2][dy/2] = 2; // burning tree
	    
		// on fait tourner l'automate
		
		while ( it != nombreDePasMaximum )
		{
			double densiteReelle = 0;
			// 1a - affiche de l'�tat courant dans la fenetre graphique (toutes les cellules)
			image.updateForest(tableauCourant);
			
			// 1 - mise a jour de l'automate (dans le tableau en tampon)
			for ( int x = 0 ; x != tableauCourant.length ; x++ ) {
				for ( int y = 0 ; y != tableauCourant[0].length ; y++ )
				{
					if(tableauCourant[x][y] == 2) { //cas case en feu
						int [] voisins = {tableauCourant[(x+dx)%dx][(y+dy-1)%dy],tableauCourant[(x+dx)%dx][(y+dy+1)%dy],
									  	  tableauCourant[(x+dx-1)%dx][(y+dy)%dy],tableauCourant[(x+dx+1)%dx][(y+dy)%dy]};//von Newmann HBGD
						
						for (int j=0; j != voisins.length; j++) {
							if(voisins[j] == 1) {
								switch(j) {
								case 0:
									nouveauTableau[(x+dx)%dx][(y+dy-1)%dy] = 2;
									break;
								case 1:
									nouveauTableau[(x+dx)%dx][(y+dy+1)%dy] = 2;
									break;
								case 2:
									nouveauTableau[(x+dx-1)%dx][(y+dy)%dy] = 2;
									break;
								case 3:
									nouveauTableau[(x+dx+1)%dx][(y+dy)%dy] = 2;
									break;
																								
								}
								
							}
						}
						nouveauTableau[x][y] = 3;
					}
					
					else if((tableauCourant[x][y] == 0 ||tableauCourant[x][y] == 3) && naissance >= Math.random()) {
						nouveauTableau[x][y] = 1;
					}
					else if(tableauCourant[x][y] == 1 && incendie >= Math.random()) {
						nouveauTableau[x][y] = 2;										
					}
					else if(tableauCourant[x][y] == 1 && nouveauTableau[x][y]== 2){//arbre devenu en feu a cette itération
						nouveauTableau[x][y] = 2;
						densiteReelle++;
					}
					
					
					else nouveauTableau[x][y] = tableauCourant[x][y];
					
				}	
				}
			
			// 2 - met a jour le tableau affichable
			
			for ( int x1 = 0 ; x1 != tableauCourant.length ; x1++ ) {
				for ( int y2 = 0 ; y2 != tableauCourant[0].length ; y2++ ) {
					if(tableauCourant[x1][y2]==1){
						densiteReelle++;
					}
					tableauCourant[x1][y2] = nouveauTableau[x1][y2];
					
				}
			}
			System.out.println(it +","+densiteReelle/(dx*dy));
			

			it++;
		
			
			// ne va pas trop vite...
			
			try {
				Thread.sleep(delai);
			} catch (InterruptedException e) 
			{
			}
		}
		
	}
}




