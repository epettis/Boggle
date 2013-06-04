import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class will maintain all of the valid words able to be used in the game.
 * 
 * @author Michael
 * 
 */
public class WordManager {
	private ArrayList<String> words;

	/**
	 * Constructor for the class WordManager.
	 */
	public WordManager() {
		words = new ArrayList<String>();
	}

	/**
	 * This will check the entire list of words to see if the given word is a
	 * valid word.
	 * 
	 * @param input
	 * @return
	 */
	public boolean isReal(String input) {
		 if (words.indexOf(input) >= 0) {
		 return true;
		}
		return false;
	}

	public String getWord(int n) {
		return words.get(n);
	}

	public int getSize() {
		return words.size();
	}

	public int getWordSize(int n) {
		String s = getWord(n);
		return s.length();
	}

	public void addWord(String s) {
		words.add(s);
	}

	public boolean removeWord(int n) {
		if (words.remove(n) != null) {
			return true;
		}
		return false;
	}

	/**
	 * This will store all of the words from the word file into a list.
	 * 
	 * @throws FileNotFoundException
	 */
	public void parseFile() throws FileNotFoundException {
		Scanner scan = new Scanner(new File("enable1.txt"));
		while (scan.hasNext()) {
			String temp = scan.nextLine();
			words.add(temp);
		}
		scan.close();
	}
}
