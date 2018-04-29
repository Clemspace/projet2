
public abstract class Agent {

	Island _world;
	
	static int redId   = 0;
	static int greenId = 1;
	static int blueId  = 2;
	
	int 	_x;
	int 	_y;
	int 	_orient;
	int 	_etat;
	
	int 	_redValue;
	int 	_greenValue;
	int 	_blueValue;
	
	public Agent( int __x, int __y, Island __w )
	{
		_x = __x;
		_y = __y;
		_world = __w;
		
		_redValue = 255;
		_greenValue = 0;
		_blueValue = 0;
		
		_orient = 0;
	}
	
	abstract public void step( );
	
}

public int Voisinage() {//identifie la direction de la première intrusion trouvée
	if(getClass().getName()=="PreyAgent") {
	
		for ( int i = 0 ; i != _world.Predateurs.size() ; i++ ){
			
				if(_world.Predateurs.get(i)._x ==_x && _world.Predateurs.get(i)._y ==_y-1 ){ //intrus en haut  
					return 0;
				}
				else if(_world.Predateurs.get(i)._x ==_x+1 && _world.Predateurs.get(i)._y ==_y) {//intrus a l'est
					return 1;	
				}
				else if(_world.Predateurs.get(i)._x ==_x && _world.Predateurs.get(i)._y ==_y+1) {//intrus au sud
					return 2;	
				}
				else if(_world.Predateurs.get(i)._x ==_x-1 && _world.Predateurs.get(i)._y ==_y) {//intrus a l'ouest
					return 3;	
				}
			}
	}
		
		if(getClass().getName()=="PredatorAgent") {
			
			for ( int i = 0 ; i != _world.Proies.size() ; i++ ){
					if(_world.Proies.get(i)._x ==_x && _world.Proies.get(i)._y ==_y-1 ){ //intrus en haut  
						return 0;
					}
					else if(_world.Proies.get(i)._x ==_x+1 && _world.Proies.get(i)._y ==_y) {//intrus a l'est
						return 1;	
					}
					else if(_world.Proies.get(i)._x ==_x && _world.Proies.get(i)._y ==_y+1) {//intrus au sud
						return 2;	
					}
					else if(_world.Proies.get(i)._x ==_x-1 && _world.Proies.get(i)._y ==_y) {//intrus a l'ouest
						return 3;	
					}
				}
			
		}
		
	
	 return -1;
}

 public boolean Chasse() {
	return (Voisinage() != -1);
}



}

