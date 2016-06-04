package website.iidesign.gomoku;


public class MindBord extends Bord{
	
 private boolean mindCount;
	public MindBord() {
		super();
		mindBord = new int[X][Y];
//		mindBord = bord;
		mindCount=count;
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++)
				mindBord[i][j] = -1;
		}
	}
	
	public void sysncMindBord(){
		for (int i = 0; i < Bord.X; i++) {
			for (int j = 0; j < Bord.Y; j++) {
				mindBord[i][j] = Bord.bord[i][j];
			}
		}
	}
	public int[][] getMindBord(){
		return mindBord;
	}
	
	
	public int sarchMind(int x, int y, int val, int pt) {
		switch (pt) {
		case 1:
			return this.getmindStorn(x + val, y);
		case 2:
			return this.getmindStorn(x - val, y);
		case 3:
			return this.getmindStorn(x + val, y + val);
		case 4:
			return this.getmindStorn(x - val, y - val);
		case 5:
			return this.getmindStorn(x + val, y - val);
		case 6:
			return this.getmindStorn(x - val, y + val);
		case 7:
			return this.getmindStorn(x, y + val);
		case 8:
			return this.getmindStorn(x, y - val);
		default:
			return -1;
		}
	}

	public boolean setMindStorn(int x, int y, boolean br) {
		int stone;
		if (x < X && y < Y && mindBord[x][y] == -1 && mindCount == br) { //&& Shinpan.ifFoul(x, y, br)!=-1) {

			stone=br ? 0 : 1;
			mindBord[x][y] = stone;
			mindCount = !br;
			return true;
		} else {
			return false;
		}
	}

	public boolean isStorn(int x, int y) {
		if (x < X && y < Y && mindBord[x][y] != -1) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isBlank(int x, int y) {
		if (x < X && y < Y && 0<X && 0 < Y &&mindBord[x][y] == -1) {
			return false;
		} else {
			return true;
		}
	}

	public int getmindStorn(int x, int y) {
		if (x < 0 || X <= x || y < 0 || X <= y)
			return -2;
		return mindBord[x][y];
	}

}
