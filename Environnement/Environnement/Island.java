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
	
	
	private ImageBuffer MoistureMap;
	private ImageBuffer HeightMap;	//infos nécessaires a la crea d'un monde interessant et coherent
	private ImageBuffer TempMap;
	

	public int _dx;
	public int _dy;
	public Case[][] Buffer0;
	public Case[][] Buffer1;

	boolean buffering;
	boolean cloneBuffer; // if buffering, clone buffer after switch
	
	int activeIndex;
	
	public ArrayList<Agent> agents;
	public ArrayList<PredatorAgent> Predateurs;
	public ArrayList<PreyAgent> Proies;


	
	public Island(int _dx, int _dy){
		
		
		try
		{
			File Height = new File("island.png");
			File Moist = new File("moisture.png");
			File Temp = new File("temper.png");

			static ImageBuffer MoistureMap = LoadFromDisk("moisture.png");//new ImageBuffer(1024,1024); // method 1 : call constructor
			HeightMap =null; //new ImageBuffer(1024,1024);	//initialisation des données pour la création de l'ile
			TempMap =null;//new ImageBuffer(1024,1024);
			//MoistureMap = loadFromDisk("moisture.png");

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		this._dx = _dx;
		this._dy = _dy;
		agents = new ArrayList<Agent>();
		Predateurs = new ArrayList<PredatorAgent>();
		Proies = new ArrayList<PreyAgent>();

		Buffer0 = new Case [_dx][_dy];
		Buffer1 = new Case [_dx][_dy];



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
		// ...
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
	
	public void display( CAImageBuffer image )
	{
		image.update(this.getCurrentBuffer());

		for ( int i = 0 ; i != agents.size() ; i++ )
			image.setPixel(agents.get(i)._x, agents.get(i)._y, agents.get(i)._redValue, agents.get(i)._greenValue, agents.get(i)._blueValue);
	}
	public void initIsland(Island world, File hm, File mm,File tm) {
		
		for ( int x = 0 ; x != _dx ; x++ )
			for ( int y = 0 ; y != _dy ; y++ ) //on parcourt les 3 images données et on remplit la matrice de cases en fonction
			{
				
				
			}
				
		
	}
	
}
	


