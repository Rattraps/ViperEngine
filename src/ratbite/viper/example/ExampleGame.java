package ratbite.viper.example;

import java.awt.Color;
import java.nio.file.Path;
import java.nio.file.Paths;

import ratbite.viper.Game;
import ratbite.viper.display.Animation;
import ratbite.viper.display.DisplayObject;
import ratbite.viper.display.GameWindow;

public class ExampleGame {
	
	public static void main(String... args){
		GameWindow mainWindow = new GameWindow("Filler Name", 1280, 1024);
		//5/4 ratio
		
		Game.setFPS(60);
		
		Animation sampleAnimation = Animation.makeAnimationFromImage("art/GOODART.png");
		
		mainWindow.getContentPane().setBackground(Color.WHITE); 
		
		DisplayObject child = new DisplayObject(400, 400, 100, 100, sampleAnimation);
		
		mainWindow.addChild(child);
		 
		Game.addWindow(mainWindow);
		
		mainWindow.setAlignment(GameWindow.ALIGN_X_MID, GameWindow.ALIGN_BOTTOM);
	}

}
