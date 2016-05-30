package website.iidesign.gomoku;

public class Shinpan {
	Bord bord;

	public Shinpan(Bord bord) {
		this.bord=bord;
	}
	
	public void hantei(){
		int count=0;
		for(int x=0;x<bord.X;x++){
			for(int y=0;y<bord.Y;y++){
				if(bord.isStorn(x, y))
					count++;
			}
		}
		System.out.println(count);
	}

}
