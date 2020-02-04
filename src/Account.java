import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**The Account class establishes a JPanel which is the structure of the account page.
 * @author Group E
 *
 */
public class Account extends JPanel{
	JButton logout = new JButton("<html><u>Logout</u></html>");
	JButton search = new JButton("<html><u>Search</u></html>");
	JButton retur = new JButton("Return");
	JButton renew = new JButton("Renew (10 days)");
	JButton read = new JButton("Read");
	
	static JButton manager = new JButton("Go to Manager Page");;
	
	//JPanel booksOut = new JPanel();
	JPanel bookInfo = new JPanel();
	
	static JPanel p = new JPanel(new GridBagLayout());
	static GridBagConstraints c = new GridBagConstraints();
	
//	String[] books = {"book1", "book2", "book3"};
	static JComboBox<String> booksOut = new JComboBox<String>();
	
	/**Constructor of an Account Page for which employees and managers use.
	 * 
	 */
	public Account(){
		setLayout(new BorderLayout());
		JLabel title = new JLabel("<html>No Tech Marketing Company<br> Reference Library<html>");
		title.setHorizontalAlignment(JLabel.CENTER);
		add(title, BorderLayout.NORTH);
		
		manager.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Page.cl.show(Page.c, "manager");
			}
		});
		
		logout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removeManagerButton();
				Page.cl.show(Page.c, "login");
			}
		});
		
		search.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Page.cl.show(Page.c, "main");
			}
		});
		
		retur.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(booksOut.getItemCount() == 0){
					JOptionPane.showMessageDialog(null, "No books to return.");
				}
				else{
					String s = (String) booksOut.getSelectedItem();
					String t = s.substring(0, s.indexOf("Due") -1);
					LocalDate l= LocalDate.parse(s.substring(s.indexOf("Date:") + 6, s.length()));
					UserAccount user = Library.getLoggedIn();
					user.removeBooksOut(t);
					ArrayList<Book> bks = Library.getBooks();
					for(int k = 0; k < bks.size(); k++){
						Book b = bks.get(k);
						if(b.getTitle().equals(t)){
							b.incrementBookAmnt();
						}
					}
					int i = booksOut.getSelectedIndex();
					booksOut.removeItemAt(i);
				}
			}
		});
		
		renew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(booksOut.getItemCount() == 0){
					JOptionPane.showMessageDialog(null, "No books to renew.");
				}
				else{
					int i = booksOut.getSelectedIndex();
					String s = (String) booksOut.getSelectedItem();
					String t = s.substring(0, s.indexOf("Due") -1);
					LocalDate l= LocalDate.parse(s.substring(s.indexOf("Date:") + 6, s.length()));
					l = l.plusDays(10);
					UserAccount user = Library.getLoggedIn();
					user.setDueDate(l, t);
					booksOut.removeItemAt(i);
					String bo = t + " Due Date: " + l;
					booksOut.addItem(bo);
					booksOut.revalidate();
				}
			}
		});
		
		logout.setBorderPainted(false);
		search.setBorderPainted(false);
		
//		booksOut.setLayout(new GridLayout(1, 3));
//		booksOut.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
//		for (int i =0; i<(1*3); i++){
//		    final JLabel label = new JLabel("<html><br>&nbsp Book and Due Date&nbsp<br><br><html>");
//		    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		    booksOut.add(label);
//		}
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		p.add(new JLabel("<html><b>My Account</b><html>"), c);
		c.gridx = 2;
		c.gridwidth = 1;
		p.add(logout, c);
		c.gridx = 3;
		p.add(search, c);
		c.gridx = 4;
		c.gridwidth = 3;
		p.add(new JLabel("         "), c);
		c.gridx=0;
		c.gridy=1;
		c.gridwidth = 4;
		p.add(new JLabel("Currently Checked Out: "), c);
		c.gridy=3;
		c.gridwidth = 13;
		c.fill = GridBagConstraints.HORIZONTAL;
		p.add(read, c);
		c.gridx=0;
		c.gridy=5;
		c.gridwidth = 13;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		p.add(retur, c);
		c.gridy=6;
		p.add(renew, c);
		c.gridy=2;
		c.gridwidth = 13;
		c.gridheight = 1;
		p.add(booksOut, c);
		
		
		add(p, BorderLayout.CENTER);		
		
	}
	
	/**Once an account is created a certain information is stored to that specific account.
	 * 
	 */
	public static void createAccountPage(){
		UserAccount user = Library.getLoggedIn();
		int size = booksOut.getItemCount();
		if(size  == 0){
			if(user.getBooksOut() == null){
				booksOut = new JComboBox<String>();
			}
			else{
				ArrayList<String> books = user.getBooksOut();
				ArrayList<LocalDate> dueDates = user.getDueDate();
				for(int i = 0; i < books.size(); i++){
					String b = books.get(i);
					String d = dueDates.get(i).toString();
					String bo = b + " Due Date: " + d;
					booksOut.addItem(bo);
				}
			}
		}
		else{
			booksOut.removeAllItems();
			if(user.getBooksOut() == null){
				booksOut = new JComboBox<String>();
			}
			else{
				ArrayList<String> books = user.getBooksOut();
				ArrayList<LocalDate> dueDates = user.getDueDate();
				for(int i = 0; i < books.size(); i++){
					String b = books.get(i);
					String d = dueDates.get(i).toString();
					String bo = b + " Due Date: " + d;
					booksOut.addItem(bo);
				}
			}
		}
		if(user.checkManager()){
			c.gridx = 4;
			c.gridy = 0;
			p.add(manager, c);
		}
	}
	
	
	/**Method that allows the manager button to be removed from the JPanel once the Manager logs out.
	 * 
	 */
	public static void removeManagerButton() {
		p.remove(manager);
	}

//	public static void main(String[] args) {
//		JFrame f = new JFrame();
//		f.setSize(500,  350);
//		f.add(new Account());
//		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		f.setVisible(true);
//
//	}

}
