import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class will be responsible for the client generated events.
 * 
 * @author Michael
 * 
 */
public class Controller implements ActionListener {
	private Model model;

	/**
	 * Create a Controller object linked to a Model object.
	 * 
	 * @param mdl
	 *            , an existing Model object to send messages
	 */
	public Controller(Model mdl) {
		model = mdl;
	}

	/**
	 * Define the Java required method to respond to an action event.
	 * 
	 * @param event
	 *            , an ActionEvent from a button on the board or a menu item
	 */
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		//System.out.println("Conrtoller " + command);
		model.respond(command);
	}
}
