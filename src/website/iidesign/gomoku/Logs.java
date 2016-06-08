package website.iidesign.gomoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import website.iidesign.csvFileMaker.CsvFileMaker;

public class Logs extends HashMap<String,HashMap<String,int[][]>>{
	
	CsvFileMaker fileMaker;
	private static final long serialVersionUID = 1L;

	public Logs() {
		fileMaker=new CsvFileMaker("log","log");
	}
	private void set(){
		ArrayList<String> stringList=new ArrayList<String>();
		stringList.addAll(fileMaker.getTextList());
		String date="";
//		this.clear();
		int i=0;
		outout:while(i<stringList.size()){
			HashMap<String, int[][]> hashmap=new HashMap<String,int[][]>();
			if(stringList.get(i).matches("DATA__.+")){
				date=stringList.get(i);
				i++;
			
				
				out:while(stringList.get(i).matches(".+teme")){
					String key=stringList.get(i).split(",")[1];
					System.out.println(key);
					i++;
					int[][] bord=new int[Bord.X][Bord.Y];
					for(int j=0;j<Bord.Y;j++){
						for(int k=0;k<Bord.X;k++){
							bord[j][k]= Integer.parseInt(stringList.get(i).split(",")[k+2]);
						}
					i++;
					}
					i--;
					hashmap.put(key, bord);
//					System.out.println("stringListSIze"+stringList.size());
//					System.out.println("put"+key);
					if(i>=stringList.size() ){
						System.out.println("put"+date);
						this.put(date, hashmap);
						break outout;
					}
					while(!stringList.get(i).matches(".+teme")&&!stringList.get(i).matches("DATA__.+")){
						System.out.println("noMatch" +i);
						i++;
						if(i>=stringList.size()){
							System.out.println("put"+date);
							this.put(date, hashmap);
							break outout;
						}
//						else if(stringList.get(i).matches("DATA__.+")){
//							break out;
//						}
					}
				}
				System.out.println("putend"+date);
				this.put(date, hashmap);
				i--;
			}
			i++;
		}
	}
	public void fetch() throws IOException{
		//ここにcsvファイルからgetする処理
		set();
//		System.out.println(log.get("2手目")[0][0]);
		StringBuffer br = new StringBuffer();
		
		List<String> sortedKeys = new ArrayList<String>(this.keySet());
		
		Collections.sort(sortedKeys);
		
//		br.append("\n");
		for(String entry : sortedKeys) {
			br.append(entry+"\n");
			List<String> sortedKeys2 = new ArrayList<String>(this.get(entry).keySet());
			Collections.sort(sortedKeys2);
			for(String entry2 : sortedKeys2) {
				br.append(","+entry2+"\n");
				for(int i=0;i<this.get(entry).get(entry2).length;i++){
					br.append(",,");
					for(int j=0;j<this.get(entry).get(entry2)[i].length;j++){
						br.append(this.get(entry).get(entry2)[i][j]+",");
					}
					br.append("\n");
				}
			}
		}
		fileMaker.writeFile(String.valueOf(br.toString()),false);
	}
	/*
	 * int[] serchPattern(int[][] bord,int tesuu)
	//現在のボードの情報と何手目かを受け取り、個々の勝ちパターンのデータと比較して一致率が最も高いデータの一致率と
	//、すべての打ちての座標を返す
	*/
	public int[] serchPattern(int[][] bord,int tesuu){
		
		int count=0;
		while(count<5&&count<this.size()){
			int sameCount=0;
			for(int i=0;i<Bord.X;i++)
				for(int j=0;j<Bord.Y;j++){
					if(bord[i][j]==this.get(0).get(tesuu)[i][j]){
						sameCount++;
					}
					
					
				}
			System.out.println(sameCount/(Bord.X*Bord.Y));//一致率
			count++;
		}
		
		return new int[]{0,0};
	}


}
