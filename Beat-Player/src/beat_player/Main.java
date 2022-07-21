package beat_player;

public class Main {

	public static final int SCREEN_WIDTH = 1280; // 가로 1280 픽셀 고정
	public static final int SCREEN_HEIGHT = 720; // 세로 720 픽셀 고정
	public static final int NOTE_SPEED = 7; // 배속 설정
	public static final int SLEEP_TIME = 10; // 
	public static final int REACH_TIME = 1; // 노트가 판정노트에 도달하기까지의 시간(초)
	public static void main(String[] args) {
		
		new BeatPlayer();
	}
}