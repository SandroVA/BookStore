package g7w14.business;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import g7w14.data.AuthorBean;
import g7w14.data.BookBean;
import g7w14.data.CategoryBean;
import g7w14.enums.OperationType;
import g7w14.persistence.BookDAO;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;


/**
 * This class manages BookBean and create SQL statements for BookDAO
 * 
 * @author Ian Ozturk and Sandro Victoria Arena and Svetlana Shopova
 * 
 */
@Named("bookManager")
@SessionScoped
public class BookManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6615548301784592017L;
	@Inject
	private BookDAO bdao;
	@Inject
	private AuthorBean author;
	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);
	private ArrayList<Object> values;
	private ArrayList<Object> statement;
	private ArrayList<BookBean> data;
	@Inject
	private BookBean book;
	private ArrayList<String> allISBN ;
	@Inject	
	private CategoryBean category;

	private ArrayList<Long> allID;

	private ResourceBundle strings = ResourceBundle.getBundle("page_labels",
			Locale.getDefault());

	/**
	 * Insert a book that corresponds to the sent in bean
	 * 
	 * @param book
	 *            The bean that represents a book
	 * @return result The number of records inserted
	 * @author Ian Ozturk
	 */
	
	public String insertBook() {
		int result = 0;

		String selectStatement = "Insert into book(Title, FormatId,ISBN, Wholesale_price, List_price, Sale_price,"
				+ "CategoryId, PublisherId, Dimensions, Weight, Removal_Status,  Image, Number_Pages, Number_copies)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		
		values= new ArrayList <Object>();
		values.add(book.getTitle());
		values.add(book.getFormatId());
		values.add(book.getIsbn());
		values.add(book.getWholesalePrice());
		values.add(book.getListPrice());
		values.add(book.getSalePrice());
		values.add(book.getCategoryId());
		values.add(book.getPublisherId());
		values.add(book.getDimensions());
		values.add(book.getWeight());
		values.add(book.isRemovalStatus());
		values.add(book.getImage());
		values.add(book.getNumberPages());
		values.add(book.getNumberCopies());
		
	
		
		try {
			result = bdao.insertRecord(selectStatement, values);

		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		if (result == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("failSavingBook")));
			return null;
		}

		return "adminInsertAuthor";
	}

	/**
	 * Updates a book in the book table
	 * @return result The number of rows affected
	 * @author Ian Ozturk
	 */
	@SuppressWarnings("unchecked")
	public int updateBook() {

		String selectStatement = "Update book set ";

		statement = createSQL(selectStatement, OperationType.UPDATE);
		selectStatement = (String) statement.get(0);
		values = (ArrayList<Object>) statement.get(1);
		int result = 0;

		try {
			result = bdao.updateBook(book.getBookId(), selectStatement, values);
		} catch (SQLException e) {

			log.error("Error in updating a record in book table", e);
		}

		return result;
	}

	/**
	 * Retrieves all books in the book table
	 * 
	 * @return data List of all books
	 * @author Ian Ozturk
	 */
	public ArrayList<BookBean> retrieveBooks() {
		data = null;
		try {
			data = (ArrayList<BookBean>) bdao.retrieveRecords();
		} catch (SQLException e) {

			log.error("Error in retrieving a record in User table", e);
			return null;
		}

		return data;
	}

	/**
	 * Retrieves the 5 latest books in the book table
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return latestBooks List of all latest books
	 */
	public ArrayList<BookBean> retrieveLatestBooks() {
		data = null;
		try {
			data = (ArrayList<BookBean>) bdao.retrieveRecords();
		} catch (SQLException e) {

			log.error("Error in retrieving a record in User table", e);
			return null;
		}

		ArrayList<BookBean> latestBooks = new ArrayList<BookBean>();
		int y = 0;
		for (int i = 1; y < 5 && i < data.size() + 1; i++) {
			if (data.get(data.size() - i) != null
					&& (data.get(data.size() - i).getSalePrice() == null || data
							.get(data.size() - i).getSalePrice().signum() <= 0)) {
				latestBooks.add(data.get(data.size() - i));
				y++;
			}
		}

		return latestBooks;
	}

	/**
	 * Retrieves the 3 latest books in the book table by category
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param categoryId
	 * @param bookId
	 * @return latestBooks the 3 latest boooks in the book table
	 */
	public ArrayList<BookBean> retrieveLatestBooksByCategory(long categoryId,
			long bookId) {
		data = null;
		try {
			data = (ArrayList<BookBean>) bdao.retrieveRecords();
		} catch (SQLException e) {

			log.error("Error in retrieving a record in User table", e);
			return null;
		}

		ArrayList<BookBean> latestBooks = new ArrayList<BookBean>();
		int y = 0;
		for (int i = 1; y < 3 && i < data.size() + 1; i++) {
			if (data.get(data.size() - i).getCategoryId() == categoryId
					&& bookId != data.get(data.size() - i).getBookId()) {
				latestBooks.add(data.get(data.size() - i));
				y++;
			}
		}

		return latestBooks;
	}

	/**
	 * Retrieves the All latest books in the book table by category
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param categoryId
	 * @return latestBooks List of all books based on categoryId
	 */
	public ArrayList<BookBean> retrieveAllBooksByCategory(long categoryId) {
		data = null;
		try {
			data = (ArrayList<BookBean>) bdao.retrieveRecords();
		} catch (SQLException e) {

			log.error("Error in retrieving a record in User table", e);
			return null;
		}

		ArrayList<BookBean> latestBooks = new ArrayList<BookBean>();

		for (int i = 1; i < data.size() + 1; i++) {
			if (data.get(data.size() - i).getCategoryId() == categoryId) {
				latestBooks.add(data.get(data.size() - i));
			}
		}

		return latestBooks;
	}

	/**
	 * Retrieves the 5 latest books that are on sale in the book table
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @return latestBooks List of all latest books on sale
	 */
	public ArrayList<BookBean> retrieveLatestBooksOnSale() {
		data = null;
		try {
			data = (ArrayList<BookBean>) bdao.retrieveRecords();
		} catch (SQLException e) {

			log.error("Error in retrieving a record in User table", e);
			return null;
		}

		ArrayList<BookBean> latestBooks = new ArrayList<BookBean>();

		int y = 0;
		for (int i = 1; y < 5 && i < data.size() + 1; i++) {
			if (data.get(data.size() - i).getSalePrice() != null
					&& data.get(data.size() - i).getSalePrice().signum() > 0) {
				latestBooks.add(data.get(data.size() - i));
				y++;
			}
		}

		return latestBooks;
	}

	/**
	 * Searches for a books that match the sent in bean
	 * 
	 * @author Ian Ozturk
	 * @return data List of Books retrieved
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<BookBean> searchBook() {

		String selectStatement = "select * from book where ";

		statement = createSQL(selectStatement, OperationType.SEARCH);

		if (statement != null) {
			selectStatement = (String) statement.get(0);

			values = (ArrayList<Object>) statement.get(1);

			try {
				data = bdao.searchBook(selectStatement, values);
			} catch (SQLException e) {

				log.error("Error in searching a record in book table", e);
			}
		}

		return data;
	}

	public void resetBookBean() {
		book.setBookId(-1);
		book.setIsbn("");
		book.setTitle("");
		book.setPublisherId(-1);
		book.setNumberCopies(-1);
		book.setWholesalePrice(new BigDecimal(-1));
		book.setListPrice(new BigDecimal(-1));
		book.setSalePrice(new BigDecimal(-1));
		book.setCategoryId(-1);
		book.setFormatId(-1);
		book.setWeight(-1);
		book.setDimensions("");
		book.setRemovalStatus(false);
		book.setNumberPages(-1);
		book.setImage("");
	}

	/**
	 * Searches for a book that match the sent in bookid
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param id
	 *            The id that represents the book you're looking for
	 * @return data List of Books retrieved
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<BookBean> searchBookById(long id) {
		book = new BookBean();
		resetBookBean();
		book.setBookId(id);

		String selectStatement = "select * from book where ";

		statement = createSQL(selectStatement, OperationType.SEARCH);

		if (statement != null) {
			selectStatement = (String) statement.get(0);

			values = (ArrayList<Object>) statement.get(1);

			try {
				data = bdao.searchBook(selectStatement, values);
			} catch (SQLException e) {

				log.error("Error in searching a record in book table", e);
			}
		}

		return data;
	}

	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String searchBooksById(long id) {

		resetBookBean();
		book.setBookId(id);

		String selectStatement = "select * from book where ";

		statement = createSQL(selectStatement, OperationType.SEARCH);

		if (statement != null) {
			selectStatement = (String) statement.get(0);

			values = (ArrayList<Object>) statement.get(1);

			try {
				data = bdao.searchBook(selectStatement, values);
			} catch (SQLException e) {

				log.error("Error in searching a record in book table", e);
			}
		}

		return "adminBooksForEditing";
	}

	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public long getLastBookId() {
		long id = 0;

		try {
			id = bdao.getLastId("Select Max(BookId) as BookId from book");
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return id;
	}

	/**
	 * @author Svetlana Shopova - adminEditBooks
	 * 
	 * @return
	 */
	public ArrayList<String> getAllISBN() {
		
		if (allISBN == null) {
			allISBN = allIsbn();
		}

		

		return allISBN;
	}// end of getAllISBN()
	
	
	public ArrayList<String> allIsbn()
	{
		allISBN = new ArrayList<String>();
		if (data == null) {
			data = retrieveBooks();
		}

		allISBN.add("");
		for (BookBean b : data) {
			allISBN.add(b.getIsbn());
		}
		return allISBN;
	}

	/**
	 * @author Svetlana Shopova - adminEditBooks
	 * 
	 * @return
	 */
	public ArrayList<Long> getAllID() {
		if(allID==null)
		{
			allID= allId();
		}
		
		return allID;
		 
	}// end of getAllID()
	
	public ArrayList<Long> allId()
	{
		allID = new ArrayList<Long>();
		if (data == null) {
			data = retrieveBooks();
		}

		allID.add(0L);
		for (BookBean b : data) {
			allID.add(b.getBookId());
		}

		return allID;
	}

	/**
	 * @author Svetlana Shopova - adminBookForEditing
	 * @return
	 */
	public String updateAll() {
		String selectStatement = "Update book set ISBN=?, Title = ?, Number_Copies=?,"
				+ " Wholesale_price=?, List_price=?, Sale_price=?, Dimensions=?,"
				+ " Removal_Status=?, Number_Pages=?  where BookId=?";

		values = new ArrayList<Object>();

		for (BookBean b : data) {
			values.add(b.getIsbn());
			values.add(b.getTitle());
			values.add(b.getNumberCopies());
			values.add(b.getWholesalePrice());
			values.add(b.getListPrice());
			values.add(b.getSalePrice());
			values.add(b.getDimensions());
			values.add(b.isRemovalStatus());
			values.add(b.getNumberPages());
			

			try {
				bdao.updateBook(b.getBookId(), selectStatement, values);
			} catch (SQLException e) {

				log.error(e.getMessage());
			}
			values = new ArrayList<Object>();
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(strings.getString("updatedBook")));

		return null;
	}// end of updateAll()

	public ArrayList<BookBean> getData() {
		return data;
	}

	public void setData(ArrayList<BookBean> data) {
		this.data = data;
	}
	
	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public String getAllByAuthorFirstName()
	{
		String sql="select * from book "
				+ " join bookauthor using (BookId) "
				+ " join author using (AuthorId) "
				+ "where First_Name =?";
		values = new ArrayList<Object>();
		values.add(author.getAuthorFirstName());
		try {
			data = bdao.searchBook(sql, values);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("findingBookFailed")));
			log.error(e.getMessage());
			return null;
		}
		
		return "adminBooksForEditing";
	}//end of getAllByAuthorFirstName()
	
	/**
	 * @author Svetlana Shopova -adminEditBook
	 * @return
	 */
	public String getAllByAuthorLastName()
	{
		String sql="select * from book "
				+ " join bookauthor using (BookId) "
				+ " join author using (AuthorId) "
				+ "where Last_Name =?";
		values = new ArrayList<Object>();
		values.add(author.getAuthorLastName());
		try {
			data = bdao.searchBook(sql, values);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("findingBookFailed")));
			log.error(e.getMessage());
			return null;
		}
		
		return "adminBooksForEditing";
	}//end of getAllByAuthorLastName()
	
	/**
	 * @author Svetlana Shopova adminEditBooks
	 * @return
	 */
	public String getAllByISBN()
	{
		String sql="select * from book "
				+ " where isbn =?";
		values = new ArrayList<Object>();
		values.add(book.getIsbn());
		try {
			data = bdao.searchBook(sql, values);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("findingBookFailed")));
			log.error(e.getMessage());
			
		}
		
		return "adminBooksForEditing";
	}//end of getAllByAuthorLastName()
	
	/**
	 * @author Svetlana Shopova adminEditBooks
	 * @return
	 */
	public String getAllByCategory(String category)
	{
		String sql="select * from book "
				+ " join category using (CategoryId) "
				+ "where Category_Name =?";
		values = new ArrayList<Object>();
		values.add(category);
		try {
			data = bdao.searchBook(sql, values);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("findingBookFailed")));
			log.error(e.getMessage());
			return null;
		}		
		
		
		
		
		return "adminBooksForEditing";
	}//end of getAllByAuthorLastName()
	
	public void isExists()
	{
		
		String  sql ="select * from book where ISBN=? and Title=?";
		
		values = new ArrayList<Object>();
		values.add(book.getIsbn());
		values.add(book.getTitle());
		
		try {
			data = bdao.searchBook(sql, values);
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		if(data.size()>0)
		{
			book=data.get(0);
			book.setNumberCopies(0);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("adminAddOldBooks.xhtml");
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		
		
	}
	
	public String addNewBook()
	{
		String sql = "update  book set Number_copies=Number_copies+? , Wholesale_price=?, List_price=?, Sale_price=? , Removal_Status=? where BookId=?";
		
		int result = 0;
		
		values=new ArrayList<Object>();
		values.add(book.getNumberCopies());
		values.add(book.getWholesalePrice());
		values.add(book.getListPrice());
		values.add(book.getSalePrice());
		values.add(book.isRemovalStatus());
		
		
		
		try {
			result = bdao.updateBook(book.getBookId(), sql, values);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("findingBookFailed")));
			log.error(e.getMessage());
			return null;
		}
		
		if(result==1)
		{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("bookUpdate")));
		}
		
		if(result==0)
		{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(strings.getString("failingUpdateBook")));
		}
		
		return null;
	}
	
	public void setAllISBN(ArrayList<String> allISBN) {
		this.allISBN = allISBN;
	}

	public void setAllID(ArrayList<Long> allID) {
		this.allID = allID;
	}

	private void resetLists() {
		// keeps values of the fields which have not null value
		values = new ArrayList<Object>();

		data = new ArrayList<BookBean>();
	}

	/**
	 * Create an SQL statement
	 * 
	 * @author Ian Ozturk
	 * @param selectStatement
	 * @param type
	 *            Enum type of operation
	 * @return statement List containing sql statement and list with values for
	 *         prepared statements
	 */
	private ArrayList<Object> createSQL(String selectStatement,
			OperationType type) {
		statement = new ArrayList<Object>();
		resetLists();
		String addition = "";
		int start = selectStatement.length();

		switch (type) {
		case INSERT:
			addition = ",";
			break;
		case SEARCH:
			addition = "=? and ";
			break;
		case UPDATE:
			addition = "=?,";
			break;
		}

		if (type.equals(OperationType.SEARCH)) {
			if (book.getBookId() != -1) {
				selectStatement = selectStatement + ("BookId" + addition);
				values.add(new Long(book.getBookId()));
			}
		}

		if (book.getIsbn() != null && book.getIsbn().compareTo("") != 0) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "ISBN = ? and ";
				values.add(book.getIsbn());
			} else {
				selectStatement = selectStatement + "ISBN" + addition;
				values.add(book.getIsbn());
			}
		}

		if (book.getTitle() != null && book.getTitle().compareTo("") != 0) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "Title = ? and ";
				values.add(book.getTitle());
			} else {
				selectStatement = selectStatement + "Title" + addition;
				values.add(book.getTitle());
			}
		}

		if (book.getPublisherId() != -1) {
			selectStatement = selectStatement + ("PublisherId" + addition);
			values.add(new Long(book.getPublisherId()));
		}

		if (book.getNumberCopies() != -1) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "Number_copies = ? and "
						+ addition;
				values.add(book.getNumberCopies());
			} else {
				selectStatement = selectStatement + "Number_copies" + addition;
				values.add(book.getNumberCopies());
			}
		}

		if (book.getWholesalePrice() != null
				&& !book.getWholesalePrice().equals(new BigDecimal(-1))) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "Wholesale_price = ? and "
						+ addition;
				values.add(book.getWholesalePrice());
			} else {
				selectStatement = selectStatement + "Wholesale_price"
						+ addition;
				values.add(book.getWholesalePrice());
			}
		}

		if (book.getListPrice() != null
				&& !book.getListPrice().equals(new BigDecimal(-1))) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "List_price = ? and "
						+ addition;
				values.add(book.getWholesalePrice());
			} else {
				selectStatement = selectStatement + "List_price" + addition;
				values.add(book.getWholesalePrice());
			}
		}

		if (book.getSalePrice() != null
				&& !book.getSalePrice().equals(new BigDecimal(-1))) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "Sale_price = ? and "
						+ addition;
				values.add(book.getWholesalePrice());
			} else {
				selectStatement = selectStatement + "Sale_price" + addition;
				values.add(book.getWholesalePrice());
			}
		}

		if (book.getCategoryId() != -1) {
			selectStatement = selectStatement + ("CategoryId" + addition);
			values.add(new Long(book.getCategoryId()));
		}

		if (book.getFormatId() != -1) {
			selectStatement = selectStatement + ("FormatId" + addition);
			values.add(new Long(book.getFormatId()));
		}

		if (book.getWeight() != -1) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "Weight = ? and "
						+ addition;
				values.add(book.getWeight());
			} else {
				selectStatement = selectStatement + "Weight" + addition;
				values.add(book.getWeight());
			}
		}

		if (book.getDimensions() != null
				&& book.getDimensions().compareTo("") != 0) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "Dimensions = ? and "
						+ addition;
				values.add(book.getDimensions());
			} else {
				selectStatement = selectStatement + "Dimensions" + addition;
				values.add(book.getDimensions());
			}
		}

		if (book.getDateAdded() != null
				&& book.getDateAdded().after(new Timestamp(0))) {
			selectStatement = selectStatement + "Date_Added" + addition;
			values.add(book.getDateAdded());
		}

		if (book.getNumberPages() != -1) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "Number_Pages = ? and "
						+ addition;
				values.add(book.getNumberPages());
			} else {
				selectStatement = selectStatement + "Number_Pages" + addition;
				values.add(book.getNumberPages());
			}
		}

		if (type.equals(OperationType.SEARCH)) {
			if (book.isRemovalStatus() == true) {
				selectStatement = selectStatement + "Removal_Status" + addition;
				values.add(new Boolean(book.isRemovalStatus()));
			}
		} else {
			selectStatement = selectStatement + "Removal_Status" + addition;
			values.add(new Boolean(book.isRemovalStatus()));
		}

		if (book.getImage() != null && book.getImage().compareTo("") != 0) {
			if (type.equals(OperationType.SEARCH)) {
				selectStatement = selectStatement + "Image = ? and " + addition;
				values.add(book.getImage());
			} else {
				selectStatement = selectStatement + "Image" + addition;
				values.add(book.getImage());
			}
		}

		switch (type) {
		case INSERT:
			String params = "values(";
			for (int i = 0; i < values.size(); i++) {
				params = params + "?,";
			}

			params = params.substring(0, params.length() - 1) + ")";
			selectStatement = selectStatement.substring(0,
					(selectStatement.length() - 1))
					+ ")" + params;
			break;
		case SEARCH:
			if (selectStatement.length() == start) {
				return null;
			}

			selectStatement = selectStatement.substring(0,
					(selectStatement.length() - 4));
			// + " order by Date_Added";
			break;
		case UPDATE:
			selectStatement = selectStatement.substring(0,
					(selectStatement.length() - 1))
					+ " where BookId = ?";
			break;
		}

		statement.add(selectStatement);
		statement.add(values);

		return statement;
	}
	
	

}
