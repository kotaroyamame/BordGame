package website.iidesign.gomoku;


public class Comp implements java.util.Comparator<int[]> {
	
	private int key;
	
	public Comp(int key){
		this.key=key;
	}

	public int compare(int[] o1, int[] o2) {
		return o1[key] - o2[key] > 0 ? -1 : o1[key] - o2[key] == 0 ? 0 : 1;
	}

}
