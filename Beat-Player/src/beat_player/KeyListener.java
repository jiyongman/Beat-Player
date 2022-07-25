package beat_player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
	
	@Override
	public void keyPressed(KeyEvent e) {
		// e.getKeyCode는 사용자가 누르는 키
		// KeyEvent.VK_ 는 컴퓨터가 갖는 키
		// static으로 설정된 game이 null 값을 갖는다면 return해서 키보드 이벤트가 실행되지 않도록 한다
		if(BeatPlayer.game == null) {
			return;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			BeatPlayer.game.pressS();
		} else if(e.getKeyCode() == KeyEvent.VK_D) {
			BeatPlayer.game.pressD();
		} else if(e.getKeyCode() == KeyEvent.VK_F) {
			BeatPlayer.game.pressF();
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			BeatPlayer.game.pressSpace();
		} else if(e.getKeyCode() == KeyEvent.VK_J) {
			BeatPlayer.game.pressJ();
		} else if(e.getKeyCode() == KeyEvent.VK_K) {
			BeatPlayer.game.pressK();
		} else if(e.getKeyCode() == KeyEvent.VK_L) {
			BeatPlayer.game.pressL();
		} 
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(BeatPlayer.game == null) {
			return;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			BeatPlayer.game.releaseS();
		} else if(e.getKeyCode() == KeyEvent.VK_D) {
			BeatPlayer.game.releaseD();
		} else if(e.getKeyCode() == KeyEvent.VK_F) {
			BeatPlayer.game.releaseF();
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			BeatPlayer.game.releaseSpace();
		} else if(e.getKeyCode() == KeyEvent.VK_J) {
			BeatPlayer.game.releaseJ();
		} else if(e.getKeyCode() == KeyEvent.VK_K) {
			BeatPlayer.game.releaseK();
		} else if(e.getKeyCode() == KeyEvent.VK_L) {
			BeatPlayer.game.releaseL();
		} 
	}

}
