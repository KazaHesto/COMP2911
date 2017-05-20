import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class WarehouseBoss implements ActionListener {

	private JFrame frame;
	private Game2 game;
	private LevelMap mapUI;
	private JMenuBar menuBar;

	public WarehouseBoss() {
		initGame();
	}

	private void initGame() {
		this.game = new Game2();

		this.frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Warehouse Boss");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = createMenuBar();

		frame.setJMenuBar(menuBar);

		this.mapUI = new LevelMap(6, 11);
		frame.add(mapUI);
		LevelMapController controller = new LevelMapController(game, mapUI);
		MenuController Mcontroller = new MenuController(mapUI);
		mapUI.setController(controller);
		mapUI.setMenuController(Mcontroller);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menuBar.getMenu(0).getItem(0))) {
			if (this.mapUI.getState() == LevelMap.STATE.GAME) {
				this.game.resetGame();
			} else {
				this.mapUI.setState();
				this.mapUI.repaint();
			}
		} 
		if (e.getSource().equals(menuBar.getMenu(0).getItem(5))) {
			System.exit(1);
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
	}
}
