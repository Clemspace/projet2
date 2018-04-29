import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Island {
	
	private JFrame frame;
	
	private Image MoistureMap;
	private Image HeightMap;
	private Image TempMap;
	
	private int spriteLength = 32;

	public int dx;
	public int dy;
	public Case[][] myWorld;
	ArrayList<Agent> agents;


	
	public Island(int _dx, int _dy){
		try
		{
			this.MoistureMap = ImageIO.read(new File("moisture.png"));
			this.HeightMap = ImageIO.read(new File("island.png"));	//initialisation des données pour la création de l'ile
			this.TempMap = ImageIO.read(new File("temper.png"));

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		this.dx = _dx;
		this.dy = _dy;
		agents = new ArrayList<Agent>();


		this.myWorld = new Case [dx][dy];
		frame = new JFrame("World of Sprite");
		frame.add(this);
		frame.setSize(1024,1024);
		frame.setVisible(true);

	}
	public void initMonde(int nbpred, int nbproies){
		
		for ( int i = 0 ; i != dx ; i++ )
			for ( int j = 0 ; j != dy ; j++ )
				myWorld[i][j] = initCase();
	}
	

}
