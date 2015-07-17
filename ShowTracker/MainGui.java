package ShowTracker;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class MainGui implements ActionListener, TreeSelectionListener {
	
	private JFrame main_window;
	private DefaultTreeModel main_tree_m;
	private DefaultMutableTreeNode main_root;
	private JTree main_tree;
	private JButton btnWatchEpisode;
	private JButton btnAddWeek;
	private JButton btnAddDay;
	private JButton btnAddMonth;
	private JButton btnAdd3Months;
	private JButton btnAddYear;
	private JButton btnRemDay;
	private JButton btnRemWeek;
	private JButton btnRemMonth;
	private JButton btnRem3Months;
	private JButton btnRemYear;
	//private DefaultTreeModel episodes_tree_m;
	//private DefaultMutableTreeNode episodes_root;
	//private JTree episodes_tree;
	private JTextPane text;
	private TVLibrary library;
	public final static Long DAY = new Long(86400000); 
	public final static Long WEEK = new Long(DAY*7);
	public final static Long MONTH = new Long(WEEK*4 + 2*DAY);
	private JButton btnRefresh;
	private JButton btnEditSource;
	
	//Private class to implement a close to the program
	private class ExitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		    System.exit(0);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("WATCH_EP")) {
			try {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) main_tree.getLastSelectedPathComponent();
				Object nodeObject = node.getUserObject();
				NodeInfo nodeI = (NodeInfo) nodeObject;
				String p = new String (nodeI.getShow().getShowPath() + nodeI.getSeason().getSeasonName() + "\\" + nodeI.getEpisode().getName());
				File f = new File(p);
				Desktop.getDesktop().open(f);
				if (f.lastModified() > nodeI.getShow().getLastWatched());
				nodeI.getShow().setLastWatched(f.lastModified());
			} catch (Exception ex) {
				System.out.println(ex);
			}	
		} else if (e.getActionCommand().equals("ADD_DAY")) {
			changeUpdate(DAY);
		} else if (e.getActionCommand().equals("ADD_WEEK")) {
			changeUpdate(WEEK);
		} else if (e.getActionCommand().equals("ADD_MONTH")) {
			changeUpdate(MONTH);
		} else if (e.getActionCommand().equals("ADD_3MONTHS")) {
			changeUpdate(MONTH*3);
		} else if (e.getActionCommand().equals("ADD_YEAR")) {
			changeUpdate(MONTH*12);
		} else if (e.getActionCommand().equals("REM_DAY")) {
			changeUpdate(-(DAY));
		} else if (e.getActionCommand().equals("REM_WEEK")) {
			changeUpdate(-(WEEK));
		} else if (e.getActionCommand().equals("REM_MONTH")) {
			changeUpdate(-(MONTH));
		} else if (e.getActionCommand().equals("REM_3MONTHS")) {
			changeUpdate(-(MONTH*3));
		} else if (e.getActionCommand().equals("REM_YEAR")) {
			changeUpdate(-(MONTH*12));
		} else if (e.getActionCommand().equals("REFRESH")) {
			library = new TVLibrary();
			makeMainTree();
		} else if (e.getActionCommand().equals("EDIT_SOURCE")) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) main_tree.getLastSelectedPathComponent();
			Object nodeObject = node.getUserObject();
			NodeInfo nodeI = (NodeInfo) nodeObject;
			String source = nodeI.getShow().getShowUpdatePath();
			String s = (String)JOptionPane.showInputDialog(main_window, "Enter show source below:", "Edit Source", JOptionPane.PLAIN_MESSAGE, null, null, source);
			if (s.length() > 0) {
				nodeI.getShow().setShowUpdatePath(s);
			}			
		}
		
	}
	
	private void changeUpdate(Long t) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) main_tree.getLastSelectedPathComponent();
		Object nodeObject = node.getUserObject();
		NodeInfo nodeI = (NodeInfo) nodeObject;
		Long new_update = new Long(t + nodeI.getShow().getNextUpdate());
		nodeI.getShow().setNextUpdate(new_update);
		
		valueChanged(null);
	}
	
	private void buttonControll(String type) {

		if (type == null) {
			btnEditSource.setEnabled(false);
			btnWatchEpisode.setEnabled(false);
			btnAddWeek.setEnabled(false);
			btnAddDay.setEnabled(false);
			btnAddMonth.setEnabled(false);
			btnAdd3Months.setEnabled(false);
			btnAddYear.setEnabled(false);
			btnRemDay.setEnabled(false);
			btnRemWeek.setEnabled(false);
			btnRemMonth.setEnabled(false);
			btnRem3Months.setEnabled(false);
			btnRemYear.setEnabled(false);
		} else if (type.equals("EPISODE")) {
			btnEditSource.setEnabled(true);
			btnWatchEpisode.setEnabled(true);
			btnAddWeek.setEnabled(true);
			btnAddDay.setEnabled(true);
			btnAddMonth.setEnabled(true);
			btnAdd3Months.setEnabled(true);
			btnAddYear.setEnabled(true);
			btnRemDay.setEnabled(true);
			btnRemWeek.setEnabled(true);
			btnRemMonth.setEnabled(true);
			btnRem3Months.setEnabled(true);
			btnRemYear.setEnabled(true);
		} else if (type.equals("SEASON")) {
			btnEditSource.setEnabled(true);
			btnWatchEpisode.setEnabled(false);
			btnAddWeek.setEnabled(true);
			btnAddDay.setEnabled(true);
			btnAddMonth.setEnabled(true);
			btnAdd3Months.setEnabled(true);
			btnAddYear.setEnabled(true);
			btnRemDay.setEnabled(true);
			btnRemWeek.setEnabled(true);
			btnRemMonth.setEnabled(true);
			btnRem3Months.setEnabled(true);
			btnRemYear.setEnabled(true);
		} else if (type.equals("SHOW")) {
			btnEditSource.setEnabled(true);
			btnWatchEpisode.setEnabled(false);
			btnAddWeek.setEnabled(true);
			btnAddDay.setEnabled(true);
			btnAddMonth.setEnabled(true);
			btnAdd3Months.setEnabled(true);
			btnAddYear.setEnabled(true);
			btnRemDay.setEnabled(true);
			btnRemWeek.setEnabled(true);
			btnRemMonth.setEnabled(true);
			btnRem3Months.setEnabled(true);
			btnRemYear.setEnabled(true);
		} else {
			btnEditSource.setEnabled(false);
			btnWatchEpisode.setEnabled(false);
			btnAddWeek.setEnabled(false);
			btnAddDay.setEnabled(false);
			btnAddMonth.setEnabled(false);
			btnAdd3Months.setEnabled(false);
			btnAddYear.setEnabled(false);
			btnRemDay.setEnabled(false);
			btnRemWeek.setEnabled(false);
			btnRemMonth.setEnabled(false);
			btnRem3Months.setEnabled(false);
			btnRemYear.setEnabled(false);			
		}
	}
	
	public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) main_tree.getLastSelectedPathComponent();
        buttonControll(null);
        text.setText("Make a selection on the left for additional information.");
        if (node == null) {
        	return;
        }
        
        Object nodeObject = node.getUserObject();
        
        //If the node object is a string, it is just a label, return and ignore it!
        if (nodeObject instanceof String) {
        	return;
        }
        
        //Cast the obect to a NodeInfo object!
        NodeInfo nodeI = (NodeInfo) nodeObject;
        
        //Get the type of node it is
        if (nodeI.getType() == null){
        	System.out.println("NODE ERROR - ERROR!!");
        	return;
        } else if (nodeI.getType().equals("Show")) {
        	text.setText(setShowText(nodeI.getShow()));
        	buttonControll("SHOW");
        } else if (nodeI.getType().equals("Season")) {
        	text.setText(setShowText(nodeI.getShow()) + setSeasonText(nodeI.getSeason()));
        	buttonControll("SEASON");
        } else if (nodeI.getType().equals("Episode")) {
        	text.setText(setShowText(nodeI.getShow()));
        	buttonControll("EPISODE");
        }
        
	}
	
	private String setSeasonText(Season season_obj) {
		String tot_epi = "-Episodes downloaded: " + season_obj.getNumEpisodes() + "\n";
		String un_w_epi = "-Unwatched episodes: " + season_obj.getUnwatched().getSize() + "\n";
		
		
		return (season_obj.getSeasonName() + ":\n" + tot_epi + un_w_epi + "\n");
	}
	
	private String setShowText(Show show_obj) {
		DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
		String show_name = "Show name: " + show_obj.getName() + "\n";
		Calendar show_n_update = Calendar.getInstance();
		show_n_update.setTimeInMillis(show_obj.getNextUpdate());
		String show_update = "";
		String show_u_path = show_obj.getShowUpdatePath();
		if (show_obj.getNextUpdate() < System.currentTimeMillis()) {
			Long diff = System.currentTimeMillis() - show_obj.getNextUpdate();
			diff = diff / DAY;
			show_update = "Needs updated! (" + diff + " days ago)\n";					
		} else {
			long diff = show_obj.getNextUpdate() - System.currentTimeMillis();
			diff = diff / DAY;
			show_update = "Next update: In " + diff + " days\n";
		}
		
		if (show_u_path == null) {
			show_u_path = "No update source added, edit source to add one.\n";
		} else {
			show_u_path += "\n";
		}
		
		
		return (show_name + show_update + show_u_path + "\n");
	}
	
	private void createAndShowGUI() {
		
		main_window = new JFrame();
		main_window.setTitle("Show Tracker");
		main_window.setBounds(150, 100, 628, 508); //GUI starts at pixel 100, 100 from the top left of the screen.
		main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		main_window.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu mFile = new JMenu("File  ");
		menuBar.add(mFile);
 
		JMenuItem mLoadConfig = new JMenuItem("Load Config");
		mLoadConfig.setActionCommand("load_config");
		mLoadConfig.addActionListener(this);
		mFile.add(mLoadConfig);
		
		JMenuItem mSaveConfig = new JMenuItem("Save Config");
		mSaveConfig.setActionCommand("save_config");
		mSaveConfig.addActionListener(this);
		mFile.add(mSaveConfig);
		
		mFile.addSeparator();
		
		JMenuItem mExit = new JMenuItem("Exit");
		mExit.addActionListener(new ExitListener());
		mFile.add(mExit);
		main_window.setJMenuBar(menuBar);
						
		
		main_root = new DefaultMutableTreeNode("Show lists:");
		main_tree_m = new DefaultTreeModel(main_root);
		main_tree = new JTree(main_tree_m);
		main_tree.setVisibleRowCount(25);
		main_tree.addTreeSelectionListener(this);
//		episodes_root = new DefaultMutableTreeNode("Episodes:");
	//	episodes_tree_m = new DefaultTreeModel(episodes_root);
		//episodes_tree = new JTree(episodes_tree_m);
		
		JScrollPane showsView = new JScrollPane(main_tree);
		text = new JTextPane();
		text.setText("Make a selection on the left for additional information.");
		JScrollPane episodesView = new JScrollPane(text);
		showsView.setBounds(9, 10, 286, 276);
		episodesView.setBounds(317, 10, 285, 276);
		
		main_window.getContentPane().add(showsView);
		main_window.getContentPane().add(episodesView);
		
		btnWatchEpisode = new JButton("Watch Episode");
		btnWatchEpisode.setEnabled(false);
		btnWatchEpisode.setActionCommand("WATCH_EP");
		btnWatchEpisode.addActionListener(this);
		btnWatchEpisode.setBounds(10, 317, 125, 23);
		main_window.getContentPane().add(btnWatchEpisode);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(305, 0, 2, 449);
		main_window.getContentPane().add(separator);
		
		btnAddDay = new JButton("1 Day");
		btnAddDay.setBounds(327, 317, 89, 23);
		btnAddDay.setActionCommand("ADD_DAY");
		btnAddDay.setEnabled(false);
		btnAddDay.addActionListener(this);
		main_window.getContentPane().add(btnAddDay);
		
		JLabel lblAlterNextUpdate = new JLabel("Alter next update date:");
		lblAlterNextUpdate.setBounds(317, 290, 214, 14);
		main_window.getContentPane().add(lblAlterNextUpdate);
		
		btnAddWeek = new JButton("1 Week");
		btnAddWeek.setBounds(326, 342, 89, 23);
		btnAddWeek.setEnabled(false);
		btnAddWeek.setActionCommand("ADD_WEEK");
		btnAddWeek.addActionListener(this);
		main_window.getContentPane().add(btnAddWeek);
		
		btnAddMonth = new JButton("1 Month");
		btnAddMonth.setBounds(326, 369, 89, 23);
		btnAddMonth.setActionCommand("ADD_MONTH");
		btnAddMonth.setEnabled(false);
		btnAddMonth.addActionListener(this);
		main_window.getContentPane().add(btnAddMonth);
		
		btnAdd3Months = new JButton("3 Months");
		btnAdd3Months.setBounds(326, 396, 89, 23);
		btnAdd3Months.setActionCommand("ADD_3MONTHS");
		btnAdd3Months.setEnabled(false);
		btnAdd3Months.addActionListener(this);
		main_window.getContentPane().add(btnAdd3Months);
		
		btnAddYear = new JButton("1 Year");
		btnAddYear.setBounds(326, 423, 89, 23);
		btnAddYear.setActionCommand("ADD_YEAR");
		btnAddYear.setEnabled(false);
		btnAddYear.addActionListener(this);
		main_window.getContentPane().add(btnAddYear);
		
		JLabel lblAdd = new JLabel("Add:");
		lblAdd.setBounds(327, 297, 46, 14);
		main_window.getContentPane().add(lblAdd);
		
		btnRemDay = new JButton("1 Day");
		btnRemDay.setBounds(513, 317, 89, 23);
		btnRemDay.setActionCommand("REM_DAY");
		btnRemDay.setEnabled(false);
		btnRemDay.addActionListener(this);
		main_window.getContentPane().add(btnRemDay);
		
		btnRemWeek = new JButton("1 Week");
		btnRemWeek.setBounds(513, 342, 89, 23);
		btnRemWeek.setActionCommand("REM_WEEK");
		btnRemWeek.setEnabled(false);
		btnRemWeek.addActionListener(this);
		main_window.getContentPane().add(btnRemWeek);
		
		btnRemMonth = new JButton("1 Month");
		btnRemMonth.setBounds(513, 369, 89, 23);
		btnRemMonth.setActionCommand("REM_MONTH");
		btnRemMonth.setEnabled(false);
		btnRemMonth.addActionListener(this);
		main_window.getContentPane().add(btnRemMonth);
		
		btnRem3Months = new JButton("3 Months");
		btnRem3Months.setBounds(513, 396, 89, 23);
		btnRem3Months.setActionCommand("REM_3MONTHS");
		btnRem3Months.setEnabled(false);
		btnRem3Months.addActionListener(this);
		main_window.getContentPane().add(btnRem3Months);
		
		btnRemYear = new JButton("1 Year");
		btnRemYear.setBounds(513, 423, 89, 23);
		btnRemYear.setActionCommand("REM_YEAR");
		btnRemYear.setEnabled(false);
		btnRemYear.addActionListener(this);
		main_window.getContentPane().add(btnRemYear);
		
		JLabel lblRemove = new JLabel("Remove:");
		lblRemove.setBounds(513, 297, 74, 14);
		main_window.getContentPane().add(lblRemove);
		
		btnRefresh = new JButton("Refresh Lists");
		btnRefresh.setBounds(10, 290, 125, 23);
		btnRefresh.setActionCommand("REFRESH");
		btnRefresh.addActionListener(this);
		main_window.getContentPane().add(btnRefresh);
		
		btnEditSource = new JButton("Edit source");
		btnEditSource.setEnabled(false);
		btnEditSource.setBounds(10, 344, 125, 23);
		btnEditSource.setActionCommand("EDIT_SOURCE");
		btnEditSource.addActionListener(this);
		main_window.getContentPane().add(btnEditSource);
		
		main_window.setVisible(true);
    }
		
	private void makeMainTree() {
		main_root.removeAllChildren();
		main_tree_m.reload();
		//First Add the 3 main queues to the main tree
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Unwatched Queue(0):");
		main_tree_m.insertNodeInto(newNode, main_root, main_root.getChildCount());
		newNode = new DefaultMutableTreeNode("Shows need updated(0):");
		main_tree_m.insertNodeInto(newNode, main_root, main_root.getChildCount());
		newNode = new DefaultMutableTreeNode("All shows(0):");
		main_tree_m.insertNodeInto(newNode, main_root, main_root.getChildCount());
		main_tree.scrollPathToVisible(new TreePath(newNode.getPath()));
		main_tree.scrollPathToVisible(new TreePath(main_root.getPath()));
		
		//Add shows to the Unwtached queue
		DefaultMutableTreeNode unwatched_root = (DefaultMutableTreeNode) main_root.getChildAt(0);
		int count = 0;
		for (int i = 0; i < library.getNumShows(); i++) {
			Show show = library.getShow(i);
			if (show.totalUnwatched() > 0) {
				count += show.totalUnwatched();
				DefaultMutableTreeNode newShow = new DefaultMutableTreeNode(new NodeInfo(library.getShow(i), 
						library.getShow(i).getName()+"(" + show.totalUnwatched() + ")"));
				main_tree_m.insertNodeInto(newShow, unwatched_root, unwatched_root.getChildCount());
				for (int k = 0; k < show.getNumSeasons(); k++) {
					Season season = show.getSeason(k);
					if (season.getUnwatched().getSize() > 0) {
						DefaultMutableTreeNode newSeason = new DefaultMutableTreeNode(new NodeInfo(show, season, 
								season.getSeasonName()+"("+season.getUnwatched().getSize()+")"));
						main_tree_m.insertNodeInto(newSeason, newShow, newShow.getChildCount());
						UnwatchedList unwatched = season.getUnwatched();
						for (int z = 0; z < unwatched.getSize(); z++) {
							DefaultMutableTreeNode newEpisode = new DefaultMutableTreeNode(new NodeInfo(show, season, 
									unwatched.getEpisode(z), unwatched.getEpisode(z).getName()));
							main_tree_m.insertNodeInto(newEpisode, newSeason, newSeason.getChildCount());
						}
					}
				}
			}
		}
		unwatched_root.setUserObject("Unwatched(" + count + "):");
		
		//Add shows to the Needs Updated tree
		DefaultMutableTreeNode needs_updated_node = (DefaultMutableTreeNode) main_root.getChildAt(1);
		count = 0;
		Long curTime = System.currentTimeMillis();
		
		for (int i = 0; i < library.getNumShows(); i++) {
			if (library.getShow(i).getNextUpdate() < curTime) {
				DefaultMutableTreeNode newShow = new DefaultMutableTreeNode(new NodeInfo(library.getShow(i), library.getShow(i).getName()));
				main_tree_m.insertNodeInto(newShow, needs_updated_node, needs_updated_node.getChildCount());
				count++;
			}
		}
		needs_updated_node.setUserObject("Need updated(" + count + "):");
		
		//Add shows to the All shows tree, include leafs under the shows for each season
		DefaultMutableTreeNode all_shows_node = (DefaultMutableTreeNode) main_root.getChildAt(2);
		all_shows_node.setUserObject("All shows(" + library.getNumShows() + "):");
		for (int i = 0; i < library.getNumShows(); i++) {
			DefaultMutableTreeNode newShow = new DefaultMutableTreeNode(new NodeInfo(library.getShow(i), library.getShow(i).getName()));
			main_tree_m.insertNodeInto(newShow, all_shows_node, all_shows_node.getChildCount());
			for (int k = 0; k < library.getShow(i).getNumSeasons(); k++) {
				DefaultMutableTreeNode newSeason = new DefaultMutableTreeNode(new NodeInfo(library.getShow(i),
						library.getShow(i).getSeason(k), library.getShow(i).getSeason(k).getSeasonName()));
				main_tree_m.insertNodeInto(newSeason, newShow, newShow.getChildCount());
				for (int z = 0; z < library.getShow(i).getSeason(k).getNumEpisodes(); z++) {
					DefaultMutableTreeNode newEpisode = new DefaultMutableTreeNode(new NodeInfo(library.getShow(i),
							library.getShow(i).getSeason(k), library.getShow(i).getSeason(k).getEpisode(z), library.getShow(i).getSeason(k).getEpisodeName(z)));
					main_tree_m.insertNodeInto(newEpisode, newSeason, newSeason.getChildCount());
				}
			}
		}
		main_tree.scrollPathToVisible(new TreePath(main_root.getPath()));
	}
	
	public MainGui() {
		createAndShowGUI();
		library = new TVLibrary();
		
		makeMainTree();
		
		for (int i = 0; i < library.getNumShows(); i++) {
			//System.out.println(library.getShowName(i));
		}
	}
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MainGui window = new MainGui();
            }
        });
    }
}
