package website.iidesign.gomoku;

import java.util.ArrayList;
import java.util.List;

import website.iidesign.csvFileMaker.CsvFileMaker;

public class AI {
	
	MindBord mindBord;
	protected ArrayList<int[]> brankList = new ArrayList<int[]>();
	private int bord[][];
	
	CsvFileMaker csvMake=new CsvFileMaker("log","aiLog");
	public AI() {
		this.mindBord=new MindBord();
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
	
	/*
	 * int[] serchPattern(int[][] bord,int[] xy,int tesuu)
		現在のボードの情報と今が何手目かを受け取り、個々の勝ちパターンのデータと比較して一致率が最も高いデータの一致率と
		、すべての打ちての座標を返す
		なお、ボード全体の一致率はパターンが膨大になるため、打った石の周囲10✕10の範囲の一致率を調べる。（未実装）
		また、点対称、線対称でもパターンは一致するため、盤を反転、回転させても調べる（未実装）
	*/
	public int[][] serchPattern(int[][] bord,int tesuu){
		
		int count=0;
		int i=0;
		int[][] returnInt;
		List<int[]> handlePC=new ArrayList<int[]>();
		List<int[]> secondPC=new ArrayList<int[]>();
		while(count<5&&i<Bord.getLogs().size()){
			int sameCount=0;
			for(int j=0;j<Bord.X;j++)
				for(int k=0;k<Bord.Y;k++){
					if(bord[j][k]==Bord.getLogs().get(i).get(tesuu)[j][k]){
						sameCount++;
					}
				}
		//一致率
			if(sameCount/(Bord.X*Bord.Y)==100){
				handlePC.add(new int[]{Bord.getLogs().get(0).get(tesuu)[Bord.Y][0],Bord.getLogs().get(0).get(tesuu)[Bord.Y][1]});
				count++;
			}else if(sameCount/(Bord.X*Bord.Y)>95){
				secondPC.add(new int[]{Bord.getLogs().get(0).get(tesuu)[Bord.Y][0],Bord.getLogs().get(0).get(tesuu)[Bord.Y][1]});
			}
			
			i++;
		}
		
		if(!handlePC.isEmpty()){
			returnInt=new int[handlePC.size()][2];
			for(int l=0;l<handlePC.size();l++){
				returnInt[l]=new int[]{handlePC.get(l)[0],handlePC.get(l)[1]};
			}
			
		}else if(!secondPC.isEmpty()){
			returnInt=new int[secondPC.size()][2];
			for(int l=0;l<secondPC.size();l++){
				returnInt[l]=new int[]{secondPC.get(l)[0],secondPC.get(l)[1]};
			}
		}else{
			returnInt=null;
		}
		
		return returnInt;
			
		
//		一致率100%の場合は複数（count以下）
//		{一致率,次の手のx座標,次の手のy座標}
//		{一致率,次の手のx座標,次の手のy座標}
//		{一致率,次の手のx座標,次の手のy座標}
//		
		
	}
	
	public int[] setStorn(int[][] bord,int tesuu) {
		int[] xy = serchPattern(bord,tesuu)[0];
		if (xy != null) {
//			System.out.println(xy[0]+" y "+xy[1]);
			return new int[] { xy[0], xy[1] };
		}
		this.brankCells();
		xy = brankList.get((int) Math.floor(Math.random() * brankList.size()));
		return xy;
	}

}
