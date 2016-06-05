package website.iidesign.gomoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import website.iidesign.csvFileMaker.CsvFileMaker;

public class Log extends HashMap<String,int[][]>{
	
	CsvFileMaker fileMaker;
	private static final long serialVersionUID = 1L;

	public Log() {
		fileMaker=new CsvFileMaker("log","log");
	}
	public void fetch() throws IOException{
		//ここにcsvファイルからgetする処理

//		System.out.println(log.get("2手目")[0][0]);
		StringBuffer br = new StringBuffer();
		
		List<String> sortedKeys = new ArrayList<String>(this.keySet());
		
		Collections.sort(sortedKeys);
		
		br.append("\n");
		for(String entry : sortedKeys) {
			br.append(entry+"\n");
			for(int i=0;i<this.get(entry).length;i++){
				br.append(",");
				for(int j=0;j<this.get(entry)[i].length;j++){
					br.append(this.get(entry)[i][j]+",");
				}
				br.append("\n");
			}
		}
		fileMaker.writeFile(String.valueOf(br.toString()),false);
	}


}
