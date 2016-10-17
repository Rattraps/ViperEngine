package ratbite.viper.display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener{
	
	private boolean[] keyArray = new boolean[256];
	private boolean safeToCheck = true;
	private boolean safeToChange = true;

	@Override
	public void keyPressed(KeyEvent e) {
		int stop = 0;
		while(!safeToChange){
			stop++;
			if(stop > 200){
				safeToCheck = false;
			}
		}
		
		safeToCheck = false;
		keyArray[e.getKeyCode()] = true;
		safeToCheck = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int stop = 0;
		while(!safeToChange){
			stop++;
			if(stop > 200){
				safeToCheck = false;
			}
		}
		
		safeToCheck = false;
		keyArray[e.getKeyCode()] = false;
		safeToCheck = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//to-do
		
	}
	
	public boolean isHeld(int keyCode){
		int stop = 0;
		while(!safeToCheck){
			stop++;
			if(stop > 100){
				safeToChange = false;
			}
		}
		
		safeToChange = false;
		boolean temp = keyArray[keyCode];
		safeToChange = true;
		
		return temp;
	}

}
