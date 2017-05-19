import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuBarController implements MenuListener {
	private JMenu Quit;
	public MenuBarController(JMenu Quit){
		this.Quit = Quit;
	}
	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuSelected(MenuEvent mb) {
		if(mb.getSource().equals(Quit)){
			System.exit(1);
		}
		
	}

}
