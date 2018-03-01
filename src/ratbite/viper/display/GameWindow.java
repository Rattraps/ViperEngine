package ratbite.viper.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.TimerTask;

import ratbite.viper.Game;

public class GameWindow extends JFrame{
	
	//screen alignment
	public final static int ALIGN_LEFT = 0;
	public final static int ALIGN_RIGHT = 2;
	public final static int ALIGN_X_MID = 1;
	public final static int ALIGN_Y_MID = 1;
	public final static int ALIGN_TOP = 0;
	public final static int ALIGN_BOTTOM = 2;
	
	//core
	private GameWindowCloser closer;
	private GamePanel panel;
	
	//actionable child objects
	private ArrayList<Displayable> children;
	private KeyboardListener keyListener;
	private GameMouseListener mouseListener;
	
	private DisplayObject screenDim;
	
	//threads
	private static Timer graphicsTimer;
	private static java.util.Timer logicTimer;
	
	//
	private boolean isCopying = true;
	private boolean isRepeating = false;
	
	private long currentTime = 0;
	
	//size values
	private int originalWidth;
	private int originalHeight;
	private int widthMod;
	private int heightMod;
	
	//x and y modifiers (related too size and alignment
	private int xMod;
	private int yMod;
	
	private boolean fullscreen = false;
	
	
	public GameWindow(String name, int width, int height){
		
		children = new ArrayList<Displayable>();
		keyListener = new KeyboardListener();
		mouseListener = new GameMouseListener();
		
		addKeyListener(keyListener);
		addMouseListener(mouseListener);
		
		
		setTitle(name);
		
		originalWidth = width;
		originalHeight = height;
		widthMod = width;
		heightMod = height;
		xMod = 0;
		yMod = 0;

		
		setSize(width,height);
		changeScreenDim();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setResizable(false);
		
		closer = new GameWindowCloser();
		panel = new GamePanel();
		panel.setOpaque(false);
		
		
		getContentPane().add(panel);
		getContentPane().setBackground(Color.BLACK);
		
		addWindowListener(closer);
	}
	
	
	public GameWindow(String name){
		this(name, 800, 600);
	}
	
	public GameWindow(){
		this(Game.getName());
	}
	
	public void repeat(){
		int time = (int) TimeUnit.SECONDS.toMillis(1)/Game.getFPS();
		graphicsTimer = new javax.swing.Timer(time, new GraphicThread());
		logicTimer = new java.util.Timer();
		logicTimer.schedule(new LogicThread(),time,time);

		graphicsTimer.start();
	}
	
	public KeyboardListener getKeyListener(){
		return keyListener;
	}
	
	public GameMouseListener getMouseListener(){
		return mouseListener;
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
		dispose();
		setUndecorated(true);
		setVisible(true);
		setResolution((int)screenSize.getWidth(), (int)screenSize.getHeight(), false);
		
		fullscreen = true;
	}
	
	public void resize(int W, int H, int xAlign, int yAlign){
		heightMod = H;
		widthMod =(int) (heightMod * 1.0 * originalWidth/originalHeight);
		System.out.println(widthMod + "x" + heightMod);
		setAlignment(xAlign, yAlign);
		changeScreenDim();
	}
	
	public void setResolution(int W, int H, boolean scaleSize)
	{
		if(fullscreen || scaleSize){
			resize(W, H, ALIGN_X_MID, ALIGN_Y_MID);
		}
		
		if(!fullscreen){
			System.out.println("New Window Bounds: " + W + "x" + H);
			setBounds(0, 0, W, H);	
			changeScreenDim();
		}
	}
	
	private void changeScreenDim(){
		int w = (int) (1.0 * originalWidth * getWidth()/widthMod) ;
		int h = (int) (1.0 * originalHeight * getHeight()/heightMod);
	
		int x = (int) (w/2 - (xMod * 1.0 * originalWidth/widthMod));
		int y = (int) (h/2 - (yMod * 1.0 * originalHeight/heightMod));
		screenDim = new DisplayObject(x, y, w, h);
	}
	
	public void setAlignment(int xAlign, int yAlign){
		
		switch(xAlign){
			case ALIGN_RIGHT:
				xMod = getWidth() - widthMod;
				break;
			case ALIGN_LEFT:
				xMod = 0;
				break;
			default:
			case ALIGN_X_MID:
				xMod = (int) ((1.0*getWidth()/widthMod - 1.0) * widthMod/2);
				break;
		}
		
		switch(xAlign){
			case ALIGN_BOTTOM:
				yMod = heightMod - getHeight();
				break;
			case ALIGN_TOP:
				yMod = 0;
				break;
			default:
			case ALIGN_Y_MID:
				yMod = (int) ((1.0*getHeight()/heightMod - 1.0) * heightMod/2);
				break;
		}
		
		changeScreenDim();
	}
	
	public void setXMod(int mod){
		xMod = mod;
		changeScreenDim();
	}
	
	public int getXMod(){
		return xMod;
	}
	
	public void setYMod(int mod){
		yMod = mod;
		changeScreenDim();
	}
	
	public int getYMod(){
		return yMod;
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
		
		graphicsTimer.stop();
		logicTimer.cancel();
		
		removeWindowListener(closer);
		removeKeyListener(keyListener);
		dispose();
		
		Game.removeWindow(this);
	}
	
	public void performLogic(){

		for(Displayable d : children){
			d.repeat();
			recurseChildren(d);
		}
	}
	
	private void recurseChildren(Displayable parent){
		
		ArrayList<Displayable> kids = parent.getChildren();
		
		for(Displayable d : kids){
			d.repeat();
			recurseChildren(d);
		}
	}
	
	public GameWindow clone(){
		GameWindow clone = new GameWindow(getName(), getWidth(), getHeight());
		for(Displayable child : children){
			clone.addChild(child.clone());
		}
		
		return clone;
	}
	
	/*
	 * Not suggested.
	 */
	public GamePanel getGamePanel(){
		return panel;
	}
	
	private class GamePanel extends JPanel{
		
		public void paintComponent(Graphics g){
			
			if(isRepeating){
				synchronized(graphicsTimer){
					try {
						graphicsTimer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			isCopying = true;
			
			GameWindow cloneWindow = GameWindow.this.clone();
			
			isCopying = false;
			
			synchronized(logicTimer){
				logicTimer.notify();
			}
				
			for(Displayable d : cloneWindow.getChildren()){
				drawChild(g, d);
				recurseChildren(d, g);
			}
			
		}	
			
		
		private void recurseChildren(Displayable parent, Graphics g){
			
			ArrayList<Displayable> kids = parent.getChildren();
			
			for(Displayable d : kids){
				drawChild(g, d);
				recurseChildren(d, g);
			}
		}
		
		private void drawChild(Graphics g, Displayable d){
			
			boolean visible = true;
			
			boolean needsResize = !(d instanceof DisplayObject) || ((DisplayObject)d).isResizable();
			
			if(d instanceof DisplayObject && needsResize){
				DisplayObject obj = (DisplayObject)d;
				if(!obj.hitObject(screenDim)){
					visible = false;
				}
			}
			
			if(visible){
				
				int x = d.getX() - d.getWidth()/2;
				int y = d.getY() - d.getHeight()/2;
				
				x += d.getOffsetX();
				y += d.getOffsetY();
				
				int w = d.getWidth();
				int h = d.getHeight();
				
				if(needsResize){
					x *= 1.0 * widthMod/originalWidth;
					y *= 1.0 * heightMod/originalHeight;
					
		
					x += xMod;
					y += yMod;

							
					w *= 1.0 * widthMod/originalWidth;
					h *= 1.0 * heightMod/originalHeight;
				}
				
				
				g.drawImage(d.getImage(), x, y, w, h, null); 
			}
			
			
			
        }
		
	}
	
	private class GraphicThread implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			panel.repaint();
			
		}
		
	}
	
	private class LogicThread extends TimerTask{

		public void run() {
			
			if(isCopying){
				synchronized(logicTimer){
					try {
						logicTimer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			isRepeating = true;
			
			performLogic();
			
			isRepeating = false;
			
			synchronized(graphicsTimer){
				graphicsTimer.notify();
			}
			
			int distance = (int) (System.currentTimeMillis() - currentTime);

			currentTime = System.currentTimeMillis();
			
		}
		
	}
	
	private class GameWindowCloser extends WindowAdapter{
		
	    public void windowClosing(WindowEvent windowEvent) {
	    	((GameWindow)windowEvent.getSource()).close();
	    }
	}

}
