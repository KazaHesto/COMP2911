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

public class WarehouseBoss implements ActionListener {

	private JFrame frame;
	private Game2 game;
	private LevelMap mapUI;
	private LevelMapController mapController;
	private JMenuBar menuBar;
	private Menu menu;
	private Player player;

	public void createWindow() {
		this.frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle(Constants.GAME_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = createMenuBar();
		frame.setJMenuBar(menuBar);

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

		titleMenuBar();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void initGame() {
		// Clears all the items currently on the window (if there are any)
		for (Component component : frame.getContentPane().getComponents()) {
			frame.getContentPane().remove(component);
		}

		this.game = new Game2();
		this.mapUI = new LevelMap(6, 11);
		this.player = new Player(1,1);
		player.setPosition(player.getRow(), player.getColumn());

		this.mapController = new LevelMapController(game, mapUI);
		mapUI.setController(mapController);
		game.addObserver(mapController);
		frame.getContentPane().add(mapUI);
		mapUI.requestFocusInWindow();

		gameMenuBar();
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
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
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
	private void gameMenuBar() {
		this.menuBar.getMenu(0).getItem(1).setEnabled(true);
		this.menuBar.getMenu(0).getItem(2).setEnabled(true);
		this.menuBar.getMenu(0).getItem(4).setEnabled(true);
	}

	// undo gameMenuBar()
	private void titleMenuBar() {
		this.menuBar.getMenu(0).getItem(1).setEnabled(false);
		this.menuBar.getMenu(0).getItem(2).setEnabled(false);
		this.menuBar.getMenu(0).getItem(4).setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menuBar.getMenu(0).getItem(0))) {
			initGame();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(1))) {
			try {
				SaveData data = (SaveData) ResourceManager.load("1.save");
				this.game.setGame(data.matrix,data.resetState, data.row, data.column, data.direction,data.undoPlayer,
						          data.gameTimer, data.seconds, data.boxes, data.undoBoxes, data.resetBoxes);
				this.mapController.initLevelMap();
				this.mapUI.repaint();
			} catch (Exception ex) {
				System.out.println("Couldn't load save data: " + ex.getMessage());
			} 
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(2))) {
			SaveData data = new SaveData();
			data.matrix = this.game.getMatrix();
			data.resetState = this.game.getResetState();
			data.row = this.game.getPlayerRow();
			data.column = this.game.getPlayerColumn();
			data.direction = this.game.getPlayerDirection();
			data.undoPlayer = this.game.getUndoPlayer();
			data.gameTimer = this.game.getGameTimer();
			data.seconds = this.game.getSeconds();
			data.boxes = this.game.getBoxes();
			data.undoBoxes = this.game.getUndoBoxes();
			data.resetBoxes = this.game.getResetBoxes();
			
			try {
				ResourceManager.save(data, "1.save");
			} catch (Exception ex) {
				System.out.println("Couldn't save: " + ex.getMessage());
			} 
		}
		
		
		if (e.getSource().equals(menuBar.getMenu(0).getItem(4))) {
			this.game.undoMove();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(5))) {
			// put options stuff here
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
