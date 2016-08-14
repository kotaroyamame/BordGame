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

public class Controller implements Initializable {
	@FXML
	private Canvas canvas;
	@FXML
	private Label text1;
	@FXML
	private Button restart;
	private GraphicsContext gc;
	private Bord bord;
	private Shinpan shinpan;
	private boolean finish = false;
	private Computer com;
	private AI ai;

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
		ai = new AI(bord,shinpan );
	}

	@FXML
	private void onRestart() {
		this.init();
	}


	@FXML
	public void onLearning() {
//		this.init();
//		for (int i0 = 0; i0 < 2000000000;i0++)
//		for (int i1 = 0; i1 < 2000000000;i1++)
//		for (int i2 = 0; i2 < 2000000000;i2++)
//		for (int i3 = 0; i3 < 2000000000;i3++)
		for (int i = 0; i < 2;i++) {
			this.init();
			out:for (;;) {
				if (finish||bord.getTekazu()>8)
					break out;

				aiRanch2();

				if (finish)
					return;
				aiRanch();
			}
		
		}
	}

	@FXML
	private void clickCanvas(MouseEvent e) {
		
		// for(int i=0;i<20;i++){
//		for (;;) {
			if (finish)
				return;
			// ここから人

			 int _x = (int) Math.floor(e.getX()) / bord.SIZE;
			 int _y = (int) Math.floor(e.getY()) / bord.SIZE;
			
			 int hbr=shinpan.ifFoul(_x, _y, true);
			 if(hbr==-1){
			 text1.setText("あなたの番です");

			// ここまで人

//			aiRanch2();

			// ここから人

			 boolean hSet=bord.setStorn(_x, _y, true);
			 if (hSet){
			 if (shinpan.hantei(_x, _y, true)) {
				 text1.setText("あなたの勝ちです");
				 	finish = true;
				 }
			 }
			 }else if(shinpan.ifFoul(_x, _y, true)==0){
				 text1.setText("先手の三々は反則です");
			 }

			// ここまで人
			 if (finish)
					return;
				comRanch();
//		}
	}

	private void comRanch() {
		int[] comStone = com.setStorn();
		boolean aSet = bord.setStorn(comStone[0], comStone[1], false);
		if (aSet) {
			if (shinpan.hantei(comStone[0], comStone[1], false)) {
				text1.setText("後手コンピュータの勝ちです");
				finish = true;
			}
		}
	}

	private void comRanch2() {
		int[] comStone = com.setStorn();
		int hbr = shinpan.ifFoul(comStone[0], comStone[1], true);
		if (hbr == -1) {
			boolean aSet = bord.setStorn(comStone[0], comStone[1], true);
			if (aSet) {
				if (shinpan.hantei(comStone[0], comStone[1], true)) {
					text1.setText("先手コンピュータの勝ちです");
					finish = true;
				}
			}
		}
	}

	private void aiRanch() {
		int[] comStone = ai.setStorn(bord.bord, false, bord.getTekazu());
		boolean aSet = bord.setStorn(comStone[0], comStone[1], false);
		if (aSet) {
			if (shinpan.hantei(comStone[0], comStone[1], false)) {
				text1.setText("後手AIの勝ちです");
				finish = true;
			}
		}
	}

	private void aiRanch2() {
		int[] comStone = ai.setStorn(bord.bord, false, bord.getTekazu());
		int hbr = shinpan.ifFoul(comStone[0], comStone[1], true);
		if (hbr == -1) {
			boolean aSet = bord.setStorn(comStone[0], comStone[1], true);
			if (aSet) {
				if (shinpan.hantei(comStone[0], comStone[1], true)) {
					text1.setText("先手AIの勝ちです");
					finish = true;
				}
			}
		}
	}

}
