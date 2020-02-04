import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**The Book class is used create objects of books that store information about each book.
 * @author Group E
 *
 */
public class Book {
	private String title;
	private ArrayList<String> author;
	private int numAuthors;
	private String subject;
	private int pageCount;
	private String isbn;
	private int timeLimit; //in days
	private int bookAmount;
	private int amountTimesCheckedOut;
	
	/**Constructor for a book that instantiates the attributes:
	 * title, author, numAuthors, subject, pageCount, isbn, bookAmount, amountTimesCheckedOut and defaults timeLimit to 30.
	 */
	public Book(String t, ArrayList<String> a, String s, int pa, String i, int b, int amnt){
		title = t;
		author = a;
		subject = s;
		pageCount = pa;
		isbn = i;
		bookAmount = b;
		amountTimesCheckedOut = amnt;
		timeLimit = 30;
		numAuthors = author.size();
	}
	
	/**Constructor for a book that instantiates the attributes:
	 * title, author, numAuthors, subject, pageCount, isbn, timeLimit, bookAmount, and amountTimesCheckedOut.
	 */
	public Book(String t, ArrayList<String> a, String s, int pa, String i, int b, int amnt, int tl){
		title = t;
		author = a;
		subject = s;
		pageCount = pa;
		isbn = i;
		bookAmount = b;
		amountTimesCheckedOut = amnt;
		timeLimit = tl;
		numAuthors = author.size();
	}
	
	/**Method to increment the bookAmount attribute.
	 */
	public void incrementBookAmnt(){
		bookAmount++;
	}
	
	/**Method to increment the amountTimesCheckedOut attribute.
	 */
	public void incrementTimesOut(){
		amountTimesCheckedOut++;
	}
	
	/**Method to decrement bookAmount attribute.
	 */
	public void decrementBookAmnt(){
		bookAmount--;
	}
	
	/**Method that returns the bookAmount attribute.
	 */
	public int getBookAmount(){
		return bookAmount;
	}
	
	/**Method that returns the amountTimesCheckedOut attribute.
	 */
	public int getTimesOut(){
		return amountTimesCheckedOut;
	}
	
	/**Method that returns the title attribute.
	 */
	public String getTitle(){
		return title;
	}
	/**Method that returns the timeLimit attribute.
	 */
	public int getTimeLimit(){
		return timeLimit;
	}
	
	/**Method that returns the author attribute.
	 */
	public ArrayList<String> getAuthors(){
		return author;
	}
	
	/**Method that returns the subject attribute.
	 */
	public String getSubject(){
		return subject;
	}
	
	/**Method that returns the pageCount attribute.
	 */
	public int getPageCount(){
		return pageCount;
	}
	
	/**Method that returns the isbn attribute.
	 */
	public String getISBN(){
		return isbn;
	}
	
	
	/**Method that returns a string that has the books title, authors, subject, isbn and booksAmount attributes.
	 */
	public String toString(){
		return title + "; " + author + "; Subject: " + subject + "; ISBN: " + isbn + "; Books Left: " + bookAmount;
	}
	
	/**Method that returns a string with all the attributes of a book to be written to a file.
	 */
	public String toFile(){
		String authors = "";
		for(int i = 0; i < author.size(); i++){
			authors += author.get(i) + ":";
		}
		return title + ":" + numAuthors + ":" + authors + subject + ":" + pageCount + 
				":" + isbn + ":" + bookAmount + ":" + amountTimesCheckedOut + ":" + timeLimit + ":";
	}

	/**Method that sets the timeLimit attribute.
	 */
	public void setTimeLimit(int tLim) {
		timeLimit = tLim;
		
	}
	
	

}
