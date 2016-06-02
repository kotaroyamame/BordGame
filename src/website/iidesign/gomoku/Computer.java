package website.iidesign.gomoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Computer {

	private ArrayList<int[]> aiStone = new ArrayList<int[]>();
	private ArrayList<int[]> humanStone = new ArrayList<int[]>();
	private ArrayList<int[]> brankList = new ArrayList<int[]>();
	private boolean human = true;
	private boolean ai = false;
	private static int COUNT=0;
	
	MindBord mindBord;
	

	public Computer() {
		mindBord = new MindBord();
		serchStone();
	}

	private int[] sarch(int x, int y, int val, int pt) {
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

	private void serchStone() {
		aiStone.clear();
		humanStone.clear();
		for (int i = 0; i < Bord.X; i++)
			for (int j = 0; j < Bord.Y; j++) {
				if (mindBord.getmindStorn(i,j) == 1) {
					aiStone.add(new int[] { i, j });
				} else if (mindBord.getmindStorn(i,j) == 0) {
					humanStone.add(new int[] { i, j });
				}
			}
	}

	private void brankCells() {
		brankList.clear();
		for (int i = 0; i < Bord.X; i++) {
			for (int j = 0; j < Bord.Y; j++) {
				if (mindBord.getMindBord()[i][j] == -1)
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

	private int[] getAiStoneBlank(int e, int i) {
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

	private int[] threeThree(int x, int y, boolean br) {
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

	public int[] isRen(int l) {//最終的には最適なxy値を返す
		int anticipate=1;//先読みの手数
		
		if(10<COUNT){
			anticipate=10;
		}
		ArrayList<int[]> xyList = new ArrayList<int[]>();// コマを置く座標
		ArrayList<int[]> aiPoint = new ArrayList<int[]>();
		ArrayList<int[]> humanPoint = new ArrayList<int[]>();
		//現在先手の予想の際に禁じ手を考慮していないので後で実装する
		int[][] patten={
				{30,5,131,6,4,3,33,6},//優先順位  {相手の四を止める,相手の三を止める,自分の五を打つ,自分の四を打つ,自分の三を打つ,自分の二を打つ,自分の三三を打つ,相手の三三を止める}
				{30,5,131,6,4,3,33,6},
				{30,5,131,6,4,3,33,6},
				{30,5,131,6,4,3,33,6},
				{30,5,131,6,4,3,33,6},
				{30,5,131,6,4,3,33,6},
				{30,5,131,6,4,3,33,6},
				{30,5,131,6,4,3,33,6}
		};
		//pointData{index(p),相手ポイントの合計,AIポイントの合計,次の手のx座標,次の手のx座標}*patten.length
		int[][] pointData=new int[patten.length][5];
		// {駒の種類、x座標,y座標,優先順位}のリスト
		// int[][] priority;
		for(int p = 0; p < patten.length; p++){
			serchStone();
			brankCells();
			mindBord.sysncMindBord();//仮想ボードを現実のボートと同期
			aiPoint.clear();
			humanPoint.clear();
			for (int i0 = 1; i0 <= anticipate; i0++) {
				xyList.clear();
				// 人の3つ揃った石を検索
				for (int i1 = 0; i1 < Bord.X; i1++) {
					for (int j1 = 0; j1 < Bord.Y; j1++) {
						if (this.ifThree(i1, j1, i0%2==0?ai:human, 4)[0] == -1) {
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?ai:human, 4)[1], this.ifThree(i1, j1, i0%2==0?ai:human, 4)[2],
							    this.ifThree(i1, j1, i0%2==0?ai:human, 4)[3] + patten[p][0] });
						}
						if (this.ifThree(i1, j1, i0%2==0?ai:human, 3)[0] == -1) {
	
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?ai:human, 3)[1], this.ifThree(i1, j1, i0%2==0?ai:human, 3)[2],
							    this.ifThree(i1, j1, i0%2==0?ai:human, 3)[3] + patten[p][1] });
	
						}
						if (this.ifThree(i1, j1, i0%2==0?human:ai, 5)[0] == -1) {
	
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?human:ai, 5)[1], this.ifThree(i1, j1, i0%2==0?human:ai, 5)[2],
							    this.ifThree(i1, j1, i0%2==0?human:ai, 5)[3] + patten[p][2] });
	
						}
						if (this.ifThree(i1, j1, i0%2==0?human:ai, 4)[0] == -1) {
	
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?human:ai,4)[1], this.ifThree(i1, j1, i0%2==0?human:ai, 4)[2],
							    this.ifThree(i1, j1, i0%2==0?human:ai, 4)[3] +  patten[p][3]});
	
						}
						if (threeThree(i1, j1, i0%2==0?human:ai)[0] == -1) {
	
							xyList.add(new int[] { i1, j1, patten[p][6] });
	
						}
						if (this.ifThree(i1, j1, i0%2==0?human:ai, 3)[0] == -1) {
	
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?human:ai, 3)[1], this.ifThree(i1, j1, i0%2==0?human:ai, 3)[2],
							    this.ifThree(i1, j1, i0%2==0?human:ai, 3)[3] + patten[p][4] });
	
						}
						if (this.ifThree(i1, j1, i0%2==0?human:ai, 2)[0] == -1) {
	
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?human:ai, 2)[1], this.ifThree(i1, j1, i0%2==0?human:ai, 2)[2], patten[p][5] });
	
						}
					}
				}
				// 上の条件がなかったら自分のおいた石の八方の開いているところにランダムに打つ
				out: for (int i = 0; i < aiStone.size(); i++) {
	
					int[] xy;
					int count = 0;
					do {
						xy = getAiStoneBlank((int) Math.floor(Math.random() * 8), i);
						count++;
						if (count > 3)
							continue out;
					} while (!isBlank(xy));
	
					// System.out.println(xy[0] + " " + xy[1]);
	
					xyList.add(new int[] { xy[0], xy[1], i0%2==0?2:3 });
	
				}
	
				out: for (int i3 = 0; i3 < humanStone.size(); i3++) {
//	System.out.println("humanStone"+humanStone.size());
					int[] xy;
					int count = 0;
					do {
						xy = getHumanStoneBlank((int) Math.floor(Math.random() * 8), i3);
						count++;
						if (count > 3)
							continue out;
					} while (!isBlank(xy));
	
					// System.out.println(xy[0] + " " + xy[1]);
	
					xyList.add(new int[] { xy[0], xy[1], i0%2==0?3:2 });
	
				}
				if (xyList.isEmpty()){
					int i9=0;
					for(;;){
						if(isBlank(new int[] { 6+i9++, 6})){
							xyList.add(new int[] { 6+i9, 6, 1 });
							break;
						}
					}
				}
				Collections.sort(xyList, new Comp(2));
				//仮想ボードに石をセット
	//			boolean currentHA=false;
	//			currentHA=i0%2==0?ai:human;
				mindBord.setMindStorn(xyList.get(p%xyList.size())[0], xyList.get(p%xyList.size())[1], i0%2==0?ai:human);
				if(i0%2==0)
					humanPoint.add(xyList.get(0));
				else
					aiPoint.add(xyList.get(0));
					
			}
			int humanP=0,aiP=0;
			for(int j0=0;j0<humanPoint.size();j0++){
				humanP=humanP+humanPoint.get(j0)[2];
			}
			for(int j1=0;j1<aiPoint.size();j1++){
				aiP=aiP+aiPoint.get(j1)[2];
			}
			pointData[p][0]=p;
			pointData[p][1]=humanP;
			pointData[p][2]=aiP;
			pointData[p][3]=aiPoint.get(0)[0];
			pointData[p][4]=aiPoint.get(0)[1];
//			System.out.println(aiPoint.get(0)[0]+" y "+aiPoint.get(0)[1]+"p"+p);
		}
		if(COUNT++==0)
			return new int[]{pointData[3][3],pointData[3][4]};
		//ここにポイントの大小で最適手の判定処理
		
//		//まずは相手のポイントが低い順にソート
//		Arrays.sort(pointData,new Comparator<int[]>(){
//			@Override
//			public int compare(int[] arg0, int[] arg1) {
//				return arg0[1]-arg1[1]>0?1:arg0[1]-arg1[1]==0?0:-1;
//			}});
//		
	//次にAIのポイントが高い順にソート
		Arrays.sort(pointData,new Comparator<int[]>(){
			@Override
			public int compare(int[] arg0, int[] arg1) {
				return arg0[2]-arg1[2]>0?-1:arg0[2]-arg1[2]==0?0:1;
			}});
		System.out.println("優先順位");
		for (int ff = 0; ff <pointData.length ; ff++) {
			System.out.println("x"+pointData[ff][3]+" y "+pointData[ff][4]);
		}
		
		
		return new int[]{pointData[0][3],pointData[0][4]};
	}

	public int[] setStorn() {
		this.serchStone();
		int[] xy = this.isRen(1);
		if (xy != null) {
//			System.out.println(xy[0]+" y "+xy[1]);
			return new int[] { xy[0], xy[1] };
		}
		xy = brankList.get((int) Math.floor(Math.random() * brankList.size()));
		return xy;
	}
}
