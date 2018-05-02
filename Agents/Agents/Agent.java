
package Agents;

import Environnement.Island;

public abstract class Agent {

	Island _world;
	
	static int redId   = 0;
	static int greenId = 1;
	static int blueId  = 2;
	
	boolean _alive;
	int faim;

	
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
	


public int Voisinage() {//identifie la direction de la première intrusion trouvée
	
	
		for ( int i = 0 ; i != _world.agents.size() ; i++ ){
			
			if(getClass().getName()!=_world.agents.get(i).getClass().getName()) {
			
				if(_world.agents.get(i)._x ==_x && _world.agents.get(i)._y ==_y-1 ){ //intrus en haut  
					return 0;
				}
				else if(_world.agents.get(i)._x ==_x+1 && _world.agents.get(i)._y ==_y) {//intrus a l'est
					return 1;	
				}
				else if(_world.agents.get(i)._x ==_x && _world.agents.get(i)._y ==_y+1) {//intrus au sud
					return 2;	
				}
				else if(_world.agents.get(i)._x ==_x-1 && _world.agents.get(i)._y ==_y) {//intrus a l'ouest
					return 3;	
				}
			}
	
		}	
	
		return -1;
	}

 public boolean Chasse() {
	return (Voisinage() != -1);
}

public int get_x() {
	return _x;
}

public void set_x(int _x) {
	this._x = _x;
}

public int get_y() {
	return _y;
}

public void set_y(int _y) {
	this._y = _y;
}

public int get_orient() {
	return _orient;
}

public void set_orient(int _orient) {
	this._orient = _orient;
}

public int get_etat() {
	return _etat;
}

public void set_etat(int _etat) {
	this._etat = _etat;
}

public int get_redValue() {
	return _redValue;
}

public void set_redValue(int _redValue) {
	this._redValue = _redValue;
}

public int get_greenValue() {
	return _greenValue;
}

public void set_greenValue(int _greenValue) {
	this._greenValue = _greenValue;
}

public int get_blueValue() {
	return _blueValue;
}

public void set_blueValue(int _blueValue) {
	this._blueValue = _blueValue;
}



}

