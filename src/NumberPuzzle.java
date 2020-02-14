import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class NumberPuzzle extends Frame implements ActionListener {
	Panel Panel1;
	Panel Panel2;
	Button newgame;
	Button grid[][] = new Button[3][3];
	int ar[][] = new int[3][3];
	Label l1;
	Label LabelMoves;
	Label l3;
	Label l4;
	int moves;
	private Component frame;
	NumberPuzzle(String name) {
		super("9 Box-Puzzle ");
		moves = 0;
//		JOptionPane.showMessageDialog(frame, "ABOUT\n\nThe 9-box puzzle is a sliding puzzle which consists of 3x3 grid of numbered\nsquares with one square missing. The squares are jumbled when the puzzle\nstart and the goal of this game is to unjumble the squares by only sliding a\nsquare into the empty space.\n\n", "9 Box-Puzzle", JOptionPane.PLAIN_MESSAGE);
//		String name = JOptionPane.showInputDialog(null, "RULES\nTo move:  If there is an empty adjacent square next to a tile, a tile may be slid into the empty location.\nTo win:  The tiles must be moved back into their original positions, numbered 1 through 8.\n\nEnter your name: ", "9 Box-Puzzle", JOptionPane.QUESTION_MESSAGE);
		setVisible(true);
		setSize(600, 600);
		Panel1 = new Panel();
		Panel2 = new Panel();
		Panel2.setSize(500, 500);
		Panel2.setLayout(new GridLayout(3, 3, 4, 4));
		Color randomColor = new Color(173, 216, 230);
		Panel2.setBackground(randomColor);
		l1 = new Label("    ");
		Font f1 = new Font("Diag", Font.ROMAN_BASELINE, 18);
		setFont(f1);
		LabelMoves = new Label("Moves:        ");
		l3 = new Label("Username:    ");
		l4 = new Label("    ");
		setLayout(new BorderLayout());
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				grid[i][j] = new Button("  ");
				Font f2 = new Font("Diag", Font.BOLD, 40);
				grid[i][j].setFont(f2);
				grid[i][j].setForeground(Color.DARK_GRAY);
			}
		}
		rand();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
			Panel2.add(grid[i][j]);
		}
		newgame = new Button("New Game");
		newgame.setForeground(Color.BLACK);
		newgame.setBackground(Color.MAGENTA);
		Panel1.add(newgame);
		Panel1.add(l4);
		Panel1.add(LabelMoves);
		Panel1.add(l3);
		add(Panel1, BorderLayout.NORTH);
		add(Panel2, BorderLayout.CENTER);
		add(l1, BorderLayout.SOUTH);
		String data = l3.getText();
		data = data + name;
		l3.setText(data);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				grid[i][j].addActionListener(this);
			}
		}
		newgame.addActionListener(this);
		addWindowListener(new myWindowAdapter(name,moves));
	}


	class myWindowAdapter extends WindowAdapter {
		String name;
		int moves;
		myWindowAdapter(String name,int moves){
			super();
			this.name=name;
			this.moves=moves;
		}
		public void windowClosing(WindowEvent e) {
			int closingWindow = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the game?", "9 Box-Puzzle", JOptionPane.YES_NO_OPTION);

			if (closingWindow == JOptionPane.YES_OPTION) {
//				File f = new File("Scores.txt");
//                FileWriter fw = null;
//                BufferedWriter bw = null;
//                try {
//                    fw = new FileWriter(f,true);
//                    bw = new BufferedWriter(fw);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                try {
//                    bw.write(name+" " + moves);
//                    bw.newLine();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                finally {
//                	try {
//						bw.close();
//						fw.close();
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//                }
				// e.Dispose();
				
				System.exit(0);
//				 JComponent comp = (JComponent) e.getSource();
//				  Window win = SwingUtilities.getWindowAncestor(comp);
//				  win.dispose();
			}
		}

	}


//	public static void main(String s[]) {
//		Game m = new Game("user");
//	}


	public void actionPerformed(ActionEvent ae) {
		//	String data = l3.getText();
		//	l3.setText(data);
		
		if (ae.getSource() == newgame) {
			replay();
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (ae.getSource() == grid[i][j]) {
					String str2 = new String("Moves: ");

					if (moves > 0) {
						str2 = str2 + moves;
						LabelMoves.setText(str2);

					}
					setcol();
					find(i, j);
					Color randomColor = new Color(173, 216, 230);
					grid[i][j].setBackground(randomColor);
				}
			}
		}
		checkwin();

	}


	void replay() {
		moves = 1;
		rand();
		setcol();
		ar[2][2] = 9;
		grid[2][2].setLabel(" ");
		l1.setText(" ");
		LabelMoves.setText("Moves:     ");
	}


	int checkwin() {
		int i, j, k = 1;
		int chk = 0;
		for (i = 0; i <= 2; i++) {
			for (j = 0; j <= 2; j++) {
				if (k == ar[i][j]) {
					chk++;
				}
				k++;
			}
		}
		if (chk == 9 || moves == 2) {
			String str = new String("You Win!");
			str = str + " In Moves " + moves;
			l1.setText(str);
		}
		return 0;
	}


	int find(int i, int j) {
		int p, q, temp;
		String st1 = new String(" ");
		p = i;
		q = j;
		if (p - 1 >= 0) {
			p--;
			if (ar[p][q] == 9) {
				st1 = st1 + ar[i][j];
				grid[p][q].setLabel(st1);
				grid[i][j].setLabel(" ");
				ar[p][q] = ar[i][j];
				ar[i][j] = 9;
				moves++;
				return 0;
			}
		}
		p = i;
		q = j;
		if (p + 1 <= 2) {
			p++;
			if (ar[p][q] == 9) {
				st1 = st1 + ar[i][j];
				grid[p][q].setLabel(st1);
				grid[i][j].setLabel(" ");
				ar[p][q] = ar[i][j];
				ar[i][j] = 9;
				moves++;
				return 0;
			}
		}
		p = i;
		q = j;
		if (q - 1 >= 0) {
			q--;
			if (ar[p][q] == 9) {
				st1 = st1 + ar[i][j];
				grid[p][q].setLabel(st1);
				grid[i][j].setLabel(" ");
				moves++;
				ar[p][q] = ar[i][j];
				ar[i][j] = 9;
				return 0;
			}
		}
		p = i;
		q = j;
		if (q + 1 <= 2) {
			q++;
			if (ar[p][q] == 9) {
				st1 = st1 + ar[i][j];
				grid[p][q].setLabel(st1);
				grid[i][j].setLabel(" ");
				moves++;
				ar[p][q] = ar[i][j];
				ar[i][j] = 9;
				return 0;
			}
		}
		return 0;
	}
	void setcol() {
		for (int ii = 0; ii < 3; ii++) {
			for (int jj = 0; jj < 3; jj++) {
				Color randomColor = new Color(0, 153, 153);
				//    Color randomColor = new Color(65,105,225);
				grid[ii][jj].setBackground(randomColor);

			}
		}
	}
	void rand() {
		int arr[] = new int[8];
		int k = 0, flag = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				flag = 0;
				int x = (int)(Math.random() * 8);
				x = x + 1;
				if (k == 0) {
					String str = new String(" ");
					str = str + x;
					grid[i][j].setLabel(str);
					ar[0][0] = x;
					arr[k] = x;
					k++;
				} else if (k <= 7) {

					while (flag == 0) {
						int chk = 0;
						for (int a = 0; a < k; a++) {
							if (arr[a] != x) {
								chk++;
							} else a = 10;
						}
						if (k == chk) {
							String str = new String(" ");
							str = str + x;
							grid[i][j].setLabel(str);
							arr[k] = x;
							ar[i][j] = x;
							k++;
							flag = 1;
						} else {
							x = (int)(Math.random() * 8);
							x = x + 1;
						}
					}
				}
				ar[2][2] = 9;
			}
		}
		setcol();

	}
}
