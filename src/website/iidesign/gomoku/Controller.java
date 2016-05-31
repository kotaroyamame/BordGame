package website.iidesign.gomoku;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller implements Initializable {
	@FXML
	private Canvas canvas;
	@FXML
	private Label text1;
	@FXML
	private Button restart;
	GraphicsContext gc;
	Bord bord;
	Shinpan shinpan;
	private boolean finish = false;
	private Computer com;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		init();
	}

	private void init() {
		gc = canvas.getGraphicsContext2D();
		shinpan = new Shinpan();
		bord = new Bord(gc);
		finish = false;
		text1.setText("");
		com = new Computer();
	}

	@FXML
	private void onRestart() {
		this.init();
	}

	@FXML
	private void clickCanvas(MouseEvent e) {
		if (finish)
			return;
		int _x = (int) Math.floor(e.getX()) / bord.SIZE;
		int _y = (int) Math.floor(e.getY()) / bord.SIZE;
		if (bord.setStorn(_x, _y, true))
			if (shinpan.hantei(_x, _y, true)) {
				text1.setText("あなたの勝ちです");
				finish = true;
			}
		if (finish)
			return;
		int[] comStone = com.setStorn();
		if (bord.setStorn(comStone[0], comStone[1], false))
			if (shinpan.hantei(comStone[0], comStone[1], false)) {
				text1.setText("コンピュータの勝ちです");
				finish = true;
			}
	}

}
