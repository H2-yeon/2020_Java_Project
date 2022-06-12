import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class game extends JFrame implements Runnable{
	public game() {
		JPanelTest win=new JPanelTest();
		setTitle("또따초코볼");
		win.spanel=new startPanel(win);
		win.cpanel=new chocoPanel(win);
		win.s1panel=new start1Panel(win);
		win.add(win.spanel);
		win.setSize(1000,1000);
		win.setResizable(false);
		win.setVisible(true);
		win.setFocusable(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocationRelativeTo(null);
		win.addKeyListener(new KeyListener());
		
	}
	class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if(chocoPanel.xpos<=0) chocoPanel.xpos=0;
                else chocoPanel.xpos-=20;
                break;
            case KeyEvent.VK_RIGHT:
              	if(chocoPanel.xpos>=800) chocoPanel.xpos=800;
               	else chocoPanel.xpos+=20;
                break;
            }
        }
    }
	
	@Override
	public void run() {
		try{
            while(true) {           	
                repaint();
                Thread.sleep(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
	}

	static public void main(String[] args) {
		game Game = new game();		
	}
}
class JPanelTest extends JFrame{
	public startPanel spanel =null;
	public chocoPanel cpanel=null;
	public start1Panel s1panel=null;
	public void change(String panelName) {
		if(panelName.equals("s1panel")) {
			getContentPane().removeAll();
			getContentPane().add(s1panel);
			revalidate();
			repaint();
		}
		if(panelName.equals("cpanel")) {
			getContentPane().removeAll();
			getContentPane().add(cpanel);
			revalidate();
			repaint();
		}
	}
}
class startPanel extends JPanel {
	private ImageIcon icon = new ImageIcon("source/sbg.jpg");
	private Image img = icon.getImage();
	private JPanelTest win;
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),null);
		g.setColor(Color.black);
	}
	public startPanel(JPanelTest win) {
		this.win=win;
		setLayout(null);
		JButton btn=new JButton();
		btn.setSize(1000, 1000);
		btn.setLocation(0, 0);
		add(btn);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.addActionListener(new MyActionListener());
	}
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			win.change("s1panel");
		}
	}
}
class start1Panel extends JPanel {
	private ImageIcon icon = new ImageIcon("source/sbg2.jpg");
	private Image img = icon.getImage();
	private JPanelTest win;
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(),null);
		g.setColor(Color.black);
	}
	public start1Panel(JPanelTest win) {
		this.win=win;
		setLayout(null);
		JButton btn=new JButton();
		btn.setSize(1000, 1000);
		btn.setLocation(0, 0);
		add(btn);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setFocusPainted(false);
		btn.addActionListener(new MyActionListener());
	}
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			win.change("cpanel");
		}
	}
}
class chocoPanel extends JPanel {
	private JPanelTest win;
	static int xpos=200;
	private ImageIcon icon = new ImageIcon("source/bg.jpg");
	private ImageIcon chocoball = new ImageIcon("source/choco.png");
	private ImageIcon player = new ImageIcon("source/player.png");
	private ImageIcon Fire = new ImageIcon("source/fire.png");
	private ImageIcon Special = new ImageIcon("source/special.png");
	private Image img = icon.getImage();
	private Image cb = chocoball.getImage();
	private Image f = Fire.getImage();
	private Image s = Special.getImage();
	private Image p=player.getImage();
	private final int choco =8;
	private final int fire =8;
	private final int special =1;
	int score=0;
	int life=3;
	private final int choco_SIZE = 60;
	private final int fire_SIZE = 60;
	private final int special_SIZE = 60;
		
	Vector<Point> chocoVector = new Vector<Point>();
	Vector<Point> fireVector = new Vector<Point>();
	Vector<Point> specialVector = new Vector<Point>();
		
	public chocoPanel(JPanelTest win) {
		setLayout(null);
		this.win=win;
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				addchoco();
				addfire();
				addspecial();
				new cfThread().start();
				chocoPanel.this.removeComponentListener(this);
			}
		});
	}
		
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0,0,1000,1000);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		g.drawImage(p, xpos,710,200,240,this);
		drawchoco(g); // 눈을 그린다.
		drawfire(g);
		drawspecial(g);
		g.setFont(new Font("배달의민족 주아", Font.BOLD, 20));
		g.drawString(" "+score,37, 33);
		g.drawString("LIFE : "+life,900, 33);
		
		g.drawImage(cb, 10, 16, 22, 22, this);
	}

	private void addchoco() {
		for(int i=0; i<choco; i++) {
			Point p = new Point((int)(Math.random()*getWidth()),(int)-(Math.random()*getHeight()));
			chocoVector.add(p);
		}
	}
		
	private void addfire() {
		for(int i=0; i<fire; i++) {
			Point p = new Point((int)(Math.random()*getWidth()),(int)-(Math.random()*getHeight()));
			fireVector.add(p);
		}
	}
	private void addspecial() {
		for(int i=0; i<special; i++) {
			Point p = new Point((int)(Math.random()*getWidth()),(int)-(Math.random()*getHeight()));
			specialVector.add(p);
		}
	}
	private void drawchoco(Graphics g) { 
		g.setColor(Color.WHITE);			
			for(int i=0; i<chocoVector.size(); i++) {
			Point p = chocoVector.get(i);			
			if(p.x>940) p.x=940;
			g.drawImage(cb, p.x, p.y,choco_SIZE, choco_SIZE, this);
		}
	}
	private void drawfire(Graphics g) { 
		g.setColor(Color.WHITE);			
		for(int i=0; i<fireVector.size(); i++) {
			Point p = fireVector.get(i);			
			if(p.x>940) p.x=940;
			g.drawImage(f, p.x, p.y,fire_SIZE, fire_SIZE, this);
		}
	}
	private void drawspecial(Graphics g) { 
		g.setColor(Color.WHITE);			
		for(int i=0; i<specialVector.size(); i++) {
			Point p = specialVector.get(i);			
			if(p.x>940) p.x=940;
			g.drawImage(s, p.x, p.y,special_SIZE, special_SIZE, this);
		}
	}
	private void changespecialPosition() { 
		for(int i=0; i<special; i++) {
			Point p = specialVector.get(i);
			int offsetY = (int)(Math.random()*7);
			if(p.x < 0) p.x = 0;
			p.y += offsetY;
			if(p.x>=xpos&&p.x+60<=xpos+200&&p.y+60>=725&&p.y+60<=730){ 
				try {
					Clip clip =AudioSystem.getClip();
					File audioFile = new File("source/getg.wav");
					AudioInputStream audioStream =AudioSystem.getAudioInputStream(audioFile);
					clip.open(audioStream);
					clip.setFramePosition(0);
					clip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
				life+=1;
				score+=3;
				p.x = (int)(Math.random()*getWidth());
				if(p.x>940) p.x=940;
				p.y = 5; 
			}
			if(p.y > getHeight()) {
				p.x = (int)(Math.random()*getWidth()); 
				if(p.x>940) p.x=940;
				p.y = 5;
			}
		}		
	}
	
	private void changechocoPosition() { 
		for(int i=0; i<choco; i++) {
			Point p = chocoVector.get(i);
			int offsetY = (int)(Math.random()*7);
			if(p.x < 0) p.x = 0;
			p.y += offsetY;
			if(p.x>=xpos&&p.x+60<=xpos+200&&p.y+60>=725&&p.y+60<=730){ 
				try {
					Clip clip =AudioSystem.getClip();
					File audioFile = new File("source/get.wav");
					AudioInputStream audioStream =AudioSystem.getAudioInputStream(audioFile);
					clip.open(audioStream);
					clip.setFramePosition(0);
					clip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
				score+=1;
				p.x = (int)(Math.random()*getWidth());
				if(p.x>940) p.x=940;
				p.y = 5;
			}
			if(p.y > getHeight()) { 
				p.x = (int)(Math.random()*getWidth()); 
				if(p.x>940) p.x=940;
				p.y = 5;
			}
		}		
	}
		
	private void changefirePosition() { 
		for(int i=0; i<fire; i++) {
			Point p = fireVector.get(i);
			int offsetY = (int)(Math.random()*7);
			if(p.x < 0) p.x = 0;
			p.y += offsetY;
			if(p.x>=xpos&&p.x+60<=xpos+200&&p.y+60>=725&&p.y+60<=730){ 
				try {
					Clip clip =AudioSystem.getClip();
					File audioFile = new File("source/getf.wav");
					AudioInputStream audioStream =AudioSystem.getAudioInputStream(audioFile);
					clip.open(audioStream);
					clip.setFramePosition(0);
					clip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
				life--;
				p.x = (int)(Math.random()*getWidth());
				if(p.x>955) p.x=955;
				p.y = 5;
			}
			if(p.y > getHeight()) { 
				p.x = (int)(Math.random()*getWidth()); 
				if(p.x>955) p.x=955;
				p.y = 5;
			}
		}		
	}

	class cfThread extends Thread {
		@Override
		public void run() {
			while(true) {
				try {
					if(score>=0&&score<10) {
						sleep(10);
					}
					else if(score>=10&&score<20) {
						sleep(9);
					}
					else if(score>=20&&score<30) {
						sleep(8);
					}
					else if(score>=30&&score<40) {
						sleep(7);
					}
					else if(score>=40&&score<50) {
						sleep(6);
					}
					else if(score>=50&&score<60) {
						sleep(5);
					}
					else if(score>=60) {
						sleep(4);
					}
				} catch (InterruptedException e) { return; }
				
				changechocoPosition();
				changefirePosition();
				changespecialPosition();
				chocoPanel.this.repaint();
				
				if(life==0) {
					if(score>=50) {
					 	Clip clip;
						new resultwinPanel();
						try {
							clip = AudioSystem.getClip();
							File audioFile = new File("source/TADA.wav");
							AudioInputStream audioStream =AudioSystem.getAudioInputStream(audioFile);
							clip.open(audioStream);
							clip.setFramePosition(0);
							clip.start();
						} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
							e.printStackTrace();
						}
						break;
					}
					else {
						Clip clip;
						new resultlosePanel();
						try {
							clip = AudioSystem.getClip();
							File audioFile = new File("source/gameover.wav");
							AudioInputStream audioStream =AudioSystem.getAudioInputStream(audioFile);
							clip.open(audioStream);
							clip.setFramePosition(0);
							clip.start();
						} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
							e.printStackTrace();
						}
						break;
					}					
				}				
			}
		}
	}
}

class resultlosePanel extends JFrame {
	private ImageIcon icon = new ImageIcon("source/rbg1.jpg");
	private Image img = icon.getImage();
	public resultlosePanel() {
		setTitle("GameOver");
		setSize(250,300);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, 250,300,null);
		g.setColor(Color.black);
		g.setFont(new Font("배달의민족 주아", Font.BOLD, 35));
		g.drawString("GAME OVER",31,80);
	}
}

class resultwinPanel extends JFrame {
	private ImageIcon icon = new ImageIcon("source/rbg.jpg");
	private Image img = icon.getImage();
	public resultwinPanel() {
		setTitle("GameClear");
		setSize(250,300);
		setResizable(false);
		setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, 250,300,null);
		g.setColor(Color.black);
		g.setFont(new Font("배달의민족 주아", Font.BOLD, 30));
		g.drawString("GAME CLEAR",37,60);
	}
}
