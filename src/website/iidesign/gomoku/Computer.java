package website.iidesign.gomoku;

import java.util.ArrayList;

public class Computer {

	private ArrayList<int[]> myStone = new ArrayList<int[]>();
	private ArrayList<int[]> humanStone = new ArrayList<int[]>();

	public Computer() {
		serchStone();
	}

	private int[] sarch(int x, int y, int val, int pt) {
		switch (pt) {
		case 1:
			return new int[] { Bord.getStorn(x + val, y), x + val, y };
		case 2:
			return new int[] { Bord.getStorn(x - val, y), x - val, y };
		case 3:
			return new int[] { Bord.getStorn(x + val, y + val), x + val, y + val };
		case 4:
			return new int[] { Bord.getStorn(x - val, y - val), x - val, y - val };
		case 5:
			return new int[] { Bord.getStorn(x + val, y - val), x + val, y - val };
		case 6:
			return new int[] { Bord.getStorn(x - val, y + val), x - val, y + val };
		case 7:
			return new int[] { Bord.getStorn(x, y + val), x, y + val };
		case 8:
			return new int[] { Bord.getStorn(x, y - val), x, y - val };
		default:
			return new int[] { -1, 0, 0 };
		}
	}

	private int[] ifThree(int x, int y, boolean br) {
		int storn = br ? 0 : 1;
		int count[]={0,0};
		out: for (int i = 1; i <= 8; i++) {//八方
			count[0]=0;
			for (int j = 1; j < 3; j++) {//n回並んでいる石
				
				if (this.sarch(x, y, j, i)[0] == storn && j == 2-count[1]) {
					if(this.sarch(x, y, j+1, i)[0]==-1){
						return this.sarch(x, y, j+1, i);
					}else if(this.sarch(x, y, 0, i)[0]==-1){
						return this.sarch(x, y, 0, i);
					}
				} else if (this.sarch(x, y, j, i)[0] == storn) {
					count[0]++;
				} else {
					count[1]=i%2==0?0:count[0];
					count[0]=0;
					continue out;
				}
			}
		}

		return new int[]{-2,0,0};
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

	private boolean isBlank(int[] xy) {
		ArrayList<int[]> brankList = new ArrayList<int[]>();
		brankList.clear();
		brankList.addAll(this.brankCells());
		for (int[] _xy : brankList) {
			if (_xy[0] == xy[0] && _xy[1] == xy[1])
				return true;
		}
		return false;
	}

	private int[] getHumanStoneBlank(int e, int i) {
		System.out.println(e);
		switch (e) {
		case 0:
			return new int[] { humanStone.get(i)[0], humanStone.get(i)[1] - 1 };
		case 1:
			return new int[] { humanStone.get(i)[0] + 1, humanStone.get(i)[1] - 1 };
		case 2:
			return new int[] { humanStone.get(i)[0] + 1, humanStone.get(i)[1] };
		case 3:
			return new int[] { humanStone.get(i)[0] + 1, humanStone.get(i)[1] + 1 };
		case 4:
			return new int[] { humanStone.get(i)[0], humanStone.get(i)[1] + 1 };
		case 5:
			return new int[] { humanStone.get(i)[0] - 1, humanStone.get(i)[1] + 1 };
		case 6:
			return new int[] { humanStone.get(i)[0] - 1, humanStone.get(i)[1] };
		case 7:
			return new int[] { humanStone.get(i)[0] - 1, humanStone.get(i)[1] - 1 };
		}
		return null;
	}

	public int[] isRen(int l) {
		for (int i1 = 0; i1 < Bord.X; i1++) {
			for (int j1 = 0; j1 < Bord.X; j1++) {
				if (this.ifThree(i1, j1, false)!=null&this.ifThree(i1, j1, false)[0]==-1) {

					return new int[]{this.ifThree(i1, j1, false)[1],this.ifThree(i1, j1, false)[2]};

				}
			}
		}

		out: for (int i = 0; i < humanStone.size(); i++) {

			int[] xy;
			int count = 0;
			do {
				xy = getHumanStoneBlank((int) Math.floor(Math.random() * 8), i);
				count++;
				if (count > 3)
					continue out;
			} while (!isBlank(xy));

			System.out.println(xy[0] + " " + xy[1]);
			return xy;

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
