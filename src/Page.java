import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.*;

/**Page class that creates the interface with all the different JPanels.
 * @author Group E
 *
 */
public class Page{
	static JFrame f = new JFrame();
	public static JPanel c = new JPanel();
	Login l = new Login();
	Main m = new Main();
	Account a = new Account();
	Manager man = new Manager();
	public static CardLayout cl = new CardLayout();
	JLabel header = new JLabel("No Tech Marketing Company Reference Library");
	
	/**Constructor for the page class that adds the panels to the CardLayout
	 * and also makes a call to the method that overwrites the files once the system is closed.
	 *
	 */
	public Page(){
		c.setLayout(cl);		
		c.add(l, "login");
		c.add(m, "main");
		c.add(a, "account");
		c.add(man, "manager");
		cl.show(c, "login");
		
		
		f.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	try {
					Library.updateFiles();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
		
		f.add(c);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 300);
		f.setVisible(true);
	}
	
//	public static void main(String[] args) {
//		Page p = new Page();
//	}
	
}
