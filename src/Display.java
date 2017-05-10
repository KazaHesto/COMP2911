import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


//the "canvas" that game.render() draws onto
//eventually gets displayed on the window
public class Display {

	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width,height;
	private KeyListener listener;
	
	public Display(String title, int width, int height, KeyListener listener) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.listener = listener;
		
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		frame.addKeyListener(this.listener);
		
		frame.add(canvas);
		frame.pack();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	public KeyListener getListener() {
		return listener;
	}
}