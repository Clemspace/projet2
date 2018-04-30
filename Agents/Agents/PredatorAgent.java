package Agents;
import Environnement.Island;

//import sun.management.Agent;

public class PredatorAgent extends Agent {
	boolean _predator;
	int faim;
	boolean chasse;
	
	public PredatorAgent( int __x, int __y, Island __w )
	{
		super(__x,__y,__w);
		
		_predator = true;
		faim = 100;
	}
	
	public void step( ){
		// met a jour l'agent
		for (int i = 0; i!=_world.Predateurs.size();i++) { //tue les prédateurs affamés
				_world.Predateurs.get(i).faim--;
				if(_world.Predateurs.get(i).faim==0) { 
				
					_world.Predateurs.remove(this);
					i--;
				}
			
		}
		
		// A COMPLETER

		int cellColor[] = _world.getCellState(_x, _y);
		
		cellColor[redId] = 255;
		cellColor[greenId] = 240;
		cellColor[blueId] = 225;

		_world.setCellState(_x, _y, cellColor);
		if(Chasse()) {
			_orient = Voisinage();// attitude de traque et suivi de la cible
		}
		else if ( Math.random() > 0.5 ) // au hasard
			_orient = (_orient+1) %4;
		else
			_orient = (_orient-1+4) %4;

		// met a jour: la position de l'agent (depend de l'orientation)
		 switch ( _orient ) 
		 {
         	case 0: // nord	
         		_y = ( _y - 1 + _world.getHeight() ) % _world.getHeight();
         		break;
         	case 1:	// est
         		_x = ( _x + 1 + _world.getWidth() ) % _world.getWidth();
 				break;
         	case 2:	// sud
         		_y = ( _y + 1 + _world.getHeight() ) % _world.getHeight();
 				break;
         	case 3:	// ouest
         		_x = ( _x - 1 + _world.getWidth() ) % _world.getWidth();
 				break;
		 }
		 
	}
	
}