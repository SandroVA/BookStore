package g7w14.business;

import g7w14.data.BookAuthorBean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * Handles SQL queries to the bookauthor table
 * 
 * @author Martin Nafekh 1032763 
 * updated by Joseph He
 * 
 */
@Named
@RequestScoped
public class BookAuthorManager implements Serializable {

	private static final long serialVersionUID = -5550149166796100070L;

	@Resource(name = "jdbc/g7w14")
	private DataSource ds;

	/**
	 * Retrieves records with either a book ID or author ID that matches the
	 * passed bean and returns them in an ArrayList
	 * 
	 * @param sql
	 * @param bean
	 *            to search with
	 * @return arraylist of matching records
	 * @throws SQLException
	 */
	public ArrayList<BookAuthorBean> retrieveRecords(BookAuthorBean bean)
			throws SQLException {
		ArrayList<BookAuthorBean> rows = new ArrayList<>();
		String sql = "select * from bookauthor where bookid = ? or authorid = ?";

		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, bean.getBookId());
			stmt.setLong(2, bean.getAuthorId());

			try (ResultSet results = stmt.executeQuery()) {
				while (results.next()) {
					BookAuthorBean bab = new BookAuthorBean();
					bab.setBookId(results.getLong("BookId"));
					bab.setAuthorId(results.getLong("AuthorId"));
					rows.add(bab);
				}
			}
		}

		return rows;
	}

	/**
	 * Inserts a new record to pair a book with an author
	 * 
	 * @param bean
	 * @return integer indicating result of insert attempt
	 * @throws SQLException
	 */
	public int insertRecord(BookAuthorBean bean) throws SQLException {
		int result = 0;
		String sql = "insert into bookauthor(bookid, authorid) values (?,?)";

		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, bean.getBookId());
			stmt.setLong(2, bean.getAuthorId());
			result = stmt.executeUpdate();
		}

		return result;
	}

}
