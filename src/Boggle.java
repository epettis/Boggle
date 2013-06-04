import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is where all of the computing will be done in the game.
 * 
 * @author Michael
 * 
 */
public class Boggle {
	private char[][] board;
	private WordManager wm;
	private WordManager cc;
	private ArrayList<String> cWG;
	private ArrayList<String> pWG;
	private int pScore;
	private int cScore;
	private Cell[] coords;
	private int count = 0;
	private BoggleDice bd;
	private int time = 0;

	/**
	 * Constructor for the class Boggle.
	 * 
	 * @throws FileNotFoundException
	 */
	public Boggle() {
		board = new char[4][4];
		wm = new WordManager();
		cc = new WordManager();
		cWG = new ArrayList<String>();
		pWG = new ArrayList<String>();
		pScore = 0;
		cScore = 0;
		bd = new BoggleDice();
		coords = new Cell[16];
		resetCells();
	}

	private void resetCells() {
		for (int i = 0; i < 16; i++) {
			coords[i] = new Cell(-1, -1);
		}
		count = 0;
	}

	/**
	 * This is to keep track on how the player will play the game.
	 */
	public void pPlay(String guessWord) {
		// System.out.println();
		// System.out.println();
		// System.out.println();
		// System.out.println(guessWord + " is a valid word. " +
		// isWord(guessWord));
		if (isWord(guessWord)) {
			// System.out.println((pWG.indexOf(guessWord) >= 0));
			// System.out.println("Size is " + pWG.size());
			if (!(pWG.indexOf(guessWord) >= 0)) {
				// System.out.println("WORK!!!!!!!!!!!!!!!!!!!!!!!!");
				pScore += scoreWord(guessWord);
				// System.out.println(pScore);
			}
			storeWordP(guessWord);
			// for (int i = 0; i < pWG.size(); i++) {
			// System.out.println(pWG.get(i));
			// }
		}
	}

	/**
	 * This will store all the guessed words guessed by the player.
	 * 
	 * @param input
	 */
	private void storeWordP(String input) {
		pWG.add(input);
	}

	public boolean isReal(String s) {
		return wm.isReal(s);
	}

	/**
	 * This is to keep track on how the computer will play the game.
	 */
	public void cPlay() {
		String guessWord = "";
		resetCells();
		Random yyz = new Random();
		int r;
		int c;
		char l;
		int finish = 0;

		while (finish < 30) {
			// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			if (cc.isReal(guessWord)) {
				// System.out.println("Option 1");
				if (!(cWG.indexOf(guessWord) >= 0)) {
					cScore += scoreWord(guessWord);
					finish++;
					// System.out.println("SCORE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					// System.out.println("SCORE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					// System.out.println("SCORE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					// System.out.println("SCORE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					// System.out.println("SCORE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				}
				storeWordC(guessWord);
				resetCells();
				guessWord = "";
				// System.out.println("\t\t\t\t" + guessWord);
				// System.out.println("\t\t\t\t" + count);
			}

			else if (guessWord.length() == 0) {
				// System.out.println("Option 2");
				r = yyz.nextInt(4);
				c = yyz.nextInt(4);
				l = getLetter(r, c);
				// System.out.println("!!!!!!!!!Letter: " + l + "\t\tR & C: (" +
				// r + ", " + c + ")");
				guessWord += l;
				storeCell(r, c);
				cc = new WordManager();
				for (int i = 0; i < wm.getSize(); i++) {
					if (guessWord.charAt(guessWord.length() - 1) == wm.getWord(i).charAt(guessWord.length() - 1)) {
						cc.addWord(wm.getWord(i));
						// System.out.println(wm.getWord(i));
					}
				}

				WordManager ccc = new WordManager();
				for (int i = 0; i < cc.getSize(); i++) {
					if (cc.getWord(i).charAt(guessWord.length() - 1) == guessWord.charAt(guessWord.length() - 1)) {
						ccc.addWord(cc.getWord(i));
					}
				}
				cc = ccc;
				// System.out.println("\t\t\t\t" + guessWord);
				// System.out.println("\t\t\t\t" + count);
			}

			else if (cc.getSize() == 0 && guessWord.length() > 1) {
				// System.out.println("Option 3");
				resetCells();
				guessWord = "";
				// System.out.println("\t\t\t\t" + guessWord);
				// System.out.println("\t\t\t\t" + count);
			}

			else if (cc.getSize() > 0) {
				// System.out.println("Option 4");
				r = yyz.nextInt(4);
				c = yyz.nextInt(4);
				// System.out.println("\t\t\tR & C: (" + r + ", " + c + ")");
				if (isAdjacent(r, c)) {
					if (count < 16) {
						WordManager ccc = new WordManager();
						l = getLetter(r, c);
						guessWord += l;
						storeCell(r, c);
						for (int i = 0; i < cc.getSize(); i++) {
							if (cc.getWord(i).charAt(guessWord.length() - 1) == guessWord.charAt(guessWord.length() - 1)) {
								ccc.addWord(cc.getWord(i));
							}
						}
						cc = ccc;
						// found = true;
					} else {
						resetCells();
						guessWord = "";
					}
				}
				// System.out.println("\t\t\t\t" + guessWord);
				// System.out.println("\t\t\t\t" + count);
			}

			/*
			 * else { System.out.println("Option 5"); while (!found) { r =
			 * yyz.nextInt(4); c = yyz.nextInt(4); if (isAdjacent(r, c)) {
			 * WordManager ccc = new WordManager(); l = getLetter(r, c);
			 * guessWord += l; storeCell(r, c); for (int i = 0; i <
			 * cc.getSize(); i++) { if (cc.getWord(i).charAt(guessWord.length()
			 * - 1) == guessWord.charAt(guessWord.length() - 1)) {
			 * ccc.addWord(cc.getWord(i)); } } cc = ccc; found = true; } else {
			 * r = yyz.nextInt(4); c = yyz.nextInt(4); } } }
			 */

		}

		/*
		 * String guessWord = ""; boolean newWord = false; Random yyz = new
		 * Random(); int n = 0; int r = yyz.nextInt(4); int c = yyz.nextInt(4);
		 * char l = getLetter(r, c); storeCell(r, c);
		 * System.out.println("!!!!!!!!!Letter: " + l + "\tR & C: (" + r + ", "
		 * + c + ")"); guessWord += l;
		 * 
		 * while (time < 60) { // exclude(guessWord); // while (!newWord) { r =
		 * yyz.nextInt(4); c = yyz.nextInt(4); System.out.println("Letter: " +
		 * getLetter(r, c) + "\t\tR & C: (" + r + ", " + c + ")");
		 * 
		 * if (cc.getSize() == 0 && count > 0) { resetCells(); for (int i = 0; i
		 * < wm.getSize(); i++) { String s = wm.getWord(i); s.toLowerCase(); if
		 * (guessWord.charAt(n) == s.charAt(n)) { cc.addWord(wm.getWord(i)); } }
		 * } else if (isAdjacent(r, c)) { WordManager ccc = new WordManager();
		 * for (int i = 0; i < cc.getSize(); i++) { String s = cc.getWord(i);
		 * s.toLowerCase(); if (guessWord.charAt(n) == s.charAt(n)) {
		 * ccc.addWord(s); } } cc = ccc; n++; System.out.println();
		 * System.out.println(); System.out.println(); for (int i = 0; i <
		 * cc.getSize(); i++) { System.out.println(cc.getWord(i)); }
		 * System.out.println(); System.out.println(); System.out.println(); }
		 * 
		 * // if (cc.getSize() == 0) { // newWord = true; // }
		 * 
		 * if (isAdjacent(r, c)) { storeCell(r, c); l = getLetter(r, c);
		 * guessWord += l; System.out.println("The word is " + guessWord);
		 * System.out.println(guessWord + " is a real word. " +
		 * isWord(guessWord)); if (isWord(guessWord)) { if
		 * (!(cWG.indexOf(guessWord) >= 0)) { cScore += scoreWord(guessWord);
		 * time++; } storeWordC(guessWord); } // } } }
		 */
	}

	/*
	 * public WordManager exclude(String guessWord) { int n = 0; if
	 * (cc.getSize() == 0) { for (int i = 0; i < wm.getSize(); i++) { String s =
	 * wm.getWord(i); s.toLowerCase(); if (guessWord.charAt(n) == s.charAt(n)) {
	 * cc.addWord(s); } } }
	 * 
	 * else { WordManager ccc = new WordManager(); for (int i = 0; i <
	 * cc.getSize(); i++) { String s = cc.getWord(i); s.toLowerCase(); if
	 * (guessWord.charAt(n) == s.charAt(n)) { ccc.addWord(s); } } cc = ccc; n++;
	 * } }
	 */

	/**
	 * This will store all the guessed words guessed by the computer.
	 * 
	 * @param input
	 */
	private void storeWordC(String input) {
		cWG.add(input);
	}

	public boolean isAdjacent(int r, int c) {
		if (((c == (getCellC() - 1) && r == (getCellR() - 1)) || (c == (getCellC() - 1) && r == (getCellR()))
				|| (c == (getCellC() - 1) && r == (getCellR() + 1)) || (c == (getCellC() + 1) && r == (getCellR() - 1))
				|| (c == (getCellC() + 1) && r == (getCellR())) || (c == (getCellC() + 1) && r == (getCellR() + 1))
				|| (c == (getCellC()) && r == (getCellR() - 1)) || (c == (getCellC()) && r == (getCellR() + 1)))
				&& !existCell(r, c)) {
			return true;
		}
		return false;
	}

	public void storeCell(int r, int c) {
		// System.out.println(count);
		coords[count].setCell(r, c);
		count++;
	}

	public void removeCell() {
		count--;
	}

	public boolean existCell(int r, int c) {
		Cell eCell = new Cell(r, c);
		for (int i = 0; i < count; i++) {
			if (coords[i] == eCell) {
				System.out.println("Has this cell been used before? " + (coords[i] == eCell));
				return true;
			}
		}
		return false;
	}

	public int getCellC() {
		return coords[count - 1].getCol();
	}

	public int getCellR() {
		return coords[count - 1].getRow();
	}

	/**
	 * This will check to see if the guessed word is a valid word.
	 * 
	 * @param input
	 * @return
	 */
	private boolean isWord(String input) {
		return wm.isReal(input);
	}

	/**
	 * This will parse all the words from the word file into a list of words.
	 * 
	 * @throws FileNotFoundException
	 */
	public void parse() throws FileNotFoundException {
		wm.parseFile();
	}

	/**
	 * This will score each word in the game and update the corresponding score.
	 * 
	 * @param input
	 * @return
	 */
	private int scoreWord(String input) {
		if (input.length() >= 12) {
			return 20;
		} else if (input.length() >= 10) {
			return 15;
		} else if (input.length() >= 8) {
			return 12;
		} else if (input.length() >= 5) {
			return 7;
		} else {
			return input.length();
		}
	}

	/**
	 * This will set each cell on the board will a randomly selected letter.
	 */
	public void setLetter() {
		char[] abc = bd.getArray();
		int z = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = abc[z];
				z++;
			}
		}
	}

	/**
	 * This will return the letter at the given coordinate on the grid.
	 * 
	 * @param r
	 *            the 'x' coordinate on the grid
	 * @param c
	 *            the 'y' coordinate on the grid
	 * @return
	 */
	public char getLetter(int r, int c) {
		char cc = board[r][c];
		return cc;
	}

	/**
	 * This will return the score for the computer.
	 * 
	 * @return
	 */
	public int getCScore() {
		return cScore;
	}

	/**
	 * This will return the score for the player.
	 * 
	 * @return
	 */
	public int getPScore() {
		return pScore;
	}

	public int getCount() {
		return count;
	}

	public ArrayList getPWG() {
		return pWG;
	}

	public ArrayList getCWG() {
		return cWG;
	}
}