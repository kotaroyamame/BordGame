package website.iidesign.gomoku;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import website.iidesign.csvFileMaker.CsvFileMaker;

public class Bord {

	public final static int X = 15;
	public final static int Y = 15;
	public final int SIZE = 20;
	public final boolean IRON = false;
	public final boolean INITIATIVE = true;
	protected boolean count = INITIATIVE;
	protected int mindBord[][];
	protected static int bord[][];
	private GraphicsContext gc;
	private static Map<String, int[][]> log;
	private static Logs logs;
	private int tekazu=1;
	private boolean isSound=true;
	private Clip clip = null;
	
	static CsvFileMaker csvMake;
	public Bord() {		
	}
	public Bord(GraphicsContext gc) {
		log=new Log();
		logs= new Logs();
		csvMake=new CsvFileMaker("log","log");
		this.gc = gc;
		Bord.bord = new int[X][Y];
		init();
		strokeLine();
	}

	private void init() {
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++)
				Bord.bord[i][j] = -1;
		}
		gc.clearRect(0, 0, 1000, 1000);
		

	}

	private void strokeLine() {
		gc.setStroke(Color.BLACK);

		for (int i = 0; i < Y; i++) {
			gc.strokeLine(0 + SIZE / 2, i * SIZE + SIZE / 2, X * SIZE - SIZE / 2, i * SIZE + SIZE / 2);
			gc.strokeLine(i * SIZE + SIZE / 2, 0 + SIZE / 2, i * SIZE + SIZE / 2, Y * SIZE - SIZE / 2);
		}

	}

	private void drowBord(int x, int y, boolean br) {
		if (br)
			gc.setFill(Color.BLACK);
		else
			gc.setFill(Color.WHITE);
		gc.fillOval(x * SIZE, y * SIZE, SIZE, SIZE);
	}
	
	private void setLog(String value) throws IOException{
		((Logs) logs).fetch();
	}
	public void endLog(boolean br) throws IOException{
		if(tekazu<8){
		String storn = br ? "__LOS0" : "__WIN1";//0:先手勝利,1:後手勝利

		String maker="DATA__";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM/dd/ HH:mm ss");
    
    ((Log) log).setLastOrFirst(br?0:1);

		logs.put(maker+sdf.format(Calendar.getInstance().getTime())+storn, (Log) log);
		((Logs) logs).fetch();
		}else{
			
		}
	}
	
	public int sarch(int x, int y, int val, int pt) {
		switch (pt) {
		case 1:
			return this.getStorn(x + val, y);
		case 2:
			return this.getStorn(x - val, y);
		case 3:
			return this.getStorn(x + val, y + val);
		case 4:
			return this.getStorn(x - val, y - val);
		case 5:
			return this.getStorn(x + val, y - val);
		case 6:
			return this.getStorn(x - val, y + val);
		case 7:
			return this.getStorn(x, y + val);
		case 8:
			return this.getStorn(x, y - val);
		default:
			return -1;
		}
	}

	public synchronized boolean setStorn(int x, int y, boolean br) {
		
		int stone;
		if (x < X && y < Y && Bord.bord[x][y] == -1 && count == br) { //&& Shinpan.ifFoul(x, y, br)!=-1) {

			this.drowBord(x, y, br);
			stone=br ? 0 : 1;
			Bord.bord[x][y] = stone;
			int [][] _bord=new int[Bord.X+1][Bord.Y];
			for(int i=0;i<Bord.X;i++){
				for(int j=0;j<Bord.Y;j++){
					_bord[i][j]=Bord.bord[i][j];
				}
			}
			_bord[Bord.X][0]=x;
			_bord[Bord.X][1]=y;
			log.put(String.format("%1$04d teme", tekazu),_bord);
			++tekazu;
			count = !br;
			if(this.isSound)
				this.sound();
			return true;
		} else {
			return false;
		}
	}

	public boolean isStorn(int x, int y) {
		if (x < X && y < Y && Bord.bord[x][y] != -1) {
			return true;
		} else {
			return false;
		}
	}

	public int getStorn(int x, int y) {
		if (x < 0 || X <= x || y < 0 || X <= y)
			return -2;
		return Bord.bord[x][y];
	}
	
	public static Logs getLogs(){
		return logs;
	}
	
	public int getTekazu(){
		return tekazu;
	}
	
	public void soundMute(){
		this.isSound=false;
	}
	public void soundOn(){
		this.isSound=true;
	}
	public void sound(){
		AudioInputStream audioInputStream;
        try{   
            audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("01.wav")));
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
        }
        catch (UnsupportedAudioFileException e)
        {   e.printStackTrace();  }
        catch (IOException e)
        {   e.printStackTrace();  }
        catch (LineUnavailableException e)
        {   e.printStackTrace();  }
        

	}
	

}
