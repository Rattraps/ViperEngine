package ratbite.viper.display;

import java.awt.Image;
import java.util.ArrayList;

public interface Displayable extends Cloneable{
	
	public void addChild(Displayable d);
	public void removeChild(Displayable d);
	public ArrayList<Displayable> getChildren();
	
	public void repeat();
	
	public Image getImage();
	
	public int getWidth();
	public int getHeight();
	public int getX();
	public int getY();
	
	public int getOffsetX();
	public int getOffsetY();
	
	public Displayable clone();
}
