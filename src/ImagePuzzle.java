import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

class MyButton extends JButton {

    private boolean isLastButton;

    public MyButton() {

        super();

        initUI();
    }

    public MyButton(Image image) {

        super(new ImageIcon(image));

        initUI();
    }

    private void initUI() {

        isLastButton = false;
        BorderFactory.createLineBorder(Color.gray);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        });
    }

    public void setLastButton() {
        
        isLastButton = true;
    }

    public boolean isLastButton() {

        return isLastButton;
    }
}

public class ImagePuzzle extends JFrame implements ActionListener {

    private JPanel panel,panel2;
    private BufferedImage source;
    private BufferedImage resized;    
    private Image image;
    private MyButton lastButton;
    private int width, height; 
    Button newgame;
    Label l3;
	Label l4;
	Label LabelMoves;
	int moves;
	
    
    private List<MyButton> buttons;
    private List<Point> solution;

    private final int NUMBER_OF_BUTTONS = 9;
    private final int DESIRED_WIDTH =600;

    public ImagePuzzle(String name) throws Exception {
    	boolean flag = false;
    	String file = new String();
    //	int turn = 0;
//    		while(!flag ) {
	    	JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	        int r = j.showOpenDialog(null);
	        if (r == JFileChooser.APPROVE_OPTION) {
	            file = j.getSelectedFile().getAbsolutePath();
	        }
	        if(file.endsWith(".jpg") || file.endsWith(".jpeg")) {
//	        	flag=true;
	        }
	        else {
	        	JOptionPane.showMessageDialog(null,"Select the '.jpg' or '.jpeg' file!!", "Error", JOptionPane.ERROR_MESSAGE);
	        	throw new Exception("File Type Error!");
	        }
	       
//    	}
        
    	
    	initUI(name,file);
    }

    private void initUI(String name,String path) {

        solution = new ArrayList<>();
        
        solution.add(new Point(0, 0));
        solution.add(new Point(0, 1));
        solution.add(new Point(0, 2));
        solution.add(new Point(1, 0));
        solution.add(new Point(1, 1));
        solution.add(new Point(1, 2));
        solution.add(new Point(2, 0));
        solution.add(new Point(2, 1));
        solution.add(new Point(2, 2));
//        solution.add(new Point(3, 0));
//        solution.add(new Point(3, 1));
//        solution.add(new Point(3, 2));

        buttons = new ArrayList<>();
        JPanel panel2 = new JPanel();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
//        panel2.setLayout(mgr);
        panel.setLayout(new GridLayout(3, 3, 0, 0));
//        newgame = new Button("New Game");
//		newgame.setForeground(Color.BLACK);
//		newgame.setBackground(Color.MAGENTA);
        String labelUser = new String("Username: ");
        labelUser = labelUser+name;
		l3 = new Label(labelUser);
		l4 = new Label("    ");
		LabelMoves = new Label("Moves: 0    ");
//		newgame.addActionListener(this);
		moves=0;
		
		
		
		
//		panel2.add(newgame);
		panel2.add(l4);
		panel2.add(LabelMoves);
		panel2.add(l3);
		add(panel2, BorderLayout.NORTH);
		
        try {
            source = loadImage(path);
            int h = getNewHeight(source.getWidth(), source.getHeight());
            resized = resizeImage(source, DESIRED_WIDTH, h,
                    BufferedImage.TYPE_INT_ARGB);

        } catch (IOException ex) {
            Logger.getLogger(ImagePuzzle.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        width = resized.getWidth(null);
        height = resized.getHeight(null);

        add(panel, BorderLayout.CENTER);

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                image = createImage(new FilteredImageSource(resized.getSource(),
                        new CropImageFilter(j * width / 3, i * height / 3,
                                (width / 3), height / 3)));
                
                MyButton button = new MyButton(image);
                button.putClientProperty("position", new Point(i, j));

                if (i == 2 && j == 2) {
                    lastButton = new MyButton();
                    lastButton.setBorderPainted(false);
                    lastButton.setContentAreaFilled(false);
                    lastButton.setLastButton();
                    lastButton.putClientProperty("position", new Point(i, j));
                } else {
                    buttons.add(button);
                }
            }
        }

        
        
        Collections.shuffle(buttons);
        buttons.add(lastButton);

        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {

            MyButton btn = buttons.get(i);
            panel.add(btn);
            btn.setBorder(BorderFactory.createLineBorder(Color.gray));
            btn.addActionListener(new ClickAction());
        }

        pack();
        setTitle("Puzzle");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == newgame) {
			Collections.shuffle(buttons);
//			JOptionPane.showMessageDialog(null, "ABOUT\n\nThe 9-box puzzle is a sliding puzzle which consists of 3x3 grid of numbered\nsquares with one square missing. The squares are jumbled when the puzzle\nstart and the goal of this game is to unjumble the squares by only sliding a\nsquare into the empty space.\n\n", "9 Box-Puzzle", JOptionPane.PLAIN_MESSAGE);
//			initUI();
//			
//			 buttons.add(lastButton);
//			panel.removeAll();
//			panel.setBorder(BorderFactory.createLineBorder(Color.gray));
////	        panel2.setLayout(mgr);
//	        panel.setLayout(new GridLayout(3, 3, 0, 0));
//			  for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
////
//		            MyButton btn = buttons.get(i);
//		            panel.add(btn);
//			
//		            btn.setBorder(BorderFactory.createLineBorder(Color.gray));
//		            //btn.addActionListener(new ClickAction());
//		        }
//			  return;
		}
   
//		checkSolution();

	}

    private int getNewHeight(int w, int h) {

        double ratio = DESIRED_WIDTH / (double) w;
        int newHeight = (int) (h * ratio);
        return newHeight;
    }

    private BufferedImage loadImage(String path) throws IOException {

        BufferedImage bimg = ImageIO.read(new File(path));

        return bimg;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width,
            int height, int type) throws IOException {

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

    private class ClickAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {

            checkButton(e);
            checkSolution();
        }

        private void checkButton(ActionEvent e) {

            int lidx = 0;
            
            for (MyButton button : buttons) {
                if (button.isLastButton()) {
                    lidx = buttons.indexOf(button);
                }
            }

            JButton button = (JButton) e.getSource();
            int bidx = buttons.indexOf(button);

            if ((bidx - 1 == lidx) || (bidx + 1 == lidx)
                    || (bidx - 3 == lidx) || (bidx + 3 == lidx)) {
                Collections.swap(buttons, bidx, lidx);
                moves++;
                updateButtons();
                //moves++;
            }
        }

        /**
         * 
         */
        private void updateButtons() {

            panel.removeAll();

            for (JComponent btn : buttons) {

                panel.add(btn);
            }
            LabelMoves.setText("Moves: "
            		+ ""+String.valueOf(moves));
            panel.validate();
        }
    }

    private void checkSolution() {

        List<Point> current = new ArrayList<>();

        for (JComponent btn : buttons) {
            current.add((Point) btn.getClientProperty("position"));
        }

        if (compareList(solution, current)) {
            JOptionPane.showMessageDialog(panel, "Finished", "Congratulation", JOptionPane.INFORMATION_MESSAGE);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    public static boolean compareList(List ls1, List ls2) {
        
        return ls1.toString().contentEquals(ls2.toString());
    }

//    public static void main(String[] args) {
////
////        EventQueue.invokeLater(new Runnable() {
////
////            @Override
////            public void run() {
//                PuzzleEx puzzle = new PuzzleEx("user");
//                puzzle.setVisible(true);
////            }
////        });
//    }
}