package website.iidesign.gomoku;

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
	private static String nowLang="en";
	
	@SuppressWarnings("serial")
	public final static HashMap<String,HashMap<String,String> > langObject =new HashMap<String,HashMap<String,String> >(){
		{
		put("restart",new HashMap<String,String>(){
			{
				put("en","Restart");
				put("ja","再スタート"); 
			}
			});
		put("youWin",new HashMap<String,String>(){
			{
				put("en","YouWin!!");
				put("ja","あなたの勝ちです"); 
			}
			});
		put("youLost",new HashMap<String,String>(){
			{
				put("en","YouLost");
				put("ja","あなたの負けです"); 
			}
			});
		put("faul_3-3",new HashMap<String,String>(){
			{
				put("en","It\'s faul");
				put("ja","先手の三々は反則です"); 
			}
			});
		put("yourTurn",new HashMap<String,String>(){
			{
				put("en","It's your turn");
				put("ja","あなたの番です"); 
				
			}
			});
		put("wait",new HashMap<String,String>(){
			{
				put("en","Please wait");
				put("ja","コンピュータ思考中"); 
				
			}
			});
		}
	};
	
	private static Boolean comLunchflg=false;


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
		
		restart.setText(langObject.get("restart").get(val));
		
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
				 text1.setText(langObject.get("yourTurn").get(nowLang));
	
				 boolean hSet=bord.setStorn(_x, _y, true);
				 if (hSet){
					 if (shinpan.hantei(_x, _y, true)) {
						 text1.setText(langObject.get("youWin").get(nowLang));
						 finish = true;
					 }
				 }
			 }else if(hbr==0){
				 text1.setText(langObject.get("faul_3-3").get(nowLang));
				 return;
			 }

			 if (finish)
					return;
			 
			 Task<Boolean> comTask = new Task<Boolean>(){
			    @Override
			    protected Boolean call() throws Exception{
			    	int[] comStone = com.setStorn();
			  		
			  		Platform.runLater(() ->text1.setText(langObject.get("wait").get(nowLang)));
			  		
			  		Thread.sleep( 1400 );
			  		
			  		boolean aSet = bord.setStorn(comStone[0], comStone[1], false);
			  		if (aSet) {
			  			if (shinpan.hantei(comStone[0], comStone[1], false)) {
			  				Platform.runLater(() ->text1.setText(langObject.get("youLost").get(nowLang)));
			  				finish = true;
			  			}else{
			  				Platform.runLater(() ->text1.setText(langObject.get("yourTurn").get(nowLang)));
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
