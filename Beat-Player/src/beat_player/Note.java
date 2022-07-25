package beat_player;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {
	
	// 노트 판정 과정
	// 노트 판정은 Queue 형식으로 구현한다. 즉 FIFO 형식
	// 이는 먼저 입력된 노트가 가자 먼저 출력되어야 하기 때문이다
	// Queue는 ArrayList로 구현한다
	
	// 노트 이미지
	private Image redNoteImage = new ImageIcon(Main.class.getResource("../images/redNote.png")).getImage();
	private Image orangeNoteImage = new ImageIcon(Main.class.getResource("../images/orangeNote.png")).getImage();
	private Image yellowNoteImage = new ImageIcon(Main.class.getResource("../images/yellowNote.png")).getImage();
	private Image greyNoteImage = new ImageIcon(Main.class.getResource("../images/greyNote.png")).getImage();
	private Image greenNoteImage = new ImageIcon(Main.class.getResource("../images/greenNote.png")).getImage();
	private Image blueNoteImage = new ImageIcon(Main.class.getResource("../images/blueNote.png")).getImage();
	private Image purpleNoteImage = new ImageIcon(Main.class.getResource("../images/purpleNote.png")).getImage();
	
	// 현재 노트 위치 확인을 위한 x, y 좌표
	// 이 중 y값의 시작 위치를 특정 값으로 고정시켜서 노트가 생성된 지 1초 뒤에 노트 판정위치인 586에 도달할 수 있도록 한다
	private int x, y = 586 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;
	private String noteType;
	
	// 현재 노트의 진행 여부, 즉 노트가 판정이 필요한 범위를 넘어가는지 확인할 수 있도록 한다
	private boolean proceeded = true;
	
	// 노트 판정, 이는 노트 타입을 반환한다
	public String getNoteType() {
		return noteType;
	}
	
	public boolean isProceeded() {
		return proceeded;
	}
	
	// 노트를 더이상 사용할 필요가 없다면 false
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
	
	// 노트가 내려오는 그래픽을 그리기 위한 screenDraw
	// 각 노트마다 색을 다르게 처리
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
	
	// 노트가 떨어지도록 만들어주는 함수
	public void drop() {
		y += Main.NOTE_SPEED;
		// y 좌표가 635를 넘어가면 Miss 판정
		if(y > 635) {
			System.out.println("Miss");
			close();
		}
	}
	
	// 스레드 실행 함수
	@Override
	public void run() {
		try {
			// 노트 떨어지는 것을 무한 반복
			// 1초에 Main.NOTE_SPEED * 100 정도만큼 움직인다
			while (true) {
				drop();
				if(proceeded) {
					// 떨어질 때 Main.SLEEP_TIME에 설정된 시간만큼 딜레이를 주면서 떨어진다
					// 현재 노트가 계속 움직이고 있다면 반복적으로 내려온다
					// 해당 노트에 대한 작업처리가 끝나면 proceeded가 false로 변경된다
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
	
	// 판정 함수
	// 판정을 후하게 측정했다
	public String judge() {
		if(y >= 561 && y <= 611) { // 586
			System.out.println("Perfect");
			close();
			return "Perfect";
		} else if(y >= 555 && y <= 617) {
			System.out.println("Great");
			close();
			return "Great";
		} else if(y >= 549 && y <= 623) {
			System.out.println("Good");
			close();
			return "Good";
		} else if(y >= 543 && y <= 629) {
			System.out.println("Bad");
			close();
			return "Bad";
		} else if(y >= 635 && y >= 537) {
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
