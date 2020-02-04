import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.*;

/**JPanel that creates an interface for users to logout, visit their account page, search for a book or rent a book.
 * @author Group E
 *
 */
public class Main extends JPanel{
	JButton account = new JButton("<html><u>My Account</u></html>");
	JButton logout = new JButton("<html><u>Logout</u></html>");
	JButton search = new JButton("Search");
	JButton rent = new JButton("Rent");
	JList<Book> results = new JList<Book>(new DefaultListModel<Book>());
	
	JTextField s = new JTextField(10);
	
	/**Constructor for the main page that adds actions to buttons and creates the panels.
	 *
	 */
	public Main(){
		setLayout(new BorderLayout());

		JLabel title = new JLabel("<html>No Tech Marketing Company<br> Reference Library<html>");
		title.setHorizontalAlignment(JLabel.CENTER);
		add(title, BorderLayout.NORTH);
		
//		s.setMinimumSize(new Dimension(10, 5));
		
		search.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(results.getModel().getSize() != 0){
					((DefaultListModel<Book>) results.getModel()).removeAllElements();
				}
				ArrayList<Book> bks = Library.getBooks();
				ArrayList<Book> matchSearch = new ArrayList<Book>();
				String srch = s.getText();
				srch = srch.toLowerCase();
				System.out.print(srch);
				
				boolean added = false;
				for(int i = 0; i < bks.size(); i++){
					Book b = bks.get(i);
					String t = b.getTitle();
					t = t.toLowerCase();
					ArrayList<String> a = b.getAuthors();
					for(int k = 0; k < a.size(); k++){
						String s = a.get(k).toLowerCase();
						a.set(k, s);
					}
					String s = b.getSubject();
					s = s.toLowerCase();
					String isbn = b.getISBN();
					isbn = isbn.toLowerCase();
					
					for(int k = 0; k < a.size(); k++){
						String auth = a.get(k);
						if(auth.contains(srch) || srch.contains(auth)){
							if(!added)
							{
							added = true;
							((DefaultListModel<Book>)results.getModel()).addElement(b);
							}
						}
					}
					
					if(t.contains(srch) || srch.contains(t)){
						if(!added)
						{
						added = true;
						((DefaultListModel<Book>)results.getModel()).addElement(b);
						}
					}
					
					if(s.contains(srch) || srch.contains(s)){
						if(!added)
						{
						added = true;
						((DefaultListModel<Book>)results.getModel()).addElement(b);
						}
					}
					
					if(isbn.contains(srch) || srch.contains(isbn)){
						if(!added)
						{
						added = true;
						((DefaultListModel<Book>)results.getModel()).addElement(b);
						}
					}
					added = false;
				}
				
			}
		});
		
		rent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Book b = results.getSelectedValue();
				if(b == null){
					JOptionPane.showMessageDialog(null, "Nothing Selected");
				}
				else{
					UserAccount user = Library.getLoggedIn();
					int s = user.getBooksOut().size();
					int bl = user.getBookLimit();
					if(s == bl){
						JOptionPane.showMessageDialog(null, "Already at book limit. Cannot take out any more books.");
					}
					else if(b.getBookAmount() == 0){
						JOptionPane.showMessageDialog(null, "No more left of this book.");
					}
					else{
						ArrayList<String> t = user.getBooksOut();
						Boolean clear = true;
						for(int i = 0; i < s; i++){
							if(t.get(i).equals(b.getTitle())){
								JOptionPane.showMessageDialog(null, "Already Checked Out.");
								clear = false;
							}
						}
						if(clear){
							LocalDate l = Library.getCurrentDate().plusDays(b.getTimeLimit());
							user.addBooksOut(b.getTitle(), l);
							b.decrementBookAmnt();
							b.incrementTimesOut();
							JOptionPane.showMessageDialog(null, "Book Checked Out.");
						}
					}
				}
				
			}
		});
		
		account.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Account.createAccountPage();
				Page.cl.show(Page.c, "account");
			}
		});
		
		logout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Account.removeManagerButton();
				Page.cl.show(Page.c, "login");
			}
		});
		
		account.setBorderPainted(false);
		logout.setBorderPainted(false);
		
		
		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=5;
		c.gridy=0;
		c.gridwidth = 2;
		p.add(account, c);
		c.gridx = 7;
		p.add(logout, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 8;
		p.add(new JLabel(" "), c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx=1.;
		c.fill=GridBagConstraints.HORIZONTAL;
		p.add(s, c);
		c.fill = 0;
		c.gridx = 5;
		c.gridwidth = 1;
		p.add(search, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 7;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 5;
		p.add(results, c);
		c.gridx=5;
		p.add(rent, c);
		
		
		
		add(p, BorderLayout.CENTER);
	}

//	public static void main(String[] args) {
//		JFrame f = new JFrame();
//		f.setSize(700,  300);
//		f.add(new Main());
//		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		f.setVisible(true);
//
//	}

}
