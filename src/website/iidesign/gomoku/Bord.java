package website.iidesign.gomoku;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bord {

	public final static int X = 15;
	public final static int Y = 15;
	public final int SIZE = 20;
	public final boolean IRON = false;
	public final boolean INITIATIVE = true;
	private boolean count = INITIATIVE;
	public static int bord[][];
	private GraphicsContext gc;

	public Bord(GraphicsContext gc) {
		this.gc = gc;
		bord = new int[X][Y];
		init();
		strokeLine();
	}

	private void init() {
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++)
				bord[i][j] = -1;
		}
		gc.clearRect(0, 0, 1000, 1000);
	}

	private void strokeLine() {
		gc.setStroke(Color.BLACK);

		for (int i = 0; i < Y; i++) {
			gc.strokeLine(0 + SIZE / 2, i * SIZE + SIZE / 2, X * SIZE - SIZE / 2, i * SIZE + SIZE / 2);
			gc.strokeLine(i * SIZE + SIZE / 2, 0 + SIZE / 2, i * SIZE + SIZE / 2, Y * SIZE - SIZE / 2);
		}

	}

	private void drowBord(int x, int y, boolean br) {
		if (br)
			gc.setFill(Color.BLACK);
		else
			gc.setFill(Color.WHITE);
		gc.fillOval(x * SIZE, y * SIZE, SIZE, SIZE);
	}
	
	public static int sarch(int x, int y, int val, int pt) {
		switch (pt) {
		case 1:
			return Bord.getStorn(x + val, y);
		case 2:
			return Bord.getStorn(x - val, y);
		case 3:
			return Bord.getStorn(x + val, y + val);
		case 4:
			return Bord.getStorn(x - val, y - val);
		case 5:
			return Bord.getStorn(x + val, y - val);
		case 6:
			return Bord.getStorn(x - val, y + val);
		case 7:
			return Bord.getStorn(x, y + val);
		case 8:
			return Bord.getStorn(x, y - val);
		default:
			return -1;
		}
	}

	public boolean setStorn(int x, int y, boolean br) {
		if (x < X && y < Y && Bord.bord[x][y] == -1 && count == br) { //&& Shinpan.ifFoul(x, y, br)!=-1) {

			this.drowBord(x, y, br);
			Bord.bord[x][y] = br ? 0 : 1;
			count = !br;
			return true;
		} else {
			return false;
		}
	}

	public boolean isStorn(int x, int y) {
		if (x < X && y < Y && Bord.bord[x][y] != -1) {
			return true;
		} else {
			return false;
		}
	}

	public static int getStorn(int x, int y) {
		if (x < 0 || X <= x || y < 0 || X <= y)
			return -2;
		return Bord.bord[x][y];
	}

}
