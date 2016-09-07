package ratbite.viper.example;

import ratbite.viper.*;
import ratbite.viper.display.DisplayObject;
import ratbite.viper.display.GameWindow;

public class TabelCat {
	public static void main(String... args){
		GameWindow mainWindow = new GameWindow("Tabel Cat");
		
		mainWindow.setIcon("C:\\Users\\autum\\Desktop\\mowth.png");
		
		Game.addWindow(mainWindow);
		
		for(int i = 0; i < 500; i++){
			mainWindow.addChild(new DisplayObject((int)(i), (int)(Math.random() * 500), 300, 300, "C:\\Users\\autum\\Desktop\\mowth.png"));
		}
		
		
		Game.start();
		
//		mainWindow.goFullScreen();
		
	}
}
