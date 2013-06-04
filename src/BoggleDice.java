import java.util.Random;

public class BoggleDice {
	private char[][] abc = { 
			{ 'a', 'a', 'e', 'e', 'g', 'n' }, 
			{ 'e', 'l', 'r', 't', 't', 'y' },
			{ 'a', 'o', 'o', 't', 't', 'w' }, 
			{ 'a', 'b', 'b', 'j', 'o', 'o' }, 
			{ 'e', 'h', 'r', 't', 'v', 'w' },
			{ 'c', 'i', 'm', 'o', 't', 'u' }, 
			{ 'd', 'i', 's', 't', 't', 'y' }, 
			{ 'e', 'i', 'o', 's', 's', 't' },
			{ 'd', 'e', 'l', 'r', 'v', 'y' }, 
			{ 'a', 'c', 'h', 'o', 'p', 's' }, 
			{ 'h', 'i', 'm', 'n', 'q', 'u' },
			{ 'e', 'e', 'i', 'n', 's', 'u' }, 
			{ 'e', 'e', 'g', 'h', 'n', 'w' }, 
			{ 'a', 'f', 'f', 'k', 'p', 's' },
			{ 'h', 'l', 'n', 'n', 'r', 'z' }, 
			{ 'd', 'e', 'i', 'l', 'r', 'x' } 
			};
	private char[] bd = new char[16];

	public BoggleDice() {
		setUp();
		shuffle(bd);
	}

	public void setUp() {
		Random r = new Random();
		for (int i = 0; i < 16; i++) {
			bd[i] = abc[i][r.nextInt(6)];
		}
	}

	public void shuffle(char[] bd2) {
		Random ran = new Random();
		for (int i = 0; i < bd2.length * 2; ++i) {
			int fromIndex = ran.nextInt(bd2.length);
			int toIndex = ran.nextInt(bd2.length);
			char tmp = bd2[fromIndex];
			bd2[fromIndex] = bd2[toIndex];
			bd2[toIndex] = tmp;
		}
	}

	public char[] getArray() {
		return bd;
	}
}
