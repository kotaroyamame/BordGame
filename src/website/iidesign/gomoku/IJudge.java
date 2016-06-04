package website.iidesign.gomoku;

public interface IJudge {
	
	abstract int ifFoul(int x, int y, boolean br);
	abstract boolean hantei(int x, int y, boolean br);
}
