package website.iidesign.gomoku;

public class Shinpan {
	Bord bord;

	public Shinpan(Bord bord) {
		this.bord = bord;
	}

	private int sarch(int x, int y, int val, int pt) {
		switch (pt) {
		case 1:
			return bord.getStorn(x + val, y);
		case 2:
			return bord.getStorn(x - val, y);
		case 3:
			return bord.getStorn(x + val, y + val);
		case 4:
			return bord.getStorn(x - val, y + val);
		case 5:
			return bord.getStorn(x + val, y - val);
		case 6:
			return bord.getStorn(x - val, y - val);
		case 7:
			return bord.getStorn(x, y + val);
		case 8:
			return bord.getStorn(x, y - val);
		default:
			return -1;
		}
	}

	public void hantei() {
		int count = 0;
		// for(int x=0;x<bord.X;x++){
		// for(int y=0;y<bord.Y;y++){
		// if(bord.isStorn(x, y))
		// count++;
		// }
		// }
	}

	public boolean hantei(int x, int y, boolean br) {
		int storn = br ? 0 : 1;
//		boolean[] flags = new boolean[8];
		//flags������

//		for (int a=0;a<flags.length;a++)
//			flags[a] = false;
		
		out:for (int i = 1; i <= 8; i++) {
			System.out.println(i);
			for(int j=1;j<5;j++){
				if (this.sarch(x, y, j, i) == storn&&j==4) {
					return true;
				}else if(this.sarch(x, y, j, i) == storn){
					
				}else{
					continue out;
				}
			}		
		}
//		for(int k=0;k<flags.length;k++)
//			if(flags[k])return true;
		return false;
	}

}
