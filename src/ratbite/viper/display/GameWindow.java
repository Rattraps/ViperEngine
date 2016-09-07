package ratbite.viper.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ratbite.viper.Game;

public class GameWindow extends JFrame implements Displayable{
	
	private GameWindowCloser closer;
	private GamePanel panel;
	
	private ArrayList<Displayable> children;
	
	public GameWindow(String name, int width, int height){
		children = new ArrayList<Displayable>();
		
		setTitle(name);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		
		closer = new GameWindowCloser();
		panel = new GamePanel();
		
		
		getContentPane().add(panel);
		
		addWindowListener(closer);
	}
	
	public GameWindow(String name){
		this(name, 800, 600);
	}
	
	public GameWindow(){
		this(Game.getName());
	}
	
	public void repeat(){
		panel.repaint();
	}
	

	public void addChild(Displayable d) {
		if(!children.contains(d)){
			children.add(d);			
		}
	}

	public void removeChild(Displayable d) {
		children.remove(d);
	}

	public ArrayList<Displayable> getChildren() {
		return (ArrayList<Displayable>) children.clone();
	}
	
	public Image getImage(){
		return panel.createImage(this.getWidth(), this.getHeight());
	}
	
	/**
	 * Warning: Won't support dual monitors
	 */
	public void goFullScreen()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		resize(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
		dispose();
		setUndecorated(true);
		setVisible(true);

	}
	
	public void resize(int X, int Y, int H, int W)
	{
		setBounds(X, Y, W, H);
	}
	
	public void setIcon(String s)
	{
		try
		{
			File f = new File(s);
			System.out.println("Icon retrieved from: " + f.getCanonicalPath());
			BufferedImage i = ImageIO.read(f);
			setIconImage(i);
		}
		catch(IOException e)
		{
			System.out.println("Error: Desired Icon Image Doesn't Exist!");
		}
	}
	
	public void close(){
		System.out.println("Closing window: " + this.getTitle());
		
		removeWindowListener(closer);
		dispose();
		
		Game.removeWindow(this);
	}
	
	private class GamePanel extends JPanel{

		public void paintComponent(Graphics g){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			for(Displayable d : children){
				drawChild(g, d);
			}
		}
		
		private void drawChild(Graphics g, Displayable d){
			g.drawImage(d.getImage(), d.getX(), d.getY(), d.getWidth(), d.getHeight(), null); 
        }
		
	}
	
	private class GameWindowCloser extends WindowAdapter{
		
	    public void windowClosing(WindowEvent windowEvent) {
	    	((GameWindow)windowEvent.getSource()).close();
	    }
	}
}
