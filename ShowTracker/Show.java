package ShowTracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Show {
	private String name = null;
	private String show_cfg = null;
	private String show_path = null;
	private ArrayList<Season> seasons = null;
	private Long last_watched = null;
	private Long last_updated = null;
	private Long next_update = null;
	private String update_path = null;

	public Show() {
		seasons = new ArrayList<Season>();
	}
	
	public Show(String n, String p) {
		seasons = new ArrayList<Season>();
		name = n;
		show_cfg = p + name + "\\" + n + "st.cfg";
		show_path = p + name + "\\";
		last_watched = new Long(0);
		last_updated = new Long(0);
		next_update = new Long(0);		
		initShow();
		buildUnwatched();
		if (next_update == 0) {
			next_update = last_updated + MainGui.WEEK + MainGui.DAY;
		}

		updateCfg();
	}
	
	private void buildUnwatched() {
		for (int i = 0; i < seasons.size(); i++) {
			Season s = seasons.get(i);
			s.resetUnwatched();
			for (int k = 0; k < s.getNumEpisodes(); k++) {
				if (last_watched < s.getEpisodeMod(k)) {
					UnwatchedList unwatched = s.getUnwatched();
					unwatched.addEp(s.getEpisode(k), s.getEpisodeMod(k));
				}
			}
		}
	}
	
	public Long getLastWatched() {
		return last_watched;
	}
	
	public int totalUnwatched() {
		int total = 0;
		for (int i = 0; i < seasons.size(); i++) {
			total += seasons.get(i).getUnwatched().getSize();
		}
		return total;
	}
	
	public String toString() {
		return name;
	}
	
	public String getName() {
		return name;
	}
	
	public Long getNextUpdate() {
		return next_update;
	}
	
	public void setNextUpdate(Long u) {
		next_update = new Long(u);
		updateCfg();
	}
	
	public Season getSeason(int i) {
		return seasons.get(i);
	}
	
	public Integer getNumSeasons() {
		return seasons.size();
	}
	
	public String getShowPath() {
		return show_path;
	}
	
	public void setLastWatched(Long d) {
		last_watched = new Long(d);
		buildUnwatched();
		updateCfg();
	}
	
	public String getShowUpdatePath() {
		return update_path;
	}
	
	public void setShowUpdatePath(String p) {
		update_path = p;
		updateCfg();
	}
	
	public void updateCfg(){
		File cfg = new File(show_cfg);
		BufferedWriter writer = null;
		String newLine = System.getProperty("line.separator");
		try {
			writer = new BufferedWriter(new FileWriter(cfg));
			
			if (last_watched != null) {
				writer.write("LAST_WATCHED " + last_watched + newLine);
				
			}
			
			if (next_update != null) {
				writer.write("NEXT_UPDATE " + next_update + newLine);
			}
			
			if (update_path != null) {
				writer.write("UPDATE_PATH " + update_path + newLine);
			}

			
			writer.close();
		} catch (Exception e) {
			
		}
	}
	
	private void initShow() {
		File show_folder = new File(show_path);
		File[] list_of_files_s = show_folder.listFiles();
		File season_folder;
		File[] list_of_files_s2;
		BufferedReader reader = null;
		Pattern patternFile = Pattern.compile("(\\w+)\\s+(.+)");
		String p_name = null;
		String c_name = null;
		for (int i = 0; i < list_of_files_s.length; i++) {
			if (list_of_files_s[i].isDirectory()) {
				season_folder = new File(show_path + list_of_files_s[i].getName());
				list_of_files_s2 = season_folder.listFiles();
		    	seasons.add(new Season(list_of_files_s[i].getName()));
		    	for (int k = 0; k < list_of_files_s2.length; k++) {
		    		seasons.get(seasons.size()-1).addEpisode(list_of_files_s2[k].getName(), list_of_files_s2[k].lastModified());
		    		if (list_of_files_s2[k].lastModified() > last_updated) {
		    			last_updated = list_of_files_s2[k].lastModified();
		    			System.out.println(name);
		    			if (last_updated > (next_update-(7*MainGui.DAY))) {
		    				next_update = last_updated + MainGui.WEEK + MainGui.DAY;
		    			}
		    		}
		    	}
		    } else if (list_of_files_s[i].isFile() && (list_of_files_s[i].toString().equals(Paths.get(show_cfg).toString()))) {
		    	try {
					reader = new BufferedReader(new FileReader(show_cfg));
					String line = null;
					while((line = reader.readLine()) != null) {
						if ((line.length() == 0) || (line.charAt(0) == '#') || (line.charAt(0) == ' ')) {
							//Above if statement checks for a line that is empty, starts with a space, or with a #
							//Skips the line is any are true.
						} 
						else if ((line.charAt(0) == ' ')) {
				    		String error = "A line was skipped in the config file because it started with a space.\nLine: " +
				    				line;
				    		System.out.println(error);
						}
						else {
							Matcher m = patternFile.matcher(line);
							if (m.matches()) {
								p_name = (m.group(1));
								c_name = (m.group(2));
							

								if (p_name.equals("LAST_WATCHED")) {
									last_watched = new Long(Long.parseLong(c_name));
								} else if (p_name.equals("NEXT_UPDATE")) {
									Long temp = new Long(Long.parseLong(c_name));
									if (temp > next_update) {
										next_update = temp;
									}
								} else if (p_name.equals("UPDATE_PATH")) {
									update_path = new String(c_name);
								}
								
								
							}

						}
					}
					
					reader.close();
				}
				catch (IOException e) {
					System.out.println("File does not exist: " + show_cfg);
				}
				catch (Exception e) {
					System.out.println(e.getStackTrace());
				} 
		    }
		}
	}
}
