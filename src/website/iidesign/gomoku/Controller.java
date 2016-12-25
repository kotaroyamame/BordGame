package website.iidesign.gomoku;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager.Property;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable {
	@FXML
	private Canvas canvas;
	@FXML
	private Label text1;
	@FXML
	private Button restart;
	@FXML
	private MenuItem English;
	@FXML
	private MenuItem Japanise;
	GraphicsContext gc;
	Bord bord;
	Shinpan shinpan;
	private boolean finish = false;
	private Computer com;
	private String nowLang="en";
	private static Boolean comLunchflg=false;
	private Lang lang=new Lang();
	private Words words;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		init();
	}

	private void init() {
		gc = canvas.getGraphicsContext2D();
		bord = new Bord();
		shinpan = new Shinpan(bord);
		bord = new Bord(gc);
		finish = false;
		text1.setText("");
		com = new Computer();
		this.words=lang.getLangObj(nowLang);
	}
	@FXML
	private void onSound(){
		this.bord.soundOn();
	}
	
	@FXML
	private void offSound(){
		this.bord.soundMute();
	}
	
	@FXML
	private void setLanguageEnglish(){
		
		this.setLanguage("en");
		
	}
	
	@FXML
	private void setLanguageJapanise(){
		
		this.setLanguage("ja");
		
	}
	private void setLanguage(String val){
		
		this.nowLang = val;
		
		this.words = this.lang.getLangObj(this.nowLang);
		
		restart.setText(words.getRestart());
		
		
	}

	@FXML
	private void onRestart() {
		this.init();
	}


	@FXML
	private void clickCanvas(MouseEvent e) {
			if (finish||comLunchflg)
				return;

			 int _x = (int) Math.floor(e.getX()) / bord.SIZE;
			 int _y = (int) Math.floor(e.getY()) / bord.SIZE;
			
			 int hbr=shinpan.ifFoul(_x, _y, true);
			 if(hbr==-1){
				 text1.setText(words.getYouTrun());
	
				 boolean hSet=bord.setStorn(_x, _y, true);
				 if (hSet){
					 if (shinpan.hantei(_x, _y, true)) {
						 text1.setText(words.getYouWin());
						 sound("trumpet1");
						 finish = true;
					 }
				 }
			 }else if(hbr==0){
				 text1.setText(words.getFaul_3_3());
				 return;
			 }

			 if (finish)
					return;
			 
			 Task<Boolean> comTask = new Task<Boolean>(){
			    @Override
			    protected Boolean call() throws Exception{
			    	int[] comStone = com.setStorn();
			  		
			  		Platform.runLater(() ->text1.setText(words.getWait()));
			  		
			  		Thread.sleep( 1400 );
			  		
			  		boolean aSet = bord.setStorn(comStone[0], comStone[1], false);
			  		if (aSet) {
			  			if (shinpan.hantei(comStone[0], comStone[1], false)) {
			  				Platform.runLater(() ->text1.setText(words.getYouLost()));
			  				sound("j-13");
			  				finish = true;
			  			}else{
			  				Platform.runLater(() ->text1.setText(words.getYouTrun()));
			  			}
			  		}  
			  		comLunchflg=false;
			  		
			      return true; 
			    }
			 };
			 
			 try{
				 Thread t = new Thread( comTask );
				 t.setDaemon( true );
				 comLunchflg=true;
				 t.start();
			 }catch(Exception e1){
				 
				 System.err.println(e1);
				 
			 }
			
	}
	private void sound(String fileName){
		Clip clip = null;
		AudioInputStream audioInputStream;
        try{   
            audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(fileName+".wav")));
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
        }
        catch (UnsupportedAudioFileException e)
        {   e.printStackTrace();  }
        catch (IOException e)
        {   e.printStackTrace();  }
        catch (LineUnavailableException e)
        {   e.printStackTrace();  }
	}
}
