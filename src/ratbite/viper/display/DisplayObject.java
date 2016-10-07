package ratbite.viper.display;

import java.awt.Image;
import java.util.ArrayList;

public class DisplayObject implements Displayable{
	
	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private Animation animation;
	
	private ArrayList<Displayable> children;
	
	public DisplayObject(int x, int y, int w, int h, Animation anim){
		children = new ArrayList<Displayable>();
		
		this.x = x;
		this.y = y;
		
		height = h;
		width = w;
		
		setAnimation(anim);

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
		return children;
	}
	
	public void setAnimation(Animation anim){
		animation = anim.clone();
	}
	
	public Animation getAnimation(){
		return animation;
	}

	public Image getImage() {
		if(animation != null){
			return animation.getImage();
		}
		return null;
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

	public void repeat() {
		
	}

}
