import javax.swing.JFrame;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class WarehouseBoss implements MenuListener, ActionListener {

	private JFrame frame;
	private Game2 game;
	private JMenu Quit;
	private JMenuItem Reset;
	private LevelMap mapUI;

	public WarehouseBoss() {
		initGame();
	}

	private void initGame() {
		this.game = new Game2();

		this.frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Warehouse Boss");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar mb = new JMenuBar();
		JMenu Game = new JMenu("Game");
		this.Quit = new JMenu("Quit");
		Quit.addMenuListener(this);
		mb.add(Game);
		mb.add(Quit);
		this.Reset = new JMenuItem("Reset");
		this.Reset.addActionListener(this);
		Game.add(Reset);
		JMenu difficulty = new JMenu("Difficulty");
		Game.add(difficulty);
		JMenuItem Easy = new JMenuItem("Easy");
		JMenuItem Normal = new JMenuItem("Normal");
		JMenuItem Hard = new JMenuItem("Hard");
		difficulty.add(Easy);
		difficulty.add(Normal);
		difficulty.add(Hard);
		
		frame.setJMenuBar(mb);

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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.mapUI.getState() == LevelMap.STATE.GAME){
			if(e.getSource().equals(Reset)){
				this.game.resetGame();
			}
		}
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuSelected(MenuEvent mb) {
		if(mb.getSource().equals(Quit)){
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
