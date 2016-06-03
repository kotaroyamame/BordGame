package website.iidesign.gomoku;

import java.util.ArrayList;
import java.util.Collections;

public class Judge {
	MindBord mindBord;
	
	protected ArrayList<int[]> aiStone = new ArrayList<int[]>();
	protected ArrayList<int[]> humanStone = new ArrayList<int[]>();
	protected ArrayList<int[]> brankList = new ArrayList<int[]>();

	public Judge(MindBord mindBord) {
		this.mindBord=mindBord;
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
	protected int[] ifThree(int x, int y, boolean br, int l) {
		int storn = br ? 0 : 1;
		int count[] = { 0, 0 };
		ArrayList<int[]> xyList = new ArrayList<int[]>();// コマを置く座標
		                                                 // {駒の種類、x座標,y座標,優先順位}のリスト
		out: for (int i = 1; i <= 8; i++) {// 八方
			count[0] = 0;
			for (int j = 1; j <= l; j++) {// n回並んでいる石

				if (this.sarch(x, y, j, i)[0] == storn && j == l - count[1]) {

					if (this.sarch(x, y, j + 1, i)[0] == -1 && this.sarch(x, y, 0, i)[0] == -1) {// nこ並んでなおかつ両端が開いている場合。
						if (count[1] == 0)
							xyList.add(new int[] { this.sarch(x, y, j + 1, i)[0], this.sarch(x, y, j + 1, i)[1],
							    this.sarch(x, y, j + 1, i)[2], 4 });
						else
							xyList.add(
							    new int[] { this.sarch(x, y, 0, i)[0], this.sarch(x, y, 0, i)[1], this.sarch(x, y, 0, i)[2], 5 });

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
		return xyList.get(0);
	}
	protected int[] sarch(int x, int y, int val, int pt) {
		switch (pt) {
		case 1:
			return new int[] { mindBord.getmindStorn(x + val, y), x + val, y };
		case 2:
			return new int[] { mindBord.getmindStorn(x - val, y), x - val, y };
		case 3:
			return new int[] { mindBord.getmindStorn(x + val, y + val), x + val, y + val };
		case 4:
			return new int[] { mindBord.getmindStorn(x - val, y - val), x - val, y - val };
		case 5:
			return new int[] { mindBord.getmindStorn(x + val, y - val), x + val, y - val };
		case 6:
			return new int[] { mindBord.getmindStorn(x - val, y + val), x - val, y + val };
		case 7:
			return new int[] { mindBord.getmindStorn(x, y + val), x, y + val };
		case 8:
			return new int[] { mindBord.getmindStorn(x, y - val), x, y - val };
		default:
			return new int[] { -2, 0, 0 };
		}
	}
	protected int[] getAiStoneBlank(int e, int i) {
		// System.out.println(e);
		switch (e) {
		case 0:
			return new int[] { aiStone.get(i)[0], aiStone.get(i)[1] - 1 };
		case 1:
			return new int[] { aiStone.get(i)[0] + 1, aiStone.get(i)[1] - 1 };
		case 2:
			return new int[] { aiStone.get(i)[0] + 1, aiStone.get(i)[1] };
		case 3:
			return new int[] { aiStone.get(i)[0] + 1, aiStone.get(i)[1] + 1 };
		case 4:
			return new int[] { aiStone.get(i)[0], aiStone.get(i)[1] + 1 };
		case 5:
			return new int[] { aiStone.get(i)[0] - 1, aiStone.get(i)[1] + 1 };
		case 6:
			return new int[] { aiStone.get(i)[0] - 1, aiStone.get(i)[1] };
		case 7:
			return new int[] { aiStone.get(i)[0] - 1, aiStone.get(i)[1] - 1 };
		}
		return null;
	}

	
	protected int[] threeThree(int x, int y, boolean br) {
		int storn = br ? 0 : 1;
		int[][] count = { { 0, 0 }, { 0, 0 } };// {{三が出現した現在,過去ログ},{飛び石用現在,飛び石用過去ログ未使用}}
		boolean flag = false;
		// ArrayList<int[]> xyList = new ArrayList<int[]>();// コマを置く座標
		out: for (int i = 1; i <= 8; i++) {

			count[0][0] = 0;
			count[1][0] = 0;
			int k = 0;
			for (int j = 1; j < 3 + k; j++) {

				if (mindBord.sarchMind(x, y, j, i) == storn && j == (2 + k) - count[0][1] && mindBord.sarchMind(x, y, j + 1, i) == -1
				    && mindBord.sarchMind(x, y, 0, i) == -1) {
					if (count[0][1] == 0) {
						if (flag) {
							flag = false;
							return new int[] { -1, x, y };
						}
						flag = true;
					} else if (mindBord.sarchMind(x, y, -j - 1, i) == -1) {
						if (flag) {
							flag = false;
							return new int[] { -1, x, y };
						}
						flag = true;
					}

				} else if (mindBord.sarchMind(x, y, j, i) == storn) {
					count[0][0]++;
				} else if (mindBord.sarchMind(x, y, j, i) == -1 && count[1][0] < 1) {// 飛び石三
					count[1][0]++;
					k = 1;
				} else {
					count[0][1] = i % 2 == 0 ? 0 : count[0][0];
					count[0][0] = 0;
					continue out;
				}
			}
		}

		return new int[] { -2, 0, 0 };
	}
	
	

}
