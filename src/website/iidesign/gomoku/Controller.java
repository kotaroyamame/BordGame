package website.iidesign.gomoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import net.arnx.jsonic.JSON;

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

}
