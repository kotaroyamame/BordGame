package website.iidesign.gomoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Computer extends Judge {

	
	private boolean human = true;
	private boolean ai = false;
	private static int COUNT=0;
	
	public Computer() {
		super(new MindBord());
		serchStone();
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


	public int[] isRen(int l) {//最終的には最適なxy値を返す
		int anticipate=1;//先読みの手数
		
		if(10<COUNT){
			anticipate=2;
		}
		ArrayList<int[]> xyList = new ArrayList<int[]>();// コマを置く座標
		ArrayList<int[]> aiPoint = new ArrayList<int[]>();
		ArrayList<int[]> humanPoint = new ArrayList<int[]>();
		//現在先手の予想の際に禁じ手を考慮していないので後で実装する
		int[][] patten={
				{110,76,231,60,12,5,55,95},//優先順位  {相手の四を止める,相手の三を止める,自分の五を打つ,自分の四を打つ,自分の三を打つ,自分の二を打つ,自分の三三を打つ,自分の四三を打つ}
				{110,76,231,60,12,5,55,95},
				{110,76,231,60,12,5,55,95},
				{110,76,231,60,12,5,55,95},
				{110,76,231,60,12,5,55,95},
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
				patten=new int[][]{
						{110,76,231,60,12,5,55,95},//優先順位  {相手の四を止める,相手の三を止める,自分の五を打つ,自分の四を打つ,自分の三を打つ,自分の二を打つ,自分の三三を打つ,自分の四三を打つ}
						{110,76,231,60,12,5,55,95},
						{110,76,231,60,12,5,55,95},
						{110,76,231,60,12,5,55,95},
						{110,76,231,60,12,5,55,95},
				};
				xyList.clear();
				// 人の3つ揃った石を検索
				for (int i1 = 0; i1 < Bord.X; i1++) {
					for (int j1 = 0; j1 < Bord.Y; j1++) {
						//4
						if (this.ifThree(i1, j1, i0%2==0?ai:human, 4)[0] == -1) {
							System.out.println("相手の四を止める"+"x="+i1+"y="+ j1);
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?ai:human, 4)[1], this.ifThree(i1, j1, i0%2==0?ai:human, 4)[2],
							    this.ifThree(i1, j1, i0%2==0?ai:human, 4)[3] + patten[p][0] });
						}
						//3
						if (this.ifThree(i1, j1, i0%2==0?ai:human)[0] == -1) {
							System.out.println("相手の三を止める"+"x="+i1+"y="+ j1);
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?ai:human)[1], this.ifThree(i1, j1, i0%2==0?ai:human)[2],
							    this.ifThree(i1, j1, i0%2==0?ai:human)[3] + patten[p][1] });
	
						}
//						if (this.ifThree(i1, j1, i0%2==0?human:ai, 5)[0] == -1) {
//							System.out.println("自分の5を打つ"+"x="+i1+"y="+ j1);
//							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?human:ai, 5)[1], this.ifThree(i1, j1, i0%2==0?human:ai, 5)[2],
//							    this.ifThree(i1, j1, i0%2==0?human:ai, 5)[3] + patten[p][2] });
//	
//						}
						if (this.ifThree(i1, j1, i0%2==0?human:ai, 4)[0] == -1) {
							System.out.println("自分の5を打つ"+"x="+i1+"y="+ j1);
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?human:ai,4)[1], this.ifThree(i1, j1, i0%2==0?human:ai, 4)[2],
							    this.ifThree(i1, j1, i0%2==0?human:ai, 4)[3] +  patten[p][2]});
	
						}
						if (threeThree(i1, j1, i0%2==0?human:ai)[0] == -1) {
							System.out.println("自分の三三"+"x="+i1+"y="+ j1);
							xyList.add(new int[] { i1, j1, patten[p][6] });
	
						}
						if (threeFore(i1, j1, i0%2==0?human:ai)[0] == -1) {
							System.out.println("自分の四三"+"x="+i1+"y="+ j1+"==========================================");
							xyList.add(new int[] { i1, j1, patten[p][7] });
							for(int m0=0;m0<patten.length;m0++){
								patten[m0][3]=200;
								patten[m0][4]=180;
								patten[m0][3]=100;
							}
	
						}
						if (this.ifThree(i1, j1, i0%2==0?human:ai, 3)[0] == -1) {
							System.out.println("自分の4を打つ"+"x="+i1+"y="+ j1);
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?human:ai, 3)[1], this.ifThree(i1, j1, i0%2==0?human:ai, 3)[2],
							    this.ifThree(i1, j1, i0%2==0?human:ai, 3)[3] + patten[p][3] });
	
						}
						if (this.ifThree(i1, j1, i0%2==0?human:ai, 2)[0] == -1) {
							System.out.println("自分の3を打つ"+"x="+i1+"y="+ j1);
							xyList.add(new int[] { this.ifThree(i1, j1, i0%2==0?human:ai, 2)[1], this.ifThree(i1, j1, i0%2==0?human:ai, 2)[2], patten[p][4] });
	
						}
					}
				}
				// 上の条件がなかったら中心に近い自分のおいた石の八方の開いているところに打つ
				ArrayList<int[]> centerDis=new ArrayList<int[]>();
				if(0<aiStone.size()){
				for (int i = 0; i < aiStone.size(); i++) {
	
					int[] xy;
					int j=0;
					
					while (j<8){
						xy=this.getAiStoneBlank(j, i);
						if(isBlank(xy)){
							centerDis.add(new int[]{xy[0],xy[1],centerDistance(xy[0],xy[1])});
						};
						j++;
					}
					
				}
				Collections.sort(centerDis, new CompM(2));
				
				xyList.add(new int[] { centerDis.get(0)[0], centerDis.get(0)[1], i0%2==0?0:1 });
				centerDis.clear();
				}
				if(0<humanStone.size()){
				for (int i = 0; i < humanStone.size(); i++) {
					
					int[] xy;
					int j=0;
					
					while (j<8){
						xy=this.getHumanStoneBlank(j, i);
						if(isBlank(xy)){
							centerDis.add(new int[]{xy[0],xy[1],centerDistance(xy[0],xy[1])});
						};
						j++;
					}
					
				}
				Collections.sort(centerDis, new CompM(2));
				xyList.add(new int[] { centerDis.get(0)[0], centerDis.get(0)[1], i0%2==0?1:0 });
				centerDis.clear();
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
				if(i0%2==0){
					if(this.victoryOrDefeat(xyList.get(0)[0],xyList.get(0)[1],ai)){
						humanPoint.add(new int[]{xyList.get(0)[0],xyList.get(0)[1],xyList.get(0)[2],10*(anticipate-i0)});
					}else if(threeFore(xyList.get(0)[0], xyList.get(0)[1], ai)[0]==-1){
						humanPoint.add(new int[]{xyList.get(0)[0],xyList.get(0)[1],xyList.get(0)[2],5*(anticipate-i0)});
					}else{
						humanPoint.add(new int[]{xyList.get(0)[0],xyList.get(0)[1],xyList.get(0)[2],0});
					}
				}
				else{
					
					if(this.victoryOrDefeat(xyList.get(0)[0],xyList.get(0)[1],ai)){
						aiPoint.add(new int[]{xyList.get(0)[0],xyList.get(0)[1],xyList.get(0)[2],10*(anticipate-i0)});
					}else if(threeFore(xyList.get(0)[0], xyList.get(0)[1], ai)[0]==-1){
						aiPoint.add(new int[]{xyList.get(0)[0],xyList.get(0)[1],xyList.get(0)[2],5*(anticipate-i0)});
					}else{
						aiPoint.add(new int[]{xyList.get(0)[0],xyList.get(0)[1],xyList.get(0)[2],0});
					}
				}
					
			}
			int humanP=0,aiP=0;
			for(int j0=0;j0<humanPoint.size();j0++){
				humanP=humanP+humanPoint.get(j0)[2];
			}
			for(int j1=0;j1<aiPoint.size();j1++){
				aiP=aiP+aiPoint.get(j1)[2]*(aiPoint.size()-j1);
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
		
		//まずは相手のポイントが低い順にソート
		Arrays.sort(pointData,new Comparator<int[]>(){
			@Override
			public int compare(int[] arg0, int[] arg1) {
				return arg0[1]-arg1[1]>0?1:arg0[1]-arg1[1]==0?0:-1;
			}});
		
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
