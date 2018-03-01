package ratbite.viper.display;

import java.awt.Image;
import java.util.ArrayList;

public class DisplayObject implements Displayable{
	
	private int x;
	private int y;
	
	private int offsetX;
	private int offsetY;
	
	private int width;
	private int height;
	
	private Animation animation;
	
	private ArrayList<Displayable> children;
	
	private ArrayList<Displayable> deadChildren;
	private boolean resize;
	
	public DisplayObject(int x, int y, int w, int h, Animation anim){
		children = new ArrayList<Displayable>();
		deadChildren = new ArrayList<Displayable>();
		
		this.x = x;
		this.y = y;
		
		offsetX = 0;
		offsetY = 0;
		
		height = h;
		width = w;
		
		resize = true;
		
		setAnimation(anim);

	}
	
	public DisplayObject(int x, int y, int w, int h){
		this(x, y, w, h, null);
	}
	
	public DisplayObject(){
		this(0, 0, 0, 0, null);
	}
	
	public boolean hitObject(DisplayObject obj){
		int objW = Math.abs(obj.getWidth());
		int thisW = Math.abs(getWidth());
		
		int objX = obj.getOffsetX() + obj.getX() - objW/2;
		int thisX = getOffsetX() + getX() - thisW/2;
		
		if(objX <= thisX && objW + objX > thisX){
			return hitObjectY(obj);
		}
		else if(thisX <= objX + objW && thisX + thisW > objX){
			return hitObjectY(obj);
		}
		return false;
	}
	
	private boolean hitObjectY(DisplayObject obj){
		int objH = Math.abs(obj.getHeight());
		int thisH = Math.abs(getHeight());
		
		int objY = obj.getOffsetY() + obj.getY() - objH/2;
		int thisY = getOffsetY() + getY() - thisH/2;
		
		if(objY <= thisY && objH + objY > thisY){
			return true;
		}
		else if(thisY <= objY + objH && thisY + thisH > objY){
			return true;
		}
		return false;
	}

	public void addChild(Displayable d) {
		if(!children.contains(d)){
			setChildOffset(d);
			
			children.add(d);			
		}
	}

	public void removeChild(Displayable d) {
		deadChildren.add(d);
		
	}

	public ArrayList<Displayable> getChildren() {
		return children;
	}
	
	public void setAnimation(Animation anim){
		if(anim != null){
			animation = anim.clone();	
		}
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
		
		for(Displayable child : children){
			setChildOffset(child);
		}
	}
	
	public int getOffsetX(){
		return offsetX;
	}
	
	protected void setOffsetX(int x){
		offsetX = x;
		
		for(Displayable child : children){
			setChildOffset(child);
		}
	}
	
	public int getOffsetY(){
		return offsetY;
	}
	
	protected void setOffsetY(int y){
		offsetY = y;
		
		for(Displayable child : children){
			setChildOffset(child);
		}
	}

	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
		
		for(Displayable child : children){
			setChildOffset(child);
		}
	}
	

	public int getY() {
		return y;
	}
	
	public void resizable(boolean r){
		resize = r;
	}
	
	public boolean isResizable(){
		return resize;
	}
	
	

	public void repeat() {
		for(Displayable d: deadChildren){
			children.remove(d);
		}
		
		deadChildren.clear();
		
	}
	
	private void setChildOffset(Displayable d){
		if(d instanceof DisplayObject){
			DisplayObject dis = (DisplayObject)d;
			dis.setOffsetX(getOffsetX() + getX());
			dis.setOffsetY(getOffsetY() + getY());
		}
	}
	
	public Displayable clone(){
		
		if(animation != null){
			animation.repeat();
		}
		
		Displayable clone = new DisplayObject(getX(), getY(), getWidth(), getHeight(), getAnimation() == null ? null : getAnimation().clone());
		((DisplayObject)clone).resize = resize;
		
		for(Displayable child : children){
			clone.addChild(child.clone());
		}
		
		return clone;
	}

}
