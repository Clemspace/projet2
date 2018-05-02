package Environnement;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import Agents.Agent;
import Agents.PredatorAgent;
import Agents.PreyAgent;

import toolbox.CAImageBuffer;
import toolbox.ImageBuffer;

public class Island {
	
	

	public int _dx;
	public int _dy;
	
	double densiteIni;
	double incendie;
	double repousse;
	
	public Case[][] Buffer0;
	public Case[][] Buffer1;

	boolean buffering;
	boolean cloneBuffer; // if buffering, clone buffer after switch
	
	int activeIndex;
	
	public ArrayList<Agent> agents;
	


	
	public Island(int _dx, int _dy){
		
		
		
		
		this._dx = _dx;
		this._dy = _dy;
		
		densiteIni = 0.3;
		repousse = 0.001;
		incendie = 0.00001;
		
		agents = new ArrayList<Agent>();
		

		Buffer0 = new Case [_dx][_dy];
		Buffer1 = new Case [_dx][_dy];
		for ( int x = 0 ; x != _dx ; x++ )
			for ( int y = 0 ; y != _dy ; y++ ) {
				Buffer0[x][y] = new Case(x,y);
				Buffer1[x][y] = new Case(x,y);

			}
				



//		this.myWorld = new Case [dx][dy];
//		frame = new JFrame("World of Sprite");
//		frame.add(this);
//		frame.setSize(1024,1024);
//		frame.setVisible(true);

	}
//	public void initMonde(int nbpred, int nbproies){
//		
//		for ( int i = 0 ; i != _dx ; i++ )
//			for ( int j = 0 ; j != _dy ; j++ )
//				Buffer0 = new Case [_dx][_dy];
//	}
	
	public void checkBounds( int __x , int __y )
	{
		if ( __x < 0 || __x > _dx || __y < 0 || __y > _dy )
		{
			System.err.println("[error] out of bounds ("+__x+","+__y+")");
			System.exit(-1);
		}
	}
	
	public int[] getCellState ( int __x, int __y )
	{
		checkBounds (__x,__y);
		
		int color[] = new int[3];

		if ( buffering == false )
		{
			color[0] = Buffer0[__x][__y].color[0];
			color[1] = Buffer0[__x][__y].color[1];
			color[2] = Buffer0[__x][__y].color[2];
		}
		else
		{
			if ( activeIndex == 1 ) // read old buffer
			{
				color[0] = Buffer0[__x][__y].color[0];
				color[1] = Buffer0[__x][__y].color[1];
				color[2] = Buffer0[__x][__y].color[2];
			}
			else
			{
				color[0] = Buffer1[__x][__y].color[0];
				color[1] = Buffer1[__x][__y].color[1];
				color[2] = Buffer1[__x][__y].color[2];
			}
		}
		
		return color;
	}
	
	public void setCellState ( int __x, int __y, int __r, int __g, int __b )
	{
		checkBounds (__x,__y);
		
		if ( buffering == false )
		{
			Buffer0[__x][__y].color[0] = __r;
			Buffer0[__x][__y].color[1] = __g;
			Buffer0[__x][__y].color[2] = __b;
		}
		else
		{
			if ( activeIndex == 0 ) // write new buffer
			{
				Buffer0[__x][__y].color[0] = __r;
				Buffer0[__x][__y].color[1] = __g;
				Buffer0[__x][__y].color[2] = __b;
			}
			else
			{
				Buffer1[__x][__y].color[0] = __r;
				Buffer1[__x][__y].color[1] = __g;
				Buffer1[__x][__y].color[2] = __b;
			}
		}
	}
	
	public void setCellState ( int __x, int __y, int __color[] )
	{
		checkBounds (__x,__y);
			
		if ( buffering == false )
		{
			Buffer0[__x][__y].color[0] = __color[0];
			Buffer0[__x][__y].color[1] = __color[1];
			Buffer0[__x][__y].color[2] = __color[2];
		}
		else
		{
			if ( activeIndex == 0 )
			{
				Buffer0[__x][__y].color[0] = __color[0];
				Buffer0[__x][__y].color[1] = __color[1];
				Buffer0[__x][__y].color[2] = __color[2];
			}
			else
			{
				Buffer1[__x][__y].color[0] = __color[0];
				Buffer1[__x][__y].color[1] = __color[1];
				Buffer1[__x][__y].color[2] = __color[2];
			}	
		}
	}
	
	/**
	 * Update the world state and return an array for the current world state (may be used for display)
	 * @return
	 */
	public void step ( )
	{
		stepWorld();
		stepAgents();
		
		if ( buffering && cloneBuffer )
		{
			if ( activeIndex == 0 )
				for ( int x = 0 ; x != _dx ; x++ )
					for ( int y = 0 ; y != _dy ; y++ )
					{
						Buffer1[x][y].color[0] = Buffer0[x][y].color[0];
						Buffer1[x][y].color[1] = Buffer0[x][y].color[1];
						Buffer1[x][y].color[2] = Buffer0[x][y].color[2];
					}
			else
				for ( int x = 0 ; x != _dx ; x++ )
					for ( int y = 0 ; y != _dy ; y++ )
					{
						Buffer0[x][y].color[0] = Buffer1[x][y].color[0];
						Buffer0[x][y].color[1] = Buffer1[x][y].color[1];
						Buffer0[x][y].color[2] = Buffer1[x][y].color[2];
					}

			activeIndex = (activeIndex + 1 ) % 2; // switch buffer index
		}

	}
	
	public Case[][] getCurrentBuffer()
	{
		if ( activeIndex == 0 || buffering == false ) 
			return Buffer0;
		else
			return Buffer1;		
	}
	
	public int getWidth()
	{
		return _dx;
	}
	
	public int getHeight()
	{
		return _dy;
	}
	
	public void add (Agent agent)
	{
		agents.add(agent);
	}
	
	public void stepWorld() // world THEN agents
	{
		this.ForestUpdate();
		//this.ecoulement();
	}
	
	public void stepAgents() // world THEN agents
	{
		for ( int i = 0 ; i != agents.size() ; i++ )
		{
			synchronized ( Buffer0 ) {
				agents.get(i).step();
			}
			
		}
	}
	public void ecoulement() {
		for (int x = 0 ; x != _dx ; x++ ) {
	    	for (int y = 0 ; y != _dy ; y++ ) {
	    		
				if(Buffer0[x][y].getType()<0) {//si la case contient de l'eau
					
					ArrayList<Case> voisins = Buffer0[x][y].getautour(this, x,y);
					
					for (int z = 0; z<voisins.size();z++) {
					
						if (voisins.get(z).getType()<0 && voisins.get(z).getHauteur()>Buffer0[x][y].getHauteur()){//si la case n'a pas d'eau et est plus basse que la case où il y a de l'eau
							int i = voisins.get(z).x;
							int j =voisins.get(z).y;  
							Buffer1[x][y].volWater =(int)Buffer0[x][y].volWater/5;
					
							
							Buffer1[i][j].type = 0;
							Buffer1[i][j].volWater = Buffer0[x][y].volWater/5;
						}
					
					}
				}
		
	    	}
		}
	}
	
	public void InitForet(double densiteIni) {
		
		for (int x = 0 ; x != _dx ; x++ ) {
	    	for (int y = 0 ; y != _dy ; y++ ) {
	    	boolean arbre =  Buffer0[x][y].moisture - 0.4*Buffer0[x][y].hauteur -Buffer0[x][y].temp >= Math.random()*35; //on utlise cette m�thode pour avoir moins d'arbres dans des cas de hauteur ou chaleur extreme
	    		if (arbre && Buffer0[x][y].type >0) { 
	    			Buffer0[x][y].arbre=1; // tree
	    			Buffer0[x][y].type = 2;
	    			
	    		}
	    	}
	    }
		Buffer0[_dx/2][_dy/2].type = 3;
	}
	
	void ForestUpdate() {
		for ( int x = 0 ; x != this.Buffer0.length ; x++ ) {
			for ( int y = 0 ; y != this.Buffer0[0].length ; y++ )
			{
				if(this.Buffer0[x][y].type == 3) { //cas case en feu
					Case[] voisins = {this.Buffer0[(x+_dx)%_dx][(y+_dy-1)%_dy],this.Buffer0[(x+_dx)%_dx][(y+_dy+1)%_dy],
								  	  this.Buffer0[(x+_dx-1)%_dx][(y+_dy)%_dy],this.Buffer0[(x+_dx+1)%_dx][(y+_dy)%_dy]};//von Newmann HBGD
					
					for (int j=0; j != voisins.length; j++) {
						if(voisins[j].type == 2) {
							switch(j) {
							case 0:
								this.Buffer1[(x+_dx)%_dx][(y+_dy-1)%_dy].type = 3;
								break;
							case 1:
								this.Buffer1[(x+_dx)%_dx][(y+_dy+1)%_dy].type = 3;
								break;
							case 2:
								this.Buffer1[(x+_dx-1)%_dx][(y+_dy)%_dy].type = 3;
								break;
							case 3:
								this.Buffer1[(x+_dx+1)%_dx][(y+_dy)%_dy].type = 3;
								break;
																							
							}
							
						}
					}
					ResetBiome(this.Buffer1[x][y]);				}
				
				else if((this.Buffer0[x][y].type >0 && this.Buffer0[x][y].type!=3) && repousse >= Math.random()&&(Buffer0[x][y].moisture - 0.4*Buffer0[x][y].hauteur -Buffer0[x][y].temp >= Math.random()*35)) {
					this.Buffer1[x][y].type = 2;
				}
				else if(this.Buffer0[x][y].type == 2 && incendie >= Math.random()*Buffer0[x][y].temp) {
					this.Buffer1[x][y].type = 3;										
				}
				else if(this.Buffer0[x][y].type == 2 && this.Buffer1[x][y].type== 3){//arbre devenu en feu a cette itération
					this.Buffer1[x][y].type = 3;
				
				}
				
				
				else this.Buffer1[x][y] = this.Buffer0[x][y];
				
			}	
			}
		
		for ( int x1 = 0 ; x1 != Buffer0.length ; x1++ ) {
			for ( int y2 = 0 ; y2 != Buffer0[0].length ; y2++ ) {
				if(Buffer0[x1][y2].type==1){
					
				}
				Buffer0[x1][y2] = Buffer1[x1][y2];
				
			}
		}
	}
	void ForestUpdateAlea(int x, int y) {
		
		if(this.Buffer0[x][y].type == 3) { //cas case en feu
		Case [] voisins = {this.Buffer0[(x+_dx)%_dx][(y+_dy-1)%_dy],this.Buffer0[(x+_dx)%_dx][(y+_dy+1)%_dy],
						  	  this.Buffer0[(x+_dx-1)%_dx][(y+_dy)%_dy],this.Buffer0[(x+_dx+1)%_dx][(y+_dy)%_dy]};//von Newmann HBGD
			
			for (int j=0; j != voisins.length; j++) {
				if(voisins[j].type == 1) {
					switch(j) {
					case 0:
						this.Buffer1[(x+_dx)%_dx][(y+_dy-1)%_dy].type = 2;
						break;
					case 1:
						this.Buffer1[(x+_dx)%_dx][(y+_dy+1)%_dy].type = 2;
						break;
					case 2:
						this.Buffer1[(x+_dx-1)%_dx][(y+_dy)%_dy].type = 2;
						break;
					case 3:
						this.Buffer1[(x+_dx+1)%_dx][(y+_dy)%_dy].type = 2;
						break;
																					
					}
					
				}
			}
			this.Buffer1[x][y].type = 3;
		}
		
		else if((this.Buffer0[x][y].type == 0 ||this.Buffer0[x][y].type == 3) && repousse >= Math.random()) {
			this.Buffer1[x][y].type = 1;
		}
		else if(this.Buffer0[x][y].type == 1 && incendie >= Math.random()) {
			this.Buffer1[x][y].type = 2;										
		}
		else if(this.Buffer0[x][y].type == 1 && this.Buffer1[x][y].type== 2){//arbre devenu en feu a cette itération
			this.Buffer1[x][y].type = 2;
			
		}
		
		
		else this.Buffer1[x][y] = this.Buffer0[x][y];
		
	}	
	
	public void display( CAImageBuffer image )
	{
		image.update(this.getCurrentBuffer());

		for ( int i = 0 ; i != agents.size() ; i++ )
			image.setPixel(agents.get(i).get_x(), agents.get(i).get_y(), agents.get(i).get_redValue(), agents.get(i).get_greenValue(), agents.get(i).get_blueValue());
	}
	public void InitIsland(ImageBuffer hm, ImageBuffer mm,ImageBuffer tm) {
		
		for ( int x = 0 ; x != _dx ; x++ ) {
			for ( int y = 0 ; y != _dy ; y++ ) //on parcourt les 3 images données et on remplit la matrice de cases en fonction
			{
				
				this.Buffer0[x][y].temp = (int)((tm.getRGB(x, y) &0xff)/2.55);
				this.Buffer0[x][y].hauteur = (int)(hm.getRGB(x, y)&0xff);
				this.Buffer0[x][y].moisture =(int) (mm.getRGB(x, y)&0xff);
				this.Buffer0[x][y].fertilite=Buffer0[x][y].moisture;
				this.Buffer0[x][y].x = x;
				this.Buffer0[x][y].y = y;
				
				ResetBiome(this.Buffer0[x][y]);
				
			}
			
		}
		InitForet(densiteIni);
		
		
		
			
		
	}
	public void ResetBiome(Case cell) {
		
		
		if (cell.hauteur<10) {
			cell.type = -1;
			cell.volWater = 100 - (int)Math.random()*50;
		}
		else if (cell.hauteur>70 && cell.temp<70) {
			cell.type = 4;
		}
		else if (cell.temp>80) {
			cell.type = 1;
		
			
		}
		else cell.type = 5 ;
	}
	
}
	


