package ratbite.viper.display;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseListener implements MouseListener{
	
	private boolean mouseDown;
	private int mouseX;
	private int mouseY;
	
	public GameMouseListener(){
		mouseDown = false;
		mouseX = 0;
		mouseY = 0;
	}
	
	public boolean mouseDown(){
		return mouseDown;
	}
	
	public int getY(){
		return mouseY;
	}
	
	public int getX(){
		return mouseX;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		mouseY = e.getY();
		mouseX = e.getX();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		
	}

}
