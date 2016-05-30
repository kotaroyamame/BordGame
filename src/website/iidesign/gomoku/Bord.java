package website.iidesign.gomoku;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bord {

	public final int X = 30;
	public final int Y = 30;
	public final int SIZE = 20;
	public final boolean IRON=false;
	public final boolean INITIATIVE=true;
	private boolean count=INITIATIVE;
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
	}

	private void strokeLine() {
		gc.setStroke(Color.BLACK);

		for (int i = 0; i < Y; i++) {
			gc.strokeLine(0, i * SIZE, X * SIZE,i * SIZE);
			gc.strokeLine( i * SIZE,0, i * SIZE,Y * SIZE);
		}

	}

	private void drowBord(int x, int y, boolean br) {
		if (br)
			gc.setFill(Color.RED);
		else
			gc.setFill(Color.BLUE);
		gc.fillRect(x * SIZE, y * SIZE, SIZE, SIZE);
	}

	public boolean setStorn(int x, int y, boolean br) {
		if (x < X && y < Y && Bord.bord[x][y] == -1&&count==br) {

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
	
	public int getStorn(int x, int y){
		if(x<0||X<x||y<0||X<y)
			return -1;
		return Bord.bord[x][y];
	}

}
