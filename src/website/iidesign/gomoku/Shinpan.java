package website.iidesign.gomoku;
import java.io.IOException;

import website.iidesign.gomoku.Bord;
public class Shinpan {

	public Shinpan() {
	}

	private boolean victoryOrDefeat(int x, int y, boolean br) {
		int storn = br ? 0 : 1;
		int count[]={0,0};
		out: for (int i = 1; i <= 8; i++) {
			count[0]=0;
			for (int j = 1; j < 5; j++) {
				
				if (sarch(x, y, j, i) == storn && j == 4-count[1]) {
					return true;
				} else if (sarch(x, y, j, i) == storn) {
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
	
	private static boolean threethreeFaol(int x, int y, boolean br) {
		int storn = br ? 0 : 1;
		int[][] count={{0,0},{0,0}};//{{三が出現した現在,過去ログ},{飛び石用現在,飛び石用過去ログ未使用}}
		boolean flag=false;
		out: for (int i = 1; i <= 8; i++) {
			
			count[0][0]=0;
			count[1][0]=0;
			int k=0;
			for (int j = 1; j < 3+k; j++) {
				
				if (sarch(x, y, j, i) == storn && j == (2+k)-count[0][1]&& sarch(x, y, j+1, i) ==-1 && sarch(x, y, 0, i) ==-1) {
					if(count[0][1]==0){	
						if(flag){
							flag=false;
							return true;
						}
						flag=true;
					}else if(sarch(x, y, -j-1, i) ==-1){
						if(flag){
							flag=false;
							return true;
						}
						flag=true;
					}
					
					
					
				} else if (sarch(x, y, j, i) == storn) {
					count[0][0]++;
				}else if (sarch(x, y, j, i) == -1&&count[1][0]<1){//飛び石三
					count[1][0]++;
					k=1;
				} else {
					count[0][1]=i%2==0?0:count[0][0];
					count[0][0]=0;
					continue out;
				}
			}
		}

		return false;
	}

	private static int sarch(int x, int y, int val, int pt) {
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

//	public void hantei() {
//		int count = 0;
//
//	}
	
	public static int ifFoul(int x, int y, boolean br){
		//ここに禁じて判定
		if(threethreeFaol( x,  y, br))return 0;
		return -1;
	}

	public boolean hantei(int x, int y, boolean br) {

		int storn = br ? 0 : 1;
		if (victoryOrDefeat(x, y, br)){
			try {
				Bord.endLog();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

}
