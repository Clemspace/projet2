package Agents;

import Environnement.Island;

public class Scavenger extends Agent{
	
	
	boolean chasse;
	int faim;
	int corpse;
	
	public Scavenger( int __x, int __y, Island __w )
	{
		super(__x,__y,__w);
		
		_redValue = 25;
		_greenValue = 25;
		_blueValue = 25;
		chasse = false;
		_alive = true;
		
		corpse = 0;
	}
	
	public void step( )
	{
		// met a jour l'agent
		for (int i = 0; i!=_world.agents.size();i++) {
			
			if(!_world.agents.get(i)._alive) {
				
				int dist = Math.abs(_world.agents.get(i)._x-_x+_world.agents.get(i)._y-_y);
				if (dist <25) {//si distance a vol d'oiseau inf a 25 alors mange le cadavre et supprime de l'arraylist
					faim +=150;
					_world.agents.remove(_world.agents.get(i));// on supprime le cadavre consommé
				}
			}
					
		}
		// ... A COMPLETER

				int cellColor[] = _world.getCellState(_x, _y);

				cellColor[redId]   = 25;
				cellColor[greenId] = 25;
				cellColor[blueId]  = 25;

				_world.setCellState(_x, _y, cellColor);
				
				
			
					
				
				if(Chasse()) { //comportement de chasse, va chercher a fuir
					_orient= Voisinage()+2;
					
				}
				else if (Math.random() > 0.5) // au hasard
					_orient = (_orient + 1) % 4;
				else 
					_orient = (_orient - 1 + 4) % 4;

				// met a jour: la position de l'agent (depend de l'orientation)
				switch (_orient) {
				case 0: // nord	
					_y = (_y - 6 + _world.getHeight()) % _world.getHeight();
					break;
				case 1: // est
					_x = (_x + 6 + _world.getWidth()) % _world.getWidth();
					break;
				case 2: // sud
					_y = (_y + 6 + _world.getHeight()) % _world.getHeight();
					break;
				case 3: // ouest
					_x = (_x - 6 + _world.getWidth()) % _world.getWidth();
					break;
				}
	}
}


