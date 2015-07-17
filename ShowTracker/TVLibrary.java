/* TVLibrary will hold a list of Shows which in turn hold information about the shows. Will include
 * methods to load the TVLibrary, and save information about the library. Will also keep track of two queues,
 * one will hold a list of shows with unwatched episodes, and how many. The other will list shows that haven't
 * been updated recently. 
 */

package ShowTracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TVLibrary {
	private String config = null;
	private String lib_path = null;
	private ArrayList<Show> shows;
	
	public TVLibrary() {
		config = new String("default.cfg");
		shows = new ArrayList<Show>();
		init();
	}
	
	public TVLibrary(String c) {
		config = c;
		init();
	}
	
	private void init() {
		loadConfig();
		
		//If the config was loaded and no lib_path was set we have a problem, exit.
		if (lib_path == null) {
			System.out.println("Config did not load the library path, make sure the config has a TV_LIB_PATH setting.");
			System.exit(-1);
		}
		
		loadShows();
		
	}
	
	private void loadShows() {
		File folder = new File(lib_path);
		File[] listOfFiles = folder.listFiles();
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {
		    	shows.add(new Show(listOfFiles[i].getName(), lib_path));
		    }
		}
	}
	
	private void loadConfig() {
		BufferedReader reader = null;
		Pattern patternFile = Pattern.compile("(\\w+)\\s+(.+)");
		String p_name = null;
		String c_name = null;
				
		try {
			reader = new BufferedReader(new FileReader(config));
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
					

						if (p_name.equals("TV_LIB_PATH")) {
							setLibPath(c_name);
						} 						
						
					}

				}
			}
			
		}
		catch (IOException e) {
			System.out.println("File does not exist: " + config);
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		} 
	}
	
	public Show getShow(int i) {
		return shows.get(i);
	}
	
	public String getLibPath() {
		return lib_path;
	}
	
	private void setLibPath(String p) {
		lib_path = p;
	}
	
	public Integer getNumShows() {
		return shows.size();
	}
	
	public String getShowName(Integer n) {
		return shows.get(n).getName();
	}
}
