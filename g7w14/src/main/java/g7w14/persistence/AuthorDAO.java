package g7w14.persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import g7w14.data.AuthorBean;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class provides the functionality to: open a database, retrieve records
 * from a author and return them as an arrayList, insert records, update records
 * of Author and authorbook tables
 * 
 * @author Svetlana Shopova, Sandro Victoria Arena and Martin Nafekh
 * @since 15.03.2014
 * @version 1.0
 */
@Named
@RequestScoped
public class AuthorDAO {
	@Resource(name = "jdbc/g7w14")
	private DataSource ds;

	/**
	 * This method returns all authors from table Author in database
	 * 
	 * @return - a list with authors
	 * @throws SQLException
	 * @author Svetlana Shopova
	 */
	public ArrayList<AuthorBean> getAll() throws SQLException {
		ArrayList<AuthorBean> allAuthors = new ArrayList<AuthorBean>();
		String sql = "Select * from author order by Last_Name";
		allAuthors = sellectAll(sql);
		return allAuthors;
	}// end getAll()

	/**
	 * @author Svetlana Shopova
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<AuthorBean> getAllByFirst() throws SQLException {
		ArrayList<AuthorBean> allAuthors = new ArrayList<AuthorBean>();
		String sql = "Select * from author order by First_Name";
		allAuthors = sellectAll(sql);
		return allAuthors;
	}// end of getAllByFirst()

	/**
	 * @author Svetlana Shopova
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	private ArrayList<AuthorBean> sellectAll(String sql) throws SQLException {
		ArrayList<AuthorBean> allAuthors = new ArrayList<AuthorBean>();
		if (ds == null)
			throw new SQLException("Can't get data source");
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);
				ResultSet rs = pStatement.executeQuery()) {
			while (rs.next()) {
				AuthorBean auth = new AuthorBean();
				auth.setAuthorId(rs.getLong("AuthorId"));
				auth.setAuthorFirstName(rs.getString("First_Name"));
				auth.setAuthorLastName(rs.getString("Last_Name"));

				allAuthors.add(auth);
			}
		}

		return allAuthors;
	}

	/**
	 * This method returns the amount of all sales for an author by defined from
	 * the admin data range
	 * 
	 * @param sql
	 *            - the sql statement
	 * @param firstName
	 *            - first name of the author
	 * @param lastName
	 *            - last name of the author
	 * @param startDate
	 *            - start of the required period
	 * @param endDate
	 *            - end day of the required period
	 * @return - amount of sales - double
	 * @throws SQLException
	 *             - if there is problem with connection
	 * @author Svetlana Shopova
	 */
	public double salesByAuthor(String sql, String firstName, String lastName,
			String startDate, String endDate) throws SQLException {
		ArrayList<Double> results = new ArrayList<Double>();

		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);
			pStatement.setString(3, startDate);
			pStatement.setString(4, endDate);

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					results.add(resultSet.getDouble(1));
				}
			}
		}

		return results.get(0);
	}// end of salesByAuthor

	/**
	 * This method converts a book id into a list of authorids.
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param sql
	 *            - the sql needed to get the authorid
	 * @param bookId
	 *            - the bookid
	 * @return - A list of all author ids related to the book
	 * @throws SQLException
	 */
	public ArrayList<Long> convertBookIdToAuthorId(String sql, long bookId)
			throws SQLException {
		ArrayList<Long> results = new ArrayList<Long>();

		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setLong(1, bookId);

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					results.add(resultSet.getLong("AuthorId"));
				}
			}
		}

		return results;
	}// end of convertBookIdToAuthorId

	/**
	 * This method converts an author id into a list of book ids
	 * 
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param sql
	 *            the sql needed to get the bookid
	 * @param authorId
	 *            the author id
	 * @return a list of all book ids related to the author
	 * @throws SQLException
	 */
	public ArrayList<Long> convertAuthorIdToBookId(String sql, long authorId)
			throws SQLException {
		ArrayList<Long> results = new ArrayList<Long>();

		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setLong(1, authorId);

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					results.add(resultSet.getLong("BookId"));
				}
			}
		}

		return results;
	}

	/**
	 * This method gets all the authors names based on their ids
	 * 
	 * @author Sandro Victoria Arena, Ian Ozturk and Martin Nafekh
	 * @param sql
	 *            - the sql needed to get the author names
	 * @param id
	 *            - the authors ids
	 * @return - A list of all author names related to their ids
	 * @throws SQLException
	 */
	public ArrayList<AuthorBean> getAuthorsById(String sql, ArrayList<Long> ids)
			throws SQLException {
		ArrayList<AuthorBean> results = new ArrayList<AuthorBean>();

		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			for (int i = 0; i < ids.size(); i++) {
				pStatement.setLong(1, ids.get(i));

				try (ResultSet resultSet = pStatement.executeQuery()) {
					while (resultSet.next()) {
						AuthorBean bean = new AuthorBean();
						bean.setAuthorId(resultSet.getLong("AuthorId"));
						bean.setAuthorFirstName((resultSet
								.getString("First_Name")));
						bean.setAuthorLastName((resultSet
								.getString("Last_Name")));

						results.add(bean);
					}
				}
			}
		}

		return results;
	}// end of getAuthosById

	/**
	 * 
	 * @param sql
	 * @param firstName
	 * @return
	 * @throws SQLException
	 * @author Svetlana Shopova
	 */
	public ArrayList<AuthorBean> getAuthorsByFirstName(String sql,
			String firstName) throws SQLException {

		if (ds == null)
			throw new SQLException("Can't get data source");
		ArrayList<AuthorBean> results = new ArrayList<AuthorBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {

			pStatement.setString(1, firstName);

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					AuthorBean bean = new AuthorBean();
					bean.setAuthorId(resultSet.getLong("AuthorId"));
					bean.setAuthorFirstName((resultSet.getString("First_Name")));
					bean.setAuthorLastName((resultSet.getString("Last_Name")));

					results.add(bean);
				}
			}
		}

		return results;
	}// end of getAuthorsByFirstName()

	/**
	 * 
	 * @param sql
	 * @param lastName
	 * @return
	 * @throws SQLException
	 * @author Svetlana Shopova
	 */
	public ArrayList<AuthorBean> getAuthorsByLastName(String sql,
			String lastName) throws SQLException {

		if (ds == null)
			throw new SQLException("Can't get data source");
		ArrayList<AuthorBean> results = new ArrayList<AuthorBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {

			pStatement.setString(1, lastName);

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					AuthorBean bean = new AuthorBean();
					bean.setAuthorId(resultSet.getLong("AuthorId"));
					bean.setAuthorFirstName((resultSet.getString("First_Name")));
					bean.setAuthorLastName((resultSet.getString("Last_Name")));

					results.add(bean);
				}
			}
		}

		return results;
	}// end of getAuthorsByLastName()

	/**
	 * @author Svetlana Shopova
	 * @param firstName
	 * @param lastName
	 * @return
	 * @throws SQLException
	 */
	public int saveAuthor(String sql, String firstName, String lastName)
			throws SQLException {
		int result = 0;
		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);

			result = pStatement.executeUpdate();
		}
		return result;
	}// end of saveAuthor()

	/**
	 * @author Svetlana Shopova
	 * @param sql
	 * @param lastName
	 * @return
	 * @throws SQLException
	 */
	public int saveAuthor(String sql, String lastName) throws SQLException {
		int result = 0;
		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {

			pStatement.setString(1, lastName);

			result = pStatement.executeUpdate();
		}
		return result;
	}// end of saveAuthor()

	/**
	 * @author Svetlana Shopova
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public long getLastAuthor(String sql) throws SQLException {
		if (ds == null)
			throw new SQLException("Can't get data source");
		long id = 0;
		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);
				ResultSet rs = pStatement.executeQuery()) {
			while (rs.next()) {
				id = rs.getLong("AuthorId");
			}

		}

		return id;
	}// end of getLastAuthor()

	/**
	 * @author Svetlana Shopova
	 * @param sql
	 * @param authorId
	 * @param bookId
	 * @throws SQLException
	 */
	public void saveBookAuthor(String sql, long authorId, long bookId)
			throws SQLException {

		if (ds == null)
			throw new SQLException("Can't get data source");

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {

			pStatement.setLong(1, authorId);
			pStatement.setLong(2, bookId);
			pStatement.executeUpdate();
		}

	}// end of saveBookAuthor()

	/**
	 * @author Sandro Victoria Arena and Ian Ozturk
	 * @param sql
	 * @param firstName
	 * @param lastName
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<AuthorBean> getAuthorIdByName(String sql,
			String firstName, String lastName) throws SQLException {
		if (ds == null)
			throw new SQLException("Can't get data source");
		ArrayList<AuthorBean> results = new ArrayList<AuthorBean>();

		try (Connection conn = ds.getConnection();
				PreparedStatement pStatement = conn.prepareStatement(sql);) {

			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);

			try (ResultSet resultSet = pStatement.executeQuery()) {
				while (resultSet.next()) {
					AuthorBean bean = new AuthorBean();
					bean.setAuthorId(resultSet.getLong("AuthorId"));

					results.add(bean);
				}
			}
		}

		return results;
	}// end of getAuthorIdByName()
}// end of AuthorDAO class
