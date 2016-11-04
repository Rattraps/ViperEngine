package ratbite.viper.example;

import ratbite.viper.*;
import ratbite.viper.display.Animation;
import ratbite.viper.display.GameWindow;

public class TabelCat {
	public static void main(String... args){
		
		GameWindow mainWindow = new GameWindow("Tabel Cat", 800, 800);
		
		mainWindow.setIcon("art/mowth.png");
		
		Game.addWindow(mainWindow);
		
		Animation mowth = Animation.makeAnimationFromImage("art/mowth.png");
		Animation corgs = Animation.makeAnimationFromGIF("art/corgi.gif");
		corgs.setDelay(6);
		
		Animation goodart = Animation.makeAnimationFromImage("art/GOODART.png");
		Animation spicy = Animation.makeAnimationFromImage("art/spicy.png");
		
		ExampleSprite exm = new ExampleSprite(100, 100, 100, 100, goodart, mainWindow.getKeyListener());
		
		mainWindow.addChild(exm);
	
		
		Game.start();
		
		//mainWindow.goFullScreen();
		
	}
}
