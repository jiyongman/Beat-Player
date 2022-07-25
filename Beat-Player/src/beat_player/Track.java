package beat_player;

public class Track {

	private String startImage; // 게임 선택 창 표지 이미지
	private String gameImage; // 해당 곡을 실행했을 때 표지 이미지
	private String startMusic; // 게임 선택 창 음악
	private String gameMusic; // 해당 곡을 실행했을 때 음악
	private String titleName; // 곡 제목
	private String musicTime; // 곡 시간
	

	public String getStartImage() {
		return startImage;
	}
	public void setStartImage(String startImage) {
		this.startImage = startImage;
	}
	public String getGameImage() {
		return gameImage;
	}
	public void setGameImage(String gameImage) {
		this.gameImage = gameImage;
	}
	public String getStartMusic() {
		return startMusic;
	}
	public void setStartMusic(String startMusic) {
		this.startMusic = startMusic;
	}
	public String getGameMusic() {
		return gameMusic;
	}
	public void setGameMusic(String gameMusic) {
		this.gameMusic = gameMusic;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getMusicTime() {
		return musicTime;
	}
	public void setMusicTime(String musicTime) {
		this.musicTime = musicTime;
	}
	
	public Track(String startImage, String gameImage, String startMusic, String gameMusic, String titleName, String musicTime) {
		super(); // 아무것도 상속받지 않은 클래스의 super 클래스
		this.startImage = startImage;
		this.gameImage = gameImage;
		this.startMusic = startMusic;
		this.gameMusic = gameMusic;
		this.titleName = titleName;
		this.musicTime = musicTime;
	}
	
}
