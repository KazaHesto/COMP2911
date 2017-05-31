import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WarehouseBoss implements ActionListener, Observer {
	// declaring variables
	
	// a JFrame instance
	private JFrame frame;
	// a Game instance
	private Game game;
	// a LevelMap instance
	private LevelMap mapUI;
	// a LevelMapController instance
	private LevelMapController mapController;
	// a JMenuBar instance
	private JMenuBar menuBar;
	// a Menu instance
	private Menu menu;
	// a row coordinate
	private int row;
	// a column coordinate
	private int column;

	/**
	 * method: createWindow() -> creates the JFrame window and shows the main menu
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public void createWindow() {
		// create a new JFrame
		this.frame = new JFrame();
		// this frame should not be resizable
		frame.setResizable(false);
		// set the frame's title
		frame.setTitle(Resources.GAME_TITLE);
		// set the frame's operation
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// create a menu bar
		menuBar = createMenuBar();
		// set the menu bar to the desired effect
		frame.setJMenuBar(menuBar);
		// set the coordinates of the window
		this.row = 11;
		this.column = 14;
		// show the menu
		Resources resources = new Resources();
		frame.setIconImage(resources.getImage(Resources.PLAYER_PUSH_RIGHT));
		showMenu();
	}

	/**
	 * method: showMenu() -> shows the main menu
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	private void showMenu() {
		// Clears all the items currently on the window (if there are any)
		for (Component component : frame.getContentPane().getComponents()) {
			frame.getContentPane().remove(component);
		}

		// create a new menu
		this.menu = new Menu();
		// assign a controller to it
		MenuController controller = new MenuController(menu, this);
		menu.setController(controller);
		// add the menu to the window
		frame.getContentPane().add(menu);

		// hide stuff we don't want seen
		hideGameOptions();
		// set visual settings
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * method: initGame() -> initialises the game
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public void initGame() {
		// create a new game
		this.game = new Game(this.row, this.column);
		// show the game
		showGame(this.row, this.column);
		// show the options
		showGameOptions();
	}
	
	/**
	 * method: initTutorial() -> initalises the tutorial
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public void initTutorial() {
		// create a new game
		this.game = new Game(-1, -1);
		this.game.startTut();
		// create the tutorial
		Tutorial tutorial = new Tutorial(this.game);
		// add an observer to the game
		game.addObserver(tutorial);
		// add an observer to the tutorial
		tutorial.addObserver(this);
		// show the game
		showGame(6, 11);
		// show the options
		showTutorialOptions();
		// prompt the player
		tutorial.ititialPrompt();
	}
	
	/**
	 * method: showGame() -> makes a new levelmap and a level map to be 
	 * 							rendered and sets a levelmap controller
	 * 
	 * @param 	row			size of the game matrix
	 * @param 	column		size of the game matrix
	 * @return	-
	 * @throws	-
	 */
	private void showGame(int row, int column) {
		// Clears all the items currently on the window (if there are any)
		for (Component component : frame.getContentPane().getComponents()) {
			frame.getContentPane().remove(component);
		}

		// create a new map
		this.mapUI = new LevelMap(row, column);

		// add a controller to the map
		this.mapController = new LevelMapController(game, mapUI, this);
		mapUI.setController(mapController);
		// add an observer to the controller
		game.addObserver(mapController);
		frame.getContentPane().add(mapUI);
		// adjust visuals
		mapUI.requestFocusInWindow();
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * method: createMenuBar() -> creates the menu bar at the top of the window
	 * 
	 * @param	-
	 * @return	menuBar 	the menu bar
	 * @throws	-
	 */
	private JMenuBar createMenuBar() {
		// create a new JMenuBar
		JMenuBar menuBar = new JMenuBar();

		// add the stuff you need to the menu bar
		JMenu menu = new JMenu("Game");
		menu.setMnemonic(KeyEvent.VK_G);
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem("New Game", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("F2"));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Load Game", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Save Game", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("Undo", KeyEvent.VK_U);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Options", KeyEvent.VK_O);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("Exit to Titlescreen", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("F7"));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		menu = new JMenu("Help");
		menuItem = new JMenuItem("View Help", KeyEvent.VK_V);
		menuItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("About WarehouseBoss", KeyEvent.VK_A);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);

		// return the menu bar
		return menuBar;
	}

	/**
	 * method: showGameOptions() -> enables game related menubar items, such as undo
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	private void showGameOptions() {
		// show the options
		this.menuBar.getMenu(0).getItem(1).setEnabled(true);
		this.menuBar.getMenu(0).getItem(2).setEnabled(true);
		this.menuBar.getMenu(0).getItem(4).setEnabled(true);
		this.menuBar.getMenu(0).getItem(5).setEnabled(true);
		this.menuBar.getMenu(0).getItem(7).setEnabled(true);
	}

	/**
	 * method: showTutorialOptions() -> disable tutorial options in menu bar
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	private void showTutorialOptions() {
		// show the options
		this.menuBar.getMenu(0).getItem(1).setEnabled(false);
		this.menuBar.getMenu(0).getItem(2).setEnabled(false);
		this.menuBar.getMenu(0).getItem(4).setEnabled(false);
		this.menuBar.getMenu(0).getItem(5).setEnabled(false);
		this.menuBar.getMenu(0).getItem(7).setEnabled(true);
	}

	/**
	 * method: hideGameOptions() -> undo gameMenuBar()
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	private void hideGameOptions() {
		// hide the options
		this.menuBar.getMenu(0).getItem(1).setEnabled(true);
		this.menuBar.getMenu(0).getItem(2).setEnabled(false);
		this.menuBar.getMenu(0).getItem(4).setEnabled(false);
		this.menuBar.getMenu(0).getItem(5).setEnabled(true);
		this.menuBar.getMenu(0).getItem(7).setEnabled(false);
	}
	
	/**
	 * method: saveGame() -> pauses the timer when the save window opens
	 * 						 and saves game state as a saveData object
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	private void saveGame() {
		// pause the timer
		this.game.pauseTimer();
		// processes to save the file
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Save Files", "save");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showSaveDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			System.out.println("Failed to save");
			return;
		}

		// retrieve data to save
		SaveData data = this.game.getState();
		// save the data
		try {
			ResourceManager.save(data, chooser.getSelectedFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method: loadGame() -> opens load game window and sets gamestate according to saved data
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	private void loadGame() {
		// if there is a game in session, pause it
		if (this.game != null) {
			this.game.pauseTimer();
		}
		
		// processes to load the file
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Save Files", "save");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			System.out.println("Failed to load");
			return;
		}
		try {
			// load the saved game's data
			SaveData data = (SaveData) ResourceManager.load(chooser.getSelectedFile());
			this.game = new Game(data);
			showGame(this.row, this.column);
			showGameOptions();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method: showOptionsPane() -> option to change the game window
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	private void showOptionsPane() {
		// a list of options
		String[] options = { "11x14", "8x11" };
		// show the options
		String s = (String) JOptionPane.showInputDialog(null, "Choose the size of level:", "Options",
				JOptionPane.PLAIN_MESSAGE, null, options, this.row + "x" + this.column);
		// depending on user input, act accordingly
		if (s == options[0]) {
			this.row = 11;
			this.column = 14;
		} else if (s == options[1]) {
			this.row = 8;
			this.column = 11;
		}
		// initialise a game
		initGame();
	}

	/**
	 * override: actionPerformed() -> does something based on user input
	 * 
	 * @param	e	an ActionEvent instance
	 * @return	-
	 * @throws	-
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// do something based on user input
		if (e.getSource().equals(menuBar.getMenu(0).getItem(0))) {
			initGame();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(1))) {
			loadGame();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(2))) {
			saveGame();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(4))) {
			this.game.undoMove();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(5))) {
			showOptionsPane();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(7))) {
			showMenu();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(8))) {
			System.exit(1);
		}
		if (e.getSource().equals(menuBar.getMenu(1).getItem(0))) {
			JOptionPane.showMessageDialog(null, Resources.HELP_TEXT, Resources.HELP_TITLE,
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource().equals(menuBar.getMenu(1).getItem(1))) {
			JOptionPane.showMessageDialog(null, Resources.ABOUT_TEXT, Resources.ABOUT_TITLE,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * override: update() -> resets or initialises a game based on user input
	 * 
	 * @param	arg		an Object instance
	 * @param	o		an Observable instance
	 * @return	-
	 * @throws	-
	 */
	@Override
	public void update(Observable o, Object arg) {
		// resets or initialises a game based on user input
		if ((int) arg == 1) {
			this.game.resetGame();
			this.mapController.initLevelMap();
		} else if ((int) arg == 2) {
			initGame();
		}
	}
	
	/**
	 * method: main() -> the main function
	 * 
	 * @param 	args	 arguments taken in
	 * @return	-
	 * @throws	-
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		// create a new WarehouseBoss instance
		WarehouseBoss ex = new WarehouseBoss();
		// run the game
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ex.createWindow();
			}
		});
	}
}
