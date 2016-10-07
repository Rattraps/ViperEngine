package ratbite.viper.example;

import ratbite.viper.*;
import ratbite.viper.display.Animation;
import ratbite.viper.display.DisplayObject;
import ratbite.viper.display.GameWindow;

public class TabelCat {
	public static void main(String... args){
		GameWindow mainWindow = new GameWindow("Tabel Cat");
		
		mainWindow.setIcon("art/mowth.png");
		
		Game.addWindow(mainWindow);
		
		Animation mowth = Animation.makeAnimationFromImage("art/mowth.png");
		Animation corgs = Animation.makeAnimationFromGIF("art/corgi.gif");
		corgs.setDelay(6);
		
		for(int i = 0; i < 500; i++){
			mainWindow.addChild(new DisplayObject((int)(i) * 5, (int)(Math.random() * 300), 300, 300, corgs));
		}
		
		
		Game.start();
		
		//mainWindow.goFullScreen();
		
	}
}
