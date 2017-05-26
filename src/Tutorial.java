import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

public class Tutorial extends Observable implements Observer {
	// declaring variables
	
	// a Game instance
	private Game game;
	// a boolean to check if a move has been made
	private boolean tutorialMoveCheck;
	// a boolean to check if a box is in the right spot
	private boolean tutorialBoxCheck;
	// a boolean to check if a berry has been triggered
	private boolean tutorialBerryCheck;
	// a boolean to check if the tutorial has ended
	private boolean tutorialEndCheck;

	/**
	 * constructor
	 * 
	 * @param 	game	game state
	 * @return	-
	 * @throws	-
	 */
	public Tutorial(Game game) {
		super();
		// initialise the game
		this.game = game;
	}
	
	/**
	 * method: ititialPrompt() -> renders the initial prompt during the tutorial
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public void ititialPrompt() {
		// display the prompt
		JOptionPane.showMessageDialog(null, "Use WASD or arrow keys to move the player.", "Tutorial",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * method: tutorialSteps() -> the steps and game matrix state set during the tutorial
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	private void tutorialSteps() {
		// if no moves have been made
		if (!tutorialMoveCheck) {
			// a move should be made after this
			tutorialMoveCheck = true;
			// change the map
			int[][] matrix = new int[][] {
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,4,4,4,4,1,4,1,4,1,1},
				{1,1,1,1,4,1,1,1,1,1,1},
				{1,1,1,1,4,4,4,1,4,3,1},
				{1,1,1,3,4,4,4,1,4,3,1},
				{1,1,1,1,1,1,1,1,1,1,1}
			};
			this.game.changeMap(matrix);
			// show prompt
			JOptionPane.showMessageDialog(null, "Walk into boxes to move push it in that direction.", "Tutorial",
					JOptionPane.INFORMATION_MESSAGE);
		}
		// if the boxes have not been moved
		if (this.game.isBox(3, 4) && !tutorialBoxCheck) {
			// the boxes will be moved after this
			tutorialBoxCheck = true;
			// show prompt
			JOptionPane.showMessageDialog(null, "Move the box onnto the cross (the thing with the arrow over them).", "Tutorial", JOptionPane.INFORMATION_MESSAGE);
		}
		// if a berry has not been triggered
		if (this.game.isBox(4, 3) && !tutorialBerryCheck) {
			// a berry will be triggered
			tutorialBerryCheck = true;
			// change the map
			int[][] matrix = new int[][] {
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,4,4,4,4,4,4,1,4,1,1},
				{1,1,1,1,4,4,4,1,1,1,1},
				{1,1,1,1,4,4,4,1,4,3,1},
				{1,1,1,3,4,4,4,1,4,3,1},
				{1,1,1,1,1,1,1,1,1,1,1}
			};
			this.game.changeMap(matrix);
			// show prompt
			JOptionPane.showMessageDialog(null, "Move Player over Berry Power up! It pauses time for 3 seconds",
					"Tutorial", JOptionPane.INFORMATION_MESSAGE);
		}
		// if there are no berries left and the tutorial is not over
		if (this.game.getBerries().isEmpty() && !tutorialEndCheck) {
			// the tutorial will be over after this
			tutorialEndCheck = true;
			// change the map
			int[][] matrix = new int[][] {
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,4,4,4,4,4,4,4,4,1,1},
				{1,1,1,1,4,1,4,4,1,1,1},
				{1,1,1,1,4,1,4,4,4,3,1},
				{1,1,1,3,4,4,4,4,4,3,1},
				{1,1,1,1,1,1,1,1,1,1,1}
			};
			this.game.changeMap(matrix);
			// show prompt
			JOptionPane.showMessageDialog(null, "Move the rest of the boxes onto crosses", "Tutorial",
					JOptionPane.INFORMATION_MESSAGE);
		}
		// if the game has been won
		if (this.game.isWin()) {
			// show the options and update the window
			int option = showTutWin();
			setChanged();
			notifyObservers(option);
		}
	}
	
	/**
	 * method: showTutWin() -> the options that are availiable the tutorial level is complete
	 * 
	 * @param	-
	 * @return	(an int)	 the option chose by user
	 * @throws	-
	 */
	private int showTutWin() {
		// a list of choices
		String[] choices = { "Practice", "Start Game", "Quit Game" };
		// show the choices
		int choice = JOptionPane.showOptionDialog(null, "Congradulations, Tutorial Complete!", "",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		// act accordingly based on the user's choice
		if (choice == JOptionPane.CANCEL_OPTION) {
			System.exit(1);
		} else if (choice == JOptionPane.YES_OPTION) {
			return 1;
		} else if (choice == JOptionPane.NO_OPTION) {
			return 2;
		}
		return 0;
	}

	/**
	 * override: update() -> updates the tutorial
	 * 
	 * @param	o		an Observable instance
	 * @param	arg		an Object instance
	 * @return	-
	 * @throws	-
	 */
	@Override
	public void update(Observable o, Object arg) {
		// call the tutorialSteps() method
		tutorialSteps();
	}
}
