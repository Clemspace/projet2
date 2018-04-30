package exe;
import CAImageBuffer;
import CAtoolbox;
import ImageFrame;

public class FeuAlea extends CAtoolbox {
		
	void renduImage() {
		CAImageBuffer image = new CAImageBuffer(dx,dy);
	    ImageFrame imageFrame =	ImageFrame.makeFrame( "Forest fire", image, delai, 600, 600 );
		
	}
	
	void updateAlea(int x, int y) {
			
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
		}
	
	void ini(int) {
		
		int dx = 50;
		int dy = 50;
		int[][] tableauCourant = new int[dx][dy];
		double densite = 0.55; //0.55; // seuil de percolation � 0.55
		double incendie = 0.0001;
		double naissance = 0.001;
	}

	public static void main(String[] args) {
		
		ini();
		
		int cpt =0; 
		int nbMMAJ = 500;
		while (cpt1<nombreDePasMaximum) {
			while (cpt<nbMAJ) {
				update();
				cpt++;
			}
			renduImage();
			cpt1++;
		}
		
		
		
		int delai = 2;//100;
		
		int nombreDePasMaximum = 10000;//1000;
		int it = 0;
		
		double densite = 0.55; //0.55; // seuil de percolation � 0.55
		double incendie = 0.0001;
		double naissance = 0.001;
		// optionnel: initialise la visualisation dans une fenetre
		
		CAImageBuffer image = new CAImageBuffer(dx,dy);
	    ImageFrame imageFrame =	ImageFrame.makeFrame( "Forest fire", image, delai, 200, 200 );

		// initialisation (peuple la foret)
	    
	    for ( int x = 0 ; x != dx ; x++ )
	    	for ( int y = 0 ; y != dy ; y++ )
	    		if ( densite >= Math.random() )
	    			tableauCourant[(int)x][(int)y]=1; // tree

	    tableauCourant[dx/2][dy/2] = 2; 
	}
}
