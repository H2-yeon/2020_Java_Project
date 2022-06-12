package finalproject;

import javax.sound.sampled.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;
import java.io.*;

public class game extends JFrame {
	int score = 0, life = 3, speed;
	JPanel upPanel = new JPanel();
	JPanel downPanel = new JPanel();
	JPanel board = new JPanel();
	Font ft1 = new Font("배달의민족 주아",Font.BOLD,20);
	JLabel info = new JLabel("SCORE: " + score + "         LIFE: " + life );
	JTextField tf = new JTextField(20); //입력창
	
	File file = new File("C:\\Users\\82104\\Desktop\\test.txt");
	Vector<String> v = new Vector<String>();
	
	int num = 10;
	int random = new Random().nextInt(300);
	int word = 0;
	String now = "";
	
	public game() {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String word = null;
			while((word=br.readLine()) != null) {
				v.add(word);
				System.out.println(word);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout(2,2));
		
		//UP Panel
		c.add(upPanel,BorderLayout.NORTH);
		upPanel.setBackground(Color.white);
		info.setFont(ft1);
		upPanel.add(info);
		
		
		//Down Panel
		c.add(downPanel,BorderLayout.SOUTH);
		downPanel.setBackground(Color.white);
		downPanel.add(tf); //입력창 추가
		tf.addActionListener(new Enter());
		tf.setFocusable(true);
		tf.requestFocus();
		
		//AcidRain Board
		WordPanel wordPanel = new WordPanel();
		c.add(wordPanel,BorderLayout.CENTER);
		wordPanel.setBackground(Color.gray);
		Thread t = new Thread(wordPanel);
		t.start();

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
		setTitle("산성비 게임 만들기");	
	}
	class Enter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(tf.getText().equals(v.get(word))) {
				try {
					Clip clip =AudioSystem.getClip();
					File audioFile = new File("C:\\Users\\82104\\Desktop\\correct.wav");
					AudioInputStream audioStream =AudioSystem.getAudioInputStream(audioFile);
					clip.open(audioStream);
					clip.setFramePosition(0);
					clip.start();
					score++;
					info.setText("SCORE: " + score + "         LIFE: " + life );
				reset();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			tf.setText("");
		}
	}
	
	class WordPanel extends JPanel implements Runnable{
		
		public WordPanel() {
			word = new Random().nextInt(v.size());
			reset();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.white);
			g.setFont(ft1);
			g.drawString(v.get(word), random, num);
		}
		
		public void run() {
			while(true) {
				if(num >= 300) {
					try {
						Clip wrong =AudioSystem.getClip();
						File sound = new File("C:\\Users\\82104\\Desktop\\wrong.wav");
						AudioInputStream Stream =AudioSystem.getAudioInputStream(sound);
						wrong.open(Stream);
						wrong.setFramePosition(0);
						wrong.start();
					} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					life--;
					info.setText("SCORE: " + score + "         LIFE: " + life );
					if(life==0) {
						try {
							Clip clip =AudioSystem.getClip();
							File audioFile = new File("C:\\Users\\82104\\Desktop\\gameOver.wav");
							AudioInputStream audioStream =AudioSystem.getAudioInputStream(audioFile);
							
							clip.open(audioStream);
							info.setText("GAME OVER!!");
							clip.setFramePosition(0);
							clip.start();
							Thread.sleep(4000);
							System.exit(0);
							break;
						}catch(InterruptedException |LineUnavailableException | IOException | UnsupportedAudioFileException e) {
							e.printStackTrace();
						}
					}
					reset();
				}
				num += 20;
				repaint();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
	
	public void reset() {
		random = new Random().nextInt(300);
		num = 10;
		word = new Random().nextInt(v.size());
		now = v.get(word);
		
	}
	public static void main(String[] args) {
		new game();
	}

}


