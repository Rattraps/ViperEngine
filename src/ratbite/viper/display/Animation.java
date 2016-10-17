package ratbite.viper.display;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class Animation{
	private ArrayList<BufferedImage> frames;
	
	private int currentFrame = 0;
	private boolean playing = false;
	
	private int delay = 0;
	private int delayCounter = 0;
	
	public static Animation makeAnimationFromGIF(String file){
		Animation anim = new Animation();
		
		Path path = Paths.get(file);
		
		try {
			ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("gif").next();
			reader.setInput(ImageIO.createImageInputStream(path.toFile()));
			for(int iter = 0; iter < reader.getNumImages(true); iter++){
				anim.frames.add(reader.read(iter));
			}
			anim.playing = true;
		} 
		
		catch (Exception e) {
			System.err.println("Failed to load animation from file.");
		}
		
		
		return anim;
	}
	
	public static Animation makeAnimationFromImage(String file){
		Animation anim = new Animation();
		
		Path path = Paths.get(file);
		
		try {
			BufferedImage img = ImageIO.read(path.toFile());
			anim.frames.add(img);
		} 
		catch (IOException e) {
			System.err.println("Failed to load animation from file.");
		}
		
		return anim;
	}
	
	private Animation(){
		frames = new ArrayList<BufferedImage>();
	}
	
	public Animation(ArrayList<BufferedImage> images){
		if(images == null){
			frames = new ArrayList<BufferedImage>();
		}
		else{
			frames = images; 
			playing = true;
		}
	}

	public Image getImage() {
		if(!frames.isEmpty()){
			Image img = frames.get(currentFrame);
			
			if(playing){
				delayCounter++;
				
				if(delayCounter > delay){
					delayCounter = 0;
					
					currentFrame++;
					if(currentFrame >= frames.size()){
						currentFrame = 0;
					}
				}
			}
			
			return img;	
		}
		return null;
	}
	
	public void setCurrentFrame(int frame){
		currentFrame = frame;
	}
	
	public int getCurrentFrame(){
		return currentFrame;
	}
	
	public void play(){
		playing = true;
	}
	
	public void stop(){
		playing = false;
	}
	
	public void setDelay(int d){
		delay = d;
	}
	
	public boolean isPlaying(){
		return playing;
	}
	
	public Animation clone(){
		Animation anim = new Animation();
		anim.frames = this.frames;
		anim.delay = delay;
		anim.playing = playing;
		return anim;
	}

}
