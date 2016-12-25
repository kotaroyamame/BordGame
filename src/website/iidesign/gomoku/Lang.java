package website.iidesign.gomoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.arnx.jsonic.JSON;

public class Lang {
	public Words En;
	public Words Jp;
	private String jp;
	private String en;
	public Lang(){
		this.inputJson("en");
		this.inputJson("ja");
	}
	
	public Words getLangObj(String lang){
		switch(lang){
		case "en":
			return this.En;
		case "ja":
		case "jp":
			return this.Jp;
		}
		return this.En;
	}
	
	private void setLangObj(String lang){
		System.out.println(this.en);
		switch(lang){
		case "en":
			this.En = JSON.decode(this.en,Words.class);
			break;
		case "ja":
		case "jp":
			this.Jp = JSON.decode(this.jp,Words.class);
			break;
		}
	}

	private void inputJson(String lang){//en or ja
		File file = new File("assets/"+lang+".json");
		try {
			FileReader filereader = new FileReader(file);
			int ch;
			StringBuffer sb=new StringBuffer();
			while((ch = filereader.read()) != -1){
				sb.append((char)ch);
			}
			switch(lang){
			case "en":
				this.en=sb.toString();
				this.setLangObj("en");
				break;
			case "ja":
			case "jp":
				this.jp = sb.toString();
				this.setLangObj("jp");
				break;
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
