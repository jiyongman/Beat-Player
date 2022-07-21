package beat_player;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {
	
	private Image redNoteImage = new ImageIcon(Main.class.getResource("../images/redNote.png")).getImage();
	private Image orangeNoteImage = new ImageIcon(Main.class.getResource("../images/orangeNote.png")).getImage();
	private Image yellowNoteImage = new ImageIcon(Main.class.getResource("../images/yellowNote.png")).getImage();
	private Image greyNoteImage = new ImageIcon(Main.class.getResource("../images/greyNote.png")).getImage();
	private Image greenNoteImage = new ImageIcon(Main.class.getResource("../images/greenNote.png")).getImage();
	private Image blueNoteImage = new ImageIcon(Main.class.getResource("../images/blueNote.png")).getImage();
	private Image purpleNoteImage = new ImageIcon(Main.class.getResource("../images/purpleNote.png")).getImage();
	private int x, y = 586 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;
	private String noteType;
	private boolean proceeded = true;
	
	public String getNoteType() {
		return noteType;
	}
	
	public boolean isProceeded() {
		return proceeded;
	}
	
	public void close() {
		proceeded = false;
	}
	public Note(String noteType) {
		if(noteType.equals("S")) {
			x = 278;
		} else if (noteType.equals("D")) {
			x = 382;
		} else if (noteType.equals("F")) {
			x = 486;
		} else if (noteType.equals("Space")) {
			x = 590;
		} else if (noteType.equals("J")) {
			x = 694;
		} else if (noteType.equals("K")) {
			x = 798;
		} else if (noteType.equals("L")) {
			x = 902;
		}
		this.noteType = noteType;
	}
	
	public void screenDraw(Graphics2D g) {
		if(noteType.equals("S")) {
			g.drawImage(redNoteImage, x, y, null);
		} else if(noteType.equals("D")) {
			g.drawImage(orangeNoteImage, x, y, null);
		} else if(noteType.equals("F")) {
			g.drawImage(yellowNoteImage, x, y, null);
		} else if(noteType.equals("Space")) {
			g.drawImage(greyNoteImage, x, y, null);
		} else if(noteType.equals("J")) {
			g.drawImage(greenNoteImage, x, y, null);
		} else if(noteType.equals("K")) {
			g.drawImage(blueNoteImage, x, y, null);
		} else if(noteType.equals("L")) {
			g.drawImage(purpleNoteImage, x, y, null);
		}
			
	}
	public void drop() {
		y += Main.NOTE_SPEED;
		if(y > 635) {
			System.out.println("Miss");
			close();
		}
	}
	@Override
	public void run() {
		try {
			while (true) {
				drop();
				if(proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
				} else {
					interrupt();
					break;
				}
			}
			
		} catch(Exception e) {
				System.err.println(e.getMessage());
		}	
	}
	
	public String judge() {
		if(y >= 570 && y <= 600) { // 586
			System.out.println("Perfect");
			close();
			return "Perfect";
		} else if(y >= 560 && y <= 610) {
			System.out.println("Great");
			close();
			return "Great";
		} else if(y >= 550 && y <= 620) {
			System.out.println("Good");
			close();
			return "Good";
		} else if(y >= 540 && y <= 630) {
			System.out.println("Bad");
			close();
			return "Bad";
		} else if(y >= 635 && y >= 530) {
			System.out.println("Miss");
			close();
			return "Miss";
		}
		return "None";
	}
	
	public int getY() {
		return y;
	}
}
