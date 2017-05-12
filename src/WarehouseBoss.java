import javax.swing.JFrame;

public class WarehouseBoss {

	private JFrame frame;

	public WarehouseBoss() {
		initGame();
	}

	private void initGame() {
		Game game = new Game();

		this.frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Warehouse Boss");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LevelMap mapUI = new LevelMap(6, 11, game);
		frame.add(mapUI);
		LevelMapController controller = new LevelMapController(game, mapUI);
		mapUI.setController(controller);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		WarehouseBoss ex = new WarehouseBoss();
	}
}
