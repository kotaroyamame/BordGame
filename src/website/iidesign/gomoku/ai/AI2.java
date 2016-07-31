package website.iidesign.gomoku.ai;

public class AI2 {
	private Node[] n;
	//入力値
	int[][] x={
			{0,0},
			{0,1},
			{1,0},
			{1,1},
	};
	//教師値
	int[] y={
			0,
			1,
			1,
			1
	};

	public AI2() {
		//ノードとWの初期化
		this.n=new Node[1];
		for(int i=0;i<n.length;i++){
			n[i]=new Node(0);
		}
		n[0].setWeight(0,0);
		
		if(n[0].hog(x[0][0],x[0][1])==y[0]){
			System.out.println("W値修正なし");
		}else{
			System.out.println("W値修正あり");
		}
		
		
	}
	
	public void sum(int... p){
		for(int i=0;i<n.length;i++){
			for(int j=0;j<p.length;j++){
				n[i].setWeight(p[j]);
			}
		}
	}

}
