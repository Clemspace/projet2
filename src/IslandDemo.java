import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
		
		myWorld =  
		
		for ( int i = 0 ; i != 32 ; i++ )
			for ( int j = 0 ; j != 32 ; j++ )
				myWorld.tilemap[i][j] = new Case();
	}

	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		for ( int i = 0 ; i < myWorld.dx ; i++ )
			for ( int j = 0 ; j < myWorld.dy ; j++ )
			{
				if ( myWorld[i][j] == 0 )
					g2.drawImage(waterSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				else
					if ( myWorld[i][j] == 1 )
						g2.drawImage(treeSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				else
					if(myWorld[i][j]==2)
						g2.drawImage(snowSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				else
					if ( myWorld[i][j] == 3 )
						g2.drawImage(treeSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				else
					if ( myWorld[i][j] == 4 )
						g2.drawImage(grassSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
				
					else	
						g2.drawImage(sandSprite,spriteLength*i,spriteLength*j,spriteLength,spriteLength, frame);
					
			}
	}

	public static void main(String[] args) {
		new SpriteDemo();
	}
}

}
