package exe;


import java.io.File;

import Agents.PredatorAgent;
import Agents.PreyAgent;
import Environnement.Case;
import Environnement.Island;
import toolbox.CAtoolbox;
import toolbox.ImageBuffer;
import toolbox.ImageFrame;
import toolbox.CAImageBuffer;

public class MyworldPix {

	public static void main(String[] args) {
		
		
		// initialisation generale
	    		double densite = 0.2;
				int dx =1024;
				int dy =1024;
				
				int displayWidth = dx;  // 200
				int displayHeight = dy; // 200

				// pick dimension for display
				if ( displayWidth < 200 )
					displayWidth = 200;
				else
					if ( displayWidth > 1024 )
						displayWidth = 1024;
					else
						if ( displayWidth < 300 )
							displayWidth = displayWidth * 2; 
				if ( displayHeight < 200 )
					displayHeight = 200;
				else
					if ( displayHeight > 1024 )
						displayHeight =1024;
					else
						if ( displayHeight < 300 )
							displayHeight = displayHeight * 2; 
				
				
				int delai = 200;//100; // -- delay before refreshing display -- program is hold during delay, even if no screen update was requested. USE WITH CARE. 
				int nombreDePasMaximum = Integer.MAX_VALUE;
				int it = 0;
				int displaySpeed = 5;//50; // from 1 to ...
				
				CAImageBuffer image = new CAImageBuffer(dx,dy);
			    ImageFrame imageFrame =	ImageFrame.makeFrame( "MonkiIsland", image, delai, displayWidth, displayHeight );

			    // initialise l'ecosysteme
			    
				
				ImageBuffer MoistureMap = ImageBuffer.LoadFromDisk("moisture.png");
				ImageBuffer HeightMap = ImageBuffer.LoadFromDisk("austra10.png");	//infos nécessaires a la crea d'un monde interessant et coherent
				ImageBuffer TempMap = ImageBuffer.LoadFromDisk("temper.png");
				
				
				
		
		  

			    
				Island world = new Island(dx,dy);
				world.InitIsland(HeightMap,MoistureMap,TempMap);
				world.display(image); 

				
				
				

				
				for ( int i = 0 ; i != 10 ; i++ )
					world.agents.add(new PreyAgent((int)(Math.random()*dx),(int)(Math.random()*dy),world));
				for ( int i = 0 ; i != 10 ; i++ )
					world.agents.add(new PredatorAgent((int)(Math.random()*dx),(int)(Math.random()*dy),world));
				
			    // mise a jour de l'�tat du monde
				
				while ( it != nombreDePasMaximum )
				{
					// 1 - display
					
					if ( it % displaySpeed == 0 )
						world.display(image); 
					image.update(world.Buffer0);
					

					// 2 - update
								
					world.step();
					
					// 3 - iterate
					
					it++;
					System.out.println(it);
					
					try {
						Thread.sleep(delai);
					} catch (InterruptedException e) 
					{
					}
				}
				
			}

		

	}


