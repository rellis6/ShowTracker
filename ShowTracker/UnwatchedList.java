package ShowTracker;

import java.util.ArrayList;

public class UnwatchedList {
	private int size;
	private ArrayList<Episode> episodes;

	private ArrayList<Long> modified;
	
	public UnwatchedList() {
		size = 0;
		episodes = new ArrayList<Episode>();
		modified = new ArrayList<Long>();
	}
	
	public void addEp(Episode e, Long m) {
		for (int i = 0; i < size; i++) {
			if (m < modified.get(i)) {
				episodes.add(i, e);
				modified.add(i, m);
				size += 1;
				return;
			}
		}
		
		episodes.add(e);
		modified.add(m);
		size += 1;
		return;
	}
	
	public Episode getEpisode(int i) {
		return episodes.get(i);
	}
	
	public int getSize() {
		return size;
	}
}
