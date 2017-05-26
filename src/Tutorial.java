import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

public class Tutorial extends Observable implements Observer {

	private Game game;
	private boolean tutorialMoveCheck;
	private boolean tutorialBoxCheck;
	private boolean tutorialBerryCheck;
	private boolean tutorialEndCheck;

	public Tutorial(Game game) {
		super();
		this.game = game;
	}

	public void ititialPrompt() {
		JOptionPane.showMessageDialog(null, "Use WASD or arrow keys to move the player.", "Tutorial",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void tutorialSteps() {
		if (!tutorialMoveCheck) {
			tutorialMoveCheck = true;
			int[][] matrix = new int[][] {
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,4,4,4,4,1,4,1,4,1,1},
				{1,1,1,1,4,1,1,1,1,1,1},
				{1,1,1,1,4,4,4,1,4,3,1},
				{1,1,1,3,4,4,4,1,4,3,1},
				{1,1,1,1,1,1,1,1,1,1,1}
			};
			this.game.changeMap(matrix);
			JOptionPane.showMessageDialog(null, "Walk into boxes to move push it in that direction.", "Tutorial",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (this.game.isBox(3, 4) && !tutorialBoxCheck) {
			tutorialBoxCheck = true;
			JOptionPane.showMessageDialog(null, "Move the box onnto the cross (the thing with the arrow over them).", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
		}
		if (this.game.isBox(4, 3) && !tutorialBerryCheck) {
			tutorialBerryCheck = true;
			int[][] matrix = new int[][] {
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,4,4,4,4,4,4,1,4,1,1},
				{1,1,1,1,4,4,4,1,1,1,1},
				{1,1,1,1,4,4,4,1,4,3,1},
				{1,1,1,3,4,4,4,1,4,3,1},
				{1,1,1,1,1,1,1,1,1,1,1}
			};
			this.game.changeMap(matrix);
			JOptionPane.showMessageDialog(null, "Move Player over Berry Power up! It pauses time for 3 seconds",
					"Tutorial", JOptionPane.INFORMATION_MESSAGE);
		}
		if (this.game.getBerries().isEmpty() && !tutorialEndCheck) {
			tutorialEndCheck = true;
			int[][] matrix = new int[][] {
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,4,4,4,4,4,4,4,4,1,1},
				{1,1,1,1,4,1,4,4,1,1,1},
				{1,1,1,1,4,1,4,4,4,3,1},
				{1,1,1,3,4,4,4,4,4,3,1},
				{1,1,1,1,1,1,1,1,1,1,1}
			};
			this.game.changeMap(matrix);
			JOptionPane.showMessageDialog(null, "Move the rest of the boxes onto crosses", "Tutorial",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (this.game.isWin()) {
			int option = showTutWin();
			setChanged();
			notifyObservers(option);
		}
	}

	private int showTutWin() {
		String[] choices = { "Practice", "Start Game", "Quit Game" };
		int choice = JOptionPane.showOptionDialog(null, "Congradulations, Tutorial Complete!", "",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		if (choice == JOptionPane.CANCEL_OPTION) {
			System.exit(1);
		} else if (choice == JOptionPane.YES_OPTION) {
			return 1;
		} else if (choice == JOptionPane.NO_OPTION) {
			return 2;
		}
		return 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		tutorialSteps();
	}
}
