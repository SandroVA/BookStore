package g7w14.business;

import g7w14.data.AuthorBean;
import g7w14.data.BookAuthorBean;
import g7w14.data.BookBean;
import g7w14.data.BookDisplayBean;
import g7w14.data.SearchBean;
import g7w14.persistence.BookDAO;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * The search manager receives the call from the search bar, then uses the
 * values from the search bean, as set by the search form, to create a query
 * statement to search the database and return the results as a list of book
 * beans
 * 
 * @author Martin Nafekh 1032763
 * 
 */
@Named("searchManager")
@RequestScoped
public class SearchManager implements Serializable {

	private static final long serialVersionUID = -4216793298429427706L;
	@Inject
	BookManager bManager;
	@Inject
	BookDAO dao;
	@Inject
	BookAuthorManager baManager;
	@Inject
	AuthorManager aManager;

	@Resource(name = "jdbc/g7w14")
	private DataSource ds;

	private ArrayList<BookDisplayBean> books;
	private ArrayList<BookBean> searchResults;

	public SearchManager() {
		super();
		books = new ArrayList<BookDisplayBean>();
		searchResults = new ArrayList<BookBean>();
	}

	/**
	 * Action method that performs a search and returns the page used to display
	 * the results
	 * 
	 * @param search
	 * @return
	 * @throws SQLException
	 */
	public String search(SearchBean search) throws SQLException {
		searchBookByField(search.getField(), search.getValue());
		return "userSearchResults";
	}

	/**
	 * Searches for books that match the field and value passed in
	 * 
	 * @param field
	 *            the selected search criteria
	 * @param value
	 *            the string to search for
	 * @throws SQLException
	 */
	public void searchBookByField(String field, String value)
			throws SQLException {
		ArrayList<BookAuthorBean> rows = new ArrayList<BookAuthorBean>();
		boolean isAuthorSearch = false;

		if (value != null && !value.equals("")) {
			String statement = "select bookid from book where ";
			switch (field) {
			case "title":
				statement += "Title";
				break;
			case "author":
				statement = "select authorid from author where first_name like ? or last_name";
				isAuthorSearch = true;
				break;
			case "isbn":
				statement += "ISBN";
				break;
			}
			statement += " like ?";

			System.out.println("statement=" + statement);

			try (Connection conn = ds.getConnection();
					PreparedStatement stmt = conn.prepareStatement(statement)) {
				stmt.setString(1, "%" + value + "%");
				if (isAuthorSearch) {
					stmt.setString(2, "%" + value + "%");
				}

				try (ResultSet results = stmt.executeQuery()) {
					while (results.next()) {
						BookAuthorBean bab = new BookAuthorBean();
						if (isAuthorSearch) {
							bab.setAuthorId(results.getInt("AuthorId"));
						} else {
							bab.setBookId(results.getInt("BookId"));
						}
						rows.add(bab);
					}
				}
			}

			prepareBooks(rows, isAuthorSearch);
		}
	}

	/**
	 * Accepts a list of IDs retrieved from the database and matches them with
	 * necessary data
	 * 
	 * @param bookAuthors
	 *            list of either book or author IDs from the database
	 * @param isAuthorSearch
	 *            indicates whether a search is being done for an author
	 */
	public void prepareBooks(ArrayList<BookAuthorBean> bookAuthors,
			boolean isAuthorSearch) {

		if (isAuthorSearch) {
			searchResults = matchAuthorToBooks(bookAuthors);
		} else {
			for (BookAuthorBean bab : bookAuthors) {
				searchResults.add(bManager.searchBookById(bab.getBookId()).get(
						0));
			}
		}
	}

	/**
	 * Matches a list of authors to their respective books and returns the list
	 * of books
	 * 
	 * @param bookAuthors
	 *            list of author IDs to match
	 * @return books written by passed in list of authors
	 */
	public ArrayList<BookBean> matchAuthorToBooks(
			ArrayList<BookAuthorBean> bookAuthors) {
		ArrayList<BookBean> authorBooks = new ArrayList<BookBean>();
		ArrayList<BookBean> matchedBooks = new ArrayList<BookBean>();

		for (BookAuthorBean bab : bookAuthors) {
			AuthorBean author = aManager.getAuthorById(bab.getAuthorId())
					.get(0);
			authorBooks = aManager.getAuthorBooks(author.getAuthorId());
			if (!matchedBooks.containsAll(authorBooks))
				matchedBooks.addAll(authorBooks);
		}

		return matchedBooks;
	}

	/**
	 * Returns list of books that have been matched with all their respective
	 * authors once search is complete
	 * 
	 * @author Martin Nafehk and Sandro Victoria Arena
	 * 
	 * @return search result
	 */
	public ArrayList<BookDisplayBean> getBooksToDisplay() {
		return books;
	}

	/**
	 * Returns the list of books found via the search process. If only one book
	 * is returned, the user is redirected to that book's detail page.
	 * 
	 * @return arraylist of books returned by the search
	 */
	public ArrayList<BookBean> getSearchResults() {
		if (searchResults.size() == 1) {
			try {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								"userGetBook.xhtml?bookId="
										+ searchResults.get(0).getBookId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return searchResults;
	}
}
