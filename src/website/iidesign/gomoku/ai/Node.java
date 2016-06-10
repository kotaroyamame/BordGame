package website.iidesign.gomoku.ai;

public class Node {
	private double w;
	private int theta=0;

	public Node(double w) {
		this.w=w;
	}
	
	public void setWeight(double w) {
		this.w=w;
	}
	
	public double getWeight() {
		return w;
	}
	
	public int hog(double p){
		int re;
		if(p*w>=theta){
			re=1;
		}else{
			re=0;
		}
		return re; 
	}

}
