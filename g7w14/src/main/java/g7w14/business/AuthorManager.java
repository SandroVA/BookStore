package g7w14.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import g7w14.data.AuthorBean;
import g7w14.data.BookBean;
import g7w14.data.CalendarBean;
import g7w14.data.CategoryBean;
import g7w14.data.PublisherBean;
import g7w14.persistence.AuthorDAO;
import g7w14.util.DateConverter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.openejb.util.Connect;
import org.apache.openejb.util.LogCategory;
import org.apache.openejb.util.Logger;

/**
 * This class is responsible for inserting, updating and getting total sales by
 * an author.
 * 
 * @author Svetlana Shopova, Sandro Victoria Arena and Martin Nafekh
 * 
 */
@Named("authorManager")
@RequestScoped
public class AuthorManager implements Serializable {

	private static final long serialVersionUID = -8921126897285623776L;
	@Inject
	private AuthorBean author;
	@Inject
	private AuthorDAO adao;
	@Inject
	private BookBean book;
	ArrayList<AuthorBean> allAuthors;
	@Inject
	private CalendarBean calendar;
	@Inject
	private BookManager bookManager;
	private String fullName;
	@Inject
	private CategoryBean category;
	@Inject
	private PublisherBean publisher;
	
	private ResourceBundle strings = ResourceBundle.getBundle("page_labels",
			Locale.getDefault());
	
	private Logger log = Logger.getInstance(LogCategory.OPENEJB, Connect.class);

	/**
	 * This method return a list with all authors in database
	 * @author Svetlana Shopova
	 * @return list with authors
	 */
	public ArrayList<AuthorBean> getAll() {
		ArrayList<AuthorBean> all = null;

		try {
			all = adao.getAll();
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return all;
	}// end of getAll()

	/**
	 * Returns a list of authors matching the passed author ID
	 * 
	 * @author Sandro Victoria Arena, Ian Ozturk, and Martin Nafekh
	 * @param authorId
	 * @return list of matching author IDs
	 */
	public ArrayList<AuthorBean> getAuthorById(long authorId) {
		ArrayList<AuthorBean> authors = new ArrayList<AuthorBean>();
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(authorId);
		String sql = "select * from author where authorId = ?";

		try {
			authors = adao.getAuthorsById(sql, ids);
		} catch (SQLException e) {
			log.error("Error in searching a record in book table", e);
		}
		return authors;
	}
	
	/**
	 * This method returns a list with all books matching a given author ID
	 * 
	 * @author Martin Nafekh, Sandro Victoria Arena and Ian Ozturk
	 * @param authorId
	 *            to search for books
	 * @return list of associated books
	 */
	public ArrayList<BookBean> getAuthorBooks(long authorId) {
		ArrayList<BookBean> all = new ArrayList<BookBean>();
		ArrayList<Long> bookIds = null;

		try {
			String sql = "select * from bookauthor where authorId = ?";
			bookIds = adao.convertAuthorIdToBookId(sql, authorId);

			for (Long id : bookIds) {
				all.add(bookManager.searchBookById(id).get(0));
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return all;
	}

	
	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public ArrayList<AuthorBean> getAllByFirstName() {
		ArrayList<AuthorBean> all = null;

		try {
			all = adao.getAllByFirst();
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return all;
	}// end of getAll()
	/**
	 * This method returns a list with all authors matching a given book ID
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param bookId
	 *            to search for authors
	 * @return list of associated authors
	 */
	public ArrayList<AuthorBean> getBookAuthors(long bookId) {
		ArrayList<AuthorBean> all = null;
		ArrayList<Long> authorIds = null;
		try {
			String sql = "Select * from bookAuthor where bookId = ?";
			authorIds = adao.convertBookIdToAuthorId(sql, bookId);
			sql = "Select * from author where authorId = ?";
			all = adao.getAuthorsById(sql, authorIds);

		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		return all;
	}// end of getBookAuthor

	/**
	 * This method return the sales by an author in specific date range
	 * @author Svetlana Shopova 
	 * Updated by Joseph He
	 * @return the amount of sales by an author
	 */
	public String getSalesByAuthor() {
		double totals = 0.0;
		String sql = "select sum(quantity*price) "
				+ "from orders join orderItem using (OrderId) "
				+ "join bookauthor using (BookId) "
				+ "join author using (AuthorId) "
				+ "where author.First_Name = ? and  author.Last_Name = ? "
				+ " and Order_Date between ? and ? and Available=1";
		setNamesFromFullName();
		try {
			totals = adao.salesByAuthor(sql, author.getAuthorFirstName(),
					author.getAuthorLastName(),
					DateConverter.convertDate(calendar.getStartSelectedDate()),
					DateConverter.convertDate(calendar.getEndSelectedDate()));

		} catch (SQLException e) {

			log.error(e.getMessage());
		}

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(strings.getString("totalSale") + " "
						+ author.getAuthorFirstName() + " "
						+ author.getAuthorLastName() + " "
						+ String.valueOf(totals)));
		return null;
	}// end of getSalesByAuthor()

	/**
	 * This method returns a list with the names of the author in format
	 * "lastName.firstName"
	 * @ author Svetlana Shopova
	 * @return - list with strings with full names of the authors
	 */
	public ArrayList<String> getAllAuthorNames() {
		ArrayList<String> allNames = new ArrayList<String>();
		ArrayList<AuthorBean> all = getAll();

		for (AuthorBean aut : all) {
			if (aut.getAuthorFirstName() != null) {
				allNames.add(aut.getAuthorLastName() + "."
						+ aut.getAuthorFirstName());
			} else {
				allNames.add(aut.getAuthorLastName());
			}
		}

		return allNames;
	}// end of getAllAuthorNames()

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * This method splits full name of the author to first name and last name
	 * and set author bean's fields
	 * @author Svetlana Shopova
	 */
	private void setNamesFromFullName() {

		String[] names = fullName.split("\\.");

		author.setAuthorLastName(names[0]);
		if (names.length > 1) {
			author.setAuthorFirstName(names[1]);
		} else {
			author.setAuthorFirstName(null);
		}
	}// end of setNamesFromFullName()

	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public ArrayList<String> getAllLastNames() {
		
		ArrayList<String> allLastNames = new ArrayList<String>();	
		if(allAuthors==null)
		{
			allAuthors=getAll();		}
			
			
		allLastNames.add(" ");
		for (AuthorBean ab :allAuthors) {
			if(!allLastNames.contains(ab.getAuthorLastName())) {
				allLastNames.add(ab.getAuthorLastName());
			}
			//allLastNames.add(ab.getAuthorLastName());
		}

		return allLastNames;

	}//end of getAllLastNames()

	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public ArrayList<String> getAllFirstNames() {
		ArrayList<String> allFirstNames = new ArrayList<String>();
			
			if(allAuthors==null)
			{
				allAuthors=getAllByFirstName();
			}
			
			allFirstNames.add(" ");
		for (AuthorBean ab : allAuthors) {
			if (ab.getAuthorFirstName() != null && !ab.getAuthorFirstName().trim().equals("") && !allFirstNames.contains(ab.getAuthorFirstName())) {
				allFirstNames.add(ab.getAuthorFirstName());
			}
		}
		Collections.sort(allFirstNames);

		return allFirstNames;
	}// end of getAllFirstNames()

	/**
	 * @author Svetlana Shopova
	 */
	public void getLastNamesByFirst() {
	
		String sql = "Select * from author where First_Name=?";
		
				
		try {
			
			
			allAuthors = adao.getAuthorsByFirstName(sql, author.getAuthorFirstName());

		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		
	}//getLastNamesByFirst()

	/**
	 * @author Svetlana Shopova
	 */
	public void getFirstNamesByLast() {
		String sql = "Select * from author where Last_Name=? ";
		
		try {			

			allAuthors = adao.getAuthorsByFirstName(sql, author.getAuthorLastName());

		} catch (SQLException e) {
			log.error(e.getMessage());
		}

		
	}//end of getFirstNamesByLast()
	
	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public String saveAuthor()
	{
		
			
		
		String sql="insert into author(First_Name,Last_Name) values(?,?)";
		int result=0;
		
		if(author.getAuthorFirstName()!=null || !author.getAuthorFirstName().trim().equals(""))
		{
			try {
				result=adao.saveAuthor(sql,author.getAuthorFirstName(), author.getAuthorLastName());
			} catch (SQLException e) {
				
				log.error(e.getMessage());
			}
		}
		else
		{
			try {
				result=adao.saveAuthor(sql,author.getAuthorLastName());
			} catch (SQLException e) {
				log.error(e.getMessage());
				}
		}
		
		if(result==0)
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("createAuthorFail")));
			return null;
		}
		
		
		try {
			author.setAuthorId(adao.getLastAuthor("select Max(AuthorId)  as AuthorId from author"));
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		saveBAuthor();
		return null;
	}//end of saveAuthor()
	
	
	/**
	 * @author Svetlana Shopova
	 * @return  return to page adminInsertAuthor
	 */
	public String createBookAuthorKey()
	{
		
		
		author.setAuthorId(getAuthorId());
		saveBAuthor();
		return null;
	}//end of createBookAuthorKey()
	
	
	/**
	 * @author Svetlana Shopova
	 * @return
	 */
	public long getAuthorId()
	{
		ArrayList<AuthorBean> authors=new ArrayList<AuthorBean>();
		
		try {
			authors= adao.getAuthorIdByName("Select AuthorId from author where First_Name=? and Last_Name=?", 
					author.getAuthorFirstName(), author.getAuthorLastName());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return authors.get(0).getAuthorId();
	}//end of getAuthorId()
	
	
	/**
	 * @author Svetlana Shopova
	 */
	private void saveBAuthor() 
	{
		book.setBookId(bookManager.getLastBookId());
		
		try {
			adao.saveBookAuthor("insert into bookauthor(AuthorId,BookId) values (?,?)", author.getAuthorId(), book.getBookId());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}//end of saveBAuthor()
	
	
	/**
	 * @author Svetlana
	 * @return
	 */
	public String refreshBeans()
	{
		if(author.getAuthorLastName()==null || author.getAuthorLastName().trim().equals("") )
		{
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(strings.getString("insertAuthorFail")));
			return null;
			
		}
		refreshedBean();
		
		return "adminInsertBooks";
	}
	
	private void refreshedBean()
	{
		book.setImage("");
		book.setNumberCopies(0);
		book.setNumberPages(0);
		book.setIsbn("");
		book.setListPrice(new BigDecimal("0.00"));
		book.setSalePrice(new BigDecimal("0.00"));
		book.setWholesalePrice(new BigDecimal("0.00"));
		book.setWeight(0);
		book.setDimensions("");
		book.setTitle("");
		category.setName("");
		publisher.setPublisherName("");
		author.setAuthorFirstName("");
		author.setAuthorLastName("");
	}
	
	public String editBooksBackButton()
	{
		refreshedBean();
		return "adminEditBooks";
	}
	
}// end of AuthorManager class
