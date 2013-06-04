import javax.swing.JButton;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * This class manages the communication between BFrame and Boggle, as well as
 * respond to the messages from the controller.
 * 
 * @author Michael
 * 
 */
public class Model {
	public static final String SPACE = " ", UNDERSCORE = "_", WHITE_SPACE = "\\s+";

	private Boggle boggle;
	private BFrame frame;

	/**
	 * Constructor for the class BFrame.
	 * 
	 * @param Bframe
	 * @throws FileNotFoundException
	 */
	public Model(BFrame Bframe) throws FileNotFoundException {
		frame = Bframe;
		boggle = new Boggle();
	}

	/**
	 * Respond to a command initiated by a client's pressing of a button.
	 */
	public void respond(String command) {
		boolean found = false;
		for (int i = 0; i < BFrame.MENU_NAMES.length && !found; i++) {
			for (int j = 0; j < BFrame.MENU_NAMES[i].length && !found; j++) {
				found = Character.isDigit(command.charAt(0)) || command.equals(BFrame.MENU_NAMES[i][j]);
				if (found) {
					try {
						parseCommand(command);
					} catch (NoSuchMethodException exc) {
					} catch (IllegalAccessException exc) {
					} catch (IllegalArgumentException exc) {
					} catch (InvocationTargetException exc) {
					}
				}
			}
		}
	}

	/**
	 * Parse the command in the string parameter.
	 * 
	 * @param command
	 *            , a string containing an action command from the menu or a
	 *            button
	 */
	private void parseCommand(String command) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (Character.isDigit(command.charAt(0)))
			changeBGC(command);
		else {
			char firstChar = command.charAt(0);
			command = command.replace(firstChar, Character.toLowerCase(firstChar));
			command = command.replaceAll(SPACE, UNDERSCORE);
			Method m = Model.class.getDeclaredMethod(command, new Class[] {});
			m.invoke(this, new Object[] {});
		}
	}

	/**
	 * Change the background for the action command of a button.
	 * 
	 * @param command
	 *            , a string action command of the form "row space column"
	 */
	private void changeBGC(String command) {
		JButton[][] cells = frame.getCells();
		String[] tokens = command.split(WHITE_SPACE);
		int x = new Integer(tokens[0]).intValue(), y = new Integer(tokens[1]).intValue();
		if (cells[x][y].getIcon() == null || (cells[x][y].getIcon() != null)) {
			frame.toggleCell(x, y);
		}
	}

	public void parse() throws FileNotFoundException {
		boggle.parse();
	}

	public void guessWord() throws InterruptedException {
		// System.out.println();
		// System.out.println();
		// System.out.println();
		// System.out.println();
		// System.out.println(frame.getLabel());
		// System.out.println(frame.isGameOver());
		boggle.pPlay(frame.getLabel());
		frame.clear();
		updateScores();
	}

	public void computer() {
		boggle.cPlay();
		updateScores();
		// System.out.println(frame.isNext());
		// System.out.println(frame.isGameOver());
		// frame.showHistory();
		// frame.setHistory(boggle.getPWG(), boggle.getCWG());
		// frame.showHistory();
	}

	public void updateScores() {
		frame.setScores(boggle.getPScore(), boggle.getCScore());
	}

	/*
	 * public void sendTime() { boggle.setTime(frame.getTime()); }
	 */

	/**
	 * This will send the letter from Boggle to BFrame.
	 */
	public void sendLetter() {
		boggle.setLetter();
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 4; c++) {
				frame.setLetter(r, c, boggle.getLetter(r, c));
			}
		}
	}

	public char getLetter(int r, int c) {
		return (boggle.getLetter(r, c));
	}

	public int getCount() {
		return boggle.getCount();
	}

	/**
	 * This will close out of the program.
	 */
	private void quit() {
		System.exit(0);
	}

	public boolean addCells(int r, int c) {
		if (boggle.getCount() == 0) {
			boggle.storeCell(r, c);
			return true;
		} else if ((c == (boggle.getCellC() - 1) && r == (boggle.getCellR() - 1))
				|| (c == (boggle.getCellC() - 1) && r == (boggle.getCellR()))
				|| (c == (boggle.getCellC() - 1) && r == (boggle.getCellR() + 1))
				|| (c == (boggle.getCellC() + 1) && r == (boggle.getCellR() - 1))
				|| (c == (boggle.getCellC() + 1) && r == (boggle.getCellR()))
				|| (c == (boggle.getCellC() + 1) && r == (boggle.getCellR() + 1))
				|| (c == (boggle.getCellC()) && r == (boggle.getCellR() - 1))
				|| (c == (boggle.getCellC()) && r == (boggle.getCellR() + 1))) {
			return true;
		}
		return false;
	}

	public boolean removeCells(int r, int c) {
		if (boggle.getCellC() == c && boggle.getCellR() == r) {
			return true;
		}
		return false;
	}

	/*
	 * private void history() { frame.setHistory(boggle.getPWG(),
	 * boggle.getCWG()); frame.showHistory(); }
	 */

	private void about() {
		frame.showAbout();
	}

	private void rules() {
		frame.showRules();
	}
}
