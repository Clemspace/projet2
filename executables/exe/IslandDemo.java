package exe;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Environnement.Case;
import Environnement.Island;

public class IslandDemo extends JPanel {
	
	private JFrame frame;
	
	private Image waterSprite;
	private Image grassSprite;
	private Image treeSprite;
	private Image snowSprite;
	private Image sandSprite;
	
	private int spriteLength = 32;
	
	private Island myWorld;

	public IslandDemo(Island myWorld)
	{
		try
		{
			waterSprite = ImageIO.read(new File("water.png"));
			treeSprite = ImageIO.read(new File("tree.png"));
			grassSprite = ImageIO.read(new File("grass.png"));
			snowSprite = ImageIO.read(new File("snow.png"));
			sandSprite = ImageIO.read(new File("sand.png"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		frame = new JFrame("MonkiIsland");
		frame.add(this);
		frame.setSize(1024,1024);
		frame.setVisible(true);
		
		
		
		for ( int i = 0 ; i != 1024; i++ )
			for ( int j = 0 ; j != 1024 ; j++ )
				myWorld.Buffer0[i][j] = new Case();
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		for ( int i = 0 ; i < myWorld._dx ; i++ )
			for ( int j = 0 ; j < myWorld._dy ; j++ )
			{
				if ( myWorld.Buffer0[i][j].type == 0 )
					g2.drawImage(waterSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				else
					if ( myWorld.Buffer0[i][j].type == 1 )
						g2.drawImage(treeSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				else
					if(myWorld.Buffer0[i][j].type==2)
						g2.drawImage(snowSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				else
					if ( myWorld.Buffer0[i][j].type == 3 )
						g2.drawImage(treeSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				else
					if ( myWorld.Buffer0[i][j].type == 4 )
						g2.drawImage(grassSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				
					else	
						g2.drawImage(sandSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
					
			}
	}

	public static void main(String[] args) {
		new SpriteDemo();
	}
}


