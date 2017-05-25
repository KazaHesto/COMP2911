import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WarehouseBoss implements ActionListener {

	private JFrame frame;
	private Game2 game;
	private LevelMap mapUI;
	private LevelMapController mapController;
	private JMenuBar menuBar;
	private Menu menu;
	private int row;
	private int column;

	public void createWindow() {
		this.frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(Constants.GAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = createMenuBar();
		frame.setJMenuBar(menuBar);
		this.row = 11;
		this.column = 14;

		showMenu();
	}

	private void showMenu() {
		// Clears all the items currently on the window (if there are any)
		for (Component component : frame.getContentPane().getComponents()) {
			frame.getContentPane().remove(component);
		}

		this.menu = new Menu();
		MenuController controller = new MenuController(menu, this);
		menu.setController(controller);
		frame.getContentPane().add(menu);

		hideGameOptions();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void initGame() {
		this.game = new Game2(this.row, this.column);
		showGame();
	}

	private void showGame() {
		// Clears all the items currently on the window (if there are any)
		for (Component component : frame.getContentPane().getComponents()) {
			frame.getContentPane().remove(component);
		}

		this.mapUI = new LevelMap(this.row, this.column);

		this.mapController = new LevelMapController(game, mapUI);
		mapUI.setController(mapController);
		game.addObserver(mapController);
		frame.getContentPane().add(mapUI);
		mapUI.requestFocusInWindow();

		showGameOptions();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

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

		return menuBar;
	}

	// enables game related menubar items, such as undo
	private void showGameOptions() {
		this.menuBar.getMenu(0).getItem(2).setEnabled(true);
		this.menuBar.getMenu(0).getItem(4).setEnabled(true);
	}

	// undo gameMenuBar()
	private void hideGameOptions() {
		this.menuBar.getMenu(0).getItem(2).setEnabled(false);
		this.menuBar.getMenu(0).getItem(4).setEnabled(false);
	}

	private void saveGame() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Save Files", "save");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showSaveDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			System.out.println("Failed to save");
			return;
		}

		SaveData data = this.game.getState();

		try {
			ResourceManager.save(data, chooser.getSelectedFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadGame() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Save Files", "save");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			System.out.println("Failed to load");
			return;
		}
		try {
			SaveData data = (SaveData) ResourceManager.load(chooser.getSelectedFile());
			this.game = new Game2(data.matrix, data.resetState, data.player, data.undoPlayer,
					data.seconds, data.numMoves, data.boxes, data.undoBoxes, data.resetBoxes, data.berries, data.berryState, data.berryCount);
			showGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showOptionsPane() {
		String[] options = { "11x14", "8x11" };
		String s = (String) JOptionPane.showInputDialog(null, "Choose the size of level:", "Options",
				JOptionPane.PLAIN_MESSAGE, null, options, this.row + "x" + this.column);
		if (s == options[0]) {
			this.row = 11;
			this.column = 14;
		} else if (s == options[1]) {
			this.row = 8;
			this.column = 11;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
			System.exit(1);
		}
		if (e.getSource().equals(menuBar.getMenu(1).getItem(0))) {
			JOptionPane.showMessageDialog(null, Constants.HELP_TEXT, Constants.HELP_TITLE,
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource().equals(menuBar.getMenu(1).getItem(1))) {
			JOptionPane.showMessageDialog(null, Constants.ABOUT_TEXT, Constants.ABOUT_TITLE,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		WarehouseBoss ex = new WarehouseBoss();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ex.createWindow();
			}
		});
	}
}
