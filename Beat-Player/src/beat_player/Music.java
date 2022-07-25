package beat_player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread {
	
	// MP3를 재생시켜주는 클래스
	private Player player; 
	
	// 음악의 무한루프 확인
	private boolean isLoop;
	
	// 파일 가져오는 클래스
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	// 음악 MP3 파일 이름, 음악 반복재생 여부
	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop; // isLoop 초기화
			
			// 음악 파일을 inputStream에 넣어서 가져온다
			file = new File(Main.class.getResource("../music/" + name).toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 실행되는 음악의 시간 위치
	// 음악에 맞춰 노트를 떨어뜨릴 때 getTime을 통해 분석한다
	public int getTime() {
		if (player == null)
			return 0;
		return player.getPosition();
	}
	
	// 음악을 종료하는 메소드
	public void close() {
		isLoop = false;
		player.close();
		this.interrupt();
	}
	
	@Override
	public void run() {
		try {
			do {
				// 버퍼에 담긴 음악 파일을 play한다
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while (isLoop);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
