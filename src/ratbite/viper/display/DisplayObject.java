package ratbite.viper.display;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class DisplayObject implements Displayable{
	
	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private ImageIcon image;
	
	private ArrayList<Displayable> children;
	
	public DisplayObject(int x, int y, int w, int h, String imagePath){
		children = new ArrayList<Displayable>();
		
		this.x = x;
		this.y = y;
		
		height = h;
		width = w;
		
		if(imagePath != null){
			setImage(imagePath);
		}
		else{
			System.out.println("Image not found - Resorting to Default.");
			image = new ImageIcon();
		}

	}
	
	public DisplayObject(int x, int y, int w, int h){
		this(x, y, w, h, null);
	}
	
	public DisplayObject(){
		this(0, 0, 0, 0, null);
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
	
	public void setImage(String imagePath){
		try
		{
			image = new ImageIcon(imagePath);
		}
		catch(Exception e)
		{
			System.out.println("Image not found");
		}
	}

	public Image getImage() {
		return image.getImage();
	}
	
	public void setWidth(int w){
		width = w;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int h){
		height = h;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

}
