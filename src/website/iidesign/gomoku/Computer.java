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
			return new int[] { humanStone.get(i)[0]+1, humanStone.get(i)[1] };
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
		out:for (int i = 0; i < humanStone.size(); i++) {

			int[] xy;
			int count = 0;
			do {
				xy = getHumanStoneBlank((int) Math.floor(Math.random() * 8), i);
				count++;
				if(count>3)continue out;
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
