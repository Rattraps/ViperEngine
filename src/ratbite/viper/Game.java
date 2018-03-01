package ratbite.viper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import java.util.concurrent.TimeUnit;

import ratbite.viper.display.Animation;
import ratbite.viper.display.GameWindow;

public class Game{
	
	private static String gameName = "Viper Game";
	
	private static int fps = 240;
	
	private static ArrayList<GameWindow> windows = new ArrayList<GameWindow>();
	
	public static void end(){
		
		while(!windows.isEmpty()){
			windows.get(0).close();
		}
		
		windows.clear();
		
		System.exit(0);
	}
	
	public static int getFPS(){
		return fps;
	}
	
	public static void setFPS(int f){
		if(f > 0){
			fps = f;
		}
	}
	
	public static void addWindow(GameWindow window){
		window.setVisible(true);
		windows.add(window);
		
		window.repeat();
	}
	
	public static void removeWindow(GameWindow window){
		windows.remove(window);
		
		if(windows.size() == 0){
			end();
		}
	}
	
	public static void setName(String name){
		gameName = name;
	}
	
	public static String getName(){
		return gameName;
	}
}
