package ShowTracker;

public class Episode {
	private String name;
	private Long modified;
	
	
	public Episode() {
		name = new String();
		modified = new Long(0);
	}
	
	public Episode(String n, Long m) {
		name = new String(n);
		modified = new Long(m);
	}
	
	public String getName() {
		return name;
	}
	
	public Long getModified() {
		return modified;
	}
}
