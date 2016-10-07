package ratbite.viper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import java.util.concurrent.TimeUnit;

import ratbite.viper.display.GameWindow;

public class Game implements ActionListener{
	
	private static String gameName = "Viper Game";
	
	private static int fps = 240;
	
	private static ArrayList<GameWindow> windows = new ArrayList<GameWindow>();;
	
	
	private static Timer timer;
	
	private long currentTime;
	
	
	public static void start(){
		timer = new Timer((int) TimeUnit.SECONDS.toMillis(1)/fps, new Game());
		
		timer.start();
	}
	
	public static void end(){
		timer.stop();
		
		for(GameWindow window : windows){
			window.close();
		}
		
		windows.clear();
		
		System.exit(0);
	}
	
	public static void setFPS(int f){
		if(f > 0){
			fps = f;
		}
	}
	
	public static void addWindow(GameWindow window){
		window.setVisible(true);
		windows.add(window);
	}
	
	public static void removeWindow(GameWindow window){
		windows.remove(window);
	}
	
	public static void setName(String name){
		gameName = name;
	}
	
	public static String getName(){
		return gameName;
	}

	public void actionPerformed(ActionEvent timerEvent) {
		if(windows.size() == 0){
			end();
		}
		
		//Track FPS in statistics
		GameStatistics.FPS = (int) (TimeUnit.SECONDS.toMillis(1)/(System.currentTimeMillis() - currentTime));
		if(GameStatistics.FPS > fps)
			GameStatistics.FPS = fps;
		
		
		System.out.println(GameStatistics.FPS);
		
		
		currentTime = System.currentTimeMillis();
		
		for(GameWindow window : windows){
			window.repeat();
		}
		
	}
}
