package ratbite.viper.example;

import java.awt.event.KeyEvent;

import ratbite.viper.display.Animation;
import ratbite.viper.display.DisplayObject;
import ratbite.viper.display.KeyboardListener;

public class ExampleSprite extends DisplayObject {
	
	private KeyboardListener keyListener;
	
	public ExampleSprite(int x, int y, int w, int h, Animation anim, KeyboardListener key){
		super(x, y, w, h, anim);
		keyListener = key;
	}
	
	@Override
	public void repeat(){
		if(keyListener != null)
		{
			if(keyListener.isHeld(KeyEvent.VK_A)){
				setX(getX()-1);
			}
			if(keyListener.isHeld(KeyEvent.VK_S)){
				setY(getY()+1);
			}
			if(keyListener.isHeld(KeyEvent.VK_D)){
				setX(getX()+1);
			}
			if(keyListener.isHeld(KeyEvent.VK_W)){
				setY(getY()-1);
			}
		}
	}
}
