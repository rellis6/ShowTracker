package ShowTracker;

import java.util.ArrayList;

public class Season {
	private String name = null;
	private ArrayList<Episode> episodes = null;
	private UnwatchedList unwatched = null;
	
	public Season() {
		
	}
	
	public Season(String n) {
		name = n;
		episodes = new ArrayList<Episode>();
		unwatched = new UnwatchedList();
	}
	
	public UnwatchedList getUnwatched() {
		return unwatched;
	}
	
	public void resetUnwatched() {
		unwatched = new UnwatchedList();
	}
	
	public void addEpisode(String n, Long d) {
		episodes.add(new Episode(n, d));
	}
	
	public String getSeasonName() {
		return name;
	}
	
	public int getNumEpisodes(){
		return episodes.size();
	}
	
	public Episode getEpisode(int i) {
		return episodes.get(i);
	}
	
	public Long getEpisodeMod(int i) {
		return episodes.get(i).getModified();
	}
	
	public String getEpisodeName(int i) {
		return episodes.get(i).getName();
	}
}
