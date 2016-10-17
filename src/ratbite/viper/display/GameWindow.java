package ratbite.viper.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ratbite.viper.Game;

public class GameWindow extends JFrame implements Displayable{
	
	private GameWindowCloser closer;
	private GamePanel panel;
	
	private ArrayList<Displayable> children;
	private KeyboardListener keyListener;
	
	public GameWindow(String name, int width, int height){
		children = new ArrayList<Displayable>();
		keyListener = new KeyboardListener();
		
		addKeyListener(keyListener);
		
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
	
	public KeyboardListener getKeyListener(){
		return keyListener;
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
		return children;
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
		resize(0, 0, (int)screenSize.getHeight(), (int)screenSize.getWidth());
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
		Path path = Paths.get(s);
		
		try
		{
			System.out.println("Icon retrieved from: " + path.toAbsolutePath());
			BufferedImage i = ImageIO.read(path.toFile());
			setIconImage(i);
		}
		catch(IOException e)
		{
			System.err.println("Error: Desired Icon Image Doesn't Exist!");
		}
	}
	
	public void close(){
		System.out.println("Closing window: " + this.getTitle());
		
		removeWindowListener(closer);
		dispose();
		
		Game.removeWindow(this);
	}
	
	/*
	 * Not suggested.
	 */
	public GamePanel getGamePanel(){
		return panel;
	}
	
	private class GamePanel extends JPanel{

		public void paintComponent(Graphics g){
			g.setColor(Color.WHITE);
			g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
			for(Displayable d : children){
				d.repeat();
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
