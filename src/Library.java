import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import javax.security.auth.Subject;
import javax.swing.JOptionPane;

/**This is the library "database" which holds the temporary ArrayLists of user data, book lists, the current date and who is currently logged in.
 * @author Group E
 *
 */
public class Library {
	private static ArrayList<String> usernames= new ArrayList<String>();
	private static ArrayList<Integer> passwords= new ArrayList<Integer>();
	private static ArrayList<UserAccount> accounts= new ArrayList<UserAccount>();
	private static ArrayList<String> bookNames = new ArrayList<String>();
	private static ArrayList<Book> books= new ArrayList<Book>();
	private static ArrayList<String> subjects = new ArrayList<String>();
	private static LocalDate currentDate = LocalDate.now();
	private static UserAccount loggedIn = null;
	
	/**
	 * @return the ArrayList of usernames
	 */
	public static ArrayList<String> getUsernames(){
		return usernames;
	}
	
	/**
	 * @return the ArrayList of accounts
	 */
	public static ArrayList<UserAccount> getAccounts(){
		return accounts;
	}
	
	/**
	 * @return the ArrayList of books
	 */
	public static ArrayList<Book> getBooks(){
		return books;
	}
	
	/**
	 * @return the ArrayList of passwords
	 */
	public static ArrayList<Integer> getPasswords(){
		return passwords;
	}
	
	/**
	 * @return the loggedIn attribute
	 */
	public static UserAccount getLoggedIn(){
		return loggedIn;
	}
	
	/**Sets an account active.
	 * @param	l 	the new account to be set active
	 */
	public static void setLoggedIn(UserAccount l){
		loggedIn = l;
	}
	
	/**
	 * @return the currentDate attribute
	 */
	public static LocalDate getCurrentDate(){
		return currentDate;
	}
	
	/**
	 * Adds book to the books ArrayList.
	 * @param	b	book to be added
	 */
	public static void addBook(Book b) {
		books.add(b);	
	}
	
	/**
	 * Removes book from the books ArrayList.
	 * @param	b	book to be removed
	 */
	public static void removeBook(Book b) {
		books.remove(b);	
	}
	
	/**
	 * Adds subject to the subject ArrayList.
	 * @param	s	subject to be added
	 */
	public static void addSubject(String s) {
		if(!subjects.contains(s)){
			subjects.add(s);
		}
	}
	
	/** Registers user if possible.
	 * @param username
	 * @param password
	 * @return boolean value to indicate weather the register was successful or not
	 */
	public static boolean registerUser(String username, String password) {
		
		// Check if username exists
		if (usernames.contains(username))
		{
			return false;
		}
		
		// Create account
		usernames.add(username);
		UserAccount newAccount = new UserAccount(username, false);
		setLoggedIn(newAccount);
		accounts.add(newAccount);		
		passwords.add(password.hashCode());
		return true;
	}
	
	/**Reads in usernames, passwords, accounts, books and subjects to the corresponding ArrayLists, 
	 * as well as check for books past due and automatically return them.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Library library = new Library();
		System.out.println(currentDate);
		File usernamesFile = new File("usernames.txt");
		
		// Create usernames file if one doesn't exist
		usernamesFile.createNewFile();
		
		Scanner inFile = new Scanner(usernamesFile);
		// Add account names from usernames file
		while (inFile.hasNextLine())
		{
			usernames.add(inFile.nextLine());
		}
		inFile.close();
		
		Scanner inFile2 = new Scanner(new File("booknames.txt"));
		// Initialize books
		while (inFile2.hasNextLine())
		{
			library.bookNames.add(inFile2.nextLine());
		}
		inFile2.close();
		
		Scanner inFile3 = new Scanner(new File("passwords.txt"));
		// Initialize books
		while (inFile3.hasNextLine())
		{
			library.passwords.add(new Integer(inFile3.nextLine()));
		}
		inFile3.close();
		
		Scanner inFile4 = new Scanner(new File("users.txt"));
		// Initialize accounts
		while (inFile4.hasNext())
		{
			inFile4.useDelimiter(":");
			String u = inFile4.next();
			String numOut = inFile4.next();
			if(numOut.equals("0")){
				boolean m = inFile4.nextBoolean();
				library.accounts.add(new UserAccount(u, m));
			}
			else{
				int nOut = new Integer(numOut);
				ArrayList<String> bo = new ArrayList<String>();
				for(int i = 0; i < nOut; i++){
					bo.add(inFile4.next());
				}
				ArrayList<LocalDate> dd = new ArrayList<LocalDate>();
				for(int i = 0; i < nOut; i++){
					dd.add(LocalDate.parse(inFile4.next()));
				}
				boolean m = inFile4.nextBoolean();
				library.accounts.add(new UserAccount(u, bo, dd, m));
			}
		}
		inFile4.close();
		
		Scanner inFile5 = new Scanner(new File("books.txt"));
		// Initialize books
		while (inFile5.hasNext())
		{
			inFile5.useDelimiter(":");
			String t = inFile5.next();
			int numAuth = new Integer(inFile5.next());
			ArrayList<String> auth = new ArrayList<String>();
			for(int i = 0; i < numAuth; i++){
				auth.add(inFile5.next());
			}
			String sub = inFile5.next();
			int pnum = new Integer(inFile5.next());
			String ISBN = inFile5.next();
			int bAmnt = new Integer(inFile5.next());
			int AmntOut = new Integer(inFile5.next());
			String timeLim = inFile5.next();
			if(timeLim.equals("null")){
				library.books.add(new Book(t, auth, sub, pnum, ISBN, bAmnt, AmntOut));
			}
			else{
				library.books.add(new Book(t, auth, sub, pnum, ISBN, bAmnt, AmntOut, new Integer(timeLim)));
			}	
			
		}
		inFile5.close();
		
		for(int i = 0; i < books.size(); i++){
			Book b = books.get(i);
			String sub = b.getSubject();
			if(!subjects.contains(sub)){
				subjects.add(sub);
			}
		}
		
		//automatically return book if due date or past due date
		for(int i = 0; i < accounts.size(); i++){
			UserAccount a = accounts.get(i);
			ArrayList<String> bs = a.getBooksOut();
			ArrayList<LocalDate> dueDates = a.getDueDate();
			for(int k = 0; k < dueDates.size(); k++){
				if(dueDates.get(k).isEqual(currentDate) || dueDates.get(k).isBefore(currentDate)){
					String t = bs.get(k);
					a.removeBooksOut(t);
					for(int j = 0; j < books.size(); j++){
						Book b = books.get(j);
						if(b.getTitle().equals(t)){
							b.incrementBookAmnt();
						}
					}
					System.out.println(t + " Returned");
				}
			}
		}
		
		Page p = new Page();
		
	}

	/**Overwrites and updates files when system is closed.
	 *  
	 * @throws FilenNotFooundException
	 */
	public static void updateFiles() throws FileNotFoundException {
		PrintWriter pwu = new PrintWriter(new FileOutputStream("usernames.txt", false));
		for(int i = 0; i < usernames.size(); i++){
			pwu.println(usernames.get(i));
		}
		pwu.close();
		
		PrintWriter pwbn = new PrintWriter(new FileOutputStream("booknames.txt", false));
		for(int i = 0; i < bookNames.size(); i++){
			pwbn.println(bookNames.get(i));
		}
		pwbn.close();
		
		PrintWriter pwp = new PrintWriter(new FileOutputStream("passwords.txt", false));
		for(int i = 0; i < passwords.size(); i++){
			pwp.println(passwords.get(i));
		}
		pwp.close();
		
		PrintWriter pwb = new PrintWriter(new FileOutputStream("books.txt", false));
		for(int i = 0; i < books.size(); i++){
			Book b = books.get(i);
			pwb.write(b.toFile());
		}
		pwb.close();
		
		
		PrintWriter pwa = new PrintWriter(new FileOutputStream("users.txt", false));
		for(int i = 0; i < accounts.size(); i++){
			UserAccount a = accounts.get(i);
			pwa.write(a.toFile());
		}
		pwa.close();
		
		System.exit(0);
	}
	
	/**Creates file for the number of times a book has been checked out report.
	 */
	public static void numCheckBooksReport(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("Number_Of_Book_Checkouts", "UTF-8");
			for(int i = 0; i < books.size(); i++){
				Book b = books.get(i);
				writer.println(b.getTitle() + ", ISBN: " + b.getISBN());
				writer.println("Checked Out " + b.getTimesOut() + " times.");
				writer.println("");
			}
			writer.close();
			System.out.println("File saved as Number_Of_Book_Checkouts");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**Creates file for the currently available books report.
	 */
	public static void currAvailableBooksReport() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("Available_Books", "UTF-8");
			for(int i = 0; i < books.size(); i++){
				Book b = books.get(i);
				if(b.getBookAmount() > 0){
					writer.println(b.getTitle() + " ISBN: " + b.getISBN() + ", " + 
									b.getBookAmount() + " left.");
					writer.println("");
				}
			}
			writer.close();
			System.out.println("File saved as Available_Books");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**Creates file for the all accounts report.
	 */
	public static void allAccountsReport() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("All_Accounts", "UTF-8");
			for(int i = 0; i < accounts.size(); i++){
				UserAccount u = accounts.get(i);
					writer.println(u.getUsername());
					writer.println("Books Out" + u.getBooksOut());
					writer.println("");
			}
			writer.close();
			System.out.println("File saved as All_Accounts");
		}
		 catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**Creates file for the books by subject report.
	 */
	public static void booksBySubjectReport() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("Books_By_Subject", "UTF-8");
			for(int i = 0; i < subjects.size(); i++){
				String s = subjects.get(i);
				writer.println(s + ": ");
				for(int j = 0; j < books.size(); j++){
					Book b = books.get(j);
					if(b.getSubject().equals(s)){
						writer.println(b.getTitle());
					}
				}
				writer.println("");
			}
			writer.close();
			System.out.println("File saved as Books_By_Subject");
		}
		 catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**Creates file for the currently checked out books report.
	 */
	public static void currCheckedOutBookReport() {
		PrintWriter writer;
		try {
			writer = new PrintWriter("Currently_Checked_Out", "UTF-8");
			for(int i = 0; i < accounts.size(); i++){
				UserAccount u = accounts.get(i);
				ArrayList<String> bks = u.getBooksOut();
				for(int k = 0; k < bks.size(); k++){
					writer.println(bks.get(k));
					writer.println(u.getUsername());
					writer.println("");
				}
			}
			writer.close();
			System.out.println("File saved as Currently_Checked_Out");
		}
		 catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
