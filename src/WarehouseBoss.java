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
	private JMenuBar menuBar;
	private Menu menu;

	public void createWindow() {
		this.frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Warehouse Boss");
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

		LevelMapController controller = new LevelMapController(game, mapUI);
		mapUI.setController(controller);
		game.addObserver(controller);
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
		this.menuBar.getMenu(0).getItem(2).setEnabled(true);
	}

	// undo gameMenuBar()
	private void titleMenuBar() {
		this.menuBar.getMenu(0).getItem(2).setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menuBar.getMenu(0).getItem(0))) {
			initGame();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(2))) {
			this.game.undoMove();
		}
		if (e.getSource().equals(menuBar.getMenu(0).getItem(5))) {
			System.exit(1);
		}
		if (e.getSource().equals(menuBar.getMenu(1).getItem(0))) {
			JOptionPane.showMessageDialog(null,
					"W - Forward\nA - Left\n" + "S - Backwards\nD - Right\nR - Reset Game\nU - Undo Previous Move\n"
							+ "Aim : To successfully move all the Warehouse Boxes onto the Green Cross",
					"Help", JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource().equals(menuBar.getMenu(1).getItem(1))) {
			JOptionPane.showMessageDialog(null, "IT'S A THING!", "About WarehouseBoss",
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
