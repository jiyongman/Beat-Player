package beat_player;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BeatPlayer extends JFrame {
	
	// ImageIcon 생성자를 이용해서 Main.class에 있는 위치에서 리소스를 가져온다
	// 이후 다시 getter를 이용해서 해당 이미지를 IntroBackground에 넣어준다
	// jar 파일로 만들 때는 getClass.getResource를 사용한다
	
	private Image screenImage; 
	private Graphics screenGraphic; // 더블 버퍼링 기법 사용
	
	// 오른쪽 위 exitButton의 기본 이미지 생성과 Button에 마우스를 올렸을 때 변하는 이미지를 생성한다
	// 초기 화면의 startButton, quit(exit)Button 역시 같은 방법으로 이미지를 생성한다
	// startButton을 클릭한 뒤 음악 선택 화면에서도 마찬가지로 leftButton, rightButton, playButton을 생성한다
	// playButton을 클릭한 뒤 게임 시작 화면에서 오른쪽 위 back(home)Button 이미지를 생성한다
	
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/exitButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/exitButtonBasic.png"));
	private ImageIcon startButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/startButtonEntered.png"));
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("../images/startButtonBasic.png"));
	private ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/quitButtonEntered.png"));
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("../images/quitButtonBasic.png"));
	private ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/leftButtonEntered.png"));
	private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("../images/leftButtonBasic.png"));
	private ImageIcon rightButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/rightButtonEntered.png"));
	private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("../images/rightButtonBasic.png"));
	private ImageIcon playButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/playButtonEntered.png"));
	private ImageIcon playButtonBasicImage = new ImageIcon(Main.class.getResource("../images/playButtonBasic.png"));
	private ImageIcon backButtonEnteredImage = new ImageIcon(Main.class.getResource("../images/backButtonEntered.png"));
	private ImageIcon backButtonBasicImage = new ImageIcon(Main.class.getResource("../images/backButtonBasic.png"));
	
	// BackGround 객체는 이후 아래에서 화면이 전환될 때 이곳에 다른 사진을 넣어서 배경 사진이 바뀔 수 있도록 한다
	// 시작화면 background
	
	private Image background = new ImageIcon(Main.class.getResource("../images/introBackground(Title).jpg")).getImage();
	
	// menuBar 객체 안에 menuBar 이미지가 들어가게 된다
	
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	
	// Button 생성
	
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);
	private JButton playButton = new JButton(playButtonBasicImage);
	private JButton backButton = new JButton(backButtonBasicImage);
	
	private int mouseX, mouseY; // 윈도우 창 위치를 menuBar를 끌어서 옮길 수 있도록 하는 마우스 좌표
	
	
	private boolean isMainScreen = false; // 게임에 맞춰 화면을 표시하기 위한 변수
	private boolean isGameScreen = false; // 게임 안으로 넘어왔는지 확인하기 위한 변수
	
	// 각 음악에 대한 정보를 배열로 만들어 담았다
	
	ArrayList<Track> trackList = new ArrayList<Track>();
	
	// trackList 안에 있는 값에 따라 아래 변수들의 값이 달라진다
	// noeSelected 값은 현재 선택된 track의 번호이자 ArrayList의 index를 의미한다
	
	private Image selectedImage;
	private Music selectedMusic;
	private Music introMusic = new Music("TheFatRat - Jackpot.mp3", true); // intro 음악 정의
	private int nowSelected = 0;
	
	// Game 인스턴스 생성 및 초기화
	// game 변수는 단 하나의 게임만 진행 가능하며 game 변수 자체가 프로젝트 전체에서 사용되어야 하기 때문에 static으로 설정한다
	
	public static Game game;
	
	public BeatPlayer() {
		
		trackList.add(new Track("Invincible.jpg", "Invincible2.jpg", "DEAF KEV - Invincible Selected.mp3", 
				"DEAF KEV - Invincible.mp3", "DEAF KEV - Invincible", "04:33"));
		trackList.add(new Track("Hellcat.jpg", "Hellcat2.jpg", "Desmeon - Hellcat Selected.mp3", 
				"Desmeon - Hellcat.mp3", "Desmeon - Hellcat", "03:46"));
		
		setUndecorated(true); // 기본 윈도우 menuBar 삭제
		setTitle("Beat Player");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false); // 한번 창이 생성되면 임의적으로 창 크기 변경 불가
		setLocationRelativeTo(null); // 창이 화면의 정중앙에 위치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 게임 창을 종료 시 프로그램 전체 종료
		setVisible(true); // 창이 보이는 여부, 반드시 true
		setBackground(new Color(0, 0, 0, 0)); // paintComponent 했을 때 배경을 흰색으로 설정
		setLayout(null); // Button이나 JLabel을 넣었을 때 그 위치 그대로 넣어진다
		
		// add 해서 KeyListener에 내가 만든 KeyListen 인식
		
		addKeyListener(new KeyListener());
		
		introMusic.start(); // 게임 시작 시 intro 음악 재생
		
		// 오른쪽 위 exit(x) Button
		
		exitButton.setBounds(1250, 0, 30, 30); // 왼쪽부터 x, y, 길이, 높이,
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		
		// 아래처럼 추상 클래스, 인터페이스 등을 상속 및 구현하는 클래스를 직접 생성하는 것 대신 { }를 통해 생성하는 것을 익명 클래스하고 한다
		// new MouseAdapter() 뒤에 부분은 익명 클래스로 취급된다
		
		exitButton.addMouseListener(new MouseAdapter() {
			@Override // 버튼에 마우스를 올렸을 때 이벤트
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override // 버튼에 마우스를 뗐을 때 이벤트 
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override // 버튼을 마우스로 눌렀을 때 이벤트
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				buttonPressedMusic.start();
				System.exit(0);
			}
		});
		add(exitButton);
		
		// 게임 시작 버튼
		
		startButton.setBounds(512, 520, 256, 64);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				buttonPressedMusic.start();
				introMusic.close();
				enterMain();
			}
		});
		add(startButton);
		
		// 시작 버튼 밑에 있는 종료 버튼(exit)
		
		quitButton.setBounds(512, 620, 256, 64);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitButton.setIcon(quitButtonEnteredImage);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				quitButton.setIcon(quitButtonBasicImage);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				buttonPressedMusic.start();
				System.exit(0);
			}
		});
		add(quitButton);
		
		// 왼쪽으로 넘기기 버튼
		
		leftButton.setVisible(false);
		leftButton.setBounds(120, 310, 80, 80);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				buttonPressedMusic.start();
				selectLeft();
			}
		});
		add(leftButton);

		// 오른쪽으로 넘기기 버튼
		
		rightButton.setVisible(false);
		rightButton.setBounds(1080, 310, 80, 80);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightButtonEnteredImage);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				buttonPressedMusic.start();
				selectRight();
			}
		});
		add(rightButton);
		
		// Play 버튼
		
		playButton.setVisible(false);
		playButton.setBounds(512, 620, 256, 64);
		playButton.setBorderPainted(false);
		playButton.setContentAreaFilled(false);
		playButton.setFocusPainted(false);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playButton.setIcon(playButtonEnteredImage);
				playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				playButton.setIcon(playButtonBasicImage);
				playButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				buttonPressedMusic.start();
				gameStart(nowSelected);
			}
		});
		add(playButton);
		
		// 오른쪽 위에 있는 나가기 뒤로가기(home) 버튼
		
		backButton.setVisible(false);
		backButton.setBounds(1220, 0, 30, 30);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backButton.setIcon(backButtonEnteredImage);
				backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setIcon(backButtonBasicImage);
				backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				buttonPressedMusic.start();
				backMain();
			}
		});
		add(backButton);
		
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);
	}
	
	// paint 메소드는 JFrame에서 상속받아 화면을 그릴 때 가장 먼저 실행되는 함수
	// 아래 순서대로 실행된다
	// 1. JFrame을 실행하면 updated(Graphics g)가 가장 먼저 실행된다
	// 2. 다음으로 paint(Graphics g)가 실행된다
	// 3. 이때 paint 함수 안에서 repaint()를 실행함으로써 paint() -> repaint() -> paint() 식으로 반복된다
	// 이렇게 화면을 계속 띄워주는 이유는 한 번만 화면을 띄워주게 되면 버퍼링 현상이 심해서 느려지는 문제가 발생하기 때문이다
	// 이런 기법을 더블 버퍼링 기법이라고 한다 (버퍼에 이미지를 담아서 계속 갱신해준다)
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 윈도우 화면 크기만큼 생성
		screenGraphic = screenImage.getGraphics(); // screenImage를 이용해서 그래픽 객체를 얻어온다
		screenDraw((Graphics2D) screenGraphic); // Graphics2D로 형변환
		g.drawImage(screenImage, 0, 0, null); // 윈도우 창에 screenImage를 뿌려준다
	}

	public void screenDraw(Graphics2D g) { // 매개변수를 Graphics에서 Graphics2D로 변경
		g.drawImage(background, 0, 0, null); // drawImage 메소드를 IntroBackground x, y 좌표에 그려준다
		if (isMainScreen) { // isMainScreen = true면 selectedImage를 보여준다
			g.drawImage(selectedImage, 320, 140, null);
		}
		if (isGameScreen) { // 인게임 화면에서의 그래픽, Game 클래스에서 관리
			game.screenDraw(g);
		}
		paintComponents(g); // JLabel처럼 추가된 요소를 그리는 것
		try {
			Thread.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.repaint();
	}
	
	// 현재 선택된 곡의 번호를 넣어줌으로써 해당 곡이 선택됨을 알려준다
	
	public void selectTrack(int nowSelected) {
		if(selectedMusic != null) { // 선택한 곡이 null이 아니면 해당 음악을 종료
			selectedMusic.close();
		}
		
		// 현재 선택된 곡이 가지고 있는 nowSelected 번호를 갖고 아래의 각 정보를 가져온다
		
		selectedImage = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage())).getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
		selectedMusic.start();
	}
	
	// 왼쪽 버튼 메소드
	
	public void selectLeft() {
		if(nowSelected == 0) { // 0번째 곡일때는 전체 trackList 크기에서 -1을 한다
			nowSelected = trackList.size() - 1;
		} else {
			nowSelected--;
		}
		selectTrack(nowSelected);
	}
	
	public void selectRight() { 
		if(nowSelected == trackList.size() - 1) { // 마지막 곡일때는 가장 처음으로 돌아가도록 한다
			nowSelected = 0;
		} else {
			nowSelected++;
		}
		selectTrack(nowSelected);
	}
	
	public void gameStart(int nowSelected) {
		
		if(selectedMusic != null) // 현재 재생되고 있는 음악이 있다면 음악 종료
			selectedMusic.close();
		isMainScreen = false; // screenDraw 함수에서 isMainScreen 부분을 멈춘다
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		playButton.setVisible(false);
		
		// backGround 이미지를 인게임 이미지로 변경한다
		
		background = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getGameImage())).getImage();
		backButton.setVisible(true);
		isGameScreen = true; // 인게임 전환 확인
		
		// 게임 시작 시 해당 선택된 곡 이름과 곡, 그리고 곡 시간을 가져온다
		
		game = new Game(trackList.get(nowSelected).getTitleName(), trackList.get(nowSelected).getGameMusic(), trackList.get(nowSelected).getMusicTime());
		game.start(); // game 인스턴스 안에 있는 run 함수 실행
		setFocusable(true);
	}
	
	public void backMain() {
		isMainScreen = true;
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		playButton.setVisible(true);
		background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		backButton.setVisible(false);
		selectTrack(nowSelected);
		isGameScreen = false;
		game.close();
	}
	
	public void enterMain() { // 메인 화면 함수
		startButton.setVisible(false);
		quitButton.setVisible(false);
		background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
		isMainScreen = true;
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		playButton.setVisible(true);
		introMusic.close();
		selectTrack(0); // nowSelected 번째 인덱스 재생
	}

}
