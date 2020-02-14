import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import java.awt.Color;

public class Puzzle {
	String name;
	private JFrame frame;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Puzzle window = new Puzzle();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Puzzle() {
		initialize();
		JOptionPane.showMessageDialog(frame, "ABOUT\n\nThe 9-box puzzle is a sliding puzzle which consists of 3x3 grid of numbered\nsquares with one square missing. The squares are jumbled when the puzzle\nstart and the goal of this game is to unjumble the squares by only sliding a\nsquare into the empty space.\n\n", "9 Box-Puzzle", JOptionPane.PLAIN_MESSAGE);
		name = JOptionPane.showInputDialog(null, "RULES\nTo move:  If there is an empty adjacent square next to a tile, a tile may be slid into the empty location.\nTo win:  The tiles must be moved back into their original positions, numbered 1 through 8.\n\nEnter your name: ", "9 Box-Puzzle", JOptionPane.QUESTION_MESSAGE);
	}

	private void initialize() {
		frame = new JFrame("9-Box Puzzle Game");
		frame.getContentPane().setBackground(UIManager.getColor("Slider.background"));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnImagePuzzle = new JButton("Image Puzzle");
		btnImagePuzzle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImagePuzzle puzzle;
				try {
					puzzle = new ImagePuzzle(name);
					puzzle.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
			}
		});
		btnImagePuzzle.setBounds(30, 115, 140, 21);
		frame.getContentPane().add(btnImagePuzzle);
		
		JButton btnNumberPuzzle = new JButton("Number Puzzle");
		btnNumberPuzzle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NumberPuzzle m = new NumberPuzzle(name);
			}
		});
		btnNumberPuzzle.setBounds(233, 115, 140, 21);
		frame.getContentPane().add(btnNumberPuzzle);
		
		JLabel lblBox = new JLabel("9 - Box Puzzle Game");
		lblBox.setBounds(206, 35, 220, 39);
		Font f=new Font("Serif", Font.BOLD, 22);
		lblBox.setFont(f);
		frame.getContentPane().add(lblBox);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Dell\\Pictures\\Saved Pictures\\download2.jpg"));
		lblNewLabel.setBounds(12, -4, 184, 259);
		frame.getContentPane().add(lblNewLabel);
	}
}
