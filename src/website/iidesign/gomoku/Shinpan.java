package website.iidesign.gomoku;
import website.iidesign.gomoku.Bord;
public class Shinpan {

	public Shinpan() {
	}

	private boolean victoryOrDefeat(int x, int y, boolean br) {
		int storn = br ? 0 : 1;
		out: for (int i = 1; i <= 8; i++) {
			System.out.println(i);
			for (int j = 1; j < 5; j++) {
				if (this.sarch(x, y, j, i) == storn && j == 4) {
					return true;
				} else if (this.sarch(x, y, j, i) == storn) {

				} else {
					continue out;
				}
			}
		}

		return false;
	}

	private int sarch(int x, int y, int val, int pt) {
		switch (pt) {
		case 1:
			return Bord.getStorn(x + val, y);
		case 2:
			return Bord.getStorn(x - val, y);
		case 3:
			return Bord.getStorn(x + val, y + val);
		case 4:
			return Bord.getStorn(x - val, y + val);
		case 5:
			return Bord.getStorn(x + val, y - val);
		case 6:
			return Bord.getStorn(x - val, y - val);
		case 7:
			return Bord.getStorn(x, y + val);
		case 8:
			return Bord.getStorn(x, y - val);
		default:
			return -1;
		}
	}

	public void hantei() {
		int count = 0;

	}
	
	public static boolean ifFoul(int x, int y, boolean br){
		//�����ɋւ��蔻��
		return true;
	}
	// �ł������8�����ɑł����5����ł����true��Ԃ�
	public boolean hantei(int x, int y, boolean br) {
		//���s����
		int storn = br ? 0 : 1;
		if (victoryOrDefeat(x, y, br))
			return true;
		return false;
	}

}
