import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;
//import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

/**
 * This class will be the user interface so that the use may see what the code
 * is representing.
 * 
 * @author Michael
 * 
 */
public class BFrame extends JFrame {
	public static final String[] BAR_NAMES = { "File", "Help" };
	public static final String[][] MENU_NAMES = { { "Quit" }, { "Rules", "About" } };
	private static final String TITLE = "Boggle";
	private static final String RULES = "The rules of this game are simple. \n\nYou have to find words by building off \nadjacent touch cells on the board. \nThis means that the letters can be \ntouching in horizontal, vertical, \nand/or diagonally. \n\nTry to achieve the highest score. \nYou may receive bonus for longer words. Have Fun!”";
	private static final String ABOUT = "Boggle is a word game designed by Allan Turoff and trademarked by Parker Brothers, a division of Hasbro. The game is played using a plastic grid of lettered dice, in which players attempt to find words in sequences of adjacent letters.";
	private static final String MENU_TITLE = "                                                                   Let's Boggle!";
	private JButton[][] cells;
	private JPanel ctrP;
	private JLabel label;
	private JLabel cSL;
	private JButton guess;
	private int pScore;
	private int cScore;
	private Model model;
	private boolean next = false;
	private Cell[] coords = new Cell[16];
	private int count = 0;
	private Timer timer;
	private JLabel labelTime;
	private int interval = 60;
	private static final int DELAY = 50;
	private static final int PERIOD = 1000;

	private static final Color GREY = new Color(200, 200, 200);
	private static final Dimension BUTTON_DIM = new Dimension(75, 75);

	/**
	 * Constructs the BFrame object.
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @throws InterruptedException
	 */
	public BFrame() throws FileNotFoundException {
		super(TITLE);
		model = new Model(this);
		Controller ctrl = new Controller(model);
		Container can = getContentPane();
		can.setLayout(new BorderLayout());
		Container can2 = getContentPane();
		can2.setLayout(new BorderLayout());

		model.parse();

		ctrP = makeCells(ctrl);
		can.add(ctrP, "Center");

		setJMenuBar(makeMenu(ctrl));

		guess = new JButton("Guess");
		ActionListener listener = new ButtonListener();
		guess.addActionListener(listener);
		can.add(guess, "East");

		label = new JLabel("", SwingConstants.RIGHT);
		label.setFont(new Font("Serif", Font.BOLD, 30));
		can2.add(label, "South");

		String s = "Computer Score: " + (Integer.toString(cScore));
		String c = "Player Score: " + (Integer.toString(pScore));
		String sc = "<html>" + s + "<br>" + c + "<br>" + "<br>" + "Good Luck, Daffy Duck!" + "</html>";
		cSL = new JLabel(sc);
		cSL.setFont(new Font("Serif", Font.PLAIN, 25));
		can2.add(cSL, "West");

		labelTime = new JLabel(Integer.toString(0), SwingConstants.CENTER);
		labelTime.setFont(new Font("Serif", Font.BOLD, 128));
		setDownTimer();

		can.add(labelTime, "North");

		model.sendLetter();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public int getTime() {
		return Integer.parseInt(labelTime.getText());
	}

	public void setDownTimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				// System.out.println(setInterval());
				labelTime.setText(Integer.toString(setInterval()));
				// if (setInterval() == 0) {
				// model.computer();
				// }
			}
		}, DELAY, PERIOD);
	}

	private int setInterval() {
		if (interval == -1) {
			next = true;
			interval = -1;
			model.computer();
			return 0;
		}
		return interval--;
	}

	public boolean isNext() {
		return next;
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == guess && !next) {
				try {
					model.guessWord();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Construct a JMenuBar object that responds to a Controller object.
	 * 
	 * @param ctrl
	 *            a Controller object to respond to events on the menu
	 * @return
	 */
	private JMenuBar makeMenu(Controller ctrl) {
		JMenuBar bar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;
		for (int i = 0; i < MENU_NAMES.length; i++) {
			menu = new JMenu(BAR_NAMES[i]);
			for (int j = 0; j < MENU_NAMES[i].length; j++) {
				menuItem = new JMenuItem(MENU_NAMES[i][j]);
				menuItem.addActionListener(ctrl);
				menu.add(menuItem);
			}
			bar.add(menu);
		}
		// menuItem = new JMenuItem(NMK_INFO);
		// bar.add(menuItem);
		menuItem = new JMenuItem(MENU_TITLE);
		bar.add(menuItem);
		return bar;
	}

	/**
	 * This creates the cells into buttons so that the game becomes more
	 * interactive with the client.
	 * 
	 * @param ctrl
	 *            the command that the client sends to the program
	 * @return
	 */
	private JPanel makeCells(Controller ctrl) {
		JPanel buttonP = new JPanel(new GridLayout(4, 4));
		JButton button;
		cells = new JButton[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				button = new JButton();
				button.setActionCommand("" + i + " " + j);
				button.addActionListener((ActionListener) ctrl);
				button.setPreferredSize(BUTTON_DIM);
				cells[i][j] = button;
				buttonP.add(button);
			}
		}
		return buttonP;
	}

	/**
	 * Returns the text from the JTextField so that game can properly play the
	 * game.
	 * 
	 * @return
	 */
	public String getText() {
		return label.getText();
	}

	private void setTextC(char c) {
		label.setText(label.getText() + c);
	}

	private void removeText() {
		String temp = label.getText();
		String temp2 = "";
		for (int i = 0; i < temp.length() - 1; i++) {
			temp2 = (temp2 + temp.charAt(i));
		}
		label.setText(temp2);
	}

	public String getLabel() {
		return label.getText();
	}

	/**
	 * This adds in a picture for each cell so the client knows what they are
	 * clicking on.
	 * 
	 * @param r
	 *            the 'x' coordinate on the grid
	 * @param c
	 *            the 'y' coordinate on the grid
	 * @param l
	 *            the letter that is being sent
	 */
	public void setLetter(int r, int c, char l) {
		// System.out.println("r is " + r + "\tc is " + c + "\tl is " + l);
		// System.out.println();
		if (l < 'a' || l > 'z') {
			System.out.println("Invalid character: %c", l);
			return;
		}
		cells[r][c].setIcon(new ImageIcon(l + ".gif"));
	}

	/**
	 * This toggles the background of the cell to let the client know that the
	 * letter has been used.
	 * 
	 * @param r
	 *            the 'x' coordinate on the grid
	 * @param c
	 *            the 'y' coordinate on the grid
	 */
	public void toggleCell(int r, int c) {
		if (cells[r][c].getBackground().equals(getBackground())) {
			Cell ccc = new Cell(r, c);
			if (count == 0) {
				cells[r][c].setBackground(GREY); //
				// System.out.println("r is " + r + " c is " + c);
				coords[count] = ccc;
				count++;
				setTextC(model.getLetter(r, c));
			} else if ((c == (coords[count - 1].getCol() - 1) && r == (coords[count - 1].getRow() - 1))
					|| (c == (coords[count - 1].getCol() - 1) && r == (coords[count - 1].getRow()))
					|| (c == (coords[count - 1].getCol() - 1) && r == (coords[count - 1].getRow() + 1))
					|| (c == (coords[count - 1].getCol() + 1) && r == (coords[count - 1].getRow() - 1))
					|| (c == (coords[count - 1].getCol() + 1) && r == (coords[count - 1].getRow()))
					|| (c == (coords[count - 1].getCol() + 1) && r == (coords[count - 1].getRow() + 1))
					|| (c == (coords[count - 1].getCol()) && r == (coords[count - 1].getRow() - 1))
					|| (c == (coords[count - 1].getCol()) && r == (coords[count - 1].getRow() + 1))) {
				cells[r][c].setBackground(GREY); //
				// System.out.println("r is " + r + " c is " + c);
				coords[count] = ccc;
				count++;
				setTextC(model.getLetter(r, c));
			}
		} else {
			if (coords[count - 1].getCol() == c && coords[count - 1].getRow() == r) {
				cells[r][c].setBackground(getBackground());
				coords[count - 1].setCell(-1, -1);
				count--;
				removeText();
			}
		}
	}

	/*
	 * public void newGame() { model.sendLetter(); interval = 60; }
	 */

	/**
	 * This returns the cells.
	 * 
	 * @return
	 */
	public JButton[][] getCells() {
		return cells;
	}

	/**
	 * This clears the board of all the modified background color.
	 */
	public void clear() {
		for (int r = 0; r < 4; r++)
			for (int c = 0; c < 4; c++) {
				cells[r][c].setBackground(getBackground());
				label.setText("");
				for (int i = 0; i < coords.length; i++) {
					coords[i] = new Cell(-1, -1);
				}
				count = 0;
			}
	}

	/*
	 * public void setHistory(ArrayList<String> p, ArrayList<String> c) {
	 * HISTORY = ""; for (int i = 0; i < c.size(); i++) { HISTORY = HISTORY +
	 * ("\t\t\tComputer: " + c.get(i) + "\n"); } if (p.size() > c.size()) { for
	 * (int i = 0; i < c.size(); i++) { HISTORY = HISTORY + ("\tPlayer: " +
	 * p.get(i) + "\t\t\tComputer: " + c.get(i) + "\n"); } for (int i =
	 * c.size(); i < p.size(); i++) { HISTORY = HISTORY + ("\tPlayer: " +
	 * p.get(i) + "\n"); } } else if (p.size() < c.size()) { for (int i = 0; i <
	 * p.size(); i++) { HISTORY = HISTORY + ("\tPlayer: " + p.get(i) +
	 * "\t\t\tComputer: " + c.get(i) + "\n"); } for (int i = p.size(); i <
	 * c.size(); i++) { HISTORY = HISTORY + ("\tComputer: " + p.get(i) + "\n");
	 * } } else { for (int i = 0; i < p.size(); i++) { HISTORY = HISTORY +
	 * ("\tPlayer: " + p.get(i) + "\t\t\tComputer: " + c.get(i) + "\n"); } } }
	 */

	/**
	 * This prints out the History of the game on a message dialog.
	 */
	/*
	 * public void showHistory() { JOptionPane.showMessageDialog(this, HISTORY);
	 * }
	 */

	/**
	 * This prints out the Rules of the game on a message dialog.
	 */
	public void showRules() {
		JOptionPane.showMessageDialog(this, RULES);
	}

	/**
	 * This prints out the Information of the game on a message dialog.
	 */
	public void showAbout() {
		JOptionPane.showMessageDialog(this, ABOUT);
	}

	/**
	 * will flash the JTextField red to let the client know that the word
	 * guessed isn't a real word.
	 * 
	 * @throws InterruptedException
	 */
	/*
	 * public void wrongGuess() throws InterruptedException { Color oC =
	 * label.getBackground(); if (label.getBackground() == CBU_RED) {
	 * label.setBackground(oC); } else { label.setBackground(CBU_RED); } }
	 */

	/*
	 * public void playAgain() { JOptionPane.showMessageDialog(this,
	 * PLAY_AGAIN); }
	 */

	/**
	 * This just updates the scores for the both the player and the computer.
	 * 
	 * @param p
	 * @param c
	 */
	public void setScores(int p, int cc) {
		cScore = cc;
		pScore = p;

		String s = "Computer Score: " + (Integer.toString(cScore));
		String c = "Player Score: " + (Integer.toString(pScore));
		String sc = "<html>" + s + "<br>" + c + "<br>" + "<br>" + "Good Luck, Daffy Duck!" + "</html>";
		cSL.setText(sc);
	}
}
