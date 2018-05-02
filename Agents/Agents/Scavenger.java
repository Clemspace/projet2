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
					_world.agents.remove(_world.agents.get(i));// on supprime le cadavre consomm�
				}
			}
				
			}
			if ( _x == _world.agents.get(i)._x && _y == _world.agents.get(i)._y) {    // proie sur la même case que prédateur, 
																							  //nourrit le prédateur
				
				
			}
		}

}
