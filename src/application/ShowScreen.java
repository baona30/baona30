package application;

//public class Screen1 implements Screen {
public class ShowScreen implements Screen {
	private String sc;
	private String title;
	public ShowScreen (String title, String sc){
		this.sc = sc;
		this.title = title;
	}
	public String getScreen() {
		return sc;
	}
	public String getTitle() {
		return title;
	}
	
}
