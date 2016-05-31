package website.iidesign.gomoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Computer {

	private ArrayList<int[]> myStone = new ArrayList<int[]>();
	private ArrayList<int[]> humanStone = new ArrayList<int[]>();
	private ArrayList<int[]> brankList = new ArrayList<int[]>();

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
			return new int[] { -2, 0, 0 };
		}
	}

	private int[] ifThree(int x, int y, boolean br, int l) {
		int storn = br ? 0 : 1;
		int count[] = { 0, 0 };
		ArrayList<int[]> xyList = new ArrayList<int[]>();// コマを置く座標
		                                                 // {駒の種類、x座標,y座標,優先順位}のリスト
		out: for (int i = 1; i <= 8; i++) {// 八方
			count[0] = 0;
			for (int j = 1; j <= l; j++) {// n回並んでいる石

				if (this.sarch(x, y, j, i)[0] == storn && j == l - count[1]) {

					if (this.sarch(x, y, j + 1, i)[0] == -1 && this.sarch(x, y, 0, i)[0] == -1) {// nこ並んでなおかつ両端が開いている場合。

						xyList.add(new int[] { this.sarch(x, y, j + 1, i)[0], this.sarch(x, y, j + 1, i)[1],
						    this.sarch(x, y, j + 1, i)[2], 4 });

					} else if (this.sarch(x, y, j + 1, i)[0] == -1) {

						xyList.add(new int[] { this.sarch(x, y, j + 1, i)[0], this.sarch(x, y, j + 1, i)[1],
						    this.sarch(x, y, j + 1, i)[2], 3 });

					} else if (this.sarch(x, y, 0, i)[0] == -1) {

						xyList
						    .add(new int[] { this.sarch(x, y, 0, i)[0], this.sarch(x, y, 0, i)[1], this.sarch(x, y, 0, i)[2], 3 });

					}
				} else if (this.sarch(x, y, j, i)[0] == storn) {
					count[0]++;
				} else {
					count[1] = i % 2 == 0 ? 0 : count[0];
					count[0] = 0;
					continue out;
				}
			}
		}
		if (xyList.isEmpty())
			return new int[] { -2, 0, 0, 0 };
		Collections.sort(xyList, new Comp(3));
		for (int k = 0; k < xyList.size(); k++)
			System.out.println(xyList.get(k)[3]);
		return xyList.get(0);
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

	private void brankCells() {
		brankList.clear();
		for (int i = 0; i < Bord.X; i++) {
			for (int j = 0; j < Bord.Y; j++) {
				if (Bord.bord[i][j] == -1)
					brankList.add(new int[] { i, j });
			}
		}
	}

	private boolean isBlank(int[] xy) {

		this.brankCells();
		for (int[] _xy : brankList) {
			if (_xy[0] == xy[0] && _xy[1] == xy[1])
				return true;
		}
		return false;
	}

	private int[] getHumanStoneBlank(int e, int i) {
		// System.out.println(e);
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
		// 優先順位
		// HashMap<String,Integer> priority=new HashMap<String,Integer>();
		ArrayList<int[]> xyList = new ArrayList<int[]>();// コマを置く座標
		// {駒の種類、x座標,y座標,優先順位}のリスト
		int[][] priority;

		// AIの3つ揃った石を検索
		for (int i1 = 0; i1 < Bord.X; i1++) {
			for (int j1 = 0; j1 < Bord.Y; j1++) {
				if (this.ifThree(i1, j1, true, 4)[0] == -1) {
					xyList.add(new int[] { this.ifThree(i1, j1, true, 4)[1], this.ifThree(i1, j1, true, 4)[2],
					    this.ifThree(i1, j1, true, 4)[3] + 1 });
				}
				if (this.ifThree(i1, j1, true, 3)[0] == -1) {

					xyList.add(new int[] { this.ifThree(i1, j1, true, 3)[1], this.ifThree(i1, j1, true, 3)[2],
					    this.ifThree(i1, j1, true, 3)[3] });

				}
				if (this.ifThree(i1, j1, false, 3)[0] == -1) {

					xyList.add(new int[] { this.ifThree(i1, j1, false, 3)[1], this.ifThree(i1, j1, false, 3)[2],
					    this.ifThree(i1, j1, false, 3)[3] + 1 });

				}
			}
		}
		// 上の条件がなかったら人のおいた石の八方の開いているところにランダムに打つ
		out: for (int i = 0; i < humanStone.size(); i++) {

			int[] xy;
			int count = 0;
			do {
				xy = getHumanStoneBlank((int) Math.floor(Math.random() * 8), i);
				count++;
				if (count > 3)
					continue out;
			} while (!isBlank(xy));

			// System.out.println(xy[0] + " " + xy[1]);

			xyList.add(new int[] { xy[0], xy[1], 1 });

		}

		if (xyList.isEmpty())
			return null;
		Collections.sort(xyList, new Comp(2));

		return xyList.get(0);

	}

	public int[] setStorn() {
		this.serchStone();
		int[] xy = this.isRen(1);
		if (xy != null) {
			return new int[] { xy[0], xy[1] };
		}
		xy = brankList.get((int) Math.floor(Math.random() * brankList.size()));
		return xy;
	}
}
