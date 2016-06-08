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
	private int lastOrFirst=0;

	public Log() {
		fileMaker=new CsvFileMaker("log","log");
	}

	public void setLastOrFirst(int val){
		this.lastOrFirst=val;
	}
	
	public int getLastOrFirst(){
		return this.lastOrFirst;
	}


}
