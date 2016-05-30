package website.iidesign.gomoku;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller  implements Initializable {
	@FXML
	private Canvas canvas;
	GraphicsContext gc;
	Bord bord;
	Shinpan shinpan;

	@Override
  public void initialize(URL location, ResourceBundle resources) {
		gc=canvas.getGraphicsContext2D();
		bord=new Bord(gc);
		shinpan=new Shinpan(bord);
		init();
	}
	
	private void init(){

	}
	@FXML
	private void clickCanvas(MouseEvent e){
		int _x=(int)Math.floor(e.getX())/bord.SIZE;
		int _y=(int)Math.floor(e.getY())/bord.SIZE;
		System.out.println(_x+" "+_y);
		bord.setStorn(_x,_y,true);
		shinpan.hantei();
	}

}
