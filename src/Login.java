import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**JPanel which creates an interface for users to log in and register.
 * It checks if their credentials are correct or not and if their register was succesful.
 * @author Group E
 *
 */
public class Login extends JPanel{
	JPanel l = new JPanel();
	
	JTextField luser = new JTextField(10);
	JTextField lpass = new JTextField(10);
	JTextField ruser = new JTextField(10);
	JTextField rpass = new JTextField(10);
	JButton login = new JButton("Log in");
	JButton register = new JButton("Register");
	
	/**Constructor for the Login class that adds actions to buttons and creates the panels.
	 *
	 */
	public Login(){
		setLayout(new BorderLayout());
		
		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// Check if username exists
				String user = luser.getText();
				String pass = lpass.getText();
				if (!Library.getUsernames().contains(user))
				{
					JOptionPane.showMessageDialog(null, "Wrong Username");
				}
				else
				{
					// Compare password to stored password
					int i = Library.getUsernames().indexOf(user);
					int passHash = pass.hashCode();
					int correctPass = Library.getPasswords().get(i);
					
					if(passHash == correctPass){
						ArrayList<UserAccount> a = Library.getAccounts();
						UserAccount li = null;
						for(int k = 0; k < a.size(); k++){
							String u = a.get(k).getUsername();
							if(u.equalsIgnoreCase(user)){
								
								li = a.get(k);
							}
						}
						
						Library.setLoggedIn(li);
						Page.cl.show(Page.c, "main");
					}
					else{
						JOptionPane.showMessageDialog(null, "Wrong Password");
					}
				}
				
			}
		});
		
		register.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String user = ruser.getText();
				String pass = rpass.getText();
				if(user.equals("") || pass.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Must Type Something");
				}
				else{
					if (Library.registerUser(user, pass))
					{
						Page.cl.show(Page.c, "main");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Username Taken");
					}
				}
			}
		});
		
		l.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		l.add(new JLabel("Username:"), c);
		c.gridy++;
		l.add(new JLabel("Password:"), c);
		c.gridy = 0;
		c.gridx = 1;
		l.add(luser, c);
		c.gridy++;
		l.add(lpass, c);
		c.gridy++;
		l.add(login, c);
		c.gridx = 3;
		c.gridy = 0;
		l.add(new JLabel("      "), c);
		c.gridx = 3;
		c.gridy = 1;
		l.add(new JLabel("      "), c);
		c.gridx = 4;
		c.gridy = 0;
		l.add(new JLabel("Username:"), c);
		c.gridy++;
		l.add(new JLabel("Password:"), c);
		c.gridy = 0;
		c.gridx = 5;
		l.add(ruser, c);
		c.gridy++;
		l.add(rpass, c);
		c.gridy++;
		l.add(register, c);
		
		JLabel title = new JLabel("<html>No Tech Marketing Company<br> Reference Library<html>");
		title.setHorizontalAlignment(JLabel.CENTER);
		add(title, BorderLayout.NORTH);
		add(l, BorderLayout.CENTER);
	}
	
//	public static void main(String[]args){
//		JFrame f = new JFrame();
//		f.setSize(450,  200);
//		f.add(new Login());
//		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		f.setVisible(true);
//	
//	}

}
