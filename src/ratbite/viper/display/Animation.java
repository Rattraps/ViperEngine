package ratbite.viper.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import ratbite.viper.Game;

public class Animation{
	private ArrayList<BufferedImage> frames;
	
	private int currentFrame = 0;
	private boolean playing = false;
	
	private int delay = 0;
	private int delayCounter = 0;
	
	public static Animation makeAnimationFromGIF(String file){
		Animation anim = new Animation();
		
		try {
			ImageReader reader = (ImageReader) ImageIO.getImageReadersByFormatName("gif").next();
			reader.setInput(ImageIO.createImageInputStream(new Object().getClass().getResource(file)));
			for(int iter = 0; iter < reader.getNumImages(true); iter++){
				anim.frames.add(reader.read(iter));
			}
			anim.playing = true;
		} 
		
		catch (Exception e) {
			System.err.println("Failed to load animation (" + file + ") from file.");
		}
		
		
		return anim;
	}
	
	public static Animation makeAnimationFromString(String text, String fontStr, int size, Color color){
		Animation anim = new Animation();
		
		Font font = new Font(fontStr, Font.PLAIN, size);
		
		BufferedImage frame = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = frame.createGraphics();
		
		Rectangle2D rect = font.getStringBounds(text, g.getFontRenderContext());
		
		frame = new BufferedImage((int)rect.getWidth(), (int)rect.getHeight(), BufferedImage.TYPE_INT_ARGB);
		g = frame.createGraphics();
		
		if(color == null){
			color = Color.BLACK;
		}
		
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, 0, (int)rect.getHeight() * 3/5);
		
		
		anim.frames.add(frame);
		return anim;
	}
	
	public static Animation makeAnimationFromFolder(String folder){
		Animation anim = new Animation();
		
		for(int iter = 1; ;iter++){
			URL file = anim.getClass().getResource(folder + "/" + iter + ".png");
			if(file == null){
				break;
			}
			
			try {
				BufferedImage img = ImageIO.read(file);
				anim.frames.add(img);
			} 
			catch (Exception e) {
				System.err.println("Failed to load animation (" + file + ") from file.");
			}
		}
		
		return anim;
	}
	
	public static Animation makeAnimationFromImage(String file){
		Animation anim = new Animation();
		
		try {
			BufferedImage img = ImageIO.read(anim.getClass().getResource(file));
			anim.frames.add(img);
		} 
		catch (Exception e) {
			System.err.println("Failed to load animation (" + file + ") from file.");
		}
		
		return anim;
	}
	
	private Animation(){
		frames = new ArrayList<BufferedImage>();
	}
	
	public Animation(BufferedImage image){
		this();
		frames.add(image);
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
	
	public ArrayList<BufferedImage> getFrames(){
		return frames;
	}

	public Image getImage() {
		if(!frames.isEmpty()){
			Image img = frames.get(currentFrame);
			
			return img;	
		}
		return null;
	}
	
	public void repeat(){
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
		anim.currentFrame = currentFrame;
		anim.delay = delay;
		anim.playing = playing;
		return anim;
	}
	
	public boolean equals(Animation o){
		return o.frames.equals(frames);
		
	}

}
