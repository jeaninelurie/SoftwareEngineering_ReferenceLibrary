import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

/**The UserAccount class is used to create objects of accoounts that store information about that user.
 * @author Group E
 *
 */
public class UserAccount {
	private String username;
	private int bookLimit;
 	private ArrayList<String> booksOut;
 	private ArrayList<LocalDate> dueDates;
 	private boolean manager;
	
 	/**Constructor that takes information for the attributes: username, booksOut, dueDates, manager, and sets bookLimit to 3.
	 * @param	u	user name of the user
	 * @param	bo	ArrayList of the users books out
	 * @param 	dd	ArrayList of the due dates for each book the user has out	
	 * @param 	m	boolean that informs if the user is a manager or not
	 */
 	public UserAccount(String u, ArrayList<String> bo, ArrayList<LocalDate> dd, boolean m){
 		username = u;
 		bookLimit = 3;
 		booksOut = bo;
 		dueDates = dd;
 		manager = m;
 				
 		// Add user data file if one doesn't exist	
 	}
 	
 	/**Constructor that takes information for the attributes: username manager, and sets bookLimit to 3.
 	 * Used when the user has no books out.
 	 * @param	u	user name of the user
	 * @param 	m	boolean that informs if the user is a manager or not
	 */
 	public UserAccount(String u, boolean m){
 		username = u;
 		bookLimit = 3;
 		booksOut = new ArrayList<String>();
 		dueDates = new ArrayList<LocalDate>();
 		manager = m;		
 		// Add user data file if one doesn't exist	
 	}
 	
 	/**
 	 * @return username attribute
 	 */
 	public String getUsername() {
 		return username;
 	}
 	
 	/**
 	 * @return bookLimit attribute
 	 */
 	public int getBookLimit() {
 		return bookLimit;
 	}
 	
 	/** sets the bookLimit attribute
 	 * @param l new book limit
 	 */
 	public void setBookLimit(int l) {
 		bookLimit = l;
 	}
 	
 	/**
 	 * @return booksOut ArrayList attribute
 	 */ 
 	public ArrayList<String> getBooksOut() {
 		return booksOut;
 	}
 	
 	/** adds a book and its due date to the corresponding ArrayLists
 	 * @param	b	book title to be added
 	 * @param	d	LocalDate to be added
 	 */
 	public void addBooksOut(String b, LocalDate d) {
 		booksOut.add(b);
 		dueDates.add(d);
 	}
 	
 	/** removes a book and its due date from the corresponding ArrayLists
 	 * @param	b	book title to be removed
 	 */
 	public void removeBooksOut(String b) {
 		int i = booksOut.indexOf(b);
 		booksOut.remove(b);
 		dueDates.remove(i);
 	}
 	
 	/** Updates the due date of a book
 	 * @param	ld 		new due date
 	 * @param 	book 	book to be updated
 	 */
 	public void setDueDate(LocalDate ld, String book){
 		int i = booksOut.indexOf(book);
 		dueDates.set(i, ld);
 	}
 	
 	/**
 	 * @return dueDates ArrayList attribute
 	 */ 
 	public ArrayList<LocalDate> getDueDate(){
 		return dueDates;
 	}
 	
 	/**
 	 * @return manager attribute
 	 */ 
 	public boolean checkManager(){
 		return manager;
 	}
 	
 	/**
 	 * @return string of the username, booksOut and dueDates attributes
 	 */ 
 	public String toString(){
 		return username + "; " + booksOut + "; " + dueDates;
 	}
 	
 	/**Method that returns a string with all the attributes of a user accounts to be written to a file.
	 */
 	public String toFile(){
 		if(booksOut.size() == 0){
 			return username + ":" + booksOut.size() + ":" + manager + ":";
 		}
 		else{
 			String bksOut = "";
 			for(int i = 0; i < booksOut.size(); i++){
 				bksOut += booksOut.get(i) + ":";
 			}
 		
 			String dueDts = "";
 			for(int i = 0; i < dueDates.size(); i++){
 				dueDts += dueDates.get(i) + ":";
 			}
 		
 			return username + ":" + booksOut.size() + ":" + bksOut + dueDts + manager + ":";
 		}
 	}
}
