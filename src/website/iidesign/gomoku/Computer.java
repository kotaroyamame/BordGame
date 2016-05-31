package website.iidesign.gomoku;

import java.util.ArrayList;

public class Computer {

	private ArrayList<int[]> myStone = new ArrayList<int[]>();
	private ArrayList<int[]> humanStone = new ArrayList<int[]>();

	public Computer() {
		serchStone();
	}

	private void serchStone() {
		myStone.clear();
		humanStone.clear();
		for (int i = 0; i < Bord.X; i++)
			for (int j = 0; j < Bord.Y; j++) {
				if (Bord.bord[i][j] == 1) {
					myStone.add(new int[] { i, j });
				} else if (Bord.bord[i][j] == 0) {
					humanStone.add(new int[] { i, j });
				}
				;
			}
	}

	private ArrayList<int[]> brankCells() {
		ArrayList<int[]> brankList = new ArrayList<int[]>();
		brankList.clear();
		for (int i = 0; i < Bord.X; i++) {
			for (int j = 0; j < Bord.Y; j++) {
				if (Bord.bord[i][j] == -1)
					brankList.add(new int[] { i, j });
			}
		}
		return brankList;
	}

	public int[] isRen(int l) {
		for (int i = 0; i < humanStone.size(); i++) {
			// for(int j=0;j<humanStone.size();j++){
			// int x=humanStone.get(i)[0] - humanStone.get(j)[0];
			// int y=humanStone.get(i)[1] - humanStone.get(j)[1];
			ArrayList<int[]> brankList = new ArrayList<int[]>();
			brankList.clear();
			brankList.addAll(this.brankCells());

			// if(l==Math.abs(x)&&l==Math.abs(y)){
			boolean isBlank = false;
			int[] xy = { humanStone.get(i)[0], humanStone.get(i)[1] + 1 };
			for(int[] _xy:brankList){
				if(_xy[0]==xy[0]&&_xy[1]==xy[1])
					isBlank=true;
			}
			System.out.println(isBlank+" "+i);
			if (!isBlank) {
				xy[0] = humanStone.get(i)[0] + 1;
				xy[1] = humanStone.get(i)[1];
				for(int[] _xy:brankList){
					if(_xy[0]==xy[0]&&_xy[1]==xy[1])
						isBlank=true;
				}
			}

			if (!isBlank) {
				xy[0] = humanStone.get(i)[0];
				xy[1] = humanStone.get(i)[1] - 1;
				for(int[] _xy:brankList){
					if(_xy[0]==xy[0]&&_xy[1]==xy[1])
						isBlank=true;
				}
			}

			if (!isBlank) {
				xy[0] = humanStone.get(i)[0] - 1;
				xy[1] = humanStone.get(i)[1];
				for(int[] _xy:brankList){
					if(_xy[0]==xy[0]&&_xy[1]==xy[1])
						isBlank=true;
				}
			}

			if (isBlank) {
				System.out.println(xy[0] + " " + xy[1]);
				return xy;
			}
			// }
			// }
		}

		return null;
	}

	public int[] setStorn() {
		this.serchStone();
		int[] xy = this.isRen(1);
		if (xy != null) {
			return new int[] { xy[0], xy[1] };
		}
		ArrayList<int[]> brankList = this.brankCells();
		xy = brankList.get((int) Math.floor(Math.random() * brankList.size()));
		return xy;
	}
}
