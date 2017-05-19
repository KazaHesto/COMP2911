import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class WarehouseBoss {

	private JFrame frame;

	public WarehouseBoss() {
		initGame();
	}

	private void initGame() {
		Game2 game = new Game2();

		this.frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Warehouse Boss");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar mb = new JMenuBar();
		JMenu Game = new JMenu("Game");
		JMenu Quit = new JMenu("Quit");
		mb.add(Game);
		mb.add(Quit);
		JMenuItem difficulty = new JMenuItem("Difficulty");
		Game.add(difficulty);
		
		MenuBarController mbc = new MenuBarController(Quit);
		Quit.addMenuListener(mbc);
		
		frame.setJMenuBar(mb);

		LevelMap mapUI = new LevelMap(6, 11);
		frame.add(mapUI);
		LevelMapController controller = new LevelMapController(game, mapUI);
		MenuController Mcontroller = new MenuController(mapUI);
		mapUI.setController(controller);
		mapUI.setMenuController(Mcontroller);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		WarehouseBoss ex = new WarehouseBoss();
	}
}
