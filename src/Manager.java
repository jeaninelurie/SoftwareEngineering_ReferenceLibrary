import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**JPanel that creates an interface for the managers privileges.
 * @author Group E
 *
 */
public class Manager extends JPanel{
	JButton bookLimit = new JButton("Set Book Limits");
	JButton timeLimit = new JButton("Set Time Limits");
	JButton add = new JButton("Add Book");
	JButton remove = new JButton("Remove Book");
	JButton reports = new JButton("Print Reports");
	
	JButton account = new JButton("<html><u>My Account</u></html>");
	JButton logout = new JButton("<html><u>Logout</u></html>");
	JButton search = new JButton("<html><u>Search</u></html>");
	
	/**Constructor for the Login class that adds actions to buttons and creates the panels.
	 *
	 */
	public Manager(){
		setLayout(new BorderLayout());
		JLabel title = new JLabel("<html>No Tech Marketing Company<br> Reference Library<html>");
		title.setHorizontalAlignment(JLabel.CENTER);
		add(title, BorderLayout.NORTH);
		
		account.setBorderPainted(false);
		logout.setBorderPainted(false);
		search.setBorderPainted(false);
		
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
		
		search.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Page.cl.show(Page.c, "main");
			}
		});
		
		bookLimit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = JOptionPane.showInputDialog(null, "Enter username of the user account:");
				if(username != null && username.length() > 0){
					String bLim  = JOptionPane.showInputDialog(null, "Enter updated Book Limit");
					if(bLim != null && bLim.length() > 0){
						UserAccount user = null;
						ArrayList<UserAccount> accnts = Library.getAccounts();
						for(int i = 0; i < accnts.size(); i++){
							UserAccount accnt = accnts.get(i);
							if(accnt.getUsername().equals(username)){
								user = accnt;
							}
						}
				
						if(user.equals(null)){
							JOptionPane.showMessageDialog(null, "No user with that username");
						}
						else{
							int numBksOut = user.getBooksOut().size();
							if(numBksOut >= new Integer(bLim)){
								JOptionPane.showMessageDialog(null, "User has too many books out to change book limit to " + bLim);
							}
							else{
								user.setBookLimit(new Integer(bLim));
								JOptionPane.showMessageDialog(null, username + " Book Limit: " + bLim);
							}
						}
					}
				}
			}
		});
		
		timeLimit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = JOptionPane.showInputDialog(null, "Enter title of the book:");
				if(title!= null && title.length() > 0){
					String tLim  = JOptionPane.showInputDialog(null, "Enter updated Time Limit in days");
					if(tLim != null && tLim.length() > 0){
						Book book = null;
						ArrayList<Book> books = Library.getBooks();
						for(int i = 0; i < books.size(); i++){
							Book bk = books.get(i);
							if(bk.getTitle().equals(title)){
								book = bk;
							}
						}
				
						if(book == null){
							JOptionPane.showMessageDialog(null, "No book with that title.");
						}
						else{
							book.setTimeLimit(new Integer(tLim));
							JOptionPane.showMessageDialog(null, title + " Time Limit: " + tLim);
						}
					}
				}
			}
		});
		
		add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = JOptionPane.showInputDialog(null, "Enter title:");
				if(title != null && title.length() > 0){
					String numAuth  = JOptionPane.showInputDialog(null, "Enter number of authors:");
					if(numAuth != null && numAuth.length()> 0){
						ArrayList<String> authors = new ArrayList<String>();
						for(int i = 0; i < new Integer(numAuth); i++){
							authors.add(JOptionPane.showInputDialog(null, "Enter authors:"));
						}
						if(authors.size() > 0){
							String subject  = JOptionPane.showInputDialog(null, "Enter subject:");
							if(subject != null && subject.length() > 0){
								String pageCount  = JOptionPane.showInputDialog(null, "Enter page count:");
								if(pageCount != null && pageCount.length() > 0){
									String isbn  = JOptionPane.showInputDialog(null, "Enter ISBN:");
									if(isbn != null && isbn.length() > 0){
										String bookAmnt  = JOptionPane.showInputDialog(null, "Enter book amount:");
										if(bookAmnt != null && bookAmnt.length() > 0){
											String timeLimit  = JOptionPane.showInputDialog(null, "Enter time limit:");
											if(timeLimit != null && timeLimit.length() > 0){
												Book b = new Book(title, authors, subject, new Integer(pageCount), 
														isbn, new Integer(bookAmnt), 0, new Integer(timeLimit));
												Library.addBook(b);
				
												Library.addSubject(subject);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		});
		
		remove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = JOptionPane.showInputDialog(null, "Enter title of book that you want to remove");
				if(title != null && title.length() > 0){
					Book book = null;
					ArrayList<Book> books = Library.getBooks();
					for(int i = 0; i < books.size(); i++){
						Book bk = books.get(i);
						if(bk.getTitle().equals(title)){
							book = bk;
						}
					}
				
					if(book == null){
						JOptionPane.showMessageDialog(null, "No book with that title.");
					}
					else{
						Library.removeBook(book);
					}
				}
			}
		});
		
		reports.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame f = new JFrame();
				JButton one = new JButton("All Reports");
				JButton two = new JButton("Number of Book Checkouts");
				JButton three = new JButton("Currently Available Books");
				JButton four = new JButton("All Accounts");
				JButton five = new JButton("Books By Subject");
				JButton six = new JButton("Currently Checked Out Books");
				JPanel p = new JPanel(new GridLayout(6, 1));
				p.add(one);
				p.add(two);
				p.add(three);
				p.add(four);
				p.add(five);
				p.add(six);
				f.add(p);
				
				one.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e)
				    {
				    	Library.numCheckBooksReport();
				    	Library.currAvailableBooksReport();
				    	Library.allAccountsReport();
				    	Library.booksBySubjectReport();
				    	Library.currCheckedOutBookReport();
				       f.dispose();
				    }
				});
				
				two.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e)
				    {
				    	Library.numCheckBooksReport();
				    	f.dispose();
				    }
				});
				
				three.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e)
				    {
				    	Library.currAvailableBooksReport();
				    	f.dispose();
				    }
				});
				
				four.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e)
				    {
				    	Library.allAccountsReport();
				    	f.dispose();
				    }
				});
				
				five.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e)
				    {
				    	Library.booksBySubjectReport();
				    	f.dispose();
				    }
				});
				
				six.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e)
				    {
				    	Library.currCheckedOutBookReport();
				    	f.dispose();
				    }
				});
				
				
				f.pack();
				f.setVisible(true);
			}
		});
		
		
		
		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		p.add(search, c);
		c.gridx=1;
		p.add(account, c);
		c.gridx=2;
		p.add(logout, c);
		c.gridx = 0;
		c.gridy=1;
		p.add(bookLimit, c);
		c.gridy=2;
		p.add(timeLimit, c);
		c.gridx=1;
		c.gridy=3;
		p.add(reports, c);
		c.gridx=2;
		c.gridy=1;
		p.add(add, c);
		c.gridy=2;
		p.add(remove, c);
		
		add(p, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(550,  350);
		f.add(new Manager());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

	}

}
