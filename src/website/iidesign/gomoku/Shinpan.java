package website.iidesign.gomoku;
import java.io.IOException;

import website.iidesign.gomoku.Bord;
public class Shinpan  extends Judge{
	Bord bord;

	public Shinpan(Bord bord) {
		super();
		this.bord=bord;
	}

	protected boolean victoryOrDefeat(int x, int y, boolean br) {
		int storn = br ? 0 : 1;
		int count[]={0,0};
		out: for (int i = 1; i <= 8; i++) {
			count[0]=0;
			for (int j = 1; j < 5; j++) {
				
				if (sarch(x, y, j, i)[0] == storn && j == 4-count[1]) {
					return true;
				} else if (sarch(x, y, j, i)[0] == storn) {
					count[0]++;
				} else {
					count[1]=i%2==0?0:count[0];
					count[0]=0;
					continue out;
				}
			}
		}

		return false;
	}
	
	

	
	public int ifFoul(int x, int y, boolean br){
		//ここに禁じて判定
		if(this.threethreeFaol( x,  y, br))return 0;
		if(this.fourfourFaol( x,  y, br))return 1;
		return -1;
	}
	
	protected int[] sarch(int x, int y, int val, int pt) {
		switch (pt) {
		case 1:
			return new int[] { bord.getStorn(x + val, y), x + val, y };
		case 2:
			return new int[] { bord.getStorn(x - val, y), x - val, y };
		case 3:
			return new int[] { bord.getStorn(x + val, y + val), x + val, y + val };
		case 4:
			return new int[] { bord.getStorn(x - val, y - val), x - val, y - val };
		case 5:
			return new int[] { bord.getStorn(x + val, y - val), x + val, y - val };
		case 6:
			return new int[] { bord.getStorn(x - val, y + val), x - val, y + val };
		case 7:
			return new int[] { bord.getStorn(x, y + val), x, y + val };
		case 8:
			return new int[] { bord.getStorn(x, y - val), x, y - val };
		default:
			return new int[] { -2, 0, 0 };
		}
	}

	public boolean hantei(int x, int y, boolean br) {

		String storn = br ? "__LOS" : "__WIN";
		if (victoryOrDefeat(x, y, br)){
			try {
				bord.endLog(storn);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

}
