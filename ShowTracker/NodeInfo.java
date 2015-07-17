package ShowTracker;

public class NodeInfo {
	private Show show_info;
	private Season season_info;
	private Episode episode_info;
	private String name;
	private String type;
	
	public NodeInfo() {
		show_info = null;
		season_info = null;
		episode_info = null;
		name = null;
		type = null;
	}
	
	public NodeInfo(Show s, String n) {
		show_info = s;
		name = new String(n);
		season_info = null;
		episode_info = null;
		type = new String("Show");
	}
	
	public NodeInfo(Show s, Season se, String n) {
		show_info = s;
		season_info = se;
		name = new String(n);
		episode_info = null;
		type = new String("Season");
	}
	
	public NodeInfo(Show s, Season se, Episode e, String n) {
		show_info = s;
		name = new String(n);
		season_info = se;
		episode_info = e;
		type = new String("Episode");
	}

	public Show getShow() {
		return show_info;
	}
	
	public Season getSeason() {
		return season_info;
	}
	
	public Episode getEpisode() {
		return episode_info;
	}
	
	public String toString() {
		return name;
	}
	
	public String getType() {
		return type;
	}
}
